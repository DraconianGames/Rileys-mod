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

@Mod.EventBusSubscriber(modid = MODID)
public class DomainExpansionManager {

    private static final int DURATION_TICKS = 20 * 30;     // base duration
    private static final int COOLDOWN_TICKS = 20 * 45;     // base cooldown
    private static final int TICK_INTERVAL = 5;
    private static final int BUILD_BLOCKS_PER_TICK = 30;  //speed of build

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
        Map<ResourceLocation, Integer> domainHeights = new LinkedHashMap<>();
        Map<ResourceLocation, Long> domainExpiresAt = new LinkedHashMap<>();

        int cooldownMultiplier = 1;
        long latestExpiresAt = now;

        for (ResourceLocation domainId : equippedDomainIds) {
            int augmentLevel = Math.max(1, AugmentData.getLevel(player, domainId));
            int radius = radiusForLevel(augmentLevel);
            int height = augmentLevel;
            long expiresAt = now + (long) DURATION_TICKS * augmentLevel;

            domainRadii.put(domainId, radius);
            domainHeights.put(domainId, height);
            domainExpiresAt.put(domainId, expiresAt);

            cooldownMultiplier = Math.max(cooldownMultiplier, augmentLevel);
            latestExpiresAt = Math.max(latestExpiresAt, expiresAt);
        }

        DomainBlockWrapper.BuildPlan buildPlan = DomainBlockWrapper.createBuildPlanForDomains(level, center, domainRadii, domainHeights);

        boolean visualCleanupOwnerAssigned = false;

        for (ResourceLocation domainId : equippedDomainIds) {
            int augmentLevel = Math.max(1, AugmentData.getLevel(player, domainId));
            long durationTicks = (long) DURATION_TICKS * augmentLevel;

            boolean ownsWrappedBlocks = !visualCleanupOwnerAssigned;
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
                    durationTicks,
                    ownsWrappedBlocks ? buildPlan : null
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
            long durationTicks,
            DomainBlockWrapper.BuildPlan buildPlan
    ) {
        UUID ownerId = owner.getUUID();

        DomainInstance inst = new DomainInstance(domainId, augmentLevel, center, now, durationTicks, buildPlan);
        ACTIVE.computeIfAbsent(ownerId, k -> new ConcurrentLinkedDeque<>()).add(inst);
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

            boolean sharedBuildInProgress = instances.stream()
                    .anyMatch(inst -> inst.buildPlan != null && !inst.finishedBuilding);

            Iterator<DomainInstance> it = instances.iterator();
            while (it.hasNext()) {
                DomainInstance inst = it.next();

                if (!inst.finishedBuilding) {
                    if (inst.buildPlan == null && sharedBuildInProgress) {
                        continue;
                    }

                    tickBuild(level, owner, inst, now);
                    continue;
                }

                if (now >= inst.expiresAtTick) {
                    Set<BlockPos> expiredWrappedBlocks = inst.wrappedBlocks;
                    boolean expiredOwnedVisuals = !expiredWrappedBlocks.isEmpty();

                    it.remove();

                    if (expiredOwnedVisuals) {
                        if (instances.isEmpty()) {
                            DomainBlockWrapper.removeWrappedBlocks(level, expiredWrappedBlocks);
                        } else {
                            refreshRemainingDomainVisuals(level, inst.center, instances, expiredWrappedBlocks);
                        }
                    }

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

    private static BlockPos centerOf(DomainInstance inst) {
        return inst.center;
    }
    private static void refreshRemainingDomainVisuals(
            ServerLevel level,
            BlockPos center,
            ConcurrentLinkedDeque<DomainInstance> remainingInstances,
            Set<BlockPos> trackedPositions
    ) {
        Map<ResourceLocation, Integer> remainingDomainRadii = new LinkedHashMap<>();
        Map<ResourceLocation, Integer> remainingDomainHeights = new LinkedHashMap<>();

        for (DomainInstance remaining : remainingInstances) {
            if (!remaining.finishedBuilding) {
                continue;
            }

            remainingDomainRadii.put(
                    remaining.domainId,
                    radiusForLevel(remaining.augmentLevel)
            );
            remainingDomainHeights.put(
                    remaining.domainId,
                    remaining.augmentLevel
            );
        }

        if (remainingDomainRadii.isEmpty()) {
            DomainBlockWrapper.removeWrappedBlocks(level, trackedPositions);
            trackedPositions.clear();
            return;
        }

        DomainBlockWrapper.refreshVisualBlocksForDomains(
                level,
                center,
                remainingDomainRadii,
                remainingDomainHeights,
                trackedPositions
        );

        DomainInstance newVisualOwner = remainingInstances.peekFirst();

        if (newVisualOwner != null) {
            newVisualOwner.wrappedBlocks.addAll(trackedPositions);
        }

        trackedPositions.clear();
    }

    private static void tickBuild(ServerLevel level, ServerPlayer owner, DomainInstance inst, long now) {
        if (inst.buildPlan != null) {
            int placed = DomainBlockWrapper.placeBuildSteps(level, inst.buildPlan, inst.buildIndex, BUILD_BLOCKS_PER_TICK);
            inst.buildIndex += placed;

            if (inst.buildIndex < inst.buildPlan.steps().size()) {
                return;
            }

            inst.wrappedBlocks.addAll(inst.buildPlan.placedPositions());
        }

        inst.finishedBuilding = true;
        inst.startedAtTick = now;
        inst.expiresAtTick = now + inst.durationTicks;
        inst.lastPulseTick = now;

        pulse(level, owner, inst);
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
        final long durationTicks;
        final Set<BlockPos> wrappedBlocks;
        final DomainBlockWrapper.BuildPlan buildPlan;

        long startedAtTick;
        long expiresAtTick;
        long lastPulseTick;
        int buildIndex;
        boolean finishedBuilding;

        private DomainInstance(
                ResourceLocation domainId,
                int augmentLevel,
                BlockPos center,
                long now,
                long durationTicks,
                DomainBlockWrapper.BuildPlan buildPlan
        ) {
            this.domainId = domainId;
            this.augmentLevel = augmentLevel;
            this.center = center;
            this.startedAtTick = now;
            this.expiresAtTick = Long.MAX_VALUE;
            this.durationTicks = durationTicks;
            this.lastPulseTick = now;
            this.buildPlan = buildPlan;
            this.wrappedBlocks = ConcurrentHashMap.newKeySet();

            this.finishedBuilding = false;
        }
    }
}

/*
Radius: RADIUS
Duration: DURATION_TICKS
How often it applies freeze + spawns particles: TICK_INTERVAL
How “sticky” the freeze is: FREEZE_REFRESH_TICKS (I set it to 12 ticks; it will be refreshed every pulse)
 */