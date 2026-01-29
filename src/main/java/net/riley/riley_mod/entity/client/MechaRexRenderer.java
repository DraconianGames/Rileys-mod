package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

public class MechaRexRenderer extends MobRenderer<MechaRexEntity, MechaRexModel<MechaRexEntity>> {
    public MechaRexRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MechaRexModel<>(pContext.bakeLayer(RileyModModelLayers.MECHAREX_LAYER)),2f);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaRexEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/mecha_rex.png");
    }

    @Override
    public void render(MechaRexEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.3f, 0.3f, 0.3f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
