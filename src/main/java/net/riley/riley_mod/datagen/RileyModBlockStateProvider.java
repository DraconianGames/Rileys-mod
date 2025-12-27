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
import net.riley.riley_mod.block.RileyModBlocks;


public class RileyModBlockStateProvider extends BlockStateProvider {
    public RileyModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RileyMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(RileyModBlocks.FUNTIUM_ORE_BLOCK);
        blockWithItem(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE);
        blockWithItem(RileyModBlocks.FUNTIUM_BLOCK);
        blockWithItem(RileyModBlocks.ACTIVACTED_FUNTIUM);
        blockWithItem(RileyModBlocks.ABYSS_PLANKS);



        leavesBlock(RileyModBlocks.ABYSS_LEAVES);
        logBlock(((RotatedPillarBlock) RileyModBlocks.ABYSS_LOG.get()));
        axisBlock(((RotatedPillarBlock) RileyModBlocks.ABYSS_WOOD.get()), blockTexture(RileyModBlocks.ABYSS_LOG.get()), blockTexture(RileyModBlocks.ABYSS_LOG.get()));

        axisBlock(((RotatedPillarBlock) RileyModBlocks.STRIPPED_ABYSS_LOG.get()), blockTexture(RileyModBlocks.STRIPPED_ABYSS_LOG.get()),
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "block/stripped_abyss_log_top"));
        axisBlock(((RotatedPillarBlock) RileyModBlocks.STRIPPED_ABYSS_WOOD.get()), blockTexture(RileyModBlocks.STRIPPED_ABYSS_LOG.get()),
                blockTexture(RileyModBlocks.STRIPPED_ABYSS_LOG.get()));

        blockItem(RileyModBlocks.ABYSS_LOG);
        blockItem(RileyModBlocks.ABYSS_WOOD);
        blockItem(RileyModBlocks.STRIPPED_ABYSS_LOG);
        blockItem(RileyModBlocks.STRIPPED_ABYSS_WOOD);
        saplingBlock(RileyModBlocks.ABYSS_SAPLING);

        blockWithItem(RileyModBlocks.ABYSS_PORTAL);
        stairsBlock(((StairBlock) RileyModBlocks.ABYSS_WOOD_STAIRS.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        slabBlock(((SlabBlock) RileyModBlocks.ABYSS_WOOD_SLAB.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        

        fenceBlock(((FenceBlock) RileyModBlocks.ABYSS_WOOD_FENCE.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) RileyModBlocks.ABYSS_WOOD_FENCE_GATE.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        wallBlock(((WallBlock) RileyModBlocks.ABYSS_WOOD_WALL.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));

        
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