package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.List;
import javax.annotation.Nullable;

public class AbyssFollowParentGoal extends Goal {
    private final AgeableMob baby;
    @Nullable
    private AgeableMob parent;
    private final double speedModifier;
    private int delayCounter;

    public AbyssFollowParentGoal(AgeableMob pBaby, double pSpeedModifier) {
        this.baby = pBaby;
        this.speedModifier = pSpeedModifier;
    }

    @Override
    public boolean canUse() {
        if (!this.baby.isBaby()) {
            return false;
        } else {
            List<? extends AgeableMob> list = this.baby.level().getEntitiesOfClass(this.baby.getClass(), this.baby.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            AgeableMob potentialParent = null;
            double d0 = Double.MAX_VALUE;

            for(AgeableMob entity : list) {
                if (!entity.isBaby()) {
                    double d1 = this.baby.distanceToSqr(entity);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        potentialParent = entity;
                    }
                }
            }

            if (potentialParent == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parent = potentialParent;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.baby.isBaby()) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double d0 = this.baby.distanceToSqr(this.parent);
            return !(d0 < 9.0D) && !(d0 > 256.0D);
        }
    }

    @Override
    public void start() {
        this.delayCounter = 0;
    }

    @Override
    public void stop() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = this.adjustedTickDelay(10);
            this.baby.getNavigation().moveTo(this.parent, this.speedModifier);
        }
    }
}