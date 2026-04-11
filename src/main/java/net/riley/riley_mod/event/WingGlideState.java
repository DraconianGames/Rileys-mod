package net.riley.riley_mod.event;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class WingGlideState {
    private static final Set<UUID> GLIDING = new HashSet<>();

    private WingGlideState() {}

    public static void request(ServerPlayer player) {
        GLIDING.add(player.getUUID());
    }

    public static boolean isGliding(ServerPlayer player) {
        return GLIDING.contains(player.getUUID());
    }

    public static void clear(ServerPlayer player) {
        GLIDING.remove(player.getUUID());
    }
}