package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaRexBombEntity;

public class MechaRex_BombRenderer extends MobRenderer<MechaRexBombEntity,MechaRex_Bomb_Model<MechaRexBombEntity>> {
    public MechaRex_BombRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MechaRex_Bomb_Model<>(pContext.bakeLayer(RileyModModelLayers.MECHAREXBOMB_LAYER)),0f);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaRexBombEntity pEntity) {
        return new ResourceLocation(RileyMod.MODID, "textures/entity/mecharex_bomb.png");
    }

    @Override
    public void render(MechaRexBombEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
