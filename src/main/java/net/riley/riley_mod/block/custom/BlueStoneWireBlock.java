package net.riley.riley_mod.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class BlueStoneWireBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST
    ));

    private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);

    private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D),
            Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D),
            Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D),
            Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)
    ));

    private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)),
            Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)),
            Direction.EAST, Shapes.or(SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)),
            Direction.WEST, Shapes.or(SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))
    ));

    private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();

    private static final Vec3[] COLORS = Util.make(new Vec3[16], colors -> {
        for (int i = 0; i <= 15; ++i) {
            float strength = (float) i / 15.0F;
            double red = 0.03 + 0.02 * strength;
            double green = 0.06 + 0.35 * strength;
            double blue = 0.4 + 0.6 * strength;
            colors[i] = new Vec3(red, green, blue);
        }
    });

    private static final float PARTICLE_DENSITY = 0.2F;

    private final BlockState crossState;
    private boolean shouldSignal = true;

    public BlueStoneWireBlock(BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, RedstoneSide.NONE)
                .setValue(EAST, RedstoneSide.NONE)
                .setValue(SOUTH, RedstoneSide.NONE)
                .setValue(WEST, RedstoneSide.NONE)
                .setValue(POWER, 0)
                .setValue(WATERLOGGED, false));

        this.crossState = this.defaultBlockState()
                .setValue(NORTH, RedstoneSide.SIDE)
                .setValue(EAST, RedstoneSide.SIDE)
                .setValue(SOUTH, RedstoneSide.SIDE)
                .setValue(WEST, RedstoneSide.SIDE);

        for (BlockState state : this.getStateDefinition().getPossibleStates()) {
            if (state.getValue(POWER) == 0) {
                SHAPES_CACHE.put(state, this.calculateShape(state));
            }
        }
    }

    private VoxelShape calculateShape(BlockState state) {
        VoxelShape shape = SHAPE_DOT;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(direction));

            if (side == RedstoneSide.SIDE) {
                shape = Shapes.or(shape, SHAPES_FLOOR.get(direction));
            } else if (side == RedstoneSide.UP) {
                shape = Shapes.or(shape, SHAPES_UP.get(direction));
            }
        }

        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES_CACHE.get(state.setValue(POWER, 0));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

        return this.getConnectionState(context.getLevel(), this.crossState, context.getClickedPos())
                .setValue(WATERLOGGED, waterlogged);
    }

    private BlockState getConnectionState(BlockGetter level, BlockState state, BlockPos pos) {
        boolean wasDot = isDot(state);

        state = this.getMissingConnections(level, this.defaultBlockState()
                .setValue(POWER, state.getValue(POWER))
                .setValue(WATERLOGGED, state.getValue(WATERLOGGED)), pos);

        if (wasDot && isDot(state)) {
            return state;
        }

        boolean northConnected = state.getValue(NORTH).isConnected();
        boolean southConnected = state.getValue(SOUTH).isConnected();
        boolean eastConnected = state.getValue(EAST).isConnected();
        boolean westConnected = state.getValue(WEST).isConnected();

        boolean noNorthSouth = !northConnected && !southConnected;
        boolean noEastWest = !eastConnected && !westConnected;

        if (!westConnected && noNorthSouth && !isVanillaRedstoneWire(level, pos.west())) {
            state = state.setValue(WEST, RedstoneSide.SIDE);
        }

        if (!eastConnected && noNorthSouth && !isVanillaRedstoneWire(level, pos.east())) {
            state = state.setValue(EAST, RedstoneSide.SIDE);
        }

        if (!northConnected && noEastWest && !isVanillaRedstoneWire(level, pos.north())) {
            state = state.setValue(NORTH, RedstoneSide.SIDE);
        }

        if (!southConnected && noEastWest && !isVanillaRedstoneWire(level, pos.south())) {
            state = state.setValue(SOUTH, RedstoneSide.SIDE);
        }

        return state;
    }

    private BlockState getMissingConnections(BlockGetter level, BlockState state, BlockPos pos) {
        boolean nonNormalCubeAbove = !level.getBlockState(pos.above()).isRedstoneConductor(level, pos);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!state.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()) {
                RedstoneSide side = this.getConnectingSide(level, pos, direction, nonNormalCubeAbove);
                state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), side);
            }
        }

        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState updated;

        if (facing == Direction.DOWN) {
            updated = state;
        } else if (facing == Direction.UP) {
            updated = this.getConnectionState(level, state, currentPos);
        } else {
            RedstoneSide side = this.getConnectingSide(level, currentPos, facing);
            boolean connectionSame = side.isConnected() == state.getValue(PROPERTY_BY_DIRECTION.get(facing)).isConnected();

            if (connectionSame && !isCross(state)) {
                updated = state.setValue(PROPERTY_BY_DIRECTION.get(facing), side);
            } else {
                updated = this.getConnectionState(level, this.crossState
                        .setValue(POWER, state.getValue(POWER))
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                        .setValue(PROPERTY_BY_DIRECTION.get(facing), side), currentPos);
            }
        }

        updated = this.removeVanillaRedstoneConnections(level, currentPos, updated);

        if (updated.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return updated;
    }

    private BlockState removeVanillaRedstoneConnections(BlockGetter level, BlockPos pos, BlockState state) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (isVanillaRedstoneWire(level, pos.relative(direction))) {
                state = state.setValue(PROPERTY_BY_DIRECTION.get(direction), RedstoneSide.NONE);
            }
        }

        return state;
    }

    private static boolean isCross(BlockState state) {
        return state.getValue(NORTH).isConnected()
                && state.getValue(SOUTH).isConnected()
                && state.getValue(EAST).isConnected()
                && state.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState state) {
        return !state.getValue(NORTH).isConnected()
                && !state.getValue(SOUTH).isConnected()
                && !state.getValue(EAST).isConnected()
                && !state.getValue(WEST).isConnected();
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, int flags, int recursionLeft) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(direction));

            if (side != RedstoneSide.NONE && !level.getBlockState(mutablePos.setWithOffset(pos, direction)).is(this)) {
                mutablePos.move(Direction.DOWN);
                BlockState belowState = level.getBlockState(mutablePos);

                if (belowState.is(this)) {
                    BlockPos neighborPos = mutablePos.relative(direction.getOpposite());
                    level.neighborShapeChanged(direction.getOpposite(), level.getBlockState(neighborPos), mutablePos, neighborPos, flags, recursionLeft);
                }

                mutablePos.setWithOffset(pos, direction).move(Direction.UP);
                BlockState aboveState = level.getBlockState(mutablePos);

                if (aboveState.is(this)) {
                    BlockPos neighborPos = mutablePos.relative(direction.getOpposite());
                    level.neighborShapeChanged(direction.getOpposite(), level.getBlockState(neighborPos), mutablePos, neighborPos, flags, recursionLeft);
                }
            }
        }
    }

    private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction face) {
        return this.getConnectingSide(level, pos, face, !level.getBlockState(pos.above()).isRedstoneConductor(level, pos));
    }

    private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction direction, boolean nonNormalCubeAbove) {
        BlockPos neighborPos = pos.relative(direction);
        BlockState neighborState = level.getBlockState(neighborPos);

        if (neighborState.is(Blocks.REDSTONE_WIRE)) {
            return RedstoneSide.NONE;
        }

        if (nonNormalCubeAbove) {
            boolean canClimbNeighbor = neighborState.getBlock() instanceof TrapDoorBlock || this.canSurviveOn(level, neighborPos, neighborState);
            BlockPos aboveNeighborPos = neighborPos.above();
            BlockState aboveNeighborState = level.getBlockState(aboveNeighborPos);

            if (canClimbNeighbor
                    && !aboveNeighborState.is(Blocks.REDSTONE_WIRE)
                    && aboveNeighborState.canRedstoneConnectTo(level, aboveNeighborPos, null)) {
                if (neighborState.isFaceSturdy(level, neighborPos, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }

                return RedstoneSide.SIDE;
            }
        }

        if (neighborState.canRedstoneConnectTo(level, neighborPos, direction)) {
            return RedstoneSide.SIDE;
        } else if (neighborState.isRedstoneConductor(level, neighborPos)) {
            return RedstoneSide.NONE;
        } else {
            BlockPos belowPos = neighborPos.below();
            BlockState belowState = level.getBlockState(belowPos);

            if (belowState.is(Blocks.REDSTONE_WIRE)) {
                return RedstoneSide.NONE;
            }

            return belowState.canRedstoneConnectTo(level, belowPos, null) ? RedstoneSide.SIDE : RedstoneSide.NONE;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return this.canSurviveOn(level, belowPos, belowState);
    }

    private boolean canSurviveOn(BlockGetter level, BlockPos pos, BlockState state) {
        return state.isFaceSturdy(level, pos, Direction.UP) || state.is(Blocks.HOPPER);
    }

    private void updatePowerStrength(Level level, BlockPos pos, BlockState state) {
        int targetPower = this.calculateTargetStrength(level, pos);

        if (state.getValue(POWER) != targetPower) {
            if (level.getBlockState(pos) == state) {
                level.setBlock(pos, state.setValue(POWER, targetPower), 2);
            }

            Set<BlockPos> positionsToUpdate = Sets.newHashSet();
            positionsToUpdate.add(pos);

            for (Direction direction : Direction.values()) {
                positionsToUpdate.add(pos.relative(direction));
            }

            for (BlockPos updatePos : positionsToUpdate) {
                level.updateNeighborsAt(updatePos, this);
            }
        }
    }

    private int calculateTargetStrength(Level level, BlockPos pos) {
        this.shouldSignal = false;
        int directSignal = this.getBestNeighborSignalIgnoringVanillaRedstone(level, pos);
        this.shouldSignal = true;

        int wireSignal = 0;

        if (directSignal < 15) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (!neighborState.is(Blocks.REDSTONE_WIRE)) {
                    wireSignal = Math.max(wireSignal, this.getWireSignal(neighborState));
                }

                BlockPos abovePos = pos.above();

                if (neighborState.isRedstoneConductor(level, neighborPos) && !level.getBlockState(abovePos).isRedstoneConductor(level, abovePos)) {
                    BlockState aboveNeighborState = level.getBlockState(neighborPos.above());

                    if (!aboveNeighborState.is(Blocks.REDSTONE_WIRE)) {
                        wireSignal = Math.max(wireSignal, this.getWireSignal(aboveNeighborState));
                    }
                } else if (!neighborState.isRedstoneConductor(level, neighborPos)) {
                    BlockState belowNeighborState = level.getBlockState(neighborPos.below());

                    if (!belowNeighborState.is(Blocks.REDSTONE_WIRE)) {
                        wireSignal = Math.max(wireSignal, this.getWireSignal(belowNeighborState));
                    }
                }
            }
        }

        return Math.max(directSignal, wireSignal - 1);
    }

    private int getBestNeighborSignalIgnoringVanillaRedstone(Level level, BlockPos pos) {
        int maxSignal = 0;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(Blocks.REDSTONE_WIRE)) {
                continue;
            }

            int signal = level.getSignal(neighborPos, direction);
            maxSignal = Math.max(maxSignal, signal);

            if (maxSignal >= 15) {
                return 15;
            }
        }

        return maxSignal;
    }

    private int getWireSignal(BlockState state) {
        return state.getBlock() instanceof BlueStoneWireBlock ? state.getValue(POWER) : 0;
    }

    private void checkCornerChangeAt(Level level, BlockPos pos) {
        if (level.getBlockState(pos).is(this)) {
            level.updateNeighborsAt(pos, this);

            for (Direction direction : Direction.values()) {
                level.updateNeighborsAt(pos.relative(direction), this);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide) {
            this.updatePowerStrength(level, pos, state);

            for (Direction direction : Direction.Plane.VERTICAL) {
                level.updateNeighborsAt(pos.relative(direction), this);
            }

            this.updateNeighborsOfNeighboringWires(level, pos);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);

            if (!level.isClientSide) {
                for (Direction direction : Direction.values()) {
                    level.updateNeighborsAt(pos.relative(direction), this);
                }

                this.updatePowerStrength(level, pos, state);
                this.updateNeighborsOfNeighboringWires(level, pos);
            }
        }
    }

    private void updateNeighborsOfNeighboringWires(Level level, BlockPos pos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            this.checkCornerChangeAt(level, pos.relative(direction));
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);

            if (level.getBlockState(neighborPos).isRedstoneConductor(level, neighborPos)) {
                this.checkCornerChangeAt(level, neighborPos.above());
            } else {
                this.checkCornerChangeAt(level, neighborPos.below());
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            if (state.canSurvive(level, pos)) {
                this.updatePowerStrength(level, pos, state);
            } else {
                dropResources(state, level, pos);
                level.removeBlock(pos, false);
            }
        }
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return !this.shouldSignal ? 0 : state.getSignal(level, pos, direction);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (!this.shouldSignal || direction == Direction.DOWN) {
            return 0;
        }

        BlockState blockReceivingSignal = level.getBlockState(pos.relative(direction.getOpposite()));

        if (blockReceivingSignal.is(Blocks.REDSTONE_WIRE)) {
            return 0;
        }

        int power = state.getValue(POWER);

        if (power == 0) {
            return 0;
        }

        if (direction == Direction.UP) {
            return power;
        }

        return state.getValue(PROPERTY_BY_DIRECTION.get(direction.getOpposite())).isConnected() ? power : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return this.shouldSignal;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        if (direction == null) {
            return false;
        }

        BlockState blockAskingToConnect = level.getBlockState(pos.relative(direction.getOpposite()));

        return !blockAskingToConnect.is(Blocks.REDSTONE_WIRE);
    }

    protected static boolean shouldConnectTo(BlockState state) {
        return shouldConnectTo(state, null);
    }

    protected static boolean shouldConnectTo(BlockState state, @Nullable Direction direction) {
        if (state.is(Blocks.REDSTONE_WIRE)) {
            return false;
        }

        if (state.getBlock() instanceof BlueStoneWireBlock) {
            return true;
        } else if (state.is(Blocks.REPEATER)) {
            Direction facing = state.getValue(RepeaterBlock.FACING);
            return facing == direction || facing.getOpposite() == direction;
        } else if (state.is(Blocks.OBSERVER)) {
            return direction == state.getValue(ObserverBlock.FACING);
        } else {
            return state.isSignalSource() && direction != null;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public static int getColorForPower(int power) {
        Vec3 color = COLORS[power];
        return Mth.color((float) color.x(), (float) color.y(), (float) color.z());
    }

    private void spawnParticlesAlongLine(Level level, RandomSource random, BlockPos pos, Vec3 particleColor, Direction xDirection, Direction zDirection, float min, float max) {
        float range = max - min;

        if (!(random.nextFloat() >= PARTICLE_DENSITY * range)) {
            float offset = 0.4375F;
            float lineOffset = min + range * random.nextFloat();

            double x = 0.5D + (double) (offset * (float) xDirection.getStepX()) + (double) (lineOffset * (float) zDirection.getStepX());
            double y = 0.5D + (double) (offset * (float) xDirection.getStepY()) + (double) (lineOffset * (float) zDirection.getStepY());
            double z = 0.5D + (double) (offset * (float) xDirection.getStepZ()) + (double) (lineOffset * (float) zDirection.getStepZ());

            level.addParticle(new DustParticleOptions(particleColor.toVector3f(), 1.0F),
                    (double) pos.getX() + x,
                    (double) pos.getY() + y,
                    (double) pos.getZ() + z,
                    0.0D,
                    0.0D,
                    0.0D);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int power = state.getValue(POWER);

        if (power != 0) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(direction));

                switch (side) {
                    case UP:
                        this.spawnParticlesAlongLine(level, random, pos, COLORS[power], direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.spawnParticlesAlongLine(level, random, pos, COLORS[power], Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.spawnParticlesAlongLine(level, random, pos, COLORS[power], Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state
                    .setValue(NORTH, state.getValue(SOUTH))
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(SOUTH, state.getValue(NORTH))
                    .setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> state
                    .setValue(NORTH, state.getValue(EAST))
                    .setValue(EAST, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(WEST))
                    .setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90 -> state
                    .setValue(NORTH, state.getValue(WEST))
                    .setValue(EAST, state.getValue(NORTH))
                    .setValue(SOUTH, state.getValue(EAST))
                    .setValue(WEST, state.getValue(SOUTH));
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state
                    .setValue(NORTH, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, POWER, WATERLOGGED);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        }

        if (isCross(state) || isDot(state)) {
            BlockState newState = isCross(state) ? this.defaultBlockState() : this.crossState;

            newState = newState
                    .setValue(POWER, state.getValue(POWER))
                    .setValue(WATERLOGGED, state.getValue(WATERLOGGED));

            newState = this.getConnectionState(level, newState, pos);

            if (newState != state) {
                level.setBlock(pos, newState, 3);
                this.updatesOnShapeChange(level, pos, state, newState);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private void updatesOnShapeChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);

            if (oldState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected() != newState.getValue(PROPERTY_BY_DIRECTION.get(direction)).isConnected()
                    && level.getBlockState(neighborPos).isRedstoneConductor(level, neighborPos)) {
                level.updateNeighborsAtExceptFromFacing(neighborPos, newState.getBlock(), direction.getOpposite());
            }
        }
    }

    private static boolean isVanillaRedstoneWire(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos).is(Blocks.REDSTONE_WIRE);
    }
}