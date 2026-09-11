package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SummoningCircleEntity extends Entity {
    private static final int LIFETIME_TICKS = 70; // 3.5 seconds at 20 TPS

    public final AnimationState releaseAnimationState = new AnimationState();

    public SummoningCircleEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.releaseAnimationState.startIfStopped(this.tickCount);
        }

        if (!this.level().isClientSide() && this.tickCount >= LIFETIME_TICKS) {
            this.discard();
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public boolean isPickable() {
        return false;
    }
}