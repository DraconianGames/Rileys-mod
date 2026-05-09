package net.riley.riley_mod.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class MechaTerrorShotEntity extends AbstractHurtingProjectile {
    private static final float DAMAGE = 14.0F;

    public MechaTerrorShotEntity(EntityType<? extends MechaTerrorShotEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static MechaTerrorShotEntity create(EntityType<? extends MechaTerrorShotEntity> type,
                                               Level level,
                                               LivingEntity shooter,
                                               double powerX,
                                               double powerY,
                                               double powerZ) {
        MechaTerrorShotEntity shot = new MechaTerrorShotEntity(type, level);
        shot.setOwner(shooter);
        shot.xPower = powerX;
        shot.yPower = powerY;
        shot.zPower = powerZ;
        return shot;
    }

    @Override
    public void tick() {
        this.baseTick();

        Vec3 motion = this.getDeltaMovement();
        Vec3 acceleration = new Vec3(this.xPower, this.yPower, this.zPower);

        this.setDeltaMovement(motion.add(acceleration).scale(0.98D));

        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onHit(hitResult);
        }

        this.move(net.minecraft.world.entity.MoverType.SELF, this.getDeltaMovement());
        this.updateRotation();

        if (this.level().isClientSide) {
            this.level().addParticle(
                    ParticleTypes.ELECTRIC_SPARK,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0.0D,
                    0.0D,
                    0.0D
            );
        }

        if (!this.level().isClientSide && this.tickCount > 80) {
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (!super.canHitEntity(entity)) return false;
        if (entity == this.getOwner()) return false;

        if (entity instanceof LivingEntity living && isFriendlyTo(living)) {
            return false;
        }

        return true;
    }

    private boolean isFriendlyTo(LivingEntity other) {
        Entity owner = this.getOwner();

        if (owner instanceof TamableAnimal ownerTameable) {
            // Wild MechaRex and wild MechaTerror should not damage each other
            if (!ownerTameable.isTame() && isWildMechaMob(ownerTameable) && isWildMechaMob(other)) {
                return true;
            }

            // Tamed mobs should not hurt their owner
            if (ownerTameable.isTame() && ownerTameable.isOwnedBy(other)) {
                return true;
            }

            // Tamed mobs should not hurt same-owner tamed mobs
            UUID ownerUUID = ownerTameable.getOwnerUUID();
            if (ownerUUID != null && other instanceof TamableAnimal otherTameable && otherTameable.isTame()) {
                return ownerUUID.equals(otherTameable.getOwnerUUID());
            }
        }

        return false;
    }

    private boolean isWildMechaMob(Entity entity) {
        if (!(entity instanceof TamableAnimal tamable) || tamable.isTame()) {
            return false;
        }

        return entity instanceof MechaRexEntity || entity instanceof MechaTerrorEntity || entity instanceof ParasiteCarrierEntity;
    }
//TODO make this a universal list for the mechas.
    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        if (this.level().isClientSide) return;

        Entity hitEntity = hitResult.getEntity();

        if (hitEntity instanceof LivingEntity living && !isFriendlyTo(living)) {
            living.hurt(this.level().damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), DAMAGE);
        }

        impactEffects();
        this.discard();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

        if (this.level().isClientSide) return;

        if (hitResult.getType() != HitResult.Type.ENTITY) {
            impactEffects();
            this.discard();
        }
    }

    private void impactEffects() {
        this.level().playSound(
                null,
                this.blockPosition(),
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.HOSTILE,
                0.45F,
                1.7F
        );

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.ELECTRIC_SPARK,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    12,
                    0.15D,
                    0.15D,
                    0.15D,
                    0.08D
            );
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }
}