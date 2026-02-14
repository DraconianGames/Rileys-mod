package net.riley.riley_mod.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.riley.riley_mod.RileyMod.MODID;

public class RileyModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> BLIZZARD_PARTICLE =
            PARTICLES.register("blizzard_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> BLIZZARD_PARTICLE_2 =
            PARTICLES.register("blizzard_particle_2", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> HURICANE_PARTICLE =
            PARTICLES.register("huricane_particle", () -> new SimpleParticleType(false));


    public static final RegistryObject<SimpleParticleType> HURICANE_PARTICLE_2 =
            PARTICLES.register("huricane_particle_2", () -> new SimpleParticleType(false));

    // --- NEW: detrus "weather" particles ---
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL =
            PARTICLES.register("detrus_partical", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL_2 =
            PARTICLES.register("detrus_partical_2", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL_3 =
            PARTICLES.register("detrus_partical_3", () -> new SimpleParticleType(false));

    // NEW: orbiting particle (carries center/radius/etc.)
    public static final RegistryObject<ParticleType<OrbitingParticleData>> ORBITING_PARTICLE =
            PARTICLES.register("orbiting_particle", () -> new ParticleType<OrbitingParticleData>(false, OrbitingParticleData.DESERIALIZER) {
                @Override
                public Codec<OrbitingParticleData> codec() {
                    // Keep it simple: not intended for datapack definitions, just networking/spawn
                    return Codec.unit(() -> new OrbitingParticleData(this, 0, 0, 0, 0, 0, 0, 0));
                }
            });
    public static final RegistryObject<ParticleType<OrbitingParticleData>> ORBITING_PARTICLE_2 =
            PARTICLES.register("orbiting_particle_2", () -> new ParticleType<OrbitingParticleData>(false, OrbitingParticleData.DESERIALIZER) {
                @Override
                public Codec<OrbitingParticleData> codec() {
                    // Keep it simple: not intended for datapack definitions, just networking/spawn
                    return Codec.unit(() -> new OrbitingParticleData(this, 0, 0, 0, 0, 0, 0, 0));
                }
            });

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}