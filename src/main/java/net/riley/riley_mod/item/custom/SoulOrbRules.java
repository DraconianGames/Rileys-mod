
/*
package net.riley.riley_mod.item.custom;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

import java.util.UUID;

public final class SoulOrbRules {
    private SoulOrbRules() {
    }

    public static boolean isBlacklisted(Player player, LivingEntity target) {
        if (target instanceof ArmorStand) {
            return true;
        }

        if (target instanceof TamableAnimal tamable && tamable.isTame()) {
            UUID ownerUUID = tamable.getOwnerUUID();
            return ownerUUID == null || !ownerUUID.equals(player.getUUID());
        }

        if (target instanceof OwnableEntity ownable) {
            UUID ownerUUID = ownable.getOwnerUUID();
            return ownerUUID == null || !ownerUUID.equals(player.getUUID());
        }

        return false;
    }

    public static boolean needsLowHealth(LivingEntity target) {
        return target instanceof EnderDragon ||
                target instanceof MechaRexEntity;
    }

    public static boolean canCapture(Player player, LivingEntity target) {
        if (isBlacklisted(player, target)) {
            return false;
        }

        if (needsLowHealth(target) && target.getHealth() >= 20.0F) {
            return false;
        }

        return true;
    }
}
*/