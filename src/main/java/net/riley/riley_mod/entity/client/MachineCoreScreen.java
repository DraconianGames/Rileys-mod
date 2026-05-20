package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.custom.MachineCoreMultiblock;
import net.riley.riley_mod.menu.MachineCoreScreenMenu;

import java.util.List;

public class MachineCoreScreen extends AbstractContainerScreen<MachineCoreScreenMenu> {

    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/machine_core_screen.png");

    private static final int TEX_W = 256;
    private static final int TEX_H = 265;

    public MachineCoreScreen(MachineCoreScreenMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = TEX_W;
        this.imageHeight = TEX_H;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.enableBlend();
        g.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, TEX_W, TEX_H);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        if (this.minecraft == null || this.minecraft.level == null) {
            return;
        }

        List<Component> lines = MachineCoreMultiblock.getConnectedSummary(this.minecraft.level, this.menu.getPos());

        int x = 12;
        int y = 12;
        int lineHeight = 10;

        for (int i = 0; i < lines.size(); i++) {
            g.drawString(this.font, lines.get(i), x, y + i * lineHeight, 0x404040, false);
        }
    }
}
/*
cable #
special screens
trophiereaders and their trophies they are reading.
 */