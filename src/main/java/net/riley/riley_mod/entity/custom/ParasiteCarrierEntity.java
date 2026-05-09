package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.*;
import net.riley.riley_mod.particle.ParasiteBlastParticleData;
import net.riley.riley_mod.particle.RileyModParticles;
import net.riley.riley_mod.util.RileyModTags;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class ParasiteCarrierEntity extends TamableAnimal {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.literal("Parasite Carrier"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS
    );
    private static final String STORED_MECHA_PARASITES_TAG = "StoredMechaParasites";
    private static final String STORAGE_COOLDOWN_TAG = "MechaParasiteStorageCooldown";
    private static final String MECHA_PARASITE_TRANSFORM_TIMER_TAG = "MechaTransformTimer";
    private static final String MECHA_PARASITE_AGE_TAG = "Age";
    private static final int STORAGE_COOLDOWN_AFTER_RELEASE = 400;
    private static final int MAX_STORED_MECHA_PARASITES = 7;
    private static final int MIN_STORED_TRANSFORM_TIMER = 200;
    private static final int TAIL_SMASH_TOTAL_TICKS = 30;
    private static final int TAIL_SMASH_DAMAGE_TICK = 20;

    private final List<CompoundTag> storedMechaParasiteData = new ArrayList<>();
    private int mechaParasiteStorageCooldown = 0;

    public ParasiteCarrierEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState tailSwipeAnimationState = new AnimationState();
    public final AnimationState launchBombAnimationState = new AnimationState();
    public final AnimationState infectionBlastAnimationState = new AnimationState();
    public final AnimationState pickUpAnimationState = new AnimationState();


    public final AnimationState activationAnimationState = new AnimationState();

    private int idleAminationTimeout = 0;
    LivingEntity shooter = this;
    private static final EntityDataAccessor<Integer> TAIL_SWIPE_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BOMB_LAUNCH_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ACTIVATION_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INFECTION_BLAST_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PICK_UP_TICKS =
            SynchedEntityData.defineId(MechaRexEntity.class, EntityDataSerializers.INT);


    private boolean bombSpawned = false;
    private boolean infectionBlastReleased = false;
    private boolean tailSmashDamageDealt = false;

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TAIL_SWIPE_TICKS, 0);
        this.entityData.define(BOMB_LAUNCH_TICKS, 0);
        this.entityData.define(ACTIVATION_TICKS, 0);
        this.entityData.define(INFECTION_BLAST_TICKS, 0);
        this.entityData.define(PICK_UP_TICKS, 0);
    }

    public boolean isLaunchingBomb() {
        return this.entityData.get(BOMB_LAUNCH_TICKS) > 0;
    }

    public boolean isUsingInfectionBlast() {
        return this.entityData.get(INFECTION_BLAST_TICKS) > 0;
    }
    public boolean isPlayingPickUpAnimation() {
        return this.entityData.get(PICK_UP_TICKS) > 0;
    }

    public void startPickUpAnimation() {
        if (this.level().isClientSide) return;
        this.entityData.set(PICK_UP_TICKS, 20);
    }
    public boolean isUsingTailSmash() {
        return this.entityData.get(TAIL_SWIPE_TICKS) > 0;
    }
    public void startInfectionBlast() {
        if (this.level().isClientSide) return;

        this.entityData.set(INFECTION_BLAST_TICKS, 80); // 4 seconds
        this.infectionBlastReleased = false;
        this.getNavigation().stop();
    }

    public void startLaunchBomb() {
        if (this.level().isClientSide) return;
        this.entityData.set(BOMB_LAUNCH_TICKS, 20); // 1.0s
        this.bombSpawned = false;
    }
    public void startTailSmash() {
        if (this.level().isClientSide) return;
        this.entityData.set(TAIL_SWIPE_TICKS, TAIL_SMASH_TOTAL_TICKS);
        this.tailSmashDamageDealt = false;
        this.getNavigation().stop();
    }

    public boolean isActivating() {
        return this.entityData.get(ACTIVATION_TICKS) > 0;

    }
    public void startActivation() {
        if (this.level().isClientSide) return;
        this.entityData.set(ACTIVATION_TICKS, 90); // 4.0s @ 20 tps
    }
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAminationStates();
        } else {
            updateBossBar();
            if (this.mechaParasiteStorageCooldown > 0) {
                this.mechaParasiteStorageCooldown--;
            }
            tickStoredMechaParasiteTimers();
            tryStoreNearbyMechaParasite();

            // Freeze AI during activation
            if (this.isActivating()) {
                this.getNavigation().stop();
                this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
                this.setTarget(null);
            }

            if (this.isUsingInfectionBlast()) {
                this.getNavigation().stop();
                this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
            }
            if (this.isUsingTailSmash()) {
                this.getNavigation().stop();
                this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
            }
            // Only run combat logic if not activating
            if (!this.isActivating()) {
                tickAttacksServer();
            }

            int act = this.entityData.get(ACTIVATION_TICKS);
            if (act > 0) {
                this.entityData.set(ACTIVATION_TICKS, act - 1);
            }
            int pickUp = this.entityData.get(PICK_UP_TICKS);
            if (pickUp > 0) {
                this.entityData.set(PICK_UP_TICKS, pickUp - 1);
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
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = super.mobInteract(player, hand);
        if (result.consumesAction()) return result;

        if (this.level().isClientSide) {
            // let server decide
            return InteractionResult.SUCCESS;
        }

        // Shift-right-click releases stored parasite.
        if (this.isTame() && this.isOwnedBy(player) && player.isShiftKeyDown()) {
            return releaseStoredMechaParasite(player);
        }

        // Normal right-click still toggles sit.
        if (this.isTame() && this.isOwnedBy(player)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.getNavigation().stop();
            this.setTarget(null);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        if (!this.level().isClientSide
                && this.canStoreMechaParasite()
                && target instanceof MechaParasiteEntity mechaParasite) {
            storeMechaParasite(mechaParasite);
            return true;
        }

        return super.doHurtTarget(target);
    }

    public boolean canStoreMechaParasite() {
        return this.getHealth() >= 10.0F
                && this.storedMechaParasiteData.size() < MAX_STORED_MECHA_PARASITES
                && this.mechaParasiteStorageCooldown <= 0;
    }


    public void storeMechaParasite(MechaParasiteEntity mechaParasite) {
        if (this.level().isClientSide || !this.canStoreMechaParasite()) {
            return;
        }

        CompoundTag parasiteData = new CompoundTag();
        mechaParasite.saveWithoutId(parasiteData);

        parasiteData.remove("UUID");
        parasiteData.remove("Pos");
        parasiteData.remove("Motion");
        parasiteData.remove("Rotation");

        this.storedMechaParasiteData.add(parasiteData.copy());
        this.startPickUpAnimation();
        mechaParasite.discard();
    }

    private void tickStoredMechaParasiteTimers() {
        if (this.storedMechaParasiteData.isEmpty()) {
            return;
        }

        for (CompoundTag parasiteData : this.storedMechaParasiteData) {
            int age = parasiteData.getInt(MECHA_PARASITE_AGE_TAG);

            if (age < 0) {
                parasiteData.putInt(MECHA_PARASITE_AGE_TAG, age + 1);
                continue;
            }

            if (!parasiteData.contains(MECHA_PARASITE_TRANSFORM_TIMER_TAG)) {
                continue;
            }

            int timer = parasiteData.getInt(MECHA_PARASITE_TRANSFORM_TIMER_TAG);

            if (timer > MIN_STORED_TRANSFORM_TIMER) {
                parasiteData.putInt(MECHA_PARASITE_TRANSFORM_TIMER_TAG, timer - 1);
            }
        }
    }

    private void tryStoreNearbyMechaParasite() {
        if (!this.canStoreMechaParasite()) {
            return;
        }

        double intakeRadius = 7.0D;

        MechaParasiteEntity parasite = this.level().getEntitiesOfClass(
                        MechaParasiteEntity.class,
                        this.getBoundingBox().inflate(intakeRadius),
                        entity -> entity.isAlive()
                                && !entity.isRemoved()
                                && this.distanceToSqr(entity) <= intakeRadius * intakeRadius
                )
                .stream()
                .min((first, second) -> Double.compare(this.distanceToSqr(first), this.distanceToSqr(second)))
                .orElse(null);

        if (parasite != null) {
            storeMechaParasite(parasite);
        }
    }

    private InteractionResult releaseStoredMechaParasite(Player player) {
        if (this.storedMechaParasiteData.isEmpty()) {
            return InteractionResult.PASS;
        }

        CompoundTag parasiteData = this.storedMechaParasiteData.remove(this.storedMechaParasiteData.size() - 1);
        this.startPickUpAnimation();
        spawnStoredMechaParasite(parasiteData, 2.0D);

        this.mechaParasiteStorageCooldown = STORAGE_COOLDOWN_AFTER_RELEASE;

        return InteractionResult.CONSUME;
    }

    private void releaseAllStoredMechaParasites() {
        if (this.storedMechaParasiteData.isEmpty()) {
            return;
        }

        this.startPickUpAnimation();

        for (int i = 0; i < this.storedMechaParasiteData.size(); i++) {
            spawnStoredMechaParasite(this.storedMechaParasiteData.get(i), 2.0D + i * 0.6D);
        }

        this.storedMechaParasiteData.clear();
    }

    private void spawnStoredMechaParasite(CompoundTag storedData, double distanceFromCarrier) {
        EntityType<MechaParasiteEntity> type = RileyModEntities.MECHA_PARASITE.get();
        MechaParasiteEntity parasite = type.create(this.level());

        if (parasite == null) {
            return;
        }

        CompoundTag parasiteData = storedData.copy();
        parasite.load(parasiteData);

        if (this.isTame()) {
            parasite.setTame(true);
            parasite.setOwnerUUID(this.getOwnerUUID());
        } else {
            parasite.setTame(false);
            parasite.setOwnerUUID(null);
        }

        double angle = this.random.nextDouble() * Math.PI * 2.0D;
        double x = this.getX() + Math.cos(angle) * distanceFromCarrier;
        double y = this.getY();
        double z = this.getZ() + Math.sin(angle) * distanceFromCarrier;

        parasite.moveTo(x, y, z, this.getYRot(), 0.0F);

        this.level().addFreshEntity(parasite);
    }

    @Override
    public void die(DamageSource damageSource) {
        if (!this.level().isClientSide) {
            releaseAllStoredMechaParasites();
        }

        super.die(damageSource);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        ListTag storedParasites = new ListTag();

        for (CompoundTag parasiteData : this.storedMechaParasiteData) {
            storedParasites.add(parasiteData.copy());
        }

        tag.put(STORED_MECHA_PARASITES_TAG, storedParasites);
        tag.putInt(STORAGE_COOLDOWN_TAG, this.mechaParasiteStorageCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        this.storedMechaParasiteData.clear();

        if (tag.contains(STORED_MECHA_PARASITES_TAG)) {
            ListTag storedParasites = tag.getList(STORED_MECHA_PARASITES_TAG, 10);

            for (int i = 0; i < storedParasites.size() && i < MAX_STORED_MECHA_PARASITES; i++) {
                this.storedMechaParasiteData.add(storedParasites.getCompound(i).copy());
            }
        }

        this.mechaParasiteStorageCooldown = tag.getInt(STORAGE_COOLDOWN_TAG);
    }

    private void tickAttacksServer() {
        tickTailSmashServer();
        tickInfectionBlastServer();


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
    private void tickTailSmashServer() {
        int tailSmash = this.entityData.get(TAIL_SWIPE_TICKS);
        if (tailSmash <= 0) return;

        if (!this.tailSmashDamageDealt && tailSmash <= TAIL_SMASH_DAMAGE_TICK) {
            this.tailSmashDamageDealt = true;
            damageEntitiesInFront();
        }

        this.entityData.set(TAIL_SWIPE_TICKS, tailSmash - 1);
    }
    private void damageEntitiesInFront() {
        double range = 7.0D;
        double halfAngleDot = 0.4D;
        Vec3 lookDirection = this.getLookAngle().multiply(1.0D, 0.0D, 1.0D).normalize();

        if (lookDirection.lengthSqr() < 1.0E-4D) {
            return;
        }

        AABB hitBox = this.getBoundingBox().inflate(range, 2.0D, range);
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        for (LivingEntity entity : this.level().getEntitiesOfClass(
                LivingEntity.class,
                hitBox,
                entity -> entity.isAlive()
                        && entity != this
                        && !isFriendlyTo(entity)
        )) {
            Vec3 toEntity = entity.position()
                    .subtract(this.position())
                    .multiply(1.0D, 0.0D, 1.0D);

            if (toEntity.lengthSqr() < 1.0E-4D) {
                continue;
            }

            double distanceSqr = toEntity.lengthSqr();

            if (distanceSqr > range * range) {
                continue;
            }

            Vec3 directionToEntity = toEntity.normalize();
            double dot = lookDirection.dot(directionToEntity);

            if (dot < halfAngleDot) {
                continue;
            }

            entity.hurt(this.damageSources().mobAttack(this), damage);

            Vec3 knockback = directionToEntity.scale(1.2D);
            entity.push(knockback.x, 0.35D, knockback.z);
        }
    }
    private void tickInfectionBlastServer() {
        int blast = this.entityData.get(INFECTION_BLAST_TICKS);
        if (blast <= 0) return;

        int elapsed = 80 - blast;

        if (!infectionBlastReleased && elapsed >= 20) {
            infectionBlastReleased = true;
            spawnParasiteBlastParticles();
            releaseInfectionBlast();
        }

        this.entityData.set(INFECTION_BLAST_TICKS, blast - 1);
    }
    private void releaseInfectionBlast() {
        double radius = 10.0D;

        for (LivingEntity entity : this.level().getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(radius),
                this::canInfect
        )) {
            entity.addEffect(new MobEffectInstance(
                    RileyModEffects.MECHANICAL_PARASITE.get(),
                    24000,
                    0
            ));
        }
    }

    private boolean canInfect(LivingEntity entity) {
        if (entity == null || !entity.isAlive()) {
            return false;
        }

        if (entity == this) {
            return false;
        }

        if (entity.hasEffect(RileyModEffects.MECHANICAL_PARASITE.get())) {
            return false;
        }

        if (entity.getType().is(RileyModTags.EntityTypes.MECHANICAL_INFECTION_IMMUNE)) {
            return false;
        }

        return !isFriendlyTo(entity);
    }


    private void spawnParasiteBlastParticles() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        double cx = this.getX();
        double cy = this.getY() + 2.5D;
        double cz = this.getZ();

        int particleCount = 64;

        for (int i = 0; i < particleCount; i++) {
            float angle = (float) ((Math.PI * 2.0D / particleCount) * i);
            float outwardSpeed = 0.16F + this.random.nextFloat() * 0.03F;
            float angularSpeed = 0.28F + this.random.nextFloat() * 0.18F;

            if (i % 2 == 0) {
                angularSpeed *= -1.0F;
            }

            float yOffset = -2.3F + this.random.nextFloat() * 1.8F;

            serverLevel.sendParticles(
                    new ParasiteBlastParticleData(
                            RileyModParticles.PARASITE_BLAST_PARTICLE.get(),
                            cx,
                            cy,
                            cz,
                            outwardSpeed,
                            angularSpeed,
                            angle,
                            yOffset
                    ),
                    cx,
                    cy,
                    cz,
                    1,
                    0.0D,
                    0.0D,
                    0.0D,
                    0.0D
            );
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
        if (this.isPlayingPickUpAnimation()) {
            pickUpAnimationState.startIfStopped(this.tickCount);
        } else {
            pickUpAnimationState.stop();
        }
        if (this.isUsingInfectionBlast()) {
            infectionBlastAnimationState.startIfStopped(this.tickCount);
        } else {
            infectionBlastAnimationState.stop();
        }
        if (this.isOrderedToSit() || this.isInSittingPose()) {
            tailSwipeAnimationState.stop();
            launchBombAnimationState.stop();
            idleAnimationState.startIfStopped(this.tickCount);
            return;
        }//TODO make sit animation
        // When server timers are active, run the matching animation on client
        if (this.isUsingTailSmash()) {
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
        this.goalSelector.addGoal(2, new ParasiteCarrierTailSmashGoal(this));


        this.goalSelector.addGoal(3, new ParasiteCarrierBombLaunchGoal(this, 11.0, 20.0));
        this.goalSelector.addGoal(4, new ParasiteCarrierInfectionBlastGoal(this, 8.0, 14.0));

        this.goalSelector.addGoal(5, new ParasiteCarrierKeepRangeGoal(this, 1.2D, 11.0, 16.0));

        this.goalSelector.addGoal(6, new ParasiteCarrierFollowOwnerWhenNoTargetGoal(this, 1.0D, 10.0F, 2.0F, false));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new WildMechDisableFriendlyTargetGoal(this));
        this.targetSelector.addGoal(0, new DisableFriendlyTargetGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new ParasiteCarrierShareTargetWithAlliesGoal(this, 32.0));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                10,
                true,
                false,
                entity -> !this.isTame() && entity instanceof Mob && this.canInfect(entity)
        ));
        this.targetSelector.addGoal(4, new ParasiteCarrierWildTargetPlayerGoal(this));

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
    private static class ParasiteCarrierTailSmashGoal extends Goal {
        private final ParasiteCarrierEntity carrier;
        private int cooldownTicks = 0;

        private ParasiteCarrierTailSmashGoal(ParasiteCarrierEntity carrier) {
            this.carrier = carrier;
            this.setFlags(java.util.EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.carrier.isActivating()) return false;
            if (this.carrier.isUsingInfectionBlast()) return false;
            if (this.carrier.isUsingTailSmash()) return false;
            if (this.carrier.isOrderedToSit() || this.carrier.isInSittingPose()) return false;

            if (this.cooldownTicks > 0) {
                this.cooldownTicks--;
                return false;
            }

            LivingEntity target = this.carrier.getTarget();

            if (target == null || !target.isAlive()) {
                return false;
            }

            return this.carrier.distanceToSqr(target) <= 7.0D * 7.0D
                    && this.carrier.hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse() {
            return this.carrier.isUsingTailSmash();
        }

        @Override
        public void start() {
            LivingEntity target = this.carrier.getTarget();

            if (target != null) {
                this.carrier.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.carrier.startTailSmash();
            this.cooldownTicks = 60;
        }

        @Override
        public void tick() {
            LivingEntity target = this.carrier.getTarget();

            if (target != null) {
                this.carrier.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            this.carrier.getNavigation().stop();
        }
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
        bomb.setHomingTarget(target);

        bomb.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.level().addFreshEntity(bomb);
    }
}
