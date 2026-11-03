package net.riley.riley_mod.world.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.AABB;
import net.riley.riley_mod.RileyMod;

public final class HuricaneDomainBehavior implements DomainBehavior {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion");

    private static final double BASE_RADIUS = 10.0;
    private static final double BASE_HEIGHT_INFLATE = 4.0;

    private static final int SPEED_REFRESH_TICKS = 14; // refresh often so it feels continuous
    private static final int SPEED_AMPLIFIER = 0;      // Swiftness I (adjust if you want scaling)

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void onPulse(ServerLevel level, ServerPlayer owner, BlockPos center) {
        onPulse(level, owner, center, 1);
    }

    @Override
    public void onPulse(ServerLevel level, ServerPlayer owner, BlockPos center, int augmentLevel) {
        int m = Math.max(1, augmentLevel);

        double radius = BASE_RADIUS * m;
        double height = BASE_HEIGHT_INFLATE * m;

        applySwiftness(level, center, radius, height);
    }

    private static void applySwiftness(ServerLevel level, BlockPos center, double radius, double heightInflate) {
        AABB box = new AABB(center).inflate(radius, heightInflate, radius);

        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, box)) {
            if (!player.isAlive()) continue;
            if (player.isSpectator()) continue;

            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    SPEED_REFRESH_TICKS,
                    SPEED_AMPLIFIER,
                    false,
                    true,
                    true
            ));
        }
    }
}