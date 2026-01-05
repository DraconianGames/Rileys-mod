package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class HuntVanillaLandMobs<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public HuntVanillaLandMobs(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        super(pMob, pTargetType, 10, pMustSee, false, (target) -> {
              boolean isHuntableType =
                      target instanceof Sheep ||
                      target instanceof Pig ||
                      target instanceof Cow ||
                      target instanceof Chicken ||
                      target instanceof Camel ||
                      target instanceof Ravager ||
                      target instanceof Rabbit ||
                      target instanceof Endermite ||
                      target instanceof Silverfish||
                      target instanceof Allay ||
                      target instanceof Axolotl ||
                      target instanceof Cat||
                      target instanceof Stray||
                      target instanceof Creeper||
                      target instanceof Donkey||
                      target instanceof EnderMan||
                      target instanceof Evoker||
                      target instanceof Fox||
                      target instanceof Frog||
                      target instanceof Goat||
                      target instanceof Hoglin||
                      target instanceof Horse||
                      target instanceof IronGolem||
                      target instanceof Llama||
                      target instanceof Spider||
                      target instanceof Mule||
                      target instanceof Ocelot||
                      target instanceof Panda||
                      target instanceof Parrot||
                      target instanceof Piglin||
                      target instanceof PiglinBrute||
                      target instanceof Pillager||
                      target instanceof PolarBear||
                      target instanceof Shulker||
                      target instanceof SkeletonHorse||
                      target instanceof Slime||
                      target instanceof SnowGolem||
                      target instanceof Strider||
                      target instanceof Villager||
                      target instanceof Vindicator||
                      target instanceof WanderingTrader||
                      target instanceof Warden||
                      target instanceof Witch||
                      target instanceof WitherSkeleton||
                      target instanceof Zoglin||
                      target instanceof Zombie||
                      target instanceof ZombieHorse||
                      target instanceof Skeleton;

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
    @Override
    public void start() {
        // Run the normal target finding logic
        super.start();

        // If we found a target, alert nearby allies
        if (this.target != null) {
            this.alertOthers();
        }
    }

    protected void alertOthers() {
        // Define the range to search for allies (e.g., 30 blocks)
        double followRange = this.mob.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE);
        AABB searchArea = this.mob.getBoundingBox().inflate(followRange, 10.0D, followRange);

        // Find all mobs of the SAME type as the caller within that area
        List<? extends Mob> allies = this.mob.level().getEntitiesOfClass(this.mob.getClass(), searchArea);

        for (Mob ally : allies) {
            // Don't alert yourself, and only alert allies that don't already have a target
            if (this.mob != ally && ally.getTarget() == null) {
                ally.setTarget(this.target);
            }
        }
    }



}
