package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.menu.BaseVehicleMenu;

import java.util.UUID;

public abstract class BaseVehicleEntity extends Mob implements MenuProvider, OwnableEntity {
    private static final EntityDataAccessor<Boolean> HAS_WRECKER_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_CARGO_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_ARMOR_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);

    private UUID ownerUUID;
    private float deltaRotation;
//TODO add inventory upgrade. inventory slot logic. and model logic.
    //TODO armor logic
    public final AnimationState parkAnimationState = new AnimationState();
    public final AnimationState forwardAnimationState = new AnimationState();
    public final AnimationState backwardAnimationState = new AnimationState();
    public final AnimationState steerLeftAnimationState = new AnimationState();
    public final AnimationState steerRightAnimationState = new AnimationState();
    private final SimpleContainer vehicleInventory = new SimpleContainer(BaseVehicleMenu.VEHICLE_SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            BaseVehicleEntity.this.syncVehicleUpgradeState();
        }
    };
    protected BaseVehicleEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(this.getVehicleStepHeight());
    }

    public SimpleContainer getVehicleInventory() {
        return this.vehicleInventory;
    }
    @Override
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public boolean isOwnedBy(Player player) {
        return this.ownerUUID != null && this.ownerUUID.equals(player.getUUID());
    }
    public ItemStack getVehicleUpgradeItem() {
        return this.vehicleInventory.getItem(BaseVehicleMenu.UPGRADE_SLOT_INDEX);
    }
    public boolean hasVehicleUpgrade(ItemStack stack) {
        ItemStack upgradeStack = this.getVehicleUpgradeItem();
        return !upgradeStack.isEmpty() && ItemStack.isSameItem(upgradeStack, stack);
    }

    public boolean hasWreckerUpgrade() {
        return this.entityData.get(HAS_WRECKER_UPGRADE);
    }

    public boolean hasCargoUpgrade() {
        return this.entityData.get(HAS_CARGO_UPGRADE);
    }
    public boolean hasArmorUpgrade() {
        return this.entityData.get(HAS_ARMOR_UPGRADE);
    }

    public boolean isValidVehicleUpgrade(ItemStack stack) {
        return stack.is(RileyModItems.WRECKER_UPGRADE.get()) ||
        stack.is(RileyModItems.CARGO_UPGRADE.get()) ||
        stack.is(RileyModItems.ARMOR_UPGRADE.get());
    }
    protected void syncVehicleUpgradeState() {
        if (!this.level().isClientSide) {
            this.entityData.set(HAS_WRECKER_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.WRECKER_UPGRADE.get()));
            this.entityData.set(HAS_CARGO_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.CARGO_UPGRADE.get()));
            this.entityData.set(HAS_ARMOR_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.ARMOR_UPGRADE.get()));
        }
    }
    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    protected float getVehicleStepHeight() {
        return 1.2F;
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
        this.entityData.define(HAS_WRECKER_UPGRADE, false);
        this.entityData.define(HAS_CARGO_UPGRADE, false);
        this.entityData.define(HAS_ARMOR_UPGRADE, false);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.hasPassenger(player)) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide && this.ownerUUID == null) {
            this.setOwnerUUID(player.getUUID());
            player.displayClientMessage(Component.literal("Vehicle claimed."), true);
        }

        if (player.isSecondaryUseActive()) {
            this.openVehicleMenu(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.level().isClientSide && this.canPassengerRide(player)) {
            player.startRiding(this);
        }

        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        if (this.ownerUUID != null) {
            compound.putUUID("Owner", this.ownerUUID);
        }

        ListTag inventoryTag = new ListTag();

        for (int i = 0; i < this.vehicleInventory.getContainerSize(); i++) {
            ItemStack stack = this.vehicleInventory.getItem(i);

            if (!stack.isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("Slot", (byte) i);
                stack.save(stackTag);
                inventoryTag.add(stackTag);
            }
        }

        compound.put("VehicleInventory", inventoryTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.hasUUID("Owner")) {
            this.ownerUUID = compound.getUUID("Owner");
        } else {
            this.ownerUUID = null;
        }

        for (int i = 0; i < this.vehicleInventory.getContainerSize(); i++) {
            this.vehicleInventory.setItem(i, ItemStack.EMPTY);
        }

        ListTag inventoryTag = compound.getList("VehicleInventory", 10);

        for (int i = 0; i < inventoryTag.size(); i++) {
            CompoundTag stackTag = inventoryTag.getCompound(i);
            int slot = stackTag.getByte("Slot") & 255;

            if (slot >= 0 && slot < this.vehicleInventory.getContainerSize()) {
                this.vehicleInventory.setItem(slot, ItemStack.of(stackTag));
            }
        }

        this.vehicleInventory.setChanged();
        this.syncVehicleUpgradeState();
    }

    protected void openVehicleMenu(Player player) {
        if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, this, buffer -> buffer.writeInt(this.getId()));
        }
    }
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BaseVehicleMenu(containerId, playerInventory, this);
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
        this.dropVehicleInventoryOnGround();
        this.remove(RemovalReason.KILLED);
    }

    private void dropVehicleInventoryOnGround() {
        if (this.level().isClientSide) {
            return;
        }

        for (int i = 0; i < this.vehicleInventory.getContainerSize(); i++) {
            ItemStack stack = this.vehicleInventory.getItem(i);

            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                this.vehicleInventory.setItem(i, ItemStack.EMPTY);
            }
        }

        this.vehicleInventory.setChanged();
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

            this.deltaRotation = 0.0F;

            if (hasGas) {
                float turnSpeed = this.getTurnSpeed();

                if (forwardInput < 0.0F) {
                    turnSpeed *= this.getReverseTurnMultiplier();
                }

                this.deltaRotation = -turnInput * turnSpeed;
                this.setYRot(this.getYRot() + this.deltaRotation);

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
            this.deltaRotation = 0.0F;
            this.setMaxUpStep(this.getVehicleStepHeight());
            super.travel(travelVector);
        }
    }
    @Override
    public boolean shouldRiderSit() {
        return true;
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

        passenger.setYRot(passenger.getYRot() + this.deltaRotation);
        passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
        passenger.setYBodyRot(passenger.getYRot());
    }

}