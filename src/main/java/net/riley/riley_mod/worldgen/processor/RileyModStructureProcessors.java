package net.riley.riley_mod.worldgen.processor;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.riley.riley_mod.RileyMod;

/**
 * Registers structure processor types directly with the vanilla built-in registries.
 * For Forge, register into BuiltinRegistries at class load time so datapack validation
 * can find your processor types when processor_list entries are loaded.
 */
public class RileyModStructureProcessors {
    // Registered at class-load time to ensure availability during datapack/registry validation.
    public static final StructureProcessorType<FillSupportProcessor> FILL_SUPPORT =
            Registry.register(
                    BuiltInRegistries.STRUCTURE_PROCESSOR,
                    new ResourceLocation(RileyMod.MODID, "fill_support"),
                    new StructureProcessorType<FillSupportProcessor>() {
                        @Override
                        public Codec<FillSupportProcessor> codec() {
                            return FillSupportProcessor.CODEC;
                        }
                    }
            );

    // Keep a no-op register() if other code calls it; it can safely be left empty.
    public static void register() {
        // Trigger class load / static initializers if needed. No-op because static field already registers.
    }
}