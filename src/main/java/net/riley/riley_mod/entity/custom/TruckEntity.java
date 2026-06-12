package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TruckEntity extends BaseVehicleEntity {
    public TruckEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ARMOR_TOUGHNESS, .3F)
                .add(Attributes.ATTACK_KNOCKBACK, 3F)
                .add(Attributes.ATTACK_DAMAGE, 10F);
    }

    @Override
    protected float getVehicleStepHeight() {
        return 1.2F;
    }


    @Override
    protected int getMaxPassengers() {
        return 1;
    }

    @Override
    protected Vec3 getSeatOffset(int passengerIndex) {
        return new Vec3(0.0D, 0.45D, 0.45D);
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.85D;
    }
}
//todo add screen to summon truck/other things