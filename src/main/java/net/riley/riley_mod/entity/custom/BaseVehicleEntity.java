package net.riley.riley_mod.entity.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BaseVehicleEntity extends Mob {
    public final AnimationState parkAnimationState = new AnimationState();
    public final AnimationState forwardAnimationState = new AnimationState();
    public final AnimationState backwardAnimationState = new AnimationState();
    public final AnimationState steerLeftAnimationState = new AnimationState();
    public final AnimationState steerRightAnimationState = new AnimationState();

    protected BaseVehicleEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(this.getVehicleStepHeight());
    }

    protected float getVehicleStepHeight() {
        return 1.0F;
    }

    protected float getTurnSpeed() {
        return 5.0F;
    }

    protected float getReverseTurnMultiplier() {
        return -0.5F;
    }

    protected float getReverseSpeedMultiplier() {
        return 0.45F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    protected Vec3 getSeatOffset(int passengerIndex) {
        return new Vec3(0.0D, 0.0D, 0.0D);
    }

    protected boolean canPassengerRide(Player player) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.hasPassenger(player)) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide && this.canPassengerRide(player)) {
            player.startRiding(this);
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() != null;
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    public boolean isPickable() {
        return !this.isVehicle();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity attacker = source.getEntity();
        Entity directEntity = source.getDirectEntity();

        if (attacker != null && this.hasPassenger(attacker)) {
            return false;
        }

        if (directEntity instanceof Projectile projectile) {
            Entity owner = projectile.getOwner();

            if (owner != null && this.hasPassenger(owner)) {
                return false;
            }
        }

        boolean wasHurt = super.hurt(source, amount);

        this.hurtTime = 0;
        this.hurtDuration = 0;
        this.invulnerableTime = 0;

        return wasHurt;
    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    @Override
    public void aiStep() {
        super.aiStep();

        this.hurtTime = 0;
        this.hurtDuration = 0;
    }

    @Override
    protected void tickDeath() {
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.updateAnimationStates();
        }
    }

    protected void updateAnimationStates() {
        LivingEntity driver = this.getControllingPassenger();

        float forwardInput = driver != null ? driver.zza : 0.0F;
        float turnInput = driver != null ? driver.xxa : 0.0F;

        boolean movingForward = forwardInput > 0.01F;
        boolean movingBackward = forwardInput < -0.01F;
        boolean steeringLeft = turnInput > 0.01F;
        boolean steeringRight = turnInput < -0.01F;
        boolean parked = !movingForward && !movingBackward;

        this.parkAnimationState.animateWhen(parked, this.tickCount);
        this.forwardAnimationState.animateWhen(movingForward, this.tickCount);
        this.backwardAnimationState.animateWhen(movingBackward, this.tickCount);
        this.steerLeftAnimationState.animateWhen(steeringLeft, this.tickCount);
        this.steerRightAnimationState.animateWhen(steeringRight, this.tickCount);
    }

    @Override
    public void travel(Vec3 travelVector) {
        LivingEntity driver = this.getControllingPassenger();

        if (this.isVehicle() && driver != null) {
            this.setMaxUpStep(this.getVehicleStepHeight());

            float forwardInput = driver.zza;
            float turnInput = driver.xxa;

            boolean hasGas = Math.abs(forwardInput) > 0.01F;

            if (hasGas) {
                float turnSpeed = this.getTurnSpeed();

                if (forwardInput < 0.0F) {
                    turnSpeed *= this.getReverseTurnMultiplier();
                }

                this.setYRot(this.getYRot() - turnInput * turnSpeed);
                this.yRotO = this.getYRot();

                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
            }

            float speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);

            if (forwardInput < 0.0F) {
                speed *= this.getReverseSpeedMultiplier();
            }

            this.setSpeed(speed);

            super.travel(new Vec3(0.0D, travelVector.y, forwardInput));
        } else {
            this.setMaxUpStep(this.getVehicleStepHeight());
            super.travel(travelVector);
        }
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        int passengerIndex = this.getPassengers().indexOf(passenger);
        Vec3 seatOffset = this.getSeatOffset(passengerIndex);

        Vec3 rotatedOffset = seatOffset.yRot(-this.getYRot() * ((float) Math.PI / 180F));

        moveFunction.accept(
                passenger,
                this.getX() + rotatedOffset.x,
                this.getY() + rotatedOffset.y + passenger.getMyRidingOffset(),
                this.getZ() + rotatedOffset.z
        );
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }
}