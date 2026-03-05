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

public class TrisonCartModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "trisoncartmodel"), "main");
	private final ModelPart trison_cart;
	private final ModelPart cart;
	private final ModelPart main_cart;
	private final ModelPart rear_suspension;
	private final ModelPart rear_wheels;
	private final ModelPart left_wheel_r;
	private final ModelPart right_wheel_r;
	private final ModelPart front_steering;
	private final ModelPart front_suspension;
	private final ModelPart front_wheels;
	private final ModelPart left_wheel_f;
	private final ModelPart right_wheel_f;
	private final ModelPart bar;
	private final ModelPart passenger_seats;
	private final ModelPart chests;
	private final ModelPart cover;
	private final ModelPart lamp_post_right;
	private final ModelPart lamp_hook;
	private final ModelPart right_lamp;
	private final ModelPart lamp_post_left;
	private final ModelPart lamp_hook2;
	private final ModelPart left_lamp;
	private final ModelPart soul_forge;

	public TrisonCartModel(ModelPart root) {
		this.trison_cart = root.getChild("trison_cart");
		this.cart = this.trison_cart.getChild("cart");
		this.main_cart = this.cart.getChild("main_cart");
		this.rear_suspension = this.main_cart.getChild("rear_suspension");
		this.rear_wheels = this.rear_suspension.getChild("rear_wheels");
		this.left_wheel_r = this.rear_wheels.getChild("left_wheel_r");
		this.right_wheel_r = this.rear_wheels.getChild("right_wheel_r");
		this.front_steering = this.main_cart.getChild("front_steering");
		this.front_suspension = this.front_steering.getChild("front_suspension");
		this.front_wheels = this.front_suspension.getChild("front_wheels");
		this.left_wheel_f = this.front_wheels.getChild("left_wheel_f");
		this.right_wheel_f = this.front_wheels.getChild("right_wheel_f");
		this.bar = this.front_steering.getChild("bar");
		this.passenger_seats = this.main_cart.getChild("passenger_seats");
		this.chests = this.main_cart.getChild("chests");
		this.cover = this.main_cart.getChild("cover");
		this.lamp_post_right = this.main_cart.getChild("lamp_post_right");
		this.lamp_hook = this.lamp_post_right.getChild("lamp_hook");
		this.right_lamp = this.lamp_hook.getChild("right_lamp");
		this.lamp_post_left = this.main_cart.getChild("lamp_post_left");
		this.lamp_hook2 = this.lamp_post_left.getChild("lamp_hook2");
		this.left_lamp = this.lamp_hook2.getChild("left_lamp");
		this.soul_forge = this.main_cart.getChild("soul_forge");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition trison_cart = partdefinition.addOrReplaceChild("trison_cart", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cart = trison_cart.addOrReplaceChild("cart", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition main_cart = cart.addOrReplaceChild("main_cart", CubeListBuilder.create().texOffs(0, 286).addBox(-29.0F, -10.0F, 15.0F, 58.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 254).addBox(-29.0F, -5.0F, -12.0F, 58.0F, 5.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(-29.0F, 7.0F, 15.0F, 58.0F, 2.0F, 68.0F, new CubeDeformation(0.0F))
		.texOffs(0, 305).addBox(-29.0F, -10.0F, 81.0F, 58.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(252, 0).addBox(27.0F, -10.0F, 17.0F, 2.0F, 17.0F, 64.0F, new CubeDeformation(0.0F))
		.texOffs(252, 81).addBox(-29.0F, -10.0F, 17.0F, 2.0F, 17.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rear_suspension = main_cart.addOrReplaceChild("rear_suspension", CubeListBuilder.create().texOffs(130, 320).addBox(-16.0F, 5.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(120, 320).addBox(14.0F, 5.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 63.0F));

		PartDefinition cube_r1 = rear_suspension.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(334, 327).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(94, 335).addBox(-31.0F, -2.0F, 0.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, -10.0F, -0.7243F, 0.0F, 0.0F));

		PartDefinition cube_r2 = rear_suspension.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(122, 335).addBox(-1.0F, -2.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(150, 335).addBox(-31.0F, -2.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 10.0F, 0.7243F, 0.0F, 0.0F));

		PartDefinition rear_wheels = rear_suspension.addOrReplaceChild("rear_wheels", CubeListBuilder.create().texOffs(252, 162).addBox(-31.0F, -1.0F, -1.0F, 62.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition left_wheel_r = rear_wheels.addOrReplaceChild("left_wheel_r", CubeListBuilder.create().texOffs(244, 327).addBox(0.5F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(322, 357).addBox(0.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(29.0F, 0.0F, 0.0F));

		PartDefinition cube_r3 = left_wheel_r.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(204, 360).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r4 = left_wheel_r.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(212, 360).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r5 = left_wheel_r.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(196, 360).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r6 = left_wheel_r.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(188, 360).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r7 = left_wheel_r.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(206, 351).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r8 = left_wheel_r.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(188, 351).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r9 = left_wheel_r.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(170, 351).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r10 = left_wheel_r.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(152, 316).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r11 = left_wheel_r.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(358, 231).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(274, 327).addBox(-1.0F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_wheel_r = rear_wheels.addOrReplaceChild("right_wheel_r", CubeListBuilder.create().texOffs(304, 327).addBox(-1.5F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(358, 278).addBox(-1.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-29.0F, 0.0F, 0.0F));

		PartDefinition cube_r12 = right_wheel_r.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(154, 361).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r13 = right_wheel_r.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(146, 361).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r14 = right_wheel_r.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(228, 360).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r15 = right_wheel_r.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(220, 360).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r16 = right_wheel_r.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(242, 357).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r17 = right_wheel_r.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(346, 355).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r18 = right_wheel_r.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(328, 355).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r19 = right_wheel_r.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(224, 351).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r20 = right_wheel_r.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(358, 294).addBox(0.0F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(328, 170).addBox(0.0F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition front_steering = main_cart.addOrReplaceChild("front_steering", CubeListBuilder.create().texOffs(146, 349).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(170, 254).addBox(-25.0F, 6.0F, -10.0F, 50.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition front_suspension = front_steering.addOrReplaceChild("front_suspension", CubeListBuilder.create().texOffs(140, 320).addBox(-16.0F, 5.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(358, 247).addBox(14.0F, 5.5F, -1.5F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, 0.0F));

		PartDefinition cube_r21 = front_suspension.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(300, 343).addBox(-1.0F, -2.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(272, 343).addBox(-31.0F, -2.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 10.0F, 0.7243F, 0.0F, 0.0F));

		PartDefinition cube_r22 = front_suspension.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(244, 343).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(334, 341).addBox(-31.0F, -2.0F, 0.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, -10.0F, -0.7243F, 0.0F, 0.0F));

		PartDefinition front_wheels = front_suspension.addOrReplaceChild("front_wheels", CubeListBuilder.create().texOffs(252, 166).addBox(-31.0F, -1.0F, -1.0F, 62.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

		PartDefinition left_wheel_f = front_wheels.addOrReplaceChild("left_wheel_f", CubeListBuilder.create().texOffs(328, 186).addBox(0.5F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(358, 310).addBox(0.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(29.0F, 0.0F, 0.0F));

		PartDefinition cube_r23 = left_wheel_f.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(110, 362).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r24 = left_wheel_f.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(102, 362).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r25 = left_wheel_f.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(94, 362).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r26 = left_wheel_f.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(162, 361).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r27 = left_wheel_f.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(358, 170).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r28 = left_wheel_f.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(296, 357).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r29 = left_wheel_f.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(278, 357).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r30 = left_wheel_f.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(260, 357).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r31 = left_wheel_f.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(170, 360).addBox(-1.0F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(328, 202).addBox(-1.0F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_wheel_f = front_wheels.addOrReplaceChild("right_wheel_f", CubeListBuilder.create().texOffs(328, 218).addBox(-1.5F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(176, 360).addBox(-1.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-29.0F, 0.0F, 0.0F));

		PartDefinition cube_r32 = right_wheel_f.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(362, 326).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r33 = right_wheel_f.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(134, 362).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -7.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r34 = right_wheel_f.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(126, 362).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r35 = right_wheel_f.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(118, 362).addBox(-1.0F, -6.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 7.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r36 = right_wheel_f.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(358, 206).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r37 = right_wheel_f.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(358, 197).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r38 = right_wheel_f.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(358, 188).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, -0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r39 = right_wheel_f.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(358, 179).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 0.0F, 0.4276F, 0.0F, 0.0F));

		PartDefinition cube_r40 = right_wheel_f.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(182, 360).addBox(0.0F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(328, 234).addBox(0.0F, -1.0F, -7.0F, 1.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bar = front_steering.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 172).addBox(-20.0F, -2.0F, -78.0F, 4.0F, 4.0F, 78.0F, new CubeDeformation(0.0F))
		.texOffs(164, 172).addBox(16.0F, -2.0F, -78.0F, 4.0F, 4.0F, 78.0F, new CubeDeformation(0.0F))
		.texOffs(94, 327).addBox(-20.0F, -2.0F, -82.0F, 40.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -10.0F));

		PartDefinition passenger_seats = main_cart.addOrReplaceChild("passenger_seats", CubeListBuilder.create().texOffs(310, 254).addBox(-6.0F, -4.0F, -2.0F, 17.0F, 4.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(182, 327).addBox(-43.0F, -4.0F, -2.0F, 11.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -2.0F, 63.0F));

		PartDefinition cube_r41 = passenger_seats.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(314, 357).addBox(0.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r42 = passenger_seats.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(358, 215).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-32.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition chests = main_cart.addOrReplaceChild("chests", CubeListBuilder.create().texOffs(170, 278).addBox(16.0F, -6.0F, 17.0F, 11.0F, 13.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(264, 278).addBox(-27.0F, -6.0F, 17.0F, 11.0F, 13.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cover = main_cart.addOrReplaceChild("cover", CubeListBuilder.create().texOffs(0, 0).addBox(-29.0F, -34.0F, -68.0F, 58.0F, 34.0F, 68.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, 83.0F));

		PartDefinition lamp_post_right = main_cart.addOrReplaceChild("lamp_post_right", CubeListBuilder.create().texOffs(120, 286).addBox(-4.0F, -30.0F, -4.0F, 4.0F, 30.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -5.0F, 3.0F, 0.288F, 0.103F, -0.3341F));

		PartDefinition lamp_hook = lamp_post_right.addOrReplaceChild("lamp_hook", CubeListBuilder.create().texOffs(152, 286).addBox(-4.0F, -11.0F, -4.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -30.0F, 0.0F, 0.8755F, 0.4302F, -0.5956F));

		PartDefinition right_lamp = lamp_hook.addOrReplaceChild("right_lamp", CubeListBuilder.create(), PartPose.offset(-4.0F, -11.0F, -4.0F));

		PartDefinition cube_r43 = right_lamp.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(120, 349).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.1309F, 0.6545F, 0.0F));

		PartDefinition lamp_post_left = main_cart.addOrReplaceChild("lamp_post_left", CubeListBuilder.create().texOffs(136, 286).addBox(0.0F, -30.0F, -4.0F, 4.0F, 30.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -5.0F, 3.0F, 0.288F, -0.103F, 0.3341F));

		PartDefinition lamp_hook2 = lamp_post_left.addOrReplaceChild("lamp_hook2", CubeListBuilder.create().texOffs(152, 301).addBox(0.0F, -11.0F, -4.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -30.0F, 0.0F, 0.8755F, -0.4302F, 0.5956F));

		PartDefinition left_lamp = lamp_hook2.addOrReplaceChild("left_lamp", CubeListBuilder.create(), PartPose.offset(4.0F, -11.0F, -4.0F));

		PartDefinition cube_r44 = left_lamp.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(94, 349).addBox(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, -0.1309F, -0.6545F, 0.0F));

		PartDefinition soul_forge = main_cart.addOrReplaceChild("soul_forge", CubeListBuilder.create().texOffs(0, 324).addBox(-12.0F, -21.0F, -10.0F, 24.0F, 21.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		trison_cart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return trison_cart;
	}
}