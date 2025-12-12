package net.riley.riley_mod.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        blockWithItem(ModBlocks.FUNTIUM_BLOCK);
        blockWithItem(ModBlocks.ACTIVACTED_FUNTIUM);
        blockWithItem(ModBlocks.ABYSS_LEAVES);
        blockWithItem(ModBlocks.ABYSS_LOG);
        blockWithItem(ModBlocks.ABYSS_PLANKS);
        blockWithItem(ModBlocks.ABYSS_WOOD);
        blockWithItem(ModBlocks.STRIPPED_ABYSS_WOOD);
        blockWithItem(ModBlocks.STRIPPED_ABYSS_LOG);
        blockWithItem(ModBlocks.ABYSS_SAPLING);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}