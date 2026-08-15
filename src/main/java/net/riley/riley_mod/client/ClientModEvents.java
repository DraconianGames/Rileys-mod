package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.SpawnEggTrophy;
import net.riley.riley_mod.client.model.WingModel;
import net.riley.riley_mod.client.render.layer.PlayerWingsLayer;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.WingSneakPacket;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);

            if (renderer == null) {
                continue;
            }

            ModelPart wingRoot = event.getEntityModels().bakeLayer(WingModel.LAYER_LOCATION);
            renderer.addLayer(new PlayerWingsLayer(renderer, wingRoot));
        }
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        BlockColors blockColors = mc.getBlockColors();
        ItemColors itemColors = mc.getItemColors();

        blockColors.register((state, world, pos, tintIndex) -> {
                    Block block = state.getBlock();
                    if (block instanceof SpawnEggTrophy trophy) {
                        return tintIndex == 0 ? trophy.getPrimaryColor() : trophy.getSecondaryColor();
                    }
                    return -1;
                },
                // add all trophy blocks here
                RileyModBlocks.TROPHY_BAT.get(),RileyModBlocks.TROPHY_ENDER_DRAGON.get() /*, RileyModBlocks.TROPHY_CREEPER.get(), ... */);

        itemColors.register((stack, tintIndex) -> {
                    if (stack.getItem() instanceof BlockItem bi) {
                        Block block = bi.getBlock();
                        if (block instanceof SpawnEggTrophy trophy) {
                            return tintIndex == 0 ? trophy.getPrimaryColor() : trophy.getSecondaryColor();
                        }
                    }
                    return -1;
                },
                // corresponding BlockItem(s)
                RileyModBlocks.TROPHY_BAT.get().asItem(), RileyModBlocks.TROPHY_ENDER_DRAGON.get().asItem() /*, RileyModBlocks.TROPHY_CREEPER.get(), ... */);
    }
}