package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RileyModItemTagGenerator extends ItemTagsProvider {
    public RileyModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                                    CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {



        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(RileyModBlocks.ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.ABYSS_WOOD.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(RileyModBlocks.ABYSS_PLANKS.get().asItem());
    }
}