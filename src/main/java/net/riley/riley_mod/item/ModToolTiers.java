package net.riley.riley_mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier FUNTIUM = TierSortingRegistry.registerTier(
            new ForgeTier(5,3000,40f,100f,30,
                    ModTags.Blocks.Needs_Funtium_TOOL, () -> Ingredient.of(Moditems.FUNTIUM_PLATE.get())),
            new ResourceLocation(RileyMod.MODID, "funtium"), List.of(Tiers.NETHERITE), List.of());

}
