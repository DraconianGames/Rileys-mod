package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.menu.MorphStationMenu;

import java.util.LinkedHashSet;
import java.util.Set;

public class MorphStationFillerBlock extends Block {
    public MorphStationFillerBlock(Properties pProperties) {
        super(pProperties);
    }
    private static BlockPos findMainStationPos(Level level, BlockPos fillerPos) {
        for (int y = -1; y <= 0; y++) {
            BlockPos checkPos = fillerPos.offset(0, y, 0);

            if (level.getBlockState(checkPos).is(RileyModBlocks.MORPH_STATION.get())) {
                return checkPos;
            }
        }

        return fillerPos;
    }
    private static Set<ResourceLocation> getUnlockedMorphs(Level level, BlockPos pos) {
        Set<ResourceLocation> unlockedMorphs = new LinkedHashSet<>();
        unlockedMorphs.add(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player"));

        Set<ResourceLocation> storedTrophies = MachineCoreMultiblock.getStoredTrophiesForConnectedDevice(level, pos);

        if (storedTrophies.contains(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter_trophy"))) {
            unlockedMorphs.add(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter"));
        }

        return unlockedMorphs;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE; // Makes the block invisible
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block(); // Makes it a full solid cube for selection
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.block(); // Makes it a full solid cube for walking
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockPos stationPos = findMainStationPos(level, pos);

            NetworkHooks.openScreen(
                    serverPlayer,
                    new SimpleMenuProvider(
                            (containerId, inv, p) -> new MorphStationMenu(containerId, inv, stationPos),
                            Component.literal("Morph Station")
                    ),
                    buf -> {
                        buf.writeBlockPos(stationPos);
                        buf.writeCollection(getUnlockedMorphs(level, stationPos), FriendlyByteBuf::writeResourceLocation);
                    }
            );
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide) {
            // Scan the 3x3x2 area around this filler to find the main trophy
            for (int x = 0; x <= 0; x++) {
                for (int z = 0; z <= 0; z++) {
                    for (int y = -1; y <= 0; y++) { // Look down/around
                        BlockPos checkPos = pPos.offset(x, y, z);
                        BlockState state = pLevel.getBlockState(checkPos);

                        if (state.is(RileyModBlocks.MORPH_STATION.get())) {
                            // If we find the trophy, break it!
                            // The trophy's own onRemove logic will then clean up all other fillers.
                            pLevel.destroyBlock(checkPos, !pPlayer.isCreative());
                            return;
                        }
                    }
                }
            }
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
}