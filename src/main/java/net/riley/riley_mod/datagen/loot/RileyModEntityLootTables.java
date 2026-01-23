package net.riley.riley_mod.datagen.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.RileyModItems;

import java.util.stream.Stream;

public class RileyModEntityLootTables extends EntityLootSubProvider {
    public RileyModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }
    @Override
    public void generate() {
        this.add(RileyModEntities.RAPTER.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));

        this.add(RileyModEntities.SUNLESS_CRAB.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.CLAW.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))
                ));
       //change later
        this.add(RileyModEntities.NIGHT_TERROR.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.5F))))
                ));

        this.add(RileyModEntities.WHALE_HUNTER.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.5F))))
                        .add(LootItem.lootTableItem(RileyModBlocks.WHALE_HUNTER_TROPHY.get())
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)) // 5% chance to drop
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                ));
        this.add(RileyModEntities.FROST_HOPPER.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.ICE_CHUNK.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 1.5F))))
                ));
        this.add(RileyModEntities.TOOTHFAIRY.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));
        this.add(RileyModEntities.BONEFAIRY.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));
        this.add(RileyModEntities.SKELETONFAIRY.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.SKELETON_SKULL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));
        this.add(RileyModEntities.SKULL_FAIRY.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.TOOTH.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.SKELETON_SKULL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));
        this.add(RileyModEntities.BISON.get(),
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.LEATHER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.BEEF)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(1.0F, 2.0F))))));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return RileyModEntities.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}
