package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SetAugmentActivePacket(ResourceLocation augmentId, boolean active) {
    public static void encode(SetAugmentActivePacket msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.augmentId);
        buf.writeBoolean(msg.active);
    }

    public static SetAugmentActivePacket decode(FriendlyByteBuf buf) {
        return new SetAugmentActivePacket(buf.readResourceLocation(), buf.readBoolean());
    }
}