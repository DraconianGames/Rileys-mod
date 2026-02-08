package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.CraftAugmentPacket;
import net.riley.riley_mod.network.SetAugmentActivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.menu.AugmentationStationMenu;
import net.riley.riley_mod.recipe.AugmentationStationRecipe;
import net.riley.riley_mod.recipe.AugmentationStationRecipeType;
import net.riley.riley_mod.recipe.Requirement;
import java.util.Optional;

import java.util.*;

public class AugmentationStationScreen extends AbstractContainerScreen<AugmentationStationMenu> {

    private static final ResourceLocation BG =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/augmentation_station_screen.png");

    private static final int TEX_W = 256;
    private static final int TEX_H = 265;

    private static final int BORDER = 9;      // 9px border in the PNG
    private static final int INNER_PAD = 6;   // padding inside the inner content area (keeps text away from edges)

    private final List<AugmentEntry> allEntries = new ArrayList<>();
    private Category expandedCategory = null;
    private AugmentEntry selectedEntry = null;

    private AugmentEntryList entryList;

    private double descriptionScroll = 0;

    private Set<ResourceLocation> unlocked = new HashSet<>();
    private Set<ResourceLocation> active = new HashSet<>();

    private Button craftButton;
    private Button activateButton;
    private Button deactivateButton;

    private Map<ResourceLocation, Integer> levels = new HashMap<>();

    private Button upgradeButton;

    public void applySync(Set<ResourceLocation> unlocked, Set<ResourceLocation> active, Map<ResourceLocation, Integer> levels) {
        this.unlocked = unlocked;
        this.active = active;
        this.levels = levels;
        this.menu.setClientState(unlocked, active, levels);
        updateActionButtons();
    }
    private void syncFromMenuOnOpen() {
        if (this.menu == null) return;
        this.unlocked = new HashSet<>(this.menu.getUnlockedClient());
        this.active = new HashSet<>(this.menu.getActiveClient());
        this.levels = new HashMap<>(this.menu.getLevelsClient());
    }
    private int getCurrentLevel(ResourceLocation augmentId) {
        return levels.getOrDefault(augmentId, unlocked.contains(augmentId) ? 1 : 0);
    }

    private void updateActionButtons() {
        boolean hasSelection = selectedEntry != null;
        boolean unlockedSel = isUnlockedSelected();
        boolean activeSel = isActiveSelected();

        boolean canCraftBase = false;
        boolean canUpgrade = false;

        Optional<AugmentationStationRecipe> baseOpt = getSelectedRecipe();
        Optional<AugmentationStationRecipe> upOpt = Optional.empty();

        if (hasSelection) {
            int currentLevel = getCurrentLevel(selectedEntry.augmentId());

            if (baseOpt.isPresent()) {
                AugmentationStationRecipe base = baseOpt.get();
                // base craft is allowed if recipe level is above current level (usually 1 > 0)
                if (base.getLevel() > currentLevel) {
                    canCraftBase = requirementsMet(base);
                }
            }

            if (unlockedSel) {
                upOpt = getNextUpgradeRecipe(selectedEntry.augmentId());
                if (upOpt.isPresent()) {
                    canUpgrade = requirementsMet(upOpt.get());
                }
            }
        }

        if (upgradeButton != null) {
            upgradeButton.visible = hasSelection && unlockedSel && upOpt.isPresent();
            upgradeButton.active = hasSelection && unlockedSel && upOpt.isPresent() && canUpgrade;
        }

        if (craftButton != null) {
            // show Craft only when base recipe is craftable (not already at/above that level)
            craftButton.visible = hasSelection && baseOpt.isPresent() && canCraftBase && !unlockedSel;
            craftButton.active = craftButton.visible;
        }

        if (activateButton != null) {
            activateButton.visible = hasSelection && unlockedSel && !activeSel;
            activateButton.active = activateButton.visible;
        }

        if (deactivateButton != null) {
            deactivateButton.visible = hasSelection && unlockedSel && activeSel;
            deactivateButton.active = deactivateButton.visible;
        }
    }

    private boolean isUnlockedSelected() {
        return selectedEntry != null && unlocked.contains(selectedEntry.augmentId());
    }

    private boolean isActiveSelected() {
        return selectedEntry != null && active.contains(selectedEntry.augmentId());
    }

    public AugmentationStationScreen(AugmentationStationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = TEX_W;
        this.imageHeight = TEX_H;

        allEntries.add(new AugmentEntry(
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "augmentation_station/huricane_domain_expansion"),
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "huricane_domain_expansion"),
                "Domain Expansion: Huricane",
                "Swiftly defeat your enemies",
                Category.DOMAIN_EXPANSIONS
        ));
        allEntries.add(new AugmentEntry(
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "augmentation_station/blizzard_domain_expansion"),
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "blizzard_domain_expansion"),
                "Domain Expansion: Blizzard",
                "Freeze your enemies in place.",
                Category.DOMAIN_EXPANSIONS
        ));

        allEntries.add(new AugmentEntry(
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "augmentation_station/reinforced_musculature"),
                ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "reinforced_musculature"),
                "Reinforced Musculature",
                "Doubles strength.",
                Category.BODY_ENHANCEMENTS
        ));
    }

    private enum Category {
        DOMAIN_EXPANSIONS("Domain Expansions"),
        BODY_ENHANCEMENTS("Body Enhancements");

        private final String label;
        Category(String label) { this.label = label; }
        public String label() { return label; }
    }

    private record AugmentEntry(ResourceLocation recipeId, ResourceLocation augmentId, String title, String description, Category category) {}

    private Optional<AugmentationStationRecipe> getSelectedRecipe() {
        if (this.minecraft == null || this.minecraft.level == null || selectedEntry == null) return Optional.empty();

        Optional<? extends Recipe<?>> any = this.minecraft.level.getRecipeManager().byKey(selectedEntry.recipeId());
        if (any.isPresent() && any.get() instanceof AugmentationStationRecipe r) return Optional.of(r);

        // Fallback: if UI stored a short id but JSON is inside augmentation_station/ folder
        ResourceLocation id = selectedEntry.recipeId();
        ResourceLocation fallback = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "augmentation_station/" + id.getPath());
        Optional<? extends Recipe<?>> any2 = this.minecraft.level.getRecipeManager().byKey(fallback);
        if (any2.isPresent() && any2.get() instanceof AugmentationStationRecipe r2) return Optional.of(r2);

        return Optional.empty();
    }
    private Optional<AugmentationStationRecipe> getNextUpgradeRecipe(ResourceLocation augmentId) {
        if (this.minecraft == null || this.minecraft.level == null) return Optional.empty();

        int current = getCurrentLevel(augmentId);
        int target = current + 1;

        List<AugmentationStationRecipe> all = this.minecraft.level.getRecipeManager()
                .getAllRecipesFor(AugmentationStationRecipeType.INSTANCE);

        for (AugmentationStationRecipe r : all) {
            if (r.getAugmentId().equals(augmentId) && r.getLevel() == target) {
                return Optional.of(r);
            }
        }
        return Optional.empty();
    }
    private int countItemInPlayerInventory(Item item) {
        if (this.minecraft == null || this.minecraft.player == null) return 0;
        Inventory inv = this.minecraft.player.getInventory();
        int total = 0;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack s = inv.getItem(i);
            if (!s.isEmpty() && s.getItem() == item) total += s.getCount();
        }
        return total;
    }

    private boolean requirementsMet(AugmentationStationRecipe recipe) {
        for (Requirement req : recipe.getRequirements()) {
            if (countItemInPlayerInventory(req.item()) < req.count()) return false;
        }
        return true;
    }
    @Override
    protected void init() {
        super.init();
        this.clearWidgets();

        // IMPORTANT: initialize UI state from what the server sent in the open-screen buffer
        syncFromMenuOnOpen();

        int x = this.leftPos;
        int y = this.topPos;

        int innerX = x + BORDER;
        int innerY = y + BORDER;
        int innerW = this.imageWidth - BORDER * 2;
        int innerH = this.imageHeight - BORDER * 2;

        int listX = innerX + INNER_PAD;
        int listTop = innerY + INNER_PAD;
        int listBottom = innerY + innerH - INNER_PAD;

        this.entryList = new AugmentEntryList(this.minecraft, 130, listBottom - listTop, listTop, listBottom, 12);
        this.entryList.setLeftPos(listX);

        for (Category cat : Category.values()) {
            entryList.addChapter(Component.literal("> " + cat.label()), () -> {
                expandedCategory = (expandedCategory == cat) ? null : cat;
                init();
            });

            if (expandedCategory == cat) {
                for (AugmentEntry entry : allEntries) {
                    if (entry.category() == cat) {
                        entryList.addLink(Component.literal(" - " + entry.title()), () -> {
                            selectedEntry = entry;
                            descriptionScroll = 0;
                            updateActionButtons();
                        });
                    }
                }
            }
        }

        this.addRenderableWidget(this.entryList);

        // Single bottom-right slot for action button
        int bw = 72;
        int bh = 18;
        int bx = innerX + innerW - INNER_PAD - bw;
        int by = innerY + innerH - INNER_PAD - bh;

        // NEW: upgrade button above the main slot
        this.upgradeButton = this.addRenderableWidget(Button.builder(Component.literal("Upgrade"), b -> {
            if (selectedEntry == null) return;
            Optional<AugmentationStationRecipe> up = getNextUpgradeRecipe(selectedEntry.augmentId());
            up.ifPresent(r -> RileyModPackets.sendToServer(new CraftAugmentPacket(this.menu.getPos(), r.getId())));
        }).bounds(bx, by - (bh + 4), bw, bh).build());

        this.craftButton = this.addRenderableWidget(Button.builder(Component.literal("Craft"), b -> {
            if (selectedEntry == null) return;

            // Use the *resolved* recipe id (handles augmentation_station/ folder recipes correctly)
            Optional<AugmentationStationRecipe> recipeOpt = getSelectedRecipe();
            if (recipeOpt.isEmpty()) return;

            RileyModPackets.sendToServer(new CraftAugmentPacket(this.menu.getPos(), recipeOpt.get().getId()));
        }).bounds(bx, by, bw, bh).build());

        this.activateButton = this.addRenderableWidget(Button.builder(Component.literal("Activate"), b -> {
            if (selectedEntry == null) return;
            RileyModPackets.sendToServer(new SetAugmentActivePacket(selectedEntry.augmentId(), true));
        }).bounds(bx, by, bw, bh).build());

        this.deactivateButton = this.addRenderableWidget(Button.builder(Component.literal("Deactivate"), b -> {
            if (selectedEntry == null) return;
            RileyModPackets.sendToServer(new SetAugmentActivePacket(selectedEntry.augmentId(), false));
        }).bounds(bx, by, bw, bh).build());

        updateActionButtons();
    }
    // Draws text scaled down (never up) so it fits maxWidthPx.
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

    // Computes a scale factor (<= 1) so the widest line fits maxWidthPx.
    private float computeScaleForLines(List<FormattedCharSequence> lines, int maxWidthPx) {
        if (maxWidthPx <= 0) return 1.0f;
        int widest = 0;
        for (FormattedCharSequence line : lines) {
            widest = Math.max(widest, this.font.width(line));
        }
        if (widest <= 0) return 1.0f;
        return Math.min(1.0f, (float) maxWidthPx / (float) widest);
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY) {
        RenderSystem.enableBlend();
        g.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, TEX_W, TEX_H);

        int innerX = this.leftPos + BORDER;
        int innerY = this.topPos + BORDER;
        int innerW = this.imageWidth - BORDER * 2;
        int innerH = this.imageHeight - BORDER * 2;

        // Right side area (clipped to the inner area so it never crosses the 9px border)
        int rightX0 = innerX + 136;
        int rightY0 = innerY + INNER_PAD;
        int rightX1 = innerX + innerW - INNER_PAD;
        int rightY1 = innerY + innerH - INNER_PAD;

        int textX = rightX0;
        int textY = rightY0;
        int maxWidth = Math.max(0, rightX1 - rightX0);
        int maxHeight = Math.max(0, rightY1 - rightY0);

        g.enableScissor(rightX0, rightY0, rightX1, rightY1);

        if (selectedEntry == null) {
            drawScaledString(g, "Augmentation Station", textX, textY, 0x303030, maxWidth);

            int bodyY = textY + 14;
            List<FormattedCharSequence> lines = this.font.split(Component.literal("Pick a category on the left."), maxWidth);
            float scale = computeScaleForLines(lines, maxWidth);

            g.pose().pushPose();
            g.pose().translate(textX, bodyY, 0);
            g.pose().scale(scale, scale, 1.0f);

            int yy = 0;
            int lineStep = Math.max(1, (int) Math.ceil(9 / scale)); // keep visual spacing ~9px after scaling
            for (FormattedCharSequence line : lines) {
                g.drawString(this.font, line, 0, yy, 0x505050, false);
                yy += lineStep;
            }
            g.pose().popPose();

            g.disableScissor();
            return;
        }

        // Title: shrink-to-fit (no truncation)
        drawScaledString(g, selectedEntry.title(), textX, textY, 0x303030, maxWidth);

        // Body: word-wrapped, then uniformly scaled so the widest wrapped line fits
        int bodyY = textY + 14;
        int bodyMaxHeight = Math.max(0, (rightY1 - bodyY));

        Optional<AugmentationStationRecipe> recipeOpt = getSelectedRecipe();
        String description = recipeOpt.map(AugmentationStationRecipe::getDescription)
                .filter(s -> !s.isBlank())
                .orElse(selectedEntry.description());

        List<FormattedCharSequence> descLines = this.font.split(Component.literal(description), maxWidth);
        float descScale = computeScaleForLines(descLines, maxWidth);

        g.pose().pushPose();
        g.pose().translate(textX, bodyY, 0);
        g.pose().scale(descScale, descScale, 1.0f);

        // descriptionScroll is in "screen pixels". Convert it into scaled-space pixels.
        int scrollScaled = (int) (descriptionScroll / descScale);
        int yy = -scrollScaled;
        int lineStep = Math.max(1, (int) Math.ceil(9 / descScale));

        for (FormattedCharSequence line : descLines) {
            if ((yy * descScale) > bodyMaxHeight) break;
            if ((yy + 9) * descScale >= 0) {
                g.drawString(this.font, line, 0, yy, 0x505050, false);
            }
            yy += lineStep;
        }
        g.pose().popPose();

        int descHeightPx = (int) Math.ceil(descLines.size() * 9 * descScale);
        int reqY = bodyY + descHeightPx + 10;
        Optional<AugmentationStationRecipe> showReq = Optional.empty();
        if (selectedEntry != null) {
            int currentLevel = getCurrentLevel(selectedEntry.augmentId());

            // If base recipe is still above our current level, show its requirements
            if (recipeOpt.isPresent() && recipeOpt.get().getLevel() > currentLevel) {
                showReq = recipeOpt;
            } else {
                // Otherwise show next upgrade requirements (if any)
                showReq = getNextUpgradeRecipe(selectedEntry.augmentId());
            }
        }

        if (showReq.isPresent()) {
            AugmentationStationRecipe recipe = showReq.get();

            int pad = 2;
            int reqX = textX + pad;

            g.drawString(this.font, Component.literal("Requirements:"), reqX, reqY, 0x303030, false);
            reqY += 12;

            for (Requirement req : recipe.getRequirements()) {
                int have = countItemInPlayerInventory(req.item());
                int need = req.count();
                int counterColor = (have >= need) ? 0x228B22 : 0x8B0000;

                g.renderItem(new ItemStack(req.item()), reqX, reqY - 2);

                String name = Component.translatable(req.item().getDescriptionId()).getString();
                int nameX = reqX + 18;
                int nameMaxWidth = Math.max(0, (rightX1 - pad) - nameX);
                drawScaledString(g, name, nameX, reqY, 0x505050, nameMaxWidth);

                String counter = have + "/" + need;
                int counterX = reqX + 8 - (this.font.width(counter) / 2);
                int minX = rightX0 + pad;
                int maxX = (rightX1 - pad) - this.font.width(counter);
                counterX = Math.max(minX, Math.min(counterX, maxX));

                g.drawString(this.font, counter, counterX, reqY + 10, counterColor, false);

                reqY += 22;
            }

            updateActionButtons();
        }

        g.disableScissor();
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY) {
        // Intentionally empty to avoid default labels overlapping the texture.
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (selectedEntry != null) {
            int innerX = this.leftPos + BORDER;
            int innerY = this.topPos + BORDER;
            int innerW = this.imageWidth - BORDER * 2;
            int innerH = this.imageHeight - BORDER * 2;

            int rightX0 = innerX + 136;
            int rightY0 = innerY + INNER_PAD;
            int rightX1 = innerX + innerW - INNER_PAD;
            int rightY1 = innerY + innerH - INNER_PAD;

            if (mouseX >= rightX0 && mouseX <= rightX1 && mouseY >= rightY0 && mouseY <= rightY1) {
                descriptionScroll = Math.max(0, descriptionScroll - scroll * 10);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private static class AugmentEntryList extends ContainerObjectSelectionList<AugmentEntryList.Entry> {
        public AugmentEntryList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
            this.setRenderBackground(false);
            this.setRenderHeader(false, 0);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            // IMPORTANT: render ONLY the list items. Do not call super.render(...)
            // because that can draw the vanilla background (the "dirt rectangle").
            this.renderList(graphics, mouseX, mouseY, partialTicks);

            // Optional: draw a small, non-dirt scrollbar if scrolling is possible
            if (this.getMaxScroll() > 0) {
                int xL = this.getScrollbarPosition();
                int xR = xL + 4;

                graphics.fill(xL, this.y0, xR, this.y1, 0x22000000);

                int h = this.y1 - this.y0;
                int handleH = (int)((float)(h * h) / (float)this.getMaxPosition());
                handleH = Math.max(10, handleH);

                int handleTop = (int)this.getScrollAmount() * (h - handleH) / this.getMaxScroll() + this.y0;

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
            public Entry(TextLinkButton button) { this.button = button; }

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
            int color = this.isHoveredOrFocused() ? 0xFFFFFF : 0x303030;

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
//TODO add crafting recipes for augments
//TODO allow you to create the augments like the 600 muscles for full body muscle transplant
//TODO add counter to display the ingredients, and what you have, 123/600