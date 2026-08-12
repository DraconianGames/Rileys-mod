package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.menu.BaseVehicleMenu;

import java.util.function.Supplier;

public class SortVehicleInventoryPacket {
    public SortVehicleInventoryPacket() {
    }

    public SortVehicleInventoryPacket(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public static void handle(SortVehicleInventoryPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) {
                return;
            }

            AbstractContainerMenu menu = player.containerMenu;

            if (menu instanceof BaseVehicleMenu baseVehicleMenu) {
                baseVehicleMenu.sortVehicleInventory();
            }
        });

        context.setPacketHandled(true);
    }
}