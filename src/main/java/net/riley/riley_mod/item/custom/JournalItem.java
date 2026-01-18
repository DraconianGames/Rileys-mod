package net.riley.riley_mod.item.custom;

import net.minecraft.core.Registry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.registries.ForgeRegistries;

public class JournalItem extends Item {
    public JournalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) {
            // Open screen only on client
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> openJournalScreen());
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }


    private void openJournalScreen() {
        Minecraft.getInstance().setScreen(new net.riley.riley_mod.entity.client.JournalScreen());
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        // Use the Level getter; field access is not allowed in these mappings
        if (player.level().isClientSide()) {
            return InteractionResult.PASS;
        }

        // Only operate server-side with a ServerPlayer
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }

        // Ensure the held item is this journal
        ItemStack held = player.getItemInHand(hand);
        if (held.isEmpty() || held.getItem() != this) return InteractionResult.PASS;

        // Resolve owner UUID for TamableAnimal, AbstractHorse, or general OwnableEntity
        UUID ownerUUID = null;
        if (target instanceof TamableAnimal tamable) {
            ownerUUID = tamable.getOwnerUUID();
        } else if (target instanceof AbstractHorse horse) {
            ownerUUID = horse.getOwnerUUID();
        } else if (target instanceof OwnableEntity ownable) {
            ownerUUID = ownable.getOwnerUUID();
        }

        // If it's not an owned entity, or owner doesn't match player, abort
        if (ownerUUID == null || !ownerUUID.equals(player.getUUID())) {
            return InteractionResult.PASS;
        }

        // Call the existing server-side store logic
        storePet(serverPlayer, held, target.getUUID());

        // Return sided success using the Level getter
        return InteractionResult.sidedSuccess(player.level().isClientSide());
    }

    public static void storePet(ServerPlayer player, ItemStack journal, UUID uuid) {
        ServerLevel level = player.serverLevel();
        Entity entity = level.getEntity(uuid);

        if (entity == null) return;

        // Resolve owner UUID for multiple pet types (TamableAnimal, AbstractHorse, OwnableEntity)
        UUID ownerUUID = null;
        if (entity instanceof TamableAnimal tamable) {
            ownerUUID = tamable.getOwnerUUID();
        } else if (entity instanceof AbstractHorse horse) {
            ownerUUID = horse.getOwnerUUID();
        } else if (entity instanceof OwnableEntity ownable) {
            ownerUUID = ownable.getOwnerUUID();
        }

        if (ownerUUID == null || !ownerUUID.equals(player.getUUID())) {
            // Not your pet — do nothing
            return;
        }

        spawnMagicCircle(level, entity.getX(), entity.getY(), entity.getZ());

        CompoundTag petData = new CompoundTag();
        // Persist entity data. Prefer LivingEntity.save for living entities.
        if (entity instanceof LivingEntity living) {
            living.save(petData);
            // CLEANING THE BACKUP
            petData.putFloat("Health", living.getMaxHealth()); // Reset to Full HP
            petData.remove("ActiveEffects"); // Strip all potion effects (Freeze, Slowness, etc.)
        } else {
            entity.save(petData);
        }

        // Ensure UUID is present so the client can reconstruct/display properly.
        try {
            petData.putUUID("UUID", uuid);
        } catch (Exception ignored) {}

        CompoundTag nbt = journal.getOrCreateTag();
        ListTag pets = nbt.getList("StoredPets", Tag.TAG_COMPOUND);

        // Update existing entry or add new one
        boolean found = false;
        for (int i = 0; i < pets.size(); i++) {
            CompoundTag existing = pets.getCompound(i);
            if (existing != null && existing.contains("UUID")) {
                try {
                    if (existing.getUUID("UUID").equals(uuid)) {
                        pets.set(i, petData);
                        found = true;
                        break;
                    }
                } catch (Exception ignored) {}
            }
        }
        if (!found) pets.add(petData);

        nbt.put("StoredPets", pets);

        // Optionally discard the entity so it appears "stored"
        entity.discard();

        player.displayClientMessage(Component.literal("Pet data synchronized to Journal."), true);
    }


    public static void deletePetData(ServerPlayer player, ItemStack journal, UUID uuid) {
        if (journal.hasTag()) {
            ListTag pets = journal.getTag().getList("StoredPets", Tag.TAG_COMPOUND);
            for (int i = 0; i < pets.size(); i++) {
                if (pets.getCompound(i).getUUID("UUID").equals(uuid)) {
                    pets.remove(i);
                    player.displayClientMessage(Component.literal("Pet data released from journal."), true);
                    // write back modified list
                    CompoundTag nbt = journal.getOrCreateTag();
                    nbt.put("StoredPets", pets);
                    return;
                }
            }
        }
    }

    public static void summonPet(ServerPlayer player, ItemStack journal, UUID uuid) {
        ServerLevel level = player.serverLevel();

        // 1. If it's already alive in the world, just bring it to us
        Entity existing = level.getEntity(uuid);
        if (existing instanceof LivingEntity living && living.isAlive()) {
            living.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
            spawnMagicCircle(level, player.getX(), player.getY(), player.getZ());
            return;
        }

        // 2. If dead or missing, spawn a fresh copy from the backup
        if (!journal.hasTag()) return;
        ListTag pets = journal.getTag().getList("StoredPets", Tag.TAG_COMPOUND);

        for (int i = 0; i < pets.size(); i++) {
            CompoundTag petData = pets.getCompound(i);
            if (petData.getUUID("UUID").equals(uuid)) {

                if (existing != null) existing.discard(); // Remove corpse if it exists

                Entity newEntity = EntityType.loadEntityRecursive(petData, level, (e) -> {
                    e.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                    if (e instanceof LivingEntity le) le.setHealth(le.getMaxHealth());
                    return e;
                });

                if (newEntity != null) {
                    level.addFreshEntity(newEntity);
                    spawnMagicCircle(level, player.getX(), player.getY(), player.getZ());
                    player.displayClientMessage(Component.literal("Summoned/Revived " + newEntity.getDisplayName().getString()), true);
                    return;
                }
            }
        }
    }
    private static void spawnMagicCircle(ServerLevel level, double x, double y, double z) {
        double radius = 1.0;
        int particles = 30;
        for (int i = 0; i < particles; i++) {
            double angle = 2 * Math.PI * i / particles;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;

            // Using SOUL_FIRE_FLAME for a "magic" blue look, or FLAME for orange
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                    x + offsetX, y + 0.1, z + offsetZ,
                    1, 0, 0, 0, 0.02);
        }
        // Add a burst in the center
        level.sendParticles(ParticleTypes.LARGE_SMOKE, x, y + 0.5, z, 5, 0.2, 0.2, 0.2, 0.05);
    }
}