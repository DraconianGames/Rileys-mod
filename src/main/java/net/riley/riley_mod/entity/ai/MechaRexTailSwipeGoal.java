package net.riley.riley_mod.entity.ai;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

import java.util.EnumSet;

public class MechaRexTailSwipeGoal extends Goal {
    private final MechaRexEntity rex;
    private int cooldownTicks = 0;

    public MechaRexTailSwipeGoal(MechaRexEntity rex) {
        this.rex = rex;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (rex.isActivating()) return false;

        if (cooldownTicks > 0) cooldownTicks--;  // <-- add this

        if (cooldownTicks > 0) return false;
        if (rex.isTailSwiping() || rex.isLaunchingBomb()) return false;

        LivingEntity target = rex.getTarget();
        if (target == null || !target.isAlive()) return false;

        double dist = rex.distanceTo(target);
        return dist <= 7.0; // tune range
    }

    @Override
    public void start() {
        rex.startTailSwipe();
        cooldownTicks = 60; // 3 seconds cooldown; tune
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