package net.riley.riley_mod.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class AugmentationStationMenu extends AbstractContainerMenu {
    private final BlockPos pos;

    // client-side copies (sent from server on open, and updated via sync packet)
    private Set<ResourceLocation> unlocked = new HashSet<>();
    private Set<ResourceLocation> active = new HashSet<>();
    private Map<ResourceLocation, Integer> levels = new HashMap<>();

    // Client constructor (from packet)
    public AugmentationStationMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, buf.readBlockPos());

        int unlockedCount = buf.readVarInt();
        for (int i = 0; i < unlockedCount; i++) unlocked.add(buf.readResourceLocation());

        int activeCount = buf.readVarInt();
        for (int i = 0; i < activeCount; i++) active.add(buf.readResourceLocation());

        int levelsCount = buf.readVarInt();
        for (int i = 0; i < levelsCount; i++) {
            ResourceLocation id = buf.readResourceLocation();
            int lvl = buf.readVarInt();
            levels.put(id, lvl);
        }
    }

    // Server constructor
    public AugmentationStationMenu(int containerId, Inventory inv, BlockPos pos) {
        super(RileyModMenuTypes.AUGMENTATION_STATION_MENU.get(), containerId);
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Set<ResourceLocation> getUnlockedClient() {
        return unlocked;
    }

    public Set<ResourceLocation> getActiveClient() {
        return active;
    }
    public Map<ResourceLocation, Integer> getLevelsClient() {
        return levels;
    }
    public void setClientState(Set<ResourceLocation> unlocked, Set<ResourceLocation> active, Map<ResourceLocation, Integer> levels) {
        this.unlocked = unlocked;
        this.active = active;
        this.levels = levels;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}