package net.riley.riley_mod.network;

import net.riley.riley_mod.client.ClientMorphData;

public final class ClientMorphSyncHandler {
    private ClientMorphSyncHandler() {}

    public static void handle(SyncMorphPacket msg) {
        ClientMorphData.setMorph(msg.playerId(), msg.morphId());
    }
}