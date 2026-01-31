package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.riley.riley_mod.entity.client.SpecialSpawnerScreen;

public final class SpecialSpawnerClient {
    private SpecialSpawnerClient() {}

    /**
     * No longer used: the Special Spawner UI is opened via NetworkHooks + MenuScreens registration.
     */
    public static void openScreen(BlockPos pos) {
        // Intentionally empty.
    }
}