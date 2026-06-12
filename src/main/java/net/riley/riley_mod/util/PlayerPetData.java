package net.riley.riley_mod.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.riley.riley_mod.entity.custom.BaseVehicleEntity;

import java.util.UUID;

public class PlayerPetData {
    private static final String ROOT_TAG = Player.PERSISTED_NBT_TAG;
    private static final String PETS_TAG = "RileyModStoredPets";
    private static final String MOUNTS_TAG = "RileyModStoredMounts";
    private static final String VEHICLES_TAG = "RileyModStoredVehicles";

    public static ListTag getPets(ServerPlayer player) {
        CompoundTag persisted = getPersistedData(player);
        return persisted.getList(PETS_TAG, Tag.TAG_COMPOUND);
    }

    public static ListTag getMounts(ServerPlayer player) {
        CompoundTag persisted = getPersistedData(player);
        return persisted.getList(MOUNTS_TAG, Tag.TAG_COMPOUND);
    }

    public static ListTag getVehicles(ServerPlayer player) {
        CompoundTag persisted = getPersistedData(player);
        return persisted.getList(VEHICLES_TAG, Tag.TAG_COMPOUND);
    }

    public static void storeRiddenCompanion(ServerPlayer player) {
        Entity vehicle = player.getVehicle();

        if (vehicle == null) {
            player.displayClientMessage(Component.literal("You are not riding anything."), true);
            return;
        }

        if (vehicle instanceof BaseVehicleEntity) {
            storeCompanion(player, vehicle, VEHICLES_TAG, "Vehicle registered.");
            return;
        }

        if (vehicle instanceof LivingEntity) {
            UUID ownerUUID = getOwnerUUID(vehicle);

            if (ownerUUID != null && !ownerUUID.equals(player.getUUID())) {
                player.displayClientMessage(Component.literal("That mount does not belong to you."), true);
                return;
            }

            storeCompanion(player, vehicle, MOUNTS_TAG, "Mount registered.");
            return;
        }

        player.displayClientMessage(Component.literal("This cannot be registered."), true);
    }

    public static void storePet(ServerPlayer player, UUID uuid) {
        ServerLevel level = player.serverLevel();
        Entity entity = level.getEntity(uuid);

        if (entity == null) {
            player.displayClientMessage(Component.literal("Could not find that pet nearby."), true);
            return;
        }

        UUID ownerUUID = getOwnerUUID(entity);

        if (ownerUUID == null || !ownerUUID.equals(player.getUUID())) {
            player.displayClientMessage(Component.literal("That is not your pet."), true);
            return;
        }

        storeCompanion(player, entity, PETS_TAG, "Pet stored.");
        spawnMagicCircle(level, entity.getX(), entity.getY(), entity.getZ());
        entity.discard();
    }

    private static void storeCompanion(ServerPlayer player, Entity entity, String listTag, String successMessage) {
        CompoundTag entityData = new CompoundTag();

        if (entity instanceof LivingEntity living) {
            living.save(entityData);
            entityData.putFloat("Health", living.getMaxHealth());
            entityData.remove("ActiveEffects");
        } else {
            entity.save(entityData);
        }

        UUID uuid = entity.getUUID();
        entityData.putUUID("UUID", uuid);

        CompoundTag persisted = getPersistedData(player);
        ListTag companions = persisted.getList(listTag, Tag.TAG_COMPOUND);

        boolean found = false;

        for (int i = 0; i < companions.size(); i++) {
            CompoundTag existing = companions.getCompound(i);

            if (existing.contains("UUID") && existing.getUUID("UUID").equals(uuid)) {
                companions.set(i, entityData);
                found = true;
                break;
            }
        }

        if (!found) {
            companions.add(entityData);
        }

        persisted.put(listTag, companions);
        savePersistedData(player, persisted);

        player.displayClientMessage(Component.literal(successMessage), true);
    }

    public static void summonPet(ServerPlayer player, UUID uuid) {
        ServerLevel level = player.serverLevel();

        Entity existing = level.getEntity(uuid);

        if (existing instanceof LivingEntity living && living.isAlive()) {
            living.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
            spawnMagicCircle(level, player.getX(), player.getY(), player.getZ());
            player.displayClientMessage(Component.literal("Pet recalled."), true);
            return;
        }

        ListTag pets = getPets(player);

        for (int i = 0; i < pets.size(); i++) {
            CompoundTag petData = pets.getCompound(i);

            if (!petData.contains("UUID") || !petData.getUUID("UUID").equals(uuid)) {
                continue;
            }

            if (existing != null) {
                existing.discard();
            }

            Entity newEntity = EntityType.loadEntityRecursive(petData, level, entity -> {
                entity.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());

                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.setHealth(livingEntity.getMaxHealth());
                }

                return entity;
            });

            if (newEntity != null) {
                level.addFreshEntity(newEntity);
                spawnMagicCircle(level, player.getX(), player.getY(), player.getZ());
                player.displayClientMessage(Component.literal("Summoned " + newEntity.getDisplayName().getString()), true);
            }

            return;
        }

        player.displayClientMessage(Component.literal("No saved data found for that pet."), true);
    }

    public static void deletePet(ServerPlayer player, UUID uuid) {
        CompoundTag persisted = getPersistedData(player);
        ListTag pets = persisted.getList(PETS_TAG, Tag.TAG_COMPOUND);

        for (int i = 0; i < pets.size(); i++) {
            CompoundTag petData = pets.getCompound(i);

            if (petData.contains("UUID") && petData.getUUID("UUID").equals(uuid)) {
                pets.remove(i);
                persisted.put(PETS_TAG, pets);
                savePersistedData(player, persisted);

                player.displayClientMessage(Component.literal("Pet data released."), true);
                return;
            }
        }
    }

    private static CompoundTag getPersistedData(ServerPlayer player) {
        CompoundTag playerData = player.getPersistentData();

        if (!playerData.contains(ROOT_TAG, Tag.TAG_COMPOUND)) {
            playerData.put(ROOT_TAG, new CompoundTag());
        }

        return playerData.getCompound(ROOT_TAG);
    }

    private static void savePersistedData(ServerPlayer player, CompoundTag persisted) {
        player.getPersistentData().put(ROOT_TAG, persisted);
    }

    private static UUID getOwnerUUID(Entity entity) {
        if (entity instanceof TamableAnimal tamable) {
            return tamable.getOwnerUUID();
        }

        if (entity instanceof AbstractHorse horse) {
            return horse.getOwnerUUID();
        }

        if (entity instanceof OwnableEntity ownable) {
            return ownable.getOwnerUUID();
        }

        return null;
    }

    private static void spawnMagicCircle(ServerLevel level, double x, double y, double z) {
        double radius = 1.0;
        int particles = 30;

        for (int i = 0; i < particles; i++) {
            double angle = 2 * Math.PI * i / particles;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;

            level.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    x + offsetX,
                    y + 0.1,
                    z + offsetZ,
                    1,
                    0,
                    0,
                    0,
                    0.02
            );
        }

        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                x,
                y + 0.5,
                z,
                5,
                0.2,
                0.2,
                0.2,
                0.05
        );
    }
}