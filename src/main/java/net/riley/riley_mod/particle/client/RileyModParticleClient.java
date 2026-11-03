package net.riley.riley_mod.particle.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.particle.RileyModParticles;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RileyModParticleClient {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(RileyModParticles.DETRUS_PARTICAL.get(), DetrusFallingParticle.Provider::new);
        event.registerSpriteSet(RileyModParticles.DETRUS_PARTICAL_2.get(), DetrusFallingParticle.Provider::new);
        event.registerSpriteSet(RileyModParticles.DETRUS_PARTICAL_3.get(), DetrusFallingParticle.Provider::new);
        event.registerSpriteSet(RileyModParticles.PARASITE_BLAST_PARTICLE.get(), ParasiteBlastParticle.Provider::new);
    }
}