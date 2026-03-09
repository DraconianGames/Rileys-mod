package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.menu.CustomMountMenu;

public class CustomMountScreen extends AbstractContainerScreen<CustomMountMenu> {
    private static final ResourceLocation HORSE_INVENTORY_LOCATION =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/horse.png");

    private static final int Y_OFFSET = 19;

    public CustomMountScreen(CustomMountMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166 + Y_OFFSET;
        this.inventoryLabelY = this.imageHeight - 112;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = this.leftPos;
        int top = this.topPos;

        RenderSystem.setShaderTexture(0, HORSE_INVENTORY_LOCATION);
        guiGraphics.blit(HORSE_INVENTORY_LOCATION, left, top, 0, 0, this.imageWidth, this.imageHeight - Y_OFFSET);

        if (this.menu.hasMountStorage()) {
            guiGraphics.blit(HORSE_INVENTORY_LOCATION,
                    left + 79,
                    top + 17,
                    0,
                    166,
                    this.menu.getStorageColumns() * 18,
                    this.menu.getStorageRows() * 18);
        }

        guiGraphics.blit(HORSE_INVENTORY_LOCATION,
                left + 7,
                top + 17,
                0,
                166,
                18,
                18);

        guiGraphics.blit(HORSE_INVENTORY_LOCATION,
                left + 7,
                top + 35 + Y_OFFSET,
                18,
                166,
                18,
                18);

        if (this.menu.getMountEntity() instanceof LivingEntity livingEntity) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    guiGraphics,
                    left + 51,
                    top + 50 + Y_OFFSET,
                    17,
                    left + 51 - mouseX,
                    top + 75 + Y_OFFSET - 50 - mouseY,
                    livingEntity
            );
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}