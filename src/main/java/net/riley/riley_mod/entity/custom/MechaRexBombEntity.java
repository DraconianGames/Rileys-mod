package net.riley.riley_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class MechaRexBombEntity extends LargeFireball {

    public final AnimationState activeAnimationState = new AnimationState();
    private static final int DIVE_START_TICKS = 40; // 2 seconds
    private static final int HARD_DIVE_TICKS = 80;  // 4 seconds (more aggressive)

    public MechaRexBombEntity(EntityType<? extends MechaRexBombEntity> type, Level level) {
        super(type, level);
    }

    public static MechaRexBombEntity create(EntityType<? extends MechaRexBombEntity> type, Level level,
                                            LivingEntity shooter, double accelX, double accelY, double accelZ) {
        MechaRexBombEntity bomb = new MechaRexBombEntity(type, level);
        bomb.setOwner(shooter);

        bomb.xPower = accelX;
        bomb.yPower = accelY;
        bomb.zPower = accelZ;

        return bomb;
    }

    @Override
    public boolean isOnFire() {
        return false; // prevents the "entity on fire" render overlay
    }

    private static final EntityDataAccessor<Integer> HOMING_TARGET_ID =
            SynchedEntityData.defineId(MechaRexBombEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOMING_TARGET_ID, 0); // 0 = no target
    }

    /** Only used for non-player targets (per your requirement). */
    public void setHomingTarget(@org.jetbrains.annotations.Nullable LivingEntity target) {
        this.entityData.set(HOMING_TARGET_ID, target == null ? 0 : target.getId());
    }

    @org.jetbrains.annotations.Nullable
    private LivingEntity getHomingTarget() {
        int id = this.entityData.get(HOMING_TARGET_ID);
        if (id == 0) return null;
        if (!(this.level().getEntity(id) instanceof LivingEntity le)) return null;
        return le;
    }
    @Override
    public void tick() {
        this.baseTick();

        if (this.level().isClientSide) {
            this.activeAnimationState.startIfStopped(this.tickCount);
        }

        if (!this.level().isClientSide) {
            boolean diving = this.tickCount >= DIVE_START_TICKS;

            // Homing only before diving starts
            LivingEntity target = diving ? null : getHomingTarget();
            if (target != null && target.isAlive() && !(target instanceof Player)) {
                Vec3 toTarget = new Vec3(
                        target.getX() - this.getX(),
                        target.getEyeY() - this.getY(),
                        target.getZ() - this.getZ()
                );

                double len = toTarget.length();
                if (len > 1.0E-4) {
                    Vec3 desiredDir = toTarget.scale(1.0 / len);

                    double thrust = Math.sqrt(this.xPower * this.xPower + this.yPower * this.yPower + this.zPower * this.zPower);
                    if (thrust < 1.0E-4) thrust = 0.1;

                    double turn = 0.15;

                    Vec3 currentDir = new Vec3(this.xPower, this.yPower, this.zPower);
                    if (currentDir.lengthSqr() < 1.0E-6) currentDir = desiredDir;

                    Vec3 newDir = currentDir.normalize().lerp(desiredDir, turn).normalize();

                    this.xPower = newDir.x * thrust;
                    this.yPower = newDir.y * thrust;
                    this.zPower = newDir.z * thrust;
                }
            }

            // Dive after timeout
            if (diving) {
                float t = (float)(this.tickCount - DIVE_START_TICKS) / (HARD_DIVE_TICKS - DIVE_START_TICKS);
                t = Mth.clamp(t, 0.0F, 1.0F);

                double thrust = Math.sqrt(this.xPower * this.xPower + this.yPower * this.yPower + this.zPower * this.zPower);
                if (thrust < 1.0E-4) thrust = 0.1;

                Vec3 dir = new Vec3(this.xPower, this.yPower, this.zPower);
                if (dir.lengthSqr() < 1.0E-6) dir = this.getDeltaMovement();

                if (dir.lengthSqr() > 1.0E-6) {
                    Vec3 desired = dir.normalize().add(0.0, -1.5 * t, 0.0).normalize();
                    this.xPower = desired.x * thrust;
                    this.yPower = desired.y * thrust;
                    this.zPower = desired.z * thrust;
                }
            }
        }

        Vec3 motion = this.getDeltaMovement();
        Vec3 accel = new Vec3(this.xPower, this.yPower, this.zPower);

        Vec3 newMotion = motion.add(accel).scale(0.95);
        this.setDeltaMovement(newMotion);

        HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hit.getType() != HitResult.Type.MISS) {
            this.onHit(hit);
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.updateRotation();

        if (!this.level().isClientSide && this.tickCount > 200) {
            this.discard();
        }
    }
    private boolean isFriendlyTo(LivingEntity other) {
        Entity owner = this.getOwner();
        if (!(owner instanceof TamableAnimal ownerTa) || !ownerTa.isTame()) return false;

        UUID ownerUUID = ownerTa.getOwnerUUID();
        if (ownerUUID == null) return false;

        // Don't hurt the owner
        if (other == owner) return true;

        // Don't hurt other tamed mobs with the same owner
        if (other instanceof TamableAnimal otherTa && otherTa.isTame()) {
            return ownerUUID.equals(otherTa.getOwnerUUID());
        }

        return false;
    }
    @Override
    protected void onHit(HitResult hitResult) {
        if (this.level().isClientSide) return;

        float radius = 3.5F;
        float maxDamage = 50.0F;

        BlockPos pos = this.blockPosition();
        this.level().playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 1.0F);

        // Proper explosion particle (server -> clients)
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.EXPLOSION,
                    this.getX(), this.getY(), this.getZ(),
                    1,
                    0.0, 0.0, 0.0,
                    0.0
            );
        }

        // Manual AoE damage with friendly-fire protection
        AABB box = this.getBoundingBox().inflate(radius);
        for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
            if (isFriendlyTo(e)) continue;

            double dist = this.distanceTo(e);
            if (dist <= radius) {
                float scaled = (float) (maxDamage * (1.0 - (dist / radius)));
                e.hurt(this.level().damageSources().explosion(this, this.getOwner()), scaled);
            }
        }

        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        // Do NOT call super.onHitEntity(hitResult) either; onHit() handles everything
    }

}
