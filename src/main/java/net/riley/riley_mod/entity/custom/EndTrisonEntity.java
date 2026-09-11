package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class EndTrisonEntity extends TrisonEntity{
    public EndTrisonEntity(EntityType<? extends TrisonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, NORMAL_SPEED)
                .add(Attributes.ARMOR_TOUGHNESS, .8f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.ATTACK_DAMAGE, 15f)
                .add(Attributes.JUMP_STRENGTH, 1.0D);
    }
}
