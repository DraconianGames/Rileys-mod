package net.riley.riley_mod.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.riley.riley_mod.entity.custom.NightTerrorEntity;


public class NightTerrorAttackGoal extends MeleeAttackGoal {

    private final NightTerrorEntity entity;
    private int attackDelay = 12;
    private int ticksUntilNextAttack= 3;
    private boolean shouldCountTillNextAttack = false;
    public NightTerrorAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity =((NightTerrorEntity) pMob);
    }
    @Override
    public void start() {
        super.start();
        attackDelay = 12;
        ticksUntilNextAttack = 3;
    }
    @Override
    public boolean canUse() {
        // Prevent babies from attacking
        return !entity.isBaby() && super.canUse();
    }
    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if (isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAminationTimeout = 0;
            entity.flyAttackAnimationState.stop();
        }
    }
    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy, double pDistToEnemySqr) {
        return pDistToEnemySqr <= this.getAttackReachSqr(pEnemy);
    }
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay * 1);

    }
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }
    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }
    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }
    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1,0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
