package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.util.PlayerPetData;

import java.util.UUID;
import java.util.function.Supplier;

public class PetSummonLocationPacket {
    private final UUID petUUID;
    private final double x;
    private final double y;
    private final double z;

    public PetSummonLocationPacket(UUID petUUID, double x, double y, double z) {
        this.petUUID = petUUID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PetSummonLocationPacket(FriendlyByteBuf buffer) {
        this.petUUID = buffer.readUUID();
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.petUUID);
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            PlayerPetData.setPetSummonCharging(player, false);
            PlayerPetData.summonPetAt(player, this.petUUID, this.x, this.y, this.z);
        });

        return true;
    }
}