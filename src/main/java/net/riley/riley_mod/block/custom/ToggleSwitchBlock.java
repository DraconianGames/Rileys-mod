package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ToggleSwitchBlock extends Block {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    public ToggleSwitchBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ACTIVATED, false)
                .setValue(POWERED, false));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide()) {
            return;
        }

        boolean hasSignal = level.hasNeighborSignal(pos);
        boolean wasPowered = state.getValue(POWERED);

        if (hasSignal && !wasPowered) {
            boolean isActivated = state.getValue(ACTIVATED);

            level.setBlock(pos, state
                    .setValue(ACTIVATED, !isActivated)
                    .setValue(POWERED, true), 3);

            level.updateNeighbourForOutputSignal(pos, this);
        } else if (!hasSignal && wasPowered) {
            level.setBlock(pos, state.setValue(POWERED, false), 3);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return state.getValue(ACTIVATED) ? 15 : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED, POWERED);
    }
}