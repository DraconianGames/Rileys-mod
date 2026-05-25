package net.riley.riley_mod.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;

import java.util.List;

public final class MorphCatalog {
    private MorphCatalog() {}

    public static List<MorphStationScreen.MorphEntry> getMorphs() {
        return List.of(
                new MorphStationScreen.MorphEntry(
                        ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player"),
                        "Player",
                        "Your original form.",
                        MorphStationScreen.Category.VANILLA,
                        true
                ),
                new MorphStationScreen.MorphEntry(
                        ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "sunless_crab"),
                        "Sunless Crab",
                        "A sturdy land-dwelling abyssal crab.",
                        MorphStationScreen.Category.ABYSS,
                        true
                ),
                new MorphStationScreen.MorphEntry(
                        ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter"),
                        "Whale Hunter",
                        "A powerful abyssal morph built for hunting massive prey.",
                        MorphStationScreen.Category.ABYSS,
                        false
                )
        );
    }
}