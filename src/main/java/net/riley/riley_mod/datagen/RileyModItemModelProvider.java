package net.riley.riley_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.item.RileyModItems;

public class RileyModItemModelProvider extends ItemModelProvider {
    public RileyModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(RileyModItems.EYE);
        simpleItem(RileyModItems.FUNTIUM_ORE);
        simpleItem(RileyModItems.FUNTIUM_MIX);
        simpleItem(RileyModItems.STARDUST);
        simpleItem(RileyModItems.FUNTIUM_PLATE);

        saplingItem(RileyModBlocks.ABYSS_SAPLING);

        fenceItem(RileyModBlocks.ABYSS_WOOD_FENCE, RileyModBlocks.ABYSS_PLANKS);
        
        wallItem(RileyModBlocks.ABYSS_WOOD_WALL, RileyModBlocks.ABYSS_PLANKS);

        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_STAIRS);
        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_SLAB);
        
        evenSimplerBlockItem(RileyModBlocks.ABYSS_WOOD_FENCE_GATE);
    }
    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"block/" + item.getId().getPath()));
    }
    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(RileyMod.MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(RileyMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }
    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(RileyMod.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"item/" + item.getId().getPath()));
    }
}