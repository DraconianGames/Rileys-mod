package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.TrisonEntity;
import net.riley.riley_mod.item.RileyModItems;

public class TrisonArmorLayer extends RenderLayer<TrisonEntity, TrisonModel<TrisonEntity>> {
    private static final ResourceLocation TRISON_ARMOR_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/trison_armor.png");

    private final TrisonModel<TrisonEntity> armorModel;

    public TrisonArmorLayer(RenderLayerParent<TrisonEntity, TrisonModel<TrisonEntity>> renderer,
                            EntityModelSet modelSet) {
        super(renderer);
        this.armorModel = new TrisonModel<>(modelSet.bakeLayer(RileyModModelLayers.TRISON_LAYER));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       TrisonEntity trison, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!trison.hasMountArmor()) {
            return;
        }

        this.getParentModel().copyPropertiesTo(this.armorModel);
        this.armorModel.prepareMobModel(trison, limbSwing, limbSwingAmount, partialTick);
        this.armorModel.setupAnim(trison, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        poseStack.pushPose();
        poseStack.scale(1.01F, 1.01F, 1.01F);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TRISON_ARMOR_TEXTURE));
        this.armorModel.renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F
        );

        poseStack.popPose();
    }
}