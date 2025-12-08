package net.riley.riley_mod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.riley.riley_mod.RileyMod;


public class ModTags {
    public static class Blocks {
        public static final TagKey<Block>Needs_Funtium_TOOL = tag("needs_funtium_tool");

    private static TagKey<Block> tag(String name) {
        return BlockTags.create(new ResourceLocation(RileyMod.MODID, name));
    }
}
    public static class Items {
        public static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(RileyMod.MODID, name));
        }
    }

}
