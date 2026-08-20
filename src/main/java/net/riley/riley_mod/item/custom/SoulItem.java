package net.riley.riley_mod.item.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.ItemStack;


public class SoulItem extends Item {
    private final EntityType<?> entityType;
    private final int primaryColor;
    private final int secondaryColor;

    public SoulItem(EntityType<?> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
        int[] cols = fetchEggColors(entityType);
        this.primaryColor = cols[0];
        this.secondaryColor = cols[1];
    }

    private static int[] fetchEggColors(EntityType<?> type) {
        for (net.minecraft.world.item.Item item : ForgeRegistries.ITEMS) {
            if (item instanceof SpawnEggItem egg) {
                // egg.getType(null) is the same approach used in your trophy class
                if (egg.getType(null) == type) {
                    return new int[] { egg.getColor(0), egg.getColor(1) };
                }
            }
        }
        return new int[] { 0xFFFFFF, 0x000000 }; // fallback
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public int getPrimaryColor(ItemStack stack) {
        return primaryColor;
    }

    public int getSecondaryColor(ItemStack stack) {
        return secondaryColor;
    }
}