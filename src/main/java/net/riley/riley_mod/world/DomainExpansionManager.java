package net.riley.riley_mod.world;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.util.AugmentData;
import net.riley.riley_mod.world.domain.DomainRegistry;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class DomainExpansionManager {

    private static final int DURATION_TICKS = 20 * 30;     // base duration
    private static final int COOLDOWN_TICKS = 20 * 45;     // base cooldown
    private static final int TICK_INTERVAL = 5;

    private static final Map<UUID, ConcurrentLinkedDeque<DomainInstance>> ACTIVE = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> COOLDOWN_UNTIL = new ConcurrentHashMap<>();

    public static void activateBatch(ServerPlayer player, Iterable<ResourceLocation> domainIds) {
        if (!(player.level() instanceof ServerLevel level)) return;

        long now = level.getGameTime();
        UUID ownerId = player.getUUID();

        long cooldownUntil = COOLDOWN_UNTIL.getOrDefault(ownerId, 0L);
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

        BlockPos center = player.blockPosition();

        boolean any = false;
        int cooldownMultiplier = 1;

        for (ResourceLocation domainId : domainIds) {
            if (domainId == null) continue;

            int augmentLevel = Math.max(1, AugmentData.getLevel(player, domainId));
            cooldownMultiplier = Math.max(cooldownMultiplier, augmentLevel);

            startInstance(level, player, domainId, augmentLevel, center, now);
            any = true;
        }

        if (!any) return;

        // Shared cooldown = baseCooldown * max(level) among domains in this cast
        COOLDOWN_UNTIL.put(ownerId, now + (long) COOLDOWN_TICKS * cooldownMultiplier);
    }

    private static void startInstance(ServerLevel level, ServerPlayer owner, ResourceLocation domainId, int augmentLevel, BlockPos center, long now) {
        UUID ownerId = owner.getUUID();

        // Per-instance duration = baseDuration * level
        long expiresAt = now + (long) DURATION_TICKS * augmentLevel;

        DomainInstance inst = new DomainInstance(domainId, augmentLevel, center, expiresAt, now);
        ACTIVE.computeIfAbsent(ownerId, k -> new ConcurrentLinkedDeque<>()).add(inst);

        pulse(level, owner, inst);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Iterator<Map.Entry<UUID, ConcurrentLinkedDeque<DomainInstance>>> itOwners = ACTIVE.entrySet().iterator();
        while (itOwners.hasNext()) {
            Map.Entry<UUID, ConcurrentLinkedDeque<DomainInstance>> entry = itOwners.next();
            UUID ownerId = entry.getKey();
            ConcurrentLinkedDeque<DomainInstance> instances = entry.getValue();

            ServerPlayer owner = event.getServer().getPlayerList().getPlayer(ownerId);
            if (owner == null || !(owner.level() instanceof ServerLevel level)) {
                itOwners.remove();
                continue;
            }

            long now = level.getGameTime();

            Iterator<DomainInstance> it = instances.iterator();
            while (it.hasNext()) {
                DomainInstance inst = it.next();

                if (now >= inst.expiresAtTick) {
                    it.remove();
                    continue;
                }

                if (now - inst.lastPulseTick < TICK_INTERVAL) continue;
                inst.lastPulseTick = now;

                pulse(level, owner, inst);
            }

            if (instances.isEmpty()) {
                itOwners.remove();
            }
        }
    }

    private static void pulse(ServerLevel level, ServerPlayer owner, DomainInstance inst) {
        var behavior = DomainRegistry.get(inst.domainId);
        if (behavior == null) return;

        behavior.onPulse(level, owner, inst.center, inst.augmentLevel);
    }

    private static class DomainInstance {
        final ResourceLocation domainId;
        final int augmentLevel;
        final BlockPos center;
        final long expiresAtTick;
        long lastPulseTick;

        private DomainInstance(ResourceLocation domainId, int augmentLevel, BlockPos center, long expiresAtTick, long lastPulseTick) {
            this.domainId = domainId;
            this.augmentLevel = augmentLevel;
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