package net.riley.riley_mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.entity.animations.BoneFairyAnimationDefinitions;
import net.riley.riley_mod.entity.custom.BoneFairyEntity;


public class BoneFairyModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "bonefairymodel"), "main");
	private final ModelPart bonefairy;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tail1;
	private final ModelPart tail2;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_ancle;
	private final ModelPart left_arm;
	private final ModelPart left_elbow;
	private final ModelPart left_wrist;
	private final ModelPart right_arm;
	private final ModelPart right_elbow2;
	private final ModelPart right_wrist;
	private final ModelPart left_wing;
	private final ModelPart left_wing2;
	private final ModelPart right_wing;
	private final ModelPart right_wing2;

	public BoneFairyModel(ModelPart root) {
		this.bonefairy = root.getChild("bonefairy");
		this.body = this.bonefairy.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tail1 = this.torso.getChild("tail1");
		this.tail2 = this.tail1.getChild("tail2");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.right_ancle = this.right_knee.getChild("right_ancle");
		this.left_arm = this.body.getChild("left_arm");
		this.left_elbow = this.left_arm.getChild("left_elbow");
		this.left_wrist = this.left_elbow.getChild("left_wrist");
		this.right_arm = this.body.getChild("right_arm");
		this.right_elbow2 = this.right_arm.getChild("right_elbow2");
		this.right_wrist = this.right_elbow2.getChild("right_wrist");
		this.left_wing = this.body.getChild("left_wing");
		this.left_wing2 = this.left_wing.getChild("left_wing2");
		this.right_wing = this.body.getChild("right_wing");
		this.right_wing2 = this.right_wing.getChild("right_wing2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bonefairy = partdefinition.addOrReplaceChild("bonefairy", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition body = bonefairy.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -16.0F, -3.0F, 12.0F, 16.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(70, 34).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 65).addBox(-4.0F, -3.0F, -10.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(28, 67).addBox(-4.0F, 0.0F, -6.0F, 8.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.0F));

		PartDefinition tail1 = torso.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(30, 43).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(72, 69).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(58, 43).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, 8.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r1 = left_ancle.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(82, 40).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 73).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -1.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(60, 56).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_ancle = right_knee.addOrReplaceChild("right_ancle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, 8.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r2 = right_ancle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(72, 82).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 69).addBox(0.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -12.0F, 0.0F, -0.1736F, -0.0226F, -0.0843F));

		PartDefinition left_elbow = left_arm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(16, 73).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 8.0F, 0.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition left_wrist = left_elbow.addOrReplaceChild("left_wrist", CubeListBuilder.create().texOffs(40, 74).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(42, 20).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(58, 20).addBox(-2.0F, 7.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 10.0F, 0.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(70, 20).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -12.0F, 0.0F, -0.1736F, 0.0226F, 0.0843F));

		PartDefinition right_elbow2 = right_arm.addOrReplaceChild("right_elbow2", CubeListBuilder.create().texOffs(28, 74).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 0.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition right_wrist = right_elbow2.addOrReplaceChild("right_wrist", CubeListBuilder.create().texOffs(78, 8).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 20).addBox(-1.0F, 5.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(64, 20).addBox(1.0F, 7.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 10.0F, 0.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(42, 8).addBox(0.0F, -2.0F, -2.0F, 15.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 54).addBox(0.0F, 1.0F, 0.0F, 15.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -14.0F, 5.0F));

		PartDefinition left_wing2 = left_wing.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(42, 0).addBox(0.0F, -1.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 25).addBox(0.0F, 1.0F, 0.0F, 19.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, 0.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(42, 14).addBox(-15.0F, -2.0F, -2.0F, 15.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(30, 56).addBox(-15.0F, 1.0F, 0.0F, 15.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -14.0F, 5.0F));

		PartDefinition right_wing2 = right_wing.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(42, 4).addBox(-19.0F, -1.0F, -1.0F, 19.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 34).addBox(-19.0F, 1.0F, 0.0F, 19.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		BoneFairyEntity fairy = (BoneFairyEntity) entity;
		// Map the Entity States to the Animation Definitions
		this.animate(fairy.idleAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_IDLE, ageInTicks);
		this.animate(fairy.walkAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_WALK, ageInTicks);
		this.animate(fairy.flyAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_FLY, ageInTicks);
		this.animate(fairy.begAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_BEG, ageInTicks);
		this.animate(fairy.sitAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_SIT, ageInTicks);

		if (fairy.getAttackSide() == 0) {
			this.animate(fairy.attackAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_LEFT_ATTACK, ageInTicks);
		} else {
			this.animate(fairy.attackAnimationState, BoneFairyAnimationDefinitions.BONEFAIRY_RIGHT_ATTACK, ageInTicks);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bonefairy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public ModelPart root() {
		return bonefairy;
	}
}