package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaParasiteEntity;


public class MechaParasiteRenderer extends MobRenderer<MechaParasiteEntity, MechaParasiteModel<MechaParasiteEntity>> {
    public MechaParasiteRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MechaParasiteModel<>(pContext.bakeLayer(RileyModModelLayers.MECHA_PARASITE_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaParasiteEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/mecha_parasite.png");
    }

    @Override
    public void render(MechaParasiteEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
