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
    private static final ResourceLocation INVENTORY_SLOT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/inventory_slot.png");
    private static final ResourceLocation VEHICLE_UPGRADE_SLOT_LOCATION =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/vehicle_upgrade_slot.png");

    private static final int VEHICLE_INV_X = 0;
    private static final int VEHICLE_INV_Y = 0;
    private static final int UPGRADE_SLOT_X = 234;
    private static final int UPGRADE_SLOT_Y = 238;
    private static final int SLOT_SIZE = 18;

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

        RenderSystem.setShaderTexture(0, INVENTORY_SLOT_LOCATION);
        for (int row = 0; row < BaseVehicleMenu.VEHICLE_ROWS; row++) {
            for (int col = 0; col < BaseVehicleMenu.VEHICLE_COLUMNS; col++) {
                int x = left + VEHICLE_INV_X + col * SLOT_SIZE;
                int y = top + VEHICLE_INV_Y + row * SLOT_SIZE;

                guiGraphics.blit(INVENTORY_SLOT_LOCATION, x, y, 0, 0, SLOT_SIZE, SLOT_SIZE, SLOT_SIZE, SLOT_SIZE);
            }
        }

        RenderSystem.setShaderTexture(0, VEHICLE_UPGRADE_SLOT_LOCATION);
        guiGraphics.blit(
                VEHICLE_UPGRADE_SLOT_LOCATION,
                left + UPGRADE_SLOT_X,
                top + UPGRADE_SLOT_Y,
                0,
                0,
                SLOT_SIZE,
                SLOT_SIZE,
                SLOT_SIZE,
                SLOT_SIZE
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}