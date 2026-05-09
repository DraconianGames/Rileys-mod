package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

public class ParasiteCarrierFollowOwnerWhenNoTargetGoal extends FollowOwnerGoal {
    private final ParasiteCarrierEntity rex;

    public ParasiteCarrierFollowOwnerWhenNoTargetGoal(ParasiteCarrierEntity rex, double speed, float startDist, float stopDist, boolean canFly) {
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