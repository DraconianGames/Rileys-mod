package net.riley.riley_mod.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

import java.util.EnumSet;
import java.util.UUID;

public class ParasiteCarrierShareTargetWithAlliesGoal extends Goal {
    private final ParasiteCarrierEntity rex;
    private final double radius;
    private int cooldown = 0;

    public ParasiteCarrierShareTargetWithAlliesGoal(ParasiteCarrierEntity rex, double radius) {
        this.rex = rex;
        this.radius = radius;
        this.setFlags(EnumSet.noneOf(Flag.class));
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return true;
    }

    @Override
    public void tick() {
        if (rex.level().isClientSide) return;
        if (cooldown-- > 0) return;
        cooldown = 10; // every 0.5s, keeps it stable

        if (!rex.isTame()) return;
        UUID ownerUUID = rex.getOwnerUUID();
        if (ownerUUID == null) return;

        LivingEntity target = rex.getTarget();
        if (target == null || !target.isAlive()) return;

        // Do not share friendly targets (extra safety)
        if (target instanceof ParasiteCarrierEntity otherRex && ownerUUID.equals(otherRex.getOwnerUUID())) return;

        for (ParasiteCarrierEntity ally : rex.level().getEntitiesOfClass(
                ParasiteCarrierEntity.class,
                rex.getBoundingBox().inflate(radius),
                a -> a != rex && a.isAlive() && a.isTame() && ownerUUID.equals(a.getOwnerUUID())
        )) {
            // Don't override an ally that's currently activating or sitting
            if (ally.isActivating()) continue;
            if (ally.isOrderedToSit() || ally.isInSittingPose()) continue;

            // If ally already has a live target, keep it (optional)
            LivingEntity allyTarget = ally.getTarget();
            if (allyTarget != null && allyTarget.isAlive()) continue;

            ally.setTarget(target);
        }
    }
}