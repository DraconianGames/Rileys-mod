package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class NightTerrorHighWanderGoal extends WaterAvoidingRandomFlyingGoal {
    private final double minY;

    public NightTerrorHighWanderGoal(PathfinderMob pMob, double pSpeedModifier, double minY) {
        super(pMob, pSpeedModifier);
        this.minY = minY;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        Vec3 pos = super.getPosition();
        if (pos == null) return null;

        // If the picked random position is below our limit,
        // shift the target position up.
        if (pos.y < minY) {
            return new Vec3(pos.x, minY + this.mob.getRandom().nextInt(10), pos.z);
        }
        return pos;
    }

    @Override
    public boolean canUse() {
        // Only wander if we don't have an active combat target
        if (this.mob instanceof net.riley.riley_mod.entity.custom.NightTerrorEntity nightTerror) {
            if (nightTerror.isTame()) return false;
        }
        return this.mob.getTarget() == null && super.canUse();
    }
}
