package net.riley.riley_mod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;

public class RileyModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RileyMod.MODID);

    public static final RegistryObject<RecipeSerializer<AugmentationStationRecipe>> AUGMENTATION_STATION_SERIALIZER =
            SERIALIZERS.register(AugmentationStationRecipe.TYPE_ID.getPath(), () -> AugmentationStationRecipeSerializer.INSTANCE);

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
    }
}