package net.riley.riley_mod.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
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
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.*;
import org.jetbrains.annotations.Nullable;

public class MechaTerrorEntity extends TamableAnimal {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.literal("Mecha Terror"),
            BossEvent.BossBarColor.BLUE,
            BossEvent.BossBarOverlay.PROGRESS
    );

    public MechaTerrorEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState activationAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();

    private static final int SHOOT_WINDUP_TICKS = 20;
    private static final int SHOOT_BLAST_TICKS = 40;
    private static final int SHOOT_COOLDOWN_TICKS = 20;
    private static final int SHOOT_TOTAL_TICKS = SHOOT_WINDUP_TICKS + SHOOT_BLAST_TICKS + SHOOT_COOLDOWN_TICKS;
    private static final int BULLET_FIRE_INTERVAL = 2;

    private int idleAminationTimeout = 0;
    private static final EntityDataAccessor<Integer> ACTIVATION_TICKS =
            SynchedEntityData.defineId(MechaTerrorEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHOOT_TICKS =
            SynchedEntityData.defineId(MechaTerrorEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACTIVATION_TICKS, 0);
        this.entityData.define(SHOOT_TICKS, 0);
    }

    public boolean isActivating() {
        return this.entityData.get(ACTIVATION_TICKS) > 0;

    }

    public boolean isShooting() {
        return this.entityData.get(SHOOT_TICKS) > 0;
    }
    public void startActivation() {
        if (this.level().isClientSide) return;
        this.entityData.set(ACTIVATION_TICKS, 50); // 2.5s @ 20 tps
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAminationStates();
        } else {
            updateBossBar();

            // Freeze AI during activation
            if (this.isActivating()) {
                this.getNavigation().stop();
                this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
                this.setTarget(null);
            }

            if (!this.isActivating()) {
                tickGunAttack();
            }

            int shootTicks = this.entityData.get(SHOOT_TICKS);
            if (shootTicks > 0) {
                this.entityData.set(SHOOT_TICKS, shootTicks - 1);
            }

            int act = this.entityData.get(ACTIVATION_TICKS);
            if (act > 0) {
                this.entityData.set(ACTIVATION_TICKS, act - 1);
            }
        }
    }

    private void updateBossBar() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        if (this.isTame() || !this.isAlive()) {
            this.bossEvent.removeAllPlayers();
            return;
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        for (ServerPlayer player : serverLevel.players()) {
            double distanceSqr = player.distanceToSqr(this);

            if (distanceSqr <= 64.0D * 64.0D) {
                this.bossEvent.addPlayer(player);
            } else {
                this.bossEvent.removePlayer(player);
            }
        }
    }
    @Override
    public void remove(RemovalReason pReason) {
        this.bossEvent.removeAllPlayers();
        super.remove(pReason);
    }

    private void tickGunAttack() {
        LivingEntity target = this.getTarget();

        if (target == null || !target.isAlive()) {
            this.entityData.set(SHOOT_TICKS, 0);
            return;
        }

        if (isFriendlyTo(target)) {
            this.entityData.set(SHOOT_TICKS, 0);
            return;
        }

        if (!this.hasLineOfSight(target)) {
            this.entityData.set(SHOOT_TICKS, 0);
            return;
        }

        if (this.distanceToSqr(target) > 30.0D * 30.0D) {
            this.entityData.set(SHOOT_TICKS, 0);
            return;
        }

        this.getLookControl().setLookAt(target, 30.0F, 30.0F);

        int shootTicks = this.entityData.get(SHOOT_TICKS);

        if (shootTicks <= 0) {
            this.entityData.set(SHOOT_TICKS, SHOOT_TOTAL_TICKS);
            return;
        }

        int elapsedShootTicks = SHOOT_TOTAL_TICKS - shootTicks;
        boolean isBlasting = elapsedShootTicks >= SHOOT_WINDUP_TICKS
                && elapsedShootTicks < SHOOT_WINDUP_TICKS + SHOOT_BLAST_TICKS;

        if (isBlasting && elapsedShootTicks % BULLET_FIRE_INTERVAL == 0) {
            shootGunAt(target);
        }
    }

    private void shootGunAt(LivingEntity target) {
        Vec3 start = getGunPosition();
        Vec3 targetPos = target.getEyePosition();

        Vec3 direction = targetPos.subtract(start);
        if (direction.lengthSqr() < 1.0E-4D) return;

        direction = direction.normalize();

        double spread = 0.045D;
        direction = direction.add(
                this.random.nextGaussian() * spread,
                this.random.nextGaussian() * spread,
                this.random.nextGaussian() * spread
        ).normalize();

        double speed = 0.22D;

        MechaTerrorShotEntity shot = MechaTerrorShotEntity.create(
                RileyModEntities.MECHA_TERROR_SHOT.get(),
                this.level(),
                this,
                direction.x * speed,
                direction.y * speed,
                direction.z * speed
        );

        shot.setPos(start.x, start.y, start.z);
        shot.setDeltaMovement(direction.scale(0.35D));

        this.level().addFreshEntity(shot);

        this.level().playSound(
                null,
                this.blockPosition(),
                SoundEvents.BLAZE_SHOOT,
                SoundSource.HOSTILE,
                0.45F,
                1.8F + this.random.nextFloat() * 0.25F
        );
    }

    private Vec3 getGunPosition() {
        Vec3 look = this.getLookAngle();
        Vec3 right = new Vec3(-look.z, 0.0D, look.x);

        if (right.lengthSqr() > 1.0E-4D) {
            right = right.normalize();
        }

        return this.position()
                .add(0.0D, this.getBbHeight() * 0.62D, 0.0D)
                .add(look.scale(1.2D))
                .add(right.scale(0.35D));
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
        if(this.idleAminationTimeout <= 0) {
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

        if (this.isShooting()) {
            shootAnimationState.startIfStopped(this.tickCount);
        } else {
            shootAnimationState.stop();
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
                .add(Attributes.FOLLOW_RANGE, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.30D)
                .add(Attributes.ARMOR_TOUGHNESS, .7f)
                .add(Attributes.ATTACK_KNOCKBACK, 10f)
                .add(Attributes.ATTACK_DAMAGE, 20f);

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));

        this.goalSelector.addGoal(4, new MechaTerrorKeepRangeGoal(this, 1.1D, 10.0D, 30.0D));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new WildMechDisableFriendlyTargetGoal(this));
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
