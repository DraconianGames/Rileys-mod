package net.riley.riley_mod.util;

import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;

import java.util.Set;

public final class OceanMorphs {
    private OceanMorphs() {}

    private static final Set<ResourceLocation> OCEAN_MORPHS = Set.of(
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter")
    );

    public static boolean isOceanMorph(ResourceLocation morphId) {
        return OCEAN_MORPHS.contains(morphId);
    }
}