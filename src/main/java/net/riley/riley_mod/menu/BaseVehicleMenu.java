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
import net.riley.riley_mod.entity.custom.BaseVehicleEntity;

public class BaseVehicleMenu extends AbstractContainerMenu {
    public static final int UPGRADE_SLOT_INDEX = 0;

    public static final int VEHICLE_COLUMNS = 14;
    public static final int VEHICLE_ROWS = 6;
    public static final int VEHICLE_STORAGE_SLOT_COUNT = VEHICLE_COLUMNS * VEHICLE_ROWS;
    public static final int VEHICLE_SLOT_COUNT = 1 + VEHICLE_STORAGE_SLOT_COUNT;


    private static final int VEHICLE_INV_X = 2;
    private static final int VEHICLE_INV_Y = 2;

    private static final int PLAYER_INV_X = 0;
    private static final int PLAYER_INV_Y = 186;
    private static final int HOTBAR_Y = 240;

    private static final int UPGRADE_SLOT_X = 236;
    private static final int UPGRADE_SLOT_Y = 236;

    private final Entity vehicleEntity;
    private final Container vehicleInventory;


    public BaseVehicleMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, playerInventory.player.level().getEntity(buf.readInt()));
    }

    public BaseVehicleMenu(int containerId, Inventory playerInventory, Entity vehicleEntity) {
        super(RileyModMenuTypes.BASE_VEHICLE_MENU.get(), containerId);
        this.vehicleEntity = vehicleEntity;
        this.vehicleInventory = vehicleEntity instanceof BaseVehicleEntity baseVehicle
                ? baseVehicle.getVehicleInventory()
                : new SimpleContainer(VEHICLE_SLOT_COUNT);

        this.vehicleInventory.startOpen(playerInventory.player);

        this.addUpgradeSlot();
        this.addVehicleInventorySlots();
        this.addPlayerInventorySlots(playerInventory);
        this.addHotbarSlots(playerInventory);
    }
    private void addUpgradeSlot() {
        this.addSlot(new Slot(this.vehicleInventory, UPGRADE_SLOT_INDEX, UPGRADE_SLOT_X, UPGRADE_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return vehicleEntity instanceof BaseVehicleEntity baseVehicle
                        && baseVehicle.isValidVehicleUpgrade(stack);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    private void addVehicleInventorySlots() {
        for (int row = 0; row < VEHICLE_ROWS; row++) {
            for (int col = 0; col < VEHICLE_COLUMNS; col++) {
                int slotIndex = 1 + col + row * VEHICLE_COLUMNS;
                int x = VEHICLE_INV_X + col * 18;
                int y = VEHICLE_INV_Y + row * 18;

                this.addSlot(new Slot(this.vehicleInventory, slotIndex, x, y));
            }
        }
    }

    private void addPlayerInventorySlots(Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = col + row * 9 + 9;
                int x = PLAYER_INV_X + col * 18;
                int y = PLAYER_INV_Y + row * 18;

                this.addSlot(new Slot(playerInventory, slotIndex, x, y));
            }
        }
    }

    private void addHotbarSlots(Inventory playerInventory) {
        for (int col = 0; col < 9; col++) {
            int x = PLAYER_INV_X + col * 18;

            this.addSlot(new Slot(playerInventory, col, x, HOTBAR_Y));
        }
    }

    public Entity getVehicleEntity() {
        return this.vehicleEntity;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.vehicleEntity != null
                && this.vehicleEntity.isAlive()
                && this.vehicleEntity.distanceTo(player) < 8.0F;
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

        int vehicleInventoryStart = 0;
        int vehicleInventoryEnd = VEHICLE_SLOT_COUNT;
        int playerInventoryStart = vehicleInventoryEnd;
        int playerInventoryEnd = this.slots.size();

        if (index >= vehicleInventoryStart && index < vehicleInventoryEnd) {
            if (!this.moveItemStackTo(stack, playerInventoryStart, playerInventoryEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else if (this.vehicleEntity instanceof BaseVehicleEntity baseVehicle
                && baseVehicle.isValidVehicleUpgrade(stack)
                && !this.slots.get(UPGRADE_SLOT_INDEX).hasItem()) {
            if (!this.moveItemStackTo(stack, UPGRADE_SLOT_INDEX, UPGRADE_SLOT_INDEX + 1, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.moveItemStackTo(stack, 1, vehicleInventoryEnd, false)) {
            return ItemStack.EMPTY;
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
        this.vehicleInventory.stopOpen(player);
    }
}