package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RileyModItemTagGenerator extends ItemTagsProvider {
    public RileyModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                                    CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(RileyModTags.Items.MEAT)
                .add(Items.BEEF,
                        Items.COOKED_BEEF,
                        Items.SALMON,
                        Items.COOKED_SALMON,
                        Items.PUFFERFISH,
                        Items.COD,
                        Items.COOKED_COD,
                        Items.CHICKEN,
                        Items.COOKED_CHICKEN,
                        Items.RABBIT,
                        Items.COOKED_RABBIT,
                        Items.PORKCHOP,
                        Items.COOKED_PORKCHOP);

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(RileyModItems.FUNTIUM_HELMET.get(),
                        RileyModItems.FUNTIUM_CHESTPLATE.get(),
                        RileyModItems.FUNTIUM_LEGGINGS.get(),
                        RileyModItems.FUNTIUM_BOOTS.get());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(RileyModBlocks.ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.ABYSS_WOOD.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_LOG.get().asItem())
                .add(RileyModBlocks.STRIPPED_ABYSS_WOOD.get().asItem());

        this.tag(ItemTags.PLANKS)
                .add(RileyModBlocks.ABYSS_PLANKS.get().asItem());
    }
}