package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TruckEntity extends BaseVehicleEntity {
    public TruckEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }
    private static double bb(double units) {
        return units / 16.0D;
    }

    private static Vec3 bbVec(double blockbenchX, double blockbenchY, double blockbenchZ) {
        return new Vec3(
                -bb(blockbenchX),
                bb(blockbenchY),
                -bb(blockbenchZ)
        );
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
    protected List<VehicleHitboxPart> getVehicleHitboxParts() {
        return List.of(
                new VehicleHitboxPart(
                        "front_left_wheel",
                        VehicleHitboxPart.VehicleHitboxType.WHEEL,
                        bbVec(-12.5D, 0.0D, -26.0D),
                        bb(10.0D),//X size
                        bb(10.0D),//Y size
                        bb(10.0D)//Z size
                ),
                new VehicleHitboxPart(
                        "front_right_wheel",
                        VehicleHitboxPart.VehicleHitboxType.WHEEL,
                        bbVec(-12.5D, 0.0D, 26.0D),
                        bb(10.0D),
                        bb(10.0D),
                        bb(10.0D)
                ),
                new VehicleHitboxPart(
                        "rear_left_wheel",
                        VehicleHitboxPart.VehicleHitboxType.WHEEL,
                        bbVec(12.5D, 0.0D, -26.0D),
                        bb(10.0D),
                        bb(10.0D),
                        bb(10.0D)
                ),
                new VehicleHitboxPart(
                        "rear_right_wheel",
                        VehicleHitboxPart.VehicleHitboxType.WHEEL,
                        bbVec(12.5D, 0.0D, 26.0D),
                        bb(10.0D),
                        bb(10.0D),
                        bb(10.0D)
                ),
                new VehicleHitboxPart(
                        "front_bumper",
                        VehicleHitboxPart.VehicleHitboxType.BUMPER,
                        bbVec(0.0D, 5.6D, -38.0D),
                        bb(30.4D),
                        bb(8.8D),
                        bb(5.6D)
                ),
                new VehicleHitboxPart(
                        "rear_bumper",
                        VehicleHitboxPart.VehicleHitboxType.BUMPER,
                        bbVec(0.0D, 5.6D, 35.0D),
                        bb(30.4D),
                        bb(8.8D),
                        bb(5.6D)
                ),
                new VehicleHitboxPart(
                        "driver_seat",
                        VehicleHitboxPart.VehicleHitboxType.SEAT,
                        bbVec(0.D, 10.4D, -7.2D),
                        bb(14.4D),
                        bb(14.4D),
                        bb(14.4D)
                ),
                new VehicleHitboxPart(
                        "rear_menu",
                        VehicleHitboxPart.VehicleHitboxType.MENU,
                        bbVec(0.0D, 11.0D,17.5D ),
                        bb(18.0D),
                        bb(16.0D),
                        bb(35.0D)
                )
        );
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.85D;
    }
}
//todo add screen to summon truck/other things