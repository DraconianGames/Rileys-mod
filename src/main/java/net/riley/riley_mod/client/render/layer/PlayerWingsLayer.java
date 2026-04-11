package net.riley.riley_mod.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.riley.riley_mod.client.ClientAugmentState;
import net.riley.riley_mod.client.ClientTickEvents;
import net.riley.riley_mod.client.WingAnimationDefinitions;
import net.riley.riley_mod.client.model.WingModel;

public class PlayerWingsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation WINGS_TEXTURE =
            new ResourceLocation("riley_mod", "textures/entity/wings.png");

    private final WingModel<AbstractClientPlayer> wingModel;

    private enum WingState {
        FOLDED,
        FALL,
        GLIDE,
        FLAP
    }

    public PlayerWingsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                            ModelPart bakedRoot) {
        super(parent);
        this.wingModel = new WingModel<>(bakedRoot);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!shouldRender(player)) {
            return;
        }

        PlayerModel<AbstractClientPlayer> playerModel = this.getParentModel();
        playerModel.prepareMobModel(player, limbSwing, limbSwingAmount, partialTick);
        playerModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        boolean gliding = player.isFallFlying();
        boolean flapping = gliding && ClientTickEvents.getWingFlapTicks() > 0;

        WingState state = resolveState(player, gliding, flapping);

        wingModel.copyBodyPoseFrom(playerModel);

        switch (state) {
            case FLAP -> wingModel.setAnimation(WingAnimationDefinitions.WING_FLAP);
            case GLIDE -> wingModel.setAnimation(WingAnimationDefinitions.WING_GLIDE);
            case FALL -> wingModel.setAnimation(WingAnimationDefinitions.WING_FALL);
            case FOLDED -> wingModel.setAnimation(WingAnimationDefinitions.WING_FOLDED);
        }

        wingModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        poseStack.pushPose();
        wingModel.root().render(
                poseStack,
                buffer.getBuffer(RenderType.entityCutoutNoCull(WINGS_TEXTURE)),
                packedLight,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F
        );
        poseStack.popPose();
    }

    private WingState resolveState(AbstractClientPlayer player, boolean gliding, boolean flapping) {
        if (flapping) return WingState.FLAP;
        if (gliding) return WingState.GLIDE;
        if (player.isShiftKeyDown() && !player.onGround()) return WingState.FALL;
        return WingState.FOLDED;
    }

    private boolean shouldRender(AbstractClientPlayer player) {
        ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath("riley_mod", "wings");
        return ClientAugmentState.isActive(wingsId) && ClientAugmentState.getLevel(wingsId) > 0;
    }
}