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
import net.riley.riley_mod.entity.animations.MechaRexAnimationDefinitions;
import net.riley.riley_mod.entity.custom.MechaRexEntity;

public class MechaRexModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "mecharexmodel"), "main");
	private final ModelPart mecha_rex;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart left_spike_1;
	private final ModelPart left_spike_3;
	private final ModelPart left_spike_2;
	private final ModelPart left_spike_4;
	private final ModelPart right_spike_1;
	private final ModelPart right_spike_2;
	private final ModelPart right_spike_3;
	private final ModelPart right_spike_4;
	private final ModelPart right_spike;
	private final ModelPart left_spike;
	private final ModelPart head;
	private final ModelPart up_left_jaw;
	private final ModelPart up_right_jaw;
	private final ModelPart down_left_jaw;
	private final ModelPart down_right_jaw;
	private final ModelPart left_horn;
	private final ModelPart left_horn_2;
	private final ModelPart left_horn_3;
	private final ModelPart left_horn_end;
	private final ModelPart horn_end2;
	private final ModelPart left_horn_b2;
	private final ModelPart left_horn_b3;
	private final ModelPart left_horn_b4;
	private final ModelPart left_horn_b5;
	private final ModelPart left_horn_c1;
	private final ModelPart right_horn;
	private final ModelPart right_horn_2;
	private final ModelPart right_horn_3;
	private final ModelPart right_horn_end;
	private final ModelPart horn_end3;
	private final ModelPart right_horn_b2;
	private final ModelPart right_horn_b3;
	private final ModelPart right_horn_b4;
	private final ModelPart right_horn_b5;
	private final ModelPart right_horn_c1;
	private final ModelPart cannon;
	private final ModelPart barrel;
	private final ModelPart tail_base;
	private final ModelPart left_tail_spike;
	private final ModelPart left_tail_spike2;
	private final ModelPart right_tail_spike;
	private final ModelPart right_tail_spike2;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart left_tail_end1;
	private final ModelPart right_tail_end2;
	private final ModelPart right_tail_end1;
	private final ModelPart left_tail_end2;
	private final ModelPart leaft_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_ancle;
	private final ModelPart right_foot;

	public MechaRexModel(ModelPart root) {
		this.mecha_rex = root.getChild("mecha_rex");
		this.body = this.mecha_rex.getChild("body");
		this.torso = this.body.getChild("torso");
		this.left_spike_1 = this.torso.getChild("left_spike_1");
		this.left_spike_3 = this.torso.getChild("left_spike_3");
		this.left_spike_2 = this.torso.getChild("left_spike_2");
		this.left_spike_4 = this.torso.getChild("left_spike_4");
		this.right_spike_1 = this.torso.getChild("right_spike_1");
		this.right_spike_2 = this.torso.getChild("right_spike_2");
		this.right_spike_3 = this.torso.getChild("right_spike_3");
		this.right_spike_4 = this.torso.getChild("right_spike_4");
		this.right_spike = this.torso.getChild("right_spike");
		this.left_spike = this.torso.getChild("left_spike");
		this.head = this.torso.getChild("head");
		this.up_left_jaw = this.head.getChild("up_left_jaw");
		this.up_right_jaw = this.head.getChild("up_right_jaw");
		this.down_left_jaw = this.head.getChild("down_left_jaw");
		this.down_right_jaw = this.head.getChild("down_right_jaw");
		this.left_horn = this.head.getChild("left_horn");
		this.left_horn_2 = this.left_horn.getChild("left_horn_2");
		this.left_horn_3 = this.left_horn_2.getChild("left_horn_3");
		this.left_horn_end = this.left_horn_3.getChild("left_horn_end");
		this.horn_end2 = this.left_horn_3.getChild("horn_end2");
		this.left_horn_b2 = this.left_horn.getChild("left_horn_b2");
		this.left_horn_b3 = this.left_horn_b2.getChild("left_horn_b3");
		this.left_horn_b4 = this.left_horn_b3.getChild("left_horn_b4");
		this.left_horn_b5 = this.left_horn_b3.getChild("left_horn_b5");
		this.left_horn_c1 = this.left_horn_b3.getChild("left_horn_c1");
		this.right_horn = this.head.getChild("right_horn");
		this.right_horn_2 = this.right_horn.getChild("right_horn_2");
		this.right_horn_3 = this.right_horn_2.getChild("right_horn_3");
		this.right_horn_end = this.right_horn_3.getChild("right_horn_end");
		this.horn_end3 = this.right_horn_3.getChild("horn_end3");
		this.right_horn_b2 = this.right_horn.getChild("right_horn_b2");
		this.right_horn_b3 = this.right_horn_b2.getChild("right_horn_b3");
		this.right_horn_b4 = this.right_horn_b3.getChild("right_horn_b4");
		this.right_horn_b5 = this.right_horn_b3.getChild("right_horn_b5");
		this.right_horn_c1 = this.right_horn_b3.getChild("right_horn_c1");
		this.cannon = this.head.getChild("cannon");
		this.barrel = this.cannon.getChild("barrel");
		this.tail_base = this.torso.getChild("tail_base");
		this.left_tail_spike = this.tail_base.getChild("left_tail_spike");
		this.left_tail_spike2 = this.tail_base.getChild("left_tail_spike2");
		this.right_tail_spike = this.tail_base.getChild("right_tail_spike");
		this.right_tail_spike2 = this.tail_base.getChild("right_tail_spike2");
		this.tail_2 = this.tail_base.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.left_tail_end1 = this.tail_3.getChild("left_tail_end1");
		this.right_tail_end2 = this.tail_3.getChild("right_tail_end2");
		this.right_tail_end1 = this.tail_3.getChild("right_tail_end1");
		this.left_tail_end2 = this.tail_3.getChild("left_tail_end2");
		this.leaft_leg = this.body.getChild("leaft_leg");
		this.left_knee = this.leaft_leg.getChild("left_knee");
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

		PartDefinition mecha_rex = partdefinition.addOrReplaceChild("mecha_rex", CubeListBuilder.create(), PartPose.offset(9.0F, -32.0F, -19.0F));

		PartDefinition body = mecha_rex.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-9.0F, 13.0F, 28.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -14.0F, -17.0F, 28.0F, 27.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(114, 128).addBox(-13.0F, -13.0F, -48.0F, 26.0F, 22.0F, 31.0F, new CubeDeformation(0.0F))
		.texOffs(230, 102).addBox(-8.0F, -11.0F, -57.0F, 16.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_spike_1 = torso.addOrReplaceChild("left_spike_1", CubeListBuilder.create().texOffs(72, 239).addBox(0.0F, -43.0F, 0.0F, 4.0F, 43.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -13.0F, -28.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition left_spike_3 = torso.addOrReplaceChild("left_spike_3", CubeListBuilder.create().texOffs(0, 252).addBox(1.0F, -42.0F, 0.0F, 4.0F, 42.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -14.0F, -1.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition left_spike_2 = torso.addOrReplaceChild("left_spike_2", CubeListBuilder.create().texOffs(90, 185).addBox(0.0F, -49.0F, 0.0F, 4.0F, 49.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -14.0F, -15.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition left_spike_4 = torso.addOrReplaceChild("left_spike_4", CubeListBuilder.create().texOffs(36, 252).addBox(0.0F, -27.0F, 0.0F, 4.0F, 27.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -14.0F, 13.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition right_spike_1 = torso.addOrReplaceChild("right_spike_1", CubeListBuilder.create().texOffs(72, 185).addBox(-4.0F, -49.0F, 0.0F, 4.0F, 49.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -14.0F, -15.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition right_spike_2 = torso.addOrReplaceChild("right_spike_2", CubeListBuilder.create().texOffs(90, 239).addBox(-4.0F, -43.0F, 0.0F, 4.0F, 43.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -13.0F, -28.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition right_spike_3 = torso.addOrReplaceChild("right_spike_3", CubeListBuilder.create().texOffs(18, 252).addBox(-5.0F, -42.0F, 0.0F, 4.0F, 42.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -14.0F, -1.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition right_spike_4 = torso.addOrReplaceChild("right_spike_4", CubeListBuilder.create().texOffs(54, 252).addBox(-4.0F, -27.0F, 0.0F, 4.0F, 27.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -14.0F, 13.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition right_spike = torso.addOrReplaceChild("right_spike", CubeListBuilder.create().texOffs(264, 197).addBox(-3.0F, -23.0F, 0.0F, 4.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -13.0F, -39.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition left_spike = torso.addOrReplaceChild("left_spike", CubeListBuilder.create().texOffs(212, 35).addBox(-1.0F, -23.0F, 0.0F, 4.0F, 24.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -13.0F, -39.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(230, 35).addBox(-9.0F, -9.0F, -14.0F, 18.0F, 18.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -57.0F));

		PartDefinition up_left_jaw = head.addOrReplaceChild("up_left_jaw", CubeListBuilder.create().texOffs(0, 185).addBox(-9.0F, 0.0F, -26.0F, 9.0F, 9.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -9.0F, -14.0F));

		PartDefinition up_right_jaw = head.addOrReplaceChild("up_right_jaw", CubeListBuilder.create().texOffs(194, 181).addBox(0.0F, 0.0F, -26.0F, 9.0F, 9.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -9.0F, -14.0F));

		PartDefinition down_left_jaw = head.addOrReplaceChild("down_left_jaw", CubeListBuilder.create().texOffs(212, 0).addBox(-9.0F, -9.0F, -26.0F, 9.0F, 9.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 9.0F, -14.0F));

		PartDefinition down_right_jaw = head.addOrReplaceChild("down_right_jaw", CubeListBuilder.create().texOffs(194, 216).addBox(0.0F, -9.0F, -26.0F, 9.0F, 9.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 9.0F, -14.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(280, 102).addBox(0.0F, -4.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.3F, -3.0F, -4.0F, -0.2174F, -0.4802F, -0.6011F));

		PartDefinition left_horn_2 = left_horn.addOrReplaceChild("left_horn_2", CubeListBuilder.create().texOffs(282, 8).addBox(0.0F, -4.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, 2.0F, 0.0539F, 0.6148F, -0.1534F));

		PartDefinition left_horn_3 = left_horn_2.addOrReplaceChild("left_horn_3", CubeListBuilder.create().texOffs(264, 226).addBox(0.0F, -4.0F, -4.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 0.0F, 0.0F, 0.1745F, 0.7854F, 0.0F));

		PartDefinition left_horn_end = left_horn_3.addOrReplaceChild("left_horn_end", CubeListBuilder.create().texOffs(146, 274).addBox(0.0F, -4.0F, -4.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, 0.0F, 0.1745F, 0.5672F, 0.0F));

		PartDefinition horn_end2 = left_horn_3.addOrReplaceChild("horn_end2", CubeListBuilder.create().texOffs(280, 118).addBox(0.0F, -4.0F, 0.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, 0.0F, -4.0F, 0.1446F, -0.27F, -0.4996F));

		PartDefinition left_horn_b2 = left_horn.addOrReplaceChild("left_horn_b2", CubeListBuilder.create().texOffs(108, 275).addBox(0.0F, -4.0F, 0.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, -2.0F, 0.1238F, -0.3272F, -0.3695F));

		PartDefinition left_horn_b3 = left_horn_b2.addOrReplaceChild("left_horn_b3", CubeListBuilder.create().texOffs(236, 270).addBox(7.0707F, -4.0F, 4.4265F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 4.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition left_horn_b4 = left_horn_b3.addOrReplaceChild("left_horn_b4", CubeListBuilder.create().texOffs(264, 234).addBox(0.0F, -4.0F, -4.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0707F, 0.0F, 8.4265F, 0.7256F, 0.9056F, 0.8452F));

		PartDefinition left_horn_b5 = left_horn_b3.addOrReplaceChild("left_horn_b5", CubeListBuilder.create().texOffs(178, 278).addBox(0.0F, -4.0F, 0.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0707F, 0.0F, 4.4265F, 0.1789F, -0.2489F, -0.6333F));

		PartDefinition left_horn_c1 = left_horn_b3.addOrReplaceChild("left_horn_c1", CubeListBuilder.create().texOffs(108, 233).addBox(-0.0707F, 0.0F, -0.4265F, 4.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0707F, -4.0F, 8.4265F, 0.4957F, 0.6825F, -0.4504F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(280, 110).addBox(-11.0F, -4.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.3F, -3.0F, -4.0F, -0.2174F, 0.4802F, 0.6011F));

		PartDefinition right_horn_2 = right_horn.addOrReplaceChild("right_horn_2", CubeListBuilder.create().texOffs(282, 16).addBox(-9.0F, -4.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 2.0F, 0.0539F, -0.6148F, 0.1534F));

		PartDefinition right_horn_3 = right_horn_2.addOrReplaceChild("right_horn_3", CubeListBuilder.create().texOffs(264, 242).addBox(-14.0F, -4.0F, -4.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.1745F, -0.7854F, 0.0F));

		PartDefinition right_horn_end = right_horn_3.addOrReplaceChild("right_horn_end", CubeListBuilder.create().texOffs(210, 278).addBox(-12.0F, -4.0F, -4.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, 0.1745F, -0.5672F, 0.0F));

		PartDefinition horn_end3 = right_horn_3.addOrReplaceChild("horn_end3", CubeListBuilder.create().texOffs(282, 0).addBox(-10.0F, -4.0F, 0.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 0.0F, -4.0F, 0.1446F, 0.27F, 0.4996F));

		PartDefinition right_horn_b2 = right_horn.addOrReplaceChild("right_horn_b2", CubeListBuilder.create().texOffs(242, 278).addBox(-12.0F, -4.0F, 0.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 0.0F, -2.0F, 0.1238F, 0.3272F, 0.3695F));

		PartDefinition right_horn_b3 = right_horn_b2.addOrReplaceChild("right_horn_b3", CubeListBuilder.create().texOffs(270, 270).addBox(-20.0707F, -4.0F, 4.4265F, 13.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 4.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition right_horn_b4 = right_horn_b3.addOrReplaceChild("right_horn_b4", CubeListBuilder.create().texOffs(200, 270).addBox(-14.0F, -4.0F, -4.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0707F, 0.0F, 8.4265F, 0.7256F, -0.9056F, -0.8452F));

		PartDefinition right_horn_b5 = right_horn_b3.addOrReplaceChild("right_horn_b5", CubeListBuilder.create().texOffs(274, 278).addBox(-12.0F, -4.0F, 0.0F, 12.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0707F, 0.0F, 4.4265F, 0.1789F, 0.2489F, 0.6333F));

		PartDefinition right_horn_c1 = right_horn_b3.addOrReplaceChild("right_horn_c1", CubeListBuilder.create().texOffs(154, 251).addBox(-3.9293F, 0.0F, -0.4265F, 4.0F, 4.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0707F, -4.0F, 8.4265F, 0.4957F, -0.6825F, 0.4504F));

		PartDefinition cannon = head.addOrReplaceChild("cannon", CubeListBuilder.create().texOffs(154, 233).addBox(-5.0F, -5.0F, -6.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -14.0F));

		PartDefinition barrel = cannon.addOrReplaceChild("barrel", CubeListBuilder.create().texOffs(230, 76).addBox(-4.0F, -4.0F, -18.0F, 8.0F, 8.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

		PartDefinition tail_base = torso.addOrReplaceChild("tail_base", CubeListBuilder.create().texOffs(118, 65).addBox(-7.0F, -8.0F, 0.0F, 14.0F, 21.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 21.0F));

		PartDefinition left_tail_spike = tail_base.addOrReplaceChild("left_tail_spike", CubeListBuilder.create().texOffs(282, 76).addBox(0.0F, -12.0F, -3.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 7.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition left_tail_spike2 = tail_base.addOrReplaceChild("left_tail_spike2", CubeListBuilder.create().texOffs(154, 282).addBox(0.0F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -8.0F, 18.0F, -0.48F, 0.0F, 0.7854F));

		PartDefinition right_tail_spike = tail_base.addOrReplaceChild("right_tail_spike", CubeListBuilder.create().texOffs(282, 24).addBox(-3.0F, -5.0F, -3.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 18.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition right_tail_spike2 = tail_base.addOrReplaceChild("right_tail_spike2", CubeListBuilder.create().texOffs(140, 282).addBox(-3.0F, -12.0F, -3.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -8.0F, 7.0F, -0.48F, 0.0F, -0.7854F));

		PartDefinition tail_2 = tail_base.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(0, 65).addBox(-5.0F, -8.0F, 0.0F, 10.0F, 14.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 42.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(0, 128).addBox(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 49.0F));

		PartDefinition left_tail_end1 = tail_3.addOrReplaceChild("left_tail_end1", CubeListBuilder.create().texOffs(228, 160).addBox(0.0F, 0.0F, 0.0F, 30.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -7.0F, 24.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition right_tail_end2 = tail_3.addOrReplaceChild("right_tail_end2", CubeListBuilder.create().texOffs(132, 52).addBox(-30.0F, 0.0F, 0.0F, 30.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.0F, 40.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition right_tail_end1 = tail_3.addOrReplaceChild("right_tail_end1", CubeListBuilder.create().texOffs(228, 169).addBox(-30.0F, 0.0F, 0.0F, 30.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.0F, 24.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition left_tail_end2 = tail_3.addOrReplaceChild("left_tail_end2", CubeListBuilder.create().texOffs(230, 67).addBox(0.0F, 0.0F, 0.0F, 30.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -7.0F, 40.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition leaft_leg = body.addOrReplaceChild("leaft_leg", CubeListBuilder.create().texOffs(132, 0).addBox(0.0F, -7.0F, -11.0F, 19.0F, 31.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.0F, -4.0F, 1.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition left_knee = leaft_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(0, 220).addBox(-8.0F, -10.0F, 0.0F, 14.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 24.0F, 10.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(108, 256).addBox(-7.0F, 0.0F, -7.0F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 22.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition left_foot = left_ancle.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(200, 251).addBox(-6.0F, -4.0F, -13.0F, 10.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(114, 181).addBox(-19.0F, -7.0F, -11.0F, 19.0F, 31.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -4.0F, 1.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(228, 128).addBox(-6.0F, -10.0F, 0.0F, 14.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0F, 24.0F, 10.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition right_ancle = right_knee.addOrReplaceChild("right_ancle", CubeListBuilder.create().texOffs(264, 178).addBox(-5.0F, 0.0F, -7.0F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 22.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition right_foot = right_ancle.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(246, 251).addBox(-4.0F, -4.0F, -13.0F, 10.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		MechaRexEntity rex = (MechaRexEntity) entity;

		if (rex.isActivating()) {
			this.animate(rex.activationAnimationState, MechaRexAnimationDefinitions.MECHAREX_ACTIVATION, ageInTicks, 1f);
			return;
		}
		// 1) WALK first (base layer)
		// Only if actually moving; otherwise it can "fight" idle/attacks.
		if (limbSwingAmount > 0.01F) {
			this.animateWalk(MechaRexAnimationDefinitions.MECHAREX_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		}

		// 2) ATTACKS second (overlay on top of walk)
		if (rex.isTailSwiping()) {
			this.animate(rex.tailSwipeAnimationState, MechaRexAnimationDefinitions.MECHAREX_TAIL_SWIPE, ageInTicks, 1f);
		}
		if (rex.isLaunchingBomb()) {
			this.animate(rex.launchBombAnimationState, MechaRexAnimationDefinitions.MECHAREX_LAUNCH_BOMB, ageInTicks, 1f);
		}

		// 3) IDLE last, but only when not walking and not attacking
		if (limbSwingAmount <= 0.01F && !rex.isTailSwiping() && !rex.isLaunchingBomb()) {
			this.animate(rex.idleAnimationState, MechaRexAnimationDefinitions.MECHAREX_IDLE, ageInTicks, 1f);
		}


	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mecha_rex.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return mecha_rex;
	}
}