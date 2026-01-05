package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.riley.riley_mod.worldgen.dimension.RileyModDimensions;
import net.riley.riley_mod.worldgen.portal.RileyModPortalShape;
import net.riley.riley_mod.worldgen.portal.RileyModTeleporter;

public class RileyModPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public RileyModPortalBlock(Properties pProperties) {
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
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity.canChangeDimensions() && !pEntity.isPassenger() && !pEntity.isVehicle() && pEntity.mayInteract(pLevel, pPos)) {
            if (pEntity.isOnPortalCooldown()) {
                // This is the key: as long as they are inside, we keep the cooldown at maximum
                pEntity.setPortalCooldown();
            } else {
                // They aren't on cooldown (meaning they just walked in after being outside)
                // We set the cooldown once and then teleport them
                pEntity.setPortalCooldown();
                handleAbyssPortal(pEntity, pPos);
            }
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
    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        Direction.Axis axis = pState.getValue(AXIS);
        if (pFacing.getAxis() != axis && pFacing.getAxis().isHorizontal()) {
            // Check if the frame is still valid using our shape class
            if (!RileyModPortalShape.findPortalShape(pLevel, pCurrentPos, (shape) -> shape.isValid() && shape.isComplete(), axis).isPresent()) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
}
