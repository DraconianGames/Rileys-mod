package net.riley.riley_mod.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class MorphStationMenu extends AbstractContainerMenu {
    private final BlockPos pos;

    // Client constructor (from packet)
    public MorphStationMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, buf.readBlockPos());
    }

    // Server constructor
    public MorphStationMenu(int containerId, Inventory inv, BlockPos pos) {
        super(RileyModMenuTypes.MORPH_STATION_MENU.get(), containerId);
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // No slots yet
        return ItemStack.EMPTY;
    }
}