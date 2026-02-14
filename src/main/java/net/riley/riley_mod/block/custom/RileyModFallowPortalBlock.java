package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.worldgen.dimension.RileyModDimensions;
import net.riley.riley_mod.worldgen.portal.RileyModFallowPortalShape;
import net.riley.riley_mod.worldgen.portal.RileyModFallowTeleporter;


public class RileyModFallowPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public RileyModFallowPortalBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction.Axis axis = pState.getValue(AXIS);
        return axis == Direction.Axis.Z ? Z_AXIS_AABB : X_AXIS_AABB;
    }
    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return net.minecraft.world.phys.shapes.Shapes.empty();
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.canChangeDimensions() && !pEntity.isPassenger() && !pEntity.isVehicle() && pEntity.mayInteract(pLevel, pPos)) {

            pEntity.handleInsidePortal(pPos);

            if (pEntity.isOnPortalCooldown()) {
                pEntity.setPortalCooldown();
            } else {
                if (pLevel.isClientSide) return;

                String TIMER_KEY = "RileyModPortalTime";
                String LAST_TICK_KEY = "RileyModLastPortalTick";
                String WAITING_KEY = "RileyModWaitingToLeavePortal";

                int portalTime = pEntity.getPersistentData().getInt(TIMER_KEY);
                int lastTick = pEntity.getPersistentData().getInt(LAST_TICK_KEY);
                boolean waitingToLeave = pEntity.getPersistentData().getBoolean(WAITING_KEY);

                // If the gap between now and the last time they were in the portal is > 1 tick,
                // it means they left. Reset the timer and the waiting flag!
                if (pEntity.tickCount - lastTick > 1) {
                    portalTime = 0;
                    pEntity.getPersistentData().putBoolean(WAITING_KEY, false);
                    waitingToLeave = false;
                }

                // Update the last seen tick
                pEntity.getPersistentData().putInt(LAST_TICK_KEY, pEntity.tickCount);

                // If they just arrived and haven't left yet, don't start the 6-second timer
                if (waitingToLeave) {
                    return;
                }

                if (pEntity instanceof Player player) {
                    if (player.getAbilities().instabuild) {
                        handleFallowPortal(pEntity, pPos);
                        return;
                    }

                    if (portalTime >= 120) { // 6 seconds
                        pEntity.getPersistentData().putInt(TIMER_KEY, 0);
                        handleFallowPortal(pEntity, pPos);
                    } else {
                        pEntity.getPersistentData().putInt(TIMER_KEY, portalTime + 1);
                    }
                } else {
                    if (portalTime >= 120) {
                        pEntity.getPersistentData().putInt(TIMER_KEY, 0);
                        handleFallowPortal(pEntity, pPos);
                    } else {
                        pEntity.getPersistentData().putInt(TIMER_KEY, portalTime + 1);
                    }
                }
            }
        }
    }
    private void handleFallowPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == RileyModDimensions.FALLOWDIM_LEVEL_KEY ?
                    Level.OVERWORLD : RileyModDimensions.FALLOWDIM_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                // Play whoosh before teleport
                serverlevel.playSound(null, pPos, SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 0.5F, 1.0F);

                if(resourcekey == RileyModDimensions.FALLOWDIM_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new RileyModFallowTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new RileyModFallowTeleporter(pPos, false));
                }

                // Play whoosh after teleport (at the new location)
                portalDimension.playSound(null, player.blockPosition(), SoundEvents.PORTAL_TRAVEL, SoundSource.BLOCKS, 0.5F, 1.0F);
            }
        }
    }
    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);
    }
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        Direction.Axis axis = pState.getValue(AXIS);
        Direction.Axis facingAxis = pFacing.getAxis();

        // If a block next to the portal (on the same plane as the portal or its frame) changes, check validity
        if (axis != facingAxis && facingAxis.isHorizontal()) {
            boolean isStillValid = RileyModFallowPortalShape.findPortalShape(pLevel, pCurrentPos, (shape) -> shape.isValid() && shape.isComplete(), axis).isPresent();
            if (!isStillValid) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}