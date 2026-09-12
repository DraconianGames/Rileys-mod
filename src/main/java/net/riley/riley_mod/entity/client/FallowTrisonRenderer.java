package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.FallowTrisonEntity;
import net.riley.riley_mod.entity.custom.TrisonEntity;

public class FallowTrisonRenderer extends MobRenderer<FallowTrisonEntity, TrisonModel<FallowTrisonEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/fallow_trison.png");

    public FallowTrisonRenderer(EntityRendererProvider.Context context) {
        super(context, new TrisonModel<>(context.bakeLayer(RileyModModelLayers.TRISON_LAYER)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(FallowTrisonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(FallowTrisonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}