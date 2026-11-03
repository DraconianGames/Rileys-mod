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


    // --- NEW: detrus "weather" particles ---
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL =
            PARTICLES.register("detrus_partical", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL_2 =
            PARTICLES.register("detrus_partical_2", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DETRUS_PARTICAL_3 =
            PARTICLES.register("detrus_partical_3", () -> new SimpleParticleType(false));


    public static final RegistryObject<ParticleType<ParasiteBlastParticleData>> PARASITE_BLAST_PARTICLE =
            PARTICLES.register("parasite_blast_particle", () -> new ParticleType<ParasiteBlastParticleData>(false, ParasiteBlastParticleData.DESERIALIZER) {
                @Override
                public Codec<ParasiteBlastParticleData> codec() {
                    return Codec.unit(() -> new ParasiteBlastParticleData(this, 0, 0, 0, 0, 0, 0, 0));
                }
            });

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}