package net.riley.riley_mod.brewing;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.potion.RileyModPotions;

/**
 * Brewing recipe: Awkward Potion + Snowball -> Freeze Potion
 */
public class FreezeBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        if (input == null) return false;
        return (input.getItem() == Items.POTION || input.getItem() == Items.SPLASH_POTION || input.getItem() == Items.LINGERING_POTION)
                && PotionUtils.getPotion(input) == Potions.AWKWARD;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        // Use the ICE_CHUNK item from your mod as the ingredient
        return ingredient != null && ingredient.getItem() == RileyModItems.ICE_CHUNK.get();
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!isInput(input) || !isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        // Preserve the bottle type (normal/splash/lingering)
        ItemStack result = new ItemStack(input.getItem());
        PotionUtils.setPotion(result, RileyModPotions.FREEZE_POTION.get());
        return result;
    }
}