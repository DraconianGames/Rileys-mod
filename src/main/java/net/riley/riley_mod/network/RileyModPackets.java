package net.riley.riley_mod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.riley.riley_mod.RileyMod;

import java.util.function.Supplier;

public class RileyModPackets {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() { return packetId++; }


    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "messages"))
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

        net.messageBuilder(DomainExpansionKeyPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DomainExpansionKeyPacket::new)
                .encoder(DomainExpansionKeyPacket::toBytes)
                .consumerMainThread(DomainExpansionKeyPacket::handle)
                .add();

        // --- Augmentation Station ---
        net.messageBuilder(CraftAugmentPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(CraftAugmentPacket::encode)
                .decoder(CraftAugmentPacket::decode)
                .consumerMainThread(AugmentationStationNetHandlers::handleCraft)
                .add();

        net.messageBuilder(SetAugmentActivePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(SetAugmentActivePacket::encode)
                .decoder(SetAugmentActivePacket::decode)
                .consumerMainThread(AugmentationStationNetHandlers::handleSetActive)
                .add();

        net.messageBuilder(SyncAugmentsPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SyncAugmentsPacket::encode)
                .decoder(SyncAugmentsPacket::decode)
                .consumerMainThread(RileyModPackets::handleSyncAugmentsClientSafe)
                .add();
    }

    private static void handleSyncAugmentsClientSafe(SyncAugmentsPacket msg, Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientAugmentSyncHandler.handle(msg)));
        ctx.setPacketHandled(true);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);

    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}