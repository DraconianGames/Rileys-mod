package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.riley.riley_mod.entity.ai.*;
import net.riley.riley_mod.item.RileyModItems;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.Mth;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;



public class NightTerrorEntity extends TamableAnimal{
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(NightTerrorEntity.class, EntityDataSerializers.BOOLEAN);

    public NightTerrorEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1,new BreedGoal(this,1D));
        this.goalSelector.addGoal(2, new NightTerrorTemptGoal(this, 1D, Ingredient.of(RileyModItems.CLAW.get()),false, 80.0D));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.2D, 10.0F, 2.0F, true));
        this.goalSelector.addGoal(1, new NightTerrorAvoidPlayerGoal(this, 1.4D));
        this.goalSelector.addGoal(3, new NightTerrorAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new NightTerrorHighWanderGoal(this, 1.0D, 90D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                (entity) -> !this.isTame()));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, SunlessCrabEntity.class, 10, true, false,
                (entity) -> !this.isTame()));

        this.targetSelector.addGoal(4, new HuntVanillaLandMobs<>(this, LivingEntity.class, true));
        this.targetSelector.addGoal(3, new HuntVanillaAirMobs<>(this, LivingEntity.class, true));
    }//change to not tamed
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Logic for taming (e.g., using raw beef)
        if (itemstack.is(RileyModItems.CLAW.get()) && !this.isTame()) {
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            } else {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (this.random.nextInt(3) == 0) { // 33% chance to tame
                    this.tame(pPlayer);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.level().broadcastEntityEvent(this, (byte)7); // Hearts
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6); // Smoke/failed
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(false);
        return navigation;
    }



    public final AnimationState flyAttackAnimationState = new AnimationState();
    public int attackAminationTimeout = 0;
    public final AnimationState flyAnimationState = new AnimationState();
    private int airTicks = 0;
    private int outOfCombatTicks = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAminationStates();
            float targetPitch = (float)(Mth.atan2(-this.getDeltaMovement().y, this.getDeltaMovement().horizontalDistance()) * (180F / (float)Math.PI));
            this.setXRot(Mth.rotLerp(0.1F, this.getXRot(), targetPitch));
        } else {
            // Server-side logic
            handleCombatTimer();
            handleAutomaticHealing();

            // Track air time
            if (this.onGround()) {
                this.airTicks = 0;
            } else {
                this.airTicks++;
            }
        }
    }
    private void handleCombatTimer() {
        // If it has a target or was recently hit, reset the timer
        if (this.getTarget() != null || this.getLastHurtByMob() != null) {
            this.outOfCombatTicks = 0;
        } else {
            this.outOfCombatTicks++;
        }
    }

    private void handleAutomaticHealing() {
        // If tamed, out of combat for 2 mins (2400 ticks), and injured
        if (this.isTame() && this.outOfCombatTicks >= 2400 && this.getHealth() < this.getMaxHealth()) {
            // Apply Regeneration II (amplifier 1) for 5 seconds (100 ticks)
            // We re-apply it every few seconds while the conditions are met
            if (this.tickCount % 40 == 0) {
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
            }
        }
    }
    private void setupAminationStates() {


        // Always ensure the flying animation is running
        this.flyAnimationState.startIfStopped(this.tickCount);

        if (this.isAttacking() && attackAminationTimeout <= 0) {
            attackAminationTimeout = 15;
            // Always play the flying attack animation
            flyAttackAnimationState.start(this.tickCount);
        } else {
            --this.attackAminationTimeout;
        }

        if (!this.isAttacking()) {
            flyAttackAnimationState.stop();
        }
    }
    @Override
    protected void updateWalkAnimation(float pPartialTick) {

        this.walkAnimation.update(0f, 0.2f);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 300D)
                .add(Attributes.FOLLOW_RANGE,100D)
                .add(Attributes.FLYING_SPEED, .3D)
                .add(Attributes.ARMOR_TOUGHNESS, .3f)
                .add(Attributes.ATTACK_KNOCKBACK,3f)
                .add(Attributes.ATTACK_DAMAGE,60f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }
    @Override
    public boolean onClimbable() {
        return false;
    }
    @Override
    public void travel(Vec3 pTravelVector) {
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
                // Flying Physics: This removes the default gravity application
                this.moveRelative(this.getSpeed(), pTravelVector);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.91F)); // Air friction
            }
        }
        this.calculateEntityAnimation(false);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, net.minecraft.world.damagesource.DamageSource pSource) {
        return false;
    }
    @Override
    public boolean fireImmune() {
        return true;
    }
    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, net.minecraft.world.level.block.state.BlockState pState, net.minecraft.core.BlockPos pPos) {
    }
    public static boolean checkNightTerrorSpawnRules(EntityType<NightTerrorEntity> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        // Only spawn if it's at least Y=100 and the block is air
        return pPos.getY() >= 100 && pLevel.isEmptyBlock(pPos);
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        NightTerrorEntity offspring = RileyModEntities.NIGHT_TERROR.get().create(pLevel);

        if (offspring != null && pOtherParent instanceof NightTerrorEntity otherParent) {
            // If the first parent is tamed, make the baby tamed by the same owner
            if (this.isTame()) {
                offspring.setOwnerUUID(this.getOwnerUUID());
                offspring.setTame(true);
            }
            // Alternatively, if the second parent is tamed, use their owner
            else if (otherParent.isTame()) {
                offspring.setOwnerUUID(otherParent.getOwnerUUID());
                offspring.setTame(true);
            }
        }

        return offspring;
    }
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(RileyModItems.CLAW.get());
    }
}
//TODO add stinger attack. Poison effect.