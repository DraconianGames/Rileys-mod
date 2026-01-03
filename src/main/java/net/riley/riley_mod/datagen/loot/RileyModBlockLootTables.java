package net.riley.riley_mod.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.item.RileyModItems;

import java.util.Set;

public class RileyModBlockLootTables extends BlockLootSubProvider {
    public RileyModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(RileyModBlocks.FUNTIUM_BLOCK.get());
        this.dropSelf(RileyModBlocks.ACTIVACTED_FUNTIUM.get());
        this.dropSelf(RileyModBlocks.ABYSS_LOG.get());
        this.dropSelf(RileyModBlocks.ABYSS_PLANKS.get());
        this.dropSelf(RileyModBlocks.ABYSS_WOOD.get());
        this.dropSelf(RileyModBlocks.STRIPPED_ABYSS_WOOD.get());
        this.dropSelf(RileyModBlocks.STRIPPED_ABYSS_LOG.get());
        this.dropSelf(RileyModBlocks.ABYSS_SAPLING.get());
        this.add(RileyModBlocks.FUNTIUM_ORE_BLOCK.get(),
                block -> createCopperLikeOreDrops(RileyModBlocks.FUNTIUM_ORE_BLOCK.get(), RileyModItems.FUNTIUM_ORE.get()));
        this.add(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE.get(),
                block -> createCopperLikeOreDrops(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE.get(), RileyModItems.FUNTIUM_ORE.get()));
        this.add(RileyModBlocks.ABYSS_LEAVES.get(), block ->
                createLeavesDrops(block, RileyModBlocks.ABYSS_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.dropSelf(RileyModBlocks.ABYSS_WOOD_STAIRS.get());
        this.dropSelf(RileyModBlocks.ABYSS_WOOD_FENCE.get());
        this.dropSelf(RileyModBlocks.ABYSS_WOOD_FENCE_GATE.get());
        this.dropSelf(RileyModBlocks.ABYSS_WOOD_WALL.get());

        this.dropSelf(RileyModBlocks.STRUCTURE_BRICK_STAIRS.get());
        this.dropSelf(RileyModBlocks.STRUCTURE_BRICK_FENCE.get());
        this.dropSelf(RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE.get());
        this.dropSelf(RileyModBlocks.STRUCTURE_BRICK_WALL.get());
        this.dropSelf(RileyModBlocks.STRUCTURE_BRICK.get());

        this.add(RileyModBlocks.ABYSS_WOOD_SLAB.get(),
                block -> createSlabItemTable(RileyModBlocks.ABYSS_WOOD_SLAB.get()));
        this.add(RileyModBlocks.STRUCTURE_BRICK_SLAB.get(),
                block -> createSlabItemTable(RileyModBlocks.STRUCTURE_BRICK_SLAB.get()));
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
        return RileyModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}