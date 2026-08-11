package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TruckEntity extends LandVehicleEntity {
    private static final List<VehicleHitboxPart> HITBOX_PARTS = List.of(
            new VehicleHitboxPart(
                    "front_left_wheel",
                    VehicleHitboxPart.VehicleHitboxType.WHEEL,
                    blockbenchOffset(13.0D, 0.0D, -22.0D),
                    blockbenchUnits(8.0D),
                    blockbenchUnits(10.0D),
                    blockbenchUnits(12.0D)
            ),
            new VehicleHitboxPart(
                    "front_right_wheel",
                    VehicleHitboxPart.VehicleHitboxType.WHEEL,
                    blockbenchOffset(-13.0D, 0.0D, -22.0D),
                    blockbenchUnits(8.0D),
                    blockbenchUnits(10.0D),
                    blockbenchUnits(12.0D)
            ),
            new VehicleHitboxPart(
                    "rear_left_wheel",
                    VehicleHitboxPart.VehicleHitboxType.WHEEL,
                    blockbenchOffset(13.0D, 0.0D, 22.0D),
                    blockbenchUnits(8.0D),
                    blockbenchUnits(10.0D),
                    blockbenchUnits(12.0D)
            ),
            new VehicleHitboxPart(
                    "rear_right_wheel",
                    VehicleHitboxPart.VehicleHitboxType.WHEEL,
                    blockbenchOffset(-13.0D, 0.0D, 22.0D),
                    blockbenchUnits(8.0D),
                    blockbenchUnits(10.0D),
                    blockbenchUnits(12.0D)
            ),
            new VehicleHitboxPart(
                    "front_bumper",
                    VehicleHitboxPart.VehicleHitboxType.BUMPER,
                    blockbenchOffset(0.0D, 5.6D, -38.0D),
                    blockbenchUnits(5.6D),
                    blockbenchUnits(8.8D),
                    blockbenchUnits(5.6D)
            ),
            new VehicleHitboxPart(
                    "rear_bumper",
                    VehicleHitboxPart.VehicleHitboxType.BUMPER,
                    blockbenchOffset(0.0D, 5.6D, 35.0D),
                    blockbenchUnits(5.6D),
                    blockbenchUnits(8.8D),
                    blockbenchUnits(5.6D)
            ),
            new VehicleHitboxPart(
                    "driver_seat",
                    VehicleHitboxPart.VehicleHitboxType.SEAT,
                    blockbenchOffset(0.0D, 10.4D, -7.2D),
                    blockbenchUnits(14.4D),
                    blockbenchUnits(14.4D),
                    blockbenchUnits(14.4D)
            ),
            new VehicleHitboxPart(
                    "rear_menu",
                    VehicleHitboxPart.VehicleHitboxType.MENU,
                    blockbenchOffset(0.0D, 11.0D, 17.5D),
                    blockbenchUnits(18.0D),
                    blockbenchUnits(16.0D),
                    blockbenchUnits(18.0D)
            )
    );

    public TruckEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    private static double blockbenchUnits(double units) {
        return units / 16.0D;
    }

    private static Vec3 blockbenchOffset(double x, double y, double z) {
        return new Vec3(
                -blockbenchUnits(x),
                blockbenchUnits(y),
                -blockbenchUnits(z)
        );
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.3D)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D);
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
        return new Vec3(0.0D, 0.25D, 0.45D);
    }

    @Override
    protected List<VehicleHitboxPart> getVehicleHitboxParts() {
        return HITBOX_PARTS;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.85D;
    }
}