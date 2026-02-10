package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.entity.SpecialSpawnerBlockEntity;
import net.riley.riley_mod.menu.SpecialSpawnerMenu;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.SpecialSpawnerSettingsPacket;
import org.lwjgl.glfw.GLFW;

public class SpecialSpawnerScreen extends AbstractContainerScreen<SpecialSpawnerMenu> {

    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/special_spawner.png");

    // IMPORTANT: set these to your PNG’s real size (common is 256x256)
    private static final int TEX_W = 256;
    private static final int TEX_H = 256;

    private EditBox mobIdBox;
    private EditBox radiusBox;
    private EditBox countBox;

    public SpecialSpawnerScreen(SpecialSpawnerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 220;
        this.imageHeight = 140;
    }

    @Override
    protected void init() {
        super.init();

        int x0 = this.leftPos;
        int y0 = this.topPos;

        this.mobIdBox = new EditBox(this.font, x0 + 12, y0 + 28, this.imageWidth - 24, 18, Component.literal("Mob ID"));
        this.mobIdBox.setMaxLength(128);
        this.addRenderableWidget(this.mobIdBox);

        this.radiusBox = new EditBox(this.font, x0 + 12, y0 + 60, 60, 18, Component.literal("Radius"));
        this.addRenderableWidget(this.radiusBox);

        this.countBox = new EditBox(this.font, x0 + 12, y0 + 92, 60, 18, Component.literal("Count"));
        this.addRenderableWidget(this.countBox);

        // Prefill from the client-side block entity (synced from server)
        String mobId = "";
        int radius = 4;
        int count = 1;

        if (this.minecraft != null && this.minecraft.level != null) {
            BlockEntity be = this.minecraft.level.getBlockEntity(this.menu.getPos());
            if (be instanceof SpecialSpawnerBlockEntity spawnerBe) {
                mobId = spawnerBe.getMobId();
                radius = spawnerBe.getRadius();
                count = spawnerBe.getCount();
            }
        }

        this.mobIdBox.setValue(mobId == null ? "" : mobId);
        this.radiusBox.setValue(Integer.toString(radius));
        this.countBox.setValue(Integer.toString(count));

        this.addRenderableWidget(Button.builder(Component.literal("Save"), b -> onSave())
                .pos(x0 + this.imageWidth - 12 - 60, y0 + this.imageHeight - 12 - 20)
                .size(60, 20)
                .build());

        this.addRenderableWidget(Button.builder(Component.literal("Close"), b -> onClose())
                .pos(x0 + this.imageWidth - 12 - 60 - 65, y0 + this.imageHeight - 12 - 20)
                .size(60, 20)
                .build());
    }

    private void onSave() {
        String mobId = mobIdBox.getValue().trim();
        int radius = clamp(parseIntOr(radiusBox.getValue(), 4), 0, 64);
        int count = clamp(parseIntOr(countBox.getValue(), 1), 1, 64);

        if (mobId.isBlank()) return;

        RileyModPackets.sendToServer(new SpecialSpawnerSettingsPacket(this.menu.getPos(), mobId, radius, count));
        this.onClose();
    }

    private static int parseIntOr(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return fallback; }
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        // Fullscreen background texture
        RenderSystem.enableBlend();
        g.blit(BG, 0, 0, 0, 0, this.width, this.height, TEX_W, TEX_H);

        // Dark panel
        g.fill(this.leftPos, this.topPos, this.leftPos + this.imageWidth, this.topPos + this.imageHeight, 0xAA000000);

        // Labels
        g.drawString(this.font, Component.literal("Mob ID (e.g. minecraft:zombie)"), this.leftPos + 12, this.topPos + 18, 0xFFFFFF, false);
        g.drawString(this.font, Component.literal("Radius"), this.leftPos + 12, this.topPos + 50, 0xFFFFFF, false);
        g.drawString(this.font, Component.literal("Count"), this.leftPos + 12, this.topPos + 82, 0xFFFFFF, false);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Prevent default title/inventory labels from drawing over your widgets
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Allow Escape to close
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }

        // Block the inventory key (default 'E') so the screen won't close while typing (or ever).
        if (this.minecraft != null && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}