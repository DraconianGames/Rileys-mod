package net.riley.riley_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class MountainTrisonEntity extends TrisonEntity{
    public MountainTrisonEntity(EntityType<? extends TrisonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public boolean canFreeze() {
        return false;
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, NORMAL_SPEED)
                .add(Attributes.ARMOR_TOUGHNESS, .8f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.ATTACK_DAMAGE, 15f)
                .add(Attributes.JUMP_STRENGTH, 0.9D);
    }

}
