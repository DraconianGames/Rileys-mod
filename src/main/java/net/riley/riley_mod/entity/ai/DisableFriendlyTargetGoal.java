package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.UUID;

public class DisableFriendlyTargetGoal extends Goal {
    private final TamableAnimal mob;

    public DisableFriendlyTargetGoal(TamableAnimal mob) {
        this.mob = mob;
        // This goal doesn't move; it just filters targeting
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        return true; // always running
    }

    @Override
    public boolean canContinueToUse() {
        return true; // always running
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) return;

        if (isFriendly(target)) {
            mob.setTarget(null);
        }
    }

    private boolean isFriendly(LivingEntity other) {
        // Only protect friendlies when the mob is tamed
        if (!mob.isTame()) return false;

        // Owner is always friendly
        if (mob.isOwnedBy(other)) return true;

        // Same-owner tamed mobs are friendly
        UUID ownerUUID = mob.getOwnerUUID();
        if (ownerUUID == null) return false;

        if (other instanceof TamableAnimal ta && ta.isTame()) {
            return ownerUUID.equals(ta.getOwnerUUID());
        }

        return false;
    }
}
