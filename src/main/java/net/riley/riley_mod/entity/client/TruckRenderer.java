package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TruckEntity;

public class TruckRenderer extends MobRenderer<TruckEntity, TruckModel<TruckEntity>> {

    public TruckRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TruckModel<>(pContext.bakeLayer(RileyModModelLayers.TRUCK_LAYER)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(TruckEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/truck.png");
    }

    @Override
    public void render(TruckEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
    @Override
    protected float getFlipDegrees(TruckEntity entity) {
        return 0.0F;
    }
}
