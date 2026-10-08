package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.entity.custom.AbstractFlyingMountEntity;

import java.util.function.Supplier;

public record MountFlightInputPacket(boolean upArrow, boolean downArrow) {
    public static void encode(MountFlightInputPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.upArrow);
        buf.writeBoolean(msg.downArrow);
    }

    public static MountFlightInputPacket decode(FriendlyByteBuf buf) {
        return new MountFlightInputPacket(buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(MountFlightInputPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (player.getVehicle() instanceof AbstractFlyingMountEntity flyingMount) {
                flyingMount.setFlightInput(msg.upArrow, msg.downArrow);
            }
        });
        ctx.setPacketHandled(true);
    }
}