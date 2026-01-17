package net.riley.riley_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import net.riley.riley_mod.item.RileyModItems;
import org.jetbrains.annotations.Nullable;

public class BoneFairyEntity extends TamableAnimal implements FlyingAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState begAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();

    public BoneFairyEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
        // Use FlyingPathNavigation so it doesn't get stuck on fences/blocks
        this.navigation = new FlyingPathNavigation(this, level);
        // Standard move control for flying animals
        this.moveControl = new FlyingMoveControl(this, 10, false);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }
    private void setupAnimationStates() {
        Player nearestPlayer = this.level().getNearestPlayer(this, 5.0D);
        boolean playerHoldingTooth = nearestPlayer != null &&
                (nearestPlayer.getMainHandItem().is(RileyModItems.TOOTH.get()) ||
                        nearestPlayer.getOffhandItem().is(RileyModItems.TOOTH.get()));

        // Use isInSittingPose() which is more reliable for renderers
        if (this.isOrderedToSit() || this.isInSittingPose()) {
            stopAllExcept(sitAnimationState);
            sitAnimationState.startIfStopped(this.tickCount);
        } else if (this.isFlying()) {
            // FLY is #2.
            stopAllExcept(flyAnimationState);
            flyAnimationState.startIfStopped(this.tickCount);
        } else if (playerHoldingTooth && this.onGround() && !this.walkAnimation.isMoving()) {
            // BEG is #3.
            stopAllExcept(begAnimationState);
            begAnimationState.startIfStopped(this.tickCount);
        } else if (this.walkAnimation.isMoving()) {
            // WALK is #4.
            stopAllExcept(walkAnimationState);
            walkAnimationState.startIfStopped(this.tickCount);
        } else {
            // IDLE is #5.
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
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
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
            // Tell the animation system she isn't "walking"
            this.calculateEntityAnimation(false);
            // Passing ZERO to super.travel allows gravity to handle the descent
            super.travel(Vec3.ZERO);
            return;
        }

        if (this.isFlying()) {
            // Remove the 'this' parameter here:
            this.calculateEntityAnimation(true);
        }
        super.travel(pTravelVector);
    }
    @Override
    public boolean isFlying() {
        // True if in air and not sitting.
        // Using this.onGround() is the most reliable way to toggle fly/walk animations.
        return !this.onGround() && !this.isOrderedToSit();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D) // Increased from 0.2
                .add(Attributes.FLYING_SPEED, 0.4D)   // Increased from 0.3
                .add(Attributes.ARMOR_TOUGHNESS, 0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.ATTACK_DAMAGE, 1f);

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
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(RileyModItems.TOOTH.get())));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(RileyModItems.TOOTH.get()), false));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.25D, 5.0F, 2.0F, true));

        // 1. Add a ground-based stroll (She will walk if already on the ground)
        // 4. Prefer walking if on ground, but allow flying wander
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));

        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.TOOTHFAIRY.get().create(pLevel);
    }

}
