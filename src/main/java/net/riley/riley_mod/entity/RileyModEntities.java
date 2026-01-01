package net.riley.riley_mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;

public class RileyModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
     DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RileyMod.MODID);

public static final RegistryObject<EntityType<SunlessCrabEntity>> SUNLESS_CRAB =
        ENTITY_TYPES.register("sunless_crab",()-> EntityType.Builder.of(SunlessCrabEntity::new, MobCategory.CREATURE)
                .sized(2.5f,2.5f).build("sunless_crab"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
