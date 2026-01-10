package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RileyModBlockTagGenerator extends BlockTagsProvider {
    public RileyModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(RileyModTags.Blocks.Needs_Funtium_TOOL)
                .add(RileyModBlocks.STRUCTURE_BRICK_FENCE.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_WALL.get())
                .add(RileyModBlocks.STRUCTURE_BRICK.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_STAIRS.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_SLAB.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE.get());

        this.tag(BlockTags.FENCES)
                .add(RileyModBlocks.ABYSS_WOOD_FENCE.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_FENCE.get());

        this.tag(BlockTags.FENCE_GATES)
                .add(RileyModBlocks.ABYSS_WOOD_FENCE_GATE.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE.get());

        this.tag(BlockTags.WALLS)
                .add(RileyModBlocks.ABYSS_WOOD_WALL.get())
                .add(RileyModBlocks.STRUCTURE_BRICK_WALL.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(RileyModBlocks.ABYSS_LOG.get())
                .add(RileyModBlocks.ABYSS_WOOD.get())
                .add(RileyModBlocks.STRIPPED_ABYSS_LOG.get())
                .add(RileyModBlocks.STRIPPED_ABYSS_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(RileyModBlocks.ABYSS_PLANKS.get());

        this.tag(BlockTags.DIRT)
                .add(RileyModBlocks.ABYSSAL_GRASS.get())
                .add(RileyModBlocks.ABYSSAL_DIRT.get());

        this.tag(BlockTags.SAND)
                .add(RileyModBlocks.BLACK_SAND.get());

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(RileyModBlocks.ABYSSAL_GRASS.get())
                .add(RileyModBlocks.ABYSSAL_DIRT.get())
                .add(RileyModBlocks.BLACK_SAND.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(RileyModBlocks.STRUCTURE_BRICK.get())
                .add(RileyModBlocks.ABYSSAL_STONE.get())
                .add(RileyModBlocks.ABYSSAL_COBBLESTONE.get());
    }
}