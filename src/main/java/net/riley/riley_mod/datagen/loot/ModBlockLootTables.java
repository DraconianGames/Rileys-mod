package net.riley.riley_mod.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.block.ModBlocks;
import net.riley.riley_mod.item.Moditems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.FUNTIUM_BLOCK.get());
        this.dropSelf(ModBlocks.ACTIVACTED_FUNTIUM.get());
        this.dropSelf(ModBlocks.ABYSS_LOG.get());
        this.dropSelf(ModBlocks.ABYSS_PLANKS.get());
        this.dropSelf(ModBlocks.ABYSS_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ABYSS_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ABYSS_LOG.get());
        this.dropSelf(ModBlocks.ABYSS_SAPLING.get());
        this.add(ModBlocks.FUNTIUM_ORE_BLOCK.get(),
                block -> createCopperLikeOreDrops(ModBlocks.FUNTIUM_ORE_BLOCK.get(), Moditems.FUNTIUM_ORE.get()));
        this.add(ModBlocks.DEEPSLATE_FUNTIUM_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_FUNTIUM_ORE.get(), Moditems.FUNTIUM_ORE.get()));
        this.add(ModBlocks.ABYSS_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ABYSS_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}