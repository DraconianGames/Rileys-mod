package net.riley.riley_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.loot.AddItemModifier;



public class RileyModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public RileyModGlobalLootModifiersProvider(PackOutput output) {
        super(output, RileyMod.MODID);
    }

    @Override
    protected void start() {
      /*  add("pine_cone_from_grass", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.35f).build()}, RileyModItems.SYNTHETIC_MUSCLE.get()));

        add("pine_cone_from_creeper", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build() }, RileyModItems.SYNTHETIC_MUSCLE.get()));

        add("metal_detector_from_jungle_temples", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build() }, RileyModItems.SYNTHETIC_MUSCLE.get()));
        */
        add("soul_from_creeper", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.CREEPER_SOUL.get()));
    }
}
