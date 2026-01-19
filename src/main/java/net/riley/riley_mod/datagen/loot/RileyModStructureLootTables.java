package net.riley.riley_mod.datagen.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;

import java.util.function.BiConsumer;

public class RileyModStructureLootTables implements LootTableSubProvider {

    public static final ResourceLocation ARENA_CHEST = new ResourceLocation(RileyMod.MODID, "chests/arena");

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(ARENA_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(RileyModItems.EYE.get()))
                        .add(LootItem.lootTableItem(RileyModItems.FUNTIUM_ORE.get()))
                )
        );
    }


}
