package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.event.WingGlideState;
import net.riley.riley_mod.util.AugmentData;

import java.util.function.Supplier;

public record WingJumpPacket() {
    public static void encode(WingJumpPacket msg, FriendlyByteBuf buf) {
    }

    public static WingJumpPacket decode(FriendlyByteBuf buf) {
        return new WingJumpPacket();
    }

    public static void handle(WingJumpPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
            if (AugmentData.getLevel(player, wingsId) < 2) return;
            if (!AugmentData.isActive(player, wingsId)) return;

            if (player.onGround() || player.isInWater() || player.isInLava()) return;

            // Must already be falling, or it will activate from a ground jump.
            if (player.fallDistance <= 0.0F) return;
            if (player.getDeltaMovement().y >= 0.0D) return;

            WingGlideState.request(player);
        });
        ctx.setPacketHandled(true);
    }
}