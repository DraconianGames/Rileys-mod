package net.riley.riley_mod.network;

import net.minecraft.client.Minecraft;
import net.riley.riley_mod.client.ClientAugmentState;
import net.riley.riley_mod.entity.client.AugmentationStationScreen;
import net.riley.riley_mod.menu.AugmentationStationMenu;

public final class ClientAugmentSyncHandler {
    private ClientAugmentSyncHandler() {}

    public static void handle(SyncAugmentsPacket msg) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Universal client cache (used for mining speed etc.)
        ClientAugmentState.apply(msg.active(), msg.levels());

        // Update station UI if it's open
        if (mc.screen instanceof AugmentationStationScreen screen) {
            screen.applySync(msg.unlocked(), msg.active(), msg.levels());
        }

        // Update current menu state if player currently has it open
        if (mc.player.containerMenu instanceof AugmentationStationMenu menu) {
            menu.setClientState(msg.unlocked(), msg.active(), msg.levels());
        }
    }
}