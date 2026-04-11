package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
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
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
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
}