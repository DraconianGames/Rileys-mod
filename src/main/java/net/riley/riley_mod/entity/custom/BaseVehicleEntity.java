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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.menu.BaseVehicleMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseVehicleEntity extends Mob implements MenuProvider, OwnableEntity {
    private static final EntityDataAccessor<Boolean> HAS_WRECKER_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_CARGO_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_ARMOR_UPGRADE =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FILLED_CARGO_STORAGE_SLOTS =
            SynchedEntityData.defineId(BaseVehicleEntity.class, EntityDataSerializers.INT);


    private UUID ownerUUID;
    private VehiclePartEntity[] vehicleParts = new VehiclePartEntity[0];

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
            BaseVehicleEntity.this.syncCargoStorageFillState();
        }
    };


    protected BaseVehicleEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(this.getVehicleStepHeight());
        this.noPhysics = true;
        this.noCulling = true;
        this.createVehicleParts();
    }

    private void createVehicleParts() {
        List<VehicleHitboxPart> hitboxParts = this.getVehicleHitboxParts();
        this.vehicleParts = new VehiclePartEntity[hitboxParts.size()];

        for (int i = 0; i < hitboxParts.size(); i++) {
            this.vehicleParts[i] = new VehiclePartEntity(this, hitboxParts.get(i));
        }
    }

    @Override
    public void setId(int id) {
        super.setId(id);

        for (int i = 0; i < this.vehicleParts.length; i++) {
            this.vehicleParts[i].setId(id + i + 1);
        }
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

    public int getFilledCargoStorageSlots() {
        return this.entityData.get(FILLED_CARGO_STORAGE_SLOTS);
    }

    public float getCargoStorageFillProgress() {
        return (float) this.getFilledCargoStorageSlots() / (float) BaseVehicleMenu.VEHICLE_STORAGE_SLOT_COUNT;
    }

    public boolean isValidVehicleUpgrade(ItemStack stack) {
        return stack.is(RileyModItems.WRECKER_UPGRADE.get())
                || stack.is(RileyModItems.CARGO_UPGRADE.get())
                || stack.is(RileyModItems.ARMOR_UPGRADE.get());
    }

    protected void syncVehicleUpgradeState() {
        if (!this.level().isClientSide) {
            this.entityData.set(HAS_WRECKER_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.WRECKER_UPGRADE.get()));
            this.entityData.set(HAS_CARGO_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.CARGO_UPGRADE.get()));
            this.entityData.set(HAS_ARMOR_UPGRADE, this.getVehicleUpgradeItem().is(RileyModItems.ARMOR_UPGRADE.get()));
        }
    }

    protected void syncCargoStorageFillState() {
        if (this.level().isClientSide) {
            return;
        }

        int filledSlots = 0;

        for (int i = 1; i < this.vehicleInventory.getContainerSize(); i++) {
            if (!this.vehicleInventory.getItem(i).isEmpty()) {
                filledSlots++;
            }
        }

        this.entityData.set(FILLED_CARGO_STORAGE_SLOTS, filledSlots);
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
    public boolean canCollideWith(Entity entity) {
        return entity != null && !this.hasPassenger(entity) && super.canCollideWith(entity);
    }

    @Override
    public boolean isInWall() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    protected float getVehicleStepHeight() {
        return 1.2F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    protected Vec3 getSeatOffset(int passengerIndex) {
        return Vec3.ZERO;
    }

    protected float getPassengerYawChange() {
        return 0.0F;
    }

    protected List<VehicleHitboxPart> getVehicleHitboxParts() {
        return List.of();
    }

    protected Vec3 rotateVehicleOffset(Vec3 offset) {
        return offset.yRot(-this.getYRot() * ((float) Math.PI / 180F));
    }

    protected void tickVehicleParts() {
        if (this.vehicleParts.length != this.getVehicleHitboxParts().size()) {
            this.createVehicleParts();
        }

        for (VehiclePartEntity partEntity : this.vehicleParts) {
            VehicleHitboxPart part = partEntity.getVehiclePart();
            Vec3 rotatedOffset = this.rotateVehicleOffset(part.offset());

            partEntity.setPos(
                    this.getX() + rotatedOffset.x,
                    this.getY() + rotatedOffset.y,
                    this.getZ() + rotatedOffset.z
            );
        }
    }

    protected List<VehiclePartEntity> getVehiclePartsByType(VehicleHitboxPart.VehicleHitboxType type) {
        List<VehiclePartEntity> matchingParts = new ArrayList<>();

        for (VehiclePartEntity part : this.vehicleParts) {
            if (part.getPartType() == type) {
                matchingParts.add(part);
            }
        }

        return matchingParts;
    }

    protected List<VehiclePartEntity> getWheelParts() {
        return this.getVehiclePartsByType(VehicleHitboxPart.VehicleHitboxType.WHEEL);
    }

    protected List<VehiclePartEntity> getBumperParts() {
        return this.getVehiclePartsByType(VehicleHitboxPart.VehicleHitboxType.BUMPER);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.vehicleParts;
    }

    protected boolean canPassengerRide(Player player) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    public InteractionResult interactVehiclePart(VehiclePartEntity partEntity, Player player, InteractionHand hand) {
        VehicleHitboxPart part = partEntity.getVehiclePart();

        if (this.hasPassenger(player)) {
            return InteractionResult.PASS;
        }

        if (!this.level().isClientSide && this.ownerUUID == null) {
            this.setOwnerUUID(player.getUUID());
            player.displayClientMessage(Component.literal("Vehicle claimed."), true);
        }

        if (part.type() == VehicleHitboxPart.VehicleHitboxType.MENU) {
            this.openVehicleMenu(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (part.type() == VehicleHitboxPart.VehicleHitboxType.SEAT) {
            if (!this.level().isClientSide && this.canPassengerRide(player)) {
                player.startRiding(this);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_WRECKER_UPGRADE, false);
        this.entityData.define(HAS_CARGO_UPGRADE, false);
        this.entityData.define(HAS_ARMOR_UPGRADE, false);
        this.entityData.define(FILLED_CARGO_STORAGE_SLOTS, 0);
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
        return false;
    }

    public boolean hurtVehiclePart(VehiclePartEntity partEntity, DamageSource source, float amount) {
        if (partEntity.isWheel()) {
            amount *= 0.75F;
        }

        if (partEntity.isBumper()) {
            amount *= 0.5F;
        }

        return this.hurt(source, amount);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.IN_WALL)) {
            return false;
        }

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
    public boolean shouldRenderAtSqrDistance(double distance) {
        double size = this.getBoundingBox().getSize();
        double renderDistance = Math.max(64.0D, size * 64.0D);

        return distance < renderDistance * renderDistance;
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

        this.tickVehicleParts();

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
        this.setMaxUpStep(this.getVehicleStepHeight());
        super.travel(travelVector);
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
                this.getY() + seatOffset.y,
                this.getZ() + rotatedOffset.z
        );

        if (passenger instanceof LivingEntity livingPassenger) {
            livingPassenger.fallDistance = 0.0F;
        }

        float yawChange = this.getPassengerYawChange();

        passenger.setYRot(passenger.getYRot() + yawChange);
        passenger.setYHeadRot(passenger.getYHeadRot() + yawChange);
        passenger.yRotO += yawChange;
    }
}