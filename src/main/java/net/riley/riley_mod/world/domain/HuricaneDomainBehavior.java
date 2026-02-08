package net.riley.riley_mod.world.domain;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.particle.OrbitingParticleData;
import net.riley.riley_mod.particle.RileyModParticles;

public final class HuricaneDomainBehavior implements DomainBehavior {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion");

    private static final double BASE_RADIUS = 10.0;
    private static final double BASE_HEIGHT_INFLATE = 4.0;

    private static final int BASE_PARTICLES_PER_PULSE = 24;
    private static final int MAX_PARTICLE_MULTIPLIER = 3; // cap for performance when stacking domains

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

        int particleCount = scaledParticleCount(BASE_PARTICLES_PER_PULSE, m);
        spawnRotatingRingParticles(level, center, particleCount, radius);
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

    private static int scaledParticleCount(int base, int level) {
        // Sub-linear scaling: base * sqrt(level), capped to avoid particle storms with multiple domains
        double scaled = base * Math.sqrt(level);
        int cap = base * MAX_PARTICLE_MULTIPLIER;
        return Math.max(base, Math.min(cap, (int) Math.round(scaled)));
    }

    private static void spawnRotatingRingParticles(ServerLevel level, BlockPos center, int count, double radius) {
        float angularSpeed = 0.05f;

        RandomSource rand = level.getRandom();

        double cx = center.getX() + 0.5;
        double cz = center.getZ() + 0.5;

        BlockPos groundAtCenter = level.getHeightmapPos(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlockPos.containing(cx, center.getY(), cz)
        );
        double cy = groundAtCenter.getY() + 0.25;

        float outerRadius = (float) radius;

        // Scale the "center hole" with the domain size so it expands naturally with upgrades
        float innerRadius = Math.max(1.5f, outerRadius * 0.20f);

        if (outerRadius <= innerRadius) return;

        // Also scale vertical spread a little with size (helps “bigger domain” feeling)
        float yMin = 0.15f;
        float yMax = (float) Math.min(3.0, 1.10f + (outerRadius / 20.0f)); // gentle growth

        for (int i = 0; i < count; i++) {
            double u = rand.nextDouble();
            float r = (float) Math.sqrt(
                    innerRadius * innerRadius + u * (outerRadius * outerRadius - innerRadius * innerRadius)
            );

            float initialAngle = (float) (rand.nextDouble() * (Math.PI * 2.0));
            float yOffset = yMin + rand.nextFloat() * (yMax - yMin);

            var particleType = rand.nextBoolean()
                    ? RileyModParticles.ORBITING_PARTICLE.get()
                    : RileyModParticles.ORBITING_PARTICLE_2.get();

            var data = new OrbitingParticleData(
                    particleType,
                    cx, cy, cz,
                    r,
                    angularSpeed,
                    initialAngle,
                    yOffset
            );

            level.sendParticles(data, cx, cy, cz, 1, 0, 0, 0, 0);
        }
    }
}