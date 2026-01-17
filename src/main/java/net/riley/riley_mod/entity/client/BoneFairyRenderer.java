package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.BoneFairyEntity;


public class BoneFairyRenderer extends MobRenderer<BoneFairyEntity, BoneFairyModel<BoneFairyEntity>> {
    public BoneFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BoneFairyModel<>(pContext.bakeLayer(RileyModModelLayers.BONEFAIRY_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(BoneFairyEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/bonefairy.png");
    }
    @Override
    public void render(BoneFairyEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }

        super.render(pEntity, pEntityYaw, pPartialTick, pMatrixStack, pBuffer, pPackedLight);
    }
}
