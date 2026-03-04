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
import net.riley.riley_mod.entity.animations.TrisonAnimationDefinitions;
import net.riley.riley_mod.entity.custom.TrisonEntity;

public class TrisonModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "trisonmodel"), "main");
	private final ModelPart trison;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart tail_base;
	private final ModelPart tail_mid;
	private final ModelPart tail_end;
	private final ModelPart head;
	private final ModelPart beard;
	private final ModelPart left_horn_base;
	private final ModelPart left_horn_end;
	private final ModelPart right_horn_base;
	private final ModelPart right_horn_end;
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
	private final ModelPart r_right_knee2;
	private final ModelPart r_right_ancle2;
	private final ModelPart r_right_hoof2;

	public TrisonModel(ModelPart root) {
		this.trison = root.getChild("trison");
		this.body = this.trison.getChild("body");
		this.torso = this.body.getChild("torso");
		this.tail_base = this.torso.getChild("tail_base");
		this.tail_mid = this.tail_base.getChild("tail_mid");
		this.tail_end = this.tail_mid.getChild("tail_end");
		this.head = this.torso.getChild("head");
		this.beard = this.head.getChild("beard");
		this.left_horn_base = this.head.getChild("left_horn_base");
		this.left_horn_end = this.left_horn_base.getChild("left_horn_end");
		this.right_horn_base = this.head.getChild("right_horn_base");
		this.right_horn_end = this.right_horn_base.getChild("right_horn_end");
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
		this.r_right_knee2 = this.r_right_leg.getChild("r_right_knee2");
		this.r_right_ancle2 = this.r_right_knee2.getChild("r_right_ancle2");
		this.r_right_hoof2 = this.r_right_ancle2.getChild("r_right_hoof2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition trison = partdefinition.addOrReplaceChild("trison", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition body = trison.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -8.0F, -22.0F, 18.0F, 19.0F, 29.0F, new CubeDeformation(0.0F))
		.texOffs(0, 48).addBox(-9.0F, -7.0F, 7.0F, 18.0F, 17.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(158, 32).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 32.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition tail_mid = tail_base.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(94, 32).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition tail_end = tail_mid.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(48, 158).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(30, 158).addBox(0.0F, -2.0F, 2.0F, 0.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 14.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(112, 110).addBox(-5.0F, -6.0F, -12.0F, 10.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -22.0F));

		PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(128, 157).addBox(0.0F, 0.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -5.0F));

		PartDefinition left_horn_base = head.addOrReplaceChild("left_horn_base", CubeListBuilder.create().texOffs(140, 22).addBox(-1.0F, -2.0F, -2.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.0F, -3.0F, 0.0F, 0.4363F, -0.3054F));

		PartDefinition left_horn_end = left_horn_base.addOrReplaceChild("left_horn_end", CubeListBuilder.create().texOffs(126, 106).addBox(-1.0F, -1.5F, -1.75F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition right_horn_base = head.addOrReplaceChild("right_horn_base", CubeListBuilder.create().texOffs(156, 123).addBox(-12.0F, -2.0F, -2.0F, 13.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.0F, -3.0F, 0.0F, -0.4363F, 0.3054F));

		PartDefinition right_horn_end = right_horn_base.addOrReplaceChild("right_horn_end", CubeListBuilder.create().texOffs(140, 28).addBox(-11.0F, -1.5F, -1.75F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bridle = head.addOrReplaceChild("bridle", CubeListBuilder.create().texOffs(58, 167).addBox(5.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(66, 167).addBox(-7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -10.0F));

		PartDefinition rein = bridle.addOrReplaceChild("rein", CubeListBuilder.create().texOffs(86, 48).addBox(6.0F, -8.0F, -1.0F, 0.0F, 9.0F, 33.0F, new CubeDeformation(0.0F))
		.texOffs(0, 90).addBox(-6.0F, -8.0F, -1.0F, 0.0F, 9.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.7F, 0.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition saddle = torso.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(66, 90).addBox(-6.0F, -2.0F, -9.0F, 12.0F, 2.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(30, 154).addBox(-1.0F, -4.0F, -9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 132).addBox(-5.0F, -8.0F, 7.0F, 10.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -6.0F));

		PartDefinition chest = torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(94, 0).addBox(9.0F, -5.0F, -10.5F, 3.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(66, 110).addBox(-12.0F, -5.0F, -10.5F, 3.0F, 12.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(0, 132).addBox(-9.5F, -8.5F, -8.0F, 19.0F, 20.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(112, 135).addBox(-9.5F, -8.5F, 5.0F, 19.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition f1_left_leg = body.addOrReplaceChild("f1_left_leg", CubeListBuilder.create().texOffs(154, 135).addBox(-1.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.0F, -17.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f1_left_knee = f1_left_leg.addOrReplaceChild("f1_left_knee", CubeListBuilder.create().texOffs(126, 32).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f1_left_hoof = f1_left_knee.addOrReplaceChild("f1_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r1 = f1_left_hoof.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(92, 158).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r2 = f1_left_hoof.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(158, 85).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition f1_right_leg = body.addOrReplaceChild("f1_right_leg", CubeListBuilder.create().texOffs(156, 106).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 11.0F, -17.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f1_right_knee = f1_right_leg.addOrReplaceChild("f1_right_knee", CubeListBuilder.create().texOffs(42, 142).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f1_right_hoof = f1_right_knee.addOrReplaceChild("f1_right_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r3 = f1_right_hoof.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(166, 0).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r4 = f1_right_hoof.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(92, 165).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition f2_left_leg = body.addOrReplaceChild("f2_left_leg", CubeListBuilder.create().texOffs(154, 152).addBox(-1.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 11.0F, -5.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f2_left_knee = f2_left_leg.addOrReplaceChild("f2_left_knee", CubeListBuilder.create().texOffs(126, 90).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f2_left_hoof = f2_left_knee.addOrReplaceChild("f2_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r5 = f2_left_hoof.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(158, 99).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r6 = f2_left_hoof.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(158, 92).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition f2_right_leg = body.addOrReplaceChild("f2_right_leg", CubeListBuilder.create().texOffs(106, 157).addBox(-3.5F, -3.0F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 11.0F, -5.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition f2_right_knee = f2_right_leg.addOrReplaceChild("f2_right_knee", CubeListBuilder.create().texOffs(74, 142).addBox(-2.0F, -2.0F, -10.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, -3.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition f2_right_hoof = f2_right_knee.addOrReplaceChild("f2_right_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -2.0F, -10.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r7 = f2_right_hoof.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(166, 14).addBox(0.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r8 = f2_right_hoof.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(166, 7).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition r_left_leg = body.addOrReplaceChild("r_left_leg", CubeListBuilder.create().texOffs(140, 0).addBox(-4.0F, -5.0F, -3.0F, 6.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 10.0F, 21.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition r_left_knee = r_left_leg.addOrReplaceChild("r_left_knee", CubeListBuilder.create().texOffs(152, 70).addBox(-3.5F, -3.0F, -2.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 4.0F));

		PartDefinition r_left_ancle = r_left_knee.addOrReplaceChild("r_left_ancle", CubeListBuilder.create().texOffs(64, 158).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 6.0F));

		PartDefinition r_left_hoof = r_left_ancle.addOrReplaceChild("r_left_hoof", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 4.0F, -2.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r9 = r_left_hoof.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(158, 42).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition cube_r10 = r_left_hoof.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(156, 129).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition r_right_leg = body.addOrReplaceChild("r_right_leg", CubeListBuilder.create().texOffs(140, 0).mirror().addBox(-2.0F, -4.0F, -5.0F, 6.0F, 15.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 10.0F, 23.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition r_right_knee2 = r_right_leg.addOrReplaceChild("r_right_knee2", CubeListBuilder.create().texOffs(0, 154).addBox(-1.5F, -4.0F, -2.0F, 5.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0746F, 2.3132F));

		PartDefinition r_right_ancle2 = r_right_knee2.addOrReplaceChild("r_right_ancle2", CubeListBuilder.create().texOffs(78, 158).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 6.0F));

		PartDefinition r_right_hoof2 = r_right_ancle2.addOrReplaceChild("r_right_hoof2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 4.0F, -2.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r11 = r_right_hoof2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(166, 129).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition cube_r12 = r_right_hoof2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(48, 166).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.0436F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(TrisonAnimationDefinitions.TRISON_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		this.animate(((TrisonEntity) entity).idleAnimationState,TrisonAnimationDefinitions.TRISON_IDLE,ageInTicks,1f);

		TrisonEntity TrisonEntity = (TrisonEntity) entity;

		// Mule-like visuals:
		this.saddle.visible = TrisonEntity.isSaddled();
		this.chest.visible = TrisonEntity.hasChest();

		// "Reins" usually only show when saddled AND being ridden/controlled.
		// If you want them visible whenever saddled, drop the isVehicle() check.
		this.rein.visible = TrisonEntity.isSaddled() && TrisonEntity.isVehicle();
		this.bridle.visible = TrisonEntity.isSaddled() && TrisonEntity.isVehicle();
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		trison.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return trison;
	}
}