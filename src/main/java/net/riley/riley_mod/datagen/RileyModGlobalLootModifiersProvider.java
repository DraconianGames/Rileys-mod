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
        add("soul_from_bat", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/bat")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.BAT_SOUL.get()));
        add("soul_from_allay", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/allay")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ALLAY_SOUL.get()));
        add("soul_from_axolotl", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/axolotl")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.AXOLOTL_SOUL.get()));
        add("soul_from_bee", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/bee")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.BEE_SOUL.get()));
        add("soul_from_blaze", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/blaze")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.BLAZE_SOUL.get()));
        add("soul_from_cat", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cat")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.CAT_SOUL.get()));
        add("soul_from_camel", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/camel")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.CAMEL_SOUL.get()));
        add("soul_from_cave_spider", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cave_spider")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.CAVE_SPIDER_SOUL.get()));
        add("soul_from_chicken", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/chicken")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.CHICKEN_SOUL.get()));
        add("soul_from_cod", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cod")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.COD_SOUL.get()));
        add("soul_from_cow", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/cow")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.COW_SOUL.get()));
        add("soul_from_dolphin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/dolphin")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.DOLPHIN_SOUL.get()));
        add("soul_from_donkey", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/donkey")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.DONKEY_SOUL.get()));
        add("soul_from_drowned", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/drowned")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.DROWNED_SOUL.get()));
        add("soul_from_elder_guardian", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/elder_guardian")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ELDER_GUARDIAN_SOUL.get()));
        add("soul_from_enderman", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/enderman")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ENDERMAN_SOUL.get()));
        add("soul_from_endermite", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/endermite")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ENDERMITE_SOUL.get()));
        add("soul_from_evoker", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/evoker")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.EVOKER_SOUL.get()));
        add("soul_from_fox", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/fox")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.FOX_SOUL.get()));
        add("soul_from_frog", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/frog")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.FROG_SOUL.get()));
        add("soul_from_ghast", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ghast")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.GHAST_SOUL.get()));
        add("soul_from_glow_squid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/glow_squid")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.GLOW_SQUID_SOUL.get()));
        add("soul_from_goat", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/goat")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.GOAT_SOUL.get()));
        add("soul_from_guardian", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/guardian")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.GUARDIAN_SOUL.get()));
        add("soul_from_hoglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/hoglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.HOGLIN_SOUL.get()));
        add("soul_from_horse", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/horse")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.HORSE_SOUL.get()));
        add("soul_from_husk", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/husk")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.HUSK_SOUL.get()));
        add("soul_from_iron_golem", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/iron_golem")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.IRON_GOLEM_SOUL.get()));
        add("soul_from_llama", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/llama")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.LLAMA_SOUL.get()));
        add("soul_from_magma_cube", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/magma_cube")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.MAGMA_CUBE_SOUL.get()));
        add("soul_from_mooshroom", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/mooshroom")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.MOOSHROOM_SOUL.get()));
        add("soul_from_mule", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/mule")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.MULE_SOUL.get()));
        add("soul_from_ocelot", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ocelot")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.OCELOT_SOUL.get()));
        add("soul_from_panda", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/panda")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PANDA_SOUL.get()));
        add("soul_from_parrot", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/parrot")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PARROT_SOUL.get()));
        add("soul_from_phantom", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/phantom")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PHANTOM_SOUL.get()));
        add("soul_from_pig", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/pig")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PIG_SOUL.get()));
        add("soul_from_piglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/piglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PIGLIN_SOUL.get()));
        add("soul_from_piglin_brute", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/piglin_brute")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PIGLIN_BRUTE_SOUL.get()));
        add("soul_from_pillager", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/pillager")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PILLAGER_SOUL.get()));
        add("soul_from_polar_bear", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/polar_bear")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.POLAR_BEAR_SOUL.get()));
        add("soul_from_pufferfish", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/pufferfish")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.PUFFERFISH_SOUL.get()));
        add("soul_from_rabbit", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/rabbit")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.RABBIT_SOUL.get()));
        add("soul_from_ravager", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ravager")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.RAVAGER_SOUL.get()));
        add("soul_from_salmon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/salmon")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SALMON_SOUL.get()));
        add("soul_from_sheep", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/sheep")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SHEEP_SOUL.get()));
        add("soul_from_shulker", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/shulker")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SHULKER_SOUL.get()));
        add("soul_from_silverfish", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/silverfish")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SILVERFISH_SOUL.get()));
        add("soul_from_skeleton", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/skeleton")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SKELETON_SOUL.get()));
        add("soul_from_skeleton_horse", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/skeleton_horse")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SKELETON_HORSE_SOUL.get()));
        add("soul_from_slime", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/slime")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SLIME_SOUL.get()));
        add("soul_from_sniffer", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/sniffer")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SNIFFER_SOUL.get()));
        add("soul_from_snow_golem", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/snow_golem")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SNOW_GOLEM_SOUL.get()));
        add("soul_from_spider", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/spider")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SPIDER_SOUL.get()));
        add("soul_from_squid", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/squid")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.SQUID_SOUL.get()));
        add("soul_from_stray", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/stray")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.STRAY_SOUL.get()));
        add("soul_from_strider", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/strider")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.STRIDER_SOUL.get()));
        add("soul_from_tadpole", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/tadpole")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.TADPOLE_SOUL.get()));
        add("soul_from_trader_llama", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/trader_llama")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.TRADER_LLAMA_SOUL.get()));
        add("soul_from_tropical_fish", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/tropical_fish")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.TROPICAL_FISH_SOUL.get()));
        add("soul_from_turtle", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/turtle")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.TURTLE_SOUL.get()));
        add("soul_from_vex", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/vex")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.VEX_SOUL.get()));
        add("soul_from_villager", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/villager")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.VILLAGER_SOUL.get()));
        add("soul_from_vindicator", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/vindicator")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.VINDICATOR_SOUL.get()));
        add("soul_from_wandering_trader", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wandering_trader")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WANDERING_TRADER_SOUL.get()));
        add("soul_from_warden", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/warden")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WARDEN_SOUL.get()));
        add("soul_from_witch", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/witch")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WITCH_SOUL.get()));
        add("soul_from_wither", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WITHER_SOUL.get()));
        add("soul_from_wither_skeleton", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wither_skeleton")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WITHER_SKELETON_SOUL.get()));
        add("soul_from_wolf", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wolf")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.WOLF_SOUL.get()));
        add("soul_from_zoglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zoglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ZOGLIN_SOUL.get()));
        add("soul_from_zombie", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombie")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ZOMBIE_SOUL.get()));
        add("soul_from_zombie_horse", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombie_horse")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ZOMBIE_HORSE_SOUL.get()));
        add("soul_from_zombie_villager", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombie_villager")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ZOMBIE_VILLAGER_SOUL.get()));
        add("soul_from_zombified_piglin", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/zombified_piglin")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ZOMBIFIED_PIGLIN_SOUL.get()));
        add("soul_from_ender_dragon", new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("entities/ender_dragon")).build(),
                LootItemRandomChanceCondition.randomChance(0.05f).build()
        }, RileyModItems.ENDER_DRAGON_SOUL.get()));
    }
}
