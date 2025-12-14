package net.riley.riley_mod.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(new ResourceLocation(RileyMod.MODID, "overworld"), 5));
    }
}