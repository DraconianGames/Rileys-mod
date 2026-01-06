package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.WhaleHunterEntity;

public class WhaleHunterRenderer extends MobRenderer<WhaleHunterEntity, WhaleHunterModel<WhaleHunterEntity>> {
    public WhaleHunterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WhaleHunterModel<>(pContext.bakeLayer(RileyModModelLayers.WHALE_HUNTER_LAYER)),2);
    }

    @Override
    public ResourceLocation getTextureLocation(WhaleHunterEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/whalehunter.png");
    }

    @Override
    public void render(WhaleHunterEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {


        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}