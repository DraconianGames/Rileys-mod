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
import net.riley.riley_mod.block.custom.DomainBlockWrapper;
import net.riley.riley_mod.util.AugmentData;
import net.riley.riley_mod.world.domain.DomainRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import static net.riley.riley_mod.RileyMod.MODID;
//todo make domain expand out in the circle, then climb the walls
//TODO make deactivated partials remove their corresponding blocks to make room for other domain's blocks
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

        List<ResourceLocation> equippedDomainIds = new ArrayList<>();

        for (ResourceLocation domainId : domainIds) {
            if (domainId != null) {
                equippedDomainIds.add(domainId);
            }
        }

        if (equippedDomainIds.isEmpty()) {
            return;
        }

        BlockPos center = player.blockPosition();

        Map<ResourceLocation, Integer> domainRadii = new LinkedHashMap<>();
        Map<ResourceLocation, Long> domainExpiresAt = new LinkedHashMap<>();

        int cooldownMultiplier = 1;
        long latestExpiresAt = now;

        for (ResourceLocation domainId : equippedDomainIds) {
            int augmentLevel = Math.max(1, AugmentData.getLevel(player, domainId));
            int radius = radiusForLevel(augmentLevel);
            long expiresAt = now + (long) DURATION_TICKS * augmentLevel;

            domainRadii.put(domainId, radius);
            domainExpiresAt.put(domainId, expiresAt);

            cooldownMultiplier = Math.max(cooldownMultiplier, augmentLevel);
            latestExpiresAt = Math.max(latestExpiresAt, expiresAt);
        }

        Set<BlockPos> wrappedBlocks = DomainBlockWrapper.wrapSphereForDomains(level, center, domainRadii);

        boolean visualCleanupOwnerAssigned = false;

        for (ResourceLocation domainId : equippedDomainIds) {
            int augmentLevel = Math.max(1, AugmentData.getLevel(player, domainId));
            long expiresAt = domainExpiresAt.get(domainId);

            boolean ownsWrappedBlocks = !visualCleanupOwnerAssigned && expiresAt == latestExpiresAt;
            if (ownsWrappedBlocks) {
                visualCleanupOwnerAssigned = true;
            }

            startInstance(
                    level,
                    player,
                    domainId,
                    augmentLevel,
                    center,
                    now,
                    expiresAt,
                    ownsWrappedBlocks ? wrappedBlocks : Collections.emptySet()
            );
        }

        // Shared cooldown = baseCooldown * max(level) among domains in this cast
        COOLDOWN_UNTIL.put(ownerId, now + (long) COOLDOWN_TICKS * cooldownMultiplier);
    }

    private static void startInstance(
            ServerLevel level,
            ServerPlayer owner,
            ResourceLocation domainId,
            int augmentLevel,
            BlockPos center,
            long now,
            long expiresAt,
            Set<BlockPos> wrappedBlocks
    ) {
        UUID ownerId = owner.getUUID();

        DomainInstance inst = new DomainInstance(domainId, augmentLevel, center, expiresAt, now, wrappedBlocks);
        ACTIVE.computeIfAbsent(ownerId, k -> new ConcurrentLinkedDeque<>()).add(inst);

        pulse(level, owner, inst);
    }

    private static int radiusForLevel(int augmentLevel) {
        return 12 + (Math.max(1, augmentLevel) * 4);
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
                for (DomainInstance inst : instances) {
                    ServerLevel cleanupLevel = event.getServer().overworld();
                    DomainBlockWrapper.removeWrappedBlocks(cleanupLevel, inst.wrappedBlocks);
                }

                itOwners.remove();
                continue;
            }

            long now = level.getGameTime();

            Iterator<DomainInstance> it = instances.iterator();
            while (it.hasNext()) {
                DomainInstance inst = it.next();

                if (now >= inst.expiresAtTick) {
                    DomainBlockWrapper.removeWrappedBlocks(level, inst.wrappedBlocks);
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
        final Set<BlockPos> wrappedBlocks;
        long lastPulseTick;

        private DomainInstance(
                ResourceLocation domainId,
                int augmentLevel,
                BlockPos center,
                long expiresAtTick,
                long lastPulseTick,
                Set<BlockPos> wrappedBlocks
        ) {
            this.domainId = domainId;
            this.augmentLevel = augmentLevel;
            this.center = center;
            this.expiresAtTick = expiresAtTick;
            this.lastPulseTick = lastPulseTick;
            this.wrappedBlocks = wrappedBlocks;
        }
    }
}
/*
Radius: RADIUS
Duration: DURATION_TICKS
How often it applies freeze + spawns particles: TICK_INTERVAL
How “sticky” the freeze is: FREEZE_REFRESH_TICKS (I set it to 12 ticks; it will be refreshed every pulse)
 */