package net.riley.riley_mod.entity.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.riley.riley_mod.entity.RileyModEntities;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.JournalEntry;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;
import java.util.List;

public class JournalScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/journal_gui.png");

    private final List<JournalEntry> ALL_ENTRIES = new ArrayList<>();
    private JournalEntry.Category expandedCategory = null;
    private JournalEntry selectedEntry = null;
    private final Map<EntityType<?>, LivingEntity> entityCache = new HashMap<>();

    private EditBox searchBox;
    private JournalEntryList entryList;
    private AbstractSliderButton rotationSlider;

    private float entityRotation = 0.0f;

    public JournalScreen() {
        super(Component.literal("Dark Journal"));

        // --- ADD YOUR ENTRIES HERE ---
        ALL_ENTRIES.add(new JournalEntry("Sunless Crab", "A land dwelling creature, the favorite prey of the Night Terror. They do like amethyst shards, and are neutral.", JournalEntry.Category.CREATURES, RileyModEntities.SUNLESS_CRAB.get(),22.0f));
        ALL_ENTRIES.add(new JournalEntry("Whale Hunter", "A massive predator deep sea predator. They are neutral.", JournalEntry.Category.CREATURES, RileyModEntities.WHALE_HUNTER.get(),10.0f));
        ALL_ENTRIES.add(new JournalEntry("Rapter", "A tiny little guy with a strong bite. Loves cooked rabbit. They are neutral, but they are opportunistic.", JournalEntry.Category.CREATURES, RileyModEntities.RAPTER.get(),25.0f));
        ALL_ENTRIES.add(new JournalEntry("Night Terror","A Winged Nightmare that loves a little crab.", JournalEntry.Category.CREATURES, RileyModEntities.NIGHT_TERROR.get(),10.0f));
        ALL_ENTRIES.add(new JournalEntry("Abyss Log", "Wood harvested from the trees of the abyss.", JournalEntry.Category.BLOCKS));
        ALL_ENTRIES.add(new JournalEntry("Activated Funtium", "To get this, you mest first get a blast furnace. smelt funtium ore into funtium plate, combine 9 into one funtium block, then blast smelt it again into activated funtium.", JournalEntry.Category.BLOCKS));
        ALL_ENTRIES.add(new JournalEntry("Eye", "To craft the eye, you need 4 obsidian, 1 activated funtium, and 4 glowstone dust. Activated funtium in the middle, glowstone dust in the corners, an the obsidian fills the rest.", JournalEntry.Category.ITEMS));
        ALL_ENTRIES.add(new JournalEntry("Funtium Ore", "A DENSE material containing mystical properties, you do need Netherite to mine it because it is so dense.", JournalEntry.Category.ITEMS));
        ALL_ENTRIES.add(new JournalEntry("The Abyss", "A dimension where messing with the wrong thing can be deadly. It has a range of terrifying creatures, unique flora, and for some reason, it's only night. Never let the creatures out unless they are tamed.", JournalEntry.Category.ABYSS));
        ALL_ENTRIES.add(new JournalEntry("The Abyss Portal", "A portal built exactly like the Nether portal, but you need activated funtium as its frame, and the eye to light it", JournalEntry.Category.ABYSS));
        ALL_ENTRIES.add(new JournalEntry("The Arena", "It has good loot but only spawn in the abyss", JournalEntry.Category.STRUCTURES));
        ALL_ENTRIES.add(new JournalEntry("The Avalon", "A place where the weary traveler can shelter out the storms. Spawns in the overworld. Designed by my Avalon herself", JournalEntry.Category.STRUCTURES));
    }

    @Override
    protected void init() {
        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;
        this.clearWidgets();

        // 1. Search Bar (Top Left)
        this.searchBox = new EditBox(this.font, x + 20, y + 12, 80, 14, Component.literal("Search..."));
        this.searchBox.setResponder(this::onSearch);
        this.addRenderableWidget(this.searchBox);

        // 2. Index/Home Button
        this.addRenderableWidget(new TextLinkButton(x + 105, y + 14, 35, 10, Component.literal("Index"), b -> {
            selectedEntry = null;
            expandedCategory = null;
            init();
        }));

        // 3. The Scrolling List (Left Page)
        // Params: Minecraft, Width, Height, Top, Bottom, ItemHeight
        this.entryList = new JournalEntryList(this.minecraft, 115, 135, y + 35, y + 165, 12);
        this.entryList.updateSize(115, 135, y + 35, y + 165);
        this.entryList.setLeftPos(x + 15); // This is the standard method for positioning lists



        for (JournalEntry.Category cat : JournalEntry.Category.values()) {
            // Add Category Header
            entryList.addChapter(Component.literal("> " + cat.name()), () -> {
                expandedCategory = (expandedCategory == cat) ? null : cat;
                init(); // Refresh list
            });

            // Add Entries if the category is expanded
            if (expandedCategory == cat) {
                for (JournalEntry entry : ALL_ENTRIES) {
                    if (entry.category() == cat) {
                        entryList.addLink(Component.literal(" - " + entry.title()), () -> {
                            selectedEntry = entry;
                        });
                    }
                }
            }
        }
        this.addRenderableWidget(this.entryList);


        this.rotationSlider = new AbstractSliderButton(x + 150, y + 160, 90, 12, Component.literal("Rotate"), entityRotation / 360.0f) {
            @Override
            protected void updateMessage() {
                this.setMessage(Component.literal("Rotate: " + (int)entityRotation + "°"));
            }

            @Override
            protected void applyValue() {
                entityRotation = (float)(this.value * 360.0);
            }
        };
        this.addRenderableWidget(this.rotationSlider);
    }

    private void onSearch(String query) {
        if (!query.isEmpty()) {
            ALL_ENTRIES.stream()
                    .filter(e -> e.title().toLowerCase().contains(query.toLowerCase()))
                    .findFirst()
                    .ifPresent(entry -> selectedEntry = entry);
        }
    }
    private LivingEntity getEntity(EntityType<?> type) {
        if (this.minecraft == null || this.minecraft.level == null) return null;

        return entityCache.computeIfAbsent(type, t -> {
            // Use the create(Level) method. In records/classes, it returns an Entity
            // so we cast it to LivingEntity for the inventory renderer.
            net.minecraft.world.entity.Entity entity = t.create(this.minecraft.level);
            return entity instanceof LivingEntity living ? living : null;
        });
    }


    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;

        if (rotationSlider != null) {
            rotationSlider.visible = (selectedEntry != null && selectedEntry.entityType() != null);
        }
        // Draw Book Background
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 180);

        // Render Right Page Content
        if (selectedEntry != null) {
            graphics.drawString(this.font, selectedEntry.title(), x + 145, y + 25, 0x303030, false);
            graphics.drawWordWrap(this.font, Component.literal(selectedEntry.content()), x + 145, y + 45, 90, 0x505050);
            if (selectedEntry.entityType() != null) {
                renderStatBlock(graphics, x + 145, y + 90, selectedEntry.entityType());
            }


        } else {
            graphics.drawString(this.font, "Abyssal Journal", x + 145, y + 25, 0x303030, false);
            graphics.drawWordWrap(this.font, Component.literal("Select a chapter from the left to reveal its secrets..."), x + 145, y + 45, 90, 0x505050);
        }
        if (selectedEntry != null && selectedEntry.entityType() != null) {
            LivingEntity entity = getEntity(selectedEntry.entityType());
            if (entity != null) {
                entity.yBodyRot = 180.0f;
                entity.yBodyRotO = 180.0f;
                entity.yHeadRot = 180.0f;
                entity.yHeadRotO = 180.0f;
                entity.setYRot(180.0f);
                entity.setXRot(0);

                // This math converts 0-360 degrees into the "Look Offset" the renderer wants
                // Multiplied by -0.45 to flip the direction and scale the rotation properly
                float lookX = (entityRotation - 180.0f) * -0.45f;

                renderEntity(graphics, x + 195, y + 145, (int)selectedEntry.scale(), lookX, 0, entity);
            }
        }

        // ... (text rendering code) ...
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
    private void renderStatBlock(GuiGraphics graphics, int x, int y, EntityType<?> type) {
        LivingEntity entity = getEntity(type);
        if (entity == null) return;

        int textColor = 0x303030;
        int spacing = 10;

        // Pulling attributes from the entity instance
        double hp = entity.getMaxHealth();
        double attack = entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double speed = entity.getAttributeValue(Attributes.MOVEMENT_SPEED);
        double armor = entity.getAttributeValue(Attributes.ARMOR);

        graphics.drawString(this.font, "HP: " + (int)hp, x, y, textColor, false);
        graphics.drawString(this.font, "ATK: " + (int)attack, x, y + spacing, textColor, false);
        graphics.drawString(this.font, "SPD: " + String.format("%.2f", speed), x, y + (spacing * 2), textColor, false);
        if (armor > 0) {
            graphics.drawString(this.font, "ARM: " + (int)armor, x, y + (spacing * 3), textColor, false);
        }
    }
    private void renderEntity(GuiGraphics graphics, int x, int y, int scale, float lookX, float lookY, LivingEntity entity) {
        // CHANGE THIS: It must use lookX and lookY, not 0 and 0!
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x, y, scale, lookX, lookY, entity);
    }


    @Override
    public boolean isPauseScreen() { return false; }

    // --- HELPER CLASSES ---

    static class TextLinkButton extends Button {
        public TextLinkButton(int x, int y, int width, int height, Component msg, OnPress onPress) {
            super(x, y, width, height, msg, onPress, DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            int color = this.isHoveredOrFocused() ? 0xFFFFFF : 0x303030;
            graphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX(), this.getY(), color, false);
        }
    }

    static class JournalEntryList extends ContainerObjectSelectionList<JournalEntryList.Entry> {
        public JournalEntryList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
            this.setRenderBackground(false);
            this.setRenderHeader(false, 0);
        }
        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            // 1. Render only the entries (this avoids the dirt texture)
            this.renderList(graphics, mouseX, mouseY, partialTicks);

            // 2. Manually draw the scrollbar ONLY if the list is long enough
            if (this.getMaxScroll() > 0) {
                int scrollbarXLeft = this.getScrollbarPosition();
                int scrollbarXRight = scrollbarXLeft + 4; // Slimmer scrollbar

                // Background of the scroll track (optional, very faint)
                graphics.fill(scrollbarXLeft, this.y0, scrollbarXRight, this.y1, 0x22000000);

                // Calculate handle size and position
                int height = this.y1 - this.y0;
                int handleHeight = (int)((float)(height * height) / (float)this.getMaxPosition());
                handleHeight = Math.max(10, handleHeight); // Minimum size so it's clickable

                int handleTop = (int)this.getScrollAmount() * (height - handleHeight) / this.getMaxScroll() + this.y0;

                // Draw the handle (Dark gray to match the book)
                graphics.fill(scrollbarXLeft, handleTop, scrollbarXRight, handleTop + handleHeight, 0xFF404040);
            }
        }
        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
            this.setScrollAmount(this.getScrollAmount() - scroll * (double)this.itemHeight / 2.0D);
            return true;
        }

        public void addChapter(Component title, Runnable onPress) {
            this.addEntry(new Entry(new TextLinkButton(0, 0, 100, 10, title, b -> onPress.run())));
        }

        public void addLink(Component title, Runnable onPress) {
            this.addEntry(new Entry(new TextLinkButton(10, 0, 90, 10, title, b -> onPress.run())));
        }

        @Override
        public int getRowWidth() { return 110; }
        @Override
        protected int getScrollbarPosition() { return this.x0 + this.width - 5; }

        public static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            final TextLinkButton button;
            public Entry(TextLinkButton button) { this.button = button; }
            @Override
            public void render(GuiGraphics g, int i, int top, int left, int w, int h, int mx, int my, boolean hover, float pt) {
                button.setX(left); button.setY(top);
                button.render(g, mx, my, pt);
            }
            @Override public List<? extends GuiEventListener> children() { return ImmutableList.of(button); }
            @Override public List<? extends NarratableEntry> narratables() { return ImmutableList.of(button); }
        }
    }
}