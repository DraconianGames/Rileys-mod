package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.block.RileyModBlocks;

import java.util.*;

public class MachineCoreMultiblock {
    private static final String[][] PATTERN = {
            {
                    "pap",
                    "aaa",
                    "pap"
            },
            {
                    "asa",
                    "scs",
                    "asa"
            },
            {
                    "aaa",
                    "aaa",
                    "aaa"
            }
    };

    public static void updateAround(Level level, BlockPos changedPos) {
        if (level.isClientSide) {
            return;
        }

        for (int centerXOffset = -1; centerXOffset <= 1; centerXOffset++) {
            for (int centerYOffset = -1; centerYOffset <= 1; centerYOffset++) {
                for (int centerZOffset = -1; centerZOffset <= 1; centerZOffset++) {
                    BlockPos possibleCenter = changedPos.offset(centerXOffset, centerYOffset, centerZOffset);

                    if (!isCenterBlock(level, possibleCenter)) {
                        continue;
                    }

                    unform(level, possibleCenter);

                    if (isValid(level, possibleCenter)) {
                        form(level, possibleCenter);
                    }
                }
            }
        }
    }

    public static void unformAround(Level level, BlockPos changedPos) {
        if (level.isClientSide) {
            return;
        }

        for (int centerXOffset = -1; centerXOffset <= 1; centerXOffset++) {
            for (int centerYOffset = -1; centerYOffset <= 1; centerYOffset++) {
                for (int centerZOffset = -1; centerZOffset <= 1; centerZOffset++) {
                    BlockPos possibleCenter = changedPos.offset(centerXOffset, centerYOffset, centerZOffset);

                    if (!isCenterBlock(level, possibleCenter)) {
                        continue;
                    }

                    unform(level, possibleCenter);
                }
            }
        }
    }

    private static boolean isCenterBlock(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(RileyModBlocks.MACHINE_CORE_CENTER.get());
    }

    private static boolean isValid(Level level, BlockPos centerPos) {
        for (int y = -1; y <= 1; y++) {
            String[] layer = PATTERN[y + 1];

            for (int z = -1; z <= 1; z++) {
                String row = layer[z + 1];

                for (int x = -1; x <= 1; x++) {
                    char expected = row.charAt(x + 1);
                    BlockPos checkPos = centerPos.offset(x, y, z);
                    BlockState state = level.getBlockState(checkPos);

                    if (!matchesExpectedBlock(state, expected)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static void form(Level level, BlockPos centerPos) {
        setFormed(level, centerPos, true);
    }

    private static void unform(Level level, BlockPos centerPos) {
        setFormed(level, centerPos, false);
    }

    private static void setFormed(Level level, BlockPos centerPos, boolean formed) {
        for (int y = -1; y <= 1; y++) {
            String[] layer = PATTERN[y + 1];

            for (int z = -1; z <= 1; z++) {
                String row = layer[z + 1];

                for (int x = -1; x <= 1; x++) {
                    char expected = row.charAt(x + 1);
                    BlockPos partPos = centerPos.offset(x, y, z);
                    BlockState state = level.getBlockState(partPos);

                    if (!matchesExpectedBlock(state, expected)) {
                        continue;
                    }

                    if (!state.hasProperty(MachineCorePartBlock.FORMED)) {
                        continue;
                    }

                    if (state.getValue(MachineCorePartBlock.FORMED) == formed) {
                        continue;
                    }

                    level.setBlock(partPos, state.setValue(MachineCorePartBlock.FORMED, formed), Block.UPDATE_ALL);
                }
            }
        }
    }

    public static BlockPos findCenter(Level level, BlockPos partPos) {
        for (int centerXOffset = -1; centerXOffset <= 1; centerXOffset++) {
            for (int centerYOffset = -1; centerYOffset <= 1; centerYOffset++) {
                for (int centerZOffset = -1; centerZOffset <= 1; centerZOffset++) {
                    BlockPos possibleCenter = partPos.offset(centerXOffset, centerYOffset, centerZOffset);

                    if (isCenterBlock(level, possibleCenter) && isValid(level, possibleCenter)) {
                        return possibleCenter;
                    }
                }
            }
        }

        return null;
    }

    public static List<Component> getConnectedSummary(Level level, BlockPos screenPos) {
        List<Component> lines = new ArrayList<>();

        BlockPos centerPos = findCenter(level, screenPos);

        if (centerPos == null) {
            lines.add(Component.literal("Machine Core: Not formed"));
            return lines;
        }

        Set<BlockPos> cables = new HashSet<>();
        Set<BlockPos> trophyReaders = new HashSet<>();
        Set<BlockPos> morphStations = new HashSet<>();
        Set<BlockPos> enchanters = new HashSet<>();
        Set<BlockPos> augmentationStations = new HashSet<>();

        for (BlockPos portPos : getPortPositions(centerPos)) {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = portPos.relative(direction);

                if (CableConnections.isCable(level, neighborPos)
                        && CableConnections.isConnectableDevice(level, portPos, direction.getOpposite())) {
                    scanCableNetwork(level, neighborPos, cables, trophyReaders, morphStations, enchanters, augmentationStations);
                }
            }
        }

        int specialScreenCount = morphStations.size() + enchanters.size() + augmentationStations.size();

        lines.add(Component.literal("Machine Core: Formed"));
        lines.add(Component.literal("Cables: " + cables.size()));
        lines.add(Component.literal("Special Screens: " + specialScreenCount));
        lines.add(Component.literal(" - Morph Stations: " + morphStations.size()));
        lines.add(Component.literal(" - Enchanters: " + enchanters.size()));
        lines.add(Component.literal(" - Augmentation Stations: " + augmentationStations.size()));
        lines.add(Component.literal("Trophy Readers: " + trophyReaders.size()));

        if (!trophyReaders.isEmpty()) {
            lines.add(Component.literal(""));

            int readerNumber = 1;
            for (BlockPos trophyReaderPos : trophyReaders) {
                Component trophyName = CableConnections.getTrophyBeingRead(level, trophyReaderPos);

                lines.add(Component.literal("Reader " + readerNumber + ": ")
                        .append(trophyName));

                readerNumber++;
            }
        }

        return lines;
    }

    private static List<BlockPos> getPortPositions(BlockPos centerPos) {
        List<BlockPos> ports = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            String[] layer = PATTERN[y + 1];

            for (int z = -1; z <= 1; z++) {
                String row = layer[z + 1];

                for (int x = -1; x <= 1; x++) {
                    char expected = row.charAt(x + 1);

                    if (expected == 'p') {
                        ports.add(centerPos.offset(x, y, z));
                    }
                }
            }
        }

        return ports;
    }

    private static void scanCableNetwork(
            Level level,
            BlockPos startCablePos,
            Set<BlockPos> cables,
            Set<BlockPos> trophyReaders,
            Set<BlockPos> morphStations,
            Set<BlockPos> enchanters,
            Set<BlockPos> augmentationStations
    ) {
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(startCablePos);

        while (!queue.isEmpty()) {
            BlockPos cablePos = queue.remove();

            if (!cables.add(cablePos)) {
                continue;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = cablePos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (CableConnections.isCable(level, neighborPos)) {
                    queue.add(neighborPos);
                    continue;
                }

                if (!CableConnections.isConnectableDevice(level, neighborPos, direction)) {
                    continue;
                }

                if (neighborState.is(RileyModBlocks.TROPHY_READER.get())) {
                    trophyReaders.add(neighborPos);
                    continue;
                }

              

                if (neighborState.is(RileyModBlocks.ENCHANTER.get())) {
                    enchanters.add(neighborPos);
                    continue;
                }

                if (neighborState.is(RileyModBlocks.AUGMENTATION_STATION.get())) {
                    augmentationStations.add(neighborPos);
                }
            }
        }
    }
    public static Set<ResourceLocation> getStoredTrophies(Level level, BlockPos corePartPos) {
        Set<ResourceLocation> storedTrophies = new LinkedHashSet<>();

        BlockPos centerPos = findCenter(level, corePartPos);

        if (centerPos == null) {
            return storedTrophies;
        }

        Set<BlockPos> cables = new HashSet<>();
        Set<BlockPos> trophyReaders = new HashSet<>();
        Set<BlockPos> morphStations = new HashSet<>();
        Set<BlockPos> enchanters = new HashSet<>();
        Set<BlockPos> augmentationStations = new HashSet<>();

        for (BlockPos portPos : getPortPositions(centerPos)) {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = portPos.relative(direction);

                if (CableConnections.isCable(level, neighborPos)
                        && CableConnections.isConnectableDevice(level, portPos, direction.getOpposite())) {
                    scanCableNetwork(level, neighborPos, cables, trophyReaders, morphStations, enchanters, augmentationStations);
                }
            }
        }

        for (BlockPos trophyReaderPos : trophyReaders) {
            Optional<ResourceLocation> trophyId = CableConnections.getTrophyIdBeingRead(level, trophyReaderPos);
            trophyId.ifPresent(storedTrophies::add);
        }

        return storedTrophies;
    }

    public static Set<ResourceLocation> getStoredTrophiesForConnectedDevice(Level level, BlockPos devicePos) {
        Set<ResourceLocation> storedTrophies = new LinkedHashSet<>();
        Set<BlockPos> checkedCables = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = devicePos.relative(direction);

            if (CableConnections.isCable(level, neighborPos)
                    && CableConnections.isConnectableDevice(level, devicePos, direction.getOpposite())) {
                queue.add(neighborPos);
            }
        }

        while (!queue.isEmpty()) {
            BlockPos cablePos = queue.remove();

            if (!checkedCables.add(cablePos)) {
                continue;
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = cablePos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (CableConnections.isCable(level, neighborPos)) {
                    queue.add(neighborPos);
                    continue;
                }

                if (!CableConnections.isConnectableDevice(level, neighborPos, direction)) {
                    continue;
                }

                if (neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get())) {
                    storedTrophies.addAll(getStoredTrophies(level, neighborPos));
                }
            }
        }

        return storedTrophies;
    }


    private static boolean matchesExpectedBlock(BlockState state, char expected) {
        return switch (expected) {
            case 'a' -> state.is(RileyModBlocks.MACHINE_CORE.get());
            case 'p' -> state.is(RileyModBlocks.MACHINE_CORE_PORT.get());
            case 'c' -> state.is(RileyModBlocks.MACHINE_CORE_CENTER.get());
            case 's' -> state.is(RileyModBlocks.MACHINE_CORE_SCREEN.get());
            default -> false;
        };
    }
}