package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class LandVehicleEntity extends BaseVehicleEntity {
    private float deltaRotation;
//todo allow partial block variation for step height
    protected LandVehicleEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
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

    protected double getWheelGroundProbeDepth() {
        return 0.12D;
    }

    protected double getWheelForwardProbePadding() {
        return 0.08D;
    }

    protected double getWheelSupportProbeDepth() {
        return 1.25D;
    }

    protected double getWheelSupportSnapDownDistance() {
        return 0.35D;
    }

    protected double getVehicleGravity() {
        return 0.08D;
    }

    protected double getVehicleMaxFallSpeed() {
        return 1.25D;
    }

    protected double getClientVerticalSmoothing() {
        return 0.35D;
    }

    protected void setVehicleYFromWheelSupport(double wantedY) {
        double yChange = wantedY - this.getY();

        if (Math.abs(yChange) <= 1.0E-4D) {
            return;
        }

        if (this.level().isClientSide && Math.abs(yChange) < 0.75D) {
            this.setPos(this.getX(), this.getY() + yChange * this.getClientVerticalSmoothing(), this.getZ());
        } else {
            this.setPos(this.getX(), wantedY, this.getZ());
        }

        this.tickVehicleParts();
    }

    protected void applyVehicleGravity() {
        Vec3 movement = this.getDeltaMovement();
        double fallSpeed = Math.max(movement.y - this.getVehicleGravity(), -this.getVehicleMaxFallSpeed());

        this.setDeltaMovement(movement.x, fallSpeed, movement.z);
        this.move(MoverType.SELF, new Vec3(0.0D, fallSpeed, 0.0D));
        this.setOnGround(false);
        this.tickVehicleParts();
        this.repositionVehiclePassengers();
    }

    protected double findBestWheelSupportY(Vec3 projectedMovement) {
        double bestSupportY = Double.NEGATIVE_INFINITY;

        for (VehiclePartEntity wheel : this.getWheelParts()) {
            AABB wheelBox = wheel.getBoundingBox().move(projectedMovement.x, 0.0D, projectedMovement.z);

            AABB supportProbeBox = new AABB(
                    wheelBox.minX,
                    wheelBox.minY - this.getWheelSupportProbeDepth(),
                    wheelBox.minZ,
                    wheelBox.maxX,
                    wheelBox.minY + this.getVehicleStepHeight() + 0.05D,
                    wheelBox.maxZ
            );

            for (VoxelShape shape : this.level().getBlockCollisions(this, supportProbeBox)) {
                if (shape.isEmpty()) {
                    continue;
                }

                AABB collisionBox = shape.bounds();

                if (!collisionBox.intersects(supportProbeBox)) {
                    continue;
                }

                if (collisionBox.maxY > wheelBox.minY + this.getVehicleStepHeight() + 0.05D) {
                    continue;
                }

                if (collisionBox.maxY < wheelBox.minY - this.getWheelSupportProbeDepth()) {
                    continue;
                }

                bestSupportY = Math.max(bestSupportY, collisionBox.maxY);
            }
        }

        return bestSupportY;
    }

    protected double getLowestWheelBottom() {
        double lowestWheelBottom = Double.POSITIVE_INFINITY;

        for (VehiclePartEntity wheel : this.getWheelParts()) {
            lowestWheelBottom = Math.min(lowestWheelBottom, wheel.getBoundingBox().minY);
        }

        return lowestWheelBottom;
    }

    protected boolean tryStepUp(Vec3 horizontalMovement) {
        if (horizontalMovement.horizontalDistanceSqr() <= 1.0E-7D) {
            return false;
        }

        double lowestWheelBottom = this.getLowestWheelBottom();

        if (lowestWheelBottom == Double.POSITIVE_INFINITY) {
            return false;
        }

        double bestSupportY = this.findBestWheelSupportY(horizontalMovement);

        if (bestSupportY == Double.NEGATIVE_INFINITY) {
            return false;
        }

        double stepHeightNeeded = bestSupportY - lowestWheelBottom;

        if (stepHeightNeeded <= 1.0E-4D) {
            return false;
        }

        if (stepHeightNeeded > this.getVehicleStepHeight() + 0.05D) {
            return false;
        }

        this.setVehicleYFromWheelSupport(this.getY() + stepHeightNeeded);
        this.setDeltaMovement(this.getDeltaMovement().x, 0.0D, this.getDeltaMovement().z);
        this.setOnGround(true);
        this.fallDistance = 0.0F;
        this.tickVehicleParts();

        return true;
    }

    protected boolean isAnyWheelTouchingGround() {
        for (VehiclePartEntity wheel : this.getWheelParts()) {
            AABB groundCheckBox = wheel.getBoundingBox().move(0.0D, -this.getWheelGroundProbeDepth(), 0.0D);

            if (!this.level().noCollision(this, groundCheckBox)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isBumperBlockedByTallObstacle(float forwardInput) {
        if (Math.abs(forwardInput) <= 0.01F) {
            return false;
        }

        Vec3 localMovementDirection = forwardInput > 0.0F
                ? new Vec3(0.0D, 0.0D, 1.0D)
                : new Vec3(0.0D, 0.0D, -1.0D);

        Vec3 movementDirection = this.rotateVehicleOffset(localMovementDirection).normalize();
        double probeDistance = Math.max(
                0.35D,
                Math.abs(forwardInput) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) + 0.35D
        );

        Vec3 probeMovement = movementDirection.scale(probeDistance);

        for (VehiclePartEntity bumper : this.getBumperParts()) {
            Vec3 localBumperOffset = bumper.getVehiclePart().offset();

            if (localBumperOffset.dot(localMovementDirection) <= 0.0D) {
                continue;
            }

            AABB bumperBox = bumper.getBoundingBox()
                    .move(probeMovement)
                    .inflate(0.03D, 0.0D, 0.03D);

            AABB tallObstacleProbe = new AABB(
                    bumperBox.minX,
                    this.getY() + this.getVehicleStepHeight() + 0.02D,
                    bumperBox.minZ,
                    bumperBox.maxX,
                    this.getY() + this.getVehicleStepHeight() + 1.5D,
                    bumperBox.maxZ
            );

            if (!this.level().noCollision(this, tallObstacleProbe)) {
                return true;
            }
        }

        return false;
    }

    protected boolean isWheelPathBlocked(float forwardInput) {
        if (Math.abs(forwardInput) <= 0.01F) {
            return false;
        }

        Vec3 localMovementDirection = forwardInput > 0.0F
                ? new Vec3(0.0D, 0.0D, 1.0D)
                : new Vec3(0.0D, 0.0D, -1.0D);

        Vec3 movementDirection = this.rotateVehicleOffset(localMovementDirection).normalize();
        double probeDistance = Math.max(
                0.12D,
                Math.abs(forwardInput) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) + this.getWheelForwardProbePadding()
        );

        Vec3 probeMovement = movementDirection.scale(probeDistance);

        for (VehiclePartEntity wheel : this.getWheelParts()) {
            AABB movedWheelBox = wheel.getBoundingBox().move(probeMovement);

            AABB wheelObstacleProbe = new AABB(
                    movedWheelBox.minX,
                    movedWheelBox.minY,
                    movedWheelBox.minZ,
                    movedWheelBox.maxX,
                    movedWheelBox.minY + this.getVehicleStepHeight() + movedWheelBox.getYsize() + 0.05D,
                    movedWheelBox.maxZ
            );

            for (VoxelShape shape : this.level().getBlockCollisions(this, wheelObstacleProbe)) {
                if (shape.isEmpty()) {
                    continue;
                }

                AABB collisionBox = shape.bounds();

                if (!collisionBox.intersects(wheelObstacleProbe)) {
                    continue;
                }

                double stepHeightNeeded = collisionBox.maxY - movedWheelBox.minY;

                if (stepHeightNeeded > this.getVehicleStepHeight() + 0.05D) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void applyWheelSupport() {
        double currentVehicleY = this.getY();
        double lowestWheelBottom = this.getLowestWheelBottom();
        double bestSupportY = this.findBestWheelSupportY(Vec3.ZERO);

        if (bestSupportY == Double.NEGATIVE_INFINITY || lowestWheelBottom == Double.POSITIVE_INFINITY) {
            this.applyVehicleGravity();
            return;
        }

        double wantedVehicleY = currentVehicleY + (bestSupportY - lowestWheelBottom);
        double yChange = wantedVehicleY - currentVehicleY;

        if (yChange > this.getVehicleStepHeight() + 0.05D) {
            this.applyVehicleGravity();
            return;
        }

        if (yChange < -this.getWheelSupportSnapDownDistance()) {
            this.applyVehicleGravity();
            return;
        }

        this.setVehicleYFromWheelSupport(wantedVehicleY);

        this.setDeltaMovement(
                this.getDeltaMovement().x,
                0.0D,
                this.getDeltaMovement().z
        );
        this.setOnGround(true);
        this.fallDistance = 0.0F;
        this.tickVehicleParts();
    }

    protected void repositionVehiclePassengers() {
        for (Entity passenger : this.getPassengers()) {
            this.positionRider(passenger);
        }
    }

    @Override
    public void tick() {
        super.tick();

        this.tickVehicleParts();

        LivingEntity driver = this.getControllingPassenger();

        if (this.isVehicle() && driver != null) {
            this.tickDrivenMovement(driver);
        }

        this.tickVehicleParts();
        this.applyWheelSupport();
    }
//todo fix car falling through things when wheel not on block but middle is
    protected void tickDrivenMovement(LivingEntity driver) {
        this.setMaxUpStep(this.getVehicleStepHeight());

        float forwardInput = driver.zza;
        float turnInput = driver.xxa;

        this.deltaRotation = 0.0F;

        if (Math.abs(forwardInput) > 0.01F) {
            float turnSpeed = this.getTurnSpeed();

            if (forwardInput < 0.0F) {
                turnSpeed *= this.getReverseTurnMultiplier();
            }

            this.deltaRotation = -turnInput * turnSpeed;
            this.setYRot(this.getYRot() + this.deltaRotation);

            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            this.tickVehicleParts();
        }

        float speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);

        if (forwardInput < 0.0F) {
            speed *= this.getReverseSpeedMultiplier();
        }

        Vec3 horizontalMovement = this.getVehicleForwardVector().scale(forwardInput * speed);

        if (Math.abs(forwardInput) > 0.01F) {
            this.tryStepUp(horizontalMovement);
            this.tickVehicleParts();
        }

        boolean wheelBlocked = this.isWheelPathBlocked(forwardInput);
        boolean bumperBlocked = this.isBumperBlockedByTallObstacle(forwardInput);

        if (wheelBlocked || bumperBlocked) {
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
            return;
        }

        this.setDeltaMovement(horizontalMovement.x, this.getDeltaMovement().y, horizontalMovement.z);
        this.move(MoverType.SELF, new Vec3(horizontalMovement.x, 0.0D, horizontalMovement.z));
        this.tickVehicleParts();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isVehicle() && this.getControllingPassenger() != null) {
            this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
            return;
        }

        this.deltaRotation = 0.0F;
        this.setMaxUpStep(this.getVehicleStepHeight());
    }
    protected Vec3 getVehicleForwardVector() {
        return new Vec3(
                -Math.sin(this.getYRot() * ((float) Math.PI / 180F)),
                0.0D,
                Math.cos(this.getYRot() * ((float) Math.PI / 180F))
        );
    }

    @Override
    protected float getPassengerYawChange() {
        return this.deltaRotation;
    }
}