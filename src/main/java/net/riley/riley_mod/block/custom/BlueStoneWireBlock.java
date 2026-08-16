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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;

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
    public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(
            ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST)
    );

    private static final VoxelShape SHAPE_DOT = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D),
            Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D),
            Direction.EAST,  Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D),
            Direction.WEST,  Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)
    ));
    private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.or(SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)),
            Direction.SOUTH, Shapes.or(SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)),
            Direction.EAST,  Shapes.or(SHAPES_FLOOR.get(Direction.EAST),  Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)),
            Direction.WEST,  Shapes.or(SHAPES_FLOOR.get(Direction.WEST),  Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))
    ));

    private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.newHashMap();
    private static final Vec3[] COLORS = Util.make(new Vec3[16], (arr) -> {
        for(int i = 0; i <= 15; ++i) {
            float f = (float)i / 15.0F;
            float f1 = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            float f2 = Mth.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float f3 = Mth.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            arr[i] = new Vec3((double)f1, (double)f2, (double)f3);
        }
    });
    private static final float PARTICLE_DENSITY = 0.2F;

    private final BlockState crossState;
    private boolean shouldSignal = true;

    public BlueStoneWireBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // default: all sides none, power 0, not waterlogged
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, RedstoneSide.NONE)
                .setValue(EAST, RedstoneSide.NONE)
                .setValue(SOUTH, RedstoneSide.NONE)
                .setValue(WEST, RedstoneSide.NONE)
                .setValue(POWER, 0)
                .setValue(WATERLOGGED, false)
        );

        this.crossState = this.defaultBlockState()
                .setValue(NORTH, RedstoneSide.SIDE)
                .setValue(EAST, RedstoneSide.SIDE)
                .setValue(SOUTH, RedstoneSide.SIDE)
                .setValue(WEST, RedstoneSide.SIDE);

        for(BlockState bs : this.getStateDefinition().getPossibleStates()) {
            if (bs.getValue(POWER) == 0) {
                SHAPES_CACHE.put(bs, calculateShape(bs));
            }
        }
    }

    private VoxelShape calculateShape(BlockState state) {
        VoxelShape shape = SHAPE_DOT;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(dir));
            if (side == RedstoneSide.SIDE) {
                shape = Shapes.or(shape, SHAPES_FLOOR.get(dir));
            } else if (side == RedstoneSide.UP) {
                shape = Shapes.or(shape, SHAPES_UP.get(dir));
            }
        }
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPES_CACHE.get(state.setValue(POWER, Integer.valueOf(0)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean water = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        BlockState base = this.getConnectionState(context.getLevel(), this.crossState, context.getClickedPos());
        return base.setValue(WATERLOGGED, Boolean.valueOf(water));
    }

    private BlockState getConnectionState(BlockGetter level, BlockState state, BlockPos pos) {
        boolean wasDot = isDot(state);
        state = this.getMissingConnections(level, this.defaultBlockState().setValue(POWER, state.getValue(POWER)), pos);
        if (wasDot && isDot(state)) {
            return state;
        } else {
            boolean n = state.getValue(NORTH).isConnected();
            boolean s = state.getValue(SOUTH).isConnected();
            boolean e = state.getValue(EAST).isConnected();
            boolean w = state.getValue(WEST).isConnected();
            boolean ns = !n && !s;
            boolean ew = !e && !w;
            if (!w && ns) {
                state = state.setValue(WEST, RedstoneSide.SIDE);
            }
            if (!e && ns) {
                state = state.setValue(EAST, RedstoneSide.SIDE);
            }
            if (!n && ew) {
                state = state.setValue(NORTH, RedstoneSide.SIDE);
            }
            if (!s && ew) {
                state = state.setValue(SOUTH, RedstoneSide.SIDE);
            }
            return state;
        }
    }

    private BlockState getMissingConnections(BlockGetter level, BlockState state, BlockPos pos) {
        boolean flag = !level.getBlockState(pos.above()).isRedstoneConductor(level, pos);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (!state.getValue(PROPERTY_BY_DIRECTION.get(dir)).isConnected()) {
                RedstoneSide side = this.getConnectingSide(level, pos, dir, flag);
                state = state.setValue(PROPERTY_BY_DIRECTION.get(dir), side);
            }
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.DOWN) {
            return state;
        } else if (facing == Direction.UP) {
            return this.getConnectionState(level, state, currentPos);
        } else {
            RedstoneSide side = this.getConnectingSide(level, currentPos, facing);
            if (side.isConnected() == state.getValue(PROPERTY_BY_DIRECTION.get(facing)).isConnected() && !isCross(state)) {
                return state.setValue(PROPERTY_BY_DIRECTION.get(facing), side);
            } else {
                BlockState updated = this.getMissingConnections(level, state, currentPos);
                if (updated.getValue(WATERLOGGED)) {
                    // ensure water tick scheduled
                    level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
                }
                return updated;
            }
        }
    }

    private static boolean isCross(BlockState state) {
        return state.getValue(NORTH).isConnected() && state.getValue(SOUTH).isConnected()
                && state.getValue(EAST).isConnected() && state.getValue(WEST).isConnected();
    }

    private static boolean isDot(BlockState state) {
        return !state.getValue(NORTH).isConnected() && !state.getValue(SOUTH).isConnected()
                && !state.getValue(EAST).isConnected() && !state.getValue(WEST).isConnected();
    }

    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor level, BlockPos pos, int flags, int recursionLeft) {
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(dir));
            if (side != RedstoneSide.NONE && !level.getBlockState(mpos.setWithOffset(pos, dir)).is(this)) {
                mpos.move(Direction.DOWN);
                BlockState bs = level.getBlockState(mpos);
                if (bs.is(this)) {
                    BlockPos rel = mpos.relative(dir.getOpposite());
                    level.neighborShapeChanged(dir.getOpposite(), level.getBlockState(rel), mpos, rel, flags, recursionLeft);
                }
                mpos.setWithOffset(pos, dir).move(Direction.UP);
                BlockState bs1 = level.getBlockState(mpos);
                if (bs1.is(this)) {
                    BlockPos rel1 = mpos.relative(dir.getOpposite());
                    level.neighborShapeChanged(dir.getOpposite(), level.getBlockState(rel1), mpos, rel1, flags, recursionLeft);
                }
            }
        }
    }

    private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction face) {
        return this.getConnectingSide(level, pos, face, !level.getBlockState(pos.above()).isRedstoneConductor(level, pos));
    }

    private RedstoneSide getConnectingSide(BlockGetter level, BlockPos pos, Direction direction, boolean nonNormalCubeAbove) {
        BlockPos other = pos.relative(direction);
        BlockState otherState = level.getBlockState(other);
        if (nonNormalCubeAbove) {
            boolean flag = otherState.getBlock() instanceof TrapDoorBlock || this.canSurviveOn(level, other, otherState);
            if (flag && level.getBlockState(other.above()).canRedstoneConnectTo(level, other.above(), null)) {
                if (otherState.isFaceSturdy(level, other, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }
                return RedstoneSide.SIDE;
            }
        }

        if (otherState.canRedstoneConnectTo(level, other, direction)) {
            return RedstoneSide.SIDE;
        } else if (otherState.isRedstoneConductor(level, other)) {
            return RedstoneSide.NONE;
        } else {
            BlockPos below = other.below();
            return level.getBlockState(below).canRedstoneConnectTo(level, below, null) ? RedstoneSide.SIDE : RedstoneSide.NONE;
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState bs = level.getBlockState(below);
        return this.canSurviveOn(level, below, bs);
    }

    private boolean canSurviveOn(BlockGetter level, BlockPos pos, BlockState state) {
        return state.isFaceSturdy(level, pos, Direction.UP) || state.is(Blocks.HOPPER);
    }

    private void updatePowerStrength(Level level, BlockPos pos, BlockState state) {
        int strength = this.calculateTargetStrength(level, pos);
        if (state.getValue(POWER) != strength) {
            if (level.getBlockState(pos) == state) {
                level.setBlock(pos, state.setValue(POWER, Integer.valueOf(strength)), 2);
            }
            Set<BlockPos> set = Sets.newHashSet();
            set.add(pos);
            for (Direction dir : Direction.values()) {
                set.add(pos.relative(dir));
            }
            for (BlockPos bp : set) {
                level.updateNeighborsAt(bp, this);
            }
        }
    }

    private int calculateTargetStrength(Level level, BlockPos pos) {
        this.shouldSignal = false;
        int best = level.getBestNeighborSignal(pos);
        this.shouldSignal = true;
        int j = 0;
        if (best < 15) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                BlockPos other = pos.relative(dir);
                BlockState otherState = level.getBlockState(other);
                j = Math.max(j, this.getWireSignal(otherState));
                BlockPos above = pos.above();
                if (otherState.isRedstoneConductor(level, other) && !level.getBlockState(above).isRedstoneConductor(level, above)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(other.above())));
                } else if (!otherState.isRedstoneConductor(level, other)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(other.below())));
                }
            }
        }
        return Math.max(best, j - 1);
    }

    private int getWireSignal(BlockState state) {
        return state.is(this) ? state.getValue(POWER) : 0;
    }

    private void checkCornerChangeAt(Level level, BlockPos pos) {
        if (level.getBlockState(pos).is(this)) {
            level.updateNeighborsAt(pos, this);
            for (Direction dir : Direction.values()) {
                level.updateNeighborsAt(pos.relative(dir), this);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock()) && !level.isClientSide) {
            this.updatePowerStrength(level, pos, state);
            for (Direction dir : Direction.Plane.VERTICAL) {
                level.updateNeighborsAt(pos.relative(dir), this);
            }
            this.updateNeighborsOfNeighboringWires(level, pos);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);
            if (!level.isClientSide) {
                for (Direction dir : Direction.values()) {
                    level.updateNeighborsAt(pos.relative(dir), this);
                }
                this.updatePowerStrength(level, pos, state);
                this.updateNeighborsOfNeighboringWires(level, pos);
            }
        }
    }

    private void updateNeighborsOfNeighboringWires(Level level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            this.checkCornerChangeAt(level, pos.relative(dir));
        }

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos p = pos.relative(dir);
            if (level.getBlockState(p).isRedstoneConductor(level, p)) {
                this.checkCornerChangeAt(level, p.above());
            } else {
                this.checkCornerChangeAt(level, p.below());
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
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return !this.shouldSignal ? 0 : state.getSignal(world, pos, side);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        if (this.shouldSignal && side != Direction.DOWN) {
            int i = state.getValue(POWER);
            if (i == 0) return 0;
            return side != Direction.UP && !this.getConnectionState(world, state, pos).getValue(PROPERTY_BY_DIRECTION.get(side.getOpposite())).isConnected() ? 0 : i;
        } else {
            return 0;
        }
    }

    protected static boolean shouldConnectTo(BlockState state) {
        return shouldConnectTo(state, (Direction)null);
    }

    protected static boolean shouldConnectTo(BlockState state, @Nullable Direction dir) {
        // allow vanilla wire OR our custom wire (this class)
        if (state.is(Blocks.REDSTONE_WIRE) || state.getBlock() instanceof BlueStoneWireBlock) {
            return true;
        } else if (state.is(Blocks.REPEATER)) {
            Direction facing = state.getValue(RepeaterBlock.FACING);
            return facing == dir || facing.getOpposite() == dir;
        } else if (state.is(Blocks.OBSERVER)) {
            return dir == state.getValue(ObserverBlock.FACING);
        } else {
            return state.isSignalSource() && dir != null;
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return this.shouldSignal;
    }

    public static int getColorForPower(int power) {
        Vec3 vec3 = COLORS[power];
        return Mth.color((float)vec3.x(), (float)vec3.y(), (float)vec3.z());
    }

    private void spawnParticlesAlongLine(Level level, RandomSource rand, BlockPos pos, Vec3 vec, Direction xDir, Direction zDir, float min, float max) {
        float f = max - min;
        if (!(rand.nextFloat() >= 0.2F * f)) {
            float f1 = 0.4375F;
            float f2 = min + f * rand.nextFloat();
            double d0 = 0.5D + (double)(0.4375F * (float)xDir.getStepX()) + (double)(f2 * (float)zDir.getStepX());
            double d1 = 0.5D + (double)(0.4375F * (float)xDir.getStepY()) + (double)(f2 * (float)zDir.getStepY());
            double d2 = 0.5D + (double)(0.4375F * (float)xDir.getStepZ()) + (double)(f2 * (float)zDir.getStepZ());
            level.addParticle(new DustParticleOptions(vec.toVector3f(), 1.0F), (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        int i = state.getValue(POWER);
        if (i != 0) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                RedstoneSide side = state.getValue(PROPERTY_BY_DIRECTION.get(dir));
                switch (side) {
                    case UP:
                        this.spawnParticlesAlongLine(level, rand, pos, COLORS[i], dir, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.spawnParticlesAlongLine(level, rand, pos, COLORS[i], Direction.DOWN, dir, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.spawnParticlesAlongLine(level, rand, pos, COLORS[i], Direction.DOWN, dir, 0.0F, 0.3F);
                }
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST))
                        .setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH))
                        .setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH))
                        .setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK:
                return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, POWER, WATERLOGGED);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            if (isCross(state) || isDot(state)) {
                BlockState bs = isCross(state) ? this.defaultBlockState() : this.crossState;
                bs = bs.setValue(POWER, state.getValue(POWER));
                bs = this.getConnectionState(level, bs, pos).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
                if (bs != state) {
                    level.setBlock(pos, bs, 3);
                    this.updatesOnShapeChange(level, pos, state, bs);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
    }

    private void updatesOnShapeChange(Level level, BlockPos pos, BlockState oldState, BlockState newState) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos p = pos.relative(dir);
            if (oldState.getValue(PROPERTY_BY_DIRECTION.get(dir)).isConnected() != newState.getValue(PROPERTY_BY_DIRECTION.get(dir)).isConnected()
                    && level.getBlockState(p).isRedstoneConductor(level, p)) {
                level.updateNeighborsAtExceptFromFacing(p, newState.getBlock(), dir.getOpposite());
            }
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}