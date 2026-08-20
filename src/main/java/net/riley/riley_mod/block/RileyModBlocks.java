package net.riley.riley_mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.custom.*;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.item.custom.NightStarBlockItem;
import net.riley.riley_mod.worldgen.tree.AbyssTreeGrower;

import java.util.function.Supplier;
//todo make method of gaining bluestone
public class RileyModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RileyMod.MODID);
    //CROPS
    public static final RegistryObject<Block> MUSCLE_CROP = registerBlock("muscle_crop",
            () -> new MuscleCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));
//blocks
    public static final RegistryObject<Block> FUNTIUM_BLOCK = registerBlock("funtium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ACTIVACTED_FUNTIUM = registerBlock("activacted_funtium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(60).explosionResistance(9000)));
    public static final RegistryObject<Block> STRUCTURE_BRICK = registerBlock("structure_brick",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).strength(60).explosionResistance(9000)));
    public static final RegistryObject<Block> ABYSSAL_STONE = registerBlock("abyssal_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> ABYSSAL_COBBLESTONE = registerBlock("abyssal_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> BLACK_SAND = registerBlock("black_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SOUL_SAND)));
    public static final RegistryObject<Block> NIGHT_STAR = BLOCKS.register("night_star",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).lightLevel((state) -> 15)));
    public static final RegistryObject<Block> FALLOW_GROUND = registerBlock("fallow_ground",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> FALLOW_EARTH = registerBlock("fallow_earth",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> FALLOW_PORTAL_FRAME = registerBlock("fallow_portal_frame",
            () -> new Block(BlockBehaviour.Properties.copy(RileyModBlocks.ACTIVACTED_FUNTIUM.get())));
//SPAWNER
    public static final RegistryObject<Block> SPECIAL_SPAWNER = registerBlock("special_spawner",
            () -> new SpecialSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    //trophies
public static final RegistryObject<Block> WHALE_HUNTER_TROPHY = registerBlock("whale_hunter_trophy",
        () -> new WhaleHunterTrophyBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));


    public static final RegistryObject<Block> TROPHY_BAT = registerBlock("trophy_bat", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG).noParticlesOnBreak(), EntityType.BAT));
    public static final RegistryObject<Block> TROPHY_ENDER_DRAGON = registerBlock("trophy_ender_dragon", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ENDER_DRAGON));
    public static final RegistryObject<Block> TROPHY_ALLAY = registerBlock("trophy_allay", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ALLAY));
    public static final RegistryObject<Block> TROPHY_AXOLOTL = registerBlock("trophy_axolotl", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.AXOLOTL));
    public static final RegistryObject<Block> TROPHY_BEE = registerBlock("trophy_bee", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.BEE));
    public static final RegistryObject<Block> TROPHY_BLAZE = registerBlock("trophy_blaze", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.BLAZE));
    public static final RegistryObject<Block> TROPHY_CAT = registerBlock("trophy_cat", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.CAT));
    public static final RegistryObject<Block> TROPHY_CAMEL = registerBlock("trophy_camel", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.CAMEL));
    public static final RegistryObject<Block> TROPHY_CAVE_SPIDER = registerBlock("trophy_cave_spider", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.CAVE_SPIDER));
    public static final RegistryObject<Block> TROPHY_CHICKEN = registerBlock("trophy_chicken", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.CHICKEN));
    public static final RegistryObject<Block> TROPHY_COD = registerBlock("trophy_cod", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.COD));
    public static final RegistryObject<Block> TROPHY_COW = registerBlock("trophy_cow", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.COW));
    public static final RegistryObject<Block> TROPHY_CREEPER = registerBlock("trophy_creeper", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.CREEPER));
    public static final RegistryObject<Block> TROPHY_DOLPHIN = registerBlock("trophy_dolphin", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.DOLPHIN));
    public static final RegistryObject<Block> TROPHY_DONKEY = registerBlock("trophy_donkey", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.DONKEY));
    public static final RegistryObject<Block> TROPHY_DROWNED = registerBlock("trophy_drowned", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.DROWNED));
    public static final RegistryObject<Block> TROPHY_ELDER_GUARDIAN = registerBlock("trophy_elder_guardian", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ELDER_GUARDIAN));
    public static final RegistryObject<Block> TROPHY_ENDERMAN = registerBlock("trophy_enderman", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ENDERMAN));
    public static final RegistryObject<Block> TROPHY_ENDERMITE = registerBlock("trophy_endermite", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ENDERMITE));
    public static final RegistryObject<Block> TROPHY_EVOKER = registerBlock("trophy_evoker", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.EVOKER));
    public static final RegistryObject<Block> TROPHY_FOX = registerBlock("trophy_fox", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.FOX));
    public static final RegistryObject<Block> TROPHY_FROG = registerBlock("trophy_frog", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.FROG));
    public static final RegistryObject<Block> TROPHY_GHAST = registerBlock("trophy_ghast", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.GHAST));
    public static final RegistryObject<Block> TROPHY_GLOW_SQUID = registerBlock("trophy_glow_squid", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.GLOW_SQUID));
    public static final RegistryObject<Block> TROPHY_GOAT = registerBlock("trophy_goat", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.GOAT));
    public static final RegistryObject<Block> TROPHY_GUARDIAN = registerBlock("trophy_guardian", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.GUARDIAN));
    public static final RegistryObject<Block> TROPHY_HOGLIN = registerBlock("trophy_hoglin", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.HOGLIN));
    public static final RegistryObject<Block> TROPHY_HORSE = registerBlock("trophy_horse", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.HORSE));
    public static final RegistryObject<Block> TROPHY_HUSK = registerBlock("trophy_husk", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.HUSK));
    public static final RegistryObject<Block> TROPHY_IRON_GOLEM = registerBlock("trophy_iron_golem", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.IRON_GOLEM));
    public static final RegistryObject<Block> TROPHY_LLAMA = registerBlock("trophy_llama", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.LLAMA));
    public static final RegistryObject<Block> TROPHY_MAGMA_CUBE = registerBlock("trophy_magma_cube", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.MAGMA_CUBE));
    public static final RegistryObject<Block> TROPHY_MOOSHROOM = registerBlock("trophy_mooshroom", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.MOOSHROOM));
    public static final RegistryObject<Block> TROPHY_MULE = registerBlock("trophy_mule", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.MULE));
    public static final RegistryObject<Block> TROPHY_OCELOT = registerBlock("trophy_ocelot", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.OCELOT));
    public static final RegistryObject<Block> TROPHY_PANDA = registerBlock("trophy_panda", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PANDA));
    public static final RegistryObject<Block> TROPHY_PARROT = registerBlock("trophy_parrot", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PARROT));
    public static final RegistryObject<Block> TROPHY_PHANTOM = registerBlock("trophy_phantom", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PHANTOM));
    public static final RegistryObject<Block> TROPHY_PIG = registerBlock("trophy_pig", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PIG));
    public static final RegistryObject<Block> TROPHY_PIGLIN = registerBlock("trophy_piglin", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PIGLIN));
    public static final RegistryObject<Block> TROPHY_PIGLIN_BRUTE = registerBlock("trophy_piglin_brute", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PIGLIN_BRUTE));
    public static final RegistryObject<Block> TROPHY_PILLAGER = registerBlock("trophy_pillager", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PILLAGER));
    public static final RegistryObject<Block> TROPHY_POLAR_BEAR = registerBlock("trophy_polar_bear", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.POLAR_BEAR));
    public static final RegistryObject<Block> TROPHY_PUFFERFISH = registerBlock("trophy_pufferfish", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.PUFFERFISH));
    public static final RegistryObject<Block> TROPHY_RABBIT = registerBlock("trophy_rabbit", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.RABBIT));
    public static final RegistryObject<Block> TROPHY_RAVAGER = registerBlock("trophy_ravager", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.RAVAGER));
    public static final RegistryObject<Block> TROPHY_SALMON = registerBlock("trophy_salmon", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SALMON));
    public static final RegistryObject<Block> TROPHY_SHEEP = registerBlock("trophy_sheep", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SHEEP));
    public static final RegistryObject<Block> TROPHY_SHULKER = registerBlock("trophy_shulker", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SHULKER));
    public static final RegistryObject<Block> TROPHY_SILVERFISH = registerBlock("trophy_silverfish", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SILVERFISH));
    public static final RegistryObject<Block> TROPHY_SKELETON = registerBlock("trophy_skeleton", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SKELETON));
    public static final RegistryObject<Block> TROPHY_SKELETON_HORSE = registerBlock("trophy_skeleton_horse", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SKELETON_HORSE));
    public static final RegistryObject<Block> TROPHY_SLIME = registerBlock("trophy_slime", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SLIME));
    public static final RegistryObject<Block> TROPHY_SNIFFER = registerBlock("trophy_sniffer", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SNIFFER));
    public static final RegistryObject<Block> TROPHY_SNOW_GOLEM = registerBlock("trophy_snow_golem", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SNOW_GOLEM));
    public static final RegistryObject<Block> TROPHY_SPIDER = registerBlock("trophy_spider", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SPIDER));
    public static final RegistryObject<Block> TROPHY_SQUID = registerBlock("trophy_squid", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.SQUID));
    public static final RegistryObject<Block> TROPHY_STRAY = registerBlock("trophy_stray", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.STRAY));
    public static final RegistryObject<Block> TROPHY_STRIDER = registerBlock("trophy_strider", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.STRIDER));
    public static final RegistryObject<Block> TROPHY_TADPOLE = registerBlock("trophy_tadpole", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.TADPOLE));
    public static final RegistryObject<Block> TROPHY_TRADER_LLAMA = registerBlock("trophy_trader_llama", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.TRADER_LLAMA));
    public static final RegistryObject<Block> TROPHY_TROPICAL_FISH = registerBlock("trophy_tropical_fish", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.TROPICAL_FISH));
    public static final RegistryObject<Block> TROPHY_TURTLE = registerBlock("trophy_turtle", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.TURTLE));
    public static final RegistryObject<Block> TROPHY_VEX = registerBlock("trophy_vex", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.VEX));
    public static final RegistryObject<Block> TROPHY_VILLAGER = registerBlock("trophy_villager", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.VILLAGER));
    public static final RegistryObject<Block> TROPHY_VINDICATOR = registerBlock("trophy_vindicator", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.VINDICATOR));
    public static final RegistryObject<Block> TROPHY_WANDERING_TRADER = registerBlock("trophy_wandering_trader", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WANDERING_TRADER));
    public static final RegistryObject<Block> TROPHY_WARDEN = registerBlock("trophy_warden", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WARDEN));
    public static final RegistryObject<Block> TROPHY_WITCH = registerBlock("trophy_witch", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WITCH));
    public static final RegistryObject<Block> TROPHY_WITHER = registerBlock("trophy_wither", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WITHER));
    public static final RegistryObject<Block> TROPHY_WITHER_SKELETON = registerBlock("trophy_wither_skeleton", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WITHER_SKELETON));
    public static final RegistryObject<Block> TROPHY_WOLF = registerBlock("trophy_wolf", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.WOLF));
    public static final RegistryObject<Block> TROPHY_ZOGLIN = registerBlock("trophy_zoglin", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ZOGLIN));
    public static final RegistryObject<Block> TROPHY_ZOMBIE = registerBlock("trophy_zombie", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ZOMBIE));
    public static final RegistryObject<Block> TROPHY_ZOMBIE_HORSE = registerBlock("trophy_zombie_horse", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ZOMBIE_HORSE));
    public static final RegistryObject<Block> TROPHY_ZOMBIE_VILLAGER = registerBlock("trophy_zombie_villager", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ZOMBIE_VILLAGER));
    public static final RegistryObject<Block> TROPHY_ZOMBIFIED_PIGLIN = registerBlock("trophy_zombified_piglin", () -> new SpawnEggTrophy(BlockBehaviour.Properties.copy(RileyModBlocks.TROPHY_BAT.get()), EntityType.ZOMBIFIED_PIGLIN));
    //Machines
    public static final RegistryObject<Block> CABLE = registerBlock("cable",
            () -> new CableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).noOcclusion()));
  //testing

    public static final RegistryObject<Block> ENCHANTER = registerBlock("enchanter",
            () -> new EnchanterBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

    public static final RegistryObject<Block> AUGMENTATION_STATION = registerBlock("augmentation_station",
            () -> new AugmentationStationBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));
//fillers
    public static final RegistryObject<Block> WHALE_HUNTER_TROPHY_FILLER = BLOCKS.register("whale_hunter_trophy_filler",
            () -> new WhaleHunterTrophyFillerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .noOcclusion()));
    public static final RegistryObject<Block> ENCHANTER_FILLER = BLOCKS.register("enchanter_filler",
            () -> new EnchanterFillerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .noOcclusion()));
    public static final RegistryObject<Block> AUGMENTATION_STATION_FILLER = BLOCKS.register("augmentation_station_filler",
            () -> new AugmentationStationFillerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .noOcclusion()));
    //redstone components
    public static final RegistryObject<Block> TOGGLE_SWITCH = registerBlock("toggle_switch",
            () -> new ToggleSwitchBlock(BlockBehaviour.Properties.copy(Blocks.STONE).lightLevel(state -> state.getValue(ToggleSwitchBlock.ACTIVATED) ? 15 : 0)));

    public static final RegistryObject<Block> SHUT_OFF = registerBlock("shut_off",
            () -> new ShutOff(BlockBehaviour.Properties.copy(Blocks.REPEATER)));

    public static final RegistryObject<Block> BLUESTONE_WIRE = registerBlock("bluestone_wire",
            () -> new BlueStoneWireBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_WIRE).noOcclusion().noCollission().instabreak().pushReaction(PushReaction.IGNORE)));

//individual parts
    public static final RegistryObject<Block> MACHINE_CORE = registerBlock("machine_core",
            () -> new MachineCorePartBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).noOcclusion()));
    public static final RegistryObject<Block> MACHINE_CORE_CENTER = registerBlock("machine_core_center",
            () -> new MachineCorePartBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).noOcclusion()));
    public static final RegistryObject<Block> MACHINE_CORE_PORT = registerBlock("machine_core_port",
            () -> new MachineCorePartBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).noOcclusion()));
    public static final RegistryObject<Block> MACHINE_CORE_SCREEN = registerBlock("machine_core_screen",
            () -> new MachineCoreScreenBlock(BlockBehaviour.Properties.copy(Blocks.DIRT).noOcclusion()));
//ores
    public static final RegistryObject<Block> FUNTIUM_ORE_BLOCK = registerBlock("funtium_ore_block",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(60).explosionResistance(9000).requiresCorrectToolForDrops(), UniformInt.of(7,9)));
    public static final RegistryObject<Block> DEEPSLATE_FUNTIUM_ORE = registerBlock("deepslate_funtium_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(60).explosionResistance(9000).requiresCorrectToolForDrops(), UniformInt.of(7,9)));
//grass
public static final RegistryObject<Block> ABYSSAL_GRASS = registerBlock("abyssal_grass",
            () -> new AbyssalGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));
public static final RegistryObject<Block> ABYSSAL_DIRT = registerBlock("abyssal_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));
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
    //complex blocks
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
    public static final RegistryObject<Block> TROPHY_READER = registerBlock("trophy_reader",
            () -> new TrophyReaderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
//portals
    public static final RegistryObject<Block> ABYSS_PORTAL = registerBlock("abyss_portal",
            () -> new RileyModAbyssPortalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).noCollission().noOcclusion().noLootTable()));
    public static final RegistryObject<Block> FALLOW_PORTAL = registerBlock("fallow_portal",
            () -> new RileyModFallowPortalBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).noCollission().noOcclusion().noLootTable()));
    //logic
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return RileyModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static final RegistryObject<Item> NIGHT_STAR_ITEM = RileyModItems.ITEMS.register("night_star",
            () -> new NightStarBlockItem(NIGHT_STAR.get(), new Item.Properties()));
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
