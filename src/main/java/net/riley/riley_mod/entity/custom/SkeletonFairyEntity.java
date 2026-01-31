package net.riley.riley_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import net.riley.riley_mod.item.RileyModItems;
import org.jetbrains.annotations.Nullable;

public class SkeletonFairyEntity extends TamableAnimal implements FlyingAnimal {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BoneFairyEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState begAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();

    public int attackAnimationTimeout = 0;

    public SkeletonFairyEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        // Use FlyingPathNavigation so it doesn't get stuck on fences/blocks

        // Standard move control for flying animals
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }
    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(false);
        return navigation;
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }
    private void setupAnimationStates() {
        if (this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 15; // Match your animation length (0.5s = 10 ticks)
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
        Player nearestPlayer = this.level().getNearestPlayer(this, 5.0D);
        boolean playerHoldingBegItem = nearestPlayer != null &&
                (nearestPlayer.getMainHandItem().is(RileyModItems.TOOTH.get()) ||
                        nearestPlayer.getOffhandItem().is(RileyModItems.TOOTH.get())|| nearestPlayer.getMainHandItem().is(RileyModItems.FANCY_SKULL.get()));

        // HEIGHT CHECK: Is the block directly below air?
        boolean nearGround = !this.level().getBlockState(this.blockPosition().below()).isAir();

        // Use isInSittingPose() which is more reliable for renderers
        if (this.isOrderedToSit() || this.isInSittingPose()) {
            stopAllExcept(sitAnimationState);
            sitAnimationState.startIfStopped(this.tickCount);
        } else if (!this.onGround() && !nearGround) { // ONLY play FLY if truly in the air
            stopAllExcept(flyAnimationState);
            flyAnimationState.startIfStopped(this.tickCount);
        } else if (playerHoldingBegItem && !this.walkAnimation.isMoving()) {
            stopAllExcept(begAnimationState);
            begAnimationState.startIfStopped(this.tickCount);
        } else if (this.walkAnimation.isMoving()) {
            stopAllExcept(walkAnimationState);
            walkAnimationState.startIfStopped(this.tickCount);
        } else {
            stopAllExcept(idleAnimationState);
            idleAnimationState.startIfStopped(this.tickCount);
        }
    }
    private void stopAllExcept(AnimationState activeState) {
        if (activeState != flyAnimationState) flyAnimationState.stop();
        if (activeState != walkAnimationState) walkAnimationState.stop();
        if (activeState != idleAnimationState) idleAnimationState.stop();
        if (activeState != begAnimationState) begAnimationState.stop();
        if (activeState != sitAnimationState) sitAnimationState.stop();
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(RileyModItems.TOOTH.get())));

        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(RileyModItems.TOOTH.get(),RileyModItems.FANCY_SKULL.get()), false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public void start() {
                super.start();
                ((SkeletonFairyEntity)this.mob).setAttacking(true);
            }
            @Override
            public void stop() {
                super.stop();
                ((SkeletonFairyEntity)this.mob).setAttacking(false);
            }
        });

        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.25D, 10.0F, 2.0F, true));

        // 1. Add a ground-based stroll (She will walk if already on the ground)
        // 4. Prefer walking if on ground, but allow flying wander
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D) // Increased from 0.2
                .add(Attributes.FLYING_SPEED, 0.2D)   // Increased from 0.3
                .add(Attributes.ARMOR_TOUGHNESS, .2f)
                .add(Attributes.ATTACK_KNOCKBACK, .4f)
                .add(Attributes.ATTACK_DAMAGE, 240f);

    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            // Night Terror Logic: Weightless unless sitting
            if (this.isOrderedToSit()) {
                this.setNoGravity(false); // Enable gravity so she falls

                // Landing Physics
                if (!this.onGround()) {
                    // Fall straight down
                    this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                } else {
                    // Stop completely on ground
                    this.setDeltaMovement(Vec3.ZERO);
                }
            } else {
                // Not sitting? Weightless flight!
                this.setNoGravity(true);

                if (this.getPose() == Pose.SITTING) {
                    this.setPose(Pose.STANDING);
                }
            }
            int inLove = this.getPersistentData().getInt("InLove");
            if (inLove > 0) {
                this.getPersistentData().putInt("InLove", inLove - 1);
            }

            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) {
                this.getPersistentData().putInt("BreedCooldown", cooldown - 1);
            }
        }

        if (this.isOrderedToSit()) {
            this.setNoGravity(false); // Enable gravity so she falls

            // If she's in the air, let her fall. If on the ground, stop moving.
            if (!this.onGround()) {
                // Keep the downward velocity (falling), but stop horizontal movement
                this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
            } else {
                // Fully stop once she hits the ground
                this.setDeltaMovement(Vec3.ZERO);
            }
        } else {
            if (this.getPose() == Pose.SITTING) {
                this.setPose(Pose.STANDING);
            }
        }

        // If on the ground and a player is nearby with a tooth, try to stay on the ground to beg
        if (!this.level().isClientSide && this.onGround() && this.isTame()) {
            Player player = this.level().getNearestPlayer(this, 10.0D);
            if (player != null && player.getMainHandItem().is(RileyModItems.TOOTH.get())) {
                this.setNoGravity(false); // Ensure she stays down to walk/beg
            }
        }
    }
    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isOrderedToSit()) {
            this.calculateEntityAnimation(false);
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.3, 0));
            this.move(MoverType.SELF, this.getDeltaMovement());
            return;
        }

        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5F));
            } else {
                // Hybrid Physics
                // Determine if we SHOULD fly based on distance to target
                double wantedX = this.moveControl.getWantedX();
                double wantedY = this.moveControl.getWantedY();
                double wantedZ = this.moveControl.getWantedZ();

                double distanceToTargetSqr = this.distanceToSqr(wantedX, wantedY, wantedZ);
                double horizontalDistanceSqr = this.distanceToSqr(wantedX, this.getY(), wantedZ);

                // FLYING LOGIC: Fly if target is far away (> 6 blocks) or high up (> 2 blocks)
                boolean shouldFly = distanceToTargetSqr > 100.0D || Math.abs(this.getY() - wantedY) > 2.0D;

                if (shouldFly || (!this.onGround() && !this.level().getBlockState(this.blockPosition().below()).isAir())) {
                    this.moveRelative(this.getSpeed(), pTravelVector);
                    this.move(MoverType.SELF, this.getDeltaMovement());

                    Vec3 delta = this.getDeltaMovement();

                    // 1-BLOCK LANDING ZONE
                    boolean nearGround = !this.level().getBlockState(this.blockPosition().below()).isAir();

                    if (nearGround && pTravelVector.y <= 0 && !shouldFly) {
                        // ULTRA-SNAP: Only acts when 1 block above ground and target is close
                        this.setDeltaMovement(delta.x * 0.91D, -0.5D, delta.z * 0.91D);
                    } else {
                        // PURE FLIGHT: Weightless glide
                        this.setDeltaMovement(delta.scale(0.91D));
                    }
                } else {
                    // Land mode: standard walking physics
                    super.travel(pTravelVector);
                }
            }
        }
        this.calculateEntityAnimation(!this.onGround());
    }
    @Override
    public boolean onClimbable() {
        return false;
    }
    @Override
    public boolean isFlying() {
        // FLIGHT THRESHOLD: Don't count as flying if there is a block within 1.2 blocks below
        // This forces the land animation and logic to take over near the floor
        boolean nearGround = !this.level().getBlockState(this.blockPosition().below()).isAir();

        return !this.onGround() && !nearGround && !this.isOrderedToSit();
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.TOOTHFAIRY.get().create(pLevel);
    }
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false; // Fairies don't take fall damage
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        // Leave empty to prevent fall damage logic from firing
    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
// Transform into Baby Bone Fairy when fed a Bone
        if (itemstack.is(RileyModItems.FANCY_SKULL.get()) && !this.isBaby()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide) {
                SkullFairyEntity babySkullFairy = this.convertTo(RileyModEntities.SKULL_FAIRY.get(), true);

                if (babySkullFairy != null) {
                    // EXPLICIT TAME TRANSFER: Ensure the new entity is tamed and owned by the same player
                    if (this.isTame()) {
                        babySkullFairy.setTame(true);
                        babySkullFairy.setOwnerUUID(this.getOwnerUUID());
                        babySkullFairy.setOrderedToSit(this.isOrderedToSit());
                    }

                    babySkullFairy.setAge(-24000); // Make it a baby
                    // Start transform animation + freeze for 2 seconds (40 ticks)
                    babySkullFairy.startTransform(80);

                    this.level().broadcastEntityEvent(babySkullFairy, (byte)7); // confirm with hearts
                }
            }else {
                // CLIENT SIDE DETECTION: Force the journal to forget the old species
                // so it doesn't linger as "Not in World"
                net.riley.riley_mod.entity.client.JournalScreen.forgetPet(this.getUUID());
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        // 1. If it's already tame and the player is the owner
        if (this.isTame() && this.isOwnedBy(pPlayer)) {
            if (this.isFood(itemstack) && this.getAge() == 0) {
                int breedCooldown = this.getPersistentData().getInt("BreedCooldown");
                int inLove = this.getPersistentData().getInt("InLove");

                if (breedCooldown <= 0 && inLove <= 0) {
                    if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);

                    // THIS IS THE KEY: Set the custom NBT tag your goal looks for
                    this.getPersistentData().putInt("InLove", 600);

                    // Optional: still call vanilla so hearts appear immediately
                    this.setInLove(pPlayer);
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
            // Otherwise, toggle sitting
            else if (!this.level().isClientSide) {
                boolean sitting = !this.isOrderedToSit();
                this.setOrderedToSit(sitting);
                this.jumping = false;
                this.navigation.stop();

                if (sitting) {
                    this.setNoGravity(false);
                    this.setOnGround(true);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        // 2. If it's not tame and the player is holding a Tooth
        else if (itemstack.is(RileyModItems.TOOTH.get()) && !this.isTame()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide) {
                // 33% chance to tame
                if (this.random.nextInt(3) == 0) {
                    this.tame(pPlayer);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte)7); // Heart particles
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6); // Smoke particles
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    // This ensures the Tooth is recognized as "breeding/taming" food
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(RileyModItems.TOOTH.get());
    }
}
