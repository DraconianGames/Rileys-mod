package net.riley.riley_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.LevelAccessor;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import net.riley.riley_mod.entity.ai.AbyssFollowParentGoal;
import net.riley.riley_mod.entity.ai.FrostHopperAttackGoal;
import net.riley.riley_mod.sound.RileyModSounds;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

public class FrostHopperEntity extends AgeableMob {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(FrostHopperEntity.class, EntityDataSerializers.BOOLEAN);

    public FrostHopperEntity(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
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
        this.goalSelector.addGoal(3, new AbyssBreedGoal(this, 1D, Ingredient.of(Items.COOKED_RABBIT)));
        this.goalSelector.addGoal(4,new AbyssFollowParentGoal(this,1D));
        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1D));
        this.goalSelector.addGoal(6,new LookAtPlayerGoal(this, Player.class,5f));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, FrostHopperEntity.class));
    }
    @Override
    public void aiStep() {
        super.aiStep();
        // Custom NBT Timer Logic (similar to Crab/Rapter)
        if (!this.level().isClientSide) {
            int inLove = this.getPersistentData().getInt("InLove");
            if (inLove > 0) this.getPersistentData().putInt("InLove", inLove - 1);
            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) this.getPersistentData().putInt("BreedCooldown", cooldown - 1);
        } else if (this.getPersistentData().getInt("InLove") > 0 && this.random.nextInt(7) == 0) {
            this.level().addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                    this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
        }
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
    public static boolean checkFrostHopperSpawnRules(EntityType<FrostHopperEntity> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        // Use the tag to check if the block below is valid for Abyss spawning
        if (!pLevel.getBlockState(pPos.below()).is(RileyModTags.Blocks.ABYSS_SPAWNABLE_ON)) {
            return false;
        }

        // Keep the darkness check so they only spawn in the spooky parts of the Abyss
        return pLevel.getRawBrightness(pPos, 0) <= 8;
    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
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
        return pStack.is(Items.COOKED_RABBIT);}
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

}

//TODO add battle cry to rest of mobs