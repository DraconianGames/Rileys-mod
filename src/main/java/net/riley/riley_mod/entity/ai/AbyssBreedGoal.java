package net.riley.riley_mod.entity.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;


public class AbyssBreedGoal extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight();
    protected final AgeableMob mob; // Changed from PathfinderMob
        protected final Level level;
        protected final Class<? extends AgeableMob> partnerClass; // Changed
        @Nullable
        protected AgeableMob partner; // Changed
        private int loveTick;
    private final double speedModifier;
    private final Ingredient foodItems;

        public AbyssBreedGoal(AgeableMob pMob, double pSpeedModifier, Ingredient pFoodItems) {
            this(pMob, pSpeedModifier, pMob.getClass(), pFoodItems);
        }

        public AbyssBreedGoal(AgeableMob pMob, double pSpeedModifier, Class<? extends AgeableMob> pPartnerClass, Ingredient pFoodItems) {
            this.mob = pMob;
        this.level = pMob.level();
        this.partnerClass = pPartnerClass;
        this.speedModifier = pSpeedModifier;
        this.foodItems = pFoodItems;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Broaden check: age >= 0 means adult.
        if (this.mob.getAge() < 0 || this.mob.getPersistentData().getInt("BreedCooldown") > 0 || !this.isInLove()) {
            return false;
        }

        this.partner = this.getFreePartner();
        return this.partner != null;
    }

    @Override
    public boolean canContinueToUse() {
        // Keep the goal running as long as both are in love and alive
        return this.partner != null && this.partner.isAlive() && this.isInLove() && this.isEntityInLove(this.partner) && this.mob.getAge() >= 0;
    }


    @Override
    public void stop() {
        this.partner = null;
        this.loveTick = 0;
    }

    @Override
    public void tick() {
        if (this.partner == null || !this.partner.isAlive() || !this.isEntityInLove(this.partner)) {
            this.stop();
            return;
        }

        this.mob.getLookControl().setLookAt(this.partner, 10.0F, (float) this.mob.getMaxHeadXRot());
        this.mob.getNavigation().moveTo(this.partner, this.speedModifier);

        ++this.loveTick;
        // Large crabs need a generous distance check. 9.0D is about 3 blocks.
        if (this.loveTick >= 20 && this.mob.distanceToSqr(this.partner) < 20.0D) {
            this.breed();
        }
    }
    @Nullable
    private AgeableMob getFreePartner() {
        List<? extends AgeableMob> list = this.level.getEntitiesOfClass(this.partnerClass, this.mob.getBoundingBox().inflate(16.0D), (entity) -> {
            return entity != this.mob && this.isEntityInLove(entity) && entity.getAge() >= 0;
        });

        double d0 = Double.MAX_VALUE;
        AgeableMob partnerFound = null;

        for (AgeableMob potentialPartner : list) {
            if (this.mob.distanceToSqr(potentialPartner) < d0) {
                partnerFound = potentialPartner;
                d0 = this.mob.distanceToSqr(potentialPartner);
            }
        }
        return partnerFound;
    }

    private boolean isInLove() {
        // We will need to implement a 'love' check since PathfinderMob doesn't have one
        return this.mob.getPersistentData().getInt("InLove") > 0;
    }

        private boolean isEntityInLove(AgeableMob entity) {
            return entity.getPersistentData().getInt("InLove") > 0;
        }

    protected void breed() {
        if (this.level instanceof ServerLevel serverLevel) {
            // Check partner again to be safe
            if (this.partner == null) return;

            AgeableMob baby = this.mob.getBreedOffspring(serverLevel, this.partner);
            if (baby != null) {
                // If parents are tamed, make the baby tamed to the same owner
                if (this.mob instanceof TamableAnimal tamableParent && tamableParent.isTame()) {
                    if (baby instanceof TamableAnimal tamableBaby) {
                        tamableBaby.setOwnerUUID(tamableParent.getOwnerUUID());
                        tamableBaby.setTame(true);
                    }
                }
                baby.setAge(-24000);
                // Position the baby at the parent's location
                baby.moveTo(this.mob.getX(), this.mob.getY(), this.mob.getZ(), 0.0F, 0.0F);

                serverLevel.addFreshEntityWithPassengers(baby);

                // Visual confirmation
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HEART,
                        this.mob.getX(), this.mob.getY() + 1.5D, this.mob.getZ(), 10, 0.5D, 0.5D, 0.5D, 0.0D);

                // Clear love state and set cooldown
                this.mob.getPersistentData().putInt("InLove", 0);
                this.partner.getPersistentData().putInt("InLove", 0);
                this.mob.getPersistentData().putInt("BreedCooldown", 6000);
                this.partner.getPersistentData().putInt("BreedCooldown", 6000);

                // Force a sync or stop the goal to ensure the server updates the client
                this.stop();
            }
        }
    }
    }
