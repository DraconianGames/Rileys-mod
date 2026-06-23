package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.TruckEntity;

public class TruckArmorUpgradeLayer extends RenderLayer<TruckEntity,TruckModel<TruckEntity>> {
    private static final ResourceLocation ARMOR_UPGRADE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/truck_armor_upgrade.png");

    private final TruckArmorUpgrade<TruckEntity> armorUpgradeModel;

    public TruckArmorUpgradeLayer(RenderLayerParent<TruckEntity, TruckModel<TruckEntity>> renderer, TruckArmorUpgrade<TruckEntity> armorUpgradeModel) {
        super(renderer);
        this.armorUpgradeModel = armorUpgradeModel;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            TruckEntity truck,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (!truck.hasArmorUpgrade()) {
            return;
        }

        this.armorUpgradeModel.setupAnim(
                truck,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch
        );

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(ARMOR_UPGRADE_TEXTURE));

        this.armorUpgradeModel.renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F,
                1.0F,
                1.0F,
                1.0F
        );
    }
}
