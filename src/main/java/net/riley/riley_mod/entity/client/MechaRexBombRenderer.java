package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.custom.MechaRexBombEntity;

public class MechaRexBombRenderer extends EntityRenderer<MechaRexBombEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "textures/entity/mecharex_bomb.png");

    private final MechaRex_Bomb_Model<MechaRexBombEntity> model;

    public MechaRexBombRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MechaRex_Bomb_Model<>(context.bakeLayer(RileyModModelLayers.MECHAREXBOMB_LAYER));
        this.shadowRadius = 0.25F;
    }

    @Override
    public void render(MechaRexBombEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

        poseStack.pushPose();

        // Compute facing from velocity (render-only), but mirror east/west by flipping X
        Vec3 v = entity.getDeltaMovement();
        Vec3 vForAngle = new Vec3(-v.x, v.y, -v.z); // <- reverses east/west for angle only

        float yaw;
        float pitch;

        if (vForAngle.lengthSqr() > 1.0E-6) {
            yaw = (float)(Mth.atan2(vForAngle.x, vForAngle.z) * (180.0D / Math.PI));
            pitch = (float)(Mth.atan2(vForAngle.y, Math.sqrt(vForAngle.x * vForAngle.x + vForAngle.z * vForAngle.z)) * (180.0D / Math.PI));
        } else {
            // fallback to entity rotation if we're basically stationary
            yaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
            pitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        // Drive the model animation (important for EntityRenderer)
        float ageInTicks = entity.tickCount + partialTick;
        model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        model.renderToBuffer(
                poseStack,
                vc,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, 1.0F
        );

        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MechaRexBombEntity entity) {
        return TEXTURE;
    }
}
