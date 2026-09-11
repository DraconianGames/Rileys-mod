package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.SummoningCircleEntity;

public class SummoningCircleRenderer extends EntityRenderer<SummoningCircleEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/summon_circle.png");

    private final SummoningCircleModel<SummoningCircleEntity> model;

    public SummoningCircleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SummoningCircleModel<>(context.bakeLayer(RileyModModelLayers.SUMMONING_CIRCLE_LAYER));
        this.shadowRadius = 0.1f;
    }

    @Override
    public void render(SummoningCircleEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 1.5D, 0.0D);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

        this.model.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTick, 0.0F, 0.0F);
        this.model.renderToBuffer(
                poseStack,
                bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity))),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SummoningCircleEntity pEntity) {
        return TEXTURE;
    }
}