package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.RileyModTags;
import net.riley.riley_mod.worldgen.biome.RileyModBiomes;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RileyModBiomeTagGenerator extends BiomeTagsProvider {
    public RileyModBiomeTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, lookupProvider, RileyMod.MODID, existingFileHelper);
    }
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(RileyModTags.Biomes.HAS_ABYSS_TREE)
                .addOptional(RileyModBiomes.ABYSS_FOREST_BIOME.location());



    }
}
