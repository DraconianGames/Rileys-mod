package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.event.WingGlideState;
import net.riley.riley_mod.util.AugmentData;

import java.util.function.Supplier;

public record WingFlapPacket() {
    public static void encode(WingFlapPacket msg, FriendlyByteBuf buf) {
    }

    public static WingFlapPacket decode(FriendlyByteBuf buf) {
        return new WingFlapPacket();
    }

    public static void handle(WingFlapPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
            if (AugmentData.getLevel(player, wingsId) < 3) return;
            if (!AugmentData.isActive(player, wingsId)) return;

            if (!WingGlideState.isGliding(player)) return;
            if (player.onGround() || player.isInWater() || player.isInLava()) return;

            Vec3 look = player.getLookAngle().normalize();
            Vec3 motion = player.getDeltaMovement();

            // Forward boost based on look direction, with a small upward lift
            Vec3 boost = look.scale(0.9D).add(0.0D, 0.35D, 0.0D);

            player.setDeltaMovement(
                    motion.x * 0.8D + boost.x,
                    Math.max(motion.y, -0.05D) + boost.y,
                    motion.z * 0.8D + boost.z
            );

            player.fallDistance = 0.0F;
            player.hurtMarked = true;
        });
        ctx.setPacketHandled(true);
    }
}