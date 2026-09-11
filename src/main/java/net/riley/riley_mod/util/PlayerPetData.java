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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.BaseVehicleEntity;
import net.riley.riley_mod.network.PetSummonChargeParticlePacket;
import net.riley.riley_mod.network.RileyModPackets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerPetData {
    private static final String ROOT_TAG = Player.PERSISTED_NBT_TAG;
    private static final String PETS_TAG = "RileyModStoredPets";
    private static final String MOUNTS_TAG = "RileyModStoredMounts";
    private static final String VEHICLES_TAG = "RileyModStoredVehicles";
    private static final int SUMMON_ANIMATION_TICKS = 70;

    private static final List<ScheduledSummon> SCHEDULED_SUMMONS = new ArrayList<>();
    private static final Set<UUID> PET_SUMMON_CHARGING_PLAYERS = new HashSet<>();

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
        spawnSmokeScreen(level, entity.getX(), entity.getY(), entity.getZ(), entity);
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
        summonPetAt(player, uuid, player.getX(), player.getY(), player.getZ());
    }

    public static void summonPetAt(ServerPlayer player, UUID uuid, double summonX, double summonY, double summonZ) {
        ServerLevel level = player.serverLevel();

        Entity existing = level.getEntity(uuid);

        if (existing instanceof LivingEntity living && living.isAlive()) {
            double x = summonX;
            double y = summonY;
            double z = summonZ;
            float yRot = player.getYRot();
            float xRot = player.getXRot();

            spawnSummoningCircle(level, x, y, z);

            SCHEDULED_SUMMONS.add(new ScheduledSummon(level, SUMMON_ANIMATION_TICKS, () -> {
                living.moveTo(x, y, z, yRot, xRot);
                spawnSmokeScreen(level, x, y, z, living);
                player.displayClientMessage(Component.literal("Pet recalled."), true);
            }));

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

            double x = summonX;
            double y = summonY;
            double z = summonZ;
            float yRot = player.getYRot();
            float xRot = player.getXRot();

            spawnSummoningCircle(level, x, y, z);

            SCHEDULED_SUMMONS.add(new ScheduledSummon(level, SUMMON_ANIMATION_TICKS, () -> {
                Entity newEntity = EntityType.loadEntityRecursive(petData, level, entity -> {
                    entity.moveTo(x, y, z, yRot, xRot);

                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.setHealth(livingEntity.getMaxHealth());
                    }

                    return entity;
                });

                if (newEntity != null) {
                    spawnSmokeScreen(level, x, y, z, newEntity);
                    level.addFreshEntity(newEntity);
                    player.displayClientMessage(Component.literal("Summoned " + newEntity.getDisplayName().getString()), true);
                }
            }));

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

    public static void setPetSummonCharging(ServerPlayer player, boolean charging) {
        if (charging) {
            PET_SUMMON_CHARGING_PLAYERS.add(player.getUUID());
        } else {
            PET_SUMMON_CHARGING_PLAYERS.remove(player.getUUID());
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        spawnPetSummonChargeParticles();

        Iterator<ScheduledSummon> iterator = SCHEDULED_SUMMONS.iterator();

        while (iterator.hasNext()) {
            ScheduledSummon summon = iterator.next();

            if (summon.level.isClientSide()) {
                iterator.remove();
                continue;
            }

            summon.ticksLeft--;

            if (summon.ticksLeft <= 0) {
                summon.action.run();
                iterator.remove();
            }
        }
    }
    private static void spawnPetSummonChargeParticles() {
        Iterator<UUID> iterator = PET_SUMMON_CHARGING_PLAYERS.iterator();

        while (iterator.hasNext()) {
            UUID playerUUID = iterator.next();
            ServerPlayer player = null;

            for (ServerPlayer onlinePlayer : net.minecraftforge.server.ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                if (onlinePlayer.getUUID().equals(playerUUID)) {
                    player = onlinePlayer;
                    break;
                }
            }

            if (player == null || player.isRemoved()) {
                iterator.remove();
                continue;
            }

            spawnModelRightHandSoulFlame(player);
        }
    }


    private static void spawnModelRightHandSoulFlame(ServerPlayer player) {
        double yawRadians = Math.toRadians(player.yBodyRot);

        double forwardX = -Math.sin(yawRadians);
        double forwardZ = Math.cos(yawRadians);

        double rightX = -Math.cos(yawRadians);
        double rightZ = -Math.sin(yawRadians);

        double x = player.getX() + forwardX * 0.18D + rightX * 0.42D;
        double y = player.getY() + (player.isCrouching() ? 0.85D : 1.05D);
        double z = player.getZ() + forwardZ * 0.18D + rightZ * 0.42D;

        sendChargeParticleToNearbyPlayersExceptCaster(player, x, y, z);
    }

    private static void sendChargeParticleToNearbyPlayersExceptCaster(ServerPlayer caster, double x, double y, double z) {
        double maxDistanceSqr = 64.0D * 64.0D;

        for (ServerPlayer viewer : caster.serverLevel().players()) {
            if (viewer.getUUID().equals(caster.getUUID())) {
                continue;
            }

            if (viewer.distanceToSqr(caster) > maxDistanceSqr) {
                continue;
            }

            RileyModPackets.sendToPlayer(viewer, new PetSummonChargeParticlePacket(x, y, z));
        }
    }
    private static void spawnSummoningCircle(ServerLevel level, double x, double y, double z) {
        Entity circle = RileyModEntities.SUMMONING_CIRCLE_ENTITY.get().create(level);

        if (circle == null) {
            return;
        }

        circle.moveTo(x, y + 0.02D, z, 0.0F, 0.0F);
        level.addFreshEntity(circle);
    }

    private static void spawnSmokeScreen(ServerLevel level, double x, double y, double z, Entity entity) {
        double width = entity == null ? 3.0D : Math.max(3.0D, entity.getBbWidth() + 2.0D);
        double height = entity == null ? 2.0D : Math.max(2.5D, entity.getBbHeight() + 0.75D);
        double radius = width * 0.6D;

        level.sendParticles(
                ParticleTypes.FLAME,
                x,
                y + 0.35D,
                z,
                140,
                radius,
                0.35D,
                radius,
                0.1D
        );

        level.sendParticles(
                ParticleTypes.SMOKE,
                x,
                y + height * 0.35D,
                z,
                180,
                radius,
                height * 0.3D,
                radius,
                0.06D
        );

        level.sendParticles(
                ParticleTypes.LARGE_SMOKE,
                x,
                y + height * 0.55D,
                z,
                220,
                radius,
                height * 0.45D,
                radius,
                0.07D
        );

        level.sendParticles(
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                x,
                y + height * 0.45D,
                z,
                80,
                radius * 0.85D,
                height * 0.35D,
                radius * 0.85D,
                0.04D
        );

        level.sendParticles(
                ParticleTypes.SOUL_FIRE_FLAME,
                x,
                y + 0.3D,
                z,
                70,
                radius * 0.75D,
                0.25D,
                radius * 0.75D,
                0.08D
        );
    }

    private static class ScheduledSummon {
        private final ServerLevel level;
        private int ticksLeft;
        private final Runnable action;

        private ScheduledSummon(ServerLevel level, int ticksLeft, Runnable action) {
            this.level = level;
            this.ticksLeft = ticksLeft;
            this.action = action;
        }
    }
}