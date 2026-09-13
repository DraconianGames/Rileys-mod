package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SkyQuadsonFlightInputPacket(boolean jumping, boolean sneaking) {
    public static void encode(SkyQuadsonFlightInputPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.jumping);
        buf.writeBoolean(msg.sneaking);
    }

    public static SkyQuadsonFlightInputPacket decode(FriendlyByteBuf buf) {
        return new SkyQuadsonFlightInputPacket(buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(SkyQuadsonFlightInputPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (player.getVehicle() instanceof net.riley.riley_mod.entity.custom.SkyQuadsonEntity skyQuadson) {
                skyQuadson.setFlightInput(msg.jumping, msg.sneaking);
            }
        });
        ctx.setPacketHandled(true);
    }

}