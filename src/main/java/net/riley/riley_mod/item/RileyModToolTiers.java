package net.riley.riley_mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.RileyModTags;

import java.util.List;

public class RileyModToolTiers {
    public static final Tier FUNTIUM = TierSortingRegistry.registerTier(
            new ForgeTier(5,3000,40f,100f,30,
                    RileyModTags.Blocks.Needs_Funtium_TOOL, () -> Ingredient.of(RileyModItems.FUNTIUM_PLATE.get())),
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "funtium"), List.of(Tiers.NETHERITE), List.of());

}
