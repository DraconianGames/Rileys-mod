package net.riley.riley_mod.worldgen.portal;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.ITeleporter;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.RileyModFallowPortalBlock;

import java.util.function.Function;

public class RileyModFallowTeleporter implements ITeleporter {
    private final BlockPos thisPos;
    public static boolean insideDimension = true;

    private static final int FALLOW_GROUND_Y = 0;
    private static final int FALLOW_PORTAL_ORIGIN_Y = FALLOW_GROUND_Y + 1; // so frame bottom (y-1) sits at y=64


    public RileyModFallowTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);

        BlockPos destinationPos = thisPos;

        if (insideDimension) {
            // Force the portal to be built on the Fallow "ground plane"
            destinationPos = new BlockPos(thisPos.getX(), FALLOW_PORTAL_ORIGIN_Y, thisPos.getZ());

            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(
                    destinationPos.below(5).west(5).north(5),
                    destinationPos.above(5).east(5).south(5)
            )) {
                if (destinationWorld.getBlockState(checkPos).is(RileyModBlocks.FALLOW_PORTAL.get())) {
                    doSetBlock = false;
                    destinationPos = checkPos; // Found an existing portal, use its position
                    break;
                }
            }
            if (doSetBlock) {
                buildPortal(destinationWorld, destinationPos);
            }
        } else {
            // Logic for returning TO the Overworld
            destinationPos = thisPos;
            for (BlockPos checkPos : BlockPos.betweenClosed(thisPos.below(10).west(10).north(10), thisPos.above(10).east(10).south(10))) {
                if (destinationWorld.getBlockState(checkPos).is(RileyModBlocks.FALLOW_PORTAL.get())) {
                    destinationPos = checkPos;
                    break;
                }
            }
        }

        // Determine axis to offset the player correctly
        BlockState portalState = destinationWorld.getBlockState(destinationPos);
        Direction.Axis axis = portalState.hasProperty(RileyModFallowPortalBlock.AXIS) ? portalState.getValue(RileyModFallowPortalBlock.AXIS) : Direction.Axis.X;

        double destX = destinationPos.getX() + 0.5D;
        double destY = destinationPos.getY();
        double destZ = destinationPos.getZ() + 0.5D;

        // We no longer add 1.5D to X or Z.
        // The +0.5D above already centers them perfectly inside the block.

        entity.teleportTo(destX, destY, destZ);
        entity.setPortalCooldown();
        entity.getPersistentData().putBoolean("RileyModWaitingToLeavePortal", true);

        return entity;
    }

    private void buildPortal(ServerLevel world, BlockPos pos) {
        BlockState frame = RileyModBlocks.FALLOW_PORTAL_FRAME.get().defaultBlockState();
        BlockState portal = RileyModBlocks.FALLOW_PORTAL.get().defaultBlockState().setValue(RileyModFallowPortalBlock.AXIS, Direction.Axis.X);

        // 1. Clear a 5x5x5 area of air around the portal so the player isn't stuck in a wall
        for (BlockPos airPos : BlockPos.betweenClosed(pos.offset(-2, 0, -2), pos.offset(2, 4, 2))) {
            world.setBlock(airPos, Blocks.AIR.defaultBlockState(), 3);
        }

        // 2. Build the frame and portal
        for (int x = -1; x < 3; x++) {
            for (int y = -1; y < 4; y++) {
                BlockPos p = pos.offset(x, y, 0);
                if (x == -1 || x == 2 || y == -1 || y == 3) {
                    world.setBlock(p, frame, 3);
                } else {
                    world.setBlock(p, portal, 3);
                }
            }
        }
    }
}