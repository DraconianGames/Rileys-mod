package net.riley.riley_mod.block.custom;

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
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Forward of vanilla RedStoneWireBlock with minimal deltas:
 *  - blue particle/color table
 *  - waterlogging support
 *  - schedule water ticks on shape updates
 *  - override animateTick so particles use blue COLORS
 *
 * Note: redstone logic/propagation remains in RedStoneWireBlock.
 */
public class BlueStoneWireBlock extends RedStoneWireBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // reuse the same directional properties from vanilla RedStoneWireBlock via inherited static fields
    // but we need the shapes and shapes cache for getShape override (optional - we forward to parent getShape)
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

    // Blue gradient colors for particles and map color (power 0..15)
    private static final Vec3[] COLORS = Util.make(new Vec3[16], (arr) -> {
        for (int i = 0; i <= 15; ++i) {
            float f = (float)i / 15.0F;
            // bluish ramp: low power -> dark blue, high power -> bright cyan-blue
            double r = 0.03 + 0.02 * f;      // almost no red
            double g = 0.06 + 0.35 * f;      // green increases for cyan tint at high power
            double b = 0.4 + 0.6 * f;        // strong blue component
            arr[i] = new Vec3(r, g, b);
        }
    });

    private static final float PARTICLE_DENSITY = 0.2F;

    private final BlockState crossState;
    private boolean shouldSignal = true;

    public BlueStoneWireBlock(BlockBehaviour.Properties properties) {
        super(properties);
        // add WATERLOGGED to default state
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

        for (BlockState bs : this.getStateDefinition().getPossibleStates()) {
            if (bs.getValue(POWER) == 0) {
                SHAPES_CACHE.put(bs, calculateShape(bs));
            }
        }
    }

    // reuse vanilla shape calculation
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean water = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        BlockState base = super.getStateForPlacement(context);
        // base may return null in some contexts, but vanilla returns a valid state -- guard just in case
        if (base == null) base = this.crossState;
        return base.setValue(WATERLOGGED, Boolean.valueOf(water));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // call parent builder to add the directional + POWER properties, then add WATERLOGGED
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        BlockState updated = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        if (updated.getValue(WATERLOGGED)) {
            // ensure water tick scheduled so water state behaves correctly
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return updated;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    // Use our blue COLORS for particles / map color
    public static int getColorForPower(int power) {
        Vec3 vec3 = COLORS[power];
        return Mth.color((float)vec3.x(), (float)vec3.y(), (float)vec3.z());
    }

    private void spawnParticlesAlongLine(Level level, RandomSource rand, BlockPos pos, Vec3 vec, Direction xDir, Direction zDir, float min, float max) {
        float f = max - min;
        if (!(rand.nextFloat() >= PARTICLE_DENSITY * f)) {
            float f1 = 0.4375F;
            float f2 = min + f * rand.nextFloat();
            double d0 = 0.5D + (double)(0.4375F * (float)xDir.getStepX()) + (double)(f2 * (float)zDir.getStepX());
            double d1 = 0.5D + (double)(0.4375F * (float)xDir.getStepY()) + (double)(f2 * (float)zDir.getStepY());
            double d2 = 0.5D + (double)(0.4375F * (float)xDir.getStepZ()) + (double)(f2 * (float)zDir.getStepZ());
            level.addParticle(new DustParticleOptions(vec.toVector3f(), 1.0F), (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, 0.0D, 0.0D, 0.0D);
        }
    }

    // override animateTick so we use the blue COLORS array instead of vanilla red
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

    // Optionally, keep a shouldConnectTo helper so you can call it from other places (note: vanilla static calls won't dispatch to this)
    protected static boolean shouldConnectTo(BlockState state) {
        return shouldConnectTo(state, (Direction)null);
    }

    protected static boolean shouldConnectTo(BlockState state, @Nullable Direction dir) {
        // connect to vanilla redstone wire and our bluestone
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
}