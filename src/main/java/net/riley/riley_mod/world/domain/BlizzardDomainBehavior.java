package net.riley.riley_mod.world.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.AABB;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.effect.RileyModEffects;

import java.util.UUID;

public final class BlizzardDomainBehavior implements DomainBehavior {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion");

    private static final double BASE_RADIUS = 10.0;
    private static final double BASE_HEIGHT_INFLATE = 4.0;



    private static final int FREEZE_REFRESH_TICKS = 12;
    private static final int FREEZE_AMPLIFIER = 0;

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

        applyFreeze(level, owner, center, radius, height);
    }

    private static void applyFreeze(ServerLevel level, ServerPlayer owner, BlockPos center, double radius, double heightInflate) {
        AABB box = new AABB(center).inflate(radius, heightInflate, radius);
        UUID ownerId = owner.getUUID();

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (target == owner) continue;
            if (!target.isAlive()) continue;
            if (isOwnedBy(target, ownerId)) continue;

            target.addEffect(new MobEffectInstance(
                    RileyModEffects.FREEZE.get(),
                    FREEZE_REFRESH_TICKS,
                    FREEZE_AMPLIFIER,
                    false,
                    true,
                    true
            ));
        }
    }

    private static boolean isOwnedBy(LivingEntity target, UUID ownerId) {
        if (target instanceof TamableAnimal tamable) {
            return tamable.isTame() && tamable.getOwnerUUID() != null && tamable.getOwnerUUID().equals(ownerId);
        }
        if (target instanceof AbstractHorse horse) {
            return horse.isTamed() && horse.getOwnerUUID() != null && horse.getOwnerUUID().equals(ownerId);
        }
        if (target instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID() != null && ownable.getOwnerUUID().equals(ownerId);
        }
        return false;
    }
}