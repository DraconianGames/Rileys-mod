package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class ShutOff extends HorizontalDirectionalBlock {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty INPUT_POWERED = BooleanProperty.create("input_powered");
    public static final IntegerProperty DELAY = IntegerProperty.create("delay", 1, 4);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 2, 16);


    public ShutOff(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(INPUT_POWERED, false)
                .setValue(DELAY, 1));
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection());
    }

    private boolean hasInputSignal(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        Direction inputDirection = facing.getOpposite();
        BlockPos inputPos = pos.relative(inputDirection);
        BlockState inputState = level.getBlockState(inputPos);

        if (inputState.getBlock() instanceof RedStoneWireBlock) {
            return inputState.getValue(RedStoneWireBlock.POWER) > 0;
        }

        return level.getSignal(inputPos, facing) > 0 || level.getDirectSignal(inputPos, facing) > 0;
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, net.minecraft.world.level.block.Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide()) {
            return;
        }

        boolean hasSignal = hasInputSignal(level, pos, state);
        boolean wasInputPowered = state.getValue(INPUT_POWERED);
        boolean isPowered = state.getValue(POWERED);

        if (hasSignal && !wasInputPowered) {
            level.setBlock(pos, state
                    .setValue(POWERED, true)
                    .setValue(INPUT_POWERED, true), 3);

            updateOutputNeighbors(level, pos, state);
        } else if (!hasSignal && wasInputPowered) {
            BlockState newState = state.setValue(INPUT_POWERED, false);

            level.setBlock(pos, newState, 3);

            if (isPowered) {
                level.scheduleTick(pos, this, state.getValue(DELAY) * 2);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!hasInputSignal(level, pos, state) && state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, false), 3);
            updateOutputNeighbors(level, pos, state);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        int currentDelay = state.getValue(DELAY);
        int nextDelay = currentDelay == 4 ? 1 : currentDelay + 1;

        level.setBlock(pos, state.setValue(DELAY, nextDelay), 3);

        return InteractionResult.CONSUME;
    }

    private void updateOutputNeighbors(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        BlockPos outputPos = pos.relative(facing.getOpposite());

        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(outputPos, this);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        if (direction == null) {
            return false;
        }

        Direction facing = state.getValue(FACING);
        return direction == facing || direction == facing.getOpposite();
    }

    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        Direction outputDirection = state.getValue(FACING);
        return state.getValue(POWERED) && direction == outputDirection.getOpposite() ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        Direction outputDirection = state.getValue(FACING);
        return state.getValue(POWERED) && direction == outputDirection.getOpposite() ? 15 : 0;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(POWERED)) {
            Direction direction = pState.getValue(FACING);
            double d0 = (double)pPos.getX() + 0.5D + (pRandom.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)pPos.getY() + 0.4D + (pRandom.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)pPos.getZ() + 0.5D + (pRandom.nextDouble() - 0.5D) * 0.2D;
            float f = -5.0F;
            if (pRandom.nextBoolean()) {
                f = (float)(pState.getValue(DELAY) * 2 - 1);
            }

            f /= 16.0F;
            double d3 = (double)(f * (float)direction.getStepX());
            double d4 = (double)(f * (float)direction.getStepZ());
            pLevel.addParticle(DustParticleOptions.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FACING, POWERED, INPUT_POWERED, DELAY);
    }
}