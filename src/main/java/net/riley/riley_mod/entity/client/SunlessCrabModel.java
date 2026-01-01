package net.riley.riley_mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.entity.animations.RileyModAnimationDefinitions;
import net.riley.riley_mod.entity.custom.SunlessCrabEntity;

public class SunlessCrabModel<T extends Entity> extends HierarchicalModel<T> {

	private final ModelPart sunless_crab;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart left_eye;
	private final ModelPart right_eye;
	private final ModelPart leftarm;
	private final ModelPart left_elbow;
	private final ModelPart left_knuckle;
	private final ModelPart rightarm;
	private final ModelPart right_elbow;
	private final ModelPart right_knuckle;
	private final ModelPart left_leg_1;
	private final ModelPart left_knee_1;
	private final ModelPart left_ancle_1;
	private final ModelPart right_leg_1;
	private final ModelPart right_knee_1;
	private final ModelPart right_ancle_1;
	private final ModelPart left_leg_2;
	private final ModelPart left_knee_2;
	private final ModelPart left_ancle_2;
	private final ModelPart right_leg_2;
	private final ModelPart right_knee_2;
	private final ModelPart right_ancle_2;
	private final ModelPart left_leg_3;
	private final ModelPart left_knee_3;
	private final ModelPart left_ancle_3;
	private final ModelPart right_leg_3;
	private final ModelPart right_knee_3;
	private final ModelPart right_ancle_3;
	private final ModelPart left_leg_4;
	private final ModelPart left_knee_4;
	private final ModelPart left_ancle_4;
	private final ModelPart right_leg_4;
	private final ModelPart right_knee_4;
	private final ModelPart right_ancle_4;

	public SunlessCrabModel(ModelPart root) {
		this.sunless_crab = root.getChild("sunless_crab");
		this.body = this.sunless_crab.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.left_eye = this.head.getChild("left_eye");
		this.right_eye = this.head.getChild("right_eye");
		this.leftarm = this.torso.getChild("leftarm");
		this.left_elbow = this.leftarm.getChild("left_elbow");
		this.left_knuckle = this.left_elbow.getChild("left_knuckle");
		this.rightarm = this.torso.getChild("rightarm");
		this.right_elbow = this.rightarm.getChild("right_elbow");
		this.right_knuckle = this.right_elbow.getChild("right_knuckle");
		this.left_leg_1 = this.torso.getChild("left_leg_1");
		this.left_knee_1 = this.left_leg_1.getChild("left_knee_1");
		this.left_ancle_1 = this.left_knee_1.getChild("left_ancle_1");
		this.right_leg_1 = this.torso.getChild("right_leg_1");
		this.right_knee_1 = this.right_leg_1.getChild("right_knee_1");
		this.right_ancle_1 = this.right_knee_1.getChild("right_ancle_1");
		this.left_leg_2 = this.torso.getChild("left_leg_2");
		this.left_knee_2 = this.left_leg_2.getChild("left_knee_2");
		this.left_ancle_2 = this.left_knee_2.getChild("left_ancle_2");
		this.right_leg_2 = this.torso.getChild("right_leg_2");
		this.right_knee_2 = this.right_leg_2.getChild("right_knee_2");
		this.right_ancle_2 = this.right_knee_2.getChild("right_ancle_2");
		this.left_leg_3 = this.torso.getChild("left_leg_3");
		this.left_knee_3 = this.left_leg_3.getChild("left_knee_3");
		this.left_ancle_3 = this.left_knee_3.getChild("left_ancle_3");
		this.right_leg_3 = this.torso.getChild("right_leg_3");
		this.right_knee_3 = this.right_leg_3.getChild("right_knee_3");
		this.right_ancle_3 = this.right_knee_3.getChild("right_ancle_3");
		this.left_leg_4 = this.torso.getChild("left_leg_4");
		this.left_knee_4 = this.left_leg_4.getChild("left_knee_4");
		this.left_ancle_4 = this.left_knee_4.getChild("left_ancle_4");
		this.right_leg_4 = this.torso.getChild("right_leg_4");
		this.right_knee_4 = this.right_leg_4.getChild("right_knee_4");
		this.right_ancle_4 = this.right_knee_4.getChild("right_ancle_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sunless_crab = partdefinition.addOrReplaceChild("sunless_crab", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));

		PartDefinition body = sunless_crab.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -2.0F, -12.0F, 18.0F, 6.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 23).addBox(-8.0F, -5.0F, -1.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 5.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 39).addBox(-5.0F, -3.0F, -7.0F, 10.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

		PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(80, 75).addBox(-1.0F, -6.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, -5.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, -6.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -2.0F, -5.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition leftarm = torso.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(70, 6).addBox(-1.0F, -2.0F, -2.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 1.0F, -11.0F, 0.0F, 0.3927F, 0.0F));

		PartDefinition left_elbow = leftarm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(0, 53).addBox(0.0F, -2.0F, -2.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 75).addBox(0.0F, -2.0F, -5.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, 0.0F, 0.0F, 0.7418F, 0.0F));

		PartDefinition left_knuckle = left_elbow.addOrReplaceChild("left_knuckle", CubeListBuilder.create().texOffs(74, 45).addBox(0.0F, -2.0F, -1.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -4.0F));

		PartDefinition rightarm = torso.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(70, 12).addBox(-12.0F, -2.0F, -2.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 1.0F, -11.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition right_elbow = rightarm.addOrReplaceChild("right_elbow", CubeListBuilder.create().texOffs(34, 57).addBox(-14.0F, -2.0F, -2.0F, 14.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 75).addBox(-5.0F, -2.0F, -5.0F, 5.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition right_knuckle = right_elbow.addOrReplaceChild("right_knuckle", CubeListBuilder.create().texOffs(74, 51).addBox(-8.0F, -2.0F, -1.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -4.0F));

		PartDefinition left_leg_1 = torso.addOrReplaceChild("left_leg_1", CubeListBuilder.create().texOffs(34, 39).addBox(-1.0F, -2.0F, -1.0F, 17.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 2.0F, -7.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition left_knee_1 = left_leg_1.addOrReplaceChild("left_knee_1", CubeListBuilder.create().texOffs(0, 59).addBox(-1.0F, -2.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition left_ancle_1 = left_knee_1.addOrReplaceChild("left_ancle_1", CubeListBuilder.create().texOffs(64, 63).addBox(0.0F, -1.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0036F));

		PartDefinition right_leg_1 = torso.addOrReplaceChild("right_leg_1", CubeListBuilder.create().texOffs(34, 45).addBox(-16.0F, -2.0F, -1.0F, 17.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 2.0F, -7.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition right_knee_1 = right_leg_1.addOrReplaceChild("right_knee_1", CubeListBuilder.create().texOffs(32, 63).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition right_ancle_1 = right_knee_1.addOrReplaceChild("right_ancle_1", CubeListBuilder.create().texOffs(0, 65).addBox(-13.0F, -1.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition left_leg_2 = torso.addOrReplaceChild("left_leg_2", CubeListBuilder.create().texOffs(34, 51).addBox(-1.0F, -2.0F, -1.0F, 17.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 2.0F, -2.0F, 0.0F, -0.1309F, -0.3054F));

		PartDefinition left_knee_2 = left_leg_2.addOrReplaceChild("left_knee_2", CubeListBuilder.create().texOffs(68, 57).addBox(-1.0F, -2.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition left_ancle_2 = left_knee_2.addOrReplaceChild("left_ancle_2", CubeListBuilder.create().texOffs(64, 69).addBox(0.0F, -1.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0036F));

		PartDefinition right_leg_2 = torso.addOrReplaceChild("right_leg_2", CubeListBuilder.create().texOffs(52, 23).addBox(-16.0F, -2.0F, -1.0F, 17.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 2.0F, -2.0F, 0.0F, 0.1309F, 0.3054F));

		PartDefinition right_knee_2 = right_leg_2.addOrReplaceChild("right_knee_2", CubeListBuilder.create().texOffs(32, 69).addBox(-12.0F, -2.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));

		PartDefinition right_ancle_2 = right_knee_2.addOrReplaceChild("right_ancle_2", CubeListBuilder.create().texOffs(70, 0).addBox(-13.0F, -1.0F, -1.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition left_leg_3 = torso.addOrReplaceChild("left_leg_3", CubeListBuilder.create().texOffs(52, 29).addBox(-1.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 1.0F, 3.0F, 0.1068F, -0.3786F, -0.2823F));

		PartDefinition left_knee_3 = left_leg_3.addOrReplaceChild("left_knee_3", CubeListBuilder.create().texOffs(70, 18).addBox(-1.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.829F));

		PartDefinition left_ancle_3 = left_knee_3.addOrReplaceChild("left_ancle_3", CubeListBuilder.create().texOffs(0, 71).addBox(-1.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0036F));

		PartDefinition right_leg_3 = torso.addOrReplaceChild("right_leg_3", CubeListBuilder.create().texOffs(52, 33).addBox(-16.0F, -1.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 1.0F, 3.0F, 0.1068F, 0.3786F, 0.2823F));

		PartDefinition right_knee_3 = right_leg_3.addOrReplaceChild("right_knee_3", CubeListBuilder.create().texOffs(74, 41).addBox(-12.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));

		PartDefinition right_ancle_3 = right_knee_3.addOrReplaceChild("right_ancle_3", CubeListBuilder.create().texOffs(74, 37).addBox(-12.0F, -1.0F, -1.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0036F));

		PartDefinition left_leg_4 = torso.addOrReplaceChild("left_leg_4", CubeListBuilder.create().texOffs(32, 75).addBox(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 2.0F, 9.0F, -0.3927F, -0.4363F, 0.0F));

		PartDefinition left_knee_4 = left_leg_4.addOrReplaceChild("left_knee_4", CubeListBuilder.create().texOffs(64, 75).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition left_ancle_4 = left_knee_4.addOrReplaceChild("left_ancle_4", CubeListBuilder.create().texOffs(48, 79).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, 0.0F, -1.0036F, 0.0F));

		PartDefinition right_leg_4 = torso.addOrReplaceChild("right_leg_4", CubeListBuilder.create().texOffs(48, 75).addBox(-5.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 2.0F, 9.0F, -0.3927F, 0.4363F, 0.0F));

		PartDefinition right_knee_4 = right_leg_4.addOrReplaceChild("right_knee_4", CubeListBuilder.create().texOffs(32, 79).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.7418F, 0.0F));

		PartDefinition right_ancle_4 = right_knee_4.addOrReplaceChild("right_ancle_4", CubeListBuilder.create().texOffs(64, 79).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, 0.0F, 0.0F, 1.0036F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(RileyModAnimationDefinitions.SUNLESS_CRAB_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		this.animate(((SunlessCrabEntity) entity).idleAnimationState, RileyModAnimationDefinitions.SUNLESS_CRAB_IDLE,ageInTicks, 1f);
		this.animate(((SunlessCrabEntity) entity).attackAnimationState, RileyModAnimationDefinitions.SUNLESS_CRAB_ATTACK,ageInTicks, 1f);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		sunless_crab.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return sunless_crab;
	}
}