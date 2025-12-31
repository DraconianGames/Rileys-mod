package net.riley.riley_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.custom.RileyModFlammableRotatedPillarBlock;
import net.riley.riley_mod.block.custom.RileyModPortalBlock;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.worldgen.tree.AbyssTreeGrower;

import java.util.function.Supplier;

public class RileyModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RileyMod.MODID);
//blocks
    public static final RegistryObject<Block> FUNTIUM_BLOCK = registerBlock("funtium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ACTIVACTED_FUNTIUM = registerBlock("activacted_funtium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(60).explosionResistance(9000)));
    public static final RegistryObject<Block> STRUCTURE_BRICK = registerBlock("structure_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(60).explosionResistance(9000)));

//ores
    public static final RegistryObject<Block> FUNTIUM_ORE_BLOCK = registerBlock("funtium_ore_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(60).explosionResistance(9000).requiresCorrectToolForDrops(), UniformInt.of(7,9)));
    public static final RegistryObject<Block> DEEPSLATE_FUNTIUM_ORE = registerBlock("deepslate_funtium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(60).explosionResistance(9000).requiresCorrectToolForDrops(), UniformInt.of(7,9)));
//wood
    public static final RegistryObject<Block> ABYSS_LOG = registerBlock("abyss_log",
            () -> new RileyModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> ABYSS_WOOD = registerBlock("abyss_wood",
            () -> new RileyModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_ABYSS_LOG = registerBlock("stripped_abyss_log",
            () -> new RileyModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_ABYSS_WOOD = registerBlock("stripped_abyss_wood",
            () -> new RileyModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
//leaves+planks
public static final RegistryObject<Block> ABYSS_PLANKS = registerBlock("abyss_planks",
        () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return 20;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return 5;
            }
        });
    public static final RegistryObject<Block> ABYSS_LEAVES = registerBlock("abyss_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });
    public static final RegistryObject<Block> ABYSS_WOOD_STAIRS = registerBlock("abyss_wood_stairs",
            () -> new StairBlock(() -> RileyModBlocks.ABYSS_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ABYSS_WOOD_SLAB = registerBlock("abyss_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> ABYSS_SAPLING = registerBlock("abyss_sapling",
            () -> new SaplingBlock(new AbyssTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> ABYSS_WOOD_FENCE = registerBlock("abyss_wood_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ABYSS_WOOD_FENCE_GATE = registerBlock("abyss_wood_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
    public static final RegistryObject<Block> ABYSS_WOOD_WALL = registerBlock("abyss_wood_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> STRUCTURE_BRICK_STAIRS = registerBlock("structure_brick_stairs",
            () -> new StairBlock(() -> RileyModBlocks.ABYSS_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> STRUCTURE_BRICK_SLAB = registerBlock("structure_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> STRUCTURE_BRICK_FENCE = registerBlock("structure_brick_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.NETHER_BRICKS)));
    public static final RegistryObject<Block> STRUCTURE_BRICK_FENCE_GATE = registerBlock("structure_brick_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.NETHER_BRICKS), SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
    public static final RegistryObject<Block> STRUCTURE_BRICK_WALL = registerBlock("structure_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.NETHER_BRICKS)));


    public static final RegistryObject<Block> ABYSS_PORTAL = registerBlock("abyss_portal",
            () -> new RileyModPortalBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noLootTable().noOcclusion().noCollission()));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;


    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return RileyModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
