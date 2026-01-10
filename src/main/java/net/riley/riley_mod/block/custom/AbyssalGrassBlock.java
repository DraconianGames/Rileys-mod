package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.block.RileyModBlocks;

public class AbyssalGrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
    public AbyssalGrassBlock(Properties properties) {
        super(properties);
    }

    // This handles the spreading logic
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            // If covered/dark, turn back into Abyssal Dirt
            level.setBlockAndUpdate(pos, RileyModBlocks.ABYSSAL_DIRT.get().defaultBlockState());
        } else {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    // Check if the target block is Abyssal Dirt and can become grass
                    if (level.getBlockState(blockpos).is(RileyModBlocks.ABYSSAL_DIRT.get()) && canPropagate(state, level, blockpos)) {
                        level.setBlockAndUpdate(blockpos, this.defaultBlockState());
                    }
                }
            }
        }
    }

    private static boolean canBeGrass(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = level.getBlockState(blockpos);
        return blockstate.getLightBlock(level, blockpos) < level.getMaxLightLevel();
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        return canBeGrass(state, level, pos) && !level.getFluidState(pos.above()).isSource();
    }

    // Bonemeal logic (optional: set to false if you don't want bonemeal to work)
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        // You can add logic here to spawn abyssal flowers/tall grass
        //Logic will look like Below
      //  level.setBlock(pos.above(), MyPlants.ABYSS_FLOWER.get().defaultBlockState(), 3
    }
}