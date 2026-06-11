package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;

import java.util.Optional;

public final class CableConnections {
    private CableConnections() {
    }

    public static boolean isCable(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos).is(RileyModBlocks.CABLE.get());
    }

    public static boolean connectsCable(BlockGetter level, BlockPos neighborPos, Direction directionFromCable) {
        BlockState neighborState = level.getBlockState(neighborPos);
        return neighborState.is(RileyModBlocks.CABLE.get()) || isConnectableDevice(level, neighborPos, directionFromCable);
    }

    public static boolean isConnectableDevice(BlockGetter level, BlockPos devicePos, Direction directionFromCable) {
        BlockState deviceState = level.getBlockState(devicePos);

        if (deviceState.is(RileyModBlocks.MACHINE_CORE_PORT.get())) {
            return directionFromCable.getAxis().isHorizontal();
        }

        if (deviceState.is(RileyModBlocks.AUGMENTATION_STATION.get())) {
            return directionFromCable == Direction.UP;
        }


        if (deviceState.is(RileyModBlocks.ENCHANTER.get())) {
            return directionFromCable == Direction.UP;
        }

        if (deviceState.is(RileyModBlocks.TROPHY_READER.get())) {
            return directionFromCable != Direction.DOWN;
        }

        return false;
    }

    public static boolean isSpecialScreen(BlockState state) {
        return state.is(RileyModBlocks.ENCHANTER.get())
                || state.is(RileyModBlocks.AUGMENTATION_STATION.get());
    }

    public static Component getSpecialScreenName(BlockState state) {


        if (state.is(RileyModBlocks.ENCHANTER.get())) {
            return Component.literal("Enchanter");
        }

        if (state.is(RileyModBlocks.AUGMENTATION_STATION.get())) {
            return Component.literal("Augmentation Station");
        }

        return Component.literal("Unknown Screen");
    }

    public static boolean isTrophy(BlockState state) {
        return state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get());
    }

    public static Component getTrophyName(BlockState state) {
        if (state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get())) {
            return Component.literal("Whale Hunter Trophy");
        }

        return Component.literal("Unknown Trophy");
    }


    public static Optional<ResourceLocation> getTrophyId(BlockState state) {
        if (state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter_trophy"));
        }

        return Optional.empty();
    }

    public static Optional<ResourceLocation> getTrophyIdBeingRead(BlockGetter level, BlockPos trophyReaderPos) {
        BlockPos trophyPos = trophyReaderPos.above();
        BlockState trophyState = level.getBlockState(trophyPos);

        return getTrophyId(trophyState);
    }

    public static Component getTrophyBeingRead(BlockGetter level, BlockPos trophyReaderPos) {
        BlockPos trophyPos = trophyReaderPos.above();
        BlockState trophyState = level.getBlockState(trophyPos);

        if (isTrophy(trophyState)) {
            return getTrophyName(trophyState);
        }

        return Component.literal("None");
    }
}