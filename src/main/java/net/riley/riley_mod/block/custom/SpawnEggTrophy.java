package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Base class for a single-mob trophy. Construct with the EntityType this trophy represents.
 * The class caches the spawn-egg colors (primary = index 0, secondary = index 1).
 */
public class SpawnEggTrophy extends Block {
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    private final EntityType<?> entityType;
    private final int primaryColor;
    private final int secondaryColor;

    public SpawnEggTrophy(Properties properties, EntityType<?> type) {
        super(properties);
        this.entityType = type;
        int[] colors = fetchEggColors(type);
        this.primaryColor = colors[0];
        this.secondaryColor = colors[1];
    }

    /**
     * Search the item registry for a SpawnEggItem bound to the requested EntityType.
     * This is necessary because there is no public static getEgg(entityType) in mappings.
     */
    private static int[] fetchEggColors(EntityType<?> type) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof SpawnEggItem egg) {
                // egg.getType(null) returns the EntityType associated with this egg (pass null NBT)
                if (egg.getType(null) == type) {
                    // getColor(0) = base, getColor(1) = spots
                    return new int[]{egg.getColor(0), egg.getColor(1)};
                }
            }
        }
        // fallback if no egg is registered for this entity
        return new int[]{0xFFFFFF, 0x000000};
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}