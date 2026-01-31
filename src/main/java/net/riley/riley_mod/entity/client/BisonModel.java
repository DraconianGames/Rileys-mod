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
import net.riley.riley_mod.entity.animations.BisonAnimationDefinitions;
import net.riley.riley_mod.entity.custom.BisonEntity;

public class BisonModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "bison"), "main");
	private final ModelPart bison;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart beard;
	private final ModelPart Left_horn;
	private final ModelPart Right_horn;
	private final ModelPart bridle;
	private final ModelPart reins;
	private final ModelPart r_reins;
	private final ModelPart l_reins;
	private final ModelPart tail_base;
	private final ModelPart tail_mid;
	private final ModelPart tail_end;
	private final ModelPart saddle;
	private final ModelPart chest;
	private final ModelPart f_left_leg;
	private final ModelPart f_left_knee;
	private final ModelPart f_left_ancle;
	private final ModelPart f_right_leg;
	private final ModelPart f_right_knee;
	private final ModelPart f_right_ancle;
	private final ModelPart r_left_leg;
	private final ModelPart r_left_knee;
	private final ModelPart r_left_ancle;
	private final ModelPart r_right_leg;
	private final ModelPart r_right_knee;
	private final ModelPart r_right_ancle;

	public BisonModel(ModelPart root) {
		this.bison = root.getChild("bison");
		this.body = this.bison.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.beard = this.head.getChild("beard");
		this.Left_horn = this.head.getChild("Left_horn");
		this.Right_horn = this.head.getChild("Right_horn");
		this.bridle = this.head.getChild("bridle");
		this.reins = this.head.getChild("reins");
		this.r_reins = this.reins.getChild("r_reins");
		this.l_reins = this.reins.getChild("l_reins");
		this.tail_base = this.torso.getChild("tail_base");
		this.tail_mid = this.tail_base.getChild("tail_mid");
		this.tail_end = this.tail_mid.getChild("tail_end");
		this.saddle = this.torso.getChild("saddle");
		this.chest = this.torso.getChild("chest");
		this.f_left_leg = this.body.getChild("f_left_leg");
		this.f_left_knee = this.f_left_leg.getChild("f_left_knee");
		this.f_left_ancle = this.f_left_knee.getChild("f_left_ancle");
		this.f_right_leg = this.body.getChild("f_right_leg");
		this.f_right_knee = this.f_right_leg.getChild("f_right_knee");
		this.f_right_ancle = this.f_right_knee.getChild("f_right_ancle");
		this.r_left_leg = this.body.getChild("r_left_leg");
		this.r_left_knee = this.r_left_leg.getChild("r_left_knee");
		this.r_left_ancle = this.r_left_knee.getChild("r_left_ancle");
		this.r_right_leg = this.body.getChild("r_right_leg");
		this.r_right_knee = this.r_right_leg.getChild("r_right_knee");
		this.r_right_ancle = this.r_right_knee.getChild("r_right_ancle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bison = partdefinition.addOrReplaceChild("bison", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition body = bison.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -5.0F, -11.0F, 10.0F, 12.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 64).addBox(-3.0F, -4.0F, -7.0F, 6.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

		PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create().texOffs(80, 83).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, -4.0F));

		PartDefinition Left_horn = head.addOrReplaceChild("Left_horn", CubeListBuilder.create(), PartPose.offset(3.0F, -2.0F, -3.0F));

		PartDefinition cube_r1 = Left_horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(80, 91).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3F, 0.0F, 0.0F, 0.3367F, -0.741F, -0.2339F));

		PartDefinition cube_r2 = Left_horn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 78).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition Right_horn = head.addOrReplaceChild("Right_horn", CubeListBuilder.create(), PartPose.offset(-3.0F, -2.0F, -3.0F));

		PartDefinition cube_r3 = Right_horn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(84, 91).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3F, 0.0F, 0.0F, 0.3367F, 0.741F, 0.2339F));

		PartDefinition cube_r4 = Right_horn.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(32, 78).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bridle = head.addOrReplaceChild("bridle", CubeListBuilder.create().texOffs(26, 74).addBox(-3.5F, 2.0F, -7.25F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(38, 78).addBox(3.5F, 2.5F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 78).addBox(-4.5F, 2.5F, -6.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition reins = head.addOrReplaceChild("reins", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition r_reins = reins.addOrReplaceChild("r_reins", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.5F, -5.75F));

		PartDefinition cube_r5 = r_reins.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(46, 61).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8F, -0.6F, 0.8731F, -0.0308F, -0.0309F));

		PartDefinition l_reins = reins.addOrReplaceChild("l_reins", CubeListBuilder.create(), PartPose.offset(4.0F, 3.5F, -5.75F));

		PartDefinition cube_r6 = l_reins.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(46, 39).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8F, -0.6F, 0.8731F, 0.0308F, 0.0309F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(84, 79).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 16.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition tail_mid = tail_base.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(38, 70).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition tail_end = tail_mid.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(84, 35).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(38, 64).addBox(0.0F, -1.0F, 3.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition saddle = torso.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(0, 39).addBox(-5.5F, -5.5F, -9.0F, 11.0F, 13.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(74, 35).addBox(-1.0F, -7.5F, -9.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 30).addBox(-5.5F, -8.5F, 1.0F, 11.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chest = torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(22, 80).addBox(-7.5F, -2.0F, 4.0F, 2.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 80).addBox(5.5F, -2.0F, 4.0F, 2.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(74, 0).addBox(-5.5F, -5.5F, 10.0F, 11.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 15).addBox(-5.5F, -5.5F, 5.0F, 11.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.3F));

		PartDefinition f_left_leg = body.addOrReplaceChild("f_left_leg", CubeListBuilder.create().texOffs(26, 64).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 7.0F, -7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition f_left_knee = f_left_leg.addOrReplaceChild("f_left_knee", CubeListBuilder.create().texOffs(84, 41).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.7243F, 0.0F, 0.0F));

		PartDefinition f_left_ancle = f_left_knee.addOrReplaceChild("f_left_ancle", CubeListBuilder.create().texOffs(90, 85).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -2.0F, 0.2705F, 0.0F, 0.0F));

		PartDefinition f_right_leg = body.addOrReplaceChild("f_right_leg", CubeListBuilder.create().texOffs(68, 83).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 7.0F, -7.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition f_right_knee = f_right_leg.addOrReplaceChild("f_right_knee", CubeListBuilder.create().texOffs(84, 65).addBox(-1.0F, 0.0F, -3.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, -0.7243F, 0.0F, 0.0F));

		PartDefinition f_right_ancle = f_right_knee.addOrReplaceChild("f_right_ancle", CubeListBuilder.create().texOffs(90, 90).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -2.0F, 0.2705F, 0.0F, 0.0F));

		PartDefinition r_left_leg = body.addOrReplaceChild("r_left_leg", CubeListBuilder.create().texOffs(56, 83).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 7.0F, 14.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition r_left_knee = r_left_leg.addOrReplaceChild("r_left_knee", CubeListBuilder.create().texOffs(84, 57).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, 0.5585F, 0.0F, 0.0F));

		PartDefinition r_left_ancle = r_left_knee.addOrReplaceChild("r_left_ancle", CubeListBuilder.create().texOffs(90, 79).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 3.0F, -0.2531F, 0.0F, 0.0F));

		PartDefinition r_right_leg = body.addOrReplaceChild("r_right_leg", CubeListBuilder.create().texOffs(44, 83).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 7.0F, 14.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition r_right_knee = r_right_leg.addOrReplaceChild("r_right_knee", CubeListBuilder.create().texOffs(84, 49).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -3.0F, 0.5585F, 0.0F, 0.0F));

		PartDefinition r_right_ancle = r_right_knee.addOrReplaceChild("r_right_ancle", CubeListBuilder.create().texOffs(84, 73).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 3.0F, -0.2531F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

this.animateWalk(BisonAnimationDefinitions.BISON_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
this.animate(((BisonEntity) entity).idleAnimationState,BisonAnimationDefinitions.BISON_IDLE,ageInTicks,1f);

		BisonEntity bisonEntity = (BisonEntity) entity;

		// Mule-like visuals:
		this.saddle.visible = bisonEntity.isSaddled();
		this.chest.visible = bisonEntity.hasChest();

		// "Reins" usually only show when saddled AND being ridden/controlled.
		// If you want them visible whenever saddled, drop the isVehicle() check.
		this.reins.visible = bisonEntity.isSaddled() && bisonEntity.isVehicle();
		this.bridle.visible = bisonEntity.isSaddled() && bisonEntity.isVehicle();


	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bison.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return bison;
	}
}