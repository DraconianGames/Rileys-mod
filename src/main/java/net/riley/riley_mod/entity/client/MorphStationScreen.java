package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.menu.MorphStationMenu;


public class MorphStationScreen extends AbstractContainerScreen<MorphStationMenu> {
    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/morph_station_screen.png");

    // PNG size (your GUI panel)
    private static final int TEX_W = 256;
    private static final int TEX_H = 265;

    public MorphStationScreen(MorphStationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = TEX_W;
        this.imageHeight = TEX_H;
    }

    @Override
    protected void init() {
        super.init();
        // leftPos/topPos are now centered for a 256x265 GUI.
        // Add buttons/widgets here later using leftPos/topPos offsets.
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.enableBlend();

        // Draw the GUI texture as a centered panel (NOT fullscreen)
        g.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, TEX_W, TEX_H);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Optional: draw text relative to the GUI panel
        // g.drawString(this.font, Component.literal("Morph Station"), 8, 6, 0x404040, false);
    }
}
//TODO make it detect trophies to unlock morphs
//TODO Have player morph by default
//TODO don't render the player augments when morphed and have them temporarily disabled until player is back to being a player
//TODO possible pipeline to underside of trophies or a world detection system. Pipeline sounds better on the computer.