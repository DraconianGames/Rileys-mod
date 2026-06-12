package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record WingSneakPacket(boolean sneaking) {
    public static void encode(WingSneakPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.sneaking);
    }

    public static WingSneakPacket decode(FriendlyByteBuf buf) {
        return new WingSneakPacket(buf.readBoolean());
    }

    public static void handle(WingSneakPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            // Do not change AugmentData active state here.
            // Sneaking is temporary input, not whether the augment is equipped.
        });
        ctx.setPacketHandled(true);
    }
}