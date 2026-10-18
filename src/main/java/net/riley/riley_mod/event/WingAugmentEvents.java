package net.riley.riley_mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WingAugmentEvents {
    private static final ResourceLocation WINGS_ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        if (!AugmentData.isUnlocked(player, WINGS_ID) || !AugmentData.isActive(player, WINGS_ID)) {
            WingGlideState.clear(player);
            return;
        }

        int level = AugmentData.getLevel(player, WINGS_ID);
        if (level < 1) {
            player.fallDistance = 0.0F;
            WingGlideState.clear(player);
            return;
        }

        boolean actuallyLanded = isActuallyCloseToGround(player);

        if ((player.onGround() && actuallyLanded) || player.isInWater() || player.isInLava()) {
            player.fallDistance = 0.0F;
            WingGlideState.clear(player);
            return;
        }

        // If glide was requested, keep it active until landing
        if (level >= 2 && WingGlideState.isGliding(player)) {
            player.fallDistance = 0.0F;

            if (!player.isFallFlying()) {
                player.startFallFlying();
            }

            return;
        }

        // Level 1: hold sneak for feather-fall behavior
        if (player.isShiftKeyDown()) {
            player.fallDistance = 0.0F;
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5, 0, false, false, false));
            return;
        }
    }
    private static boolean isActuallyCloseToGround(ServerPlayer player) {
        Vec3 start = player.position();
        Vec3 end = start.add(0.0D, -0.15D, 0.0D);

        return player.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        )).getType() == HitResult.Type.BLOCK;
    }
}