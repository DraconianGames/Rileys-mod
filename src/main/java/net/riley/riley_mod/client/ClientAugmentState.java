package net.riley.riley_mod.client;

import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ClientAugmentState {
    private static Set<ResourceLocation> active = new HashSet<>();
    private static Map<ResourceLocation, Integer> levels = new HashMap<>();

    private ClientAugmentState() {}

    public static void apply(Set<ResourceLocation> activeSet, Map<ResourceLocation, Integer> levelMap) {
        active = new HashSet<>(activeSet);
        levels = new HashMap<>(levelMap);
    }

    public static boolean isActive(ResourceLocation augmentId) {
        return active.contains(augmentId);
    }

    public static int getLevel(ResourceLocation augmentId) {
        return levels.getOrDefault(augmentId, 0);
    }

    public static Set<ResourceLocation> getActiveSnapshot() {
        return Collections.unmodifiableSet(active);
    }
}