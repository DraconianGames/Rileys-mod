package net.riley.riley_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.LevelAccessor;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import net.riley.riley_mod.entity.ai.AbyssFollowParentGoal;
import net.riley.riley_mod.entity.ai.HuntVanillaLandMobs;
import net.riley.riley_mod.entity.ai.SunlessCrabAttackGoal;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

public class SunlessCrabEntity extends AgeableMob {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SunlessCrabEntity.class, EntityDataSerializers.BOOLEAN);

    private int inLove; // Custom timer for breeding mode

    public SunlessCrabEntity(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAminationTimeout = 0;

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide) {
            // Show hearts while in love
            if (this.getPersistentData().getInt("InLove") > 0 && this.random.nextInt(7) == 0) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                        this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            }
        } else {
            // Server side timers
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


        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(Items.AMETHYST_SHARD)));

        this.goalSelector.addGoal(2,new TemptGoal(this,2D, Ingredient.of(Items.AMETHYST_SHARD),false));

        this.goalSelector.addGoal(3, new SunlessCrabAttackGoal(this, 1.6D, true));

        this.goalSelector.addGoal(4, new AbyssFollowParentGoal(this, 1.25D));

        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1D));
        this.goalSelector.addGoal(6,new LookAtPlayerGoal(this, Player.class,5f));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, SunlessCrabEntity.class));
        this.targetSelector.addGoal(3, new HuntVanillaLandMobs<>(this, LivingEntity.class, true));


    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (this.isFood(itemstack)) {
            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            int inLove = this.getPersistentData().getInt("InLove");

            // If on cooldown, play a "refusal" animation/sound or just return fail
            if (cooldown > 0) {
                if (this.level().isClientSide) {
                    // Spawn smoke particles to show they don't want it
                    for(int i = 0; i < 5; ++i) {
                        this.level().addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE,
                                this.getRandomX(0.5D), this.getRandomY() + 0.5D, this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
                    }
                }
                return InteractionResult.PASS;
            }

            if (inLove <= 0) {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.getPersistentData().putInt("InLove", 600);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.AMETHYST_SHARD);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.FOLLOW_RANGE,30D)
                .add(Attributes.MOVEMENT_SPEED, .2D)
                .add(Attributes.ARMOR_TOUGHNESS, .3f)
                .add(Attributes.ATTACK_KNOCKBACK,3f)
                .add(Attributes.ATTACK_DAMAGE,20f);
    }
    public static boolean checkSunlessCrabSpawnRules(EntityType<SunlessCrabEntity> pType, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        // Check for any solid block below and air where the crab will be
        return pLevel.getBlockState(pPos.below()).isSolid() && pLevel.getBlockState(pPos).isAir();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.SUNLESS_CRAB.get().create(pLevel);
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
