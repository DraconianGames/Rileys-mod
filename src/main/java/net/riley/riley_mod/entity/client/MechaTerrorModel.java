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
import net.minecraft.world.entity.Entity;

public class MechaTerrorModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mechaterror"), "main");
	private final ModelPart mecha_terror;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart jaw_left;
	private final ModelPart jaw_right;
	private final ModelPart cannon;
	private final ModelPart barrels;
	private final ModelPart left_horn;
	private final ModelPart left_horn_mid;
	private final ModelPart left_horn_end;
	private final ModelPart right_horn;
	private final ModelPart right_horn_mid;
	private final ModelPart right_horn_end;
	private final ModelPart tail_base;
	private final ModelPart tail_mid;
	private final ModelPart tail_end;
	private final ModelPart back_tank;
	private final ModelPart fluid;
	private final ModelPart t_left_claw;
	private final ModelPart t_left_claw_end;
	private final ModelPart t_left_claw2;
	private final ModelPart t_left_claw_end2;
	private final ModelPart t_right_claw2;
	private final ModelPart t_right_claw_end2;
	private final ModelPart t_right_claw;
	private final ModelPart t_right_claw_end;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_ancle;
	private final ModelPart right_foot;

	public MechaTerrorModel(ModelPart root) {
		this.mecha_terror = root.getChild("mecha_terror");
		this.body = this.mecha_terror.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.jaw_left = this.head.getChild("jaw_left");
		this.jaw_right = this.head.getChild("jaw_right");
		this.cannon = this.head.getChild("cannon");
		this.barrels = this.cannon.getChild("barrels");
		this.left_horn = this.head.getChild("left_horn");
		this.left_horn_mid = this.left_horn.getChild("left_horn_mid");
		this.left_horn_end = this.left_horn_mid.getChild("left_horn_end");
		this.right_horn = this.head.getChild("right_horn");
		this.right_horn_mid = this.right_horn.getChild("right_horn_mid");
		this.right_horn_end = this.right_horn_mid.getChild("right_horn_end");
		this.tail_base = this.torso.getChild("tail_base");
		this.tail_mid = this.tail_base.getChild("tail_mid");
		this.tail_end = this.tail_mid.getChild("tail_end");
		this.back_tank = this.torso.getChild("back_tank");
		this.fluid = this.back_tank.getChild("fluid");
		this.t_left_claw = this.back_tank.getChild("t_left_claw");
		this.t_left_claw_end = this.t_left_claw.getChild("t_left_claw_end");
		this.t_left_claw2 = this.back_tank.getChild("t_left_claw2");
		this.t_left_claw_end2 = this.t_left_claw2.getChild("t_left_claw_end2");
		this.t_right_claw2 = this.back_tank.getChild("t_right_claw2");
		this.t_right_claw_end2 = this.t_right_claw2.getChild("t_right_claw_end2");
		this.t_right_claw = this.back_tank.getChild("t_right_claw");
		this.t_right_claw_end = this.t_right_claw.getChild("t_right_claw_end");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.left_foot = this.left_ancle.getChild("left_foot");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.right_ancle = this.right_knee.getChild("right_ancle");
		this.right_foot = this.right_ancle.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mecha_terror = partdefinition.addOrReplaceChild("mecha_terror", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition body = mecha_terror.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(84, 0).addBox(-7.0F, -10.0F, -18.0F, 14.0F, 14.0F, 19.0F, new CubeDeformation(0.0F))
		.texOffs(0, 86).addBox(-7.0F, -10.0F, 1.0F, 14.0F, 15.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(58, 124).addBox(-6.0F, -7.0F, -13.0F, 12.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -18.0F));

		PartDefinition jaw_left = head.addOrReplaceChild("jaw_left", CubeListBuilder.create().texOffs(108, 124).addBox(-6.0F, -5.0F, -17.0F, 6.0F, 12.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -2.0F, -13.0F));

		PartDefinition jaw_right = head.addOrReplaceChild("jaw_right", CubeListBuilder.create().texOffs(0, 148).addBox(0.0F, -5.0F, -17.0F, 6.0F, 12.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -2.0F, -13.0F));

		PartDefinition cannon = head.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(62, 86).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -13.0F));

		PartDefinition barrels = cannon.addOrReplaceChild("barrels", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -2.0F));

		PartDefinition cube_r1 = barrels.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(62, 116).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(110, 33).addBox(-2.5F, -2.5F, -5.0F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -8.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = barrels.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(160, 82).addBox(-1.0F, -1.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = barrels.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(156, 156).addBox(-1.0F, -1.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r4 = barrels.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(156, 140).addBox(-1.0F, -1.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r5 = barrels.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(154, 124).addBox(-1.0F, -1.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(160, 112).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -5.0F, -4.0F, 1.1472F, 0.573F, 0.8771F));

		PartDefinition left_horn_mid = left_horn.addOrReplaceChild("left_horn_mid", CubeListBuilder.create().texOffs(166, 58).addBox(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 7.0F, 1.2874F, 0.4926F, 0.1781F));

		PartDefinition left_horn_end = left_horn_mid.addOrReplaceChild("left_horn_end", CubeListBuilder.create().texOffs(160, 98).addBox(-2.5373F, -0.6106F, -0.2519F, 3.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.48F, 0.5672F, 0.0F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(166, 47).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -5.0F, -4.0F, 1.1472F, -0.573F, -0.8771F));

		PartDefinition right_horn_mid = right_horn.addOrReplaceChild("right_horn_mid", CubeListBuilder.create().texOffs(166, 69).addBox(-1.0F, -1.0F, -2.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 7.0F, 1.2874F, -0.4926F, -0.1781F));

		PartDefinition right_horn_end = right_horn_mid.addOrReplaceChild("right_horn_end", CubeListBuilder.create().texOffs(166, 33).addBox(-0.4627F, -0.6106F, -0.2519F, 3.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, 0.48F, -0.5672F, 0.0F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(0, 118).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 11.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 18.0F));

		PartDefinition tail_mid = tail_base.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(82, 82).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 9.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

		PartDefinition tail_end = tail_mid.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 6.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 33.0F));

		PartDefinition back_tank = torso.addOrReplaceChild("back_tank", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -11.0F, -12.0F, 10.0F, 11.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -3.0F));

		PartDefinition fluid = back_tank.addOrReplaceChild("fluid", CubeListBuilder.create().texOffs(82, 43).addBox(-5.0F, -7.0F, -12.0F, 10.0F, 7.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition t_left_claw = back_tank.addOrReplaceChild("t_left_claw", CubeListBuilder.create().texOffs(76, 97).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, -8.0F));

		PartDefinition t_left_claw_end = t_left_claw.addOrReplaceChild("t_left_claw_end", CubeListBuilder.create().texOffs(110, 40).addBox(-4.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition t_left_claw2 = back_tank.addOrReplaceChild("t_left_claw2", CubeListBuilder.create().texOffs(76, 110).addBox(0.0F, -11.0F, -1.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, 14.0F));

		PartDefinition t_left_claw_end2 = t_left_claw2.addOrReplaceChild("t_left_claw_end2", CubeListBuilder.create().texOffs(124, 33).addBox(-4.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition t_right_claw2 = back_tank.addOrReplaceChild("t_right_claw2", CubeListBuilder.create().texOffs(156, 172).addBox(-1.0F, -11.0F, -1.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 14.0F));

		PartDefinition t_right_claw_end2 = t_right_claw2.addOrReplaceChild("t_right_claw_end2", CubeListBuilder.create().texOffs(124, 36).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition t_right_claw = back_tank.addOrReplaceChild("t_right_claw", CubeListBuilder.create().texOffs(162, 172).addBox(-1.0F, -11.0F, -1.0F, 1.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, -8.0F));

		PartDefinition t_right_claw_end = t_right_claw.addOrReplaceChild("t_right_claw_end", CubeListBuilder.create().texOffs(124, 39).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(46, 149).addBox(0.0F, -6.0F, -5.0F, 7.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -2.0F, 8.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(80, 153).addBox(-3.0F, -6.0F, 0.0F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 17.0F, 5.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(84, 33).addBox(-2.0F, 0.0F, -4.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

		PartDefinition left_foot = left_ancle.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -2.0F));

		PartDefinition cube_r6 = left_foot.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(98, 33).addBox(-2.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0036F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(150, 0).addBox(-7.0F, -6.0F, -5.0F, 7.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -2.0F, 8.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(118, 153).addBox(-2.0F, -6.0F, 0.0F, 5.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 17.0F, 5.0F));

		PartDefinition right_ancle = right_knee.addOrReplaceChild("right_ancle", CubeListBuilder.create().texOffs(62, 97).addBox(-1.0F, 0.0F, -4.0F, 3.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

		PartDefinition right_foot = right_ancle.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, -2.0F));

		PartDefinition cube_r7 = right_foot.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(62, 107).addBox(-1.0F, -2.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.0036F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mecha_terror.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return mecha_terror;
	}
}