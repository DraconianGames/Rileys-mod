package net.riley.riley_mod.block.entity.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.entity.SpecialSpawnerBlockEntity;

public class RileyModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RileyMod.MODID);

    public static final RegistryObject<BlockEntityType<SpecialSpawnerBlockEntity>> SPECIAL_SPAWNER_BE =
            BLOCK_ENTITIES.register("special_spawner_be",
                    () -> BlockEntityType.Builder.of(SpecialSpawnerBlockEntity::new,
                            RileyModBlocks.SPECIAL_SPAWNER.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}