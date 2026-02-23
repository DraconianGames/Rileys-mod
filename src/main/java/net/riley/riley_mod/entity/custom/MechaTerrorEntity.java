package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.ai.*;
import org.jetbrains.annotations.Nullable;

public class MechaTerrorEntity extends TamableAnimal {
    public MechaTerrorEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState activationAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    private static final EntityDataAccessor<Integer> ACTIVATION_TICKS =
            SynchedEntityData.defineId(MechaTerrorEntity.class, EntityDataSerializers.INT);
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTIVATION_TICKS, 0);
    }
    public boolean isActivating() {
        return this.entityData.get(ACTIVATION_TICKS) > 0;

    }
    public void startActivation() {
        if (this.level().isClientSide) return;
        this.entityData.set(ACTIVATION_TICKS, 50); // 4.0s @ 20 tps
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAminationStates();
        } else {
            // Freeze AI during activation
            if (this.isActivating()) {
                this.getNavigation().stop();
                this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
                this.setTarget(null);
            }
/*
            // Only run combat logic if not activating
            if (!this.isActivating()) {
                tickAttacksServer();
            }
*/
            //TODO add bullet thingies for this guy
            int act = this.entityData.get(ACTIVATION_TICKS);
            if (act > 0) {
                this.entityData.set(ACTIVATION_TICKS, act - 1);
            }
        }
    }
    private boolean isFriendlyTo(LivingEntity other) {
        if (other == null) return false;
        if (!this.isTame()) return false;

        // Don't hurt the owner
        if (this.isOwnedBy(other)) return true;

        // Don't hurt other tamed mobs with the same owner
        if (other instanceof TamableAnimal ta && ta.isTame()) {
            return this.getOwnerUUID() != null && this.getOwnerUUID().equals(ta.getOwnerUUID());
        }

        return false;
    }
    private void setupAminationStates() {
        if(this.idleAminationTimeout <- 0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
        }
        if (this.isActivating()) {
            activationAnimationState.startIfStopped(this.tickCount);
        } else {
            activationAnimationState.stop();
        }
    }
    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose()== Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        }else{
            f=0f;
        }
        this.walkAnimation.update(f,.2f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1000D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ARMOR_TOUGHNESS, .7f)
                .add(Attributes.ATTACK_KNOCKBACK, 10f)
                .add(Attributes.ATTACK_DAMAGE, 20f);

    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));



        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new DisableFriendlyTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

        // If tamed: attack whatever your owner attacks / whoever attacks your owner
        this.targetSelector.addGoal(4, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new OwnerHurtTargetGoal(this));

        // If NOT tamed: attack players on sight
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(
                this,
                Player.class,
                true,
                player -> !this.isTame()
        ));

    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
}
