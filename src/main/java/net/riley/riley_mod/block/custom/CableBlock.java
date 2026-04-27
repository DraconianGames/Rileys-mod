package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.block.RileyModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CableBlock extends Block {
    public static final BooleanProperty NORTH_C = BooleanProperty.create("north_c");
    public static final BooleanProperty NORTH_P = BooleanProperty.create("north_p");
    public static final BooleanProperty SOUTH_C = BooleanProperty.create("south_c");
    public static final BooleanProperty SOUTH_P = BooleanProperty.create("south_p");
    public static final BooleanProperty EAST_C = BooleanProperty.create("east_c");
    public static final BooleanProperty EAST_P = BooleanProperty.create("east_p");
    public static final BooleanProperty WEST_C = BooleanProperty.create("west_c");
    public static final BooleanProperty WEST_P = BooleanProperty.create("west_p");
    public static final BooleanProperty UP_C = BooleanProperty.create("up_c");
    public static final BooleanProperty UP_P = BooleanProperty.create("up_p");
    public static final BooleanProperty DOWN_C = BooleanProperty.create("down_c");
    public static final BooleanProperty DOWN_P = BooleanProperty.create("down_p");

    private static final VoxelShape SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

    public CableBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH_C, false)
                .setValue(NORTH_P, false)
                .setValue(SOUTH_C, false)
                .setValue(SOUTH_P, false)
                .setValue(EAST_C, false)
                .setValue(EAST_P, false)
                .setValue(WEST_C, false)
                .setValue(WEST_P, false)
                .setValue(UP_C, false)
                .setValue(UP_P, false)
                .setValue(DOWN_C, false)
                .setValue(DOWN_P, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getConnectionState(this.defaultBlockState(), pContext.getLevel(), pContext.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState,
                                  LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return getConnectionState(pState, pLevel, pCurrentPos);
    }

    private BlockState getConnectionState(BlockState state, BlockGetter level, BlockPos pos) {
        return state
                .setValue(NORTH_P, isPort(level, pos.north(), Direction.NORTH))
                .setValue(NORTH_C, connectsCable(level, pos.north(), Direction.NORTH))
                .setValue(SOUTH_P, isPort(level, pos.south(), Direction.SOUTH))
                .setValue(SOUTH_C, connectsCable(level, pos.south(), Direction.SOUTH))
                .setValue(EAST_P, isPort(level, pos.east(), Direction.EAST))
                .setValue(EAST_C, connectsCable(level, pos.east(), Direction.EAST))
                .setValue(WEST_P, isPort(level, pos.west(), Direction.WEST))
                .setValue(WEST_C, connectsCable(level, pos.west(), Direction.WEST))
                .setValue(UP_P, isPort(level, pos.above(), Direction.UP))
                .setValue(UP_C, connectsCable(level, pos.above(), Direction.UP))
                .setValue(DOWN_P, isPort(level, pos.below(), Direction.DOWN))
                .setValue(DOWN_C, connectsCable(level, pos.below(), Direction.DOWN));
    }

    private boolean connectsCable(BlockGetter level, BlockPos pos, Direction direction) {
        BlockState neighborState = level.getBlockState(pos);
        return neighborState.is(this) || isPort(level, pos, direction);
    }

    private boolean isPort(BlockGetter level, BlockPos pos, Direction direction) {
        BlockState neighborState = level.getBlockState(pos);
//neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get()) For all attachment points
        return direction == Direction.EAST && neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get())
                ||direction == Direction.WEST && neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get())
                ||direction == Direction.SOUTH && neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get())
                ||direction == Direction.NORTH && neighborState.is(RileyModBlocks.MACHINE_CORE_PORT.get())
                || direction == Direction.UP && neighborState.is(RileyModBlocks.AUGMENTATION_STATION.get())
                || direction == Direction.UP && neighborState.is(RileyModBlocks.MORPH_STATION.get())
                || direction == Direction.UP && neighborState.is(RileyModBlocks.ENCHANTER.get())
                || neighborState.is(RileyModBlocks.TROPHY_READER.get());//does all sides
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(
                NORTH_C, NORTH_P,
                SOUTH_C, SOUTH_P,
                EAST_C, EAST_P,
                WEST_C, WEST_P,
                UP_C, UP_P,
                DOWN_C, DOWN_P
        );
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}