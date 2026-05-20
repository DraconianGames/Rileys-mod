package net.riley.riley_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.custom.MachineCoreMultiblock;
import net.riley.riley_mod.menu.MorphStationMenu;
import net.riley.riley_mod.util.MorphData;

import java.util.Set;
import java.util.function.Supplier;

public record SetMorphPacket(ResourceLocation morphId) {
    private static final ResourceLocation PLAYER_MORPH =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player");

    public static void encode(SetMorphPacket msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.morphId);
    }

    public static SetMorphPacket decode(FriendlyByteBuf buf) {
        return new SetMorphPacket(buf.readResourceLocation());
    }

    public static void handle(SetMorphPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            ResourceLocation morphId = msg.morphId();

            if (!PLAYER_MORPH.equals(morphId)) {
                if (!(player.containerMenu instanceof MorphStationMenu morphMenu)) {
                    return;
                }

                Set<ResourceLocation> storedTrophies = MachineCoreMultiblock.getStoredTrophiesForConnectedDevice(
                        player.level(),
                        morphMenu.getPos()
                );

                boolean unlocked = false;

                if (morphId.equals(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter"))) {
                    unlocked = storedTrophies.contains(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter_trophy"));
                }

                if (!unlocked) {
                    return;
                }
            }

            MorphData.setCurrentMorph(player, morphId);
            RileyModPackets.sendToPlayer(player, new SyncMorphPacket(player.getUUID(), morphId));
        });

        ctx.setPacketHandled(true);
    }
}