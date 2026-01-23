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
import net.riley.riley_mod.entity.custom.*;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(RileyModEntities.SUNLESS_CRAB.get(), SunlessCrabEntity.createAttributes().build());
        event.put(RileyModEntities.RAPTER.get(), RapterEntity.createAttributes().build());
        event.put(RileyModEntities.NIGHT_TERROR.get(), NightTerrorEntity.createAttributes().build());
        event.put(RileyModEntities.WHALE_HUNTER.get(), WhaleHunterEntity.createAttributes().build());
        event.put(RileyModEntities.FROST_HOPPER.get(), FrostHopperEntity.createAttributes().build());
        event.put(RileyModEntities.TOOTHFAIRY.get(), ToothFairyEntity.createAttributes().build());
        event.put(RileyModEntities.BONEFAIRY.get(), BoneFairyEntity.createAttributes().build());
        event.put(RileyModEntities.SKELETONFAIRY.get(), SkeletonFairyEntity.createAttributes().build());
        event.put(RileyModEntities.SKULL_FAIRY.get(), SkullFairyEntity.createAttributes().build());
        event.put(RileyModEntities.BISON.get(), BisonEntity.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(
                RileyModEntities.NIGHT_TERROR.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS, // This is essential for large flyers
                Heightmap.Types.WORLD_SURFACE,
                NightTerrorEntity::checkNightTerrorSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR
        );

        event.register(
                RileyModEntities.WHALE_HUNTER.get(),
                SpawnPlacements.Type.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                WhaleHunterEntity::checkWhaleHunterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR // Removed Strategy.AND
        );
        event.register(
                RileyModEntities.FROST_HOPPER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                FrostHopperEntity::checkFrostHopperSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                RileyModEntities.SUNLESS_CRAB.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                SunlessCrabEntity::checkSunlessCrabSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR
        );
        event.register(
                RileyModEntities.RAPTER.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                RapterEntity::checkRapterSpawnRules,
                SpawnPlacementRegisterEvent.Operation.OR
        );
    }
}
