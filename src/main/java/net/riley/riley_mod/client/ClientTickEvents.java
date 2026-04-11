package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.WingFlapPacket;
import net.riley.riley_mod.network.WingJumpPacket;
import net.riley.riley_mod.network.WingSneakPacket;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTickEvents {
    private static boolean lastSneakingState = false;
    private static boolean lastJumpState = false;
    private static int wingFlapTicks = 0;
    private static boolean sneaking = false;

    public static int getWingFlapTicks() {
        return wingFlapTicks;
    }

    public static boolean isSneaking() {
        return sneaking;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (wingFlapTicks > 0) {
            --wingFlapTicks;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            lastSneakingState = false;
            lastJumpState = false;
            wingFlapTicks = 0;
            sneaking = false;
            return;
        }

        sneaking = player.isShiftKeyDown();

        if (sneaking != lastSneakingState) {
            lastSneakingState = sneaking;
            RileyModPackets.sendToServer(new WingSneakPacket(sneaking));
        }

        boolean jumping = Minecraft.getInstance().options.keyJump.isDown();
        boolean jumpPressed = jumping && !lastJumpState;

        if (jumpPressed && !player.onGround() && !player.isInWater() && !player.isInLava()) {
            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
            int level = ClientAugmentState.getLevel(wingsId);

            if (level >= 3 && player.isFallFlying()) {
                wingFlapTicks = 40;
                RileyModPackets.sendToServer(new WingFlapPacket());
            } else if (level >= 2) {
                RileyModPackets.sendToServer(new WingJumpPacket());
            }
        }

        lastJumpState = jumping;
    }
}