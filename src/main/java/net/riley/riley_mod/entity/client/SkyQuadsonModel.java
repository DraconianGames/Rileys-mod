package net.riley.riley_mod.entity.client;// Made with Blockbench 5.1.6
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
import net.riley.riley_mod.entity.animations.SkyQuadsonAnimationDefinitions;
import net.riley.riley_mod.entity.custom.SkyQuadsonEntity;
public class SkyQuadsonModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "sky_quadson"), "main");
	private final ModelPart quadson;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart tail_base;
	private final ModelPart tail_mid;
	private final ModelPart tail_end;
	private final ModelPart head;
	private final ModelPart beard;
	private final ModelPart right_horn_base;
	private final ModelPart right_horn_mid;
	private final ModelPart right_horn_end;
	private final ModelPart left_horn_base;
	private final ModelPart left_horn_mid;
	private final ModelPart left_horn_end;
	private final ModelPart bridle;
	private final ModelPart rein;
	private final ModelPart saddle;
	private final ModelPart chest;
	private final ModelPart f1_left_leg;
	private final ModelPart f1_left_knee;
	private final ModelPart f1_left_hoof;
	private final ModelPart f1_right_leg;
	private final ModelPart f1_right_knee;
	private final ModelPart f1_right_hoof;
	private final ModelPart f2_left_leg;
	private final ModelPart f2_left_knee;
	private final ModelPart f2_left_hoof;
	private final ModelPart f2_right_leg;
	private final ModelPart f2_right_knee;
	private final ModelPart f2_right_hoof;
	private final ModelPart r_left_leg;
	private final ModelPart r_left_knee;
	private final ModelPart r_left_ancle;
	private final ModelPart r_left_hoof;
	private final ModelPart r_right_leg;
	private final ModelPart r_right_knee;
	private final ModelPart r_right_ancle;
	private final ModelPart r_right_hoof;
	private final ModelPart left_wing;
	private final ModelPart left_wing_mid;
	private final ModelPart left_wing_end;
	private final ModelPart right_wing;
	private final ModelPart right_wing_mid;
	private final ModelPart right_wing_end;

	public SkyQuadsonModel(ModelPart root) {
		this.quadson = root.getChild("quadson");
		this.body = this.quadson.getChild("body");
		this.torso = this.body.getChild("torso");
		this.tail_base = this.torso.getChild("tail_base");
		this.tail_mid = this.tail_base.getChild("tail_mid");
		this.tail_end = this.tail_mid.getChild("tail_end");
		this.head = this.torso.getChild("head");
		this.beard = this.head.getChild("beard");
		this.right_horn_base = this.head.getChild("right_horn_base");
		this.right_horn_mid = this.right_horn_base.getChild("right_horn_mid");
		this.right_horn_end = this.right_horn_mid.getChild("right_horn_end");
		this.left_horn_base = this.head.getChild("left_horn_base");
		this.left_horn_mid = this.left_horn_base.getChild("left_horn_mid");
		this.left_horn_end = this.left_horn_mid.getChild("left_horn_end");
		this.bridle = this.head.getChild("bridle");
		this.rein = this.bridle.getChild("rein");
		this.saddle = this.torso.getChild("saddle");
		this.chest = this.torso.getChild("chest");
		this.f1_left_leg = this.body.getChild("f1_left_leg");
		this.f1_left_knee = this.f1_left_leg.getChild("f1_left_knee");
		this.f1_left_hoof = this.f1_left_knee.getChild("f1_left_hoof");
		this.f1_right_leg = this.body.getChild("f1_right_leg");
		this.f1_right_knee = this.f1_right_leg.getChild("f1_right_knee");
		this.f1_right_hoof = this.f1_right_knee.getChild("f1_right_hoof");
		this.f2_left_leg = this.body.getChild("f2_left_leg");
		this.f2_left_knee = this.f2_left_leg.getChild("f2_left_knee");
		this.f2_left_hoof = this.f2_left_knee.getChild("f2_left_hoof");
		this.f2_right_leg = this.body.getChild("f2_right_leg");
		this.f2_right_knee = this.f2_right_leg.getChild("f2_right_knee");
		this.f2_right_hoof = this.f2_right_knee.getChild("f2_right_hoof");
		this.r_left_leg = this.body.getChild("r_left_leg");
		this.r_left_knee = this.r_left_leg.getChild("r_left_knee");
		this.r_left_ancle = this.r_left_knee.getChild("r_left_ancle");
		this.r_left_hoof = this.r_left_ancle.getChild("r_left_hoof");
		this.r_right_leg = this.body.getChild("r_right_leg");
		this.r_right_knee = this.r_right_leg.getChild("r_right_knee");
		this.r_right_ancle = this.r_right_knee.getChild("r_right_ancle");
		this.r_right_hoof = this.r_right_ancle.getChild("r_right_hoof");
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

		PartDefinition quadson = partdefinition.addOrReplaceChild("quadson", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition body = quadson.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 121).addBox(-9.0F, -8.0F, -22.0F, 18.0F, 19.0F, 29.0F, new CubeDeformation(0.0F))
				.texOffs(156, 0).addBox(-9.0F, -7.0F, 7.0F, 18.0F, 17.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(202, 121).addBox(-6.0F, -4.0F, 0.0F, 12.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 32.0F));

		PartDefinition tail_mid = tail_base.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(66, 217).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

		PartDefinition tail_end = tail_mid.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(80, 179).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 30.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.0F, 0.0F, 14.0F, 33.0F, 0.0F, 45.0F, new CubeDeformation(0.0F))
				.texOffs(0, 45).addBox(-36.0F, 0.0F, 14.0F, 33.0F, 0.0F, 45.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 24.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(218, 201).addBox(-5.0F, -6.0F, -12.0F, 10.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -22.0F));

		PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(170, 255).addBox(0.0F, 0.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -5.0F));

		PartDefinition right_horn_base = head.addOrReplaceChild("right_horn_base", CubeListBuilder.create().texOffs(134, 248).addBox(-13.0F, -2.0F, -3.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.0F, -3.0F, -0.215F, -0.2184F, 0.2958F));

		PartDefinition right_horn_mid = right_horn_base.addOrReplaceChild("right_horn_mid", CubeListBuilder.create().texOffs(58, 249).addBox(-16.0F, -1.0F, -3.0F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, -0.5F, 1.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition right_horn_end = right_horn_mid.addOrReplaceChild("right_horn_end", CubeListBuilder.create().texOffs(156, 86).addBox(-12.0F, -0.5F, -2.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition left_horn_base = head.addOrReplaceChild("left_horn_base", CubeListBuilder.create().texOffs(96, 249).addBox(-1.0F, -2.0F, -3.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.0F, -3.0F, -0.215F, 0.2184F, -0.2958F));

		PartDefinition left_horn_mid = left_horn_base.addOrReplaceChild("left_horn_mid", CubeListBuilder.create().texOffs(170, 249).addBox(0.0F, -1.0F, -3.0F, 16.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -0.5F, 1.0F, 0.0F, 1.0472F, 0.0F));

		PartDefinition left_horn_end = left_horn_mid.addOrReplaceChild("left_horn_end", CubeListBuilder.create().texOffs(184, 86).addBox(0.0F, -0.5F, -2.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition bridle = head.addOrReplaceChild("bridle", CubeListBuilder.create().texOffs(212, 86).addBox(5.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(134, 217).addBox(-7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -10.0F));

		PartDefinition rein = bridle.addOrReplaceChild("rein", CubeListBuilder.create().texOffs(152, 179).addBox(6.0F, -8.0F, -1.0F, 0.0F, 9.0F, 33.0F, new CubeDeformation(0.0F))
				.texOffs(0, 200).addBox(-6.0F, -8.0F, -1.0F, 0.0F, 9.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7F, 0.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition saddle = torso.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(202, 153).addBox(-6.0F, -2.0F, -9.0F, 12.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(142, 217).addBox(-1.0F, -4.0F, -9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(132, 256).addBox(-5.0F, -8.0F, 7.0F, 10.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -6.0F));

		PartDefinition chest = torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(228, 86).addBox(-9.5F, -0.5F, -6.0F, 19.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(134, 228).addBox(-9.5F, -0.5F, 5.0F, 19.0F, 18.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 169).addBox(-8.0F, -7.0F, -10.0F, 16.0F, 7.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 13.0F));

		PartDefinition f1_left_leg = body.addOrReplaceChild("f1_left_leg", CubeListBuilder.create().texOffs(208, 249).addBox(-1.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.0F, -17.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f1_left_knee = f1_left_leg.addOrReplaceChild("f1_left_knee", CubeListBuilder.create().texOffs(176, 233).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f1_left_hoof = f1_left_knee.addOrReplaceChild("f1_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r1 = f1_left_hoof.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(254, 226).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r2 = f1_left_hoof.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 209).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition f1_right_leg = body.addOrReplaceChild("f1_right_leg", CubeListBuilder.create().texOffs(230, 249).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 11.0F, -17.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f1_right_knee = f1_right_leg.addOrReplaceChild("f1_right_knee", CubeListBuilder.create().texOffs(208, 233).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f1_right_hoof = f1_right_knee.addOrReplaceChild("f1_right_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r3 = f1_right_hoof.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(190, 255).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r4 = f1_right_hoof.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(80, 255).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition f2_left_leg = body.addOrReplaceChild("f2_left_leg", CubeListBuilder.create().texOffs(252, 249).addBox(-1.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.0F, -5.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f2_left_knee = f2_left_leg.addOrReplaceChild("f2_left_knee", CubeListBuilder.create().texOffs(240, 233).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f2_left_hoof = f2_left_knee.addOrReplaceChild("f2_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r5 = f2_left_hoof.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(90, 257).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r6 = f2_left_hoof.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(156, 256).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition f2_right_leg = body.addOrReplaceChild("f2_right_leg", CubeListBuilder.create().texOffs(58, 255).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 11.0F, -5.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f2_right_knee = f2_right_leg.addOrReplaceChild("f2_right_knee", CubeListBuilder.create().texOffs(0, 242).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f2_right_hoof = f2_right_knee.addOrReplaceChild("f2_right_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r7 = f2_right_hoof.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(110, 257).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r8 = f2_right_hoof.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(100, 257).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition r_left_leg = body.addOrReplaceChild("r_left_leg", CubeListBuilder.create().texOffs(242, 0).addBox(-4.0F, -5.0F, -3.0F, 6.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 10.0F, 21.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition r_left_knee = r_left_leg.addOrReplaceChild("r_left_knee", CubeListBuilder.create().texOffs(228, 106).addBox(-3.5F, -3.0F, -2.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 4.0F));

		PartDefinition r_left_ancle = r_left_knee.addOrReplaceChild("r_left_ancle", CubeListBuilder.create().texOffs(80, 169).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 6.0F));

		PartDefinition r_left_hoof = r_left_ancle.addOrReplaceChild("r_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 4.0F, -2.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r9 = r_left_hoof.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(204, 221).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition cube_r10 = r_left_hoof.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(194, 221).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition r_right_leg = body.addOrReplaceChild("r_right_leg", CubeListBuilder.create().texOffs(242, 0).mirror().addBox(-2.0F, -5.0F, -3.0F, 6.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 10.0F, 21.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition r_right_knee = r_right_leg.addOrReplaceChild("r_right_knee", CubeListBuilder.create().texOffs(228, 106).mirror().addBox(-1.5F, -3.0F, -2.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 4.0F));

		PartDefinition r_right_ancle = r_right_knee.addOrReplaceChild("r_right_ancle", CubeListBuilder.create().texOffs(80, 169).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 6.0F));

		PartDefinition r_right_hoof = r_right_ancle.addOrReplaceChild("r_right_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 4.0F, -2.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r11 = r_right_hoof.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(204, 221).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition cube_r12 = r_right_hoof.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(194, 221).mirror().addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(218, 183).addBox(0.0F, -2.0F, -4.0F, 26.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 90).addBox(0.0F, 0.0F, 1.0F, 26.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -1.0F, -15.0F, 0.0F, 0.0F, 1.2217F));

		PartDefinition left_wing_mid = left_wing.addOrReplaceChild("left_wing_mid", CubeListBuilder.create().texOffs(134, 221).addBox(-1.0F, -1.5F, -1.5F, 26.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(94, 121).addBox(0.0F, 0.0F, 2.0F, 25.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(26.0F, 0.0F, -2.0F, 0.0F, 0.0F, -3.0543F));

		PartDefinition left_wing_end = left_wing_mid.addOrReplaceChild("left_wing_end", CubeListBuilder.create().texOffs(202, 173).addBox(-1.0F, -1.0F, -1.0F, 31.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(156, 42).addBox(0.0F, 0.0F, 2.0F, 30.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(25.0F, 0.0F, 0.0F, 0.0F, 0.0F, 3.0543F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(218, 192).addBox(-26.0F, -2.0F, -4.0F, 26.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(114, 90).addBox(-26.0F, 0.0F, 1.0F, 26.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -1.0F, -15.0F, 0.0F, 0.0F, -1.2217F));

		PartDefinition right_wing_mid = right_wing.addOrReplaceChild("right_wing_mid", CubeListBuilder.create().texOffs(194, 226).addBox(-25.0F, -1.5F, -1.5F, 26.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(94, 150).addBox(-25.0F, 0.0F, 2.0F, 25.0F, 0.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.0F, 0.0F, -2.0F, 0.0F, 0.0F, 3.0543F));

		PartDefinition right_wing_end = right_wing_mid.addOrReplaceChild("right_wing_end", CubeListBuilder.create().texOffs(218, 178).addBox(-30.0F, -1.0F, -1.0F, 31.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(156, 64).addBox(-30.0F, 0.0F, 2.0F, 30.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-25.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.0543F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(SkyQuadsonAnimationDefinitions.QUADSON_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		this.animate(((SkyQuadsonEntity) entity).idleAnimationState,SkyQuadsonAnimationDefinitions.QUADSON_IDLE,ageInTicks,1f);
		this.animate(((SkyQuadsonEntity) entity).flyAnimationState,SkyQuadsonAnimationDefinitions.QUADSON_FLY,ageInTicks,1f);

		SkyQuadsonEntity QuadsonEntity = (SkyQuadsonEntity) entity;


		this.saddle.visible = QuadsonEntity.isSaddled();
		this.chest.visible = QuadsonEntity.hasChest();

		this.rein.visible = QuadsonEntity.isSaddled() && QuadsonEntity.isVehicle();
		this.bridle.visible = QuadsonEntity.isSaddled() && QuadsonEntity.isVehicle();
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		quadson.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return quadson;
	}
}