package net.riley.riley_mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.riley.riley_mod.RileyMod;

public class RileyModPackets {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() { return packetId++; }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RileyMod.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(PetActionPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PetActionPacket::new)
                .encoder(PetActionPacket::toBytes)
                .consumerMainThread(PetActionPacket::handle)
                .add();

        net.messageBuilder(SpecialSpawnerSettingsPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SpecialSpawnerSettingsPacket::new)
                .encoder(SpecialSpawnerSettingsPacket::toBytes)
                .consumerMainThread(SpecialSpawnerSettingsPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
}