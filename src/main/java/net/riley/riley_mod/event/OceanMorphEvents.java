package net.riley.riley_mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.MorphData;
import net.riley.riley_mod.util.OceanMorphs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class OceanMorphEvents {
    private OceanMorphEvents() {}

    private static final int BEACHING_GRACE_TICKS = 100;
    private static final int BEACHING_DAMAGE_INTERVAL_TICKS = 40;
    private static final float BEACHING_DAMAGE = 2.0F;

    private static final double BEACHED_MOVEMENT_SPEED = 0.05D;

    private static final UUID BEACHED_MOVEMENT_SPEED_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000201");

    private static final String BEACHED_MOVEMENT_SPEED_NAME = "Ocean morph beached movement speed";

    private static final Map<UUID, Integer> BEACHED_TICKS = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!(event.player instanceof ServerPlayer player)) {
            return;
        }

        ResourceLocation currentMorph = MorphData.getCurrentMorph(player);

        if (!OceanMorphs.isOceanMorph(currentMorph)) {
            BEACHED_TICKS.remove(player.getUUID());
            removeBeachedSpeed(player);
            return;
        }

        handleOceanMorphTick(player);
    }

    private static void handleOceanMorphTick(ServerPlayer player) {
        if (isInWater(player)) {
            handleInWater(player);
            return;
        }

        handleBeached(player);
    }

    private static boolean isInWater(ServerPlayer player) {
        return player.isInWater()
                || player.isInWaterOrBubble()
                || player.level().getFluidState(player.blockPosition()).is(FluidTags.WATER);
    }

    private static void handleInWater(ServerPlayer player) {
        BEACHED_TICKS.remove(player.getUUID());
        removeBeachedSpeed(player);
        player.setAirSupply(player.getMaxAirSupply());
        player.clearFire();
    }

    private static void handleBeached(ServerPlayer player) {
        int beachedTicks = BEACHED_TICKS.merge(player.getUUID(), 1, Integer::sum);

        applyBeachedSpeed(player);

        int maxAir = player.getMaxAirSupply();
        int remainingAir = Math.max(0, maxAir - beachedTicks);
        player.setAirSupply(remainingAir);

        if (beachedTicks < maxAir + BEACHING_GRACE_TICKS) {
            return;
        }

        if (beachedTicks % BEACHING_DAMAGE_INTERVAL_TICKS == 0) {
            player.hurt(player.damageSources().dryOut(), BEACHING_DAMAGE);
        }
    }

    private static void applyBeachedSpeed(ServerPlayer player) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(BEACHED_MOVEMENT_SPEED_ID);

        double currentSpeed = movementSpeed.getValue();
        double difference = BEACHED_MOVEMENT_SPEED - currentSpeed;

        movementSpeed.addTransientModifier(new AttributeModifier(
                BEACHED_MOVEMENT_SPEED_ID,
                BEACHED_MOVEMENT_SPEED_NAME,
                difference,
                AttributeModifier.Operation.ADDITION
        ));
    }

    private static void removeBeachedSpeed(ServerPlayer player) {
        AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        movementSpeed.removeModifier(BEACHED_MOVEMENT_SPEED_ID);
    }
}