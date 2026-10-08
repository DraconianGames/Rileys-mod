package net.riley.riley_mod.entity.custom;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFlyingMountEntity extends AbstractInventoryMountEntity {
    private static final int FLIGHT_TOGGLE_COOLDOWN = 10;

    private boolean flying = false;
    private boolean flightUpInput = false;
    private boolean flightDownInput = false;
    private int lastFlightToggleTick = 0;

    protected AbstractFlyingMountEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
        super(entityType, level);
    }

    protected double getFlightSpeed() {
        return 0.55D;
    }

    protected double getVerticalFlightSpeed() {
        return 0.35D;
    }

    protected double getFlightDriftDamping() {
        return 0.65D;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;

        if (!flying) {
            this.flightUpInput = false;
            this.flightDownInput = false;
        }
    }

    public void toggleFlight() {
        if (this.tickCount - this.lastFlightToggleTick > FLIGHT_TOGGLE_COOLDOWN) {
            this.setFlying(!this.flying);
            this.lastFlightToggleTick = this.tickCount;
        }
    }

    public void setFlightInput(boolean upArrow, boolean downArrow) {
        this.flightUpInput = upArrow;
        this.flightDownInput = downArrow;
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (!this.flying) {
            super.travel(travelVector);
            return;
        }

        Vec3 currentMotion = this.getDeltaMovement();

        double newX = currentMotion.x * this.getFlightDriftDamping();
        double newZ = currentMotion.z * this.getFlightDriftDamping();

        LivingEntity controllingPassenger = this.getControllingPassenger();
        if (controllingPassenger instanceof Player) {
            float strafe = controllingPassenger.xxa;
            float forward = controllingPassenger.zza;

            Vec3 input = new Vec3(strafe, 0.0D, forward);
            if (input.lengthSqr() > 1.0E-7D) {
                input = input.normalize()
                        .scale(this.getFlightSpeed())
                        .yRot(-controllingPassenger.getYRot() * Mth.DEG_TO_RAD);

                newX = input.x;
                newZ = input.z;
            }
        }

        double newY = 0.0D;

        if (this.flightUpInput && !this.flightDownInput) {
            newY = this.getVerticalFlightSpeed();
        } else if (this.flightDownInput && !this.flightUpInput) {
            newY = -this.getVerticalFlightSpeed();
        }

        this.setDeltaMovement(newX, newY, newZ);
        this.hasImpulse = true;
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.fallDistance = 0.0F;
    }

    @Override
    public boolean canJump() {
        return !this.flying && super.canJump();
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        if (!this.flying) {
            super.onPlayerJump(jumpPower);
        }
    }

    @Override
    public void handleStartJump(int jumpPower) {
        if (!this.flying) {
            super.handleStartJump(jumpPower);
        }
    }

    @Override
    public void handleStopJump() {
        if (!this.flying) {
            super.handleStopJump();
        }
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }
}