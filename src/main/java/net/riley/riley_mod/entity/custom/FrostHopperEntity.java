package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.FrostHopperAttackGoal;
import net.riley.riley_mod.sound.RileyModSounds;
import org.jetbrains.annotations.Nullable;

public class FrostHopperEntity extends Animal {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(FrostHopperEntity.class, EntityDataSerializers.BOOLEAN);

    public FrostHopperEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAminationTimeout = 0;
    public void playBattleCry() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                RileyModSounds.FROST_HOPPER_BATTLE_CRY.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public void setTarget(@Nullable LivingEntity pTarget) {
        // Check if we are acquiring a NEW target (not just clearing the target)
        if (pTarget != null && this.getTarget() == null) {
            this.playBattleCry();
        }
        super.setTarget(pTarget);
    }
    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAminationStates();
        }
    }
    private void setupAminationStates() {
        if(this.idleAminationTimeout <- 0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
        }

        if (this.isAttacking() && attackAminationTimeout<=0) {
            attackAminationTimeout = 15; //leangth in ticks of the animation
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
        if(this.getPose()== Pose.STANDING) {
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
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(2, new FrostHopperAttackGoal(this,1D,true));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1D, Ingredient.of(Items.COOKED_RABBIT), false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1D));
        this.goalSelector.addGoal(4,new FollowParentGoal(this,1D));
        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1D));
        this.goalSelector.addGoal(6,new LookAtPlayerGoal(this, Player.class,5f));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, FrostHopperEntity.class));
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 15D)
                .add(Attributes.FOLLOW_RANGE,30D)
                .add(Attributes.MOVEMENT_SPEED, .3D)
                .add(Attributes.ARMOR_TOUGHNESS, .0f)
                .add(Attributes.ATTACK_KNOCKBACK,.3f)
                .add(Attributes.ATTACK_DAMAGE,2f);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.FROST_HOPPER.get().create(pLevel);
    }
    @Override
    public boolean doHurtTarget(Entity pTarget) {
        boolean hurt = super.doHurtTarget(pTarget);
        if (hurt && pTarget instanceof LivingEntity livingTarget) {
            // Apply Freeze effect for 60 ticks (3 seconds)
            livingTarget.addEffect(new MobEffectInstance(RileyModEffects.FREEZE.get(), 60, 0), this);
        }
        return hurt;
    }
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.COOKED_RABBIT);
    }

}

//TODO add battle cry to rest of mobs