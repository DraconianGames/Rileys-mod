package net.riley.riley_mod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AugmentHudOverlay {
    private static final ResourceLocation AUGMENT_BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/augment_backgound.png");

    private static final int BACKGROUND_SIZE = 20;
    private static final int ICON_SIZE = 16;
    private static final int ICON_OFFSET = (BACKGROUND_SIZE - ICON_SIZE) / 2;
    private static final int ICON_SPACING = 22;

    private static final ResourceLocation REINFORCED_MUSCULATURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "reinforced_musculature");
    private static final ResourceLocation WINGS = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
    private static final ResourceLocation FROST_DOMAIN = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion");
    private static final ResourceLocation HURICANE_DOMAIN = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion");



    private static final Map<ResourceLocation, ResourceLocation> AUGMENT_ICONS = new HashMap<>();

    static {
        AUGMENT_ICONS.put(
                REINFORCED_MUSCULATURE,
                ResourceLocation.fromNamespaceAndPath(
                        RileyMod.MODID,
                        "textures/icons/reinforced_musculature_augment_icon.png"
                )
        );
        AUGMENT_ICONS.put(
                WINGS,
                ResourceLocation.fromNamespaceAndPath(
                        RileyMod.MODID,
                        "textures/icons/wings_icon.png"
                )
        );
        AUGMENT_ICONS.put(
                FROST_DOMAIN,
                ResourceLocation.fromNamespaceAndPath(
                        RileyMod.MODID,
                        "textures/icons/frost_domain_icon.png"
                )
        );
        AUGMENT_ICONS.put(
                HURICANE_DOMAIN,
                ResourceLocation.fromNamespaceAndPath(
                        RileyMod.MODID,
                        "textures/icons/hurricane_domain_icon.png"
                )
        );
    }

    private AugmentHudOverlay() {}

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) return;
        if (minecraft.options.hideGui) return;

        Set<ResourceLocation> activeAugments = ClientAugmentState.getActiveSnapshot();
        if (activeAugments.isEmpty()) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();

        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        int x = 6;
        int y = screenHeight - 28;

        int rendered = 0;

        for (ResourceLocation augmentId : activeAugments) {
            ResourceLocation icon = AUGMENT_ICONS.get(augmentId);
            if (icon == null) continue;

            int iconX = x;
            int iconY = y - rendered * ICON_SPACING;

            renderIconWithBackground(guiGraphics, icon, iconX, iconY);

            rendered++;
        }
    }

    private static void renderIconWithBackground(GuiGraphics guiGraphics, ResourceLocation icon, int x, int y) {
        RenderSystem.enableBlend();

        guiGraphics.blit(
                AUGMENT_BACKGROUND,
                x,
                y,
                0,
                0,
                BACKGROUND_SIZE,
                BACKGROUND_SIZE,
                BACKGROUND_SIZE,
                BACKGROUND_SIZE
        );

        guiGraphics.blit(
                icon,
                x + ICON_OFFSET,
                y + ICON_OFFSET,
                0,
                0,
                ICON_SIZE,
                ICON_SIZE,
                ICON_SIZE,
                ICON_SIZE
        );

        RenderSystem.disableBlend();
    }
}