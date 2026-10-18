package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.block.RileyModBlocks;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DomainBlockWrapper {
    private DomainBlockWrapper() {
    }

    public static Set<BlockPos> wrapSphere(ServerLevel level, BlockPos center, int radius) {
        return wrapSphere(level, center, radius, List.of(RileyModBlocks.DOMAIN_BLOCK.get()));
    }

    public static Set<BlockPos> wrapSphere(ServerLevel level, BlockPos center, int radius, List<Block> domainBlocks) {
        Set<BlockPos> placedPositions = new HashSet<>();
        int radiusSqr = radius * radius;

        if (domainBlocks == null || domainBlocks.isEmpty()) {
            domainBlocks = List.of(RileyModBlocks.DOMAIN_BLOCK.get());
        }

        RandomSource random = level.getRandom();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + y * y + z * z > radiusSqr) {
                        continue;
                    }

                    mutablePos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                    BlockPos pos = mutablePos.immutable();

                    if (!canPlaceDomainBlock(level, pos)) {
                        continue;
                    }

                    Block chosenBlock = domainBlocks.get(random.nextInt(domainBlocks.size()));
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

    public static void removeWrappedBlocks(ServerLevel level, Set<BlockPos> placedPositions) {
        for (BlockPos pos : placedPositions) {
            if (level.getBlockState(pos).getBlock() instanceof DomainBlock) {
                level.removeBlock(pos, false);
            }
        }
    }

    private static boolean canPlaceDomainBlock(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        return state.isAir() || state.canBeReplaced();
    }
    public record WrappedDomainBlocks(
            Set<BlockPos> positions,
            Map<BlockPos, ResourceLocation> domainByPos
    ) {}
}