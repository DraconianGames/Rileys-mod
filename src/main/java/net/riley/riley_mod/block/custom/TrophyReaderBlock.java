package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class TrophyReaderBlock extends Block {
    public TrophyReaderBlock(Properties properties) {
        super(properties);
    }

    public Component getReadTrophy(Level level, BlockPos pos) {
        return CableConnections.getTrophyBeingRead(level, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getItemInHand(hand).isEmpty()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            player.displayClientMessage(
                    Component.literal("Reading: ").append(getReadTrophy(level, pos)),
                    true
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}