package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TestEntity;

public class TestRenderer extends MobRenderer<TestEntity, TestModel<TestEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/test.png");

    public TestRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TestModel<>(pContext.bakeLayer(RileyModModelLayers.TEST_LAYER)), 0.1f);
        this.addLayer(new YumTranslucentLayer(this, this.model, TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(TestEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public void render(TestEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, net.minecraft.client.renderer.MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}