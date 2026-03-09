package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.menu.TrisonCartMenu;

public class TrisonCartScreen extends AbstractContainerScreen<TrisonCartMenu> {
    private static final ResourceLocation BACKGROUND =
            ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/gui/trison_cart_screen.png");
    private static final ResourceLocation COVER_BUTTON =
            ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/gui/buttons/cart_cover_button.png");
    private static final ResourceLocation UNCOVER_BUTTON =
            ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/gui/buttons/cart_uncover_button.png");

    public static final int GUI_WIDTH = 256;
    public static final int GUI_HEIGHT = 256;

    public static final int CART_INV_X = 9;
    public static final int CART_INV_Y = 9;

    public static final int PLAYER_INV_X = 10;
    public static final int PLAYER_INV_Y = 174;
    public static final int HOTBAR_Y = 228;

    public static final int COVER_BUTTON_X = 228;
    public static final int COVER_BUTTON_Y = 238;
    public static final int COVER_BUTTON_WIDTH = 18;
    public static final int COVER_BUTTON_HEIGHT = 8;

    public TrisonCartScreen(TrisonCartMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
        this.titleLabelX = 9;
        this.titleLabelY = 87;
        this.inventoryLabelX = PLAYER_INV_X;
        this.inventoryLabelY = PLAYER_INV_Y - 12;
    }

    @Override
    protected void init() {
        super.init();
    }

    private int getCoverButtonScreenX() {
        return this.leftPos + COVER_BUTTON_X;
    }

    private int getCoverButtonScreenY() {
        return this.topPos + COVER_BUTTON_Y;
    }

    private boolean isHoveringCoverButton(double mouseX, double mouseY) {
        int x = this.getCoverButtonScreenX();
        int y = this.getCoverButtonScreenY();
        return mouseX >= x && mouseX < x + COVER_BUTTON_WIDTH && mouseY >= y && mouseY < y + COVER_BUTTON_HEIGHT;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isHoveringCoverButton(mouseX, mouseY)) {
            this.menu.toggleCartCover();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, BACKGROUND);
        guiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        ResourceLocation buttonTexture = this.menu.isCartCovered() ? UNCOVER_BUTTON : COVER_BUTTON;
        guiGraphics.blit(
                buttonTexture,
                this.getCoverButtonScreenX(),
                this.getCoverButtonScreenY(),
                0,
                0,
                COVER_BUTTON_WIDTH,
                COVER_BUTTON_HEIGHT,
                COVER_BUTTON_WIDTH,
                COVER_BUTTON_HEIGHT
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if (this.isHoveringCoverButton(mouseX, mouseY)) {
            Component tooltip = this.menu.isCartCovered()
                    ? Component.literal("Remove Cover")
                    : Component.literal("Add Cover");
            guiGraphics.renderTooltip(this.font, tooltip, mouseX, mouseY);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
