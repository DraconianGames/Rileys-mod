package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;

public class SunlessCrabRenderer extends MobRenderer<SunlessCrabEntity,SunlessCrabModel<SunlessCrabEntity>> {
    public SunlessCrabRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SunlessCrabModel<>(pContext.bakeLayer(RileyModModelLayers.SUNLESS_CRAB_LAYER)), 1.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SunlessCrabEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/sunless_crab.png");
    }
    @Override
    public void render(SunlessCrabEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.1f, 0.1f, 0.1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}