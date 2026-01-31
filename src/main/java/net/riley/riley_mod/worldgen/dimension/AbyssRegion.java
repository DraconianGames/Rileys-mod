package net.riley.riley_mod.worldgen.dimension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.worldgen.biome.RileyModBiomes;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class AbyssRegion extends Region {
    public AbyssRegion(int weight) {
        super(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "abyss_region"), RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        // We provide the ResourceKey (e.g., RileyModBiomes.ABYSSAL_TRENCH) directly now.
        this.addBiome(mapper, Climate.parameters(0.0F, 0.0F, -1.1F, 0.9F, 0.0F, 0.8F, 0.0F), RileyModBiomes.ABYSSAL_TRENCH);
        this.addBiome(mapper, Climate.parameters(0.5F, 0.0F, -0.6F, 0.5F, 0.0F, 0.0F, 0.0F), RileyModBiomes.ABYSS_OCEAN);
        this.addBiome(mapper, Climate.parameters(0.5F, 0.0F, -0.12F, 0.2F, 0.0F, 0.0F, 0.0F), RileyModBiomes.ABYSS_BEACH);
        this.addBiome(mapper, Climate.parameters(0.8F, 0.4F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F), RileyModBiomes.ABYSS_FOREST_BIOME);
        this.addBiome(mapper, Climate.parameters(0.2F, 0.1F, 0.8F, 0.5F, 0.0F, 0.0F, 0.0F), RileyModBiomes.ABYSS_PLAINS);
        this.addBiome(mapper, Climate.parameters(0.0F, 0.5F, 1F, -1.1F, 0.0F, 0.0F, 0.0F), RileyModBiomes.OBSIDIAN_PEAKS);
    }
}