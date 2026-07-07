package net.riley.riley_mod.entity.custom;

import net.minecraft.world.phys.Vec3;

public record VehicleHitboxPart(
        String name,
        VehicleHitboxType type,
        Vec3 offset,
        double width,
        double height,
        double depth
) {
    public enum VehicleHitboxType {
        WHEEL,
        BUMPER,
        SEAT,
        MENU
    }
}