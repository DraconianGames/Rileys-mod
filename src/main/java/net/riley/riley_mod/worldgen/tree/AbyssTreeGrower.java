package net.riley.riley_mod.worldgen.tree;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.riley.riley_mod.worldgen.RileyModConfiguredFeatures;

import javax.annotation.Nullable;

public class AbyssTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return RileyModConfiguredFeatures.ABYSS_KEY;
    }
}