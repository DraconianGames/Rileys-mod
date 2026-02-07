package net.riley.riley_mod.world;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.particle.RileyModParticles;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class DomainExpansionManager {

    private static final int DURATION_TICKS = 20 * 30;     // 10 seconds
    private static final int COOLDOWN_TICKS = 20 * 45;     // 45 seconds cooldown after activation
    private static final int TICK_INTERVAL = 5;            // do work every 5 ticks
    private static final double RADIUS = 10.0;             // blocks
    private static final int PARTICLES_PER_PULSE = 20;

    private static final int FREEZE_REFRESH_TICKS = 12;    // re-apply often (keeps them frozen)
    private static final int FREEZE_AMPLIFIER = 0;


    private static final Map<UUID, DomainInstance> ACTIVE = new ConcurrentHashMap<>();
    // New: per-player cooldown timestamps (in game ticks)
    private static final Map<UUID, Long> COOLDOWN_UNTIL = new ConcurrentHashMap<>();

    public static void activate(ServerPlayer player) {
        if (!(player.level() instanceof ServerLevel level)) return;

        long now = level.getGameTime();
        UUID id = player.getUUID();

        // If already active, show remaining duration
        DomainInstance existing = ACTIVE.get(id);
        if (existing != null) {
            long remainingTicks = Math.max(0, existing.expiresAtTick - now);
            int seconds = Mth.ceil(remainingTicks / 20.0f);
            player.displayClientMessage(
                    Component.literal("Domain Expansion already active (" + seconds + "s left).")
                            .withStyle(ChatFormatting.AQUA),
                    true
            );
            return;
        }

        // Cooldown check
        long cooldownUntil = COOLDOWN_UNTIL.getOrDefault(id, 0L);
        if (now < cooldownUntil) {
            long remainingTicks = cooldownUntil - now;
            int seconds = Mth.ceil(remainingTicks / 20.0f);
            player.displayClientMessage(
                    Component.literal("Domain Expansion cooldown: " + seconds + "s")
                            .withStyle(ChatFormatting.RED),
                    true
            );
            return;
        }

        // Start domain + set cooldown
        BlockPos center = player.blockPosition();

        ACTIVE.put(id, new DomainInstance(center, now + DURATION_TICKS, now));
        COOLDOWN_UNTIL.put(id, now + COOLDOWN_TICKS);

        spawnParticles(level, center, 120);
        applyFreeze(level, player, center);
    }
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Iterator<Map.Entry<UUID, DomainInstance>> it = ACTIVE.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, DomainInstance> entry = it.next();
            UUID playerId = entry.getKey();
            DomainInstance inst = entry.getValue();

            ServerPlayer player = event.getServer().getPlayerList().getPlayer(playerId);
            if (player == null || !(player.level() instanceof ServerLevel level)) {
                it.remove();
                continue;
            }

            long now = level.getGameTime();
            if (now >= inst.expiresAtTick) {
                it.remove();
                continue;
            }

            if (now - inst.lastPulseTick < TICK_INTERVAL) continue;
            inst.lastPulseTick = now;

            spawnParticles(level, inst.center, PARTICLES_PER_PULSE);
            applyFreeze(level, player, inst.center);
        }
    }

    private static void applyFreeze(ServerLevel level, ServerPlayer owner, BlockPos center) {
        AABB box = new AABB(center).inflate(RADIUS, 4.0, RADIUS);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box)) {
            if (target == owner) continue;
            if (!target.isAlive()) continue;

            // Don't freeze the owner's tames
            if (target instanceof TamableAnimal tamable) {
                if (tamable.isTame() && tamable.isOwnedBy(owner)) continue;
            }

            // Optional: if you want to avoid freezing other players too, uncomment:
            // if (target instanceof ServerPlayer) continue;

            target.addEffect(new MobEffectInstance(
                    RileyModEffects.FREEZE.get(),
                    FREEZE_REFRESH_TICKS,
                    FREEZE_AMPLIFIER,
                    false,   // ambient
                    true,    // showParticles (status effect particles)
                    true     // showIcon
            ));
        }
    }

    private static void spawnParticles(ServerLevel level, BlockPos center, int count) {
        RandomSource rand = level.getRandom();

        for (int i = 0; i < count; i++) {
            double angle = rand.nextDouble() * (Math.PI * 2.0);
            double dist = Math.sqrt(rand.nextDouble()) * RADIUS;

            double x = center.getX() + 0.5 + Math.cos(angle) * dist + (rand.nextDouble() - 0.5) * 0.2;
            double z = center.getZ() + 0.5 + Math.sin(angle) * dist + (rand.nextDouble() - 0.5) * 0.2;

            BlockPos ground = level.getHeightmapPos(
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.containing(x, center.getY(), z)
            );

            double y = ground.getY() + 0.2 + rand.nextDouble() * 1.0;

            boolean variant = rand.nextBoolean();
            var type = variant ? RileyModParticles.HURICANE_PARTICLE.get() : RileyModParticles.HURICANE_PARTICLE_2.get();

            level.sendParticles(type, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private static class DomainInstance {
        final BlockPos center;
        final long expiresAtTick;
        long lastPulseTick;

        private DomainInstance(BlockPos center, long expiresAtTick, long lastPulseTick) {
            this.center = center;
            this.expiresAtTick = expiresAtTick;
            this.lastPulseTick = lastPulseTick;
        }
    }
}
/*
Radius: RADIUS
Duration: DURATION_TICKS
How often it applies freeze + spawns particles: TICK_INTERVAL
How “sticky” the freeze is: FREEZE_REFRESH_TICKS (I set it to 12 ticks; it will be refreshed every pulse)
 */