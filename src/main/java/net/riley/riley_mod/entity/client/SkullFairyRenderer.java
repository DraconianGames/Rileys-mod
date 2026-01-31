package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.SkullFairyEntity;

public class SkullFairyRenderer extends MobRenderer<SkullFairyEntity, SkullFairyModel<SkullFairyEntity>> {
    public SkullFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkullFairyModel<>(pContext.bakeLayer(RileyModModelLayers.SKULL_FAIRY_LAYER)), 2f);
    }

    @Override
    public ResourceLocation getTextureLocation(SkullFairyEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/skull_fairy.png");
    }

    @Override
    public void render(SkullFairyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
