package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.RapterEntity;

public class RapterRenderer extends MobRenderer<RapterEntity,RapterModel<RapterEntity>> {
    public RapterRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new RapterModel<>(pContext.bakeLayer(RileyModModelLayers.RAPTER_LAYER)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(RapterEntity pEntity) {
        return switch(pEntity.getVariant()) {
            case 1 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_albino.png");
            case 2 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_scarred.png");
            case 4 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_melinistc.png");
            case 5 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_gold.png");
            case 6 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_green.png");
            case 7 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_purple.png");
            case 8 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_red.png");
            case 9 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_silver.png");
            case 10 -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter_rainbow.png");
            default -> ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/rapter.png");
        };
    }
    @Override
    public void render(RapterEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}


