package net.riley.riley_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;

import java.util.Optional;

public final class CableConnections {
    private CableConnections() {
    }

    public static boolean isCable(BlockGetter level, BlockPos pos) {
        return level.getBlockState(pos).is(RileyModBlocks.CABLE.get());
    }

    public static boolean connectsCable(BlockGetter level, BlockPos neighborPos, Direction directionFromCable) {
        BlockState neighborState = level.getBlockState(neighborPos);
        return neighborState.is(RileyModBlocks.CABLE.get()) || isConnectableDevice(level, neighborPos, directionFromCable);
    }

    public static boolean isConnectableDevice(BlockGetter level, BlockPos devicePos, Direction directionFromCable) {
        BlockState deviceState = level.getBlockState(devicePos);

        if (deviceState.is(RileyModBlocks.MACHINE_CORE_PORT.get())) {
            return directionFromCable.getAxis().isHorizontal();
        }

        if (deviceState.is(RileyModBlocks.AUGMENTATION_STATION.get())) {
            return directionFromCable == Direction.UP;
        }


        if (deviceState.is(RileyModBlocks.ENCHANTER.get())) {
            return directionFromCable == Direction.UP;
        }

        if (deviceState.is(RileyModBlocks.TROPHY_READER.get())) {
            return directionFromCable != Direction.DOWN;
        }

        return false;
    }

    public static boolean isSpecialScreen(BlockState state) {
        return state.is(RileyModBlocks.ENCHANTER.get())
                || state.is(RileyModBlocks.AUGMENTATION_STATION.get());
    }

    public static Component getSpecialScreenName(BlockState state) {


        if (state.is(RileyModBlocks.ENCHANTER.get())) {
            return Component.literal("Enchanter");
        }

        if (state.is(RileyModBlocks.AUGMENTATION_STATION.get())) {
            return Component.literal("Augmentation Station");
        }

        return Component.literal("Unknown Screen");
    }

    public static boolean isTrophy(BlockState state) {
        return state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get()) ||
                state.is(RileyModBlocks.TROPHY_BAT.get()) ||
                state.is(RileyModBlocks.TROPHY_ALLAY.get()) ||
                state.is(RileyModBlocks.TROPHY_AXOLOTL.get()) ||
                state.is(RileyModBlocks.TROPHY_BEE.get()) ||
                state.is(RileyModBlocks.TROPHY_BLAZE.get()) ||
                state.is(RileyModBlocks.TROPHY_CAT.get()) ||
                state.is(RileyModBlocks.TROPHY_CAMEL.get()) ||
                state.is(RileyModBlocks.TROPHY_CAVE_SPIDER.get()) ||
                state.is(RileyModBlocks.TROPHY_CHICKEN.get()) ||
                state.is(RileyModBlocks.TROPHY_COD.get()) ||
                state.is(RileyModBlocks.TROPHY_COW.get()) ||
                state.is(RileyModBlocks.TROPHY_CREEPER.get()) ||
                state.is(RileyModBlocks.TROPHY_DOLPHIN.get()) ||
                state.is(RileyModBlocks.TROPHY_DONKEY.get()) ||
                state.is(RileyModBlocks.TROPHY_DROWNED.get()) ||
                state.is(RileyModBlocks.TROPHY_ELDER_GUARDIAN.get()) ||
                state.is(RileyModBlocks.TROPHY_ENDERMAN.get()) ||
                state.is(RileyModBlocks.TROPHY_ENDERMITE.get()) ||
                state.is(RileyModBlocks.TROPHY_EVOKER.get()) ||
                state.is(RileyModBlocks.TROPHY_FOX.get()) ||
                state.is(RileyModBlocks.TROPHY_FROG.get()) ||
                state.is(RileyModBlocks.TROPHY_GHAST.get()) ||
                state.is(RileyModBlocks.TROPHY_GLOW_SQUID.get()) ||
                state.is(RileyModBlocks.TROPHY_GOAT.get()) ||
                state.is(RileyModBlocks.TROPHY_GUARDIAN.get()) ||
                state.is(RileyModBlocks.TROPHY_HOGLIN.get()) ||
                state.is(RileyModBlocks.TROPHY_HORSE.get()) ||
                state.is(RileyModBlocks.TROPHY_HUSK.get()) ||
                state.is(RileyModBlocks.TROPHY_IRON_GOLEM.get()) ||
                state.is(RileyModBlocks.TROPHY_LLAMA.get()) ||
                state.is(RileyModBlocks.TROPHY_MAGMA_CUBE.get()) ||
                state.is(RileyModBlocks.TROPHY_MOOSHROOM.get()) ||
                state.is(RileyModBlocks.TROPHY_MULE.get()) ||
                state.is(RileyModBlocks.TROPHY_OCELOT.get()) ||
                state.is(RileyModBlocks.TROPHY_PANDA.get()) ||
                state.is(RileyModBlocks.TROPHY_PARROT.get()) ||
                state.is(RileyModBlocks.TROPHY_PHANTOM.get()) ||
                state.is(RileyModBlocks.TROPHY_PIG.get()) ||
                state.is(RileyModBlocks.TROPHY_PIGLIN.get()) ||
                state.is(RileyModBlocks.TROPHY_PIGLIN_BRUTE.get()) ||
                state.is(RileyModBlocks.TROPHY_PILLAGER.get()) ||
                state.is(RileyModBlocks.TROPHY_POLAR_BEAR.get()) ||
                state.is(RileyModBlocks.TROPHY_PUFFERFISH.get()) ||
                state.is(RileyModBlocks.TROPHY_RABBIT.get()) ||
                state.is(RileyModBlocks.TROPHY_RAVAGER.get()) ||
                state.is(RileyModBlocks.TROPHY_SALMON.get()) ||
                state.is(RileyModBlocks.TROPHY_SHEEP.get()) ||
                state.is(RileyModBlocks.TROPHY_SHULKER.get()) ||
                state.is(RileyModBlocks.TROPHY_SILVERFISH.get()) ||
                state.is(RileyModBlocks.TROPHY_SKELETON.get()) ||
                state.is(RileyModBlocks.TROPHY_SKELETON_HORSE.get()) ||
                state.is(RileyModBlocks.TROPHY_SLIME.get()) ||
                state.is(RileyModBlocks.TROPHY_SNIFFER.get()) ||
                state.is(RileyModBlocks.TROPHY_SNOW_GOLEM.get()) ||
                state.is(RileyModBlocks.TROPHY_SPIDER.get()) ||
                state.is(RileyModBlocks.TROPHY_SQUID.get()) ||
                state.is(RileyModBlocks.TROPHY_STRAY.get()) ||
                state.is(RileyModBlocks.TROPHY_STRIDER.get()) ||
                state.is(RileyModBlocks.TROPHY_TADPOLE.get()) ||
                state.is(RileyModBlocks.TROPHY_TRADER_LLAMA.get()) ||
                state.is(RileyModBlocks.TROPHY_TROPICAL_FISH.get()) ||
                state.is(RileyModBlocks.TROPHY_TURTLE.get()) ||
                state.is(RileyModBlocks.TROPHY_VEX.get()) ||
                state.is(RileyModBlocks.TROPHY_VILLAGER.get()) ||
                state.is(RileyModBlocks.TROPHY_VINDICATOR.get()) ||
                state.is(RileyModBlocks.TROPHY_WANDERING_TRADER.get()) ||
                state.is(RileyModBlocks.TROPHY_WARDEN.get()) ||
                state.is(RileyModBlocks.TROPHY_WITCH.get()) ||
                state.is(RileyModBlocks.TROPHY_WITHER.get()) ||
                state.is(RileyModBlocks.TROPHY_WITHER_SKELETON.get()) ||
                state.is(RileyModBlocks.TROPHY_WOLF.get()) ||
                state.is(RileyModBlocks.TROPHY_ZOGLIN.get()) ||
                state.is(RileyModBlocks.TROPHY_ZOMBIE.get()) ||
                state.is(RileyModBlocks.TROPHY_ZOMBIE_HORSE.get()) ||
                state.is(RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get()) ||
                state.is(RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get()) ||
                state.is(RileyModBlocks.TROPHY_ENDER_DRAGON.get());
    }

    public static Component getTrophyName(BlockState state) {
        if (state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get())) {
            return Component.literal("Whale Hunter Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_BAT.get())) {
            return Component.literal("Bat Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ALLAY.get())) {
            return Component.literal("Allay Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_AXOLOTL.get())) {
            return Component.literal("Axolotl Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_BEE.get())) {
            return Component.literal("Bee Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_BLAZE.get())) {
            return Component.literal("Blaze Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_CAT.get())) {
            return Component.literal("Cat Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_CAMEL.get())) {
            return Component.literal("Camel Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_CAVE_SPIDER.get())) {
            return Component.literal("Cave Spider Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_CHICKEN.get())) {
            return Component.literal("Chicken Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_COD.get())) {
            return Component.literal("Cod Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_COW.get())) {
            return Component.literal("Cow Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_CREEPER.get())) {
            return Component.literal("Creeper Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_DOLPHIN.get())) {
            return Component.literal("Dolphin Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_DONKEY.get())) {
            return Component.literal("Donkey Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_DROWNED.get())) {
            return Component.literal("Drowned Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ELDER_GUARDIAN.get())) {
            return Component.literal("Elder Guardian Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ENDERMAN.get())) {
            return Component.literal("Enderman Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ENDERMITE.get())) {
            return Component.literal("Endermite Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_EVOKER.get())) {
            return Component.literal("Evoker Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_FOX.get())) {
            return Component.literal("Fox Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_FROG.get())) {
            return Component.literal("Frog Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_GHAST.get())) {
            return Component.literal("Ghast Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_GLOW_SQUID.get())) {
            return Component.literal("Glow Squid Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_GOAT.get())) {
            return Component.literal("Goat Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_GUARDIAN.get())) {
            return Component.literal("Guardian Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_HOGLIN.get())) {
            return Component.literal("Hoglin Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_HORSE.get())) {
            return Component.literal("Horse Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_HUSK.get())) {
            return Component.literal("Husk Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_IRON_GOLEM.get())) {
            return Component.literal("Iron Golem Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_LLAMA.get())) {
            return Component.literal("Llama Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_MAGMA_CUBE.get())) {
            return Component.literal("Magma Cube Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_MOOSHROOM.get())) {
            return Component.literal("Mooshroom Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_MULE.get())) {
            return Component.literal("Mule Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_OCELOT.get())) {
            return Component.literal("Ocelot Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PANDA.get())) {
            return Component.literal("Panda Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PARROT.get())) {
            return Component.literal("Parrot Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PHANTOM.get())) {
            return Component.literal("Phantom Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PIG.get())) {
            return Component.literal("Pig Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PIGLIN.get())) {
            return Component.literal("Piglin Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PIGLIN_BRUTE.get())) {
            return Component.literal("Piglin Brute Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PILLAGER.get())) {
            return Component.literal("Pillager Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_POLAR_BEAR.get())) {
            return Component.literal("Polar Bear Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_PUFFERFISH.get())) {
            return Component.literal("Pufferfish Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_RABBIT.get())) {
            return Component.literal("Rabbit Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_RAVAGER.get())) {
            return Component.literal("Ravager Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SALMON.get())) {
            return Component.literal("Salmon Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SHEEP.get())) {
            return Component.literal("Sheep Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SHULKER.get())) {
            return Component.literal("Shulker Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SILVERFISH.get())) {
            return Component.literal("Silverfish Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SKELETON.get())) {
            return Component.literal("Skeleton Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SKELETON_HORSE.get())) {
            return Component.literal("Skeleton Horse Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SLIME.get())) {
            return Component.literal("Slime Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SNIFFER.get())) {
            return Component.literal("Sniffer Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SNOW_GOLEM.get())) {
            return Component.literal("Snow Golem Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SPIDER.get())) {
            return Component.literal("Spider Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_SQUID.get())) {
            return Component.literal("Squid Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_STRAY.get())) {
            return Component.literal("Stray Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_STRIDER.get())) {
            return Component.literal("Strider Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_TADPOLE.get())) {
            return Component.literal("Tadpole Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_TRADER_LLAMA.get())) {
            return Component.literal("Trader Llama Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_TROPICAL_FISH.get())) {
            return Component.literal("Tropical Fish Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_TURTLE.get())) {
            return Component.literal("Turtle Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_VEX.get())) {
            return Component.literal("Vex Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_VILLAGER.get())) {
            return Component.literal("Villager Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_VINDICATOR.get())) {
            return Component.literal("Vindicator Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WANDERING_TRADER.get())) {
            return Component.literal("Wandering Trader Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WARDEN.get())) {
            return Component.literal("Warden Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WITCH.get())) {
            return Component.literal("Witch Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WITHER.get())) {
            return Component.literal("Wither Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WITHER_SKELETON.get())) {
            return Component.literal("Wither Skeleton Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_WOLF.get())) {
            return Component.literal("Wolf Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ZOGLIN.get())) {
            return Component.literal("Zoglin Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE.get())) {
            return Component.literal("Zombie Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE_HORSE.get())) {
            return Component.literal("Zombie Horse Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get())) {
            return Component.literal("Zombie Villager Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get())) {
            return Component.literal("Zombified Piglin Trophy");
        }
        if (state.is(RileyModBlocks.TROPHY_ENDER_DRAGON.get())) {
            return Component.literal("Ender Dragon Trophy");
        }

        return Component.literal("Unknown Trophy");
    }

    public static Optional<ResourceLocation> getTrophyId(BlockState state) {
        if (state.is(RileyModBlocks.WHALE_HUNTER_TROPHY.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter_trophy"));
        }
        if (state.is(RileyModBlocks.TROPHY_BAT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_bat"));
        }
        if (state.is(RileyModBlocks.TROPHY_ALLAY.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_allay"));
        }
        if (state.is(RileyModBlocks.TROPHY_AXOLOTL.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_axolotl"));
        }
        if (state.is(RileyModBlocks.TROPHY_BEE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_bee"));
        }
        if (state.is(RileyModBlocks.TROPHY_BLAZE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_blaze"));
        }
        if (state.is(RileyModBlocks.TROPHY_CAT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_cat"));
        }
        if (state.is(RileyModBlocks.TROPHY_CAMEL.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_camel"));
        }
        if (state.is(RileyModBlocks.TROPHY_CAVE_SPIDER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_cave_spider"));
        }
        if (state.is(RileyModBlocks.TROPHY_CHICKEN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_chicken"));
        }
        if (state.is(RileyModBlocks.TROPHY_COD.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_cod"));
        }
        if (state.is(RileyModBlocks.TROPHY_COW.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_cow"));
        }
        if (state.is(RileyModBlocks.TROPHY_CREEPER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_creeper"));
        }
        if (state.is(RileyModBlocks.TROPHY_DOLPHIN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_dolphin"));
        }
        if (state.is(RileyModBlocks.TROPHY_DONKEY.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_donkey"));
        }
        if (state.is(RileyModBlocks.TROPHY_DROWNED.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_drowned"));
        }
        if (state.is(RileyModBlocks.TROPHY_ELDER_GUARDIAN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_elder_guardian"));
        }
        if (state.is(RileyModBlocks.TROPHY_ENDERMAN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_enderman"));
        }
        if (state.is(RileyModBlocks.TROPHY_ENDERMITE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_endermite"));
        }
        if (state.is(RileyModBlocks.TROPHY_EVOKER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_evoker"));
        }
        if (state.is(RileyModBlocks.TROPHY_FOX.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_fox"));
        }
        if (state.is(RileyModBlocks.TROPHY_FROG.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_frog"));
        }
        if (state.is(RileyModBlocks.TROPHY_GHAST.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_ghast"));
        }
        if (state.is(RileyModBlocks.TROPHY_GLOW_SQUID.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_glow_squid"));
        }
        if (state.is(RileyModBlocks.TROPHY_GOAT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_goat"));
        }
        if (state.is(RileyModBlocks.TROPHY_GUARDIAN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_guardian"));
        }
        if (state.is(RileyModBlocks.TROPHY_HOGLIN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_hoglin"));
        }
        if (state.is(RileyModBlocks.TROPHY_HORSE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_horse"));
        }
        if (state.is(RileyModBlocks.TROPHY_HUSK.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_husk"));
        }
        if (state.is(RileyModBlocks.TROPHY_IRON_GOLEM.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_iron_golem"));
        }
        if (state.is(RileyModBlocks.TROPHY_LLAMA.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_llama"));
        }
        if (state.is(RileyModBlocks.TROPHY_MAGMA_CUBE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_magma_cube"));
        }
        if (state.is(RileyModBlocks.TROPHY_MOOSHROOM.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_mooshroom"));
        }
        if (state.is(RileyModBlocks.TROPHY_MULE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_mule"));
        }
        if (state.is(RileyModBlocks.TROPHY_OCELOT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_ocelot"));
        }
        if (state.is(RileyModBlocks.TROPHY_PANDA.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_panda"));
        }
        if (state.is(RileyModBlocks.TROPHY_PARROT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_parrot"));
        }
        if (state.is(RileyModBlocks.TROPHY_PHANTOM.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_phantom"));
        }
        if (state.is(RileyModBlocks.TROPHY_PIG.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_pig"));
        }
        if (state.is(RileyModBlocks.TROPHY_PIGLIN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_piglin"));
        }
        if (state.is(RileyModBlocks.TROPHY_PIGLIN_BRUTE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_piglin_brute"));
        }
        if (state.is(RileyModBlocks.TROPHY_PILLAGER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_pillager"));
        }
        if (state.is(RileyModBlocks.TROPHY_POLAR_BEAR.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_polar_bear"));
        }
        if (state.is(RileyModBlocks.TROPHY_PUFFERFISH.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_pufferfish"));
        }
        if (state.is(RileyModBlocks.TROPHY_RABBIT.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_rabbit"));
        }
        if (state.is(RileyModBlocks.TROPHY_RAVAGER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_ravager"));
        }
        if (state.is(RileyModBlocks.TROPHY_SALMON.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_salmon"));
        }
        if (state.is(RileyModBlocks.TROPHY_SHEEP.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_sheep"));
        }
        if (state.is(RileyModBlocks.TROPHY_SHULKER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_shulker"));
        }
        if (state.is(RileyModBlocks.TROPHY_SILVERFISH.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_silverfish"));
        }
        if (state.is(RileyModBlocks.TROPHY_SKELETON.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_skeleton"));
        }
        if (state.is(RileyModBlocks.TROPHY_SKELETON_HORSE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_skeleton_horse"));
        }
        if (state.is(RileyModBlocks.TROPHY_SLIME.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_slime"));
        }
        if (state.is(RileyModBlocks.TROPHY_SNIFFER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_sniffer"));
        }
        if (state.is(RileyModBlocks.TROPHY_SNOW_GOLEM.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_snow_golem"));
        }
        if (state.is(RileyModBlocks.TROPHY_SPIDER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_spider"));
        }
        if (state.is(RileyModBlocks.TROPHY_SQUID.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_squid"));
        }
        if (state.is(RileyModBlocks.TROPHY_STRAY.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_stray"));
        }
        if (state.is(RileyModBlocks.TROPHY_STRIDER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_strider"));
        }
        if (state.is(RileyModBlocks.TROPHY_TADPOLE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_tadpole"));
        }
        if (state.is(RileyModBlocks.TROPHY_TRADER_LLAMA.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_trader_llama"));
        }
        if (state.is(RileyModBlocks.TROPHY_TROPICAL_FISH.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_tropical_fish"));
        }
        if (state.is(RileyModBlocks.TROPHY_TURTLE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_turtle"));
        }
        if (state.is(RileyModBlocks.TROPHY_VEX.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_vex"));
        }
        if (state.is(RileyModBlocks.TROPHY_VILLAGER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_villager"));
        }
        if (state.is(RileyModBlocks.TROPHY_VINDICATOR.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_vindicator"));
        }
        if (state.is(RileyModBlocks.TROPHY_WANDERING_TRADER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_wandering_trader"));
        }
        if (state.is(RileyModBlocks.TROPHY_WARDEN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_warden"));
        }
        if (state.is(RileyModBlocks.TROPHY_WITCH.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_witch"));
        }
        if (state.is(RileyModBlocks.TROPHY_WITHER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_wither"));
        }
        if (state.is(RileyModBlocks.TROPHY_WITHER_SKELETON.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_wither_skeleton"));
        }
        if (state.is(RileyModBlocks.TROPHY_WOLF.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_wolf"));
        }
        if (state.is(RileyModBlocks.TROPHY_ZOGLIN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_zoglin"));
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_zombie"));
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE_HORSE.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_zombie_horse"));
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_zombie_villager"));
        }
        if (state.is(RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_zombified_piglin"));
        }
        if (state.is(RileyModBlocks.TROPHY_ENDER_DRAGON.get())) {
            return Optional.of(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "trophy_ender_dragon"));
        }

        return Optional.empty();
    }

    public static Optional<ResourceLocation> getTrophyIdBeingRead(BlockGetter level, BlockPos trophyReaderPos) {
        BlockPos trophyPos = trophyReaderPos.above();
        BlockState trophyState = level.getBlockState(trophyPos);

        return getTrophyId(trophyState);
    }

    public static Component getTrophyBeingRead(BlockGetter level, BlockPos trophyReaderPos) {
        BlockPos trophyPos = trophyReaderPos.above();
        BlockState trophyState = level.getBlockState(trophyPos);

        if (isTrophy(trophyState)) {
            return getTrophyName(trophyState);
        }

        return Component.literal("None");
    }
}