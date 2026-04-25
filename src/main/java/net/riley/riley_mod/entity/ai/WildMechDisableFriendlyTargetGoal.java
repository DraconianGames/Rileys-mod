package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.riley.riley_mod.entity.custom.MechaRexEntity;
import net.riley.riley_mod.entity.custom.MechaTerrorEntity;

import java.util.EnumSet;

public class WildMechDisableFriendlyTargetGoal extends Goal {
    private final TamableAnimal mob;

    public WildMechDisableFriendlyTargetGoal(TamableAnimal mob) {
        this.mob = mob;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target == null) return;

        if (isWildMechaFriendly(target)) {
            this.mob.setTarget(null);
        }
    }

    private boolean isWildMechaFriendly(LivingEntity other) {
        if (this.mob.isTame()) return false;

        boolean mobIsWildMecha = this.mob instanceof MechaRexEntity
                || this.mob instanceof MechaTerrorEntity;

        if (!mobIsWildMecha) return false;

        if (other instanceof MechaRexEntity rex) {
            return !rex.isTame();
        }

        if (other instanceof MechaTerrorEntity terror) {
            return !terror.isTame();
        }

        return false;
    }
}