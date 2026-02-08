package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;
import net.riley.riley_mod.world.DomainExpansionManager;
import net.riley.riley_mod.world.domain.DomainRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

            // Must be ACTIVE (not just unlocked)
            Set<ResourceLocation> active = AugmentData.getActive(player);

            List<ResourceLocation> domainsToCast = new ArrayList<>();
            for (ResourceLocation augmentId : active) {
                if (DomainRegistry.isDomain(augmentId)) {
                    domainsToCast.add(augmentId);
                }
            }

            if (domainsToCast.isEmpty()) {
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.literal("No active Domain Expansions selected."),
                        true
                );
                return;
            }

            DomainExpansionManager.activateBatch(player, domainsToCast);
        });
        return true;
    }
}