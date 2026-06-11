package net.riley.riley_mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.SyncAugmentsPacket;
import net.riley.riley_mod.util.AugmentData;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLoginSyncEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        Set<ResourceLocation> unlocked = AugmentData.getUnlocked(player);
        Set<ResourceLocation> active = AugmentData.getActive(player);

        Map<ResourceLocation, Integer> levels = new HashMap<>();
        for (ResourceLocation id : unlocked) {
            levels.put(id, AugmentData.getLevel(player, id));
        }

        RileyModPackets.sendToPlayer(player, new SyncAugmentsPacket(unlocked, active, levels));



    }
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;


    }
}