package net.riley.riley_mod.event;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
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
        event.put(RileyModEntities.MECHAREX.get(), MechaRexEntity.createAttributes().build());
        event.put(RileyModEntities.MECHA_TERROR.get(), MechaTerrorEntity.createAttributes().build());
        event.put(RileyModEntities.TRISON.get(), TrisonEntity.createAttributes().build());
        event.put(RileyModEntities.TRISON_CART.get(), TrisonCartEntity.createAttributes().build());
        event.put(RileyModEntities.TEST.get(), TrisonCartEntity.createAttributes().build());
        event.put(RileyModEntities.MECHA_PARASITE.get(), MechaParasiteEntity.createAttributes().build());
        event.put(RileyModEntities.PARASITE_CARRIER.get(), ParasiteCarrierEntity.createAttributes().build());
        event.put(RileyModEntities.TRUCK.get(), TruckEntity.createAttributes().build());
        event.put(RileyModEntities.NETHER_TRISON.get(), NetherTrisonEntity.createAttributes().build());
        event.put(RileyModEntities.MOUNTAIN_TRISON.get(), MountainTrisonEntity.createAttributes().build());
        event.put(RileyModEntities.END_TRISON.get(), EndTrisonEntity.createAttributes().build());
        event.put(RileyModEntities.ABYSS_TRISON.get(), AbyssTrisonEntity.createAttributes().build());
        event.put(RileyModEntities.FALLOW_TRISON.get(), FallowTrisonEntity.createAttributes().build());
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
        SpawnPlacements.register(
                RileyModEntities.BISON.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules
        );
    }
}
