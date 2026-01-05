package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.custom.NightTerrorEntity;

import java.util.EnumSet;

public class NightTerrorAvoidPlayerGoal extends Goal {
    private final NightTerrorEntity mob;
    private final double speedModifier;
    private Player targetPlayer;
    private Vec3 hidePos;

    public NightTerrorAvoidPlayerGoal(NightTerrorEntity mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.mob.isBaby() || this.mob.isTame()) return false;

        this.targetPlayer = this.mob.level().getNearestPlayer(this.mob, 12.0D);
        if (this.targetPlayer == null) return false;

        // getPosAway finds a position that increases distance from the provided vector
        this.hidePos = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.targetPlayer.position());

        return this.hidePos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.hidePos.x, this.hidePos.y, this.hidePos.z, this.speedModifier);
    }
}