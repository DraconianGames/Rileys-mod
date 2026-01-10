package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.FrostHopperEntity;
import net.riley.riley_mod.entity.custom.RapterEntity;

public class FrostHopperRenderer extends MobRenderer<FrostHopperEntity, FrostHopperModel<FrostHopperEntity>> {
    public FrostHopperRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FrostHopperModel(pContext.bakeLayer(RileyModModelLayers.FROST_HOPPER_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(FrostHopperEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID,"textures/entity/frost_hopper.png");
    }


    @Override
    public void render(FrostHopperEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}