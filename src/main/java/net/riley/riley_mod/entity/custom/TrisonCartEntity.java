package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.menu.TrisonCartMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class TrisonCartEntity extends PathfinderMob implements MenuProvider {
    private static final EntityDataAccessor<Optional<UUID>> ATTACHED_TRISON =
            SynchedEntityData.defineId(TrisonCartEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> HAS_COVER =
            SynchedEntityData.defineId(TrisonCartEntity.class, EntityDataSerializers.BOOLEAN);

    private static final double HITCH_DISTANCE = 3.0D;
    private static final double STOPPING_DISTANCE = 2.0D;
    private static final double SNAP_DISTANCE = 0.35D;
    private static final double TRISON_MOVING_SPEED_SQR = 0.0025D;
    private static final double FOLLOW_STEP = 0.45D;

    private static final Vec3 RIDER_1_OFFSET = new Vec3(1.4D, 1.80D, -4.75D);
    private static final Vec3 RIDER_2_OFFSET = new Vec3(-1.4D, 1.80D, -4.75D);

    private final net.minecraft.world.SimpleContainer cartInventory = new net.minecraft.world.SimpleContainer(52);

    private @Nullable Vec3 lockedHitchTarget = null;
    private @Nullable UUID lastResolvedTrisonUuid = null;
    private Vec3 lastPullDirection = new Vec3(0.0D, 0.0D, -1.0D);
    private @Nullable Vec3 previousHitchTarget = null;

    public TrisonCartEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACHED_TRISON, Optional.empty());
        this.entityData.define(HAS_COVER, true);
    }

    public void setAttachedTrison(@Nullable UUID trisonUuid) {
        this.entityData.set(ATTACHED_TRISON, Optional.ofNullable(trisonUuid));
    }

    public @Nullable UUID getAttachedTrisonUuid() {
        return this.entityData.get(ATTACHED_TRISON).orElse(null);
    }

    public boolean hasAttachedTrison() {
        return this.getAttachedTrisonUuid() != null;
    }

    public boolean isAttachedTo(Entity entity) {
        return entity != null && entity.getUUID().equals(this.getAttachedTrisonUuid());
    }
    private void resetTrailerState() {
        this.lockedHitchTarget = null;
        this.lastResolvedTrisonUuid = null;
        this.previousHitchTarget = null;
        this.lastPullDirection = new Vec3(0.0D, 0.0D, -1.0D);
        this.setDeltaMovement(Vec3.ZERO);
        this.getNavigation().stop();
    }


    public boolean attachToTrison(TrisonEntity trison) {
        if (trison == null || !trison.isAlive()) {
            return false;
        }

        this.setAttachedTrison(trison.getUUID());
        this.lastResolvedTrisonUuid = trison.getUUID();
        this.lockedHitchTarget = createTrailerTarget(trison);
        this.previousHitchTarget = this.lockedHitchTarget;
        this.snapBehindTrison(trison);
        return true;
    }

    public void detachFromTrison() {
        this.setAttachedTrison(null);
        this.resetTrailerState();
    }

    public @Nullable Entity getAttachedTrisonEntity() {
        UUID attachedUuid = this.getAttachedTrisonUuid();
        if (attachedUuid == null) {
            return null;
        }

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(64.0D))) {
            if (entity.getUUID().equals(attachedUuid)) {
                return entity;
            }
        }

        return null;
    }
    private Vec3 getCartToTrisonDirection(Entity trison) {
        Vec3 fromCartToTrison = new Vec3(
                trison.getX() - this.getX(),
                0.0D,
                trison.getZ() - this.getZ()
        );

        if (fromCartToTrison.lengthSqr() < 1.0E-6D) {
            return this.lastPullDirection.lengthSqr() > 1.0E-6D ? this.lastPullDirection : new Vec3(0.0D, 0.0D, 1.0D);
        }

        return fromCartToTrison.normalize();
    }

    private Vec3 createTrailerTarget(Entity trison) {
        Vec3 directionToCartSide = getCartToTrisonDirection(trison);

        // target should be behind the trison, opposite of trison -> cart direction
        Vec3 behindDirection = directionToCartSide.scale(-1.0D);

        this.lastPullDirection = behindDirection;

        return new Vec3(
                trison.getX() + behindDirection.x * HITCH_DISTANCE,
                trison.getY(),
                trison.getZ() + behindDirection.z * HITCH_DISTANCE
        );
    }

    private Vec3 getLockedTrailerTarget(Entity trison) {
        UUID currentUuid = trison.getUUID();

        if (this.lastResolvedTrisonUuid == null || !this.lastResolvedTrisonUuid.equals(currentUuid)) {
            this.lastResolvedTrisonUuid = currentUuid;
            this.lockedHitchTarget = createTrailerTarget(trison);
            this.previousHitchTarget = this.lockedHitchTarget;
            return this.lockedHitchTarget;
        }

        Vec3 currentTarget = createTrailerTarget(trison);

        if (this.previousHitchTarget == null) {
            this.previousHitchTarget = currentTarget;
        }

        if (this.previousHitchTarget.distanceToSqr(currentTarget) > TRISON_MOVING_SPEED_SQR) {
            this.lockedHitchTarget = currentTarget;
        } else if (this.lockedHitchTarget == null) {
            this.lockedHitchTarget = currentTarget;
        }

        this.previousHitchTarget = currentTarget;
        return this.lockedHitchTarget;
    }

    private static final float MAX_TURN_PER_TICK = 10.0F;

    private void setCartYaw(float yaw) {
        this.setYRot(yaw);
        this.setYHeadRot(yaw);
        this.yHeadRot = yaw;
        this.yBodyRot = yaw;
    }

    private void rotateTowardYaw(float targetYaw) {
        float currentYaw = this.getYRot();
        float delta = Mth.wrapDegrees(targetYaw - currentYaw);
        float step = Mth.clamp(delta, -MAX_TURN_PER_TICK, MAX_TURN_PER_TICK);
        this.setCartYaw(currentYaw + step);
    }

    private void rotateTowardPosition(Vec3 target) {
        double dx = target.x - this.getX();
        double dz = target.z - this.getZ();
        if (dx * dx + dz * dz < 1.0E-6D) {
            return;
        }

        float targetYaw = (float) (Mth.atan2(-dx, dz) * (180F / Math.PI));
        this.rotateTowardYaw(targetYaw);
    }

    private void updateRotationFromMovement() {
        double dx = this.getX() - this.xo;
        double dz = this.getZ() - this.zo;
        double horizontalMoveSqr = dx * dx + dz * dz;

        if (horizontalMoveSqr < 1.0E-6D) {
            return;
        }

        float targetYaw = (float) (Mth.atan2(-dx, dz) * (180F / Math.PI));
        this.rotateTowardYaw(targetYaw);
    }

    private void pullToward(Vec3 target) {
        Vec3 current = this.position();
        Vec3 toTarget = target.subtract(current);
        Vec3 horizontal = new Vec3(toTarget.x, 0.0D, toTarget.z);
        double horizontalDistance = horizontal.length();

        if (horizontalDistance < 1.0E-6D) {
            return;
        }

        double step = Math.min(FOLLOW_STEP, horizontalDistance);
        Vec3 move = horizontal.normalize().scale(step);

        this.setPos(this.getX() + move.x, target.y, this.getZ() + move.z);
    }

    public void snapBehindTrison(Entity trison) {
        if (trison == null) {
            return;
        }

        Vec3 target = createTrailerTarget(trison);
        this.lockedHitchTarget = target;
        this.previousHitchTarget = target;
        this.lastResolvedTrisonUuid = trison.getUUID();

        this.setPos(target.x, target.y, target.z);
        this.setCartYaw(trison.getYRot());
        this.setDeltaMovement(Vec3.ZERO);
        this.getNavigation().stop();
    }

    public boolean hasCover() {
        return this.entityData.get(HAS_COVER);
    }

    public void setCover(boolean hasCover) {
        this.entityData.set(HAS_COVER, hasCover);
    }

    public void toggleCover() {
        this.setCover(!this.hasCover());
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            if (!this.hasAttachedTrison()) {
                this.resetTrailerState();
                return;
            }

            Entity trison = this.getAttachedTrisonEntity();
            if (trison == null) {
                this.detachFromTrison();
                return;
            }

            Vec3 oldTarget = this.lockedHitchTarget;
            Vec3 target = getLockedTrailerTarget(trison);

            boolean hitchMoving = oldTarget != null && oldTarget.distanceToSqr(target) > TRISON_MOVING_SPEED_SQR;
            double distanceToTargetSqr = this.position().distanceToSqr(target);
            double stopDistanceSqr = STOPPING_DISTANCE * STOPPING_DISTANCE;
            double snapDistanceSqr = SNAP_DISTANCE * SNAP_DISTANCE;

            if (!hitchMoving) {
                if (distanceToTargetSqr > snapDistanceSqr) {
                    this.setPos(target.x, target.y, target.z);
                }

                this.setDeltaMovement(Vec3.ZERO);
                this.getNavigation().stop();
                this.rotateTowardPosition(new Vec3(trison.getX(), this.getY(), trison.getZ()));
            } else if (distanceToTargetSqr > stopDistanceSqr) {
                this.pullToward(target);
                this.updateRotationFromMovement();
            } else {
                this.setDeltaMovement(Vec3.ZERO);
                this.getNavigation().stop();
                this.rotateTowardPosition(new Vec3(trison.getX(), this.getY(), trison.getZ()));
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            if (player.isShiftKeyDown()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, this, buffer -> buffer.writeInt(this.getId()));
                }
                return InteractionResult.CONSUME;
            }

            if (this.getPassengers().size() < 2) {
                player.startRiding(this);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        return null;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        int passengerIndex = this.getPassengers().indexOf(passenger);
        Vec3 seatOffset = getSeatOffset(passengerIndex);

        if (seatOffset == null) {
            super.positionRider(passenger, moveFunction);
            return;
        }

        Vec3 rotatedOffset = seatOffset.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
        moveFunction.accept(
                passenger,
                this.getX() + rotatedOffset.x,
                this.getY() + rotatedOffset.y + passenger.getMyRidingOffset(),
                this.getZ() + rotatedOffset.z
        );

        passenger.setYBodyRot(this.getYRot());
        passenger.setYRot(this.getYRot());
        passenger.setYHeadRot(this.getYRot());
    }

    private @Nullable Vec3 getSeatOffset(int passengerIndex) {
        return switch (passengerIndex) {
            case 0 -> RIDER_1_OFFSET;
            case 1 -> RIDER_2_OFFSET;
            default -> null;
        };
    }

    public net.minecraft.world.SimpleContainer getCartInventory() {
        return this.cartInventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Trison Cart");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory playerInventory, Player player) {
        return new TrisonCartMenu(containerId, playerInventory, this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        UUID attachedUuid = this.getAttachedTrisonUuid();
        if (attachedUuid != null) {
            tag.putUUID("AttachedTrison", attachedUuid);
        }

        tag.putBoolean("HasCover", this.hasCover());

        net.minecraft.nbt.ListTag inventoryTag = new net.minecraft.nbt.ListTag();
        for (int i = 0; i < this.cartInventory.getContainerSize(); i++) {
            net.minecraft.world.item.ItemStack stack = this.cartInventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("Slot", (byte) i);
                stack.save(stackTag);
                inventoryTag.add(stackTag);
            }
        }
        tag.put("CartInventory", inventoryTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.hasUUID("AttachedTrison")) {
            this.setAttachedTrison(tag.getUUID("AttachedTrison"));
        } else {
            this.setAttachedTrison(null);
        }

        this.setCover(tag.getBoolean("HasCover"));

        net.minecraft.nbt.ListTag inventoryTag = tag.getList("CartInventory", 10);
        for (int i = 0; i < this.cartInventory.getContainerSize(); i++) {
            this.cartInventory.setItem(i, net.minecraft.world.item.ItemStack.EMPTY);
        }

        for (int i = 0; i < inventoryTag.size(); i++) {
            CompoundTag stackTag = inventoryTag.getCompound(i);
            int slot = stackTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < this.cartInventory.getContainerSize()) {
                this.cartInventory.setItem(slot, net.minecraft.world.item.ItemStack.of(stackTag));
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.5D);
    }
}