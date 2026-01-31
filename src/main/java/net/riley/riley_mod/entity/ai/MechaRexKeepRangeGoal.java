package net.riley.riley_mod.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

import java.util.EnumSet;

public class MechaRexKeepRangeGoal extends Goal {
    private final MechaRexEntity rex;
    private final double speed;
    private final double minRange;
    private final double maxRange;
    // Strafing behavior
    private int strafeCooldown = 0;
    private int switchDirCooldown = 0;
    private boolean clockwise = true;

    public MechaRexKeepRangeGoal(MechaRexEntity rex, double speed, double minRange, double maxRange) {
        this.rex = rex;
        this.speed = speed;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (rex.isActivating()) return false;
        if (rex.isOrderedToSit() || rex.isInSittingPose()) return false;
        if (rex.isTailSwiping() || rex.isLaunchingBomb()) return false;

        LivingEntity target = rex.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void tick() {
        LivingEntity target = rex.getTarget();
        if (target == null) return;

        rex.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double dist2 = rex.distanceToSqr(target);
        double min2 = minRange * minRange;
        double max2 = maxRange * maxRange;

        if (switchDirCooldown-- <= 0) {
            switchDirCooldown = 40 + rex.getRandom().nextInt(60); // switch every ~2-5s
            if (rex.getRandom().nextFloat() < 0.5F) {
                clockwise = !clockwise;
            }
        }

        if (dist2 > max2) {
            // Too far: close distance
            rex.getNavigation().moveTo(target, speed);
            return;
        }

        if (dist2 < min2) {
            // Too close: back away (with slight sideways drift)
            Vec3 away = rex.position().subtract(target.position()).normalize();
            Vec3 side = new Vec3(-away.z, 0.0, away.x).normalize();
            if (!clockwise) side = side.scale(-1.0);

            Vec3 dest = rex.position().add(away.scale(6.0)).add(side.scale(2.0));
            boolean ok = rex.getNavigation().moveTo(dest.x, dest.y, dest.z, speed);
            if (!ok) rex.getNavigation().stop();
            return;
        }

        // In the sweet spot: circle-strafe around the target at mid radius
        if (strafeCooldown-- <= 0) {
            strafeCooldown = 10; // repick destination twice per second

            double desiredRadius = (minRange + maxRange) * 0.5;

            Vec3 toRex = rex.position().subtract(target.position());
            Vec3 horizontal = new Vec3(toRex.x, 0.0, toRex.z);

            // If we're exactly on top of the target, pick a direction based on facing
            if (horizontal.lengthSqr() < 1.0E-4) {
                float yawRad = rex.getYRot() * Mth.DEG_TO_RAD;
                horizontal = new Vec3(-Mth.sin(yawRad), 0.0, Mth.cos(yawRad));
            }

            Vec3 radial = horizontal.normalize(); // points from target -> rex
            Vec3 tangent = new Vec3(-radial.z, 0.0, radial.x); // 90° around Y
            if (!clockwise) tangent = tangent.scale(-1.0);

            // Target point on the ring: target + radial*radius + tangent*stride
            Vec3 dest = target.position()
                    .add(radial.scale(desiredRadius))
                    .add(tangent.scale(4.0)); // stride amount; tune 2-6

            boolean ok = rex.getNavigation().moveTo(dest.x, dest.y, dest.z, speed);
            if (!ok) {
                // If pathing fails, don't jitter: stop and let other attacks (like tail swipe) handle it
                rex.getNavigation().stop();
            }
        }
    }

    @Override
    public void stop() {
        rex.getNavigation().stop();
    }
}