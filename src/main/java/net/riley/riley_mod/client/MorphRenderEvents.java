package net.riley.riley_mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.client.RileyModModelLayers;
import net.riley.riley_mod.entity.client.WhaleHunterModel;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MorphRenderEvents {
    private static final ResourceLocation WHALE_HUNTER_MORPH =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "whale_hunter");

    private static final ResourceLocation WHALE_HUNTER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/whalehunter.png");

    private static WhaleHunterModel<Player> whaleHunterModel;

    private MorphRenderEvents() {}

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        ResourceLocation morphId = ClientMorphData.getMorph(player.getUUID());

        if (!WHALE_HUNTER_MORPH.equals(morphId)) {
            return;
        }

        event.setCanceled(true);

        Minecraft minecraft = Minecraft.getInstance();
        EntityModelSet modelSet = minecraft.getEntityModels();

        if (whaleHunterModel == null) {
            whaleHunterModel = new WhaleHunterModel<>(modelSet.bakeLayer(RileyModModelLayers.WHALE_HUNTER_LAYER));
        }

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();

        poseStack.pushPose();

        float partialTick = event.getPartialTick();
        float bodyYaw = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * partialTick;

        poseStack.mulPose(Axis.YP.rotationDegrees(-bodyYaw));

        // Minecraft entity models are rendered in an inverted render-space here.
        // Flip it upright, then rotate it so the model faces the same direction as the player.
        poseStack.scale(1.0F, -1.0F, -1.0F);
        poseStack.translate(0.0F, -1.5F, 0.0F);

        whaleHunterModel.setupAnim(
                player,
                player.walkAnimation.position(partialTick),
                player.walkAnimation.speed(partialTick),
                player.tickCount + partialTick,
                player.getYHeadRot() - player.getYRot(),
                player.getXRot()
        );

        whaleHunterModel.renderToBuffer(
                poseStack,
                buffer.getBuffer(whaleHunterModel.renderType(WHALE_HUNTER_TEXTURE)),
                event.getPackedLight(),
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        poseStack.popPose();
    }
}