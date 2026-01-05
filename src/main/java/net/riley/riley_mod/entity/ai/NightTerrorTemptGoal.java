package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.TamableAnimal;

public class NightTerrorTemptGoal extends TemptGoal {
    private final double range;

    public NightTerrorTemptGoal(PathfinderMob pMob, double pSpeedModifier, Ingredient pItems, boolean pCanScare, double range) {
        super(pMob, pSpeedModifier, pItems, pCanScare);
        this.range = range;
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof TamableAnimal tamable && tamable.isTame()) {
            return false;
        }
        // Standard TemptGoal uses a hardcoded 10.0D check in its internal logic.
        // We override the player search here.
        Player player = this.mob.level().getNearestPlayer(this.mob, this.range);
        if (player == null) return false;

        // This is a bit of a hack to make the parent class use our player
        // since the parent's field is private.
        return super.canUse();
    }
}
