package net.riley.riley_mod.item;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.custom.CaggedFairyItem;
import net.riley.riley_mod.item.custom.RileyModArmorItem;
import net.riley.riley_mod.item.custom.RileyModPortalItem;


public class RileyModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RileyMod.MODID);

    //guidebook

    public static final RegistryObject<Item> STARDUST = ITEMS.register("stardust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject <Item> FUNTIUM_MIX = ITEMS.register("funtium_mix",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_PLATE = ITEMS.register("funtium_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_ORE = ITEMS.register("funtium_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EYE = ITEMS.register("eye",
            () -> new RileyModPortalItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ICE_CHUNK = ITEMS.register("ice_chunk",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAW = ITEMS.register("claw",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOOTH = ITEMS.register("tooth",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FANCY_SKULL = ITEMS.register("fancy_skull",
            () -> new Item(new Item.Properties()));

    //TODO make new crafting block
    public static final RegistryObject<Item> LIVING_MACHANICAL_NURONS = ITEMS.register("living_machanical_nurons",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LYDAR = ITEMS.register("lydar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHA_REX_UNASSEMBLED_HEAD = ITEMS.register("mecha_rex_unassembled_head",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_BRAIN = ITEMS.register("mecharex_brain",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MECHAREX_EGG = ITEMS.register("mecharex_egg",
            () -> new Item(new Item.Properties()));//TODO make spawn tamed mecha rex baby
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
            () -> new Item(new Item.Properties()));
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
    public static final RegistryObject<Item> CAGGED_FAIRY = ITEMS.register("cagged_fairy",
            () -> new CaggedFairyItem(new Item.Properties().stacksTo(16)));
//book
public static final RegistryObject<Item> DARK_JOURNAL = ITEMS.register("dark_journal",
        () -> new net.riley.riley_mod.item.custom.JournalItem(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
