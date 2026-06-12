package net.riley.riley_mod.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.entity.client.PetScreen;

import java.util.function.Supplier;

public class SyncPetDataPacket {
    private final ListTag pets;
    private final ListTag mounts;
    private final ListTag vehicles;

    public SyncPetDataPacket(ListTag pets, ListTag mounts, ListTag vehicles) {
        this.pets = pets.copy();
        this.mounts = mounts.copy();
        this.vehicles = vehicles.copy();
    }

    public SyncPetDataPacket(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();

        this.pets = tag.getList("Pets", 10);
        this.mounts = tag.getList("Mounts", 10);
        this.vehicles = tag.getList("Vehicles", 10);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        CompoundTag tag = new CompoundTag();

        tag.put("Pets", this.pets);
        tag.put("Mounts", this.mounts);
        tag.put("Vehicles", this.vehicles);

        buffer.writeNbt(tag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> PetScreen.setSyncedCompanions(this.pets, this.mounts, this.vehicles));

        return true;
    }
}