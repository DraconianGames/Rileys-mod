package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MountainTrisonEntity;

public class MountianTrisonRenderer extends MobRenderer<MountainTrisonEntity, TrisonModel<MountainTrisonEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/mountian_trison.png");

    public MountianTrisonRenderer(EntityRendererProvider.Context context) {
        super(context, new TrisonModel<>(context.bakeLayer(RileyModModelLayers.TRISON_LAYER)), 1.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(MountainTrisonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(MountainTrisonEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, net.minecraft.client.renderer.MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}