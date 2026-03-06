package net.riley.riley_mod.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;

import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class TrisonCartEntity extends AgeableMob {
    public TrisonCartEntity(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
//TODO make function like a cart
    // make follow tamed trison when equipped to trison. Steering thing must always face trison.
    //Shift left click to attach to trison.
    //Shift right click to access inventory
    //Right click to ride 1 of 2 passenger seats
    //Cover can be unequipped in menu
    //same goes for soul forge and lanterns and chests
    //cart can't be controlled by being in the cart, only by riding the trison.
//TODO fix rendering error when looking away from cart but still in it.
//TODO standing inside cart logic?
//TODO custom mob avoidance logic when soul forge is equipped
//TODO custom light logic when night and lanterns equipped
    //if too difficult, only when cart is not parked.
//TODO seating positions.
//TODO screen that shows cart inventory and attachments. Separate one for the chest inventory.
    //Make attachments disappear unless they are equipped.
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE,30D)
                .add(Attributes.MOVEMENT_SPEED, .6D)
                .add(Attributes.ARMOR_TOUGHNESS, .5f)
                .add(Attributes.ATTACK_KNOCKBACK,3f)
                .add(Attributes.ATTACK_DAMAGE,10f)
                .add(Attributes.JUMP_STRENGTH, 0.7D);

    }
}
