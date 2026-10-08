package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.entity.custom.AbstractFlyingMountEntity;

import java.util.function.Supplier;

public class MountFlightTogglePacket {
    public static void encode(MountFlightTogglePacket msg, FriendlyByteBuf buf) {
    }

    public static MountFlightTogglePacket decode(FriendlyByteBuf buf) {
        return new MountFlightTogglePacket();
    }

    public static void handle(MountFlightTogglePacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (player.getVehicle() instanceof AbstractFlyingMountEntity flyingMount) {
                flyingMount.toggleFlight();
            }
        });
        ctx.setPacketHandled(true);
    }
}