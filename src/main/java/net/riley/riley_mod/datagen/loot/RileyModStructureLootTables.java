package net.riley.riley_mod.datagen.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;

import java.util.function.BiConsumer;

public class RileyModStructureLootTables implements LootTableSubProvider {

    public static final ResourceLocation ARENA_CHEST = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "chests/arena");

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(ARENA_CHEST, LootTable.lootTable()
                // Always include 1 Eye
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.EYE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                )
                // Always include 1 Caged Fairy
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.CAGGED_FAIRY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.SADDLE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                )
                // 50% chance to spawn one of: Diamonds, Emeralds, or Ice Chunks (5-12 of them)
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(LootItemRandomChanceCondition.randomChance(0.5f)) // adjust probability as desired
                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F)))
                                .setWeight(1))
                        .add(LootItem.lootTableItem(Items.EMERALD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F)))
                                .setWeight(1))
                        .add(LootItem.lootTableItem(RileyModItems.ICE_CHUNK.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 12.0F)))
                                .setWeight(1))
                )
        );
    }
}