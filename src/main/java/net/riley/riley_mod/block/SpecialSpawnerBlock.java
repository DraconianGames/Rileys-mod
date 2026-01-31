package net.riley.riley_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.block.entity.SpecialSpawnerBlockEntity;
import net.riley.riley_mod.block.entity.registry.RileyModBlockEntities;
import net.riley.riley_mod.client.SpecialSpawnerClient;

import javax.annotation.Nullable;

public class SpecialSpawnerBlock extends BaseEntityBlock {

    public SpecialSpawnerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SpecialSpawnerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,
                                                                  BlockState state,
                                                                  BlockEntityType<T> type) {
        if (level.isClientSide) return null;

        return createTickerHelper(type, RileyModBlockEntities.SPECIAL_SPAWNER_BE.get(),
                SpecialSpawnerBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state,
                                 Level level,
                                 BlockPos pos,
                                 Player player,
                                 InteractionHand hand,
                                 BlockHitResult hit) {

        if (!level.isClientSide && player.isCreative()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof SpecialSpawnerBlockEntity spawnerBe && player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, spawnerBe, buf -> buf.writeBlockPos(pos));
            }
            return InteractionResult.CONSUME;
        }

        return InteractionResult.CONSUME;
    }
}