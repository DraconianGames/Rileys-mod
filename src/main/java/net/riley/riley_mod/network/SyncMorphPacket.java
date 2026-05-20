package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record SyncMorphPacket(UUID playerId, ResourceLocation morphId) {
    public static void encode(SyncMorphPacket msg, FriendlyByteBuf buf) {
        buf.writeUUID(msg.playerId);
        buf.writeResourceLocation(msg.morphId);
    }

    public static SyncMorphPacket decode(FriendlyByteBuf buf) {
        return new SyncMorphPacket(buf.readUUID(), buf.readResourceLocation());
    }
}