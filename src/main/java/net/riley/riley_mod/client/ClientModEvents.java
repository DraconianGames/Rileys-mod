package net.riley.riley_mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.block.RileyModBlocks;
import net.riley.riley_mod.block.custom.BlueStoneWireBlock;
import net.riley.riley_mod.block.custom.SpawnEggTrophy;
import net.riley.riley_mod.client.model.WingModel;
import net.riley.riley_mod.client.render.layer.PlayerWingsLayer;
import net.riley.riley_mod.network.RileyModPackets;
import net.riley.riley_mod.network.WingSneakPacket;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        for (String skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);

            if (renderer == null) {
                continue;
            }

            ModelPart wingRoot = event.getEntityModels().bakeLayer(WingModel.LAYER_LOCATION);
            renderer.addLayer(new PlayerWingsLayer(renderer, wingRoot));
        }
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft mc = Minecraft.getInstance();
        BlockColors blockColors = mc.getBlockColors();

        blockColors.register((state, world, pos, tintIndex) -> {
            // only tint index 0 (your models use tintindex:0)
            if (tintIndex != 0) return -1;
            if (state == null) return -1; // defensive: sometimes called with null
            try {
                int power = state.getValue(BlueStoneWireBlock.POWER);
                return BlueStoneWireBlock.getColorForPower(power);
            } catch (Exception e) {
                return -1;
            }
        }, RileyModBlocks.BLUESTONE_WIRE.get());

        ItemColors itemColors = mc.getItemColors();

        blockColors.register((state, world, pos, tintIndex) -> {
                    Block block = state.getBlock();
                    if (block instanceof SpawnEggTrophy trophy) {
                        return tintIndex == 0 ? trophy.getPrimaryColor() : trophy.getSecondaryColor();
                    }
                    return -1;
                },
                // add all trophy blocks here
                RileyModBlocks.TROPHY_BAT.get(),
                RileyModBlocks.TROPHY_ALLAY.get(),
                RileyModBlocks.TROPHY_AXOLOTL.get(),
                RileyModBlocks.TROPHY_BEE.get(),
                RileyModBlocks.TROPHY_BLAZE.get(),
                RileyModBlocks.TROPHY_CAT.get(),
                RileyModBlocks.TROPHY_CAMEL.get(),
                RileyModBlocks.TROPHY_CAVE_SPIDER.get(),
                RileyModBlocks.TROPHY_CHICKEN.get(),
                RileyModBlocks.TROPHY_COD.get(),
                RileyModBlocks.TROPHY_COW.get(),
                RileyModBlocks.TROPHY_CREEPER.get(),
                RileyModBlocks.TROPHY_DOLPHIN.get(),
                RileyModBlocks.TROPHY_DONKEY.get(),
                RileyModBlocks.TROPHY_DROWNED.get(),
                RileyModBlocks.TROPHY_ELDER_GUARDIAN.get(),
                RileyModBlocks.TROPHY_ENDERMAN.get(),
                RileyModBlocks.TROPHY_ENDERMITE.get(),
                RileyModBlocks.TROPHY_EVOKER.get(),
                RileyModBlocks.TROPHY_FOX.get(),
                RileyModBlocks.TROPHY_FROG.get(),
                RileyModBlocks.TROPHY_GHAST.get(),
                RileyModBlocks.TROPHY_GLOW_SQUID.get(),
                RileyModBlocks.TROPHY_GOAT.get(),
                RileyModBlocks.TROPHY_GUARDIAN.get(),
                RileyModBlocks.TROPHY_HOGLIN.get(),
                RileyModBlocks.TROPHY_HORSE.get(),
                RileyModBlocks.TROPHY_HUSK.get(),
                RileyModBlocks.TROPHY_IRON_GOLEM.get(),
                RileyModBlocks.TROPHY_LLAMA.get(),
                RileyModBlocks.TROPHY_MAGMA_CUBE.get(),
                RileyModBlocks.TROPHY_MOOSHROOM.get(),
                RileyModBlocks.TROPHY_MULE.get(),
                RileyModBlocks.TROPHY_OCELOT.get(),
                RileyModBlocks.TROPHY_PANDA.get(),
                RileyModBlocks.TROPHY_PARROT.get(),
                RileyModBlocks.TROPHY_PHANTOM.get(),
                RileyModBlocks.TROPHY_PIG.get(),
                RileyModBlocks.TROPHY_PIGLIN.get(),
                RileyModBlocks.TROPHY_PIGLIN_BRUTE.get(),
                RileyModBlocks.TROPHY_PILLAGER.get(),
                RileyModBlocks.TROPHY_POLAR_BEAR.get(),
                RileyModBlocks.TROPHY_PUFFERFISH.get(),
                RileyModBlocks.TROPHY_RABBIT.get(),
                RileyModBlocks.TROPHY_RAVAGER.get(),
                RileyModBlocks.TROPHY_SALMON.get(),
                RileyModBlocks.TROPHY_SHEEP.get(),
                RileyModBlocks.TROPHY_SHULKER.get(),
                RileyModBlocks.TROPHY_SILVERFISH.get(),
                RileyModBlocks.TROPHY_SKELETON.get(),
                RileyModBlocks.TROPHY_SKELETON_HORSE.get(),
                RileyModBlocks.TROPHY_SLIME.get(),
                RileyModBlocks.TROPHY_SNIFFER.get(),
                RileyModBlocks.TROPHY_SNOW_GOLEM.get(),
                RileyModBlocks.TROPHY_SPIDER.get(),
                RileyModBlocks.TROPHY_SQUID.get(),
                RileyModBlocks.TROPHY_STRAY.get(),
                RileyModBlocks.TROPHY_STRIDER.get(),
                RileyModBlocks.TROPHY_TADPOLE.get(),
                RileyModBlocks.TROPHY_TRADER_LLAMA.get(),
                RileyModBlocks.TROPHY_TROPICAL_FISH.get(),
                RileyModBlocks.TROPHY_TURTLE.get(),
                RileyModBlocks.TROPHY_VEX.get(),
                RileyModBlocks.TROPHY_VILLAGER.get(),
                RileyModBlocks.TROPHY_VINDICATOR.get(),
                RileyModBlocks.TROPHY_WANDERING_TRADER.get(),
                RileyModBlocks.TROPHY_WARDEN.get(),
                RileyModBlocks.TROPHY_WITCH.get(),
                RileyModBlocks.TROPHY_WITHER.get(),
                RileyModBlocks.TROPHY_WITHER_SKELETON.get(),
                RileyModBlocks.TROPHY_WOLF.get(),
                RileyModBlocks.TROPHY_ZOGLIN.get(),
                RileyModBlocks.TROPHY_ZOMBIE.get(),
                RileyModBlocks.TROPHY_ZOMBIE_HORSE.get(),
                RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get(),
                RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get(),
                RileyModBlocks.TROPHY_ENDER_DRAGON.get()

                /*, RileyModBlocks.TROPHY_CREEPER.get(), ... */);

        itemColors.register((stack, tintIndex) -> {
                    if (stack.getItem() instanceof BlockItem bi) {
                        Block block = bi.getBlock();
                        if (block instanceof SpawnEggTrophy trophy) {
                            return tintIndex == 0 ? trophy.getPrimaryColor() : trophy.getSecondaryColor();
                        }
                    }
                    return -1;
                },
                // corresponding BlockItem(s)
                RileyModBlocks.TROPHY_BAT.get().asItem(),
                RileyModBlocks.TROPHY_ALLAY.get().asItem(),
                RileyModBlocks.TROPHY_AXOLOTL.get().asItem(),
                RileyModBlocks.TROPHY_BEE.get().asItem(),
                RileyModBlocks.TROPHY_BLAZE.get().asItem(),
                RileyModBlocks.TROPHY_CAMEL.get().asItem(),
                RileyModBlocks.TROPHY_CAVE_SPIDER.get().asItem(),
                RileyModBlocks.TROPHY_CHICKEN.get().asItem(),
                RileyModBlocks.TROPHY_COD.get().asItem(),
                RileyModBlocks.TROPHY_COW.get().asItem(),
                RileyModBlocks.TROPHY_CREEPER.get().asItem(),
                RileyModBlocks.TROPHY_DOLPHIN.get().asItem(),
                RileyModBlocks.TROPHY_DONKEY.get().asItem(),
                RileyModBlocks.TROPHY_DROWNED.get().asItem(),
                RileyModBlocks.TROPHY_ELDER_GUARDIAN.get().asItem(),
                RileyModBlocks.TROPHY_ENDERMAN.get().asItem(),
                RileyModBlocks.TROPHY_ENDERMITE.get().asItem(),
                RileyModBlocks.TROPHY_EVOKER.get().asItem(),
                RileyModBlocks.TROPHY_FOX.get().asItem(),
                RileyModBlocks.TROPHY_FROG.get().asItem(),
                RileyModBlocks.TROPHY_GHAST.get().asItem(),
                RileyModBlocks.TROPHY_GLOW_SQUID.get().asItem(),
                RileyModBlocks.TROPHY_GOAT.get().asItem(),
                RileyModBlocks.TROPHY_GUARDIAN.get().asItem(),
                RileyModBlocks.TROPHY_HOGLIN.get().asItem(),
                RileyModBlocks.TROPHY_HORSE.get().asItem(),
                RileyModBlocks.TROPHY_HUSK.get().asItem(),
                RileyModBlocks.TROPHY_IRON_GOLEM.get().asItem(),
                RileyModBlocks.TROPHY_LLAMA.get().asItem(),
                RileyModBlocks.TROPHY_MAGMA_CUBE.get().asItem(),
                RileyModBlocks.TROPHY_MOOSHROOM.get().asItem(),
                RileyModBlocks.TROPHY_MULE.get().asItem(),
                RileyModBlocks.TROPHY_OCELOT.get().asItem(),
                RileyModBlocks.TROPHY_PANDA.get().asItem(),
                RileyModBlocks.TROPHY_PARROT.get().asItem(),
                RileyModBlocks.TROPHY_PHANTOM.get().asItem(),
                RileyModBlocks.TROPHY_PIG.get().asItem(),
                RileyModBlocks.TROPHY_PIGLIN.get().asItem(),
                RileyModBlocks.TROPHY_PIGLIN_BRUTE.get().asItem(),
                RileyModBlocks.TROPHY_PILLAGER.get().asItem(),
                RileyModBlocks.TROPHY_POLAR_BEAR.get().asItem(),
                RileyModBlocks.TROPHY_PUFFERFISH.get().asItem(),
                RileyModBlocks.TROPHY_RABBIT.get().asItem(),
                RileyModBlocks.TROPHY_RAVAGER.get().asItem(),
                RileyModBlocks.TROPHY_SALMON.get().asItem(),
                RileyModBlocks.TROPHY_SHEEP.get().asItem(),
                RileyModBlocks.TROPHY_SHULKER.get().asItem(),
                RileyModBlocks.TROPHY_SILVERFISH.get().asItem(),
                RileyModBlocks.TROPHY_SKELETON.get().asItem(),
                RileyModBlocks.TROPHY_SKELETON_HORSE.get().asItem(),
                RileyModBlocks.TROPHY_SLIME.get().asItem(),
                RileyModBlocks.TROPHY_SNIFFER.get().asItem(),
                RileyModBlocks.TROPHY_SNOW_GOLEM.get().asItem(),
                RileyModBlocks.TROPHY_SPIDER.get().asItem(),
                RileyModBlocks.TROPHY_SQUID.get().asItem(),
                RileyModBlocks.TROPHY_STRAY.get().asItem(),
                RileyModBlocks.TROPHY_STRIDER.get().asItem(),
                RileyModBlocks.TROPHY_TADPOLE.get().asItem(),
                RileyModBlocks.TROPHY_TRADER_LLAMA.get().asItem(),
                RileyModBlocks.TROPHY_TROPICAL_FISH.get().asItem(),
                RileyModBlocks.TROPHY_TURTLE.get().asItem(),
                RileyModBlocks.TROPHY_VEX.get().asItem(),
                RileyModBlocks.TROPHY_VILLAGER.get().asItem(),
                RileyModBlocks.TROPHY_VINDICATOR.get().asItem(),
                RileyModBlocks.TROPHY_WANDERING_TRADER.get().asItem(),
                RileyModBlocks.TROPHY_WARDEN.get().asItem(),
                RileyModBlocks.TROPHY_WITCH.get().asItem(),
                RileyModBlocks.TROPHY_WITHER.get().asItem(),
                RileyModBlocks.TROPHY_WITHER_SKELETON.get().asItem(),
                RileyModBlocks.TROPHY_WOLF.get().asItem(),
                RileyModBlocks.TROPHY_ZOGLIN.get().asItem(),
                RileyModBlocks.TROPHY_ZOMBIE.get().asItem(),
                RileyModBlocks.TROPHY_ZOMBIE_HORSE.get().asItem(),
                RileyModBlocks.TROPHY_ZOMBIE_VILLAGER.get().asItem(),
                RileyModBlocks.TROPHY_ZOMBIFIED_PIGLIN.get().asItem(),
                RileyModBlocks.TROPHY_ENDER_DRAGON.get().asItem()
                /*, RileyModBlocks.TROPHY_CREEPER.get(), ... */);
    }
}