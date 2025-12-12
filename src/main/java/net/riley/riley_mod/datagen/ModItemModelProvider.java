package net.riley.riley_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.ModBlocks;
import net.riley.riley_mod.item.Moditems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RileyMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(Moditems.EYE);
        simpleItem(Moditems.FUNTIUM_ORE);
        simpleItem(Moditems.FUNTIUM_MIX);
        simpleItem(Moditems.STARDUST);
        simpleItem(Moditems.FUNTIUM_PLATE);

        saplingItem(ModBlocks.ABYSS_SAPLING);
    }
    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RileyMod.MODID,"item/" + item.getId().getPath()));
    }
}