package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.block.RileyModBlocks;

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