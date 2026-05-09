package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

public class ParasiteCarrierRenderer extends MobRenderer<ParasiteCarrierEntity, ParasiteCarrierModel<ParasiteCarrierEntity>> {
    public ParasiteCarrierRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ParasiteCarrierModel<>(pContext.bakeLayer(RileyModModelLayers.PARASITE_CARRIER_LAYER)), 3F);
    }

    @Override
    public ResourceLocation getTextureLocation(ParasiteCarrierEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/parasite_carrier.png");
    }
    @Override
    public void render(ParasiteCarrierEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
