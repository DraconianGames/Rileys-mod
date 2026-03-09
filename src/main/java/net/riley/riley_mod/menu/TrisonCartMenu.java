package net.riley.riley_mod.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.riley.riley_mod.entity.custom.TrisonCartEntity;
import org.jetbrains.annotations.Nullable;

public class TrisonCartMenu extends AbstractContainerMenu {
    public static final int CART_COLUMNS = 13;
    public static final int CART_ROWS = 4;
    public static final int CART_SLOT_COUNT = CART_COLUMNS * CART_ROWS;
    public static final int PLAYER_INV_SLOT_COUNT = 27;
    public static final int HOTBAR_SLOT_COUNT = 9;
    public static final int VANILLA_SLOT_COUNT = PLAYER_INV_SLOT_COUNT + HOTBAR_SLOT_COUNT;
    public static final int TOTAL_SLOT_COUNT = CART_SLOT_COUNT + VANILLA_SLOT_COUNT;

    private static final int CART_INV_X = 10;
    private static final int CART_INV_Y = 10;

    private static final int PLAYER_INV_X = 9;
    private static final int PLAYER_INV_Y = 178;
    private static final int HOTBAR_Y = 232;

    private final Container cartInventory;
    private final ContainerData cartData;
    private final @Nullable TrisonCartEntity cartEntity;

    public TrisonCartMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, getCartFromBuffer(playerInventory, buf));
    }

    public TrisonCartMenu(int containerId, Inventory playerInventory, @Nullable TrisonCartEntity cartEntity) {
        super(RileyModMenuTypes.TRISON_CART_MENU.get(), containerId);
        this.cartEntity = cartEntity;
        this.cartInventory = cartEntity != null ? cartEntity.getCartInventory() : new SimpleContainer(CART_SLOT_COUNT);
        this.cartData = new net.minecraft.world.inventory.SimpleContainerData(1);

        checkContainerSize(this.cartInventory, CART_SLOT_COUNT);
        checkContainerDataCount(this.cartData, 1);

        this.cartInventory.startOpen(playerInventory.player);
        this.addDataSlots(this.cartData);

        if (this.cartEntity != null) {
            this.cartData.set(0, this.cartEntity.hasCover() ? 1 : 0);
        }

        addCartSlots();
        addPlayerInventorySlots(playerInventory);
        addHotbarSlots(playerInventory);
    }


    private static @Nullable TrisonCartEntity getCartFromBuffer(Inventory playerInventory, FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        Entity entity = playerInventory.player.level().getEntity(entityId);
        return entity instanceof TrisonCartEntity trisonCartEntity ? trisonCartEntity : null;
    }

    private void addCartSlots() {
        for (int row = 0; row < CART_ROWS; row++) {
            for (int col = 0; col < CART_COLUMNS; col++) {
                int slotIndex = col + row * CART_COLUMNS;
                int x = CART_INV_X + col * 18;
                int y = CART_INV_Y + row * 18;
                this.addSlot(new Slot(this.cartInventory, slotIndex, x, y));
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

    public boolean isCartCovered() {
        if (this.cartEntity != null) {
            return this.cartEntity.hasCover();
        }
        return this.cartData.get(0) != 0;
    }

    public void setCartCovered(boolean covered) {
        this.cartData.set(0, covered ? 1 : 0);
        if (this.cartEntity != null) {
            this.cartEntity.setCover(covered);
        }
    }

    public void toggleCartCover() {
        this.setCartCovered(!this.isCartCovered());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copiedStack = ItemStack.EMPTY;
        Slot sourceSlot = this.slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        copiedStack = sourceStack.copy();

        if (index < CART_SLOT_COUNT) {
            if (!this.moveItemStackTo(sourceStack, CART_SLOT_COUNT, TOTAL_SLOT_COUNT, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < TOTAL_SLOT_COUNT) {
            if (!this.moveItemStackTo(sourceStack, 0, CART_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copiedStack;
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.cartEntity != null) {
            return this.cartEntity.isAlive() && this.cartEntity.distanceTo(player) < 8.0F;
        }
        return this.cartInventory.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.cartInventory.stopOpen(player);
    }
}