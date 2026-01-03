package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.RapterEntity;

public class RapterRenderer extends MobRenderer<RapterEntity,RapterModel<RapterEntity>> {
    public RapterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RapterModel<>(pContext.bakeLayer(RileyModModelLayers.RAPTER_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(RapterEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/rapter_texture.png");
    }
    @Override
    public void render(RapterEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}


