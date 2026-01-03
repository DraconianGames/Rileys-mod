package net.riley.riley_mod.datagen;

import net.riley.riley_mod.datagen.loot.RileyModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.riley.riley_mod.datagen.loot.RileyModEntityLootTables;
import net.riley.riley_mod.datagen.loot.RileyModStructureLootTables;


import java.util.List;
import java.util.Set;

public class RileyModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(RileyModBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(RileyModEntityLootTables::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(RileyModStructureLootTables::new, LootContextParamSets.CHEST)
        ));
    }
}