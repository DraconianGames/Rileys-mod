package net.riley.riley_mod.particle;

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

    public static final RegistryObject<SimpleParticleType> HURICANE_PARTICLE =
            PARTICLES.register("huricane_particle", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> HURICANE_PARTICLE_2 =
            PARTICLES.register("huricane_particle_2", () -> new SimpleParticleType(false));

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}