package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.block.RileyModBlocks;
import org.jetbrains.annotations.Nullable;

public class DomainBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    private static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    private static final VoxelShape UP_SHAPE = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public DomainBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    //todo add block wrapping logic
    public static @Nullable BlockState createWrappingState(BlockGetter level, BlockPos pos) {
        return createWrappingState(level, pos, RileyModBlocks.DOMAIN_BLOCK.get());
    }

    public static @Nullable BlockState createWrappingState(BlockGetter level, BlockPos pos, Block domainBlock) {
        boolean north = shouldWrapNeighbor(level, pos.north());
        boolean south = shouldWrapNeighbor(level, pos.south());
        boolean east = shouldWrapNeighbor(level, pos.east());
        boolean west = shouldWrapNeighbor(level, pos.west());
        boolean up = shouldWrapNeighbor(level, pos.above());
        boolean down = shouldWrapNeighbor(level, pos.below());

        if (!north && !south && !east && !west && !up && !down) {
            return null;
        }

        return domainBlock.defaultBlockState()
                .setValue(NORTH, north)
                .setValue(SOUTH, south)
                .setValue(EAST, east)
                .setValue(WEST, west)
                .setValue(UP, up)
                .setValue(DOWN, down);
    }

    private static boolean shouldWrapNeighbor(BlockGetter level, BlockPos neighborPos) {
        BlockState neighborState = level.getBlockState(neighborPos);

        if (neighborState.isAir()) {
            return false;
        }

        if (neighborState.getBlock() instanceof DomainBlock) {
            return false;
        }

        return neighborState.canOcclude();
    }


    private static BooleanProperty propertyForDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            case UP -> UP;
            case DOWN -> DOWN;
        };
    }

    private static boolean hasAnyFace(BlockState state) {
        return state.getValue(NORTH)
                || state.getValue(SOUTH)
                || state.getValue(EAST)
                || state.getValue(WEST)
                || state.getValue(UP)
                || state.getValue(DOWN);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState wrappingState = createWrappingState(context.getLevel(), context.getClickedPos());

        if (wrappingState != null) {
            return wrappingState;
        }

        return this.defaultBlockState();
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos currentPos,
            BlockPos neighborPos
    ) {
        BlockState updatedState = state.setValue(
                propertyForDirection(direction),
                shouldWrapNeighbor(level, neighborPos)
        );

        if (!hasAnyFace(updatedState)) {
            return Blocks.AIR.defaultBlockState();
        }

        return updatedState;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return makeShape(state);
    }

    private static VoxelShape makeShape(BlockState state) {
        VoxelShape shape = Shapes.empty();

        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, NORTH_SHAPE);
        }

        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, SOUTH_SHAPE);
        }

        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, EAST_SHAPE);
        }

        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, WEST_SHAPE);
        }

        if (state.getValue(UP)) {
            shape = Shapes.or(shape, UP_SHAPE);
        }

        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, DOWN_SHAPE);
        }

        return shape;
    }
}