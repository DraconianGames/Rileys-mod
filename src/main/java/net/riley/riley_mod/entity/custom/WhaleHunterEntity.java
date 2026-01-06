package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.util.RandomSource;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.HuntVanillaSeaMobs;
import net.riley.riley_mod.entity.ai.WhaleHunterAttackGoal;
import net.riley.riley_mod.entity.ai.WhaleHunterWanderGoal;
import net.riley.riley_mod.item.RileyModItems;
import org.jetbrains.annotations.Nullable;

public class WhaleHunterEntity extends WaterAnimal {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(WhaleHunterEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAminationTimeout = 0;

    public final AnimationState swimAnimationState = new AnimationState();



    public WhaleHunterEntity(EntityType<? extends WaterAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        // We boost the speed here to 1.2 and set the 'true' flag for 3D rotation
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.05F, 0.1F, true);
        this.lookControl = new net.minecraft.world.entity.ai.control.LookControl(this);
    }
    @Override
    protected void registerGoals() {
        // Priority 0: Always stay in water

        this.goalSelector.addGoal(0, new WhaleHunterAttackGoal(this, 1.4D, true));
        this.goalSelector.addGoal(4, new WhaleHunterWanderGoal(this, 1.0D, 10));

        this.targetSelector.addGoal(1, new HuntVanillaSeaMobs<>(this, LivingEntity.class, true));

        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }
    public static boolean checkWhaleHunterSpawnRules(EntityType<WhaleHunterEntity> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() < 50 && pLevel.getFluidState(pPos).is(net.minecraft.tags.FluidTags.WATER);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }
    @Override
    public void tick() {
        super.tick();
        if (this.isEffectiveAi() && this.isInWater()) {
            this.setNoGravity(true); // Stop standard gravity from pulling it down/making it bob
        } else {
            this.setNoGravity(false);
        }
        if(this.level().isClientSide()) {
            setupAminationStates();
        }
        if (this.level().isClientSide) {
            float targetPitch = (float)(Mth.atan2(-this.getDeltaMovement().y, this.getDeltaMovement().horizontalDistance()) * (180F / (float)Math.PI));
            this.setXRot(Mth.rotLerp(0.1F, this.getXRot(), targetPitch));

            float rotationDelta = Mth.wrapDegrees(this.yBodyRot - this.yBodyRotO);
        }
    }
    @Override
    public double getFluidJumpThreshold() {
        return 0.0D;
    }
    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());

            // Reduce horizontal and vertical momentum more strictly
            Vec3 delta = this.getDeltaMovement();
            this.setDeltaMovement(delta.x * 0.8D, delta.y * 0.5D, delta.z * 0.8D);

            // If not actively moving up, apply a constant sinking weight
            if (this.getDeltaMovement().y <= 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.02D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }
    private void setupAminationStates() {
        if (this.idleAminationTimeout <= 0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
        }

        // Play swimming animation if moving, otherwise stop it
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D) {
            this.swimAnimationState.startIfStopped(this.tickCount);
        } else {
            this.swimAnimationState.stop();
        }

        if (this.isAttacking() && attackAminationTimeout <= 0) {
            attackAminationTimeout = 15;
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAminationTimeout;
        }

        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }
    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }



    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        super.setTarget(pTarget);
        if (pTarget != null) {
            this.getLookControl().setLookAt(pTarget);
        }
    }
    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        WaterBoundPathNavigation waternavigation = new WaterBoundPathNavigation(this, pLevel);
        // This is key: it tells the AI that it's allowed to calculate paths in the middle of water blocks
        waternavigation.setCanFloat(true);
        return waternavigation;
    }



    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = super.doHurtTarget(pEntity);
        if (flag && pEntity instanceof LivingEntity living) {
            // If the hit kills the prey, we discard it immediately to skip drops
            if (living.getHealth() <= 0 || living.isDeadOrDying()) {
                living.discard(); // The prey vanishes, dropping nothing
                // Play a munching sound
                this.playSound(net.minecraft.sounds.SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            }
        }
        return flag;
    }//don't work

    @Override
    public void aiStep() {
        // Bypass WaterAnimal buoyancy by calling LivingEntity's aiStep
        // This is a common trick to stop sea monsters from bobbing
        if (this.isInWater()) {
            this.setAirSupply(300); // Manually prevent drowning if not using WaterAnimal logic
        }
        super.aiStep();
    }
    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }
    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300D)
                .add(Attributes.FOLLOW_RANGE, 64D) // Increased range so they find targets easier
                .add(Attributes.MOVEMENT_SPEED, 1.2D)
                .add(Attributes.ATTACK_DAMAGE, 60f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f);
    }

}