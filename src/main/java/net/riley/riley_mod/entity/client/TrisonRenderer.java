package net.riley.riley_mod.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TrisonEntity;

public class TrisonRenderer extends MobRenderer<TrisonEntity, TrisonModel<TrisonEntity>> {
    public TrisonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TrisonModel<>(pContext.bakeLayer(RileyModModelLayers.TRISON_LAYER)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(TrisonEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/trison.png");
    }
    @Override
    public void render(TrisonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
