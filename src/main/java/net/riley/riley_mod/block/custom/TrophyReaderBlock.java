package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.block.RileyModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrophyReaderBlock extends Block {
    // 3 blocks wide (48 pixels), 1.5 blocks tall (24 pixels), 1 block deep (16 pixels)
    // Values are (minX, minY, minZ, maxX, maxY, maxZ) in pixels (0-16 is one block)
    private static final VoxelShape SHAPE = Shapes.box(-1.0, 0.0, -1.0, 2.0, 1.0, 2.0);

    public TrophyReaderBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockState filler = RileyModBlocks.TROPHY_READER_FILLER.get().defaultBlockState();//TODO make filler blocks for this and stations
            // Fill 3x3 area, 2 blocks high (for the 1.5 height)
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = 0; y == 0; y++) {
                        BlockPos targetPos = pPos.offset(x, y, z);
                        // Don't replace the actual trophy block itself!
                        if (!targetPos.equals(pPos)) {
                            pLevel.setBlock(targetPos, filler, 3);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            // Remove all filler blocks in the 3x3x2 area
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = 0; y <= 1; y++) {
                        BlockPos targetPos = pPos.offset(x, y, z);
                        if (!targetPos.equals(pPos) && pLevel.getBlockState(targetPos).is(RileyModBlocks.TROPHY_READER_FILLER.get())) {
                            pLevel.removeBlock(targetPos, false);
                        }
                    }
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();

        // Check if the spaces for the "wings" of the trophy are clear
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = 0; y <= 0; y++) {
                    BlockPos targetPos = pos.offset(x, y, z);
                    if (!targetPos.equals(pos) && !level.getBlockState(targetPos).canBeReplaced(pContext)) {
                        return null; // Area is blocked!
                    }
                }
            }
        }

        return this.defaultBlockState();
    }
}