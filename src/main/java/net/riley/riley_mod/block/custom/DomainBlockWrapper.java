package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DomainBlockWrapper {
    private DomainBlockWrapper() {
    }

    public static BuildPlan createBuildPlanForDomains(ServerLevel level, BlockPos center, Map<ResourceLocation, Integer> domainRadii) {
        Map<Block, Integer> blockRadii = new LinkedHashMap<>();
        int maxRadius = 0;

        if (domainRadii != null) {
            for (Map.Entry<ResourceLocation, Integer> entry : domainRadii.entrySet()) {
                Block block = visualBlockForDomain(entry.getKey());
                int radius = Math.max(1, entry.getValue());

                blockRadii.merge(block, radius, Math::max);
                maxRadius = Math.max(maxRadius, radius);
            }
        }

        if (blockRadii.isEmpty()) {
            blockRadii.put(RileyModBlocks.DOMAIN_BLOCK.get(), 1);
            maxRadius = 1;
        }

        Set<BlockPos> placedPositions = new HashSet<>();
        placeInvisibleDomainWall(level, center, maxRadius, placedPositions);

        List<BuildStep> steps = createVisualBuildSteps(level, center, maxRadius, blockRadii);

        return new BuildPlan(placedPositions, steps);
    }

    private static List<BuildStep> createVisualBuildSteps(
            ServerLevel level,
            BlockPos center,
            int maxRadius,
            Map<Block, Integer> blockRadii
    ) {
        List<BuildStep> steps = new ArrayList<>();
        int maxRadiusSqr = maxRadius * maxRadius;

        RandomSource random = level.getRandom();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -maxRadius; x <= maxRadius; x++) {
            for (int y = -maxRadius; y <= maxRadius; y++) {
                for (int z = -maxRadius; z <= maxRadius; z++) {
                    int horizontalDistanceSqr = x * x + z * z;
                    int distanceSqr = horizontalDistanceSqr + y * y;

                    if (distanceSqr > maxRadiusSqr) {
                        continue;
                    }

                    mutablePos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    BlockPos pos = mutablePos.immutable();

                    if (!canPlaceOrReplaceDomainBlock(level, pos)) {
                        continue;
                    }

                    List<Block> candidates = new ArrayList<>();

                    for (Map.Entry<Block, Integer> entry : blockRadii.entrySet()) {
                        int domainRadius = entry.getValue();
                        int domainRadiusSqr = domainRadius * domainRadius;

                        if (distanceSqr <= domainRadiusSqr) {
                            candidates.add(entry.getKey());
                        }
                    }

                    if (candidates.isEmpty()) {
                        continue;
                    }

                    Block chosenBlock = candidates.get(random.nextInt(candidates.size()));
                    BlockState wrappingState = DomainBlock.createWrappingState(level, pos, chosenBlock);

                    if (wrappingState == null) {
                        continue;
                    }

                    steps.add(new BuildStep(pos, wrappingState, horizontalDistanceSqr, y));
                }
            }
        }

        steps.sort(Comparator
                .comparingInt(BuildStep::relativeY)
                .thenComparingInt(BuildStep::horizontalDistanceSqr));

        return steps;
    }

    private static Set<BlockPos> wrapSphere(ServerLevel level, BlockPos center, int maxRadius, Map<Block, Integer> blockRadii) {
        Set<BlockPos> placedPositions = new HashSet<>();
        int maxRadiusSqr = maxRadius * maxRadius;

        placeInvisibleDomainWall(level, center, maxRadius, placedPositions);

        RandomSource random = level.getRandom();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -maxRadius; x <= maxRadius; x++) {
            for (int y = -maxRadius; y <= maxRadius; y++) {
                for (int z = -maxRadius; z <= maxRadius; z++) {
                    int distanceSqr = x * x + y * y + z * z;

                    if (distanceSqr > maxRadiusSqr) {
                        continue;
                    }

                    mutablePos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    BlockPos pos = mutablePos.immutable();

                    if (!canPlaceDomainBlock(level, pos)) {
                        continue;
                    }

                    List<Block> candidates = new ArrayList<>();

                    for (Map.Entry<Block, Integer> entry : blockRadii.entrySet()) {
                        int domainRadius = entry.getValue();
                        int domainRadiusSqr = domainRadius * domainRadius;

                        if (distanceSqr <= domainRadiusSqr) {
                            candidates.add(entry.getKey());
                        }
                    }

                    if (candidates.isEmpty()) {
                        continue;
                    }

                    Block chosenBlock = candidates.get(random.nextInt(candidates.size()));
                    BlockState wrappingState = DomainBlock.createWrappingState(level, pos, chosenBlock);
                    if (wrappingState == null) {
                        continue;
                    }

                    level.setBlock(pos, wrappingState, 3);
                    placedPositions.add(pos);
                }
            }
        }

        return placedPositions;
    }

    private static void placeInvisibleDomainWall(ServerLevel level, BlockPos center, int radius, Set<BlockPos> placedPositions) {
        int innerRadiusSqr = radius * radius;
        int outerRadius = radius + 1;
        int outerRadiusSqr = outerRadius * outerRadius;

        BlockState wallState = RileyModBlocks.INVISIBLE_DOMAIN_WALL.get().defaultBlockState();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -outerRadius; x <= outerRadius; x++) {
            for (int y = -outerRadius; y <= outerRadius; y++) {
                for (int z = -outerRadius; z <= outerRadius; z++) {
                    int distanceSqr = x * x + y * y + z * z;

                    if (distanceSqr <= innerRadiusSqr || distanceSqr > outerRadiusSqr) {
                        continue;
                    }

                    mutablePos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    BlockPos pos = mutablePos.immutable();

                    if (!canPlaceDomainBlock(level, pos)) {
                        continue;
                    }

                    level.setBlock(pos, wallState, 3);
                    placedPositions.add(pos);
                }
            }
        }
    }

    public static void removeWrappedBlocks(ServerLevel level, Set<BlockPos> placedPositions) {
        for (BlockPos pos : placedPositions) {
            BlockState state = level.getBlockState(pos);

            if (state.getBlock() instanceof DomainBlock || state.is(RileyModBlocks.INVISIBLE_DOMAIN_WALL.get())) {
                level.removeBlock(pos, false);
            }
        }
    }

    public static int placeBuildSteps(ServerLevel level, BuildPlan buildPlan, int startIndex, int maxBlocks) {
        int placed = 0;

        while (placed < maxBlocks && startIndex + placed < buildPlan.steps().size()) {
            BuildStep step = buildPlan.steps().get(startIndex + placed);

            if (canPlaceOrReplaceDomainBlock(level, step.pos())) {
                level.setBlock(step.pos(), step.state(), 3);
                buildPlan.placedPositions().add(step.pos());
            }

            placed++;
        }

        return placed;
    }

    public static void refreshVisualBlocksForDomains(
            ServerLevel level,
            BlockPos center,
            Map<ResourceLocation, Integer> domainRadii,
            Set<BlockPos> trackedPositions
    ) {
        BuildPlan buildPlan = createBuildPlanForDomains(level, center, domainRadii);

        placeBuildSteps(level, buildPlan, 0, buildPlan.steps().size());
        trackedPositions.addAll(buildPlan.placedPositions());
    }

    private static boolean canPlaceDomainBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        return state.isAir() || state.canBeReplaced();
    }

    private static boolean canPlaceOrReplaceDomainBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        return state.isAir()
                || state.canBeReplaced()
                || state.getBlock() instanceof DomainBlock;
    }

    private static Block visualBlockForDomain(ResourceLocation domainId) {
        if (domainId == null) {
            return RileyModBlocks.DOMAIN_BLOCK.get();
        }

        if (domainId.equals(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion"))) {
            return RileyModBlocks.BLIZZARD_DOMAIN_BLOCK.get();
        }

        if (domainId.equals(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion"))) {
            return RileyModBlocks.HURICAN_DOMAIN_BLOCK.get();
        }

        return RileyModBlocks.DOMAIN_BLOCK.get();
    }

    public record BuildPlan(
            Set<BlockPos> placedPositions,
            List<BuildStep> steps
    ) {}

    public record BuildStep(
            BlockPos pos,
            BlockState state,
            int horizontalDistanceSqr,
            int relativeY
    ) {}

    public record WrappedDomainBlocks(
            Set<BlockPos> positions,
            Map<BlockPos, ResourceLocation> domainByPos
    ) {}
}