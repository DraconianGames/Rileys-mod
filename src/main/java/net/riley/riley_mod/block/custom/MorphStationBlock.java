package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.menu.AugmentationStationMenu;
import net.riley.riley_mod.menu.MorphStationMenu;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MorphStationBlock extends Block {
    // 3 blocks wide (48 pixels), 1.5 blocks tall (24 pixels), 1 block deep (16 pixels)
    // Values are (minX, minY, minZ, maxX, maxY, maxZ) in pixels (0-16 is one block)
    private static final VoxelShape SHAPE = Shapes.box(0, 0.0, 0, 1.0, 2, 1.0);

    public MorphStationBlock(Properties pProperties) {
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(
                    serverPlayer,
                    new SimpleMenuProvider(
                            (containerId, inv, p) -> new MorphStationMenu(containerId, inv, pos),
                            Component.literal("Morph Station")
                    ),
                    buf -> buf.writeBlockPos(pos)
            );
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockState filler = RileyModBlocks.MORPH_STATION_FILLER.get().defaultBlockState();
            // Fill 3x3 area, 2 blocks high (for the 1.5 height)
            for (int x = 0; x <= 0; x++) {
                for (int z = 0; z <= 0; z++) {
                    for (int y = 0; y <= 1; y++) {
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
            for (int x = 0; x <= 0; x++) {
                for (int z = 0; z <= 0; z++) {
                    for (int y = 0; y <= 1; y++) {
                        BlockPos targetPos = pPos.offset(x, y, z);
                        if (!targetPos.equals(pPos) && pLevel.getBlockState(targetPos).is(RileyModBlocks.MORPH_STATION_FILLER.get())) {
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
        for (int x = 0; x <= 0; x++) {
            for (int z = 0; z <= 0; z++) {
                for (int y = 0; y <= 1; y++) {
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