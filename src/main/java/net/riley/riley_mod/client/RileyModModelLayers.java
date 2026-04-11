package net.riley.riley_mod.client;

import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.client.model.WingModel;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RileyModModelLayers {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WingModel.LAYER_LOCATION, WingModel::createBodyLayer);
    }
}