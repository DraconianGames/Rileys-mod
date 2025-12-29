package net.riley.riley_mod.worldgen.processor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.riley.riley_mod.RileyMod;

/**
 * Structure processor that fills support blocks below any placed structure block.
 * - Overwrites liquids (will replace them) as requested.
 * - The processor encodes the support block as a ResourceLocation string 'support'.
 */
public class FillSupportProcessor extends StructureProcessor {
    public static final Codec<FillSupportProcessor> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("support").orElse(new ResourceLocation("minecraft", "cobblestone")).forGetter(p -> p.supportId)
            ).apply(instance, FillSupportProcessor::new)
    );

    public static final String DEFAULT_SUPPORT = "minecraft:cobblestone";

    private final ResourceLocation supportId;
    private final Block supportBlock;

    public FillSupportProcessor(ResourceLocation supportId) {
        this.supportId = supportId == null ? new ResourceLocation(DEFAULT_SUPPORT) : supportId;
        Block block = BuiltInRegistries.BLOCK.get(this.supportId);
        this.supportBlock = block == null ? Blocks.COBBLESTONE : block;
    }

    public ResourceLocation getSupportId() {
        return supportId;
    }

    @Override
    public StructureBlockInfo process(LevelReader worldReader, BlockPos pos, BlockPos pivot,
                                      StructureBlockInfo localBlockInfo, StructureBlockInfo worldBlockInfo,
                                      StructurePlaceSettings settings, StructureTemplate template) {
        // Use accessor methods (state() and pos()) instead of private fields.
        BlockState placedState = worldBlockInfo.state();
        if (placedState.isAir()) {
            return worldBlockInfo;
        }

        if (!(worldReader instanceof Level)) {
            return worldBlockInfo;
        }
        Level world = (Level) worldReader;

        // Start one block below the placed block position
        BlockPos current = worldBlockInfo.pos().below();
        int minY = world.getMinBuildHeight();

        // Fill downward until we hit min build height or a non-air block is found.
        // Because we want to overwrite liquids, we treat only air as stopping condition.
        while (current.getY() >= minY) {
            BlockState stateAt = world.getBlockState(current);
            if (!stateAt.isAir()) {
                break;
            }

            world.setBlock(current, supportBlock.defaultBlockState(), 2);
            current = current.below();
        }

        return worldBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        // Return the registered StructureProcessorType instance directly (not .get()).
        return RileyModStructureProcessors.FILL_SUPPORT;
    }
}