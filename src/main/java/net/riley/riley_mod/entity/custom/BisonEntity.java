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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.ai.AbyssBreedGoal;
import org.jetbrains.annotations.Nullable;

public class BisonEntity extends AbstractInventoryMountEntity {

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
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AbyssBreedGoal(this, 1.0D, Ingredient.of(Items.CARROT)));
        this.goalSelector.addGoal(3,new TemptGoal(this,1D, Ingredient.of(Items.CARROT),false));

    }

    @Override
    protected int getBaseStorageColumns() {
        return 5;
    }

    @Override
    protected int getBaseStorageRows() {
        return 3;
    }

    @Override
    public boolean canEquipMountArmor(ItemStack stack) {
        return false;
    }
    /**
     * Determines which Trison variant to transform into based on the current biome.
     */
    //todo fix transformation deleating entries in the pet screen again
    private EntityType<?> getTrisonTypeForBiome() {
        var biomeKey = this.level().getBiome(this.blockPosition()).unwrapKey();

        if (biomeKey.isEmpty()) {
            return RileyModEntities.TRISON.get();
        }

        String biomePath = biomeKey.get().location().toString();

        // Check for Nether biomes - all nether biomes contain these keywords
        if (biomePath.contains("crimson") || biomePath.contains("warped") ||
                biomePath.contains("nether_wastes") || biomePath.contains("soul_sand") ||
                biomePath.contains("basalt")) {
            return RileyModEntities.NETHER_TRISON.get();
        }

        // Check for End biomes (all vanilla end biomes)
        if (biomePath.contains("end_barrens") || biomePath.contains("the_end") ||
                biomePath.contains("end_midlands") || biomePath.contains("end_highlands") ||
                biomePath.contains("small_end_islands")) {
            return RileyModEntities.END_TRISON.get();
        }

        // Check for mountain
        if (biomePath.contains("frozen_peaks") || biomePath.contains("jagged_peaks") || biomePath.contains("stony_peaks")) {
            return RileyModEntities.MOUNTAIN_TRISON.get();
        }

        // Check for abyss biomes (your custom abyss biomes)
        if (biomePath.contains("abyss") || biomePath.contains("deep_dark")) {
            return RileyModEntities.ABYSS_TRISON.get();
        }

        // Check for fallow/dead/barren biomes (your custom fallow biomes)
        if (biomePath.contains("fallow")) {
            return RileyModEntities.FALLOW_TRISON.get();
        }

        return RileyModEntities.TRISON.get();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        // Chest-on-like-a-mule behavior (explicit, reliable)
        if (itemstack.is(Items.CHEST) && this.isTamed() && !this.isBaby() && !this.hasChest()) {
            if (!this.level().isClientSide) {
                this.setChest(true);
                this.createInventory(); // expands inventory for chested horses
                if (!pPlayer.getAbilities().instabuild) itemstack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

// Transform into appropriate Trison variant when fed a Golden Apple
        if (itemstack.is(Items.GOLDEN_APPLE) && this.isTamed() && !this.isBaby()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide) {
                this.dropMountInventoryOnGround();
                // Send packet to server to delete the mount from PlayerPetData
                net.riley.riley_mod.network.RileyModPackets.sendToServer(
                        new net.riley.riley_mod.network.PetActionPacket(this.getUUID(), 2)
                );
                // Get the appropriate Trison type based on current biome
                EntityType<?> trisonType = getTrisonTypeForBiome();
                net.minecraft.world.entity.Entity trisonEntity = trisonType.create((ServerLevel) this.level());

                if (trisonEntity instanceof AgeableMob ageableMob) {
                    ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    ageableMob.setAge(-24000); // make the new Trison a baby
                    this.level().addFreshEntity(trisonEntity);
                    this.level().broadcastEntityEvent(trisonEntity, (byte) 7);
                    this.discard();
                }
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

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
                .add(Attributes.ATTACK_DAMAGE,10f)
                .add(Attributes.JUMP_STRENGTH, 0.7D);

    }
    @Override
    public double getPassengersRidingOffset() {
        // Raise the rider; tweak this number until it looks right.
        return super.getPassengersRidingOffset() + 0.35D;
    }
    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return RileyModEntities.BISON.get().create(pLevel);
    }



}
