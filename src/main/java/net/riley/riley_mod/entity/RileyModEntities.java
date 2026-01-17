package net.riley.riley_mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.*;


public class RileyModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
     DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RileyMod.MODID);

    public static final RegistryObject<EntityType<SunlessCrabEntity>> SUNLESS_CRAB =
            ENTITY_TYPES.register("sunless_crab",()-> EntityType.Builder.of(SunlessCrabEntity::new, MobCategory.MONSTER)
                    .sized(2.5f,1.5f).build("sunless_crab"));

public static final RegistryObject<EntityType<RapterEntity>> RAPTER =
        ENTITY_TYPES.register("rapter",()-> EntityType.Builder.of(RapterEntity::new,MobCategory.MONSTER)
                .sized(1f,1f).build("rapter"));
public static final RegistryObject<EntityType<NightTerrorEntity>> NIGHT_TERROR =
        ENTITY_TYPES.register("night_terror",()-> EntityType.Builder.of(NightTerrorEntity::new,MobCategory.MONSTER)
                .sized(2f,1.3f).build("night_terror"));
public static final RegistryObject<EntityType<WhaleHunterEntity>> WHALE_HUNTER =
        ENTITY_TYPES.register("whalehunter",()-> EntityType.Builder.of(WhaleHunterEntity::new,MobCategory.WATER_CREATURE)
                .sized(3f,1f).build("whalehunter"));
    public static final RegistryObject<EntityType<FrostHopperEntity>> FROST_HOPPER =
            ENTITY_TYPES.register("frost_hopper",()-> EntityType.Builder.of(FrostHopperEntity::new,MobCategory.MONSTER)
                    .sized(1f,1f).build("frost_hopper"));



    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
