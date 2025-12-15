package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import net.minecraft.world.level.biome.Biome;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;

import java.util.concurrent.CompletableFuture;

public final class ModBiomeTagsProvider extends BiomeTagsProvider
{

    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider)
    {

    }

    @SafeVarargs
    private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags)
    {
        for(TagKey<Biome> key : tags)
        {
            tag(key).add(biome);
        }
    }

    @Override
    public String getName()
    {
        return "Forge Biome Tags";
    }
}
