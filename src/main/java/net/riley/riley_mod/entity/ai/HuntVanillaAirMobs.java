package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Phantom;

public class HuntVanillaAirMobs <T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public HuntVanillaAirMobs(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, 10, pMustSee, false, (target) -> {
            boolean isHuntableType =  target instanceof Allay||
                    target instanceof Bat||
                    target instanceof Bee||
                    target instanceof Blaze||
                    target instanceof EnderDragon||
                    target instanceof Ghast||
                    target instanceof Parrot||
                    target instanceof Phantom;


            return isHuntableType && (target instanceof AgeableMob ageable && !ageable.isBaby());

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