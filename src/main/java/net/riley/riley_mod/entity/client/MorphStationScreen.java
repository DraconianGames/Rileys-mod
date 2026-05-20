package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;

import net.riley.riley_mod.menu.MorphStationMenu;
import net.riley.riley_mod.menu.MorphStationMenu;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.SetMorphPacket;

import java.util.ArrayList;
import java.util.List;

public class MorphStationScreen extends AbstractContainerScreen<MorphStationMenu> {
    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/morph_station_screen.png");

    // PNG size (your GUI panel)
    private static final int TEX_W = 256;
    private static final int TEX_H = 265;

    private static final int BORDER = 9;
    private static final int INNER_PAD = 6;

    private final List<MorphEntry> allEntries = new ArrayList<>();
    private Category expandedCategory = null;
    private MorphEntry selectedEntry = null;

    private MorphEntryList entryList;
    private Button morphButton;

    public MorphStationScreen(MorphStationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = TEX_W;
        this.imageHeight = TEX_H;

        allEntries.add(new MorphEntry(
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player"),
                "Player",
                "Your original form.",
                Category.VANILLA,
                true
        ));
        allEntries.add(new MorphEntry(
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter"),
                "Whale Hunter",
                "A powerful abyssal morph built for hunting massive prey.",
                Category.ABYSS,
                false
        ));

    }

    private enum Category {
        VANILLA("Vanilla"),
        ABYSS("Abyss"),
        MECHANICAL("Mechanical"),
        CRYSTATIC("Crystatic");

        private final String label;

        Category(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    private record MorphEntry(
            ResourceLocation morphId,
            String title,
            String description,
            Category category,
            boolean alwaysUnlocked
    ) {}
    private boolean isSelectedUnlocked() {
        return selectedEntry != null
                && (selectedEntry.alwaysUnlocked() || this.menu.getUnlockedMorphs().contains(selectedEntry.morphId()));
    }
    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        int x = this.leftPos;
        int y = this.topPos;

        int innerX = x + BORDER;
        int innerY = y + BORDER;
        int innerW = this.imageWidth - BORDER * 2;
        int innerH = this.imageHeight - BORDER * 2;

        int listX = innerX + INNER_PAD;
        int listTop = innerY + INNER_PAD;
        int listBottom = innerY + innerH - INNER_PAD;

        this.entryList = new MorphEntryList(this.minecraft, 130, listBottom - listTop, listTop, listBottom, 12);
        this.entryList.setLeftPos(listX);

        for (Category cat : Category.values()) {
            entryList.addChapter(Component.literal("> " + cat.label()), () -> {
                expandedCategory = (expandedCategory == cat) ? null : cat;
                init();
            });

            if (expandedCategory == cat) {
                for (MorphEntry entry : allEntries) {
                    if (entry.category() == cat) {
                        entryList.addLink(Component.literal(" - " + entry.title()), () -> {
                            selectedEntry = entry;

                            if (morphButton != null) {
                                morphButton.visible = true;
                                morphButton.active = isSelectedUnlocked();
                            }
                        });
                    }
                }
            }
        }

        this.addRenderableWidget(this.entryList); int bw = 72;
        int bh = 18;
        int bx = innerX + innerW - INNER_PAD - bw;
        int by = innerY + innerH - INNER_PAD - bh;

        this.morphButton = this.addRenderableWidget(Button.builder(Component.literal("Morph"), b -> {
            if (selectedEntry == null) return;
            if (!isSelectedUnlocked()) return;

            RileyModPackets.sendToServer(new SetMorphPacket(selectedEntry.morphId()));
        }).bounds(bx, by, bw, bh).build());

        this.morphButton.visible = selectedEntry != null;
        this.morphButton.active = isSelectedUnlocked();
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.enableBlend();

        // Draw the GUI texture as a centered panel (NOT fullscreen)
        g.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, TEX_W, TEX_H);

        int innerX = this.leftPos + BORDER;
        int innerY = this.topPos + BORDER;
        int innerW = this.imageWidth - BORDER * 2;
        int innerH = this.imageHeight - BORDER * 2;

        int rightX0 = innerX + 136;
        int rightY0 = innerY + INNER_PAD;
        int rightX1 = innerX + innerW - INNER_PAD;
        int rightY1 = innerY + innerH - INNER_PAD;

        int textX = rightX0;
        int textY = rightY0;
        int maxWidth = Math.max(0, rightX1 - rightX0);

        g.enableScissor(rightX0, rightY0, rightX1, rightY1);

        if (selectedEntry == null) {
            drawScaledString(g, "Morph Station", textX, textY, 0x000000, maxWidth);

            int bodyY = textY + 14;
            List<FormattedCharSequence> lines = this.font.split(Component.literal("Pick a morph category on the left."), maxWidth);

            int yy = bodyY;
            for (FormattedCharSequence line : lines) {
                g.drawString(this.font, line, textX, yy, 0x000000, false);
                yy += 9;
            }

            g.disableScissor();
            return;
        }

        drawScaledString(g, selectedEntry.title(), textX, textY, 0x000000, maxWidth);

        int bodyY = textY + 14;
        String description = selectedEntry.description();

        if (!isSelectedUnlocked()) {
            description += "\n\nLocked.";
        }

        List<FormattedCharSequence> lines = this.font.split(Component.literal(description), maxWidth);

        int yy = bodyY;
        for (FormattedCharSequence line : lines) {
            if (yy > rightY1) break;
            g.drawString(this.font, line, textX, yy, 0x000000, false);
            yy += 9;
        }

        g.disableScissor();
    }

    private void drawScaledString(GuiGraphics g, String text, int x, int y, int color, int maxWidthPx) {
        int w = this.font.width(text);
        if (w <= 0 || maxWidthPx <= 0) return;

        float scale = Math.min(1.0f, (float) maxWidthPx / (float) w);

        g.pose().pushPose();
        g.pose().translate(x, y, 0);
        g.pose().scale(scale, scale, 1.0f);
        g.drawString(this.font, text, 0, 0, color, false);
        g.pose().popPose();
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Intentionally empty to avoid default labels overlapping the texture.
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class MorphEntryList extends ContainerObjectSelectionList<MorphEntryList.Entry> {
        public MorphEntryList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
            this.setRenderBackground(false);
            this.setRenderHeader(false, 0);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            this.renderList(graphics, mouseX, mouseY, partialTicks);

            if (this.getMaxScroll() > 0) {
                int xL = this.getScrollbarPosition();
                int xR = xL + 4;

                graphics.fill(xL, this.y0, xR, this.y1, 0x22000000);

                int h = this.y1 - this.y0;
                int handleH = (int) ((float) (h * h) / (float) this.getMaxPosition());
                handleH = Math.max(10, handleH);

                int handleTop = (int) this.getScrollAmount() * (h - handleH) / this.getMaxScroll() + this.y0;

                graphics.fill(xL, handleTop, xR, handleTop + handleH, 0xFF404040);
            }
        }

        public void addChapter(Component title, Runnable onPress) {
            this.addEntry(new Entry(new TextLinkButton(0, 0, 120, 10, title, b -> onPress.run())));
        }

        public void addLink(Component title, Runnable onPress) {
            this.addEntry(new Entry(new TextLinkButton(10, 0, 110, 10, title, b -> onPress.run())));
        }

        @Override
        public int getRowWidth() {
            return 120;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.x0 + this.width - 5;
        }

        public static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            private final TextLinkButton button;

            public Entry(TextLinkButton button) {
                this.button = button;
            }

            @Override
            public void render(GuiGraphics g, int i, int top, int left, int w, int h, int mx, int my, boolean hover, float pt) {
                button.setX(left);
                button.setY(top);
                button.render(g, mx, my, pt);
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return List.of(button);
            }

            @Override
            public List<? extends NarratableEntry> narratables() {
                return List.of(button);
            }
        }
    }

    private static class TextLinkButton extends Button {
        public TextLinkButton(int x, int y, int width, int height, Component msg, OnPress onPress) {
            super(x, y, width, height, msg, onPress, DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            int color = 0x000000;

            int pad = 0;
            int maxTextWidth = Math.max(0, this.getWidth() - pad * 2);

            String full = this.getMessage().getString();
            String clipped = Minecraft.getInstance().font.plainSubstrByWidth(full, maxTextWidth);

            if (!clipped.equals(full) && maxTextWidth >= Minecraft.getInstance().font.width("...")) {
                int dotsW = Minecraft.getInstance().font.width("...");
                clipped = Minecraft.getInstance().font.plainSubstrByWidth(full, Math.max(0, maxTextWidth - dotsW)) + "...";
            }

            graphics.enableScissor(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
            graphics.drawString(Minecraft.getInstance().font, clipped, this.getX() + pad, this.getY(), color, false);
            graphics.disableScissor();
        }
    }
}
//TODO change hand texture to the morphs texture
//TODO fix animations
//TODO swimming logic for whale hunter
//TODO make morphs take on stats and hitbox size of the mob
//TODO fix rejoin affecting morph. same fix as augments table.

