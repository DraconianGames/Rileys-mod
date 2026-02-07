package net.riley.riley_mod.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Set;

public final class AugmentData {
    private AugmentData() {}

    private static final String ROOT = "riley_mod.augments";
    private static final String UNLOCKED = "unlocked";
    private static final String ACTIVE = "active";
    private static final String LEVELS = "levels"; // NEW

    private static CompoundTag root(ServerPlayer player) {
        CompoundTag pd = player.getPersistentData();
        if (!pd.contains(ROOT)) pd.put(ROOT, new CompoundTag());
        return pd.getCompound(ROOT);
    }

    public static Set<ResourceLocation> getUnlocked(ServerPlayer player) {
        return readSet(root(player), UNLOCKED);
    }

    public static Set<ResourceLocation> getActive(ServerPlayer player) {
        return readSet(root(player), ACTIVE);
    }

    public static boolean isUnlocked(ServerPlayer player, ResourceLocation augmentId) {
        return getUnlocked(player).contains(augmentId);
    }

    public static boolean isActive(ServerPlayer player, ResourceLocation augmentId) {
        return getActive(player).contains(augmentId);
    }

    public static boolean unlock(ServerPlayer player, ResourceLocation augmentId) {
        Set<ResourceLocation> unlocked = getUnlocked(player);
        if (!unlocked.add(augmentId)) return false;
        writeSet(root(player), UNLOCKED, unlocked);
        return true;
    }

    public static void setActive(ServerPlayer player, ResourceLocation augmentId, boolean active) {
        Set<ResourceLocation> unlocked = getUnlocked(player);
        if (!unlocked.contains(augmentId)) return; // can't activate something not unlocked

        Set<ResourceLocation> actives = getActive(player);
        if (active) actives.add(augmentId);
        else actives.remove(augmentId);

        writeSet(root(player), ACTIVE, actives);
    }

    private static Set<ResourceLocation> readSet(CompoundTag tag, String key) {
        Set<ResourceLocation> out = new HashSet<>();
        if (!tag.contains(key)) return out;

        ListTag list = tag.getList(key, 8); // 8 = StringTag
        for (int i = 0; i < list.size(); i++) {
            String s = list.getString(i);
            try { out.add(new ResourceLocation(s)); }
            catch (Exception ignored) {}
        }
        return out;
    }
    public static int getLevel(ServerPlayer player, ResourceLocation augmentId) {
        CompoundTag r = root(player);
        if (!r.contains(LEVELS)) return isUnlocked(player, augmentId) ? 1 : 0;
        CompoundTag levels = r.getCompound(LEVELS);
        return levels.contains(augmentId.toString()) ? levels.getInt(augmentId.toString()) : (isUnlocked(player, augmentId) ? 1 : 0);
    }

    public static void setLevel(ServerPlayer player, ResourceLocation augmentId, int level) {
        int lvl = Math.max(1, level);
        CompoundTag r = root(player);
        if (!r.contains(LEVELS)) r.put(LEVELS, new CompoundTag());
        r.getCompound(LEVELS).putInt(augmentId.toString(), lvl);

        // ensure unlocked set contains it too (keeps old logic compatible)
        unlock(player, augmentId);
    }
    private static void writeSet(CompoundTag tag, String key, Set<ResourceLocation> set) {
        ListTag list = new ListTag();
        for (ResourceLocation id : set) list.add(StringTag.valueOf(id.toString()));
        tag.put(key, list);
    }
}