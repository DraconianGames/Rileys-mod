package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;

public class VehiclePartEntity extends PartEntity<BaseVehicleEntity> {
    private final BaseVehicleEntity parent;
    private final VehicleHitboxPart vehiclePart;
    private final EntityDimensions size;

    public VehiclePartEntity(BaseVehicleEntity parent, VehicleHitboxPart vehiclePart) {
        super(parent);
        this.parent = parent;
        this.vehiclePart = vehiclePart;
        this.size = EntityDimensions.scalable(
                (float) Math.max(vehiclePart.width(), vehiclePart.depth()),
                (float) vehiclePart.height()
        );

        this.refreshDimensions();
    }

    public VehicleHitboxPart getVehiclePart() {
        return this.vehiclePart;
    }

    public String getPartName() {
        return this.vehiclePart.name();
    }

    public VehicleHitboxPart.VehicleHitboxType getPartType() {
        return this.vehiclePart.type();
    }

    public boolean isWheel() {
        return this.vehiclePart.type() == VehicleHitboxPart.VehicleHitboxType.WHEEL;
    }

    public boolean isBumper() {
        return this.vehiclePart.type() == VehicleHitboxPart.VehicleHitboxType.BUMPER;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean is(Entity entity) {
        return this == entity || this.parent == entity;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return this.parent.hurtVehiclePart(this, source, amount);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        return this.parent.interactVehiclePart(this, player, hand);
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.parent.getPickResult();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.size;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }
}