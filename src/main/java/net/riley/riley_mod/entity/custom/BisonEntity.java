package net.riley.riley_mod.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import org.jetbrains.annotations.Nullable;
public class BisonEntity extends AbstractChestedHorse {

    public BisonEntity(EntityType<? extends BisonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAminationTimeout = 0;


    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide) {
            if (this.getPersistentData().getInt("InLove") > 0 && this.random.nextInt(7) == 0) {
                this.level().addParticle(net.minecraft.core.particles.ParticleTypes.HEART,
                        this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            }
        } else {
            int inLove = this.getPersistentData().getInt("InLove");
            if (inLove > 0) {
                this.getPersistentData().putInt("InLove", inLove - 1);
            }

            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) {
                this.getPersistentData().putInt("BreedCooldown", cooldown - 1);
            }
        }
    }
    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAminationStates();
        }
    }
    private void setupAminationStates() {
        if(this.idleAminationTimeout <- 0) {
            this.idleAminationTimeout = this.random.nextInt(20) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAminationTimeout;
        }
    }
    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose()== Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        }else{
            f=0f;
        }
        this.walkAnimation.update(f,.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(Items.CARROT)));
        this.goalSelector.addGoal(3,new TemptGoal(this,1D, Ingredient.of(Items.CARROT),false));

    }
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (this.isFood(itemstack)) {
            int cooldown = this.getPersistentData().getInt("BreedCooldown");
            if (cooldown > 0) return InteractionResult.PASS;

            if (this.getPersistentData().getInt("InLove") <= 0) {
                if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);
                this.getPersistentData().putInt("InLove", 600);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(pPlayer, pHand);
    }
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.CARROT); // Or whatever food you prefer
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.FOLLOW_RANGE,30D)
                .add(Attributes.MOVEMENT_SPEED, .3D)
                .add(Attributes.ARMOR_TOUGHNESS, .3f)
                .add(Attributes.ATTACK_KNOCKBACK,3f)
                .add(Attributes.ATTACK_DAMAGE,10f);
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.BISON.get().create(pLevel);
    }
    //TODO Adjust rider position
    //TODO add chest and saddle logic
    /*
     protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
      int i = this.getPassengers().indexOf(pPassenger);
      if (i >= 0) {
         boolean flag = i == 0;
         float f = 0.5F;
         float f1 = (float)(this.isRemoved() ? (double)0.01F : this.getBodyAnchorAnimationYOffset(flag, 0.0F) + pPassenger.getMyRidingOffset());
         if (this.getPassengers().size() > 1) {
            if (!flag) {
               f = -0.7F;
            }

            if (pPassenger instanceof Animal) {
               f += 0.2F;
            }
         }

         Vec3 vec3 = (new Vec3(0.0D, 0.0D, (double)f)).yRot(-this.yBodyRot * ((float)Math.PI / 180F));
         pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + (double)f1, this.getZ() + vec3.z);
         this.clampRotation(pPassenger);
      }
   }
    */

}
