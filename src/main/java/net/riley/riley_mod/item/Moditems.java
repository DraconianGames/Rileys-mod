package net.riley.riley_mod.item;


import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;

public class Moditems {
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
    //Tools
    public static final RegistryObject<Item> FUNTIUM_SWORD = ITEMS.register("funtium_sword",
            () -> new SwordItem(ModToolTiers.FUNTIUM,4,3,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_PICKAXE = ITEMS.register("funtium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FUNTIUM,1,1,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_AXE = ITEMS.register("funtium_axe",
            () -> new AxeItem(ModToolTiers.FUNTIUM,4,3,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_SHOVEL = ITEMS.register("funtium_shovel",
            () -> new ShovelItem(ModToolTiers.FUNTIUM,0,0,new Item.Properties()));
    public static final RegistryObject<Item> FUNTIUM_HOE = ITEMS.register("funtium_hoe",
            () -> new HoeItem(ModToolTiers.FUNTIUM,0,0,new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
