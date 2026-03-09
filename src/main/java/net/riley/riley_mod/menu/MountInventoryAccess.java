package net.riley.riley_mod.menu;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

public interface MountInventoryAccess {
    SimpleContainer getMountInventory();
    boolean canEquipMountSaddle(ItemStack stack);
    boolean canEquipMountArmor(ItemStack stack);
    boolean hasMountSaddle();
    boolean hasMountArmor();
    int getMountStorageColumns();
    int getMountStorageRows();
}