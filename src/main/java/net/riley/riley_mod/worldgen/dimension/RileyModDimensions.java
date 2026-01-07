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
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.riley.riley_mod.RileyMod;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.block.Blocks;
import net.riley.riley_mod.worldgen.biome.RileyModBiomes;
import java.util.List;
import java.util.OptionalLong;
import terrablender.api.ParameterUtils;


public class RileyModDimensions {
    public static final ResourceKey<LevelStem> ABYSSDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim"));
    public static final ResourceKey<Level> ABYSSDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim"));
    public static final ResourceKey<DimensionType> ABYSSDIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID,"abyss_dim_type"));
    public static final ResourceKey<NoiseGeneratorSettings> ABYSS_NOISE_SETTINGS = ResourceKey.create(Registries.NOISE_SETTINGS,
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyss_noise_settings"));


    public static void bootstrapNoise(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(ABYSS_NOISE_SETTINGS, new NoiseGeneratorSettings(
                // minY: -128, height: 384 (Gives more depth than standard -64)
                NoiseSettings.create(-128, 384, 1, 2),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                NoiseGeneratorSettings.overworld(context, false, false).noiseRouter(),
                SurfaceRuleData.overworldLike(true, false, false),
                List.of(),
                63, // Keep sea level at 63
                false, true, false, false
        ));
    }
//change 24000 to 12000 when satisfieded
    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(ABYSSDIM_TYPE, new DimensionType(
                OptionalLong.of(24000),
                false, false, false, false,
                1.0, true, true,
                -128, // Start deeper
                384,  // Maintain height
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0f,
                new DimensionType.MonsterSettings(true, true, ConstantInt.of(0), 0)));
    }


    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        // We update this list to match the logical flow:
        // Trench -> Ocean -> Beach -> Forest -> Plains -> Peaks
        MultiNoiseBiomeSource biomeSource = MultiNoiseBiomeSource.createFromList(
                new Climate.ParameterList<>(List.of(
                        Pair.of(Climate.parameters(0.0F, 0.0F, -1.8F, -1.0F, 0.0F, 1.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.ABYSSAL_TRENCH)),
                        Pair.of(Climate.parameters(0.0F, 0.0F, -1.1F, -0.2F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.ABYSS_OCEAN)),
                        Pair.of(Climate.parameters(0.5F, 0.0F, -0.15F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.ABYSS_BEACH)),
                        Pair.of(Climate.parameters(0.5F, 0.4F, 0.1F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.ABYSS_FOREST_BIOME)),
                        Pair.of(Climate.parameters(0.2F, 0.1F, 0.4F, 0.5F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.ABYSS_PLAINS)),
                        Pair.of(Climate.parameters(0.0F, 0.5F, 1.1F, -1.1F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(RileyModBiomes.OBSIDIAN_PEAKS))
                ))
        );

        LevelStem stem = new LevelStem(dimTypes.getOrThrow(RileyModDimensions.ABYSSDIM_TYPE),
                new NoiseBasedChunkGenerator(biomeSource, noiseGenSettings.getOrThrow(ABYSS_NOISE_SETTINGS)));

        context.register(ABYSSDIM_KEY, stem);
    }
}