package net.riley.riley_mod.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.item.RileyModItems;
public class TrisonEntity extends BaseTrisonEntity {
    public TrisonEntity(EntityType<? extends BaseTrisonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Transform into a Sky Quadson when fed an Eye
        if (itemstack.is(RileyModItems.EYE.get()) && this.isTamed() && !this.isBaby()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide) {
                this.dropMountInventoryOnGround();
                // Send packet to server to delete the mount from PlayerPetData
                net.riley.riley_mod.network.RileyModPackets.sendToServer(
                        new net.riley.riley_mod.network.PetActionPacket(this.getUUID(), 2)
                );
                // Get the Sky Quadson type
                EntityType<?> skyQuadsonType = RileyModEntities.SKY_QUADSON.get();
                net.minecraft.world.entity.Entity skyQuadsonEntity = skyQuadsonType.create((ServerLevel) this.level());

                if (skyQuadsonEntity instanceof AgeableMob ageableMob) {
                    ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    ageableMob.setAge(-24000); // make the new Sky Quadson a baby
                    this.level().addFreshEntity(skyQuadsonEntity);
                    this.level().broadcastEntityEvent(skyQuadsonEntity, (byte) 7);
                    this.discard();
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    private void dropMountInventoryOnGround() {
        if (this.level().isClientSide) {
            return;
        }

        for (int i = 0; i < this.getMountInventory().getContainerSize(); i++) {
            ItemStack stack = this.getMountInventory().getItem(i);
            if (!stack.isEmpty()) {
                this.spawnAtLocation(stack);
                this.getMountInventory().setItem(i, ItemStack.EMPTY);
            }
        }

        this.getMountInventory().setChanged();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50D)
                .add(Attributes.FOLLOW_RANGE, 30D)
                .add(Attributes.MOVEMENT_SPEED, NORMAL_SPEED)
                .add(Attributes.ARMOR_TOUGHNESS, .5f)
                .add(Attributes.ATTACK_KNOCKBACK, 3f)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.JUMP_STRENGTH, 0.7D);
    }
}