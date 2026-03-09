package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import net.riley.riley_mod.item.RileyModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class TrisonEntity extends AbstractInventoryMountEntity {
    private static final int STORAGE_COLUMNS = 5;
    private static final int STORAGE_ROWS = 3;
    private static final double RIDER_FORWARD_OFFSET = 0.125D;
    private static final double NORMAL_SPEED = 0.3D;
    private static final double HOOKED_SPEED = 0.18D;
    private static final double CART_HOOK_RADIUS = 5.0D;

    private static final EntityDataAccessor<Optional<UUID>> ATTACHED_CART =
            SynchedEntityData.defineId(TrisonEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;

    public TrisonEntity(EntityType<? extends TrisonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACHED_CART, Optional.empty());
    }

    @Override
    protected int getBaseStorageColumns() {
        return STORAGE_COLUMNS;
    }

    @Override
    protected int getBaseStorageRows() {
        return STORAGE_ROWS;
    }

    public void setAttachedCart(@Nullable UUID cartUuid) {
        this.entityData.set(ATTACHED_CART, Optional.ofNullable(cartUuid));
    }

    public @Nullable UUID getAttachedCartUuid() {
        return this.entityData.get(ATTACHED_CART).orElse(null);
    }

    public boolean hasAttachedCart() {
        return this.getAttachedCartUuid() != null;
    }

    public @Nullable TrisonCartEntity getAttachedCartEntity() {
        UUID cartUuid = this.getAttachedCartUuid();
        if (cartUuid == null) {
            return null;
        }

        for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(64.0D))) {
            if (entity instanceof TrisonCartEntity cart && entity.getUUID().equals(cartUuid)) {
                return cart;
            }
        }

        return null;
    }

    private @Nullable TrisonCartEntity findNearbyUnattachedCart() {
        AABB searchBox = this.getBoundingBox().inflate(CART_HOOK_RADIUS, 2.5D, CART_HOOK_RADIUS);
        double bestDistance = Double.MAX_VALUE;
        TrisonCartEntity closestCart = null;

        for (TrisonCartEntity cart : this.level().getEntitiesOfClass(TrisonCartEntity.class, searchBox)) {
            if (cart.getAttachedTrisonUuid() != null) {
                continue;
            }

            double distance = this.distanceToSqr(cart);
            if (distance < bestDistance) {
                bestDistance = distance;
                closestCart = cart;
            }
        }

        return closestCart;
    }

    public boolean attachNearbyCart() {
        TrisonCartEntity cart = this.findNearbyUnattachedCart();
        if (cart == null) {
            return false;
        }

        if (!cart.attachToTrison(this)) {
            return false;
        }

        cart.snapBehindTrison(this);
        this.setAttachedCart(cart.getUUID());
        return true;
    }

    public void detachCart() {
        TrisonCartEntity cart = this.getAttachedCartEntity();

        this.setAttachedCart(null);

        if (cart != null) {
            cart.detachFromTrison();
        }
    }

    private void updateHookedSpeed() {
        AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed == null) {
            return;
        }

        double targetSpeed = this.hasAttachedCart() ? HOOKED_SPEED : NORMAL_SPEED;
        if (movementSpeed.getBaseValue() != targetSpeed) {
            movementSpeed.setBaseValue(targetSpeed);
        }
    }

    @Override
    public boolean canEquipMountArmor(ItemStack stack) {
        return stack.is(RileyModItems.TRISON_ARMOR.get());
    }

    @Override
    protected @Nullable Vec3 getCustomPassengerOffset(Entity passenger, int passengerIndex) {
        if (passengerIndex == 0) {
            return new Vec3(0.0D, this.getPassengersRidingOffset(), RIDER_FORWARD_OFFSET);
        }
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide) {
            if (this.getPersistentData().getInt("InLove") > 0 && this.random.nextInt(7) == 0) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                        this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D),
                        0.0D, 0.0D, 0.0D);
            }
        } else {
            int inLove = this.getPersistentData().getInt("InLove");
            if (inLove > 0) {
                this.getPersistentData().putInt("InLove", inLove - 1);
            }

            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) {
                this.getPersistentData().putInt("BreedCooldown", cooldown - 1);
            }

            this.updateHookedSpeed();

            if (this.hasAttachedCart()) {
                this.getNavigation().stop();

                if (this.getFirstPassenger() == null) {
                    this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);
                }
            }

            TrisonCartEntity cart = this.getAttachedCartEntity();
            if (cart == null && this.hasAttachedCart()) {
                this.setAttachedCart(null);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAminationStates();
        }
    }

    private void setupAminationStates() {
        if (this.idleAminationTimeout < -0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        } else {
            f = 0f;
        }
        this.walkAnimation.update(f, .2f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(Items.CARROT)));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1D, Ingredient.of(Items.CARROT), false));
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (!this.level().isClientSide && pHand == InteractionHand.MAIN_HAND && pPlayer.isSecondaryUseActive()) {
            UUID ownerUuid = this.getOwnerUUID();
            if (!this.isTamed()  || ownerUuid == null || !ownerUuid.equals(pPlayer.getUUID())) {
                return InteractionResult.PASS;
            }

            if (this.hasAttachedCart()) {
                this.detachCart();
            } else {
                this.attachNearbyCart();
            }
            return InteractionResult.CONSUME;
        }

        if (this.hasAttachedCart()) {
            return super.mobInteract(pPlayer, pHand);
        }

        if (this.isFood(itemstack)) {
            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) return InteractionResult.PASS;

            if (this.getPersistentData().getInt("InLove") <= 0) {
                if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);
                this.getPersistentData().putInt("InLove", 600);
                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.CARROT);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, NORMAL_SPEED)
                .add(Attributes.ARMOR_TOUGHNESS, .5f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.JUMP_STRENGTH, 0.7D);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.7D;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        UUID cartUuid = this.getAttachedCartUuid();
        if (cartUuid != null) {
            tag.putUUID("AttachedCart", cartUuid);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.hasUUID("AttachedCart")) {
            this.setAttachedCart(tag.getUUID("AttachedCart"));
        } else {
            this.setAttachedCart(null);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.BISON.get().create(pLevel);
    }
}