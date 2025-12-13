package net.riley.riley_mod.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
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



        leavesBlock(ModBlocks.ABYSS_LEAVES);
        logBlock(((RotatedPillarBlock) ModBlocks.ABYSS_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.ABYSS_WOOD.get()), blockTexture(ModBlocks.ABYSS_LOG.get()), blockTexture(ModBlocks.ABYSS_LOG.get()));

        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ABYSS_LOG.get()), blockTexture(ModBlocks.STRIPPED_ABYSS_LOG.get()),
                new ResourceLocation(RileyMod.MODID, "block/stripped_abyss_log_top"));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_ABYSS_WOOD.get()), blockTexture(ModBlocks.STRIPPED_ABYSS_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_ABYSS_LOG.get()));

        blockItem(ModBlocks.ABYSS_LOG);
        blockItem(ModBlocks.ABYSS_WOOD);
        blockItem(ModBlocks.STRIPPED_ABYSS_LOG);
        blockItem(ModBlocks.STRIPPED_ABYSS_WOOD);
        saplingBlock(ModBlocks.ABYSS_SAPLING);

        stairsBlock(((StairBlock) ModBlocks.ABYSS_WOOD_STAIRS.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.ABYSS_WOOD_SLAB.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()));
        

        fenceBlock(((FenceBlock) ModBlocks.ABYSS_WOOD_FENCE.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.ABYSS_WOOD_FENCE_GATE.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()));
        wallBlock(((WallBlock) ModBlocks.ABYSS_WOOD_WALL.get()), blockTexture(ModBlocks.ABYSS_PLANKS.get()));

        
    }
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
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