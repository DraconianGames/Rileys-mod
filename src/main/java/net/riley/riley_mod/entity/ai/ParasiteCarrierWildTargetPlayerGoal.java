package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.riley.riley_mod.entity.custom.MechaRexEntity;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

public class ParasiteCarrierWildTargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    private final ParasiteCarrierEntity rex;

    public ParasiteCarrierWildTargetPlayerGoal(ParasiteCarrierEntity rex) {
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
