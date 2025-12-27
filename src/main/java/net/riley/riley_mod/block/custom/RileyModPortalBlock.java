package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.riley.riley_mod.worldgen.dimension.RileyModDimensions;
import net.riley.riley_mod.worldgen.portal.RileyModTeleporter;

public class RileyModPortalBlock extends Block {
    public RileyModPortalBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.canChangeDimensions()) {
            handleAbyssPortal(pPlayer, pPos);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handleAbyssPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == RileyModDimensions.ABYSSDIM_LEVEL_KEY ?
                    Level.OVERWORLD : RileyModDimensions.ABYSSDIM_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if(resourcekey == RileyModDimensions.ABYSSDIM_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new RileyModTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new RileyModTeleporter(pPos, false));
                }
            }
        }
    }
}