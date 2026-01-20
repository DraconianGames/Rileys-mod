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
import net.riley.riley_mod.entity.animations.SkullFairyAnimationDefinitions;
import net.riley.riley_mod.entity.custom.SkullFairyEntity;


public class SkullFairyModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "skullfairymodel"), "main");
	private final ModelPart skull_fairy;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart left_horn_1_base;
	private final ModelPart left_horn_1_mid;
	private final ModelPart left_horn_1_tip;
	private final ModelPart right_horn_1_base;
	private final ModelPart right_horn_1_mid;
	private final ModelPart right_horn_1_tip;
	private final ModelPart lefthorn2base;
	private final ModelPart righthorn2base;
	private final ModelPart left_tail_base;
	private final ModelPart left_tail_mid;
	private final ModelPart left_tail_end;
	private final ModelPart right_tail_base;
	private final ModelPart right_tail_mid;
	private final ModelPart right_tail_end;
	private final ModelPart left_arm;
	private final ModelPart left_elbow;
	private final ModelPart left_wrist;
	private final ModelPart right_arm;
	private final ModelPart right_elbow;
	private final ModelPart right_wrist;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart right_leg;
	private final ModelPart right_knee3;
	private final ModelPart right_ancle2;
	private final ModelPart left_wing;
	private final ModelPart left_wing_mid;
	private final ModelPart left_wing_end;
	private final ModelPart right_wing;
	private final ModelPart right_wing_mid;
	private final ModelPart right_wing_end;

	public SkullFairyModel(ModelPart root) {
		this.skull_fairy = root.getChild("skull_fairy");
		this.body = this.skull_fairy.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.left_horn_1_base = this.head.getChild("left_horn_1_base");
		this.left_horn_1_mid = this.left_horn_1_base.getChild("left_horn_1_mid");
		this.left_horn_1_tip = this.left_horn_1_mid.getChild("left_horn_1_tip");
		this.right_horn_1_base = this.head.getChild("right_horn_1_base");
		this.right_horn_1_mid = this.right_horn_1_base.getChild("right_horn_1_mid");
		this.right_horn_1_tip = this.right_horn_1_mid.getChild("right_horn_1_tip");
		this.lefthorn2base = this.head.getChild("lefthorn2base");
		this.righthorn2base = this.head.getChild("righthorn2base");
		this.left_tail_base = this.torso.getChild("left_tail_base");
		this.left_tail_mid = this.left_tail_base.getChild("left_tail_mid");
		this.left_tail_end = this.left_tail_mid.getChild("left_tail_end");
		this.right_tail_base = this.torso.getChild("right_tail_base");
		this.right_tail_mid = this.right_tail_base.getChild("right_tail_mid");
		this.right_tail_end = this.right_tail_mid.getChild("right_tail_end");
		this.left_arm = this.body.getChild("left_arm");
		this.left_elbow = this.left_arm.getChild("left_elbow");
		this.left_wrist = this.left_elbow.getChild("left_wrist");
		this.right_arm = this.body.getChild("right_arm");
		this.right_elbow = this.right_arm.getChild("right_elbow");
		this.right_wrist = this.right_elbow.getChild("right_wrist");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee3 = this.right_leg.getChild("right_knee3");
		this.right_ancle2 = this.right_knee3.getChild("right_ancle2");
		this.left_wing = this.body.getChild("left_wing");
		this.left_wing_mid = this.left_wing.getChild("left_wing_mid");
		this.left_wing_end = this.left_wing_mid.getChild("left_wing_end");
		this.right_wing = this.body.getChild("right_wing");
		this.right_wing_mid = this.right_wing.getChild("right_wing_mid");
		this.right_wing_end = this.right_wing_mid.getChild("right_wing_end");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition skull_fairy = partdefinition.addOrReplaceChild("skull_fairy", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = skull_fairy.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 192).addBox(-12.0F, -4.0F, -20.0F, 24.0F, 15.0F, 26.0F, new CubeDeformation(0.0F))
		.texOffs(204, 72).addBox(-11.0F, -2.0F, 6.0F, 22.0F, 10.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(290, 72).addBox(-5.0F, -3.0F, -27.0F, 10.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(188, 266).addBox(-6.0F, -7.0F, -10.0F, 12.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(188, 285).addBox(-6.0F, -3.0F, -21.0F, 12.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(242, 282).addBox(5.0F, 0.0F, -20.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(138, 298).addBox(-6.0F, 0.0F, -20.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(290, 96).addBox(5.0F, 0.0F, -16.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(296, 96).addBox(-6.0F, 0.0F, -16.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -26.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 288).addBox(-5.0F, 0.0F, -11.0F, 10.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(244, 112).addBox(4.0F, -2.0F, -10.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(250, 112).addBox(-5.0F, -2.0F, -10.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(256, 112).addBox(4.0F, -1.0F, -6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(262, 112).addBox(-5.0F, -1.0F, -6.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

		PartDefinition left_horn_1_base = head.addOrReplaceChild("left_horn_1_base", CubeListBuilder.create().texOffs(90, 233).addBox(-1.0F, -9.7071F, -1.7071F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.0F, -2.0F, -0.7854F, 0.2618F, 0.0F));

		PartDefinition left_horn_1_mid = left_horn_1_base.addOrReplaceChild("left_horn_1_mid", CubeListBuilder.create().texOffs(234, 282).addBox(-1.1228F, -6.9705F, -1.2074F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.5672F, -0.1745F, 0.0F));

		PartDefinition left_horn_1_tip = left_horn_1_mid.addOrReplaceChild("left_horn_1_tip", CubeListBuilder.create().texOffs(292, 217).addBox(-1.1569F, -6.8156F, -0.4431F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.7854F, -0.1745F, 0.0F));

		PartDefinition right_horn_1_base = head.addOrReplaceChild("right_horn_1_base", CubeListBuilder.create().texOffs(90, 244).addBox(-1.0F, -9.7071F, -1.7071F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -5.0F, -2.0F, -0.7854F, -0.2618F, 0.0F));

		PartDefinition right_horn_1_mid = right_horn_1_base.addOrReplaceChild("right_horn_1_mid", CubeListBuilder.create().texOffs(292, 209).addBox(-0.8772F, -6.9705F, -1.2074F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.5672F, 0.1745F, 0.0F));

		PartDefinition right_horn_1_tip = right_horn_1_mid.addOrReplaceChild("right_horn_1_tip", CubeListBuilder.create().texOffs(130, 298).addBox(-0.8431F, -6.8156F, -0.4431F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.7854F, 0.1745F, 0.0F));

		PartDefinition lefthorn2base = head.addOrReplaceChild("lefthorn2base", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, 2.0F, -3.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition cube_r1 = lefthorn2base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(204, 112).addBox(-1.4617F, -3.8568F, -1.2296F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, -0.2618F, 0.0F, 0.48F));

		PartDefinition righthorn2base = head.addOrReplaceChild("righthorn2base", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, 2.0F, -3.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition cube_r2 = righthorn2base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(224, 112).addBox(-6.5382F, -3.8568F, -1.2296F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, -0.2618F, 0.0F, -0.48F));

		PartDefinition left_tail_base = torso.addOrReplaceChild("left_tail_base", CubeListBuilder.create().texOffs(100, 228).addBox(-3.0F, -5.0F, -1.0F, 6.0F, 7.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 4.0F, 27.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition left_tail_mid = left_tail_base.addOrReplaceChild("left_tail_mid", CubeListBuilder.create().texOffs(64, 266).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 5.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 30.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition left_tail_end = left_tail_mid.addOrReplaceChild("left_tail_end", CubeListBuilder.create().texOffs(0, 255).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(100, 192).addBox(1.0F, -1.0F, 0.0F, 12.0F, 0.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(204, 0).addBox(-13.0F, -1.0F, 0.0F, 12.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 26.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition right_tail_base = torso.addOrReplaceChild("right_tail_base", CubeListBuilder.create().texOffs(174, 228).addBox(-3.0F, -5.0F, -1.0F, 6.0F, 7.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 4.0F, 27.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_tail_mid = right_tail_base.addOrReplaceChild("right_tail_mid", CubeListBuilder.create().texOffs(126, 266).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 5.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 30.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition right_tail_end = right_tail_mid.addOrReplaceChild("right_tail_end", CubeListBuilder.create().texOffs(248, 257).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 30.0F, new CubeDeformation(0.0F))
		.texOffs(196, 192).addBox(1.0F, -1.0F, 0.0F, 12.0F, 0.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(204, 36).addBox(-13.0F, -1.0F, 0.0F, 12.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 26.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(282, 290).addBox(0.0F, -3.9397F, -2.658F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 3.0F, -15.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_elbow = left_arm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(42, 288).addBox(-2.0F, -2.9962F, -3.0872F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 9.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition left_wrist = left_elbow.addOrReplaceChild("left_wrist", CubeListBuilder.create().texOffs(64, 255).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(292, 192).addBox(-7.0F, -3.9397F, -2.658F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 3.0F, -15.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_elbow = right_arm.addOrReplaceChild("right_elbow", CubeListBuilder.create().texOffs(62, 298).addBox(-3.0F, -2.9962F, -3.0872F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 9.0F, 0.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition right_wrist = right_elbow.addOrReplaceChild("right_wrist", CubeListBuilder.create().texOffs(290, 87).addBox(-3.0F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(234, 290).addBox(0.0F, -2.0F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, 19.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(98, 298).addBox(-2.0F, -1.4253F, -1.3488F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 12.0F, 1.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(84, 255).addBox(-2.0F, -0.4824F, -1.9319F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5747F, 0.6512F, -0.2618F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(258, 290).addBox(-6.0F, -2.0F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 19.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition right_knee3 = right_leg.addOrReplaceChild("right_knee3", CubeListBuilder.create().texOffs(114, 298).addBox(-2.0F, -1.4253F, -1.3488F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.0F, 1.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition right_ancle2 = right_knee3.addOrReplaceChild("right_ancle2", CubeListBuilder.create().texOffs(232, 274).addBox(-2.0F, -0.4824F, -1.9319F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.5747F, 0.6512F, -0.2618F, 0.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 233).addBox(0.0F, -3.0F, -3.0F, 39.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 157).addBox(-1.0F, 0.0F, 3.0F, 40.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -5.0F, -16.0F));

		PartDefinition left_wing_mid = left_wing.addOrReplaceChild("left_wing_mid", CubeListBuilder.create().texOffs(248, 237).addBox(-1.0F, -2.0F, -3.0F, 38.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 116).addBox(0.0F, 0.0F, 3.0F, 37.0F, 0.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(39.0F, 0.0F, 0.0F));

		PartDefinition left_wing_end = left_wing_mid.addOrReplaceChild("left_wing_end", CubeListBuilder.create().texOffs(204, 103).addBox(-1.0F, -1.0F, -3.0F, 45.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, 0.0F, 3.0F, 44.0F, 0.0F, 58.0F, new CubeDeformation(0.0F)), PartPose.offset(37.0F, 0.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 244).addBox(-39.0F, -3.0F, -3.0F, 39.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(150, 157).addBox(-39.0F, 0.0F, 3.0F, 40.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -5.0F, -16.0F));

		PartDefinition right_wing_mid = right_wing.addOrReplaceChild("right_wing_mid", CubeListBuilder.create().texOffs(248, 247).addBox(-37.0F, -2.0F, -3.0F, 38.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(156, 116).addBox(-37.0F, 0.0F, 3.0F, 37.0F, 0.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(-39.0F, 0.0F, 0.0F));

		PartDefinition right_wing_end = right_wing_mid.addOrReplaceChild("right_wing_end", CubeListBuilder.create().texOffs(248, 228).addBox(-44.0F, -1.0F, -3.0F, 45.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-44.0F, 0.0F, 3.0F, 44.0F, 0.0F, 58.0F, new CubeDeformation(0.0F)), PartPose.offset(-37.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		SkullFairyEntity fairy = (SkullFairyEntity) entity;
		// Map the Entity States to the Animation Definitions
		this.animate(fairy.idleAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_IDLE, ageInTicks);
		this.animate(fairy.walkAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_WALK, ageInTicks);
		this.animate(fairy.flyAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_FLY, ageInTicks);
		this.animate(fairy.begAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_BEG, ageInTicks);
		this.animate(fairy.sitAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_SIT, ageInTicks);

		this.animate(fairy.attackAnimationState, SkullFairyAnimationDefinitions.SKULL_FAIRY_BITE, ageInTicks);
//TODO add transformation animation after it transforms only from the skeleton fairy. Like how the warden only plays the animation when summoned by shreeker.
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		skull_fairy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public ModelPart root() {
		return skull_fairy;
	}
}