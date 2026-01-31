package net.riley.riley_mod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.block.entity.registry.RileyModBlockEntities;
import net.riley.riley_mod.menu.SpecialSpawnerMenu;

import javax.annotation.Nullable;

public class SpecialSpawnerBlockEntity extends BlockEntity implements MenuProvider {

    private String mobId = "";
    private int radius = 4;
    private int count = 1;

    public SpecialSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(RileyModBlockEntities.SPECIAL_SPAWNER_BE.get(), pos, state);
    }

    public String getMobId() { return mobId; }
    public int getRadius() { return radius; }
    public int getCount() { return count; }

    public boolean hasMobSelected() {
        return mobId != null && !mobId.isBlank();
    }

    public void applySettings(String mobId, int radius, int count) {
        this.mobId = mobId == null ? "" : mobId.trim();
        this.radius = Math.max(0, Math.min(radius, 64));
        this.count = Math.max(1, Math.min(count, 64));
        setChanged();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Special Spawner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new SpecialSpawnerMenu(containerId, inventory, this.getBlockPos());
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, SpecialSpawnerBlockEntity be) {
        // Only trigger if configured
        if (!be.hasMobSelected()) return;

        // Find nearest SURVIVAL player within 16 blocks
        Player nearest = level.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16.0, false);
        if (nearest == null) return;
        if (nearest.isCreative() || nearest.isSpectator()) return;

        // Trigger: spawn + remove block
        spawnConfigured(level, pos, be.mobId, be.radius, be.count);
        level.removeBlock(pos, false);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SpecialSpawnerBlockEntity be) {
        if (level.isClientSide) return;
        if (!(level instanceof ServerLevel serverLevel)) return;

        if (!be.hasMobSelected()) return;

        Player nearest = serverLevel.getNearestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16.0, false);
        if (nearest == null) return;
        if (nearest.isCreative() || nearest.isSpectator()) return;

        spawnConfigured(serverLevel, pos, be.mobId, be.radius, be.count);
        serverLevel.removeBlock(pos, false);
    }

    private static void spawnConfigured(ServerLevel level, BlockPos pos, String mobId, int radius, int count) {
        ResourceLocation rl = ResourceLocation.tryParse(mobId);
        if (rl == null) return;

        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(rl);
        if (type == null) return;

        RandomSource random = level.getRandom();
        for (int i = 0; i < count; i++) {
            Entity e = type.create(level);
            if (e == null) continue;

            double dx = (random.nextDouble() * 2.0 - 1.0) * radius;
            double dz = (random.nextDouble() * 2.0 - 1.0) * radius;

            double x = pos.getX() + 0.5 + dx;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + dz;

            e.moveTo(x, y, z, random.nextFloat() * 360.0F, 0.0F);
            level.addFreshEntity(e);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("MobId", mobId);
        tag.putInt("Radius", radius);
        tag.putInt("Count", count);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        mobId = tag.getString("MobId");
        radius = tag.getInt("Radius");
        count = tag.getInt("Count");
    }
}