package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TrisonCartEntity;

public class TrisonCartRenderer extends MobRenderer<TrisonCartEntity, TrisonCartModel<TrisonCartEntity>> {
    public TrisonCartRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TrisonCartModel<>(pContext.bakeLayer(RileyModModelLayers.TRISON_CART_LAYER)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(TrisonCartEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/trison_cart.png");
    }

    @Override
    public void render(TrisonCartEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
