package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.ABYSS_WOOD_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ABYSS_WOOD_FENCE_GATE.get());
        this.tag(BlockTags.WALLS)
                .add(ModBlocks.ABYSS_WOOD_WALL.get());
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ABYSS_LOG.get())
                .add(ModBlocks.ABYSS_WOOD.get())
                .add(ModBlocks.STRIPPED_ABYSS_LOG.get())
                .add(ModBlocks.STRIPPED_ABYSS_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ABYSS_PLANKS.get());
    }
}