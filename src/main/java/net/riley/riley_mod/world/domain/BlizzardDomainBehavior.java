package net.riley.riley_mod.world.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.particle.RileyModParticles;

import java.util.UUID;

public final class BlizzardDomainBehavior implements DomainBehavior {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion");

    private static final double BASE_RADIUS = 10.0;
    private static final double BASE_HEIGHT_INFLATE = 4.0;
    private static final int BASE_PARTICLES_PER_PULSE = 20;
    private static final int MAX_PARTICLE_MULTIPLIER = 3; // cap at 3x base


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

        int particleCount = scaledParticleCount(BASE_PARTICLES_PER_PULSE, m);

        spawnParticles(level, center, particleCount, radius);
        applyFreeze(level, owner, center, radius, height);
    }

    private static int scaledParticleCount(int base, int level) {
        // Sub-linear scaling: base * sqrt(level), capped to avoid particle storms with multiple domains
        double scaled = base * Math.sqrt(level);
        int cap = base * MAX_PARTICLE_MULTIPLIER;
        return Math.max(base, Math.min(cap, (int) Math.round(scaled)));
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

    private static void spawnParticles(ServerLevel level, BlockPos center, int count, double radius) {
        RandomSource rand = level.getRandom();

        for (int i = 0; i < count; i++) {
            double angle = rand.nextDouble() * (Math.PI * 2.0);
            double dist = Math.sqrt(rand.nextDouble()) * radius;

            double x = center.getX() + 0.5 + Math.cos(angle) * dist + (rand.nextDouble() - 0.5) * 0.2;
            double z = center.getZ() + 0.5 + Math.sin(angle) * dist + (rand.nextDouble() - 0.5) * 0.2;

            BlockPos ground = level.getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.containing(x, center.getY(), z)
            );

            double y = ground.getY() + 0.2 + rand.nextDouble() * 1.0;

            boolean variant = rand.nextBoolean();
            var type = variant ? RileyModParticles.BLIZZARD_PARTICLE.get() : RileyModParticles.BLIZZARD_PARTICLE_2.get();
            level.sendParticles(type, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }
}