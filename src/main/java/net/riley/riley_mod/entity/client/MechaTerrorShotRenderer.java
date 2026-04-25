package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaTerrorShotEntity;

public class MechaTerrorShotRenderer extends EntityRenderer<MechaTerrorShotEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/particle/mecha_terror_shot.png");

    public MechaTerrorShotRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    @Override
    public void render(MechaTerrorShotEntity entity,
                       float entityYaw,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight) {
        poseStack.pushPose();

        poseStack.scale(0.75F, 0.75F, 0.75F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        var vertexConsumer = buffer.getBuffer(net.minecraft.client.renderer.RenderType.entityTranslucentEmissive(TEXTURE));
        var pose = poseStack.last().pose();

        int light = 15728880;

        vertexConsumer.vertex(pose, -0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 1.0F).overlayCoords(0).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(pose, 0.5F, -0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 1.0F).overlayCoords(0).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(pose, 0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(1.0F, 0.0F).overlayCoords(0).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(pose, -0.5F, 0.5F, 0.0F).color(255, 255, 255, 255).uv(0.0F, 0.0F).overlayCoords(0).uv2(light).normal(0.0F, 1.0F, 0.0F).endVertex();

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaTerrorShotEntity entity) {
        return TEXTURE;
    }
}