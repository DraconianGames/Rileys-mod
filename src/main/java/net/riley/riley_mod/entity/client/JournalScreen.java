package net.riley.riley_mod.entity.client;

import net.minecraft.world.item.ItemStack;
import net.riley.riley_mod.network.PetActionPacket;
import net.riley.riley_mod.network.RileyModPackets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.util.JournalEntry;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.riley.riley_mod.effect.RileyModEffects;

public class JournalScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/journal_gui.png");

    // Static list to keep pets in memory even if the entity is deleted from the world
    private static final List<JournalEntry> SAVED_PETS = new ArrayList<>();

    private final List<JournalEntry> ALL_ENTRIES = new ArrayList<>();
    private JournalEntry.Category expandedCategory = null;
    private JournalEntry selectedEntry = null;
    private final Map<EntityType<?>, LivingEntity> entityCache = new HashMap<>();

    private int currentPage = 0;
    private Button nextButton;
    private Button prevButton;

    private EditBox searchBox;
    private JournalEntryList entryList;
    private AbstractSliderButton rotationSlider;

    private Button petActionButton; // Summon/Revive
    private Button storePetButton;  // Store in Journal
    private Button deletePetButton; // Remove from Journal

    private float entityRotation = 0.0f;
    private double descriptionScroll = 0;

    public JournalScreen() {
        super(Component.literal("Dark Journal"));

        // --- ADD YOUR ENTRIES HERE ---
        ALL_ENTRIES.add(new JournalEntry("Sunless Crab", " They do like amethyst shards, and are neutral.", JournalEntry.Category.CREATURES, RileyModEntities.SUNLESS_CRAB.get(),22.0f, Items.AMETHYST_SHARD
                ,java.util.List.of(),null));
        ALL_ENTRIES.add(new JournalEntry("Whale Hunter", "A massive predator deep sea predator. They are neutral. They don't like boats.", JournalEntry.Category.CREATURES, RileyModEntities.WHALE_HUNTER.get(),10.0f,null,
                java.util.List.of(),null));
        ALL_ENTRIES.add(new JournalEntry("Rapter", "Loves cooked rabbit. They are neutral, but they are opportunistic. They are stronger than their cousin the Frost Hopper.", JournalEntry.Category.CREATURES, RileyModEntities.RAPTER.get(),15.0f,net.minecraft.world.item.Items.COOKED_RABBIT,
                java.util.List.of(new MobEffectInstance(RileyModEffects.BLEED.get(), 100, 0)),null));
        ALL_ENTRIES.add(new JournalEntry("Night Terror","A Winged Nightmare that loves flying way too much. It's roar will make you go deaf temporary", JournalEntry.Category.CREATURES, RileyModEntities.NIGHT_TERROR.get(),10.0f, RileyModItems.CLAW.get(),
                java.util.List.of(new MobEffectInstance(RileyModEffects.DEAF.get(), 6000, 0)),null));
        ALL_ENTRIES.add(new JournalEntry("Frost Hopper","Lives Obsidian Peaks in the abyss. Smaller than they rapter, they developed a poison that will freeze you in place, it comes out of the horn on their forehead. They hunt in packs.", JournalEntry.Category.CREATURES, RileyModEntities.FROST_HOPPER.get(),25.0f,net.minecraft.world.item.Items.COOKED_RABBIT,
                java.util.List.of(new MobEffectInstance(RileyModEffects.FREEZE.get(), 60, 0)),null));
        ALL_ENTRIES.add(new JournalEntry("Tooth Fairy", "An extremely docile creature looking for protection. They are more useless than a bat. They do like bones for some reason.", JournalEntry.Category.CREATURES, RileyModEntities.TOOTHFAIRY.get(),25.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null));
        ALL_ENTRIES.add(new JournalEntry("Bone Fairy", "The larger, much deadlier evolutionary cousin of the Tooth Fairy. It can throw a pretty good punch. They do like skeleton skulls for some reason.", JournalEntry.Category.CREATURES, RileyModEntities.BONEFAIRY.get(), 5.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null));
        ALL_ENTRIES.add(new JournalEntry("Skeleton Fairy", "After countless fights, the Bone Fairie's claws wore down, force to only be used as feet. It grew too heavy to stand upright, and developed a nice set of fangs. It also developed more eyes for better precision.", JournalEntry.Category.CREATURES, RileyModEntities.SKELETONFAIRY.get(), 10.0f, RileyModItems.TOOTH.get(),
                java.util.List.of(),null));
        ALL_ENTRIES.add(new JournalEntry("Abyss Log", "Wood harvested from the trees of the abyss.", JournalEntry.Category.BLOCKS));
        ALL_ENTRIES.add(new JournalEntry("Activated Funtium", "To get this, you mest first get a blast furnace. smelt funtium ore into funtium plate, combine 9 into one funtium block, then blast smelt it again into activated funtium.", JournalEntry.Category.BLOCKS));
        ALL_ENTRIES.add(new JournalEntry("Eye", "To craft the eye, you need 4 obsidian, 1 activated funtium, and 4 glowstone dust. Activated funtium in the middle, glowstone dust in the corners, an the obsidian fills the rest.", JournalEntry.Category.ITEMS));
        ALL_ENTRIES.add(new JournalEntry("Caged Fairy","A baby Toothfairy is held within. Right click on any surface to release it. Make sure to tame it before it wanders off.", JournalEntry.Category.ITEMS));
        ALL_ENTRIES.add(new JournalEntry("Funtium Ore", "A DENSE material containing mystical properties, you do need Netherite to mine it because it is so dense.", JournalEntry.Category.ITEMS));
        ALL_ENTRIES.add(new JournalEntry("The Abyss", "A dimension where messing with the wrong thing can be deadly. It has a range of terrifying creatures, unique flora, and for some reason, it's only night. Never let the creatures out unless they are tamed.", JournalEntry.Category.ABYSS));
        ALL_ENTRIES.add(new JournalEntry("The Abyss Portal", "A portal built exactly like the Nether portal, but you need activated funtium as its frame, and the eye to light it", JournalEntry.Category.ABYSS));
        ALL_ENTRIES.add(new JournalEntry("The Arena", "It has good loot but only spawn in the abyss", JournalEntry.Category.STRUCTURES));
        ALL_ENTRIES.add(new JournalEntry("The Avalon", "A place where the weary traveler can shelter out the storms. Spawns in the overworld. Designed by Avalon herself", JournalEntry.Category.STRUCTURES));
        ALL_ENTRIES.add(new JournalEntry("The Wither Skeleton Room","A room with wither skeleton spawner and good loot", JournalEntry.Category.STRUCTURES));
    }

    @Override
    protected void init() {
        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;
        this.clearWidgets();

        // Add dynamically found Pets to ALL_ENTRIES
        refreshPetEntries();
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
                            currentPage = 0;
                            descriptionScroll = 0;// Reset to first page
                        });
                    }
                }
            }
        }
        this.addRenderableWidget(this.entryList);

        this.prevButton = Button.builder(Component.literal("<"), b -> {
            if (currentPage > 0) currentPage--;descriptionScroll = 0;
        }).bounds(x + 145, y + 12, 20, 14).build(); // Moved to top

        this.nextButton = Button.builder(Component.literal(">"), b -> {
            if (currentPage < 1) currentPage++;
        }).bounds(x + 215, y + 12, 20, 14).build(); // Moved to top

        this.addRenderableWidget(this.prevButton);
        this.addRenderableWidget(this.nextButton);

        // Pet Action Buttons (Hidden by default)
        this.petActionButton = Button.builder(Component.literal("Summon"), b -> {
            handlePetAction();
        }).bounds(x + 145, y + 100, 45, 14).build();

        this.storePetButton = Button.builder(Component.literal("Store"), b -> {
            handleStorePet();
        }).bounds(x + 195, y + 100, 45, 14).build();

        this.deletePetButton = Button.builder(Component.literal("Release"), b -> {
            handleDeletePet();
        }).bounds(x + 145, y + 140, 95, 14).build(); // Centered under the other two

        this.addRenderableWidget(this.petActionButton);
        this.addRenderableWidget(this.storePetButton);
        this.addRenderableWidget(this.deletePetButton);

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
    private void handleDeletePet() {
        if (selectedEntry != null && selectedEntry.entityUUID() != null) {
            // 1. Tell the server to physically remove the NBT data from the item
            RileyModPackets.sendToServer(new PetActionPacket(selectedEntry.entityUUID(), 2));

            // 2. Clear from local memory
            SAVED_PETS.removeIf(e -> e.entityUUID().equals(selectedEntry.entityUUID()));
            ALL_ENTRIES.removeIf(e -> e.entityUUID() != null && e.entityUUID().equals(selectedEntry.entityUUID()));

            selectedEntry = null;
            init(); // Re-initialize UI to show updated list
        }
    }

    private void handlePetAction() {
        if (selectedEntry != null && selectedEntry.entityUUID() != null) {
            // Action 0 is Summon/Revive
            RileyModPackets.sendToServer(new PetActionPacket(selectedEntry.entityUUID(), 0));
            this.onClose();
        }
    }
    private void handleStorePet() {
        if (selectedEntry != null && selectedEntry.entityUUID() != null) {
            // Action 1 is Store
            RileyModPackets.sendToServer(new PetActionPacket(selectedEntry.entityUUID(), 1));
            this.onClose();
        }
    }
    public static void forgetPet(java.util.UUID uuid) {
        SAVED_PETS.removeIf(e -> uuid.equals(e.entityUUID()));
    }


    private void refreshPetEntries() {
        if (this.minecraft == null || this.minecraft.level == null || this.minecraft.player == null) return;

        // Clear the list we'll build for this frame
        SAVED_PETS.clear();

        // 1) Read stored/backups from the journal into a map by UUID (don't add them to the list yet)
        java.util.Map<java.util.UUID, net.minecraft.nbt.CompoundTag> storedMap = new java.util.HashMap<>();
        ItemStack journal = this.minecraft.player.getMainHandItem();
        if (journal.hasTag() && journal.getTag().contains("StoredPets")) {
            net.minecraft.nbt.ListTag stored = journal.getTag().getList("StoredPets", 10);
            for (int i = 0; i < stored.size(); i++) {
                net.minecraft.nbt.CompoundTag data = stored.getCompound(i);
                try {
                    java.util.UUID uuid = data.getUUID("UUID");
                    storedMap.put(uuid, data);
                } catch (Exception ignored) {}
            }
        }

        // Keep track of which stored UUIDs we handled as LIVE (so we don't also add the "stored" placeholder)
        java.util.Set<java.util.UUID> handled = new java.util.HashSet<>();

        // 2) Scan the client world for live pets. Treat entity as a pet if:
        //    - it's owned by the player (ownerUUID matches), OR
        //    - its UUID matches a stored backup (so a summoned/respawned pet will override the stored backup)
        for (net.minecraft.world.entity.Entity e : this.minecraft.level.entitiesForRendering()) {
            if (!(e instanceof net.minecraft.world.entity.LivingEntity living)) continue;
            if (e.isRemoved()) continue;

            // Resolve owner UUID (supports TamableAnimal, AbstractHorse, and generic OwnableEntity)
            java.util.UUID ownerUUID = null;
            try {
                if (living instanceof net.minecraft.world.entity.TamableAnimal ta) ownerUUID = ta.getOwnerUUID();
                else if (living instanceof net.minecraft.world.entity.animal.horse.AbstractHorse ho) ownerUUID = ho.getOwnerUUID();
                else if (living instanceof net.minecraft.world.entity.OwnableEntity oe) ownerUUID = oe.getOwnerUUID();
            } catch (Exception ignored) {}

            boolean ownedByPlayer = ownerUUID != null && ownerUUID.equals(this.minecraft.player.getUUID());
            java.util.UUID entityUUID = living.getUUID();

            // If the live entity is owned by the player OR matches a stored backup, add it as an ACTIVE pet
            if (ownedByPlayer || storedMap.containsKey(entityUUID)) {
                handled.add(entityUUID); // mark handled so we won't add the stored placeholder later

                String name = living.hasCustomName() ? living.getCustomName().getString() : living.getType().getDescription().getString();
                SAVED_PETS.add(new net.riley.riley_mod.util.JournalEntry(
                        name,
                        "Status: Active",
                        net.riley.riley_mod.util.JournalEntry.Category.PETS,
                        living.getType(),
                        20.0f,
                        null,
                        java.util.List.of(),
                        entityUUID
                ));
            }
        }

        // 3) Any stored backups not handled by the live-scan should be added as stored placeholders
        for (java.util.Map.Entry<java.util.UUID, net.minecraft.nbt.CompoundTag> entry : storedMap.entrySet()) {
            java.util.UUID uuid = entry.getKey();
            if (handled.contains(uuid)) continue; // live entity already replaced this backup

            net.minecraft.nbt.CompoundTag data = entry.getValue();
            String petId = data.contains("id") ? data.getString("id") : "";
            String displayName = "Unknown Pet";
            if (data.contains("CustomName", 8)) {
                try {
                    net.minecraft.network.chat.Component nameComp = net.minecraft.network.chat.Component.Serializer.fromJson(data.getString("CustomName"));
                    if (nameComp != null) displayName = nameComp.getString();
                } catch (Exception ignored) {}
            } else if (!petId.isEmpty()) {
                displayName = net.minecraft.world.entity.EntityType.byString(petId).map(t -> t.getDescription().getString()).orElse("Pet");
            }

            SAVED_PETS.add(new net.riley.riley_mod.util.JournalEntry(
                    "[] " + displayName,
                    "Eternal Backup",
                    net.riley.riley_mod.util.JournalEntry.Category.PETS,
                    net.minecraft.world.entity.EntityType.byString(petId).orElse(null),
                    20f,
                    null,
                    java.util.List.of(),
                    uuid
            ));
        }

        // 4) Replace any previous Pets entries in ALL_ENTRIES with the freshly computed list
        ALL_ENTRIES.removeIf(e -> e.category() == net.riley.riley_mod.util.JournalEntry.Category.PETS);
        ALL_ENTRIES.addAll(SAVED_PETS);
    }




    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;

        boolean hasEntity = (selectedEntry != null && selectedEntry.entityType() != null);
        boolean isPet = selectedEntry != null && selectedEntry.category() == JournalEntry.Category.PETS;

        if (deletePetButton != null) {
            // Only show the delete button for pets
            deletePetButton.visible = isPet;
        }
        // --- Consolidated Widget Visibility Logic ---
        if (rotationSlider != null) {
            // Slider only for non-pets on page 1
            rotationSlider.visible = !isPet && hasEntity && currentPage == 1;
        }

        if (nextButton != null && prevButton != null) {
            // NAVIGATION ARROWS: Hidden entirely if it's a pet
            nextButton.visible = !isPet && hasEntity && currentPage == 0;
            prevButton.visible = !isPet && hasEntity && currentPage == 1;
        }

        if (petActionButton != null && storePetButton != null) {
            petActionButton.visible = isPet;
            storePetButton.visible = isPet;

            if (isPet) {
                // Dynamically check if the selected pet is dead to update button text
                boolean isDead = isSelectedPetDead();
                petActionButton.setMessage(isDead ? Component.literal("Revive") : Component.literal("Summon"));
            }
        }


        // Draw Book Background
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 180);

        // Render Right Page Content
        if (selectedEntry != null) {
            graphics.drawString(this.font, selectedEntry.title(), x + 145, y + 28, 0x303030, false);

            if (isPet) {
                // PET SINGLE PAGE LAYOUT
                renderPetStatusPage(graphics, x + 145, y + 45);
            } else if (currentPage == 0 || !hasEntity) {
                // PAGE 0: SCROLLABLE TEXT
                int textX = x + 145;
                int textY = y + 45;
                int maxWidth = 90;
                int maxHeight = 110; // Area available for text

                // Scissor the area so text doesn't bleed out of the book
                graphics.enableScissor(textX, textY, textX + maxWidth, textY + maxHeight);
                graphics.drawWordWrap(this.font, Component.literal(selectedEntry.content()), textX, (int)(textY - descriptionScroll), maxWidth, 0x505050);
                graphics.disableScissor();

            } else if (currentPage == 1 && hasEntity) {
                // PAGE 1: MODEL AND STATS
                renderStatBlock(graphics, x + 145, y + 45, selectedEntry.entityType());

                LivingEntity entity = getEntity(selectedEntry.entityType());

                if (entity != null) {
                    // --- TEMPTATION SECTION (Moved closer to stats) ---
                    int currentY = y + 82; // Shifted up from 85
                    if (selectedEntry.temptationItem() != null) {
                        graphics.drawString(this.font, "Tempted by:", x + 145, currentY, 0x303030, false);
                        graphics.renderItem(new net.minecraft.world.item.ItemStack(selectedEntry.temptationItem()), x + 205, currentY - 4);
                        currentY += 18; // Advance Y for the next section
                    } else {
                        currentY += 5; // Small gap if no food
                    }

                    // --- EFFECT SECTION (Moved up) ---
                    if (!selectedEntry.hitEffects().isEmpty()) {
                        graphics.drawString(this.font, "Effects:", x + 145, currentY, 0x303030, false);
                        int iconX = x + 145;
                        int iconY = currentY + 10;
                        int spacing = 20;

                        for (int i = 0; i < selectedEntry.hitEffects().size(); i++) {
                            MobEffectInstance instance = selectedEntry.hitEffects().get(i);
                            net.minecraft.world.effect.MobEffect effect = instance.getEffect();
                            int currentIconX = iconX + (i * spacing);

                            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = this.minecraft.getMobEffectTextures().get(effect);
                            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
                            graphics.blit(currentIconX, iconY, 0, 18, 18, sprite);

                            if (mouseX >= currentIconX && mouseX <= currentIconX + 18 && mouseY >= iconY && mouseY <= iconY + 18) {
                                graphics.renderTooltip(this.font, effect.getDisplayName(), mouseX, mouseY);
                            }
                        }
                    }
                    entity.yBodyRot = 180.0f;
                    entity.yBodyRotO = 180.0f;
                    entity.yHeadRot = 180.0f;
                    entity.yHeadRotO = 180.0f;
                    entity.setYRot(180.0f);
                    entity.setXRot(0);

                    float lookX = (entityRotation - 180.0f) * -0.45f;
                    // y + 155 gives the model more room at the bottom
                    renderEntity(graphics, x + 195, y + 155, (int)selectedEntry.scale(), lookX, 0, entity);
                }
            }

        } else {
            graphics.drawString(this.font, "Abyssal Journal", x + 145, y + 25, 0x303030, false);
            graphics.drawWordWrap(this.font, Component.literal("Select a chapter from the left to reveal its secrets..."), x + 145, y + 45, 90, 0x505050);
        }


        // ... (text rendering code) ...
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    // Replace the existing isSelectedPetDead() method body with this:
    private boolean isSelectedPetDead() {
        if (selectedEntry == null || selectedEntry.entityUUID() == null || this.minecraft == null || this.minecraft.level == null) {
            return false; // no selection or no world — treat as "not dead" so UI won't show "Revive" for nothing
        }

        // Look for the entity by UUID in the client's rendering list (works for TamableAnimal, AbstractHorse, OwnableEntity, etc.)
        for (net.minecraft.world.entity.Entity e : this.minecraft.level.entitiesForRendering()) {
            if (e.getUUID().equals(selectedEntry.entityUUID())) {
                if (e instanceof net.minecraft.world.entity.LivingEntity living) {
                    return !living.isAlive();
                }
                return true; // present but not a living entity -> treat as dead/missing
            }
        }

        // Not present in the world -> needs summoning/revival
        return true;
    }
    private void renderPetStatusPage(GuiGraphics graphics, int x, int y) {
        if (this.minecraft == null || this.minecraft.level == null || selectedEntry == null) return;

        // --- FIX: Re-scan for the pet every frame to get the LIVE version ---
        java.util.UUID targetUUID = selectedEntry.entityUUID();
        net.minecraft.world.entity.LivingEntity livePet = null;

        for (net.minecraft.world.entity.Entity e : this.minecraft.level.entitiesForRendering()) {
            if (e.getUUID().equals(targetUUID) && e instanceof net.minecraft.world.entity.LivingEntity living) {
                livePet = living;
                break;
            }
        }

        int textColor = 0x303030;
        int currentY = y;

        if (livePet != null) {
            // PET STATUS
            graphics.drawString(this.font, String.format("Health: %.1f / %.1f", livePet.getHealth(), livePet.getMaxHealth()), x, currentY, textColor, false);
            currentY += 10;
            String lifeStatus = livePet.isAlive() ? "Status: Alive" : "Status: Dead";
            graphics.drawString(this.font, lifeStatus, x, currentY, livePet.isAlive() ? 0x228B22 : 0x8B0000, false);
            currentY += 12;


            // REMOVED: Active Effects Section

            // Position buttons at a standard spot
            int buttonY = y + 85;
            petActionButton.setY(buttonY);
            storePetButton.setY(buttonY);
            deletePetButton.visible = false;

        } else {

            // PET IS GONE
            graphics.drawString(this.font, "Status: Stored", x, currentY, 0x8B0000, false);
            currentY += 12;

            List<net.minecraft.util.FormattedCharSequence> lines = this.font.split(Component.literal("This pet is no longer nearby. It may have evolved or moved away."), 90);
            for (net.minecraft.util.FormattedCharSequence line : lines) {
                graphics.drawString(this.font, line, x, currentY, 0x505050, false);
                currentY += 9;
            }

            currentY += 6;
            deletePetButton.setY(currentY);
            deletePetButton.visible = true;

            // Move Summon/Store buttons below the Release button
            int buttonY = currentY + 18;
            petActionButton.setY(buttonY);
            storePetButton.setY(buttonY);
        }
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
        int currentY = y + (spacing * 3);
        if (armor > 0) {
            graphics.drawString(this.font, "ARM: " + (int)armor, x, currentY + (spacing * 3), textColor, false);
        }
        // Removed the Effects logic from here to keep Creature page purely for descriptions
        if (selectedEntry != null && selectedEntry.category() == JournalEntry.Category.PETS) {
            String status = entity.isAlive() ? "Status: Healthy" : "Status: Fainted";
            graphics.drawString(this.font, status, x, currentY, textColor, false);
        }
    }
    private void renderEntity(GuiGraphics graphics, int x, int y, int scale, float lookX, float lookY, LivingEntity entity) {
        // CHANGE THIS: It must use lookX and lookY, not 0 and 0!
        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, x, y, scale, lookX, lookY, entity);
    }
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        // If mouse is over the right page and we are on page 0
        int x = (this.width - 256) / 2;
        if (currentPage == 0 && mouseX > x + 140) {
            descriptionScroll = Math.max(0, descriptionScroll - (scroll * 10));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
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