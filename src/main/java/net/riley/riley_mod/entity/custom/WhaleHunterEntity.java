package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.util.RandomSource;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.ai.HuntVanillaSeaMobs;
import net.riley.riley_mod.entity.ai.WhaleHunterAttackGoal;
import net.riley.riley_mod.entity.ai.WhaleHunterWanderGoal;
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

        this.targetSelector.addGoal(2, new HuntVanillaSeaMobs<>(this, LivingEntity.class, true));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

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
        if (!this.level().isClientSide && this.isEffectiveAi() && this.tickCount % 5 == 0) {
            java.util.List<Boat> boats = this.level().getEntitiesOfClass(Boat.class, this.getBoundingBox().inflate(16.0D));

            Boat closestBoat = null;
            double closestDistance = Double.MAX_VALUE;

            if (!boats.isEmpty()) {
                // DISTRACTION FIX: If there are boats, ignore current living targets (fish/mobs)
                // This forces the whale to focus on the wood until it's all gone.
                if (this.getTarget() != null) {
                    this.setTarget(null);
                }

                for (Boat boat : boats) {
                    if (boat.isInWater()) {
                        double dist = this.distanceToSqr(boat);

                        // 1. Attack logic: Smash ANY boat we are currently touching
                        if (this.getBoundingBox().inflate(1.5D).intersects(boat.getBoundingBox())) {
                            this.doHurtTarget(boat);
                        }

                        // 2. Navigation logic: Find the closest boat to head towards
                        if (dist < closestDistance) {
                            closestDistance = dist;
                            closestBoat = boat;
                        }
                    }
                }
            }

            // Move towards the single closest boat
            if (closestBoat != null) {
                this.moveControl.setWantedPosition(closestBoat.getX(), closestBoat.getY(), closestBoat.getZ(), 1.4D);
                this.getLookControl().setLookAt(closestBoat, 30.0F, 30.0F);
            }
        }

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

            Vec3 delta = this.getDeltaMovement();

            // If the AI is NOT trying to move vertically (y is near 0), apply sinking
            // This prevents the "sink" from fighting the "swim up"
            if (Math.abs(pTravelVector.y) < 0.005D && delta.y <= 0) {
                this.setDeltaMovement(delta.x * 0.8D, delta.y - 0.02D, delta.z * 0.8D);
            } else {
                this.setDeltaMovement(delta.scale(0.8D)); // General friction
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
        this.setAttacking(true);
        this.attackAminationTimeout = 15;

        // Special handling for Boats
        if (pEntity instanceof Boat boat) {
            // Smash the boat (100 damage is plenty)
            boolean destroyed = boat.hurt(this.damageSources().mobAttack(this), 100.0F);
            if (destroyed) {
                this.playSound(SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, 1.0F, 0.5F);
            }
            return destroyed;
        }


        boolean flag = super.doHurtTarget(pEntity);

        if (flag && pEntity instanceof LivingEntity living) {
            // Check if the attack was lethal
            if (living.isDeadOrDying() || living.getHealth() <= 0.0F) {
                // Play a munching sound to indicate consumption
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

                // On the server, discard the entity immediately so it doesn't trigger death drops
                if (!this.level().isClientSide) {
                    living.discard();
                }
            }
        }
        return flag;
    }

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