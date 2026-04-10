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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.riley.riley_mod.menu.CustomMountMenu;
import net.riley.riley_mod.menu.MountInventoryAccess;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractInventoryMountEntity extends AbstractChestedHorse implements MenuProvider, MountInventoryAccess {
    public static final int SADDLE_SLOT = 0;
    public static final int ARMOR_SLOT = 1;
    public static final int STORAGE_START_SLOT = 2;

    private static final EntityDataAccessor<Boolean> HAS_MOUNT_SADDLE =
            SynchedEntityData.defineId(AbstractInventoryMountEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_MOUNT_ARMOR =
            SynchedEntityData.defineId(AbstractInventoryMountEntity.class, EntityDataSerializers.BOOLEAN);

    protected final SimpleContainer mountInventory;

    protected AbstractInventoryMountEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
        super(entityType, level);
        this.mountInventory = new SimpleContainer(this.getTotalMountSlotCount()) {
            @Override
            public void setChanged() {
                super.setChanged();
                AbstractInventoryMountEntity.this.syncMountInventoryState();
            }
        };
    }
//TODO change the tame logic to support the transformation logic
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_MOUNT_SADDLE, false);
        this.entityData.define(HAS_MOUNT_ARMOR, false);
    }

    protected int getTotalMountSlotCount() {
        return STORAGE_START_SLOT + this.getBaseStorageSlotCount();
    }

    protected int getBaseStorageSlotCount() {
        return this.getBaseStorageColumns() * this.getBaseStorageRows();
    }

    protected abstract int getBaseStorageColumns();

    protected abstract int getBaseStorageRows();

    protected boolean supportsChestStorage() {
        return this.getBaseStorageSlotCount() > 0;
    }

    @Override
    public SimpleContainer getMountInventory() {
        return this.mountInventory;
    }

    public ItemStack getMountSaddleItem() {
        return this.mountInventory.getItem(SADDLE_SLOT);
    }

    public ItemStack getMountArmorItem() {
        return this.mountInventory.getItem(ARMOR_SLOT);
    }

    @Override
    public boolean hasMountSaddle() {
        return this.entityData.get(HAS_MOUNT_SADDLE);
    }

    @Override
    public boolean hasMountArmor() {
        return this.entityData.get(HAS_MOUNT_ARMOR);
    }

    protected void syncMountInventoryState() {
        this.entityData.set(HAS_MOUNT_SADDLE, !this.getMountSaddleItem().isEmpty());
        this.entityData.set(HAS_MOUNT_ARMOR, !this.getMountArmorItem().isEmpty());
    }

    @Override
    public boolean canEquipMountSaddle(ItemStack stack) {
        return stack.is(Items.SADDLE);
    }

    @Override
    public abstract boolean canEquipMountArmor(ItemStack stack);

    public boolean equipMountSaddle(ItemStack stack) {
        if (!this.canEquipMountSaddle(stack) || this.hasMountSaddle()) {
            return false;
        }

        this.mountInventory.setItem(SADDLE_SLOT, stack.copyWithCount(1));
        this.mountInventory.setChanged();
        return true;
    }

    public boolean equipMountArmor(ItemStack stack) {
        if (!this.canEquipMountArmor(stack) || this.hasMountArmor()) {
            return false;
        }

        this.mountInventory.setItem(ARMOR_SLOT, stack.copyWithCount(1));
        this.mountInventory.setChanged();
        return true;
    }

    public ItemStack removeMountSaddle() {
        ItemStack removed = this.mountInventory.removeItemNoUpdate(SADDLE_SLOT);
        this.mountInventory.setChanged();
        return removed;
    }

    public ItemStack removeMountArmor() {
        ItemStack removed = this.mountInventory.removeItemNoUpdate(ARMOR_SLOT);
        this.mountInventory.setChanged();
        return removed;
    }

    @Override
    public boolean isSaddleable() {
        return !this.isBaby() && this.isTamed();
    }

    @Override
    public boolean isSaddled() {
        return this.hasMountSaddle();
    }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    @Override
    public boolean isArmor(ItemStack stack) {
        return this.canEquipMountArmor(stack);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        ListTag inventoryTag = new ListTag();
        for (int i = 0; i < this.mountInventory.getContainerSize(); i++) {
            ItemStack stack = this.mountInventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stackTag.putByte("Slot", (byte) i);
                stack.save(stackTag);
                inventoryTag.add(stackTag);
            }
        }
        compound.put("MountInventory", inventoryTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        for (int i = 0; i < this.mountInventory.getContainerSize(); i++) {
            this.mountInventory.setItem(i, ItemStack.EMPTY);
        }

        ListTag inventoryTag = compound.getList("MountInventory", 10);
        for (int i = 0; i < inventoryTag.size(); i++) {
            CompoundTag stackTag = inventoryTag.getCompound(i);
            int slot = stackTag.getByte("Slot") & 255;
            if (slot < 0 || slot >= this.mountInventory.getContainerSize()) {
                continue;
            }

            ItemStack stack = ItemStack.of(stackTag);
            if (slot == SADDLE_SLOT) {
                if (this.canEquipMountSaddle(stack)) {
                    this.mountInventory.setItem(slot, stack);
                }
            } else if (slot == ARMOR_SLOT) {
                if (this.canEquipMountArmor(stack)) {
                    this.mountInventory.setItem(slot, stack);
                }
            } else {
                this.mountInventory.setItem(slot, stack);
            }
        }

        this.mountInventory.setChanged();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isTamed() && !this.isBaby() && player.isSecondaryUseActive()) {
            this.openCustomInventoryScreen(player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (this.canEquipMountSaddle(itemStack) && this.isSaddleable() && !this.hasMountSaddle()) {
            if (!this.level().isClientSide && this.equipMountSaddle(itemStack) && !player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (this.canEquipMountArmor(itemStack) && this.isTamed() && !this.isBaby() && !this.hasMountArmor()) {
            if (!this.level().isClientSide && this.equipMountArmor(itemStack) && !player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (this.supportsChestStorage() && itemStack.is(Items.CHEST) && this.isTamed() && !this.isBaby() && !this.hasChest()) {
            if (!this.level().isClientSide) {
                this.setChest(true);
                this.createInventory();
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (!this.isTamed()) {
            return;
        }

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
        return new CustomMountMenu(containerId, playerInventory, this);
    }

    @Override
    public int getMountStorageColumns() {
        return this.hasChest() ? this.getBaseStorageColumns() : 0;
    }

    @Override
    public int getMountStorageRows() {
        return this.hasChest() ? this.getBaseStorageRows() : 0;
    }

    @Nullable
    protected Vec3 getCustomPassengerOffset(Entity passenger, int passengerIndex) {
        return null;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        int passengerIndex = this.getPassengers().indexOf(passenger);
        Vec3 customOffset = passengerIndex >= 0 ? this.getCustomPassengerOffset(passenger, passengerIndex) : null;

        if (customOffset == null) {
            super.positionRider(passenger, moveFunction);
            return;
        }

        Vec3 rotatedOffset = customOffset.yRot(-this.yBodyRot * ((float)Math.PI / 180F));
        moveFunction.accept(
                passenger,
                this.getX() + rotatedOffset.x,
                this.getY() + rotatedOffset.y + passenger.getMyRidingOffset(),
                this.getZ() + rotatedOffset.z
        );

        this.clampRotation(passenger);
    }

    protected void clampRotation(Entity passenger) {
        passenger.setYBodyRot(this.getYRot());
        float currentYaw = passenger.getYRot();
        float wrapped = net.minecraft.util.Mth.wrapDegrees(currentYaw - this.getYRot());
        float clamped = net.minecraft.util.Mth.clamp(wrapped, -160.0F, 160.0F);
        passenger.yRotO += clamped - wrapped;
        float finalYaw = currentYaw + clamped - wrapped;
        passenger.setYRot(finalYaw);
        passenger.setYHeadRot(finalYaw);
    }
}