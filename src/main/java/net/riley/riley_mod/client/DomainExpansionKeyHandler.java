package net.riley.riley_mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.network.DomainExpansionKeyPacket;
import net.riley.riley_mod.network.RileyModPackets;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class DomainExpansionKeyHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        while (RileyModKeyMappings.DOMAIN_EXPANSION.consumeClick()) {
            RileyModPackets.sendToServer(new DomainExpansionKeyPacket());
        }
    }
}