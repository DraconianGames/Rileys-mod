package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.SkyQuadsonEntity;

public class SkyQuadsonRenderer extends MobRenderer<SkyQuadsonEntity, SkyQuadsonModel<SkyQuadsonEntity>> {
    public SkyQuadsonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkyQuadsonModel<>(pContext.bakeLayer(RileyModModelLayers.SKY_QUADSON_LAYER)), 2.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(SkyQuadsonEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/sky_quadson.png");
    }

    @Override
    public void render(SkyQuadsonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
