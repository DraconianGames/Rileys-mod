package net.riley.riley_mod.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.custom.MechaTerrorEntity;

import java.util.EnumSet;

public class MechaTerrorKeepRangeGoal extends Goal {
    private final MechaTerrorEntity mechaTerror;
    private final double speed;
    private final double minRange;
    private final double maxRange;

    private int strafeCooldown = 0;
    private int switchDirCooldown = 0;
    private boolean clockwise = true;

    public MechaTerrorKeepRangeGoal(MechaTerrorEntity mechaTerror, double speed, double minRange, double maxRange) {
        this.mechaTerror = mechaTerror;
        this.speed = speed;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (mechaTerror.isActivating()) return false;
        if (mechaTerror.isOrderedToSit() || mechaTerror.isInSittingPose()) return false;

        LivingEntity target = mechaTerror.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        LivingEntity target = mechaTerror.getTarget();
        if (target == null) return;

        mechaTerror.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double dist2 = mechaTerror.distanceToSqr(target);
        double min2 = minRange * minRange;
        double max2 = maxRange * maxRange;
        boolean hasLineOfSight = mechaTerror.hasLineOfSight(target);

        if (switchDirCooldown-- <= 0) {
            switchDirCooldown = 40 + mechaTerror.getRandom().nextInt(60);

            if (mechaTerror.getRandom().nextFloat() < 0.5F) {
                clockwise = !clockwise;
            }
        }

        if (dist2 > max2) {
            mechaTerror.getNavigation().moveTo(target, speed);
            return;
        }

        if (dist2 < min2) {
            Vec3 away = mechaTerror.position().subtract(target.position());
            Vec3 horizontalAway = new Vec3(away.x, 0.0D, away.z);

            if (horizontalAway.lengthSqr() < 1.0E-4D) {
                float yawRad = mechaTerror.getYRot() * Mth.DEG_TO_RAD;
                horizontalAway = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
            }

            Vec3 awayDir = horizontalAway.normalize();
            Vec3 side = new Vec3(-awayDir.z, 0.0D, awayDir.x);
            if (!clockwise) side = side.scale(-1.0D);

            Vec3 dest = mechaTerror.position()
                    .add(awayDir.scale(7.0D))
                    .add(side.scale(3.0D));

            boolean ok = mechaTerror.getNavigation().moveTo(dest.x, dest.y, dest.z, speed);

            if (!ok) {
                mechaTerror.getNavigation().stop();
            }

            return;
        }

        if (!hasLineOfSight || strafeCooldown-- <= 0) {
            strafeCooldown = hasLineOfSight ? 10 : 5;

            double desiredRadius = (minRange + maxRange) * 0.5D;

            Vec3 toMechaTerror = mechaTerror.position().subtract(target.position());
            Vec3 horizontal = new Vec3(toMechaTerror.x, 0.0D, toMechaTerror.z);

            if (horizontal.lengthSqr() < 1.0E-4D) {
                float yawRad = mechaTerror.getYRot() * Mth.DEG_TO_RAD;
                horizontal = new Vec3(-Mth.sin(yawRad), 0.0D, Mth.cos(yawRad));
            }

            Vec3 radial = horizontal.normalize();
            Vec3 tangent = new Vec3(-radial.z, 0.0D, radial.x);

            if (!clockwise) {
                tangent = tangent.scale(-1.0D);
            }

            double strafeDistance = hasLineOfSight ? 5.0D : 9.0D;

            Vec3 dest = target.position()
                    .add(radial.scale(desiredRadius))
                    .add(tangent.scale(strafeDistance));

            boolean ok = mechaTerror.getNavigation().moveTo(dest.x, dest.y, dest.z, speed);

            if (!ok) {
                mechaTerror.getNavigation().stop();
            }
        }
    }

    @Override
    public void stop() {
        mechaTerror.getNavigation().stop();
    }
}