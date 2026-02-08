package net.riley.riley_mod.world.domain;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DomainRegistry {
    private DomainRegistry() {}

    private static final Map<ResourceLocation, DomainBehavior> BY_ID = new ConcurrentHashMap<>();

    public static void register(DomainBehavior behavior) {
        BY_ID.put(behavior.id(), behavior);
    }

    public static boolean isDomain(ResourceLocation id) {
        return id != null && BY_ID.containsKey(id);
    }

    public static DomainBehavior get(ResourceLocation id) {
        return id == null ? null : BY_ID.get(id);
    }

    public static Collection<ResourceLocation> ids() {
        return Collections.unmodifiableSet(BY_ID.keySet());
    }
}