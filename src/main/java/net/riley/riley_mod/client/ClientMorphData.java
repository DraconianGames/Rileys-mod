package net.riley.riley_mod.client;

import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ClientMorphData {
    private static final ResourceLocation PLAYER_MORPH =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player");

    private static final Map<UUID, ResourceLocation> CURRENT_MORPHS = new HashMap<>();

    private ClientMorphData() {}

    public static void setMorph(UUID playerId, ResourceLocation morphId) {
        if (PLAYER_MORPH.equals(morphId)) {
            CURRENT_MORPHS.remove(playerId);
        } else {
            CURRENT_MORPHS.put(playerId, morphId);
        }
    }

    public static ResourceLocation getMorph(UUID playerId) {
        return CURRENT_MORPHS.getOrDefault(playerId, PLAYER_MORPH);
    }

    public static boolean isMorphed(UUID playerId) {
        return CURRENT_MORPHS.containsKey(playerId);
    }
}