package net.riley.riley_mod.item;


import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.custom.*;


public class RileyModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RileyMod.MODID);

    public static final RegistryObject<Item> STARDUST = ITEMS.register("stardust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject <Item> FUNTIUM_MIX = ITEMS.register("funtium_mix",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_PLATE = ITEMS.register("funtium_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_ORE = ITEMS.register("funtium_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LAMP_HOOK = ITEMS.register("lamp_hook",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_FORGE = ITEMS.register("soul_forge",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRISON_CART_COVER = ITEMS.register("trison_cart_cover",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRISON_CART_ITEM = ITEMS.register("trison_cart_item",
            () -> new TrisonCartItem(new Item.Properties()));
    public static final RegistryObject<Item> UNASSEMBLED_TRISON_CART = ITEMS.register("unassembled_trison_cart",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WRECKER_UPGRADE = ITEMS.register("wrecker_upgrade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CARGO_UPGRADE = ITEMS.register("cargo_upgrade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_UPGRADE = ITEMS.register("armor_upgrade",
            () -> new Item(new Item.Properties()));
  //souls
    //TODO make the items unkillable, like how lava don't destroy the netheright sword
    public static final RegistryObject<Item> SOUL = ITEMS.register("soul",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CREEPER_SOUL = ITEMS.register("creeper_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.CREEPER, new Item.Properties()));
    public static final RegistryObject<Item> BAT_SOUL = ITEMS.register("bat_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.BAT, new Item.Properties()));
    public static final RegistryObject<Item> ALLAY_SOUL = ITEMS.register("allay_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ALLAY, new Item.Properties()));
    public static final RegistryObject<Item> AXOLOTL_SOUL = ITEMS.register("axolotl_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.AXOLOTL, new Item.Properties()));
    public static final RegistryObject<Item> BEE_SOUL = ITEMS.register("bee_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.BEE, new Item.Properties()));
    public static final RegistryObject<Item> BLAZE_SOUL = ITEMS.register("blaze_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.BLAZE, new Item.Properties()));
    public static final RegistryObject<Item> CAT_SOUL = ITEMS.register("cat_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.CAT, new Item.Properties()));
    public static final RegistryObject<Item> CAMEL_SOUL = ITEMS.register("camel_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.CAMEL, new Item.Properties()));
    public static final RegistryObject<Item> CAVE_SPIDER_SOUL = ITEMS.register("cave_spider_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.CAVE_SPIDER, new Item.Properties()));
    public static final RegistryObject<Item> CHICKEN_SOUL = ITEMS.register("chicken_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.CHICKEN, new Item.Properties()));
    public static final RegistryObject<Item> COD_SOUL = ITEMS.register("cod_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.COD, new Item.Properties()));
    public static final RegistryObject<Item> COW_SOUL = ITEMS.register("cow_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.COW, new Item.Properties()));
    public static final RegistryObject<Item> DOLPHIN_SOUL = ITEMS.register("dolphin_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.DOLPHIN, new Item.Properties()));
    public static final RegistryObject<Item> DONKEY_SOUL = ITEMS.register("donkey_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.DONKEY, new Item.Properties()));
    public static final RegistryObject<Item> DROWNED_SOUL = ITEMS.register("drowned_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.DROWNED, new Item.Properties()));
    public static final RegistryObject<Item> ELDER_GUARDIAN_SOUL = ITEMS.register("elder_guardian_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ELDER_GUARDIAN, new Item.Properties()));
    public static final RegistryObject<Item> ENDERMAN_SOUL = ITEMS.register("enderman_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ENDERMAN, new Item.Properties()));
    public static final RegistryObject<Item> ENDERMITE_SOUL = ITEMS.register("endermite_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ENDERMITE, new Item.Properties()));
    public static final RegistryObject<Item> EVOKER_SOUL = ITEMS.register("evoker_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.EVOKER, new Item.Properties()));
    public static final RegistryObject<Item> FOX_SOUL = ITEMS.register("fox_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.FOX, new Item.Properties()));
    public static final RegistryObject<Item> FROG_SOUL = ITEMS.register("frog_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.FROG, new Item.Properties()));
    public static final RegistryObject<Item> GHAST_SOUL = ITEMS.register("ghast_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.GHAST, new Item.Properties()));
    public static final RegistryObject<Item> GLOW_SQUID_SOUL = ITEMS.register("glow_squid_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.GLOW_SQUID, new Item.Properties()));
    public static final RegistryObject<Item> GOAT_SOUL = ITEMS.register("goat_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.GOAT, new Item.Properties()));
    public static final RegistryObject<Item> GUARDIAN_SOUL = ITEMS.register("guardian_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.GUARDIAN, new Item.Properties()));
    public static final RegistryObject<Item> HOGLIN_SOUL = ITEMS.register("hoglin_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.HOGLIN, new Item.Properties()));
    public static final RegistryObject<Item> HORSE_SOUL = ITEMS.register("horse_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.HORSE, new Item.Properties()));
    public static final RegistryObject<Item> HUSK_SOUL = ITEMS.register("husk_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.HUSK, new Item.Properties()));
    public static final RegistryObject<Item> IRON_GOLEM_SOUL = ITEMS.register("iron_golem_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.IRON_GOLEM, new Item.Properties()));
    public static final RegistryObject<Item> LLAMA_SOUL = ITEMS.register("llama_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.LLAMA, new Item.Properties()));
    public static final RegistryObject<Item> MAGMA_CUBE_SOUL = ITEMS.register("magma_cube_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.MAGMA_CUBE, new Item.Properties()));
    public static final RegistryObject<Item> MOOSHROOM_SOUL = ITEMS.register("mooshroom_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.MOOSHROOM, new Item.Properties()));
    public static final RegistryObject<Item> MULE_SOUL = ITEMS.register("mule_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.MULE, new Item.Properties()));
    public static final RegistryObject<Item> OCELOT_SOUL = ITEMS.register("ocelot_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.OCELOT, new Item.Properties()));
    public static final RegistryObject<Item> PANDA_SOUL = ITEMS.register("panda_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PANDA, new Item.Properties()));
    public static final RegistryObject<Item> PARROT_SOUL = ITEMS.register("parrot_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PARROT, new Item.Properties()));
    public static final RegistryObject<Item> PHANTOM_SOUL = ITEMS.register("phantom_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PHANTOM, new Item.Properties()));
    public static final RegistryObject<Item> PIG_SOUL = ITEMS.register("pig_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PIG, new Item.Properties()));
    public static final RegistryObject<Item> PIGLIN_SOUL = ITEMS.register("piglin_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PIGLIN, new Item.Properties()));
    public static final RegistryObject<Item> PIGLIN_BRUTE_SOUL = ITEMS.register("piglin_brute_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PIGLIN_BRUTE, new Item.Properties()));
    public static final RegistryObject<Item> PILLAGER_SOUL = ITEMS.register("pillager_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PILLAGER, new Item.Properties()));
    public static final RegistryObject<Item> POLAR_BEAR_SOUL = ITEMS.register("polar_bear_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.POLAR_BEAR, new Item.Properties()));
    public static final RegistryObject<Item> PUFFERFISH_SOUL = ITEMS.register("pufferfish_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.PUFFERFISH, new Item.Properties()));
    public static final RegistryObject<Item> RABBIT_SOUL = ITEMS.register("rabbit_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.RABBIT, new Item.Properties()));
    public static final RegistryObject<Item> RAVAGER_SOUL = ITEMS.register("ravager_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.RAVAGER, new Item.Properties()));
    public static final RegistryObject<Item> SALMON_SOUL = ITEMS.register("salmon_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SALMON, new Item.Properties()));
    public static final RegistryObject<Item> SHEEP_SOUL = ITEMS.register("sheep_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SHEEP, new Item.Properties()));
    public static final RegistryObject<Item> SHULKER_SOUL = ITEMS.register("shulker_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SHULKER, new Item.Properties()));
    public static final RegistryObject<Item> SILVERFISH_SOUL = ITEMS.register("silverfish_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SILVERFISH, new Item.Properties()));
    public static final RegistryObject<Item> SKELETON_SOUL = ITEMS.register("skeleton_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SKELETON, new Item.Properties()));
    public static final RegistryObject<Item> SKELETON_HORSE_SOUL = ITEMS.register("skeleton_horse_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SKELETON_HORSE, new Item.Properties()));
    public static final RegistryObject<Item> SLIME_SOUL = ITEMS.register("slime_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SLIME, new Item.Properties()));
    public static final RegistryObject<Item> SNIFFER_SOUL = ITEMS.register("sniffer_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SNIFFER, new Item.Properties()));
    public static final RegistryObject<Item> SNOW_GOLEM_SOUL = ITEMS.register("snow_golem_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SNOW_GOLEM, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_SOUL = ITEMS.register("spider_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SPIDER, new Item.Properties()));
    public static final RegistryObject<Item> SQUID_SOUL = ITEMS.register("squid_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.SQUID, new Item.Properties()));
    public static final RegistryObject<Item> STRAY_SOUL = ITEMS.register("stray_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.STRAY, new Item.Properties()));
    public static final RegistryObject<Item> STRIDER_SOUL = ITEMS.register("strider_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.STRIDER, new Item.Properties()));
    public static final RegistryObject<Item> TADPOLE_SOUL = ITEMS.register("tadpole_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.TADPOLE, new Item.Properties()));
    public static final RegistryObject<Item> TRADER_LLAMA_SOUL = ITEMS.register("trader_llama_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.TRADER_LLAMA, new Item.Properties()));
    public static final RegistryObject<Item> TROPICAL_FISH_SOUL = ITEMS.register("tropical_fish_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.TROPICAL_FISH, new Item.Properties()));
    public static final RegistryObject<Item> TURTLE_SOUL = ITEMS.register("turtle_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.TURTLE, new Item.Properties()));
    public static final RegistryObject<Item> VEX_SOUL = ITEMS.register("vex_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.VEX, new Item.Properties()));
    public static final RegistryObject<Item> VILLAGER_SOUL = ITEMS.register("villager_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.VILLAGER, new Item.Properties()));
    public static final RegistryObject<Item> VINDICATOR_SOUL = ITEMS.register("vindicator_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.VINDICATOR, new Item.Properties()));
    public static final RegistryObject<Item> WANDERING_TRADER_SOUL = ITEMS.register("wandering_trader_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WANDERING_TRADER, new Item.Properties()));
    public static final RegistryObject<Item> WARDEN_SOUL = ITEMS.register("warden_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WARDEN, new Item.Properties()));
    public static final RegistryObject<Item> WITCH_SOUL = ITEMS.register("witch_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WITCH, new Item.Properties()));
    public static final RegistryObject<Item> WITHER_SOUL = ITEMS.register("wither_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WITHER, new Item.Properties()));
    public static final RegistryObject<Item> WITHER_SKELETON_SOUL = ITEMS.register("wither_skeleton_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WITHER_SKELETON, new Item.Properties()));
    public static final RegistryObject<Item> WOLF_SOUL = ITEMS.register("wolf_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.WOLF, new Item.Properties()));
    public static final RegistryObject<Item> ZOGLIN_SOUL = ITEMS.register("zoglin_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ZOGLIN, new Item.Properties()));
    public static final RegistryObject<Item> ZOMBIE_SOUL = ITEMS.register("zombie_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ZOMBIE, new Item.Properties()));
    public static final RegistryObject<Item> ZOMBIE_HORSE_SOUL = ITEMS.register("zombie_horse_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ZOMBIE_HORSE, new Item.Properties()));
    public static final RegistryObject<Item> ZOMBIE_VILLAGER_SOUL = ITEMS.register("zombie_villager_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ZOMBIE_VILLAGER, new Item.Properties()));
    public static final RegistryObject<Item> ZOMBIFIED_PIGLIN_SOUL = ITEMS.register("zombified_piglin_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ZOMBIFIED_PIGLIN, new Item.Properties()));
    public static final RegistryObject<Item> ENDER_DRAGON_SOUL = ITEMS.register("ender_dragon_soul",
            () -> new SoulItem(net.minecraft.world.entity.EntityType.ENDER_DRAGON, new Item.Properties()));

    public static final RegistryObject<Item> EYE = ITEMS.register("eye",
            () -> new RileyModAbyssPortalItem(new Item.Properties()));
    public static final RegistryObject<Item> TOME = ITEMS.register("tome",
            () -> new RileyModFallowPortalItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TRISON_ARMOR = ITEMS.register("trison_armor",
            () -> new TrisonArmorItem(7, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SOUL_ORB = ITEMS.register("soul_orb",
            () -> new net.riley.riley_mod.item.custom.SoulOrbItem(new Item.Properties()));

    public static final RegistryObject<Item> FILLED_SOUL_ORB = ITEMS.register("filled_soul_orb",
            () -> new net.riley.riley_mod.item.custom.FilledSoulOrbItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ICE_CHUNK = ITEMS.register("ice_chunk",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAW = ITEMS.register("claw",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTH = ITEMS.register("tooth",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FANCY_SKULL = ITEMS.register("fancy_skull",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARTIFICIAL_ORGAN = ITEMS.register("artificial_organ",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIVING_MACHANICAL_NURONS = ITEMS.register("living_machanical_nurons",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LYDAR = ITEMS.register("lydar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHA_REX_UNASSEMBLED_HEAD = ITEMS.register("mecha_rex_unassembled_head",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_BRAIN = ITEMS.register("mecharex_brain",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_EGG = ITEMS.register("mecharex_egg",
            () -> new MecharexEggItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MECHAREX_ENGINE = ITEMS.register("mecharex_engine",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_HEAD = ITEMS.register("mecharex_head",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_LEG = ITEMS.register("mecharex_leg",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_TAIL = ITEMS.register("mecharex_tail",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_TORSO = ITEMS.register("mecharex_torso",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SYNTHETIC_MUSCLE = ITEMS.register("synthetic_muscle",
            () -> new ItemNameBlockItem(RileyModBlocks.MUSCLE_CROP.get(), (new Item.Properties())));

    public static final RegistryObject<Item> BLUESTONE = ITEMS.register("bluestone",
            () -> new ItemNameBlockItem(RileyModBlocks.BLUESTONE_WIRE.get(), (new Item.Properties())));
    //TODO fix not turning off when connected to redstone. fix middle mouse click getting block instead of the item

    public static final RegistryObject<Item> UNASSEMBLED_MECHAREX_TORSO = ITEMS.register("unassembled_mecharex_torso",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNASSEMBLED_MECHAREX_TAIL = ITEMS.register("unassembled_mecharex_tail",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNASSEMBLED_MECHAREX_LEG = ITEMS.register("unassembled_mecharex_leg",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_CANNON = ITEMS.register("mecharex_cannon",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARMOR_PLATING = ITEMS.register("armor_plating",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANNON_SHEILD = ITEMS.register("cannon_sheild",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MECHA_TERROR_EGG = ITEMS.register("mecha_terror_egg",
            () -> new MechaTerrorEggItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PARASITE_CARRIER_EGG = ITEMS.register("parasite_carrier_egg",
            () -> new ParasiteCarrierEggItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> TRUCK = ITEMS.register("truck",
            () -> new TruckItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SPEAR = ITEMS.register("spear",
            () -> new Item(new Item.Properties()));

    //Tools
    public static final RegistryObject<Item> FUNTIUM_SWORD = ITEMS.register("funtium_sword",
            () -> new SwordItem(RileyModToolTiers.FUNTIUM,4,3,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_PICKAXE = ITEMS.register("funtium_pickaxe",
            () -> new PickaxeItem(RileyModToolTiers.FUNTIUM,1,1,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_AXE = ITEMS.register("funtium_axe",
            () -> new AxeItem(RileyModToolTiers.FUNTIUM,4,3,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_SHOVEL = ITEMS.register("funtium_shovel",
            () -> new ShovelItem(RileyModToolTiers.FUNTIUM,0,0,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_HOE = ITEMS.register("funtium_hoe",
            () -> new HoeItem(RileyModToolTiers.FUNTIUM,0,0,new Item.Properties()));

    public static final RegistryObject<Item> FUNTIUM_HELMET = ITEMS.register("funtium_helmet",
            () -> new RileyModArmorItem(RileyModArmorMaterials.EYE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_CHESTPLATE = ITEMS.register("funtium_chestplate",
            () -> new RileyModArmorItem(RileyModArmorMaterials.EYE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_LEGGINGS = ITEMS.register("funtium_leggings",
            () -> new RileyModArmorItem(RileyModArmorMaterials.EYE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_BOOTS = ITEMS.register("funtium_boots",
            () -> new RileyModArmorItem(RileyModArmorMaterials.EYE, ArmorItem.Type.BOOTS, new Item.Properties()));
//spawn egg
    public static final RegistryObject<Item> SUNLESS_CRAB_SPAWN_EGG = ITEMS.register("sunless_crab_spawn_egg",
        () -> new ForgeSpawnEggItem(RileyModEntities.SUNLESS_CRAB,0x0a0303 ,0x9c0000,
                new Item.Properties()));
    public static final RegistryObject<Item> RAPTER_SPAWN_EGG = ITEMS.register("rapter_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.RAPTER,0x0a0303 ,0x001957,
                    new Item.Properties()));
    public static final RegistryObject<Item> NIGHT_TERROR_SPAWN_EGG = ITEMS.register("night_terror_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.NIGHT_TERROR,0x0a0303 ,0xd1d1d1,
                    new Item.Properties()));
    public static final RegistryObject<Item> WHALE_HUNTER_SPAWN_EGG = ITEMS.register("whale_hunter_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.WHALE_HUNTER,0x0a0303 ,0x4a7eff,
                    new Item.Properties()));
    public static final RegistryObject<Item> FROST_HOPPER_SPAWN_EGG = ITEMS.register("frost_hopper_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.FROST_HOPPER,0xebfcff ,0x8dddeb,
                    new Item.Properties()));
    public static final RegistryObject<Item> TOOTHFAIRY_SPAWN_EGG = ITEMS.register("toothfairy_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.TOOTHFAIRY,0x1666b5 ,0x09427a,
                    new Item.Properties()));
    public static final RegistryObject<Item> BONEFAIRY_SPAWN_EGG = ITEMS.register("bonefairy_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.BONEFAIRY,0x0b3f73 ,0x062b4f,
                    new Item.Properties()));
    public static final RegistryObject<Item> SKELETONFAIRY_SPAWN_EGG = ITEMS.register("skeletonfairy_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.SKELETONFAIRY,0x07024a ,0xbcc720,
                    new Item.Properties()));
    public static final RegistryObject<Item> SKULL_FAIRY_SPAWN_EGG = ITEMS.register("skull_fairy_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.SKULL_FAIRY,0x120d0b ,0x635223,
                    new Item.Properties()));
    public static final RegistryObject<Item> BISON_EGG = ITEMS.register("bison_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.BISON,0x543404 ,0x6e4913,
                    new Item.Properties()));
    public static final RegistryObject<Item> TRISON_EGG = ITEMS.register("trison_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.TRISON,0x543424 ,0x6e4943,
                    new Item.Properties()));
    public static final RegistryObject<Item> MECHA_REX_SPAWN_EGG= ITEMS.register("mecha_rex_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.MECHAREX,0x8f8f8f ,0x6a1cbd,
                    new Item.Properties()));
    public static final RegistryObject<Item> MECHA_TERROR_SPAWN_EGG= ITEMS.register("mecha_terror_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.MECHA_TERROR,0x916c06 ,0x262522,
                    new Item.Properties()));
    public static final RegistryObject<Item> PARASITE_CARRIER_SPAWN_EGG= ITEMS.register("parasite_carrier_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.PARASITE_CARRIER,0x5f6967 ,0x3e403f,
                    new Item.Properties()));
    public static final RegistryObject<Item> MECHA_PARASITE_SPAWN_EGG= ITEMS.register("mecha_parasite_spawn_egg",
            () -> new ForgeSpawnEggItem(RileyModEntities.MECHA_PARASITE,0x5f6967 ,0x4f080b,
                    new Item.Properties()));

    public static final RegistryObject<Item> CAGGED_FAIRY = ITEMS.register("cagged_fairy",
            () -> new CaggedFairyItem(new Item.Properties().stacksTo(16)));
//book
public static final RegistryObject<Item> DARK_JOURNAL = ITEMS.register("dark_journal",
        () -> new net.riley.riley_mod.item.custom.JournalItem(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
