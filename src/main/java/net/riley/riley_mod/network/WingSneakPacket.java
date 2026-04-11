package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;

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

            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");

            if (AugmentData.getLevel(player, wingsId) >= 1) {
                AugmentData.setActive(player, wingsId, msg.sneaking());
            }
        });
        ctx.setPacketHandled(true);
    }
}