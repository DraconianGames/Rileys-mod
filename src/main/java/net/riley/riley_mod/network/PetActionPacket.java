package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.util.PlayerPetData;

import java.util.UUID;
import java.util.function.Supplier;

public class PetActionPacket {
    private final UUID petUUID;
    private final int actionType;

    public PetActionPacket(UUID petUUID, int actionType) {
        this.petUUID = petUUID;
        this.actionType = actionType;
    }

    public PetActionPacket(FriendlyByteBuf buffer) {
        this.petUUID = buffer.readUUID();
        this.actionType = buffer.readInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(this.petUUID);
        buffer.writeInt(this.actionType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            if (this.actionType == 1) {
                PlayerPetData.storePet(player, this.petUUID);
            } else if (this.actionType == 0) {
                PlayerPetData.summonPet(player, this.petUUID);
            } else if (this.actionType == 2) {
                PlayerPetData.deletePet(player, this.petUUID);
            }
        });

        return true;
    }
}