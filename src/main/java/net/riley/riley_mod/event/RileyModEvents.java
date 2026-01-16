package net.riley.riley_mod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.effect.RileyModEffects;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RileyModEvents {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        Player player = Minecraft.getInstance().player;

        // If the local player has the DEAF effect, we set the result sound to null
        if (player != null && player.hasEffect(RileyModEffects.DEAF.get())) {
            event.setSound(null);
        }
    }
}