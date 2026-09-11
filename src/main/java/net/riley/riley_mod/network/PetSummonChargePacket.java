package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.util.PlayerPetData;

import java.util.function.Supplier;

public class PetSummonChargePacket {
    private final boolean charging;

    public PetSummonChargePacket(boolean charging) {
        this.charging = charging;
    }

    public PetSummonChargePacket(FriendlyByteBuf buffer) {
        this.charging = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.charging);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            PlayerPetData.setPetSummonCharging(player, this.charging);
        });

        return true;
    }
}