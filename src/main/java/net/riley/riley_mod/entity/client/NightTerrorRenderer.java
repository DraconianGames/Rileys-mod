package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.NightTerrorEntity;

public class NightTerrorRenderer extends MobRenderer<NightTerrorEntity,NightTerrorModel<NightTerrorEntity>> {
    public NightTerrorRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new  NightTerrorModel<>(pContext.bakeLayer(RileyModModelLayers.NIGHT_TERROR_LAYER)),.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(NightTerrorEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/night_terror.png");
    }

    @Override
    public void render(NightTerrorEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
