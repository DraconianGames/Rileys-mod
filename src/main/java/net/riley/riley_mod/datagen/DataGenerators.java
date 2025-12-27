package net.riley.riley_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        generator.addProvider(event.includeServer(), new RileyModRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), RileyModLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new RileyModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new RileyModItemModelProvider(packOutput, existingFileHelper));

        RileyModBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new RileyModBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new RileyModItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new RileyModWorldGenProvider(packOutput, lookupProvider));


    }
}
