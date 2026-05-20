package net.riley.riley_mod.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;

public class MorphStationMenu extends AbstractContainerMenu {
    private final BlockPos pos;
    private final Set<ResourceLocation> unlockedMorphs;

    // Client constructor (from packet)
    public MorphStationMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, buf.readBlockPos(), buf.readCollection(LinkedHashSet::new, FriendlyByteBuf::readResourceLocation));
    }

    // Server constructor
    public MorphStationMenu(int containerId, Inventory inv, BlockPos pos) {
        this(containerId, inv, pos, Set.of());
    }

    private MorphStationMenu(int containerId, Inventory inv, BlockPos pos, Set<ResourceLocation> unlockedMorphs) {
        super(RileyModMenuTypes.MORPH_STATION_MENU.get(), containerId);
        this.pos = pos;
        this.unlockedMorphs = unlockedMorphs;
    }
    public BlockPos getPos() {
        return pos;
    }

    public Set<ResourceLocation> getUnlockedMorphs() {
        return unlockedMorphs;
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