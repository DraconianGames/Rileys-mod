package net.riley.riley_mod.datagen;


import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.MachineCorePartBlock;
import net.riley.riley_mod.block.custom.MuscleCropBlock;
import net.riley.riley_mod.block.custom.RileyModAbyssPortalBlock;
import net.riley.riley_mod.block.custom.RileyModFallowPortalBlock;

import java.util.function.Function;


public class RileyModBlockStateProvider extends BlockStateProvider {
    public RileyModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, RileyMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        blockWithItem(RileyModBlocks.FUNTIUM_ORE_BLOCK);
        blockWithItem(RileyModBlocks.BLUESTONE_ORE);
        blockWithItem(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE);
        blockWithItem(RileyModBlocks.FUNTIUM_BLOCK);
        blockWithItem(RileyModBlocks.ACTIVACTED_FUNTIUM);
        blockWithItem(RileyModBlocks.ABYSS_PLANKS);
        blockWithItem(RileyModBlocks.STRUCTURE_BRICK);
        blockWithItem(RileyModBlocks.ABYSSAL_STONE);
        blockWithItem(RileyModBlocks.ABYSSAL_COBBLESTONE);
        blockWithItem(RileyModBlocks.BLACK_SAND);
        blockWithItem(RileyModBlocks.ABYSSAL_DIRT);
        blockWithItem(RileyModBlocks.NIGHT_STAR);
        blockWithItem(RileyModBlocks.SPECIAL_SPAWNER);
        blockWithItem(RileyModBlocks.FALLOW_GROUND);
        blockWithItem(RileyModBlocks.FALLOW_EARTH);
        blockWithItem(RileyModBlocks.FALLOW_PORTAL_FRAME);
        machineCorePartBlock(RileyModBlocks.MACHINE_CORE, "machine_core");
        machineCoreCenterBlock(
                RileyModBlocks.MACHINE_CORE_CENTER,
                "machine_core_center",
                "machine_core_formed"
        );


        abyssalGrassBlock(RileyModBlocks.ABYSSAL_GRASS);

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
        makeFallowPortal(RileyModBlocks.FALLOW_PORTAL.get());
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

        topTexturedBlock(RileyModBlocks.TROPHY_READER, "trophy_reader", "trophy_reader_top");

        machineCoreTopBottomPartBlock(RileyModBlocks.MACHINE_CORE_PORT, "machine_core_port", "machine_core");
        machineCoreTopBottomPartBlock(RileyModBlocks.MACHINE_CORE_SCREEN, "machine_core_screen", "machine_core");

        makeMuscleCrop((CropBlock) RileyModBlocks.MUSCLE_CROP.get(), "muscle_crop_stage", "muscle_crop_stage");

        trophyBlock(RileyModBlocks.TROPHY_BAT);
        trophyBlock(RileyModBlocks.TROPHY_ALLAY);
        trophyBlock(RileyModBlocks.TROPHY_AXOLOTL);
        trophyBlock(RileyModBlocks.TROPHY_BEE);
        trophyBlock(RileyModBlocks.TROPHY_BLAZE);
        trophyBlock(RileyModBlocks.TROPHY_CAT);
        trophyBlock(RileyModBlocks.TROPHY_CAMEL);
        trophyBlock(RileyModBlocks.TROPHY_CAVE_SPIDER);
        trophyBlock(RileyModBlocks.TROPHY_CHICKEN);
        trophyBlock(RileyModBlocks.TROPHY_COD);
        trophyBlock(RileyModBlocks.TROPHY_COW);
        trophyBlock(RileyModBlocks.TROPHY_CREEPER);
        trophyBlock(RileyModBlocks.TROPHY_DOLPHIN);
        trophyBlock(RileyModBlocks.TROPHY_DONKEY);
        trophyBlock(RileyModBlocks.TROPHY_DROWNED);
        trophyBlock(RileyModBlocks.TROPHY_ELDER_GUARDIAN);
        trophyBlock(RileyModBlocks.TROPHY_ENDERMAN);
        trophyBlock(RileyModBlocks.TROPHY_ENDERMITE);
        trophyBlock(RileyModBlocks.TROPHY_EVOKER);
        trophyBlock(RileyModBlocks.TROPHY_FOX);
        trophyBlock(RileyModBlocks.TROPHY_FROG);
        trophyBlock(RileyModBlocks.TROPHY_GHAST);
        trophyBlock(RileyModBlocks.TROPHY_GLOW_SQUID);
        trophyBlock(RileyModBlocks.TROPHY_GOAT);
        trophyBlock(RileyModBlocks.TROPHY_GUARDIAN);
        trophyBlock(RileyModBlocks.TROPHY_HOGLIN);
        trophyBlock(RileyModBlocks.TROPHY_HORSE);
        trophyBlock(RileyModBlocks.TROPHY_HUSK);
        trophyBlock(RileyModBlocks.TROPHY_IRON_GOLEM);
        trophyBlock(RileyModBlocks.TROPHY_LLAMA);
        trophyBlock(RileyModBlocks.TROPHY_MAGMA_CUBE);
        trophyBlock(RileyModBlocks.TROPHY_MOOSHROOM);
        trophyBlock(RileyModBlocks.TROPHY_MULE);
        trophyBlock(RileyModBlocks.TROPHY_OCELOT);
        trophyBlock(RileyModBlocks.TROPHY_PANDA);
        trophyBlock(RileyModBlocks.TROPHY_PARROT);
        trophyBlock(RileyModBlocks.TROPHY_PHANTOM);
        trophyBlock(RileyModBlocks.TROPHY_PIG);
        trophyBlock(RileyModBlocks.TROPHY_PIGLIN);
        trophyBlock(RileyModBlocks.TROPHY_PIGLIN_BRUTE);
        trophyBlock(RileyModBlocks.TROPHY_PILLAGER);
        trophyBlock(RileyModBlocks.TROPHY_POLAR_BEAR);
        trophyBlock(RileyModBlocks.TROPHY_PUFFERFISH);
        trophyBlock(RileyModBlocks.TROPHY_RABBIT);
        trophyBlock(RileyModBlocks.TROPHY_RAVAGER);
        trophyBlock(RileyModBlocks.TROPHY_SALMON);
        trophyBlock(RileyModBlocks.TROPHY_SHEEP);
        trophyBlock(RileyModBlocks.TROPHY_SHULKER);
        trophyBlock(RileyModBlocks.TROPHY_SILVERFISH);
        trophyBlock(RileyModBlocks.TROPHY_SKELETON);
        trophyBlock(RileyModBlocks.TROPHY_SKELETON_HORSE);
        trophyBlock(RileyModBlocks.TROPHY_SLIME);
        trophyBlock(RileyModBlocks.TROPHY_SNIFFER);
        trophyBlock(RileyModBlocks.TROPHY_SNOW_GOLEM);
        trophyBlock(RileyModBlocks.TROPHY_SPIDER);
        trophyBlock(RileyModBlocks.TROPHY_SQUID);
        trophyBlock(RileyModBlocks.TROPHY_STRAY);
        trophyBlock(RileyModBlocks.TROPHY_STRIDER);
        trophyBlock(RileyModBlocks.TROPHY_TADPOLE);
        trophyBlock(RileyModBlocks.TROPHY_TRADER_LLAMA);
        trophyBlock(RileyModBlocks.TROPHY_TROPICAL_FISH);
        trophyBlock(RileyModBlocks.TROPHY_TURTLE);
        trophyBlock(RileyModBlocks.TROPHY_VEX);
        trophyBlock(RileyModBlocks.TROPHY_VILLAGER);
        trophyBlock(RileyModBlocks.TROPHY_VINDICATOR);
        trophyBlock(RileyModBlocks.TROPHY_WANDERING_TRADER);
        trophyBlock(RileyModBlocks.TROPHY_WARDEN);
        trophyBlock(RileyModBlocks.TROPHY_WITCH);
        trophyBlock(RileyModBlocks.TROPHY_WITHER);
        trophyBlock(RileyModBlocks.TROPHY_WITHER_SKELETON);
        trophyBlock(RileyModBlocks.TROPHY_WOLF);
        trophyBlock(RileyModBlocks.TROPHY_ZOGLIN);
        trophyBlock(RileyModBlocks.TROPHY_ZOMBIE);
        trophyBlock(RileyModBlocks.TROPHY_ZOMBIE_HORSE);
        trophyBlock(RileyModBlocks.TROPHY_ZOMBIE_VILLAGER);
        trophyBlock(RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN);
        trophyBlock(RileyModBlocks.TROPHY_ENDER_DRAGON);
    }
    private void trophyBlock(RegistryObject<Block> blockRegistryObject) {
        String blockName = blockRegistryObject.getId().getPath();

        // Create the block model that inherits from spawn_egg_trophy
        ModelFile trophyModel = models().withExistingParent(blockName, modLoc("block/spawn_egg_trophy"));

        // Set the blockstate variant
        simpleBlock(blockRegistryObject.get(), trophyModel);
    }

    public void makeMuscleCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> strawberryStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] strawberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((MuscleCropBlock) block).getAgeProperty()),
                new ResourceLocation(RileyMod.MODID, "block/" + textureName + state.getValue(((MuscleCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }
    private void makeFallowPortal(Block block) {
        // This creates the thin model (4 pixels thick on the Z axis)
        ModelFile portalModel = models().withExistingParent("fallow_portal", "block/block")
                .texture("particle", modLoc("block/fallow_portal"))
                .texture("portal", modLoc("block/fallow_portal"))
                .element()
                .from(0f, 0f, 6f)
                .to(16f, 16f, 10f)
                .face(Direction.NORTH).uvs(0f, 0f, 16f, 16f).texture("#portal").end()
                .face(Direction.SOUTH).uvs(0f, 0f, 16f, 16f).texture("#portal").end()
                .end();

        // This sets up the variants for X and Z axis
        getVariantBuilder(block).partialState()
                .with(RileyModFallowPortalBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(portalModel).addModel()
                .partialState()
                .with(RileyModFallowPortalBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(portalModel).rotationY(90).addModel();
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
                .with(RileyModAbyssPortalBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(portalModel).addModel()
                .partialState()
                .with(RileyModAbyssPortalBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(portalModel).rotationY(90).addModel();
    }
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),ResourceLocation.fromNamespaceAndPath("minecraft","block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }
    private void topTexturedBlock(RegistryObject<Block> blockRegistryObject, String sideAndBottomTexture, String topTexture) {
        ModelFile model = models().cubeBottomTop(
                blockRegistryObject.getId().getPath(),
                modLoc("block/" + sideAndBottomTexture), // sides
                modLoc("block/" + sideAndBottomTexture), // bottom (same as default)
                modLoc("block/" + topTexture)            // top
        );
        simpleBlockWithItem(blockRegistryObject.get(), model);
    }
    private void topBottomTexturedBlock(RegistryObject<Block> blockRegistryObject, String sideTexture, String topAndBottomTexture) {
        ModelFile model = models().cubeBottomTop(
                blockRegistryObject.getId().getPath(),
                modLoc("block/" + sideTexture), // sides
                modLoc("block/" + topAndBottomTexture), // bottom (same as default)
                modLoc("block/" + topAndBottomTexture)            // top
        );
        simpleBlockWithItem(blockRegistryObject.get(), model);
    }
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(RileyMod.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
    private void abyssalGrassBlock(RegistryObject<Block> blockRegistryObject) {
        ModelFile abyssalGrassModel = models().cubeBottomTop(blockRegistryObject.getId().getPath(),
                modLoc("block/abyssal_grass_side"),  // Side texture
                modLoc("block/abyssal_dirt"),        // Bottom texture
                modLoc("block/abyssal_grass_top"));  // Top texture

        simpleBlockWithItem(blockRegistryObject.get(), abyssalGrassModel);
    }
    private void machineCoreCenterBlock(RegistryObject<Block> blockRegistryObject, String normalTexture, String formedModelName) {
        ModelFile normalModel = models().cubeAll(
                blockRegistryObject.getId().getPath(),
                modLoc("block/" + normalTexture)
        );

        ModelFile formedModel = new ModelFile.UncheckedModelFile(
                modLoc("block/" + formedModelName)
        );

        getVariantBuilder(blockRegistryObject.get())
                .partialState()
                .with(MachineCorePartBlock.FORMED, false)
                .modelForState()
                .modelFile(normalModel)
                .addModel()
                .partialState()
                .with(MachineCorePartBlock.FORMED, true)
                .modelForState()
                .modelFile(formedModel)
                .addModel();

        simpleBlockItem(blockRegistryObject.get(), normalModel);
    }
    private void machineCoreTopBottomPartBlock(RegistryObject<Block> blockRegistryObject, String sideTexture, String topAndBottomTexture) {
        ModelFile normalModel = models().cubeBottomTop(
                blockRegistryObject.getId().getPath(),
                modLoc("block/" + sideTexture),
                modLoc("block/" + topAndBottomTexture),
                modLoc("block/" + topAndBottomTexture)
        );

        machineCorePartBlockWithModel(blockRegistryObject, normalModel, sideTexture);
    }

    private void machineCorePartBlock(RegistryObject<Block> blockRegistryObject, String normalTexture) {
        ModelFile normalModel = models().cubeAll(
                blockRegistryObject.getId().getPath(),
                modLoc("block/" + normalTexture)
        );

        machineCorePartBlockWithModel(blockRegistryObject, normalModel, normalTexture);
    }

    private void machineCorePartBlockWithModel(RegistryObject<Block> blockRegistryObject, ModelFile normalModel, String particleTexture) {
        ModelFile invisibleModel = models()
                .withExistingParent(blockRegistryObject.getId().getPath() + "_invisible", mcLoc("block/air"))
                .texture("particle", modLoc("block/" + particleTexture));

        getVariantBuilder(blockRegistryObject.get())
                .partialState()
                .with(MachineCorePartBlock.FORMED, false)
                .modelForState()
                .modelFile(normalModel)
                .addModel()
                .partialState()
                .with(MachineCorePartBlock.FORMED, true)
                .modelForState()
                .modelFile(invisibleModel)
                .addModel();

        simpleBlockItem(blockRegistryObject.get(), normalModel);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}