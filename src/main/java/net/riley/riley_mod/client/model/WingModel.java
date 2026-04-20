package net.riley.riley_mod.client.model;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.client.WingAnimationDefinitions;

public class WingModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(new ResourceLocation("riley_mod", "wings"), "main");

    private final ModelPart root;
    private final ModelPart body;

    private final AnimationState animationState = new AnimationState();

    private AnimationDefinition currentAnimation = null;
    private AnimationDefinition lastAnimation = null;

    public WingModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
    }

    public void setAnimation(AnimationDefinition animation) {
        this.currentAnimation = animation;
    }

    public void copyBodyPoseFrom(PlayerModel<?> playerModel) {
        this.body.copyFrom(playerModel.body);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition armorBody = body.addOrReplaceChild("armorBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_wing = armorBody.addOrReplaceChild("left_wing", CubeListBuilder.create()
                        .texOffs(40, 34).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 0).addBox(0.0F, 0.5F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, 0.5F, 2.0F));

        PartDefinition left_wing_mid = left_wing.addOrReplaceChild("left_wing_mid", CubeListBuilder.create()
                        .texOffs(22, 45).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 24).addBox(0.0F, 0.5F, 0.0F, 0.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition left_wing_end = left_wing_mid.addOrReplaceChild("left_wing_end", CubeListBuilder.create()
                        .texOffs(0, 56).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(0.0F, 0.5F, 0.0F, 0.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition right_wing = armorBody.addOrReplaceChild("right_wing", CubeListBuilder.create()
                        .texOffs(0, 45).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 17).addBox(0.0F, 0.5F, 0.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, 0.5F, 2.0F));

        PartDefinition right_wing_mid = right_wing.addOrReplaceChild("right_wing_mid", CubeListBuilder.create()
                        .texOffs(44, 45).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 24).addBox(0.0F, 0.5F, 0.0F, 0.0F, 11.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition right_wing_end = right_wing_mid.addOrReplaceChild("right_wing_end", CubeListBuilder.create()
                        .texOffs(22, 56).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 0).addBox(0.0F, 0.5F, 0.0F, 0.0F, 14.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 10.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if (entity instanceof net.minecraft.client.player.AbstractClientPlayer player && player.isCrouching()) {
            this.body.xRot = 0.5F;
            body.y += 3.2F;//TODO make number right
        }

        if (currentAnimation == null) {
            animationState.stop();
            lastAnimation = null;
            return;
        }

        if (currentAnimation != lastAnimation) {
            animationState.stop();
            animationState.start(entity.tickCount);
            lastAnimation = currentAnimation;
        } else {
            animationState.startIfStopped(entity.tickCount);
        }

        this.animate(animationState, currentAnimation, ageInTicks, 1.0F);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    public ModelPart body() {
        return body;
    }
}