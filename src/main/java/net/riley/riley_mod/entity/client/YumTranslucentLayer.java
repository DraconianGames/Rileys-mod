package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TestEntity;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class YumTranslucentLayer extends RenderLayer<TestEntity, TestModel<TestEntity>> {
    private final TestModel<TestEntity> model;
    private final ResourceLocation texture;

    public YumTranslucentLayer(RenderLayerParent<TestEntity, TestModel<TestEntity>> parent, TestModel<TestEntity> model, ResourceLocation texture) {
        super(parent);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, TestEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(texture));
        this.model.renderYum(poseStack, consumer, packedLight, getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
    }
}