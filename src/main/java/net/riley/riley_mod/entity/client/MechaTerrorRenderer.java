package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaTerrorEntity;

public class MechaTerrorRenderer extends MobRenderer<MechaTerrorEntity, MechaTerrorModel<MechaTerrorEntity>> {
    public MechaTerrorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MechaTerrorModel<>(pContext.bakeLayer(RileyModModelLayers.MECHA_TERROR_LAYER)),1f);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaTerrorEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/mecha_terror_copper.png");
    }
    @Override
    public void render(MechaTerrorEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }

        /* This scales the adult model size
        if(!pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }*/
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
