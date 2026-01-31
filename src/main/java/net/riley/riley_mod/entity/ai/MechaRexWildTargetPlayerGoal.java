package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

public class MechaRexWildTargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    private final MechaRexEntity rex;

    public MechaRexWildTargetPlayerGoal(MechaRexEntity rex) {
        super(rex, Player.class, true);
        this.rex = rex;
    }

    @Override
    public boolean canUse() {
        return !rex.isActivating() && !rex.isTame() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !rex.isTame() && super.canContinueToUse();
    }
}
