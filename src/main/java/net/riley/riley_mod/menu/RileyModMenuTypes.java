package net.riley.riley_mod.menu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;

public class RileyModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RileyMod.MODID);

    public static final RegistryObject<MenuType<SpecialSpawnerMenu>> SPECIAL_SPAWNER_MENU =
            MENUS.register("special_spawner_menu",
                    () -> IForgeMenuType.create((IContainerFactory<SpecialSpawnerMenu>) SpecialSpawnerMenu::new));

    public static final RegistryObject<MenuType<AugmentationStationMenu>> AUGMENTATION_STATION_MENU =
            MENUS.register("augmentation_station_menu",
                    () -> IForgeMenuType.create((IContainerFactory<AugmentationStationMenu>) AugmentationStationMenu::new));

    public static final RegistryObject<MenuType<MorphStationMenu>> MORPH_STATION_MENU =
            MENUS.register("morph_station_menu",
                    () -> IForgeMenuType.create((IContainerFactory<MorphStationMenu>) MorphStationMenu::new));

    public static final RegistryObject<MenuType<EnchanterMenu>> ENCHANTER_MENU =
            MENUS.register("enchanter_menu",
                    () -> IForgeMenuType.create((IContainerFactory<EnchanterMenu>) EnchanterMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}