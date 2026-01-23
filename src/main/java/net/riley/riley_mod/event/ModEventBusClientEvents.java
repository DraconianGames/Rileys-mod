package net.riley.riley_mod.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.client.*;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
@SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(RileyModModelLayers.TOOTHFAIRY_LAYER, ToothFairyModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.SUNLESS_CRAB_LAYER, SunlessCrabModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.RAPTER_LAYER, RapterModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.NIGHT_TERROR_LAYER, NightTerrorModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.WHALE_HUNTER_LAYER, WhaleHunterModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.FROST_HOPPER_LAYER, FrostHopperModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.BONEFAIRY_LAYER, BoneFairyModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.SKELETONFAIRY_LAYER, SkeletonFairyModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.SKULL_FAIRY_LAYER, SkullFairyModel::createBodyLayer);
        event.registerLayerDefinition(RileyModModelLayers.BISON_LAYER, BisonModel::createBodyLayer);
    }
}
