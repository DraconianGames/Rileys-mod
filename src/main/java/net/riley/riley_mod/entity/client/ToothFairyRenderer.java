package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.RileyMod;

import net.riley.riley_mod.entity.custom.RapterEntity;
import net.riley.riley_mod.entity.custom.ToothFairyEntity;

public class ToothFairyRenderer extends MobRenderer<ToothFairyEntity, ToothFairyModel<ToothFairyEntity>> {

    public ToothFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new ToothFairyModel<>(pContext.bakeLayer(RileyModModelLayers.TOOTHFAIRY_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(ToothFairyEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/toothfairy.png");
    }

    @Override
    public void render(ToothFairyEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
       if(pEntity.isBaby()) {
           pMatrixStack.scale(0.3f, 0.3f, 0.3f);
       }

        super.render(pEntity, pEntityYaw, pPartialTick, pMatrixStack, pBuffer, pPackedLight);
    }

}
