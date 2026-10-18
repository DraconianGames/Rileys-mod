package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.event.WingGlideState;
import net.riley.riley_mod.util.AugmentData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;


public record WingJumpPacket(boolean groundLaunch) {
    private static final double GROUND_LAUNCH_Y_VELOCITY = 1.35D;
    private static final int GROUND_LAUNCH_COOLDOWN_TICKS = 10;
    private static final Map<UUID, Integer> LAST_GROUND_LAUNCH_TICK = new HashMap<>();

    public static void encode(WingJumpPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.groundLaunch);
    }

    public static WingJumpPacket decode(FriendlyByteBuf buf) {
        return new WingJumpPacket(buf.readBoolean());
    }

    public static void handle(WingJumpPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
            int level = AugmentData.getLevel(player, wingsId);

            if (!AugmentData.isActive(player, wingsId)) return;

            if (msg.groundLaunch()) {
                if (level < 4) return;
            } else if (level < 2) {
                return;
            }

            if (player.isInWater() || player.isInLava()) return;

            if (msg.groundLaunch()) {
                if (level < 4) return;

                int lastLaunchTick = LAST_GROUND_LAUNCH_TICK.getOrDefault(player.getUUID(), -GROUND_LAUNCH_COOLDOWN_TICKS);
                if (player.tickCount - lastLaunchTick < GROUND_LAUNCH_COOLDOWN_TICKS) return;

                if (!player.onGround() && !isCloseToGroundForLaunch(player)) return;

                Vec3 motion = player.getDeltaMovement();
                player.setDeltaMovement(motion.x, GROUND_LAUNCH_Y_VELOCITY, motion.z);

                player.setOnGround(false);
                player.fallDistance = 0.0F;
                player.hurtMarked = true;

                LAST_GROUND_LAUNCH_TICK.put(player.getUUID(), player.tickCount);
                WingGlideState.request(player);
                return;
            }

            if (player.onGround()) return;

            // Must already be falling, or it will activate from a normal upward jump.
            if (player.fallDistance <= 0.0F) return;
            if (player.getDeltaMovement().y >= 0.0D) return;

            player.fallDistance = 0.0F;
            WingGlideState.request(player);
        });
        ctx.setPacketHandled(true);
    }


    private static boolean isCloseToGroundForLaunch(ServerPlayer player) {
        Vec3 start = player.position();
        Vec3 end = start.add(0.0D, -1.0D, 0.0D);

        return player.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        )).getType() == HitResult.Type.BLOCK;
    }
}