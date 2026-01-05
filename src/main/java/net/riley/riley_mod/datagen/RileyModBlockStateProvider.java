package net.riley.riley_mod.datagen;


import net.minecraft.core.Direction;
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
import net.riley.riley_mod.block.custom.RileyModPortalBlock;


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
        blockWithItem(RileyModBlocks.STRUCTURE_BRICK);



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

        makeAbyssPortal(RileyModBlocks.ABYSS_PORTAL.get());
        stairsBlock(((StairBlock) RileyModBlocks.ABYSS_WOOD_STAIRS.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        slabBlock(((SlabBlock) RileyModBlocks.ABYSS_WOOD_SLAB.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        fenceBlock(((FenceBlock) RileyModBlocks.ABYSS_WOOD_FENCE.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) RileyModBlocks.ABYSS_WOOD_FENCE_GATE.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));
        wallBlock(((WallBlock) RileyModBlocks.ABYSS_WOOD_WALL.get()), blockTexture(RileyModBlocks.ABYSS_PLANKS.get()));

        stairsBlock(((StairBlock) RileyModBlocks.STRUCTURE_BRICK_STAIRS.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()));
        slabBlock(((SlabBlock) RileyModBlocks.STRUCTURE_BRICK_SLAB.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()));
        fenceBlock(((FenceBlock) RileyModBlocks.STRUCTURE_BRICK_FENCE.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()));
        fenceGateBlock(((FenceGateBlock) RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()));
        wallBlock(((WallBlock) RileyModBlocks.STRUCTURE_BRICK_WALL.get()), blockTexture(RileyModBlocks.STRUCTURE_BRICK.get()));
        
    }
    private void makeAbyssPortal(Block block) {
        // This creates the thin model (4 pixels thick on the Z axis)
        ModelFile portalModel = models().withExistingParent("abyss_portal", "block/block")
                .texture("particle", modLoc("block/abyss_portal"))
                .texture("portal", modLoc("block/abyss_portal"))
                .element()
                .from(0f, 0f, 6f)
                .to(16f, 16f, 10f)
                .face(Direction.NORTH).uvs(0f, 0f, 16f, 16f).texture("#portal").end()
                .face(Direction.SOUTH).uvs(0f, 0f, 16f, 16f).texture("#portal").end()
                .end();

        // This sets up the variants for X and Z axis
        getVariantBuilder(block).partialState()
                .with(RileyModPortalBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(portalModel).addModel()
                .partialState()
                .with(RileyModPortalBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(portalModel).rotationY(90).addModel();
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