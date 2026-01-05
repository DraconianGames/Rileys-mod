package net.riley.riley_mod.worldgen.portal;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.ITeleporter;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.RileyModPortalBlock;

import java.util.function.Function;

public class RileyModTeleporter implements ITeleporter {
    private final BlockPos thisPos;
    public static boolean insideDimension = true;

    public RileyModTeleporter(BlockPos pos, boolean insideDim) {
        thisPos = pos;
        insideDimension = insideDim;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld,
                              float yaw, Function<Boolean, Entity> repositionEntity) {
        entity = repositionEntity.apply(false);
        BlockPos destinationPos = thisPos;


        if (insideDimension) {
            boolean doSetBlock = true;
            for (BlockPos checkPos : BlockPos.betweenClosed(destinationPos.below(5).west(5), destinationPos.above(5).east(5))) {
                if (destinationWorld.getBlockState(checkPos).is(RileyModBlocks.ABYSS_PORTAL.get())) {
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
            // Search for an existing portal near the original coordinates to avoid spawning on top of the frame
            destinationPos = thisPos;
            for (BlockPos checkPos : BlockPos.betweenClosed(thisPos.below(10).west(10).north(10), thisPos.above(10).east(10).south(10))) {
                if (destinationWorld.getBlockState(checkPos).is(RileyModBlocks.ABYSS_PORTAL.get())) {
                    destinationPos = checkPos;
                    break;
                }
            }
        }

        // Determine axis to offset the player correctly
        BlockState portalState = destinationWorld.getBlockState(destinationPos);
        Direction.Axis axis = portalState.hasProperty(RileyModPortalBlock.AXIS) ? portalState.getValue(RileyModPortalBlock.AXIS) : Direction.Axis.X;

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
        BlockState frame = RileyModBlocks.ACTIVACTED_FUNTIUM.get().defaultBlockState();
        BlockState portal = RileyModBlocks.ABYSS_PORTAL.get().defaultBlockState().setValue(RileyModPortalBlock.AXIS, Direction.Axis.X);

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