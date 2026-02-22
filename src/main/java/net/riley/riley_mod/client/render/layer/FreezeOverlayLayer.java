package net.riley.riley_mod.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.effect.RileyModEffects;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class FreezeOverlayLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    // Path: src/main/resources/assets/riley_mod/textures/misc/freeze_effect_texture.png
    private static final ResourceLocation FREEZE_OVERLAY =
            new ResourceLocation(RileyMod.MODID, "textures/misc/freeze_effect_texture.png");

    public FreezeOverlayLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (!entity.hasEffect(RileyModEffects.FREEZE.get())) {
            return;
        }
        System.out.println("Freeze overlay rendering for entity: " + entity);

        if (!entity.hasEffect(RileyModEffects.FREEZE.get())) {
            return;
        }

        poseStack.pushPose();

        // Use translucent so PNG alpha is respected
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(FREEZE_OVERLAY));

        // Tint (r,g,b) and alpha. Lower alpha = more subtle overlay
        float r = 1.0f;
        float g = 1.0f;
        float b = 1.0f;
        float a = 1.0f;

        this.getParentModel().renderToBuffer(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), r, g, b, a);

        poseStack.popPose();
    }

}