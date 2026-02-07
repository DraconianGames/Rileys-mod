package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;
import net.riley.riley_mod.world.DomainExpansionManager;

import java.util.function.Supplier;

public class DomainExpansionKeyPacket {

    public DomainExpansionKeyPacket() {
    }

    public DomainExpansionKeyPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation REQUIRED = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "prototype_domain_expansion");
            if (!AugmentData.isUnlocked(player, REQUIRED)) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("You haven't crafted a Domain Expansion yet."), true);
                return;
            }

            DomainExpansionManager.activate(player);
        });
        return true;
    }
}