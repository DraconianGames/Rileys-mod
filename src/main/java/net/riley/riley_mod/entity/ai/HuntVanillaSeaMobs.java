package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Guardian;

public class HuntVanillaSeaMobs<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public HuntVanillaSeaMobs(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, 10, pMustSee, false, (target) -> {
            boolean isHuntableType =  target instanceof Axolotl||
                    target instanceof Cod||
                    target instanceof Dolphin||
                    target instanceof Drowned||
                    target instanceof Squid||
                    target instanceof Guardian||
                    target instanceof Pufferfish||
                    target instanceof Salmon||
                    target instanceof Tadpole||
                    target instanceof TropicalFish||
                    target instanceof Turtle||
                    target instanceof Frog;


            if (target instanceof AgeableMob ageable) {
                return isHuntableType && !ageable.isBaby();
            }

            return isHuntableType;

        });
    }
    @Override
    public boolean canUse() {
        // If the attacker is tamable and already tamed, don't start the hunt goal
        if (this.mob instanceof TamableAnimal tamable && tamable.isTame()) {
            return false;
        }
        return super.canUse();
    }
}
