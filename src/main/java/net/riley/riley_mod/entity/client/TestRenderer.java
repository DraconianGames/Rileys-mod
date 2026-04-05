package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.entity.custom.TestEntity;

public class TestRenderer extends MobRenderer<TestEntity, TestModel<TestEntity>> {
    public TestRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TestModel<>(pContext.bakeLayer(RileyModModelLayers.TEST_LAYER)), 0.1f);

    }
    @Override
    public ResourceLocation getTextureLocation(TestEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath("riley_mod", "textures/entity/test.png");
    }

    @Override
    public void render(TestEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
