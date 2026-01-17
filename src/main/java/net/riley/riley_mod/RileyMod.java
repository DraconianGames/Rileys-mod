package net.riley.riley_mod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.brewing.FreezeBrewingRecipe;
import net.riley.riley_mod.effect.RileyModEffects;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.client.*;
import net.riley.riley_mod.entity.custom.WhaleHunterEntity;
import net.riley.riley_mod.item.RileyModCreativeModTabs;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.sound.RileyModSounds;
import net.riley.riley_mod.worldgen.dimension.AbyssRegion;
import terrablender.api.Regions;


import net.riley.riley_mod.potion.RileyModPotions;
import org.slf4j.Logger;

import static net.riley.riley_mod.RileyMod.MODID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MODID)
public class RileyMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "riley_mod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public RileyMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();


        RileyModCreativeModTabs.register(modEventBus);
//TODO add new structure to nether for wither skeleton spawner.
//TODO add new mobs to the ocean.
//TODO add mob that uses the freeze effect, add ice chunk to that mobs loot table
//TODO add more effects that mobs can use
//TODO add a recipe for the dark journal
//TODO add tooth fairy and bone fairy. Evolution possible.
//TODO Update block pages for book
//TODO Evolution Ideas: potion effect, menu screen, kill counter, special food/item, special tree with fruit of evolution
//TODO Make pets stay tamed for evolution.
//TODO upgrade tree
//TODO Block when activated spawns a boss
//TODO remove crab hunting from night terror

        RileyModSounds.register(modEventBus);

        RileyModItems.register(modEventBus);
        RileyModEntities.register(modEventBus);
        RileyModBlocks.register(modEventBus);
        RileyModEffects.register(modEventBus);
        RileyModPotions.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // register brewing recipes on the main thread
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new FreezeBrewingRecipe());

        });


    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){

        }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(RileyModEntities.SUNLESS_CRAB.get(), SunlessCrabRenderer::new);
            EntityRenderers.register(RileyModEntities.RAPTER.get(), RapterRenderer::new);
            EntityRenderers.register(RileyModEntities.NIGHT_TERROR.get(), NightTerrorRenderer::new);
            EntityRenderers.register(RileyModEntities.FROST_HOPPER.get(), FrostHopperRenderer::new);
            EntityRenderers.register(RileyModEntities.WHALE_HUNTER.get(), WhaleHunterRenderer::new);
            ItemBlockRenderTypes.setRenderLayer(RileyModBlocks.ABYSS_PORTAL.get(), RenderType.translucent());
        }
    }
}
