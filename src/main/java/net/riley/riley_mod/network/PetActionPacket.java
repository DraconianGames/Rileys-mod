package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.item.custom.JournalItem;
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
        buffer.writeUUID(petUUID);
        buffer.writeInt(actionType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (!(stack.getItem() instanceof JournalItem)) stack = player.getItemInHand(InteractionHand.OFF_HAND);

            if (stack.getItem() instanceof JournalItem) {
                if (actionType == 1) {
                    JournalItem.storePet(player, stack, petUUID);
                } else if (actionType == 0) {
                    JournalItem.summonPet(player, stack, petUUID);
                } else if (actionType == 2) {
                    JournalItem.deletePetData(player, stack, petUUID);
                }
            }
        });
        return true;
    }
}
