package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.network.PetActionPacket;
import net.riley.riley_mod.network.RequestPetDataPacket;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.util.JournalEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetScreen extends Screen {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/gui/pet_gui.png");

    private static final List<JournalEntry> SAVED_PETS = new ArrayList<>();
    private static net.minecraft.nbt.ListTag syncedStoredPets = new net.minecraft.nbt.ListTag();
    private static net.minecraft.nbt.ListTag syncedStoredMounts = new net.minecraft.nbt.ListTag();
    private static net.minecraft.nbt.ListTag syncedStoredVehicles = new net.minecraft.nbt.ListTag();

    private final List<JournalEntry> petEntries = new ArrayList<>();

    private JournalEntry selectedPet;
    private PetEntryList petList;

    private Button summonButton;
    private Button storeButton;
    private Button releaseButton;

    public PetScreen() {
        super(Component.literal("Pets"));
    }

    public static void setSyncedPets(net.minecraft.nbt.ListTag pets) {
        syncedStoredPets = pets.copy();
    }

    public static void setSyncedCompanions(
            net.minecraft.nbt.ListTag pets,
            net.minecraft.nbt.ListTag mounts,
            net.minecraft.nbt.ListTag vehicles
    ) {
        syncedStoredPets = pets.copy();
        syncedStoredMounts = mounts.copy();
        syncedStoredVehicles = vehicles.copy();
    }
    @Override
    protected void init() {
        RileyModPackets.sendToServer(new RequestPetDataPacket());
        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;

        this.clearWidgets();
        this.refreshPetEntries();

        this.petList = new PetEntryList(this.minecraft, 115, 135, y + 35, y + 165, 12);
        this.petList.updateSize(115, 135, y + 35, y + 165);
        this.petList.setLeftPos(x + 15);

        this.petList.addHeader(Component.literal("> Pets"));
        for (JournalEntry entry : this.petEntries) {
            this.petList.addPet(Component.literal(" - " + entry.title()), () -> this.selectedPet = entry);
        }

        this.petList.addHeader(Component.literal("> Mounts"));
        addStoredCompanionEntries(this.syncedStoredMounts, "Mount");

        this.petList.addHeader(Component.literal("> Vehicles"));
        addStoredCompanionEntries(this.syncedStoredVehicles, "Vehicle");

        this.addRenderableWidget(this.petList);

        this.summonButton = Button.builder(Component.literal("Summon"), b -> this.handlePetAction())
                .bounds(x + 145, y + 100, 45, 14)
                .build();

        this.storeButton = Button.builder(Component.literal("Store"), b -> this.handleStorePet())
                .bounds(x + 195, y + 100, 45, 14)
                .build();

        this.releaseButton = Button.builder(Component.literal("Release"), b -> this.handleDeletePet())
                .bounds(x + 145, y + 140, 95, 14)
                .build();

        this.addRenderableWidget(this.summonButton);
        this.addRenderableWidget(this.storeButton);
        this.addRenderableWidget(this.releaseButton);
    }

    private void refreshPetEntries() {
        if (this.minecraft == null || this.minecraft.level == null || this.minecraft.player == null) return;

        SAVED_PETS.clear();

        java.util.Map<UUID, net.minecraft.nbt.CompoundTag> storedMap = new java.util.HashMap<>();

        net.minecraft.nbt.ListTag stored = syncedStoredPets;

        for (int i = 0; i < stored.size(); i++) {
            net.minecraft.nbt.CompoundTag data = stored.getCompound(i);

            try {
                UUID uuid = data.getUUID("UUID");
                storedMap.put(uuid, data);
            } catch (Exception ignored) {
            }
        }

        java.util.Set<UUID> handled = new java.util.HashSet<>();

        for (net.minecraft.world.entity.Entity entity : this.minecraft.level.entitiesForRendering()) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (entity.isRemoved()) continue;

            UUID ownerUUID = null;

            try {
                if (living instanceof net.minecraft.world.entity.TamableAnimal tamable) {
                    ownerUUID = tamable.getOwnerUUID();
                } else if (living instanceof net.minecraft.world.entity.animal.horse.AbstractHorse horse) {
                    ownerUUID = horse.getOwnerUUID();
                } else if (living instanceof net.minecraft.world.entity.OwnableEntity ownable) {
                    ownerUUID = ownable.getOwnerUUID();
                }
            } catch (Exception ignored) {
            }

            boolean ownedByPlayer = ownerUUID != null && ownerUUID.equals(this.minecraft.player.getUUID());
            UUID entityUUID = living.getUUID();

            if (ownedByPlayer || storedMap.containsKey(entityUUID)) {
                handled.add(entityUUID);

                String name = living.hasCustomName()
                        ? living.getCustomName().getString()
                        : living.getType().getDescription().getString();

                SAVED_PETS.add(new JournalEntry(
                        name,
                        "Status: Active",
                        JournalEntry.Category.PETS,
                        living.getType(),
                        null,
                        java.util.List.of(),
                        entityUUID
                ));
            }
        }

        for (java.util.Map.Entry<UUID, net.minecraft.nbt.CompoundTag> entry : storedMap.entrySet()) {
            UUID uuid = entry.getKey();

            if (handled.contains(uuid)) continue;

            net.minecraft.nbt.CompoundTag data = entry.getValue();
            String petId = data.contains("id") ? data.getString("id") : "";
            String displayName = "Unknown Pet";

            if (data.contains("CustomName", 8)) {
                try {
                    Component nameComponent = Component.Serializer.fromJson(data.getString("CustomName"));

                    if (nameComponent != null) {
                        displayName = nameComponent.getString();
                    }
                } catch (Exception ignored) {
                }
            } else if (!petId.isEmpty()) {
                displayName = net.minecraft.world.entity.EntityType.byString(petId)
                        .map(type -> type.getDescription().getString())
                        .orElse("Pet");
            }

            SAVED_PETS.add(new JournalEntry(
                    "[] " + displayName,
                    "Eternal Backup",
                    JournalEntry.Category.PETS,
                    net.minecraft.world.entity.EntityType.byString(petId).orElse(null),
                    null,
                    java.util.List.of(),
                    uuid
            ));
        }

        this.petEntries.clear();
        this.petEntries.addAll(SAVED_PETS);
    }
    private void addStoredCompanionEntries(net.minecraft.nbt.ListTag stored, String fallbackName) {
        for (int i = 0; i < stored.size(); i++) {
            net.minecraft.nbt.CompoundTag data = stored.getCompound(i);

            UUID uuid;

            try {
                uuid = data.getUUID("UUID");
            } catch (Exception ignored) {
                continue;
            }

            String entityId = data.contains("id") ? data.getString("id") : "";
            String displayName = fallbackName;

            if (data.contains("CustomName", 8)) {
                try {
                    Component nameComponent = Component.Serializer.fromJson(data.getString("CustomName"));

                    if (nameComponent != null) {
                        displayName = nameComponent.getString();
                    }
                } catch (Exception ignored) {
                }
            } else if (!entityId.isEmpty()) {
                displayName = net.minecraft.world.entity.EntityType.byString(entityId)
                        .map(type -> type.getDescription().getString())
                        .orElse(fallbackName);
            }

            final String finalDisplayName = displayName;
            final String finalEntityId = entityId;
            final UUID finalUuid = uuid;
            final String finalFallbackName = fallbackName;

            this.petList.addPet(Component.literal(" - " + finalDisplayName), () -> {
                // Selection support can be expanded later for summon/delete/open-actions.
                this.selectedPet = new JournalEntry(
                        finalDisplayName,
                        "Registered " + finalFallbackName,
                        JournalEntry.Category.PETS,
                        net.minecraft.world.entity.EntityType.byString(finalEntityId).orElse(null),
                        null,
                        java.util.List.of(),
                        finalUuid
                );
            });
        }
    }
    private void handlePetAction() {
        if (this.selectedPet != null && this.selectedPet.entityUUID() != null) {
            RileyModPackets.sendToServer(new PetActionPacket(this.selectedPet.entityUUID(), 0));
            RileyModPackets.sendToServer(new RequestPetDataPacket());
            this.onClose();
        }
    }

    private void handleStorePet() {
        if (this.selectedPet != null && this.selectedPet.entityUUID() != null) {
            RileyModPackets.sendToServer(new PetActionPacket(this.selectedPet.entityUUID(), 1));
            RileyModPackets.sendToServer(new RequestPetDataPacket());
            this.onClose();
        }
    }

    private void handleDeletePet() {
        if (this.selectedPet != null && this.selectedPet.entityUUID() != null) {
            RileyModPackets.sendToServer(new PetActionPacket(this.selectedPet.entityUUID(), 2));
            RileyModPackets.sendToServer(new RequestPetDataPacket());

            UUID uuid = this.selectedPet.entityUUID();
            SAVED_PETS.removeIf(entry -> uuid.equals(entry.entityUUID()));
            this.petEntries.removeIf(entry -> uuid.equals(entry.entityUUID()));

            this.selectedPet = null;
            this.init();
        }
    }

    public static void forgetPet(UUID uuid) {
        SAVED_PETS.removeIf(entry -> uuid.equals(entry.entityUUID()));
    }

    private boolean isSelectedPetDead() {
        if (this.selectedPet == null || this.selectedPet.entityUUID() == null || this.minecraft == null || this.minecraft.level == null) {
            return false;
        }

        for (net.minecraft.world.entity.Entity entity : this.minecraft.level.entitiesForRendering()) {
            if (entity.getUUID().equals(this.selectedPet.entityUUID())) {
                if (entity instanceof LivingEntity living) {
                    return !living.isAlive();
                }

                return true;
            }
        }

        return true;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);

        int x = (this.width - 256) / 2;
        int y = (this.height - 180) / 2;

        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        graphics.blit(GUI_TEXTURE, x, y, 0, 0, 256, 180);

        graphics.drawString(this.font, "Pets", x + 20, y + 18, 0x303030, false);

        boolean hasSelectedPet = this.selectedPet != null;

        this.summonButton.visible = hasSelectedPet;
        this.storeButton.visible = hasSelectedPet;
        this.releaseButton.visible = hasSelectedPet;

        if (hasSelectedPet) {
            this.summonButton.setMessage(this.isSelectedPetDead()
                    ? Component.literal("Revive")
                    : Component.literal("Summon"));

            graphics.drawString(this.font, this.selectedPet.title(), x + 145, y + 28, 0x303030, false);
            this.renderPetStatusPage(graphics, x + 145, y + 45);
        } else {
            graphics.drawString(this.font, "Select a pet from the list.", x + 145, y + 45, 0x505050, false);
        }

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    private void renderPetStatusPage(GuiGraphics graphics, int x, int y) {
        if (this.minecraft == null || this.minecraft.level == null || this.selectedPet == null) return;

        UUID targetUUID = this.selectedPet.entityUUID();
        LivingEntity livePet = null;

        for (net.minecraft.world.entity.Entity entity : this.minecraft.level.entitiesForRendering()) {
            if (entity.getUUID().equals(targetUUID) && entity instanceof LivingEntity living) {
                livePet = living;
                break;
            }
        }

        int currentY = y;

        if (livePet != null) {
            graphics.drawString(this.font, String.format("Health: %.1f / %.1f", livePet.getHealth(), livePet.getMaxHealth()), x, currentY, 0x303030, false);
            currentY += 10;

            String lifeStatus = livePet.isAlive() ? "Status: Alive" : "Status: Dead";
            graphics.drawString(this.font, lifeStatus, x, currentY, livePet.isAlive() ? 0x228B22 : 0x8B0000, false);

            int buttonY = y + 85;
            this.summonButton.setY(buttonY);
            this.storeButton.setY(buttonY);
            this.releaseButton.visible = false;
        } else {
            graphics.drawString(this.font, "Status: Stored", x, currentY, 0x8B0000, false);
            currentY += 12;

            List<net.minecraft.util.FormattedCharSequence> lines = this.font.split(
                    Component.literal("This pet is no longer nearby. It may have evolved or moved away."),
                    90
            );

            for (net.minecraft.util.FormattedCharSequence line : lines) {
                graphics.drawString(this.font, line, x, currentY, 0x505050, false);
                currentY += 9;
            }

            currentY += 6;
            this.releaseButton.setY(currentY);
            this.releaseButton.visible = true;

            int buttonY = currentY + 18;
            this.summonButton.setY(buttonY);
            this.storeButton.setY(buttonY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class PetEntryList extends ContainerObjectSelectionList<PetEntryList.Entry> {
        public PetEntryList(Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight) {
            super(minecraft, width, height, top, bottom, itemHeight);
            this.setRenderBackground(false);
            this.setRenderHeader(false, 0);
        }

        public void addPet(Component title, Runnable onPress) {
            this.addEntry(new Entry(new PetTextButton(10, 0, 90, 10, title, button -> onPress.run())));
        }
        public void addHeader(Component title) {
            this.addEntry(new Entry(new PetTextButton(0, 0, 100, 10, title, button -> {
            })));
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            this.renderList(graphics, mouseX, mouseY, partialTicks);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
            this.setScrollAmount(this.getScrollAmount() - scroll * (double) this.itemHeight / 2.0D);
            return true;
        }

        @Override
        public int getRowWidth() {
            return 110;
        }

        @Override
        protected int getScrollbarPosition() {
            return this.x0 + this.width - 5;
        }

        public static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
            final PetTextButton button;

            public Entry(PetTextButton button) {
                this.button = button;
            }

            @Override
            public void render(GuiGraphics graphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTicks) {
                this.button.setX(left);
                this.button.setY(top);
                this.button.render(graphics, mouseX, mouseY, partialTicks);
            }

            @Override
            public List<? extends GuiEventListener> children() {
                return List.of(this.button);
            }

            @Override
            public List<? extends NarratableEntry> narratables() {
                return List.of(this.button);
            }
        }
    }

    static class PetTextButton extends Button {
        public PetTextButton(int x, int y, int width, int height, Component message, OnPress onPress) {
            super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            int color = this.isHoveredOrFocused() ? 0xFFFFFF : 0x303030;
            graphics.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX(), this.getY(), color, false);
        }
    }
}