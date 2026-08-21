package net.riley.riley_mod.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.MuscleCropBlock;
import net.riley.riley_mod.item.RileyModItems;

import java.util.Set;

public class RileyModBlockLootTables extends BlockLootSubProvider {
    public RileyModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(RileyModBlocks.FUNTIUM_BLOCK.get());
        this.dropSelf(RileyModBlocks.FALLOW_GROUND.get());
        this.dropSelf(RileyModBlocks.FALLOW_EARTH.get());
        this.dropSelf(RileyModBlocks.FALLOW_PORTAL_FRAME.get());
        this.dropSelf(RileyModBlocks.ACTIVACTED_FUNTIUM.get());
        this.dropSelf(RileyModBlocks.ABYSS_LOG.get());
        this.dropSelf(RileyModBlocks.ABYSS_PLANKS.get());
        this.dropSelf(RileyModBlocks.ABYSS_WOOD.get());
        this.dropSelf(RileyModBlocks.STRIPPED_ABYSS_WOOD.get());
        this.dropSelf(RileyModBlocks.STRIPPED_ABYSS_LOG.get());
        this.dropSelf(RileyModBlocks.ABYSS_SAPLING.get());
        this.dropSelf(RileyModBlocks.ABYSSAL_COBBLESTONE.get());
        this.dropSelf(RileyModBlocks.ABYSSAL_DIRT.get());
        this.dropSelf(RileyModBlocks.BLACK_SAND.get());
        this.dropSelf(RileyModBlocks.WHALE_HUNTER_TROPHY.get());
        this.dropSelf(RileyModBlocks.ENCHANTER.get());
        this.dropSelf(RileyModBlocks.AUGMENTATION_STATION.get());
        this.dropSelf(RileyModBlocks.TROPHY_READER.get());
        this.dropSelf(RileyModBlocks.NIGHT_STAR.get());
        this.dropSelf(RileyModBlocks.SPECIAL_SPAWNER.get());
        this.dropSelf(RileyModBlocks.CABLE.get());
        this.dropSelf(RileyModBlocks.MACHINE_CORE.get());
        this.dropSelf(RileyModBlocks.MACHINE_CORE_CENTER.get());
        this.dropSelf(RileyModBlocks.MACHINE_CORE_PORT.get());
        this.dropSelf(RileyModBlocks.MACHINE_CORE_SCREEN.get());
        this.dropSelf(RileyModBlocks.SHUT_OFF.get());
        this.dropSelf(RileyModBlocks.TOGGLE_SWITCH.get());
        this.dropSelf(RileyModBlocks.TROPHY_BAT.get());
        this.dropSelf(RileyModBlocks.TROPHY_ALLAY.get());
        this.dropSelf(RileyModBlocks.TROPHY_AXOLOTL.get());
        this.dropSelf(RileyModBlocks.TROPHY_BEE.get());
        this.dropSelf(RileyModBlocks.TROPHY_BLAZE.get());
        this.dropSelf(RileyModBlocks.TROPHY_CAT.get());
        this.dropSelf(RileyModBlocks.TROPHY_CAMEL.get());
        this.dropSelf(RileyModBlocks.TROPHY_CAVE_SPIDER.get());
        this.dropSelf(RileyModBlocks.TROPHY_CHICKEN.get());
        this.dropSelf(RileyModBlocks.TROPHY_COD.get());
        this.dropSelf(RileyModBlocks.TROPHY_COW.get());
        this.dropSelf(RileyModBlocks.TROPHY_CREEPER.get());
        this.dropSelf(RileyModBlocks.TROPHY_DOLPHIN.get());
        this.dropSelf(RileyModBlocks.TROPHY_DONKEY.get());
        this.dropSelf(RileyModBlocks.TROPHY_DROWNED.get());
        this.dropSelf(RileyModBlocks.TROPHY_ELDER_GUARDIAN.get());
        this.dropSelf(RileyModBlocks.TROPHY_ENDERMAN.get());
        this.dropSelf(RileyModBlocks.TROPHY_ENDERMITE.get());
        this.dropSelf(RileyModBlocks.TROPHY_EVOKER.get());
        this.dropSelf(RileyModBlocks.TROPHY_FOX.get());
        this.dropSelf(RileyModBlocks.TROPHY_FROG.get());
        this.dropSelf(RileyModBlocks.TROPHY_GHAST.get());
        this.dropSelf(RileyModBlocks.TROPHY_GLOW_SQUID.get());
        this.dropSelf(RileyModBlocks.TROPHY_GOAT.get());
        this.dropSelf(RileyModBlocks.TROPHY_GUARDIAN.get());
        this.dropSelf(RileyModBlocks.TROPHY_HOGLIN.get());
        this.dropSelf(RileyModBlocks.TROPHY_HORSE.get());
        this.dropSelf(RileyModBlocks.TROPHY_HUSK.get());
        this.dropSelf(RileyModBlocks.TROPHY_IRON_GOLEM.get());
        this.dropSelf(RileyModBlocks.TROPHY_LLAMA.get());
        this.dropSelf(RileyModBlocks.TROPHY_MAGMA_CUBE.get());
        this.dropSelf(RileyModBlocks.TROPHY_MOOSHROOM.get());
        this.dropSelf(RileyModBlocks.TROPHY_MULE.get());
        this.dropSelf(RileyModBlocks.TROPHY_OCELOT.get());
        this.dropSelf(RileyModBlocks.TROPHY_PANDA.get());
        this.dropSelf(RileyModBlocks.TROPHY_PARROT.get());
        this.dropSelf(RileyModBlocks.TROPHY_PHANTOM.get());
        this.dropSelf(RileyModBlocks.TROPHY_PIG.get());
        this.dropSelf(RileyModBlocks.TROPHY_PIGLIN.get());
        this.dropSelf(RileyModBlocks.TROPHY_PIGLIN_BRUTE.get());
        this.dropSelf(RileyModBlocks.TROPHY_PILLAGER.get());
        this.dropSelf(RileyModBlocks.TROPHY_POLAR_BEAR.get());
        this.dropSelf(RileyModBlocks.TROPHY_PUFFERFISH.get());
        this.dropSelf(RileyModBlocks.TROPHY_RABBIT.get());
        this.dropSelf(RileyModBlocks.TROPHY_RAVAGER.get());
        this.dropSelf(RileyModBlocks.TROPHY_SALMON.get());
        this.dropSelf(RileyModBlocks.TROPHY_SHEEP.get());
        this.dropSelf(RileyModBlocks.TROPHY_SHULKER.get());
        this.dropSelf(RileyModBlocks.TROPHY_SILVERFISH.get());
        this.dropSelf(RileyModBlocks.TROPHY_SKELETON.get());
        this.dropSelf(RileyModBlocks.TROPHY_SKELETON_HORSE.get());
        this.dropSelf(RileyModBlocks.TROPHY_SLIME.get());
        this.dropSelf(RileyModBlocks.TROPHY_SNIFFER.get());
        this.dropSelf(RileyModBlocks.TROPHY_SNOW_GOLEM.get());
        this.dropSelf(RileyModBlocks.TROPHY_SPIDER.get());
        this.dropSelf(RileyModBlocks.TROPHY_SQUID.get());
        this.dropSelf(RileyModBlocks.TROPHY_STRAY.get());
        this.dropSelf(RileyModBlocks.TROPHY_STRIDER.get());
        this.dropSelf(RileyModBlocks.TROPHY_TADPOLE.get());
        this.dropSelf(RileyModBlocks.TROPHY_TRADER_LLAMA.get());
        this.dropSelf(RileyModBlocks.TROPHY_TROPICAL_FISH.get());
        this.dropSelf(RileyModBlocks.TROPHY_TURTLE.get());
        this.dropSelf(RileyModBlocks.TROPHY_VEX.get());
        this.dropSelf(RileyModBlocks.TROPHY_VILLAGER.get());
        this.dropSelf(RileyModBlocks.TROPHY_VINDICATOR.get());
        this.dropSelf(RileyModBlocks.TROPHY_WANDERING_TRADER.get());
        this.dropSelf(RileyModBlocks.TROPHY_WARDEN.get());
        this.dropSelf(RileyModBlocks.TROPHY_WITCH.get());
        this.dropSelf(RileyModBlocks.TROPHY_WITHER.get());
        this.dropSelf(RileyModBlocks.TROPHY_WITHER_SKELETON.get());
        this.dropSelf(RileyModBlocks.TROPHY_WOLF.get());
        this.dropSelf(RileyModBlocks.TROPHY_ZOGLIN.get());
        this.dropSelf(RileyModBlocks.TROPHY_ZOMBIE.get());
        this.dropSelf(RileyModBlocks.TROPHY_ZOMBIE_HORSE.get());
        this.dropSelf(RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get());
        this.dropSelf(RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get());
        this.dropSelf(RileyModBlocks.TROPHY_ENDER_DRAGON.get());

        this.add(RileyModBlocks.BLUESTONE_WIRE.get(),
                block -> createSingleItemTableWithSilkTouch(block, RileyModItems.BLUESTONE.get()));


        this.add(RileyModBlocks.FUNTIUM_ORE_BLOCK.get(),
                block -> createCopperLikeOreDrops(RileyModBlocks.FUNTIUM_ORE_BLOCK.get(), RileyModItems.FUNTIUM_ORE.get()));
        this.add(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE.get(),
                block -> createCopperLikeOreDrops(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE.get(), RileyModItems.FUNTIUM_ORE.get()));
        this.add(RileyModBlocks.BLUESTONE_ORE.get(),
                block -> createCopperLikeOreDrops(RileyModBlocks.BLUESTONE_ORE.get(), RileyModItems.BLUESTONE.get()));
        this.add(RileyModBlocks.ABYSS_LEAVES.get(), block ->
                createLeavesDrops(block, RileyModBlocks.ABYSS_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.add(RileyModBlocks.ABYSSAL_GRASS.get(),
                block -> createSingleItemTableWithSilkTouch(block, RileyModBlocks.ABYSSAL_DIRT.get()));

        this.add(RileyModBlocks.ABYSSAL_STONE.get(), block ->
                createSingleItemTableWithSilkTouch(block, RileyModBlocks.ABYSSAL_COBBLESTONE.get()));


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

        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(RileyModBlocks.MUSCLE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MuscleCropBlock.AGE, 3));

        this.add(RileyModBlocks.MUSCLE_CROP.get(), createCropDrops(RileyModBlocks.MUSCLE_CROP.get(), RileyModItems.SYNTHETIC_MUSCLE.get(),RileyModItems.SYNTHETIC_MUSCLE.get(),
                 lootitemcondition$builder));
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
//Figure out entity loot tables