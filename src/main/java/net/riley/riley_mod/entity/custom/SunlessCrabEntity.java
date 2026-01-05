package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.HuntVanillaLandMobs;
import net.riley.riley_mod.entity.ai.SunlessCrabAttackGoal;
import org.jetbrains.annotations.Nullable;

public class SunlessCrabEntity extends Animal {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SunlessCrabEntity.class, EntityDataSerializers.BOOLEAN);


    public SunlessCrabEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAminationTimeout = 0;


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
setupAminationStates();
        }
    }

    private void setupAminationStates() {
        if(this.idleAminationTimeout <- 0) {
            this.idleAminationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
        --this.idleAminationTimeout;
        }

        if (this.isAttacking() && attackAminationTimeout<=0) {
            attackAminationTimeout = 80; //leangth in ticks of the animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAminationTimeout;
        }
        if (!this.isAttacking()) {
            attackAnimationState.stop();
        }

    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose()==Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        }else{
            f=0f;
        }
        this.walkAnimation.update(f,.2f);
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

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SunlessCrabAttackGoal(this, 1.6D, true));

        this.goalSelector.addGoal(2,new BreedGoal(this,2D));
        this.goalSelector.addGoal(3,new TemptGoal(this,2D, Ingredient.of(Items.AMETHYST_SHARD),false));
        this.goalSelector.addGoal(4,new FollowParentGoal(this,2D));
        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1D));
        this.goalSelector.addGoal(6,new LookAtPlayerGoal(this, Player.class,5f));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, SunlessCrabEntity.class));
        this.targetSelector.addGoal(3, new HuntVanillaLandMobs<>(this, LivingEntity.class, true));


    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.FOLLOW_RANGE,30D)
                .add(Attributes.MOVEMENT_SPEED, .2D)
                .add(Attributes.ARMOR_TOUGHNESS, .3f)
                .add(Attributes.ATTACK_KNOCKBACK,3f)
                .add(Attributes.ATTACK_DAMAGE,100f);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.SUNLESS_CRAB.get().create(pLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.AMETHYST_SHARD);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.STRIDER_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PARROT_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }
}
