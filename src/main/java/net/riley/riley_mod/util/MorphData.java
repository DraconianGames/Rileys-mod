package net.riley.riley_mod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.riley.riley_mod.RileyMod;

public final class MorphData {
    private static final String ROOT = RileyMod.MODID + "_morphs";
    private static final String CURRENT = "current";
    private static final String UNLOCKED = "unlocked";

    private static final ResourceLocation PLAYER_MORPH =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player");

    private MorphData() {}

    private static CompoundTag root(ServerPlayer player) {
        CompoundTag persistent = player.getPersistentData();

        if (!persistent.contains(ROOT)) {
            persistent.put(ROOT, new CompoundTag());
        }

        return persistent.getCompound(ROOT);
    }

    private static CompoundTag unlockedRoot(ServerPlayer player) {
        CompoundTag root = root(player);

        if (!root.contains(UNLOCKED)) {
            root.put(UNLOCKED, new CompoundTag());
        }

        return root.getCompound(UNLOCKED);
    }

    public static boolean isUnlocked(ServerPlayer player, ResourceLocation morphId) {
        if (PLAYER_MORPH.equals(morphId)) return true;

        CompoundTag unlocked = unlockedRoot(player);
        return unlocked.getBoolean(morphId.toString());
    }

    public static void unlock(ServerPlayer player, ResourceLocation morphId) {
        CompoundTag unlocked = unlockedRoot(player);
        unlocked.putBoolean(morphId.toString(), true);
    }

    public static ResourceLocation getCurrentMorph(ServerPlayer player) {
        CompoundTag root = root(player);

        if (!root.contains(CURRENT)) {
            return PLAYER_MORPH;
        }

        ResourceLocation parsed = ResourceLocation.tryParse(root.getString(CURRENT));
        return parsed == null ? PLAYER_MORPH : parsed;
    }

    public static void setCurrentMorph(ServerPlayer player, ResourceLocation morphId) {
        root(player).putString(CURRENT, morphId.toString());
    }
}