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
                        output.accept(RileyModItems.STARDUST.get());
                        output.accept(RileyModItems.FUNTIUM_MIX.get());
                        output.accept(RileyModItems.FUNTIUM_PLATE.get());
                        output.accept(RileyModItems.FUNTIUM_ORE.get());
                        output.accept(RileyModItems.EYE.get());
                        output.accept(RileyModItems.SUNLESS_CRAB_SPAWN_EGG.get());

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


                    })
                    .build());
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
