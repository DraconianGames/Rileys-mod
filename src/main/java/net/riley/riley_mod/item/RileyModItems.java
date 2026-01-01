package net.riley.riley_mod.item;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.custom.RileyModArmorItem;

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
    public static final RegistryObject<Item> EYE = ITEMS.register("eye",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ICE_CHUNK = ITEMS.register("ice_chunk",
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
        () -> new ForgeSpawnEggItem(RileyModEntities.SUNLESS_CRAB,651212 ,8620202,
                new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
