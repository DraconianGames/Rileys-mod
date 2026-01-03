package net.riley.riley_mod.datagen;

import net.riley.riley_mod.datagen.loot.RileyModBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.riley.riley_mod.datagen.loot.RileyModEntityLootTables;

import java.util.List;
import java.util.Set;

public class RileyModLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(RileyModBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}