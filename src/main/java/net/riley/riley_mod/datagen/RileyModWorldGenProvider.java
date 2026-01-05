package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.worldgen.RileyModBiomeModifiers;
import net.riley.riley_mod.worldgen.RileyModConfiguredFeatures;
import net.riley.riley_mod.worldgen.RileyModPlacedFeatures;
import net.riley.riley_mod.worldgen.biome.RileyModBiomes;
import net.riley.riley_mod.worldgen.dimension.RileyModDimensions;


import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RileyModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, RileyModDimensions::bootstrapType)
            .add(Registries.BIOME, RileyModBiomes::boostrap)
            .add(Registries.LEVEL_STEM, RileyModDimensions::bootstrapStem)
            .add(Registries.CONFIGURED_FEATURE, RileyModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, RileyModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, RileyModBiomeModifiers::bootstrap);


    public RileyModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(RileyMod.MODID));
    }
}
