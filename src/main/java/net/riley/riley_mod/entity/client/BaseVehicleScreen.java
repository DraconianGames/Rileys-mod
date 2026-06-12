package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.menu.BaseVehicleMenu;

public class BaseVehicleScreen extends AbstractContainerScreen<BaseVehicleMenu> {
    private static final ResourceLocation VEHICLE_MENU_LOCATION =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/vehicle_menu.png");

    public BaseVehicleScreen(BaseVehicleMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top = this.topPos;

        RenderSystem.setShaderTexture(0, VEHICLE_MENU_LOCATION);
        guiGraphics.blit(VEHICLE_MENU_LOCATION, left, top, 0, 0, 256, 256);

    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}