package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.util.PlayerPetData;

import java.util.function.Supplier;

public class RequestPetDataPacket {
    public RequestPetDataPacket() {
    }

    public RequestPetDataPacket(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            RileyModPackets.sendToPlayer(player, new SyncPetDataPacket(
                    PlayerPetData.getPets(player),
                    PlayerPetData.getMounts(player),
                    PlayerPetData.getVehicles(player)
            ));
        });

        return true;
    }
}