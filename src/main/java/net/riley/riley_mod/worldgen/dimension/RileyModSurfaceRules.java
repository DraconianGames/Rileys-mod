package net.riley.riley_mod.worldgen.dimension;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.worldgen.biome.RileyModBiomes;

public class RileyModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(RileyModBlocks.ABYSSAL_DIRT.get());
    private static final SurfaceRules.RuleSource GRASS = makeStateRule(RileyModBlocks.ABYSSAL_GRASS.get());
    private static final SurfaceRules.RuleSource STONE = makeStateRule(RileyModBlocks.ABYSSAL_STONE.get());
    private static final SurfaceRules.RuleSource SAND = makeStateRule(RileyModBlocks.BLACK_SAND.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtSurface = SurfaceRules.ON_FLOOR;
        SurfaceRules.ConditionSource isAboveWater = SurfaceRules.waterBlockCheck(0, 0);

        // Rule for Grass/Dirt biomes (Plains, Forest)
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(
                // SURFACE LAYER
                SurfaceRules.ifTrue(isAtSurface,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isAboveWater, GRASS),
                                STONE // This makes the ocean floor stone instead of dirt
                        )
                ),
                // SUBSURFACE (The layers just below the grass/ocean floor)
                // If you want a few layers of dirt under the grass on land:
                SurfaceRules.ifTrue(SurfaceRules.stoneDepthCheck(0, true, CaveSurface.FLOOR),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isAboveWater, DIRT),
                                STONE // Underwater subsurface is also stone
                        )
                ),
                // DEEP UNDERGROUND
                STONE
        );

        // Rule for Sand biomes (Beach)
        SurfaceRules.RuleSource sandSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(isAtSurface, SAND),
                STONE
        );

        return SurfaceRules.sequence(
                // Apply Grass to Plains and Forest
                SurfaceRules.ifTrue(SurfaceRules.isBiome(RileyModBiomes.ABYSS_PLAINS, RileyModBiomes.ABYSS_FOREST_BIOME), grassSurface),

                // Apply Sand to Beach
                SurfaceRules.ifTrue(SurfaceRules.isBiome(RileyModBiomes.ABYSS_BEACH), sandSurface),

                // Default filler for other biomes (Trench, etc)
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, STONE)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}