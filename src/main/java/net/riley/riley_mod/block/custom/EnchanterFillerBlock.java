package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.block.RileyModBlocks;

public class EnchanterFillerBlock extends Block {
    public EnchanterFillerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE; // Makes the block invisible
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block(); // Makes it a full solid cube for selection
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block(); // Makes it a full solid cube for walking
    }
    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide) {
            // Scan the 3x3x2 area around this filler to find the main trophy
            for (int x = 0; x <= 0; x++) {
                for (int z = 0; z <= 0; z++) {
                    for (int y = -1; y <= 0; y++) { // Look down/around
                        BlockPos checkPos = pPos.offset(x, y, z);
                        BlockState state = pLevel.getBlockState(checkPos);

                        if (state.is(RileyModBlocks.ENCHANTER.get())) {
                            // If we find the trophy, break it!
                            // The trophy's own onRemove logic will then clean up all other fillers.
                            pLevel.destroyBlock(checkPos, !pPlayer.isCreative());
                            return;
                        }
                    }
                }
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
}