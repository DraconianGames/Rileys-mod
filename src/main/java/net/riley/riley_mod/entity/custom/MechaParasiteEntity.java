package net.riley.riley_mod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;

public class MechaParasiteEntity extends TamableAnimal {
    private static final String TRANSFORM_TIMER_TAG = "MechaTransformTimer";
    private static final int TRANSFORM_TIME = 24000;

    private int mechaTransformTimer = TRANSFORM_TIME;

    public MechaParasiteEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;
    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAminationStates();
        } else {
            tickMechaTransformation();
        }
    }


    private void tickMechaTransformation() {
        if (this.isBaby()) {
            return;
        }

        if (this.mechaTransformTimer > 0) {
            this.mechaTransformTimer--;
            return;
        }

        transformIntoRandomMech();
    }
    private void transformIntoRandomMech() {
        if (!(this.level() instanceof ServerLevel serverLevel) || this.isRemoved()) {
            return;
        }

        EntityType<? extends TamableAnimal> transformType = switch (this.random.nextInt(3)) {
            case 0 -> RileyModEntities.MECHAREX.get();
            case 1 -> RileyModEntities.MECHA_TERROR.get();
            default -> RileyModEntities.PARASITE_CARRIER.get();
        };
        TamableAnimal mech = transformType.create(serverLevel);

        if (mech == null) {
            return;
        }

        mech.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        mech.setYBodyRot(this.yBodyRot);
        mech.setYHeadRot(this.yHeadRot);

        if (this.isTame()) {
            mech.setTame(true);
            mech.setOwnerUUID(this.getOwnerUUID());
        } else {
            mech.setTame(false);
            mech.setOwnerUUID(null);
        }

        if (this.hasCustomName()) {
            mech.setCustomName(this.getCustomName());
            mech.setCustomNameVisible(this.isCustomNameVisible());
        }

        serverLevel.addFreshEntity(mech);
        this.discard();
    }
    private void setupAminationStates() {
        if(this.idleAminationTimeout <- 0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
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
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SeekParasiteCarrierGoal(this, 1.2D, 32.0D));

        this.goalSelector.addGoal(5,new RandomStrollGoal(this,1D));
        this.goalSelector.addGoal(6,new LookAtPlayerGoal(this, Player.class,5f));
        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));
    }
    private static class SeekParasiteCarrierGoal extends Goal {
        private final MechaParasiteEntity parasite;
        private final double speedModifier;
        private final double searchRange;
        @Nullable
        private ParasiteCarrierEntity targetCarrier;

        private SeekParasiteCarrierGoal(MechaParasiteEntity parasite, double speedModifier, double searchRange) {
            this.parasite = parasite;
            this.speedModifier = speedModifier;
            this.searchRange = searchRange;
            this.setFlags(EnumSet.of(Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.parasite.level().isClientSide) {
                return false;
            }

            this.targetCarrier = this.parasite.level().getEntitiesOfClass(
                            ParasiteCarrierEntity.class,
                            this.parasite.getBoundingBox().inflate(this.searchRange),
                            carrier -> carrier.isAlive() && carrier.canStoreMechaParasite()
                    )
                    .stream()
                    .min(Comparator.comparingDouble(this.parasite::distanceToSqr))
                    .orElse(null);

            return this.targetCarrier != null;
        }

        @Override
        public boolean canContinueToUse() {
            return this.targetCarrier != null
                    && this.targetCarrier.isAlive()
                    && this.targetCarrier.canStoreMechaParasite()
                    && this.parasite.distanceToSqr(this.targetCarrier) <= this.searchRange * this.searchRange;
        }

        @Override
        public void tick() {
            if (this.targetCarrier == null) {
                return;
            }

            this.parasite.getLookControl().setLookAt(this.targetCarrier, 30.0F, 30.0F);
            this.parasite.getNavigation().moveTo(this.targetCarrier, this.speedModifier);

            double storeDistance = 7.0D;

            if (this.parasite.distanceToSqr(this.targetCarrier) <= storeDistance * storeDistance) {
                this.targetCarrier.storeMechaParasite(this.parasite);
            }
        }

        @Override
        public void stop() {
            this.targetCarrier = null;
            this.parasite.getNavigation().stop();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 5D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ARMOR_TOUGHNESS, .1f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.ATTACK_DAMAGE, 1f);

    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt(TRANSFORM_TIMER_TAG, this.mechaTransformTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(TRANSFORM_TIMER_TAG)) {
            this.mechaTransformTimer = tag.getInt(TRANSFORM_TIMER_TAG);
        } else {
            this.mechaTransformTimer = TRANSFORM_TIME;
        }
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
}
