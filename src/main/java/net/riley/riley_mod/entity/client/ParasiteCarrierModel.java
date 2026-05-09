package net.riley.riley_mod.entity.client;// Made with Blockbench 5.1.4
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
import net.riley.riley_mod.entity.animations.ParasiteCarrierAnimationDefinitions;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;

public class ParasiteCarrierModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "parasitecarrier"), "main");
	private final ModelPart carrier;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart tail_base;
	private final ModelPart tail_mid;
	private final ModelPart tail_mid_2;
	private final ModelPart tail_end;
	private final ModelPart tail_left_club;
	private final ModelPart tail_right_club;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart frill;
	private final ModelPart upper_jaw;
	private final ModelPart lower_jaw;
	private final ModelPart left_horn;
	private final ModelPart left_horn_end;
	private final ModelPart right_horn;
	private final ModelPart right_horn_end;
	private final ModelPart cannon;
	private final ModelPart barrel;
	private final ModelPart ribs;
	private final ModelPart rib_left_4;
	private final ModelPart rib_left_1_mid4;
	private final ModelPart rib_right_4;
	private final ModelPart rib_right_1_mid4;
	private final ModelPart rib_left_3;
	private final ModelPart rib_left_1_mid3;
	private final ModelPart rib_right_3;
	private final ModelPart rib_right_1_mid3;
	private final ModelPart rib_left_2;
	private final ModelPart rib_left_1_mid2;
	private final ModelPart rib_right_2;
	private final ModelPart rib_right_1_mid2;
	private final ModelPart rib_left_1;
	private final ModelPart rib_left_1_mid;
	private final ModelPart rib_right_1;
	private final ModelPart rib_right_1_mid;
	private final ModelPart spikes;
	private final ModelPart left_spike_1;
	private final ModelPart right_spike_1;
	private final ModelPart left_spike_2;
	private final ModelPart right_spike_2;
	private final ModelPart left_spike_3;
	private final ModelPart right_spike_3;
	private final ModelPart left_spike_4;
	private final ModelPart right_spike_4;
	private final ModelPart left_spike_5;
	private final ModelPart right_spike_5;
	private final ModelPart left_spike_6;
	private final ModelPart right_spike_6;
	private final ModelPart left_spike_7;
	private final ModelPart right_spike_7;
	private final ModelPart left_spike_8;
	private final ModelPart right_spike_8;
	private final ModelPart left_spike_9;
	private final ModelPart right_spike_9;
	private final ModelPart left_spike_10;
	private final ModelPart right_spike_10;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart left_ancle2;
	private final ModelPart right_foot;

	public ParasiteCarrierModel(ModelPart root) {
		this.carrier = root.getChild("carrier");
		this.body = this.carrier.getChild("body");
		this.torso = this.body.getChild("torso");
		this.tail_base = this.torso.getChild("tail_base");
		this.tail_mid = this.tail_base.getChild("tail_mid");
		this.tail_mid_2 = this.tail_mid.getChild("tail_mid_2");
		this.tail_end = this.tail_mid_2.getChild("tail_end");
		this.tail_left_club = this.tail_end.getChild("tail_left_club");
		this.tail_right_club = this.tail_end.getChild("tail_right_club");
		this.neck = this.torso.getChild("neck");
		this.head = this.neck.getChild("head");
		this.frill = this.head.getChild("frill");
		this.upper_jaw = this.head.getChild("upper_jaw");
		this.lower_jaw = this.head.getChild("lower_jaw");
		this.left_horn = this.head.getChild("left_horn");
		this.left_horn_end = this.left_horn.getChild("left_horn_end");
		this.right_horn = this.head.getChild("right_horn");
		this.right_horn_end = this.right_horn.getChild("right_horn_end");
		this.cannon = this.head.getChild("cannon");
		this.barrel = this.cannon.getChild("barrel");
		this.ribs = this.torso.getChild("ribs");
		this.rib_left_4 = this.ribs.getChild("rib_left_4");
		this.rib_left_1_mid4 = this.rib_left_4.getChild("rib_left_1_mid4");
		this.rib_right_4 = this.ribs.getChild("rib_right_4");
		this.rib_right_1_mid4 = this.rib_right_4.getChild("rib_right_1_mid4");
		this.rib_left_3 = this.ribs.getChild("rib_left_3");
		this.rib_left_1_mid3 = this.rib_left_3.getChild("rib_left_1_mid3");
		this.rib_right_3 = this.ribs.getChild("rib_right_3");
		this.rib_right_1_mid3 = this.rib_right_3.getChild("rib_right_1_mid3");
		this.rib_left_2 = this.ribs.getChild("rib_left_2");
		this.rib_left_1_mid2 = this.rib_left_2.getChild("rib_left_1_mid2");
		this.rib_right_2 = this.ribs.getChild("rib_right_2");
		this.rib_right_1_mid2 = this.rib_right_2.getChild("rib_right_1_mid2");
		this.rib_left_1 = this.ribs.getChild("rib_left_1");
		this.rib_left_1_mid = this.rib_left_1.getChild("rib_left_1_mid");
		this.rib_right_1 = this.ribs.getChild("rib_right_1");
		this.rib_right_1_mid = this.rib_right_1.getChild("rib_right_1_mid");
		this.spikes = this.torso.getChild("spikes");
		this.left_spike_1 = this.spikes.getChild("left_spike_1");
		this.right_spike_1 = this.spikes.getChild("right_spike_1");
		this.left_spike_2 = this.spikes.getChild("left_spike_2");
		this.right_spike_2 = this.spikes.getChild("right_spike_2");
		this.left_spike_3 = this.spikes.getChild("left_spike_3");
		this.right_spike_3 = this.spikes.getChild("right_spike_3");
		this.left_spike_4 = this.spikes.getChild("left_spike_4");
		this.right_spike_4 = this.spikes.getChild("right_spike_4");
		this.left_spike_5 = this.spikes.getChild("left_spike_5");
		this.right_spike_5 = this.spikes.getChild("right_spike_5");
		this.left_spike_6 = this.spikes.getChild("left_spike_6");
		this.right_spike_6 = this.spikes.getChild("right_spike_6");
		this.left_spike_7 = this.spikes.getChild("left_spike_7");
		this.right_spike_7 = this.spikes.getChild("right_spike_7");
		this.left_spike_8 = this.spikes.getChild("left_spike_8");
		this.right_spike_8 = this.spikes.getChild("right_spike_8");
		this.left_spike_9 = this.spikes.getChild("left_spike_9");
		this.right_spike_9 = this.spikes.getChild("right_spike_9");
		this.left_spike_10 = this.spikes.getChild("left_spike_10");
		this.right_spike_10 = this.spikes.getChild("right_spike_10");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.left_foot = this.left_ancle.getChild("left_foot");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.left_ancle2 = this.right_knee.getChild("left_ancle2");
		this.right_foot = this.left_ancle2.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition carrier = partdefinition.addOrReplaceChild("carrier", CubeListBuilder.create(), PartPose.offset(0.0F, -36.0F, 0.0F));

		PartDefinition body = carrier.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -15.0F, -45.0F, 40.0F, 34.0F, 49.0F, new CubeDeformation(0.0F))
		.texOffs(170, 83).addBox(-16.0F, -15.0F, 4.0F, 32.0F, 32.0F, 46.0F, new CubeDeformation(0.0F))
		.texOffs(0, 168).addBox(-15.0F, -26.0F, -42.0F, 30.0F, 11.0F, 56.0F, new CubeDeformation(0.0F))
		.texOffs(128, 288).addBox(-12.0F, -22.0F, 14.0F, 24.0F, 7.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(0, 83).addBox(-13.0F, -8.0F, -1.0F, 26.0F, 26.0F, 59.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 50.0F));

		PartDefinition tail_mid = tail_base.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(178, 0).addBox(-10.0F, -7.0F, -1.0F, 20.0F, 21.0F, 54.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 58.0F));

		PartDefinition tail_mid_2 = tail_mid.addOrReplaceChild("tail_mid_2", CubeListBuilder.create().texOffs(172, 161).addBox(-6.0F, -8.0F, -1.0F, 12.0F, 17.0F, 63.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 53.0F));

		PartDefinition tail_end = tail_mid_2.addOrReplaceChild("tail_end", CubeListBuilder.create().texOffs(0, 235).addBox(-4.0F, -6.0F, -1.0F, 8.0F, 12.0F, 56.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 62.0F));

		PartDefinition tail_left_club = tail_end.addOrReplaceChild("tail_left_club", CubeListBuilder.create().texOffs(322, 206).addBox(-3.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 49.0F));

		PartDefinition cube_r1 = tail_left_club.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(88, 330).addBox(-3.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

		PartDefinition cube_r2 = tail_left_club.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(326, 78).addBox(-3.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r3 = tail_left_club.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(326, 110).addBox(-3.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition tail_right_club = tail_end.addOrReplaceChild("tail_right_club", CubeListBuilder.create().texOffs(322, 206).mirror().addBox(-13.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 49.0F));

		PartDefinition cube_r4 = tail_right_club.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(88, 330).mirror().addBox(-13.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

		PartDefinition cube_r5 = tail_right_club.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(326, 78).mirror().addBox(-13.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r6 = tail_right_club.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(326, 110).mirror().addBox(-13.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(152, 330).addBox(-8.0F, -8.0F, -11.0F, 16.0F, 17.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -45.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(322, 161).addBox(-12.0F, -10.0F, -24.0F, 24.0F, 20.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -11.0F));

		PartDefinition frill = head.addOrReplaceChild("frill", CubeListBuilder.create().texOffs(326, 42).addBox(-18.0F, -18.0F, -1.0F, 36.0F, 32.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(188, 359).addBox(18.0F, -15.0F, -1.0F, 2.0F, 25.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(132, 362).addBox(-20.0F, -15.0F, -1.0F, 2.0F, 25.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(326, 142).addBox(-15.0F, -26.0F, -1.0F, 30.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

		PartDefinition upper_jaw = head.addOrReplaceChild("upper_jaw", CubeListBuilder.create().texOffs(128, 241).addBox(-12.0F, 0.0F, -37.0F, 24.0F, 10.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -24.0F));

		PartDefinition lower_jaw = head.addOrReplaceChild("lower_jaw", CubeListBuilder.create().texOffs(250, 241).addBox(-12.0F, -10.0F, -37.0F, 24.0F, 10.0F, 37.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -24.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(152, 359).addBox(0.0F, -32.0F, -4.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -10.0F, -11.0F, 1.1678F, -0.1084F, 0.1897F));

		PartDefinition left_horn_end = left_horn.addOrReplaceChild("left_horn_end", CubeListBuilder.create().texOffs(44, 369).addBox(0.0F, -20.0F, 0.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -32.0F, -4.0F, -0.1741F, 0.022F, 0.1132F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(152, 359).mirror().addBox(-4.0F, -32.0F, -4.0F, 4.0F, 32.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -10.0F, -11.0F, 1.1678F, 0.1084F, -0.1897F));

		PartDefinition right_horn_end = right_horn.addOrReplaceChild("right_horn_end", CubeListBuilder.create().texOffs(44, 369).mirror().addBox(-4.0F, -20.0F, 0.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -32.0F, -4.0F, -0.1741F, -0.022F, -0.1132F));

		PartDefinition cannon = head.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(348, 353).addBox(-7.0F, -7.0F, -4.0F, 14.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -24.0F));

		PartDefinition barrel = cannon.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(326, 0).addBox(-5.0F, -5.0F, -32.0F, 10.0F, 10.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

		PartDefinition ribs = torso.addOrReplaceChild("ribs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rib_left_4 = ribs.addOrReplaceChild("rib_left_4", CubeListBuilder.create().texOffs(22, 369).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 18.0F, -3.0F, 0.1309F, 0.0F, 0.5236F));

		PartDefinition rib_left_1_mid4 = rib_left_4.addOrReplaceChild("rib_left_1_mid4", CubeListBuilder.create().texOffs(372, 238).addBox(-4.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition rib_right_4 = ribs.addOrReplaceChild("rib_right_4", CubeListBuilder.create().texOffs(22, 369).mirror().addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, 18.0F, -3.0F, 0.1309F, 0.0F, -0.5236F));

		PartDefinition rib_right_1_mid4 = rib_right_4.addOrReplaceChild("rib_right_1_mid4", CubeListBuilder.create().texOffs(372, 238).mirror().addBox(0.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 15.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition rib_left_3 = ribs.addOrReplaceChild("rib_left_3", CubeListBuilder.create().texOffs(0, 369).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 18.0F, -14.0F, 0.1309F, 0.0F, 0.5236F));

		PartDefinition rib_left_1_mid3 = rib_left_3.addOrReplaceChild("rib_left_1_mid3", CubeListBuilder.create().texOffs(370, 371).addBox(-4.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition rib_right_3 = ribs.addOrReplaceChild("rib_right_3", CubeListBuilder.create().texOffs(0, 369).mirror().addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, 18.0F, -14.0F, 0.1309F, 0.0F, -0.5236F));

		PartDefinition rib_right_1_mid3 = rib_right_3.addOrReplaceChild("rib_right_1_mid3", CubeListBuilder.create().texOffs(370, 371).mirror().addBox(0.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 15.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition rib_left_2 = ribs.addOrReplaceChild("rib_left_2", CubeListBuilder.create().texOffs(110, 362).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 18.0F, -26.0F, 0.1309F, 0.0F, 0.5236F));

		PartDefinition rib_left_1_mid2 = rib_left_2.addOrReplaceChild("rib_left_1_mid2", CubeListBuilder.create().texOffs(348, 371).addBox(-4.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition rib_right_2 = ribs.addOrReplaceChild("rib_right_2", CubeListBuilder.create().texOffs(110, 362).mirror().addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, 18.0F, -26.0F, 0.1309F, 0.0F, -0.5236F));

		PartDefinition rib_right_1_mid2 = rib_right_2.addOrReplaceChild("rib_right_1_mid2", CubeListBuilder.create().texOffs(348, 371).mirror().addBox(0.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 15.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition rib_left_1 = ribs.addOrReplaceChild("rib_left_1", CubeListBuilder.create().texOffs(88, 362).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 18.0F, -36.0F, 0.1309F, 0.0F, 0.5236F));

		PartDefinition rib_left_1_mid = rib_left_1.addOrReplaceChild("rib_left_1_mid", CubeListBuilder.create().texOffs(354, 322).addBox(-4.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 15.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition rib_right_1 = ribs.addOrReplaceChild("rib_right_1", CubeListBuilder.create().texOffs(88, 362).mirror().addBox(-2.0F, -2.0F, -5.0F, 4.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, 18.0F, -36.0F, 0.1309F, 0.0F, -0.5236F));

		PartDefinition rib_right_1_mid = rib_right_1.addOrReplaceChild("rib_right_1_mid", CubeListBuilder.create().texOffs(354, 322).mirror().addBox(0.0F, 0.0F, -5.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 15.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition spikes = torso.addOrReplaceChild("spikes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_spike_1 = spikes.addOrReplaceChild("left_spike_1", CubeListBuilder.create().texOffs(354, 288).addBox(0.0F, -29.0F, 0.0F, 5.0F, 29.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -15.0F, -41.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_1 = spikes.addOrReplaceChild("right_spike_1", CubeListBuilder.create().texOffs(354, 288).mirror().addBox(-5.0F, -29.0F, 0.0F, 5.0F, 29.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, -15.0F, -41.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_2 = spikes.addOrReplaceChild("left_spike_2", CubeListBuilder.create().texOffs(308, 340).addBox(0.0F, -33.0F, 0.0F, 5.0F, 33.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -15.0F, -32.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_2 = spikes.addOrReplaceChild("right_spike_2", CubeListBuilder.create().texOffs(308, 340).mirror().addBox(-5.0F, -33.0F, 0.0F, 5.0F, 33.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, -15.0F, -32.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_3 = spikes.addOrReplaceChild("left_spike_3", CubeListBuilder.create().texOffs(268, 340).addBox(0.0F, -39.0F, 0.0F, 5.0F, 39.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -15.0F, -23.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_3 = spikes.addOrReplaceChild("right_spike_3", CubeListBuilder.create().texOffs(268, 340).mirror().addBox(-5.0F, -39.0F, 0.0F, 5.0F, 39.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, -15.0F, -23.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_4 = spikes.addOrReplaceChild("left_spike_4", CubeListBuilder.create().texOffs(248, 340).addBox(0.0F, -42.0F, 0.0F, 5.0F, 42.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -15.0F, -14.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_4 = spikes.addOrReplaceChild("right_spike_4", CubeListBuilder.create().texOffs(248, 340).mirror().addBox(-5.0F, -42.0F, 0.0F, 5.0F, 42.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, -15.0F, -14.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_5 = spikes.addOrReplaceChild("left_spike_5", CubeListBuilder.create().texOffs(228, 340).addBox(-1.0F, -44.0F, 0.0F, 5.0F, 44.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -15.0F, -4.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_5 = spikes.addOrReplaceChild("right_spike_5", CubeListBuilder.create().texOffs(228, 340).mirror().addBox(-4.0F, -44.0F, 0.0F, 5.0F, 44.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.0F, -15.0F, -4.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_6 = spikes.addOrReplaceChild("left_spike_6", CubeListBuilder.create().texOffs(208, 330).addBox(0.0F, -45.0F, 0.0F, 5.0F, 45.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -15.0F, 5.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_6 = spikes.addOrReplaceChild("right_spike_6", CubeListBuilder.create().texOffs(208, 330).mirror().addBox(-5.0F, -45.0F, 0.0F, 5.0F, 45.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.0F, -15.0F, 5.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_7 = spikes.addOrReplaceChild("left_spike_7", CubeListBuilder.create().texOffs(288, 340).addBox(0.0F, -39.0F, 0.0F, 5.0F, 39.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 14.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_7 = spikes.addOrReplaceChild("right_spike_7", CubeListBuilder.create().texOffs(288, 340).mirror().addBox(-5.0F, -39.0F, 0.0F, 5.0F, 39.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -15.0F, 14.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_8 = spikes.addOrReplaceChild("left_spike_8", CubeListBuilder.create().texOffs(328, 340).addBox(0.0F, -33.0F, 0.0F, 5.0F, 33.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 23.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_8 = spikes.addOrReplaceChild("right_spike_8", CubeListBuilder.create().texOffs(328, 340).mirror().addBox(-5.0F, -33.0F, 0.0F, 5.0F, 33.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -15.0F, 23.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_9 = spikes.addOrReplaceChild("left_spike_9", CubeListBuilder.create().texOffs(168, 359).addBox(0.0F, -26.0F, 0.0F, 5.0F, 26.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 32.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_9 = spikes.addOrReplaceChild("right_spike_9", CubeListBuilder.create().texOffs(168, 359).mirror().addBox(-5.0F, -26.0F, 0.0F, 5.0F, 26.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -15.0F, 32.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_spike_10 = spikes.addOrReplaceChild("left_spike_10", CubeListBuilder.create().texOffs(60, 369).addBox(0.0F, -15.0F, 0.0F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -15.0F, 41.0F, -0.48F, 0.0F, 0.3491F));

		PartDefinition right_spike_10 = spikes.addOrReplaceChild("right_spike_10", CubeListBuilder.create().texOffs(60, 369).mirror().addBox(-5.0F, -15.0F, 0.0F, 5.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -15.0F, 41.0F, -0.48F, 0.0F, -0.3491F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 303).addBox(-8.0F, -7.0F, -13.0F, 19.0F, 41.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 0.0F, 33.0F, -0.8727F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(246, 288).addBox(-7.0F, -13.0F, 0.0F, 15.0F, 13.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 33.0F, 12.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(348, 340).addBox(-6.0F, 0.0F, 0.0F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 30.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition left_foot = left_ancle.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(88, 303).addBox(-5.0F, -4.0F, -4.0F, 11.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 5.0F, -0.9163F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 303).mirror().addBox(-11.0F, -7.0F, -13.0F, 19.0F, 41.0F, 25.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-16.0F, 0.0F, 33.0F, -0.8727F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(246, 288).mirror().addBox(-8.0F, -13.0F, 0.0F, 15.0F, 13.0F, 39.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 33.0F, 12.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_ancle2 = right_knee.addOrReplaceChild("left_ancle2", CubeListBuilder.create().texOffs(348, 340).mirror().addBox(-7.0F, 0.0F, 0.0F, 13.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 30.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition right_foot = left_ancle2.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(88, 303).mirror().addBox(-6.0F, -4.0F, -4.0F, 11.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, 5.0F, -0.9163F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		ParasiteCarrierEntity rex = (ParasiteCarrierEntity) entity;

		if (rex.isActivating()) {
			this.animate(rex.activationAnimationState, ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_ACTIVATION, ageInTicks, 1f);
			return;
		}
		if (rex.isUsingTailSmash()) {
			this.animate(rex.tailSwipeAnimationState, ParasiteCarrierAnimationDefinitions.TAIL_SMASH, ageInTicks, 1f);
			return;
		}
		if (rex.isPlayingPickUpAnimation()) {
			this.animate(rex.pickUpAnimationState, ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_PICK_UP, ageInTicks, 1f);
			return;
		}
		if (rex.isUsingInfectionBlast()) {
			this.animate(rex.infectionBlastAnimationState, ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_PARASITE_CLOUD, ageInTicks, 1f);
			return;
		}
		if (limbSwingAmount > 0.01F) {
			this.animateWalk(ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		}
		if (rex.isLaunchingBomb()) {
			this.animate(rex.launchBombAnimationState, ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_LAUNCH_BOMB, ageInTicks, 1f);
		}
		if (limbSwingAmount <= 0.01F  && !rex.isLaunchingBomb()) {
			this.animate(rex.idleAnimationState, ParasiteCarrierAnimationDefinitions.PARASITE_CARRIER_IDLE, ageInTicks, 1f);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		carrier.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public ModelPart root() {
		return carrier;
	}
}