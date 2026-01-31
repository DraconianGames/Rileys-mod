package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

public class MechaRexFollowOwnerWhenNoTargetGoal extends FollowOwnerGoal {
    private final MechaRexEntity rex;

    public MechaRexFollowOwnerWhenNoTargetGoal(MechaRexEntity rex, double speed, float startDist, float stopDist, boolean canFly) {
        super(rex, speed, startDist, stopDist, canFly);
        this.rex = rex;
    }

    @Override
    public boolean canUse() {
        if (rex.getTarget() != null) return false;
        if (rex.isActivating()) return false;
        if (rex.isOrderedToSit() || rex.isInSittingPose()) return false;
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (rex.getTarget() != null) return false;
        if (rex.isOrderedToSit() || rex.isInSittingPose()) return false;
        return super.canContinueToUse();
    }
}