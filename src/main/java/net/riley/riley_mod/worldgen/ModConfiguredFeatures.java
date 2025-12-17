package net.riley.riley_mod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ABYSS_KEY = registerKey("abyss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FUNTIUM_ORE_KEY = registerKey("funtium_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {



        register(context, ABYSS_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ABYSS_LOG.get()),
                new ForkingTrunkPlacer(5, 7, 5),

                BlockStateProvider.simple(ModBlocks.ABYSS_LEAVES.get()),
                new FancyFoliagePlacer(ConstantInt.of(4), ConstantInt.of(1), 3),

                new TwoLayersFeatureSize(0, 0, 0)).build());

        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);


        List<OreConfiguration.TargetBlockState> overworldSapphireOres = List.of(OreConfiguration.target(stoneReplaceable,
                        ModBlocks.FUNTIUM_ORE_BLOCK.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_FUNTIUM_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_FUNTIUM_ORE_KEY, Feature.ORE, new OreConfiguration(overworldSapphireOres, 9));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}