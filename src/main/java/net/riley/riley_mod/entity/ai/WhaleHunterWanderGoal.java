package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WhaleHunterWanderGoal extends RandomSwimmingGoal {
    public WhaleHunterWanderGoal(PathfinderMob pMob, double pSpeedModifier, int pInterval) {
        super(pMob, pSpeedModifier, pInterval);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        Vec3 pos = super.getPosition();
        if (pos == null) return null;

        // Vec3 uses .x, .y, .z directly
        net.minecraft.core.BlockPos checkPos = new net.minecraft.core.BlockPos((int)pos.x, (int)pos.y + 2, (int)pos.z);

        if (this.mob.level().getBlockState(checkPos).isAir()) {
            return new Vec3(pos.x, pos.y - 4, pos.z);
        }

        return pos;
    }
}