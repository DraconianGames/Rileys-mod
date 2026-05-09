package net.riley.riley_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.MechaParasiteEntity;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

public class MechanicalParasiteEffect extends MobEffect {
    protected MechanicalParasiteEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level level = pLivingEntity.level();

        if (!level.isClientSide()) {
            pacifyAndFollowCarrier(pLivingEntity);

            MobEffectInstance effectInstance = pLivingEntity.getEffect(RileyModEffects.MECHANICAL_PARASITE.get());

            if (effectInstance != null && effectInstance.getDuration() <= 1) {
                EntityType<MechaParasiteEntity> type = RileyModEntities.MECHA_PARASITE.get();
                MechaParasiteEntity parasite = type.create(level);

                if (parasite != null) {
                    parasite.moveTo(
                            pLivingEntity.getX(),
                            pLivingEntity.getY(),
                            pLivingEntity.getZ(),
                            pLivingEntity.getYRot(),
                            0.0F
                    );

                    parasite.setAge(-24000);
                    level.addFreshEntity(parasite);
                }

                if (pLivingEntity.getMaxHealth() <= 20.0F) {
                    pLivingEntity.hurt(pLivingEntity.damageSources().magic(), Float.MAX_VALUE);
                } else {
                    float halfHealth = pLivingEntity.getMaxHealth() / 2.0F;
                    pLivingEntity.hurt(pLivingEntity.damageSources().magic(), halfHealth);
                }
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    private void pacifyAndFollowCarrier(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Mob mob)) {
            return;
        }

        mob.setTarget(null);
        mob.setLastHurtByMob(null);
        mob.setLastHurtMob(null);

        double followRange = 32.0D;

        ParasiteCarrierEntity carrier = mob.level().getEntitiesOfClass(
                        ParasiteCarrierEntity.class,
                        mob.getBoundingBox().inflate(followRange),
                        entity -> entity.isAlive() && entity != mob
                )
                .stream()
                .min((first, second) -> Double.compare(mob.distanceToSqr(first), mob.distanceToSqr(second)))
                .orElse(null);

        if (carrier == null) {
            mob.getNavigation().stop();
            return;
        }

        mob.getLookControl().setLookAt(carrier, 30.0F, 30.0F);

        double stopDistance = 7.0D;

        if (mob.distanceToSqr(carrier) > stopDistance * stopDistance) {
            mob.getNavigation().moveTo(carrier, 1.1D);
        } else {
            mob.getNavigation().stop();
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}