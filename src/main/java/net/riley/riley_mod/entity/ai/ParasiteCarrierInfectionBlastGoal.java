package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

import java.util.EnumSet;

public class ParasiteCarrierInfectionBlastGoal extends Goal{
    private final ParasiteCarrierEntity rex;
    private final double minRange;
    private final double maxRange;
    private int cooldownTicks = 0;

    public ParasiteCarrierInfectionBlastGoal(ParasiteCarrierEntity rex, double minRange, double maxRange) {
        this.rex = rex;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (rex.isActivating()) return false;
        if (rex.isOrderedToSit() || rex.isInSittingPose()) return false;

        if (cooldownTicks > 0) cooldownTicks--;
        if (cooldownTicks > 0) return false;



        LivingEntity target = rex.getTarget();
        if (target == null || !target.isAlive()) return false;

        double dist2 = rex.distanceToSqr(target);
        double min2 = minRange * minRange;
        double max2 = maxRange * maxRange;

        return dist2 >= min2 && dist2 <= max2 && rex.hasLineOfSight(target);
    }
    @Override
    public boolean canContinueToUse() {
        LivingEntity target = rex.getTarget();
        if (target == null || !target.isAlive()) return false;

        double dist2 = rex.distanceToSqr(target);
        return dist2 >= (minRange * minRange) && dist2 <= (maxRange * maxRange);
    }

    @Override
    public void start() {
        rex.startInfectionBlast();
        cooldownTicks = 80; // 4 seconds cooldown; tune
    }

    @Override
    public void tick() {
        if (cooldownTicks > 0) cooldownTicks--;

        LivingEntity target = rex.getTarget();
        if (target != null) {
            rex.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }
}
