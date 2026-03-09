package net.riley.riley_mod.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CustomMountMenu extends AbstractContainerMenu {
    public static final int SADDLE_SLOT_INDEX = 0;
    public static final int ARMOR_SLOT_INDEX = 1;
    public static final int STORAGE_START_INDEX = 2;
    private static final int Y_OFFSET = 19;

    private final Container mountInventory;
    private final Entity mountEntity;
    private final MountInventoryAccess mountAccess;
    private final int storageColumns;
    private final int storageRows;

    public CustomMountMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, playerInventory.player.level().getEntity(buf.readInt()));
    }

    public CustomMountMenu(int containerId, Inventory playerInventory, Entity entity) {
        super(RileyModMenuTypes.CUSTOM_MOUNT_MENU.get(), containerId);

        this.mountEntity = entity;
        this.mountAccess = entity instanceof MountInventoryAccess access ? access : null;
        this.mountInventory = this.mountAccess != null ? this.mountAccess.getMountInventory() : new SimpleContainer(2);
        this.storageColumns = this.mountAccess != null ? this.mountAccess.getMountStorageColumns() : 0;
        this.storageRows = this.mountAccess != null ? this.mountAccess.getMountStorageRows() : 0;

        this.mountInventory.startOpen(playerInventory.player);

        this.addSlot(new Slot(this.mountInventory, SADDLE_SLOT_INDEX, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return mountAccess != null && mountAccess.canEquipMountSaddle(stack) && stack.is(Items.SADDLE);
            }

            @Override
            public boolean isActive() {
                return mountAccess != null;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        this.addSlot(new Slot(this.mountInventory, ARMOR_SLOT_INDEX, 8, 36 + Y_OFFSET) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return mountAccess != null && mountAccess.canEquipMountArmor(stack);
            }

            @Override
            public boolean isActive() {
                return mountAccess != null;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int row = 0; row < this.storageRows; ++row) {
            for (int col = 0; col < this.storageColumns; ++col) {
                this.addSlot(new Slot(this.mountInventory,
                        STORAGE_START_INDEX + col + row * this.storageColumns,
                        80 + col * 18,
                        18 + row * 18));
            }
        }

        int playerRowsY = 65 + Y_OFFSET;
        int hotbarY = 142;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        8 + col * 18,
                        playerRowsY + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col,
                    8 + col * 18,
                    hotbarY));
        }
    }

    public Entity getMountEntity() {
        return this.mountEntity;
    }

    public boolean hasMountStorage() {
        return this.storageRows > 0 && this.storageColumns > 0;
    }

    public int getStorageColumns() {
        return this.storageColumns;
    }

    public int getStorageRows() {
        return this.storageRows;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.mountEntity != null
                && this.mountEntity.isAlive()
                && this.mountEntity.distanceTo(player) < 8.0F;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        result = stack.copy();

        int mountSlotCount = 2 + (this.storageRows * this.storageColumns);
        int playerInventoryStart = mountSlotCount;
        int hotbarStart = playerInventoryStart + 27;
        int playerInventoryEnd = this.slots.size();

        if (index < mountSlotCount) {
            if (!this.moveItemStackTo(stack, playerInventoryStart, playerInventoryEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (this.mountAccess != null
                    && this.mountAccess.canEquipMountArmor(stack)
                    && !this.slots.get(ARMOR_SLOT_INDEX).hasItem()) {
                if (!this.moveItemStackTo(stack, ARMOR_SLOT_INDEX, ARMOR_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.mountAccess != null
                    && this.mountAccess.canEquipMountSaddle(stack)
                    && !this.slots.get(SADDLE_SLOT_INDEX).hasItem()) {
                if (!this.moveItemStackTo(stack, SADDLE_SLOT_INDEX, SADDLE_SLOT_INDEX + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.hasMountStorage()) {
                if (!this.moveItemStackTo(stack, STORAGE_START_INDEX, mountSlotCount, false)) {
                    if (index < hotbarStart) {
                        if (!this.moveItemStackTo(stack, hotbarStart, playerInventoryEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(stack, playerInventoryStart, hotbarStart, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (index < hotbarStart) {
                if (!this.moveItemStackTo(stack, hotbarStart, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, playerInventoryStart, hotbarStart, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return result;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.mountInventory.stopOpen(player);
    }
}