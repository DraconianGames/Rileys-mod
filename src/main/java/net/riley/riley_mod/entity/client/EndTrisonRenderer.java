package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.EndTrisonEntity;

public class EndTrisonRenderer extends MobRenderer<EndTrisonEntity, TrisonModel<EndTrisonEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/end_trison.png");

    public EndTrisonRenderer(EntityRendererProvider.Context context) {
        super(context, new TrisonModel<>(context.bakeLayer(RileyModModelLayers.TRISON_LAYER)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(EndTrisonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(EndTrisonEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}