package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record SyncAugmentsPacket(Set<ResourceLocation> unlocked,
                                 Set<ResourceLocation> active,
                                 Map<ResourceLocation, Integer> levels) {

    public static void encode(SyncAugmentsPacket msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.unlocked.size());
        for (ResourceLocation id : msg.unlocked) buf.writeResourceLocation(id);

        buf.writeVarInt(msg.active.size());
        for (ResourceLocation id : msg.active) buf.writeResourceLocation(id);

        buf.writeVarInt(msg.levels.size());
        for (var e : msg.levels.entrySet()) {
            buf.writeResourceLocation(e.getKey());
            buf.writeVarInt(e.getValue());
        }
    }

    public static SyncAugmentsPacket decode(FriendlyByteBuf buf) {
        int u = buf.readVarInt();
        Set<ResourceLocation> unlocked = new HashSet<>();
        for (int i = 0; i < u; i++) unlocked.add(buf.readResourceLocation());

        int a = buf.readVarInt();
        Set<ResourceLocation> active = new HashSet<>();
        for (int i = 0; i < a; i++) active.add(buf.readResourceLocation());

        int n = buf.readVarInt();
        Map<ResourceLocation, Integer> levels = new HashMap<>();
        for (int i = 0; i < n; i++) {
            ResourceLocation id = buf.readResourceLocation();
            int lvl = buf.readVarInt();
            levels.put(id, lvl);
        }

        return new SyncAugmentsPacket(unlocked, active, levels);
    }
}