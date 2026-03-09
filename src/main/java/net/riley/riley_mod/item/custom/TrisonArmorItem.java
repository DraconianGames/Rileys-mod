package net.riley.riley_mod.item.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.riley.riley_mod.RileyMod;

public class TrisonArmorItem extends HorseArmorItem {
    public TrisonArmorItem(int protection, Properties properties) {
        super(protection,
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/trison_armor.png"),
                properties);
    }
}