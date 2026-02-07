package net.riley.riley_mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;

public class RileyModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RileyMod.MODID);
    
    public static final RegistryObject<CreativeModeTab> FUNTIUM_TAB = CREATIVE_MODE_TABS.register("funtium_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(RileyModItems.STARDUST.get()))
                    .title(Component.translatable("creativetab.funtium_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(RileyModBlocks.SPECIAL_SPAWNER.get());

                        output.accept(RileyModItems.STARDUST.get());
                        output.accept(RileyModItems.FUNTIUM_MIX.get());
                        output.accept(RileyModItems.FUNTIUM_PLATE.get());
                        output.accept(RileyModItems.FUNTIUM_ORE.get());
                        output.accept(RileyModItems.EYE.get());
                        output.accept(RileyModItems.CLAW.get());
                        output.accept(RileyModItems.TOOTH.get());
                        output.accept(RileyModItems.SUNLESS_CRAB_SPAWN_EGG.get());
                        output.accept(RileyModItems.RAPTER_SPAWN_EGG.get());
                        output.accept(RileyModItems.NIGHT_TERROR_SPAWN_EGG.get());
                        output.accept(RileyModItems.WHALE_HUNTER_SPAWN_EGG.get());
                        output.accept(RileyModItems.FROST_HOPPER_SPAWN_EGG.get());
                        output.accept(RileyModItems.TOOTHFAIRY_SPAWN_EGG.get());
                        output.accept(RileyModItems.BONEFAIRY_SPAWN_EGG.get());
                        output.accept(RileyModItems.SKELETONFAIRY_SPAWN_EGG.get());
                        output.accept(RileyModItems.SKULL_FAIRY_SPAWN_EGG.get());
                        output.accept(RileyModItems.BISON_EGG.get());
                        output.accept(RileyModItems.FANCY_SKULL.get());
                        output.accept(RileyModItems.SPEAR.get());

                        output.accept(RileyModItems.CAGGED_FAIRY.get());

                        output.accept(RileyModBlocks.NIGHT_STAR.get());

                        output.accept(RileyModItems.FUNTIUM_SWORD.get());
                        output.accept(RileyModItems.FUNTIUM_PICKAXE.get());
                        output.accept(RileyModItems.FUNTIUM_AXE.get());
                        output.accept(RileyModItems.FUNTIUM_SHOVEL.get());
                        output.accept(RileyModItems.FUNTIUM_HOE.get());

                        output.accept(RileyModItems.FUNTIUM_HELMET.get());
                        output.accept(RileyModItems.FUNTIUM_CHESTPLATE.get());
                        output.accept(RileyModItems.FUNTIUM_LEGGINGS.get());
                        output.accept(RileyModItems.FUNTIUM_BOOTS.get());


                        output.accept(RileyModBlocks.FUNTIUM_BLOCK.get());
                        output.accept(RileyModBlocks.ACTIVACTED_FUNTIUM.get());
                        output.accept(RileyModBlocks.FUNTIUM_ORE_BLOCK.get());
                        output.accept(RileyModBlocks.DEEPSLATE_FUNTIUM_ORE.get());

                        output.accept(RileyModBlocks.ABYSS_LOG.get());
                        output.accept(RileyModBlocks.STRIPPED_ABYSS_LOG.get());
                        output.accept(RileyModBlocks.ABYSS_WOOD.get());
                        output.accept(RileyModBlocks.STRIPPED_ABYSS_WOOD.get());
                        output.accept(RileyModBlocks.ABYSS_PLANKS.get());
                        output.accept(RileyModBlocks.ABYSS_LEAVES.get());
                        output.accept(RileyModBlocks.ABYSS_SAPLING.get());

                        output.accept(RileyModBlocks.ABYSS_WOOD_FENCE.get());
                        output.accept(RileyModBlocks.ABYSS_WOOD_SLAB.get());
                        output.accept(RileyModBlocks.ABYSS_WOOD_WALL.get());
                        output.accept(RileyModBlocks.ABYSS_WOOD_FENCE_GATE.get());
                        output.accept(RileyModBlocks.ABYSS_WOOD_STAIRS.get());
                        output.accept(RileyModBlocks.ABYSS_PORTAL.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK_FENCE.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK_SLAB.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK_WALL.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK_FENCE_GATE.get());
                        output.accept(RileyModBlocks.STRUCTURE_BRICK_STAIRS.get());
                        output.accept(RileyModBlocks.ABYSSAL_DIRT.get());
                        output.accept(RileyModBlocks.ABYSSAL_GRASS.get());
                        output.accept(RileyModBlocks.ABYSSAL_COBBLESTONE.get());
                        output.accept(RileyModBlocks.ABYSSAL_STONE.get());
                        output.accept(RileyModBlocks.BLACK_SAND.get());

                        output.accept(RileyModBlocks.WHALE_HUNTER_TROPHY.get());
                        output.accept(RileyModBlocks.AUGMENTATION_STATION.get());
                        output.accept(RileyModBlocks.ENCHANTER.get());
                        output.accept(RileyModBlocks.MORPH_STATION.get());
                        output.accept(RileyModBlocks.TROPHY_READER.get());

                        output.accept(RileyModItems.DARK_JOURNAL.get());

                        output.accept(RileyModItems.LIVING_MACHANICAL_NURONS.get());
                        output.accept(RileyModItems.LYDAR.get());
                        output.accept(RileyModItems.MECHA_REX_UNASSEMBLED_HEAD.get());
                        output.accept(RileyModItems.MECHAREX_BRAIN.get());
                        output.accept(RileyModItems.MECHAREX_EGG.get());
                        output.accept(RileyModItems.MECHAREX_ENGINE.get());
                        output.accept(RileyModItems.MECHAREX_HEAD.get());
                        output.accept(RileyModItems.MECHAREX_LEG.get());
                        output.accept(RileyModItems.MECHAREX_TORSO.get());
                        output.accept(RileyModItems.MECHAREX_TAIL.get());
                        output.accept(RileyModItems.SYNTHETIC_MUSCLE.get());
                        output.accept(RileyModItems.UNASSEMBLED_MECHAREX_TORSO.get());
                        output.accept(RileyModItems.UNASSEMBLED_MECHAREX_LEG.get());
                        output.accept(RileyModItems.UNASSEMBLED_MECHAREX_TAIL.get());
                        output.accept(RileyModItems.MECHAREX_CANNON.get());
                        output.accept(RileyModItems.ARMOR_PLATING.get());
                        output.accept(RileyModItems.CANNON_SHEILD.get());



                    })
                    .build());
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
