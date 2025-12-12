package net.riley.riley_mod.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.ModBlocks;


public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RileyMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(ModBlocks.FUNTIUM_ORE_BLOCK);
        blockWithItem(ModBlocks.DEEPSLATE_FUNTIUM_ORE);
        blockWithItem(ModBlocks.FUNTIUM_BLOCK);
        blockWithItem(ModBlocks.ACTIVACTED_FUNTIUM);
        blockWithItem(ModBlocks.ABYSS_PLANKS);
        blockWithItem(ModBlocks.ABYSS_SAPLING);

        blockItem(ModBlocks.ABYSS_LOG);
        blockItem(ModBlocks.ABYSS_WOOD);
        blockItem(ModBlocks.STRIPPED_ABYSS_LOG);
        blockItem(ModBlocks.STRIPPED_ABYSS_WOOD);
        leavesBlock(ModBlocks.ABYSS_LEAVES);

    }
    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(RileyMod.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }



    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}