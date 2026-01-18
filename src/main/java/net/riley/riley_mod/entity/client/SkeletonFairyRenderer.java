package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.BoneFairyEntity;
import net.riley.riley_mod.entity.custom.SkeletonFairyEntity;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;

public class SkeletonFairyRenderer extends MobRenderer<SkeletonFairyEntity,SkeletonFairyModel<SkeletonFairyEntity>> {
    public SkeletonFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkeletonFairyModel<>(pContext.bakeLayer(RileyModModelLayers.SKELETONFAIRY_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonFairyEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/skeletonfairy.png");
    }

    @Override
    public void render(SkeletonFairyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
