package net.riley.riley_mod.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT)
public final class AugmentMiningSpeedClientHandler {

    private static final ResourceLocation REINFORCED_MUSCULATURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "reinforced_musculature");

    private AugmentMiningSpeedClientHandler() {}

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        int level = ClientAugmentState.getLevel(REINFORCED_MUSCULATURE);
        if (level <= 0) return;
        if (!ClientAugmentState.isActive(REINFORCED_MUSCULATURE)) return;

        // Linear scaling:
        // level 1 => 2x speed, level 2 => 3x, etc.
        float multiplier = 1.0f + level;
        event.setNewSpeed(event.getNewSpeed() * multiplier);
    }
}