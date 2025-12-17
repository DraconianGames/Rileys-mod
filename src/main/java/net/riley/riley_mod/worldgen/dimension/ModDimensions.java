package net.riley.riley_mod.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.worldgen.biome.ModBiomes;

import java.util.List;
import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> ABYSSDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim"));
    public static final ResourceKey<Level> ABYSSDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim"));
    public static final ResourceKey<DimensionType> ABYSSDIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim_type"));
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(ABYSSDIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                true, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(true, true, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(ModBiomes.ABYSS_FOREST_BIOME)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(Pair.of(
                                        Climate.parameters(0.3F, 0.2F, 1F, 0F, 1F, -2F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.ABYSS_FOREST_BIOME)),
                                Pair.of(
                                        Climate.parameters(0.4F, 0.2F, 1F, 0F, 1F, -2F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.ABYSS_PLAINS))


                        ))),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.ABYSSDIM_TYPE), noiseBasedChunkGenerator);

        context.register(ABYSSDIM_KEY, stem);
    }
}