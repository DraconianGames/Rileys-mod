package net.riley.riley_mod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.*;
import org.jetbrains.annotations.Nullable;

public class MechaRexEntity extends TamableAnimal {
    public MechaRexEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState tailSwipeAnimationState = new AnimationState();
    public final AnimationState launchBombAnimationState = new AnimationState();

    public final AnimationState activationAnimationState = new AnimationState();

    private int idleAminationTimeout = 0;
    LivingEntity shooter = this; // inside your Mecha Rex class
    // Attack animations (client-side visuals)
    private static final EntityDataAccessor<Integer> TAIL_SWIPE_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOMB_LAUNCH_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);


    private boolean tailSwipeDamageDone = false;
    private boolean bombSpawned = false;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TAIL_SWIPE_TICKS, 0);
        this.entityData.define(BOMB_LAUNCH_TICKS, 0);
        this.entityData.define(ACTIVATION_TICKS, 0);
    }
    public boolean isTailSwiping() {
        return this.entityData.get(TAIL_SWIPE_TICKS) > 0;
    }

    public boolean isLaunchingBomb() {
        return this.entityData.get(BOMB_LAUNCH_TICKS) > 0;
    }

    public void startTailSwipe() {
        if (this.level().isClientSide) return;
        this.entityData.set(TAIL_SWIPE_TICKS, 30); // 1.5s
        this.tailSwipeDamageDone = false;
    }

    public void startLaunchBomb() {
        if (this.level().isClientSide) return;
        this.entityData.set(BOMB_LAUNCH_TICKS, 20); // 1.0s
        this.bombSpawned = false;
    }
    public boolean isActivating() {
        return this.entityData.get(ACTIVATION_TICKS) > 0;

    }
    public void startActivation() {
        if (this.level().isClientSide) return;
        this.entityData.set(ACTIVATION_TICKS, 80); // 4.0s @ 20 tps
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

            // Only run combat logic if not activating
            if (!this.isActivating()) {
                tickAttacksServer();
            }

            int act = this.entityData.get(ACTIVATION_TICKS);
            if (act > 0) {
                this.entityData.set(ACTIVATION_TICKS, act - 1);
            }
        }
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = super.mobInteract(player, hand);
        if (result.consumesAction()) return result;

        if (this.level().isClientSide) {
            // let server decide
            return InteractionResult.SUCCESS;
        }

        // Only owner can command sit
        if (this.isTame() && this.isOwnedBy(player)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.getNavigation().stop();
            this.setTarget(null);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
    private void tickAttacksServer() {
        int tail = this.entityData.get(TAIL_SWIPE_TICKS);
        if (tail > 0) {
            int elapsed = 30 - tail;

            // halfway point: 15 ticks after start
            if (!tailSwipeDamageDone && elapsed >= 15) {
                tailSwipeDamageDone = true;
                doTailSwipeDamage();
            }

            this.entityData.set(TAIL_SWIPE_TICKS, tail - 1);
        }

        int bomb = this.entityData.get(BOMB_LAUNCH_TICKS);
        if (bomb > 0) {
            int elapsed = 20 - bomb;

            // 0.25 seconds after start = 5 ticks
            if (!bombSpawned && elapsed >= 5) {
                bombSpawned = true;
                LivingEntity target = this.getTarget();
                if (target != null && target.isAlive()) {
                    shootBombAt(target);
                }
            }

            this.entityData.set(BOMB_LAUNCH_TICKS, bomb - 1);
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
    private void doTailSwipeDamage() {
        double radius = 6.0;
        AABB box = this.getBoundingBox().inflate(radius, 2.0, radius);

        for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, box, en -> en.isAlive() && en != this)) {
            if (this.distanceTo(e) <= radius) {
                if (isFriendlyTo(e)) continue;

                e.hurt(this.damageSources().mobAttack(this), 60.0F);
            }
        }
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
        if (this.isOrderedToSit() || this.isInSittingPose()) {
            tailSwipeAnimationState.stop();
            launchBombAnimationState.stop();
            idleAnimationState.startIfStopped(this.tickCount);
            return;
        }//TODO make sit animation
        // When server timers are active, run the matching animation on client
        if (this.isTailSwiping()) {
            tailSwipeAnimationState.startIfStopped(this.tickCount);
        } else {
            tailSwipeAnimationState.stop();
        }

        if (this.isLaunchingBomb()) {
            launchBombAnimationState.startIfStopped(this.tickCount);
        } else {
            launchBombAnimationState.stop();
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
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2000D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ARMOR_TOUGHNESS, .7f)
                .add(Attributes.ATTACK_KNOCKBACK, 10f)
                .add(Attributes.ATTACK_DAMAGE, 20f);

    }
   @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));

        this.goalSelector.addGoal(2, new MechaRexTailSwipeGoal(this));
        this.goalSelector.addGoal(3, new MechaRexBombLaunchGoal(this, 11.0, 20.0));
        this.goalSelector.addGoal(4, new MechaRexKeepRangeGoal(this, 1.2D, 11.0, 16.0));

        this.goalSelector.addGoal(6, new MechaRexFollowOwnerWhenNoTargetGoal(this, 1.0D, 10.0F, 2.0F, false));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new DisableFriendlyTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MechaRexShareTargetWithAlliesGoal(this, 32.0));

        this.targetSelector.addGoal(3, new MechaRexWildTargetPlayerGoal(this));

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
    private void shootBombAt(LivingEntity target) {
        LivingEntity shooter = this;

        double dx = target.getX() - shooter.getX();
        double dy = target.getEyeY() - shooter.getEyeY();
        double dz = target.getZ() - shooter.getZ();

        double speed = 0.1;
        double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (len < 1.0E-4) return;

        double ax = (dx / len) * speed;
        double ay = (dy / len) * speed;
        double az = (dz / len) * speed;

        MechaRexBombEntity bomb = MechaRexBombEntity.create(
                RileyModEntities.MECHAREXBOMB.get(),
                this.level(),
                shooter,
                ax, ay, az
        );

        // Homing only for non-player targets
        if (!(target instanceof Player)) {
            bomb.setHomingTarget(target);
        }

        bomb.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.level().addFreshEntity(bomb);
    }
}
