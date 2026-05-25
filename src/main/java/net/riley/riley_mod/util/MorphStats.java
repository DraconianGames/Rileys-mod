package net.riley.riley_mod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;

import java.util.UUID;

public final class MorphStats {
    private MorphStats() {}

    private static final ResourceLocation PLAYER_MORPH =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "player");

    private static final ResourceLocation WHALE_HUNTER =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter");

    private static final ResourceLocation SUNLESS_CRAB =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "sunless_crab");

    private static final boolean ROUND_MORPH_HEALTH = true;

    private static final UUID MORPH_MAX_HEALTH_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000101");

    private static final UUID MORPH_MOVEMENT_SPEED_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000102");

    private static final UUID MORPH_ATTACK_DAMAGE_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000103");

    private static final UUID MORPH_ATTACK_KNOCKBACK_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000104");

    private static final String MORPH_MAX_HEALTH_NAME = "Morph max health";
    private static final String MORPH_MOVEMENT_SPEED_NAME = "Morph movement speed";
    private static final String MORPH_ATTACK_DAMAGE_NAME = "Morph attack damage";
    private static final String MORPH_ATTACK_KNOCKBACK_NAME = "Morph attack knockback";

    public static void apply(ServerPlayer player, ResourceLocation morphId) {
        float healthPercent = getHealthPercent(player);

        clear(player);

        if (PLAYER_MORPH.equals(morphId)) {
            applyHealthPercent(player, healthPercent);
            return;
        }

        EntityType<? extends LivingEntity> morphEntityType = getEntityTypeForMorph(morphId);
        if (morphEntityType == null) {
            applyHealthPercent(player, healthPercent);
            return;
        }

        LivingEntity morphEntity = morphEntityType.create(player.level());
        if (morphEntity == null) {
            applyHealthPercent(player, healthPercent);
            return;
        }

        copyAttributeFromMorph(
                player,
                morphEntity,
                Attributes.MAX_HEALTH,
                MORPH_MAX_HEALTH_ID,
                MORPH_MAX_HEALTH_NAME
        );

        copyAttributeFromMorph(
                player,
                morphEntity,
                Attributes.MOVEMENT_SPEED,
                MORPH_MOVEMENT_SPEED_ID,
                MORPH_MOVEMENT_SPEED_NAME
        );

        copyAttributeFromMorph(
                player,
                morphEntity,
                Attributes.ATTACK_DAMAGE,
                MORPH_ATTACK_DAMAGE_ID,
                MORPH_ATTACK_DAMAGE_NAME
        );

        copyAttributeFromMorph(
                player,
                morphEntity,
                Attributes.ATTACK_KNOCKBACK,
                MORPH_ATTACK_KNOCKBACK_ID,
                MORPH_ATTACK_KNOCKBACK_NAME
        );

        morphEntity.discard();
        applyHealthPercent(player, healthPercent);
    }

    public static void clear(ServerPlayer player) {
        removeModifier(player, Attributes.MAX_HEALTH, MORPH_MAX_HEALTH_ID);
        removeModifier(player, Attributes.MOVEMENT_SPEED, MORPH_MOVEMENT_SPEED_ID);
        removeModifier(player, Attributes.ATTACK_DAMAGE, MORPH_ATTACK_DAMAGE_ID);
        removeModifier(player, Attributes.ATTACK_KNOCKBACK, MORPH_ATTACK_KNOCKBACK_ID);
    }

    private static EntityType<? extends LivingEntity> getEntityTypeForMorph(ResourceLocation morphId) {
        if (WHALE_HUNTER.equals(morphId)) {
            return RileyModEntities.WHALE_HUNTER.get();
        }
        if (SUNLESS_CRAB.equals(morphId)) {
            return RileyModEntities.SUNLESS_CRAB.get();
        }

        return null;
    }

    private static void copyAttributeFromMorph(
            ServerPlayer player,
            LivingEntity morphEntity,
            Attribute attribute,
            UUID modifierId,
            String modifierName
    ) {
        AttributeInstance playerAttribute = player.getAttribute(attribute);
        AttributeInstance morphAttribute = morphEntity.getAttribute(attribute);

        if (playerAttribute == null || morphAttribute == null) {
            return;
        }

        double targetValue = morphAttribute.getValue();
        double currentPlayerValue = playerAttribute.getValue();
        double difference = targetValue - currentPlayerValue;

        if (Math.abs(difference) < 0.0001D) {
            return;
        }

        playerAttribute.addPermanentModifier(new AttributeModifier(
                modifierId,
                modifierName,
                difference,
                AttributeModifier.Operation.ADDITION
        ));
    }

    private static void removeModifier(ServerPlayer player, Attribute attribute, UUID modifierId) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) return;

        instance.removeModifier(modifierId);
    }

    private static float getHealthPercent(ServerPlayer player) {
        float maxHealth = player.getMaxHealth();

        if (maxHealth <= 0.0F) {
            return 1.0F;
        }

        return Math.max(0.0F, Math.min(1.0F, player.getHealth() / maxHealth));
    }

    private static void applyHealthPercent(ServerPlayer player, float healthPercent) {
        float maxHealth = player.getMaxHealth();
        float newHealth = maxHealth * Math.max(0.0F, Math.min(1.0F, healthPercent));

        if (ROUND_MORPH_HEALTH) {
            newHealth = Math.round(newHealth);
        }

        player.setHealth(Math.max(1.0F, Math.min(maxHealth, newHealth)));
    }
}