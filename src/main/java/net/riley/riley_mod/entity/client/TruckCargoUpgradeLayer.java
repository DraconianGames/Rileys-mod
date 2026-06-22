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

public class TruckCargoUpgradeLayer extends RenderLayer<TruckEntity,TruckModel<TruckEntity>> {
    private static final ResourceLocation CARGO_UPGRADE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/truck_cargo_upgrade.png");

    private final TruckCargoUpgrade<TruckEntity> cargoUpgradeModel;

    public TruckCargoUpgradeLayer(RenderLayerParent<TruckEntity, TruckModel<TruckEntity>> renderer, TruckCargoUpgrade<TruckEntity> cargoUpgradeModel) {
        super(renderer);
        this.cargoUpgradeModel = cargoUpgradeModel;
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
        if (!truck.hasCargoUpgrade()) {
            return;
        }

        this.cargoUpgradeModel.setupAnim(
                truck,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch
        );

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(CARGO_UPGRADE_TEXTURE));

        this.cargoUpgradeModel.renderToBuffer(
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
