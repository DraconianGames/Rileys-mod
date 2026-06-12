package net.riley.riley_mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WingAugmentEvents {
    private static final ResourceLocation WINGS_ID =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        if (!AugmentData.isUnlocked(player, WINGS_ID) || !AugmentData.isActive(player, WINGS_ID)) {
            WingGlideState.clear(player);
            return;
        }

        int level = AugmentData.getLevel(player, WINGS_ID);
        if (level < 1) {
            WingGlideState.clear(player);
            return;
        }

        if (player.onGround() || player.isInWater() || player.isInLava()) {
            WingGlideState.clear(player);
            return;
        }

        // Level 1: hold sneak for feather-fall behavior
        if (player.isShiftKeyDown()) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 5, 0, false, false, false));
            return;
        }

        // If glide was requested, keep it active until landing
        if (level >= 2 && WingGlideState.isGliding(player)) {
            if (!player.isFallFlying()) {
                player.startFallFlying();
            }
        }
    }
}