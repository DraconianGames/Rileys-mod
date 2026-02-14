package net.riley.riley_mod.particle.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.particle.RileyModParticles;
import net.riley.riley_mod.worldgen.dimension.RileyModDimensions;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class DetrusWeatherClient {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.isPaused()) return;

        ClientLevel level = mc.level;
        if (level == null || mc.player == null) return;

        // Constant detrus only in your dimension (swap key if needed)
        if (level.dimension() != RileyModDimensions.FALLOWDIM_LEVEL_KEY) return;


        RandomSource rand = level.random;

        // Respect the user's particle setting (so "minimal particles" doesn't melt PCs)
        int budget = switch (mc.options.particles().get()) {
            case ALL -> 6;        // heavy
            case DECREASED -> 3;  // medium
            case MINIMAL -> 1;    // light
        };

        double px = mc.player.getX();
        double py = mc.player.getY();
        double pz = mc.player.getZ();

        double radius = 16.0;

        for (int i = 0; i < budget; i++) {
            double x = px + (rand.nextDouble() - 0.5) * 2.0 * radius;
            double z = pz + (rand.nextDouble() - 0.5) * 2.0 * radius;

            // Spawn above the player so it drifts down through view
            double y = py + 10.0 + rand.nextDouble() * 14.0;

            // Gentle motion
            double xd = (rand.nextDouble() - 0.5) * 0.01;
            double yd = -0.01 - rand.nextDouble() * 0.01;
            double zd = (rand.nextDouble() - 0.5) * 0.01;

            SimpleParticleType type = switch (rand.nextInt(3)) {
                case 0 -> RileyModParticles.DETRUS_PARTICAL.get();
                case 1 -> RileyModParticles.DETRUS_PARTICAL_2.get();
                default -> RileyModParticles.DETRUS_PARTICAL_3.get();
            };

            level.addParticle(type, x, y, z, xd, yd, zd);
        }
    }
}
/*
What to tweak
More/less constant detrus: change the budget numbers (6/3/1)
Wider coverage: increase radius (try 24.0)
Starts higher up: increase spawn y range (the 10.0 and 14.0)
Falls slower: in your DetrusFallingParticle, lower gravity (e.g. 0.002F) and/or make yd closer to -0.005
 */