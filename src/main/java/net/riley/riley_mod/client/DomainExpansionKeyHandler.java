package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.entity.client.PetScreen;
import net.riley.riley_mod.network.DomainExpansionKeyPacket;
import net.riley.riley_mod.network.RegisterRiddenCompanionPacket;
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

        while (RileyModKeyMappings.PET_MENU.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player == null || minecraft.level == null) {
                return;
            }

            Entity vehicle = minecraft.player.getVehicle();

            if (vehicle != null && minecraft.screen == null) {
                RileyModPackets.sendToServer(new RegisterRiddenCompanionPacket());
                return;
            }

            if (minecraft.screen == null) {
                minecraft.setScreen(new PetScreen());
            }
        }
    }
}