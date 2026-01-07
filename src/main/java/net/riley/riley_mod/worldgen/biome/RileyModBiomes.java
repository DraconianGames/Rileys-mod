package net.riley.riley_mod.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.worldgen.RileyModPlacedFeatures;

public class RileyModBiomes {
    public static final ResourceKey<Biome> ABYSS_FOREST_BIOME = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyss_forest_biome"));
    public static final ResourceKey<Biome> ABYSS_PLAINS = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_plains"));
    public static final ResourceKey<Biome> ABYSSAL_TRENCH = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyssal_trench"));
    public static final ResourceKey<Biome> OBSIDIAN_PEAKS = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "obsidian_peaks"));
    public static final ResourceKey<Biome> ABYSS_BEACH = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyss_beach"));
    public static final ResourceKey<Biome> ABYSS_OCEAN = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyss_ocean"));


    public static void boostrap(BootstapContext<Biome> context) {
        context.register(ABYSS_FOREST_BIOME, abyssForestBiome(context));
        context.register(ABYSS_PLAINS, abyssPlainsBiome(context));
        context.register(ABYSSAL_TRENCH, abyssalTrench(context));
        context.register(OBSIDIAN_PEAKS, obsidianPeaks(context));
        context.register(ABYSS_BEACH, abyssBeach(context));
        context.register(ABYSS_OCEAN, abyssOcean(context));

    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }
    public static Biome abyssPlainsBiome(BootstapContext<Biome> context){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.SUNLESS_CRAB.get(), 50, 3, 7));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.RAPTER.get(), 70, 8, 13));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.NIGHT_TERROR.get(), 80,1,3 ));




        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        BiomeDefaultFeatures.addExtraEmeralds(biomeBuilder);

        //biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        //biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, RileyModPlacedFeatures.ABYSS_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(740303)
                        .waterFogColor(900000)
                        .skyColor(0x30c00)
                        .grassColorOverride(440000)
                        .foliageColorOverride(411818)
                        .fogColor(0x30c00)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome abyssForestBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();


        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.SUNLESS_CRAB.get(), 50, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.RAPTER.get(), 70, 8, 13));




        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        BiomeDefaultFeatures.addExtraEmeralds(biomeBuilder);

        //biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);


        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(740303)
                        .waterFogColor(900000)
                        .skyColor(0x30c00)
                        .grassColorOverride(440000)
                        .foliageColorOverride(411818)
                        .fogColor(0x30c00)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
    public static Biome abyssalTrench(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        // Spawns plenty of Whale Hunters and Glow Squids in the dark depths
        spawnBuilder.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.WHALE_HUNTER.get(), 100, 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        // Add kelp and seagrass logic
        BiomeDefaultFeatures.addColdOceanExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.5f)
                .temperature(0.8f) // Increased temperature to prevent ice formation
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x0a0a1a)
                        .waterFogColor(0x050510)
                        .skyColor(0x000000)
                        .fogColor(0x000000)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }

    public static Biome obsidianPeaks(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        // Night Terrors hunt in the high peaks
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(RileyModEntities.NIGHT_TERROR.get(), 100, 1, 2));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraEmeralds(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(1.0f) // High precipitation for snow/storms
                .temperature(-0.8f) // Freezing peaks
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x0a0a1a)
                        .waterFogColor(0x0d0600)
                        .skyColor(0x050505)
                        .fogColor(0x111111)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
    public static Biome abyssBeach(BootstapContext<Biome> context) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true).downfall(0.8f).temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x3f76e4).waterFogColor(0x050533).skyColor(0x30c00).fogColor(0x30c00).build())
                .build();
    }

    public static Biome abyssOcean(BootstapContext<Biome> context) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        globalOverworldGeneration(biomeBuilder);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(false).downfall(0.5f).temperature(0.5f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x0000FF).waterFogColor(0x050533).skyColor(0x000000).fogColor(0x000000).build())
                .build();
    }
}