package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.BisonEntity;

public class BisonRenderer extends MobRenderer<BisonEntity, BisonModel<BisonEntity>> {
    public BisonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BisonModel<>(pContext.bakeLayer(RileyModModelLayers.BISON_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(BisonEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/bison.png");
    }

    @Override
    public void render(BisonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
