package net.riley.riley_mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.ModBlocks;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RileyMod.MODID);
    
    public static final RegistryObject<CreativeModeTab> FUNTIUM_TAB = CREATIVE_MODE_TABS.register("funtium_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.STARDUST.get()))
                    .title(Component.translatable("creativetab.funtium_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(Moditems.STARDUST.get());
                        output.accept(Moditems.FUNTIUM_MIX.get());
                        output.accept(Moditems.FUNTIUM_PLATE.get());
                        output.accept(Moditems.FUNTIUM_ORE.get());
                        output.accept(Moditems.EYE.get());

                        output.accept(Moditems.FUNTIUM_SWORD.get());
                        output.accept(Moditems.FUNTIUM_PICKAXE.get());
                        output.accept(Moditems.FUNTIUM_AXE.get());
                        output.accept(Moditems.FUNTIUM_SHOVEL.get());
                        output.accept(Moditems.FUNTIUM_HOE.get());


                        output.accept(ModBlocks.FUNTIUM_BLOCK.get());
                        output.accept(ModBlocks.ACTIVACTED_FUNTIUM.get());
                        output.accept(ModBlocks.FUNTIUM_ORE_BLOCK.get());
                        output.accept(ModBlocks.DEEPSLATE_FUNTIUM_ORE.get());

                        output.accept(ModBlocks.ABYSS_LOG.get());
                        output.accept(ModBlocks.STRIPPED_ABYSS_LOG.get());
                        output.accept(ModBlocks.ABYSS_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_ABYSS_WOOD.get());
                        output.accept(ModBlocks.ABYSS_PLANKS.get());
                        output.accept(ModBlocks.ABYSS_LEAVES.get());
                        output.accept(ModBlocks.ABYSS_SAPLING.get());
                    })
                    .build());
    
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
