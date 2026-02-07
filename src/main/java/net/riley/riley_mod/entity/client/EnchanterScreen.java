package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.menu.EnchanterMenu;

public class EnchanterScreen extends AbstractContainerScreen<EnchanterMenu> {

    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/enchanter_screen.png");

    private static final int TEX_W = 256;
    private static final int TEX_H = 265;

    public EnchanterScreen(EnchanterMenu menu, Inventory inv, Component title) {
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
        // keep empty unless you want labels
    }
}
//TODO add section below everything to see the inventory
//TODO add slot where one puts item to enchant
//TODO add scrolling list that displays enchantments and levels
//TODO add increasing cost for every level + tracking the current level of the item
//TODO it cost exp