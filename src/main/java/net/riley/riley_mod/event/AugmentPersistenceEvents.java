package net.riley.riley_mod.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AugmentPersistenceEvents {
    private static final String ROOT = "riley_mod.augments";

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;
        if (!(event.getOriginal() instanceof ServerPlayer oldPlayer)) return;

        CompoundTag oldTag = oldPlayer.getPersistentData().getCompound(ROOT).copy();
        newPlayer.getPersistentData().put(ROOT, oldTag);
    }
}