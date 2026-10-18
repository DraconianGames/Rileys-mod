package net.riley.riley_mod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class AugmentInventoryEffectRenderer {
    private static final ResourceLocation INVENTORY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/inventory.png");

    /*
     * Later, when you make a custom augment/domain background, replace this texture:
     *
     * private static final ResourceLocation AUGMENT_BACKGROUND =
     *         ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/augment_effect_background.png");
     */

    private static final int PANEL_WIDTH = 120;
    private static final int PANEL_HEIGHT = 32;

    private static final int INVENTORY_WIDTH = 176;
    private static final int INVENTORY_HEIGHT = 166;

    private static final int ICON_SIZE = 18;
    private static final int PANEL_SPACING = 33;

    private static final ResourceLocation REINFORCED_MUSCULATURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "reinforced_musculature");
    private static final ResourceLocation WINGS = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
    private static final ResourceLocation FROST_DOMAIN = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion");
    private static final ResourceLocation HURICANE_DOMAIN = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion");


    private static final Map<ResourceLocation, ResourceLocation> AUGMENT_ICONS = new HashMap<>();
    private static final Map<ResourceLocation, Component> AUGMENT_NAMES = new HashMap<>();

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
        AUGMENT_NAMES.put(
                REINFORCED_MUSCULATURE,
                Component.translatable("augment.riley_mod.reinforced_musculature")
        );
        AUGMENT_NAMES.put(
                WINGS,
                Component.translatable("augment.riley_mod.wings")
        );
        AUGMENT_NAMES.put(
                FROST_DOMAIN,
                Component.translatable("augment.riley_mod.frost_domain")
        );
        AUGMENT_NAMES.put(
                HURICANE_DOMAIN,
                Component.translatable("augment.riley_mod.huricane_domain")
        );
    }

    private AugmentInventoryEffectRenderer() {}

    @SubscribeEvent
    public static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        if (!(event.getScreen() instanceof InventoryScreen)) return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        Set<ResourceLocation> activeAugments = ClientAugmentState.getActiveSnapshot();
        if (activeAugments.isEmpty()) return;

        int leftPos = (event.getScreen().width - INVENTORY_WIDTH) / 2;
        int topPos = (event.getScreen().height - INVENTORY_HEIGHT) / 2;

        int panelX = leftPos - PANEL_WIDTH - 4;
        int panelY = topPos;

        GuiGraphics guiGraphics = event.getGuiGraphics();

        int rendered = 0;

        for (ResourceLocation augmentId : activeAugments) {
            ResourceLocation icon = AUGMENT_ICONS.get(augmentId);
            if (icon == null) continue;

            Component name = AUGMENT_NAMES.getOrDefault(
                    augmentId,
                    Component.literal(augmentId.getPath())
            );

            int level = ClientAugmentState.getLevel(augmentId);

            int y = panelY + rendered * PANEL_SPACING;

            renderAugmentPanel(guiGraphics, minecraft, icon, name, level, panelX, y);

            rendered++;
        }
    }

    private static void renderAugmentPanel(
            GuiGraphics guiGraphics,
            Minecraft minecraft,
            ResourceLocation icon,
            Component name,
            int level,
            int x,
            int y
    ) {
        RenderSystem.enableBlend();

        /*
         * This uses Minecraft's potion-effect inventory background.
         * In inventory.png, the effect panel is 120x32 at texture position 0,166.
         */
        guiGraphics.blit(
                INVENTORY_TEXTURE,
                x,
                y,
                0,
                166,
                PANEL_WIDTH,
                PANEL_HEIGHT
        );

        guiGraphics.blit(
                icon,
                x + 6,
                y + 7,
                0,
                0,
                ICON_SIZE,
                ICON_SIZE,
                ICON_SIZE,
                ICON_SIZE
        );

        guiGraphics.drawString(
                minecraft.font,
                name,
                x + 30,
                y + 7,
                0xFFFFFF,
                false
        );

        guiGraphics.drawString(
                minecraft.font,
                Component.literal("Level " + level),
                x + 30,
                y + 18,
                0x7F7F7F,
                false
        );

        RenderSystem.disableBlend();
    }
}