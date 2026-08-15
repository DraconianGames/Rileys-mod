package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
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
 * The class caches the spawn-egg colors (primary = index 0, secondary = index 1) and prints them.
 */
public class SpawnEggTrophy extends Block {
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    private final EntityType<?> entityType;
    private final int primaryColor;
    private final int secondaryColor;

    public SpawnEggTrophy(Properties properties, EntityType<?> type) {
        super(properties);
        this.entityType = type;
        int[] cols = fetchEggColors(type);
        this.primaryColor = cols[0];
        this.secondaryColor = cols[1];

        // Debug print so you can see what colors were calculated in the console
        // ForgeRegistries.ENTITY_TYPES.getKey(type) gives the registry name (may be null in some edge cases)
        String id = String.valueOf(ForgeRegistries.ENTITY_TYPES.getKey(type));
        System.out.println(String.format("SpawnEggTrophy constructed for %s -> primary=0x%06X secondary=0x%06X",
                id, primaryColor & 0xFFFFFF, secondaryColor & 0xFFFFFF));
    }

    /**
     * Search the item registry for a SpawnEggItem bound to the requested EntityType.
     * This is necessary because there is no public static getEgg(entityType) in mappings.
     */
    private static int[] fetchEggColors(EntityType<?> type) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof SpawnEggItem egg) {
                if (egg.getType(null) == type) {
                    return new int[] { egg.getColor(0), egg.getColor(1) };
                }
            }
        }
        return new int[] { 0xFFFFFF, 0x000000 }; // fallback
    }

    public EntityType<?> getEntityType() { return entityType; }
    public int getPrimaryColor() { return primaryColor; }
    public int getSecondaryColor() { return secondaryColor; }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}