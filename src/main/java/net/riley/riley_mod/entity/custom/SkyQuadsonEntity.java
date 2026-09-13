package net.riley.riley_mod.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SkyQuadsonEntity extends AbstractInventoryMountEntity{
    private static final int STORAGE_COLUMNS = 5;
    private static final int STORAGE_ROWS = 3;
    private static final double RIDER_FORWARD_OFFSET = 0.125D;
    private static final double FLIGHT_SPEED = 0.15D;
    private static final double VERTICAL_SPEED = 0.1D;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();

    private boolean isFlying = false;
    private int lastTabPressTime = 0;
    private static final int TAB_COOLDOWN = 10; // ticks between tab presses

    // Flight input state
    private boolean currentJumpInput = false;
    private boolean currentSneakInput = false;

    private void setupAnimationStates() {
        if (isFlying) {
            stopAllExcept(flyAnimationState);
            flyAnimationState.startIfStopped(this.tickCount);
        } else if (this.walkAnimation.isMoving()) {
            stopAllExcept(walkAnimationState);
            walkAnimationState.startIfStopped(this.tickCount);
        } else {
            stopAllExcept(idleAnimationState);
            idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    private void stopAllExcept(AnimationState activeState) {
        if (activeState != walkAnimationState) walkAnimationState.stop();
        if (activeState != idleAnimationState) idleAnimationState.stop();
        if (activeState != flyAnimationState) flyAnimationState.stop();
    }

    public SkyQuadsonEntity(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void randomizeAttributes(RandomSource random) {}

    @Override
    protected int getBaseStorageColumns() {
        return STORAGE_COLUMNS;
    }

    @Override
    protected int getBaseStorageRows() {
        return STORAGE_ROWS;
    }

    @Override
    public boolean canEquipMountArmor(ItemStack stack) {
        return false;
    }

    @Override
    protected @Nullable Vec3 getCustomPassengerOffset(Entity passenger, int passengerIndex) {
        if (passengerIndex == 0) {
            return new Vec3(0.0D, this.getPassengersRidingOffset(), RIDER_FORWARD_OFFSET);
        }
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ARMOR_TOUGHNESS, .5f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.JUMP_STRENGTH, 0.7D);
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
        }
    }
//todo make up and down arrow keys be up and down for flight. also make movement work in flight mode
    @Override
    public void travel(Vec3 travelVector) {
        if (isFlying) {
            // Handle flight mode movement
            Vec3 currentMotion = this.getDeltaMovement();

            // Dampen horizontal movement to hover
            double newX = currentMotion.x * 0.8D;
            double newZ = currentMotion.z * 0.8D;

            // Handle vertical flight input from rider
            double verticalInput = 0;

            // Space key for up
            if (currentJumpInput) {
                verticalInput = VERTICAL_SPEED;
            }

            // Ctrl key (sneak) for down
            if (currentSneakInput) {
                verticalInput = -VERTICAL_SPEED;
            }

            this.setDeltaMovement(newX, verticalInput, newZ);
            this.move(MoverType.SELF, this.getDeltaMovement());
        } else {
            // Normal movement/walking
            super.travel(travelVector);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(Items.APPLE)));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1D, Ingredient.of(Items.CARROT), false));
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Chest-on-like-a-mule behavior (explicit, reliable)
        if (itemstack.is(Items.CHEST) && this.isTamed() && !this.isBaby() && !this.hasChest()) {
            if (!this.level().isClientSide) {
                this.setChest(true);
                this.createInventory(); // expands inventory for chested horses
                if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
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

    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.APPLE);
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.7D;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.BISON.get().create(pLevel);
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        this.isFlying = flying;
    }

    public void toggleFlight() {
        if (this.tickCount - lastTabPressTime > TAB_COOLDOWN) {
            this.isFlying = !this.isFlying;
            this.lastTabPressTime = this.tickCount;
        }
    }

    public void setFlightInput(boolean jumping, boolean sneaking) {
        this.currentJumpInput = jumping;
        this.currentSneakInput = sneaking;
    }
}