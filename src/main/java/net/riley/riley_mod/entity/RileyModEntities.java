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
public static final RegistryObject<EntityType<ToothFairyEntity>> TOOTHFAIRY =
        ENTITY_TYPES.register("toothfairy",()-> EntityType.Builder.of(ToothFairyEntity::new,MobCategory.CREATURE)
                .sized(.25f,0.75f).build("toothfairy"));
public static final RegistryObject<EntityType<BoneFairyEntity>> BONEFAIRY =
        ENTITY_TYPES.register("bonefairy",()-> EntityType.Builder.of(BoneFairyEntity::new,MobCategory.CREATURE)
                .sized(1f,2.5f).build("bonefairy"));
public static final RegistryObject<EntityType<SkeletonFairyEntity>> SKELETONFAIRY =
        ENTITY_TYPES.register("skeletonfairy",()-> EntityType.Builder.of(SkeletonFairyEntity::new,MobCategory.CREATURE)
                .sized(1.5f,1.5f).build("skeletonfairy"));
public static final RegistryObject<EntityType<SkullFairyEntity>> SKULL_FAIRY =
        ENTITY_TYPES.register("skull_fairy", ()-> EntityType.Builder.of(SkullFairyEntity::new,MobCategory.CREATURE)
                .sized(2.5f,2.3f).build("skull_fairy"));
public static final RegistryObject<EntityType<BisonEntity>> BISON =
        ENTITY_TYPES.register("bison",()-> EntityType.Builder.of(BisonEntity::new,MobCategory.CREATURE)
                .sized(1f,1.5f).build("bison"));
    public static final RegistryObject<EntityType<MechaRexBombEntity>> MECHAREXBOMB =
            ENTITY_TYPES.register("mecharexbomb", () ->
                    EntityType.Builder.<MechaRexBombEntity>of(MechaRexBombEntity::new, MobCategory.MISC)
                            .sized(0.75f, 0.75f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("mecharexbomb")
            );
    public static final RegistryObject<EntityType<MechaRexEntity>> MECHAREX =
            ENTITY_TYPES.register("mecharex", ()-> EntityType.Builder.of(MechaRexEntity::new,MobCategory.CREATURE)
                    .sized(4f,3.5f).build("mecharex"));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
