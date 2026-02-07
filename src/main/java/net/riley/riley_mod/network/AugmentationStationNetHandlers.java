package net.riley.riley_mod.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.recipe.AugmentationStationRecipe;
import net.riley.riley_mod.recipe.Requirement;
import net.riley.riley_mod.util.AugmentData;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public final class AugmentationStationNetHandlers {
    private AugmentationStationNetHandlers() {}

    public static void handleCraft(CraftAugmentPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            Level level = player.level();
            BlockPos pos = msg.pos();

            if (!(level.getBlockState(pos).is(RileyModBlocks.AUGMENTATION_STATION.get())
                    || level.getBlockState(pos).is(RileyModBlocks.AUGMENTATION_STATION_FILLER.get()))) {
                return;
            }

            Optional<?> any = level.getRecipeManager().byKey(msg.recipeId());
            if (any.isEmpty() || !(any.get() instanceof AugmentationStationRecipe recipe)) return;

            int currentLevel = AugmentData.getLevel(player, recipe.getAugmentId());
            int targetLevel = recipe.getLevel();

            // If already at or above this recipe's level, no-op
            if (currentLevel >= targetLevel) {
                syncToClient(player);
                return;
            }

            if (!hasAll(player.getInventory(), recipe)) return;

            consumeAll(player.getInventory(), recipe);

            // Craft unlocks (does NOT activate) but sets/bumps the level
            AugmentData.setLevel(player, recipe.getAugmentId(), targetLevel);

            syncToClient(player);
        });
        ctx.setPacketHandled(true);
    }

    public static void handleSetActive(SetAugmentActivePacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation augmentId = msg.augmentId();
            if (!AugmentData.isUnlocked(player, augmentId)) return;

            AugmentData.setActive(player, augmentId, msg.active());
            syncToClient(player);
        });
        ctx.setPacketHandled(true);
    }

    private static boolean hasAll(Inventory inv, AugmentationStationRecipe recipe) {
        for (Requirement r : recipe.getRequirements()) {
            if (countItem(inv, r.item()) < r.count()) return false;
        }
        return true;
    }

    private static void consumeAll(Inventory inv, AugmentationStationRecipe recipe) {
        for (Requirement r : recipe.getRequirements()) {
            removeItem(inv, r.item(), r.count());
        }
    }

    private static int countItem(Inventory inv, Item item) {
        int total = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (!s.isEmpty() && s.getItem() == item) total += s.getCount();
        }
        return total;
    }

    private static void removeItem(Inventory inv, Item item, int amount) {
        int remaining = amount;
        for (int i = 0; i < inv.getContainerSize() && remaining > 0; i++) {
            ItemStack s = inv.getItem(i);
            if (s.isEmpty() || s.getItem() != item) continue;

            int take = Math.min(remaining, s.getCount());
            s.shrink(take);
            remaining -= take;

            if (s.isEmpty()) inv.setItem(i, ItemStack.EMPTY);
        }
    }

    private static void syncToClient(ServerPlayer player) {
        Set<ResourceLocation> unlocked = AugmentData.getUnlocked(player);
        Set<ResourceLocation> active = AugmentData.getActive(player);

        Map<ResourceLocation, Integer> levels = new HashMap<>();
        for (ResourceLocation id : unlocked) {
            levels.put(id, AugmentData.getLevel(player, id));
        }

        RileyModPackets.sendToPlayer(player, new SyncAugmentsPacket(unlocked, active, levels));
    }
}
