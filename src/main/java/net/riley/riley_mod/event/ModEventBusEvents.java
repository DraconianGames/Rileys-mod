package net.riley.riley_mod.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.NightTerrorEntity;
import net.riley.riley_mod.entity.custom.RapterEntity;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(RileyModEntities.SUNLESS_CRAB.get(), SunlessCrabEntity.createAttributes().build());
        event.put(RileyModEntities.RAPTER.get(), RapterEntity.createAttributes().build());
        event.put(RileyModEntities.NIGHT_TERROR.get(), NightTerrorEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(
                RileyModEntities.NIGHT_TERROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NightTerrorEntity::checkNightTerrorSpawnRules,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        );
    }

}
