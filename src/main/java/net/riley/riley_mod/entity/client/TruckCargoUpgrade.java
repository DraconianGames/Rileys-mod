package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TruckCargoUpgrade<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = RileyModModelLayers.TRUCK_CARGO_UPGRADE_LAYER;
	private final ModelPart truck;
	private final ModelPart cargo_upgrade;
	private final ModelPart front_loop;
	private final ModelPart rear_loop;
	private final ModelPart left_handle;
	private final ModelPart right_handle;
	private final ModelPart acordian_part;
	private final ModelPart left_ridges;
	private final ModelPart left_ridge_1;
	private final ModelPart left_ridge_2;
	private final ModelPart left_ridge_3;
	private final ModelPart left_ridge_4;
	private final ModelPart left_ridge_5;
	private final ModelPart left_ridge_6;
	private final ModelPart left_ridge_7;
	private final ModelPart left_ridge_8;
	private final ModelPart left_ridge_9;
	private final ModelPart right_ridges;
	private final ModelPart right_ridge_1;
	private final ModelPart right_ridge_2;
	private final ModelPart right_ridge_3;
	private final ModelPart right_ridge_4;
	private final ModelPart right_ridge_5;
	private final ModelPart right_ridge_6;
	private final ModelPart right_ridge_7;
	private final ModelPart right_ridge_8;
	private final ModelPart right_ridge_9;
	private final ModelPart top_ridges;
	private final ModelPart top_ridge_1;
	private final ModelPart top_ridge_2;
	private final ModelPart top_ridge_3;
	private final ModelPart top_ridge_4;
	private final ModelPart top_ridge_5;
	private final ModelPart top_ridge_6;
	private final ModelPart top_ridge_7;
	private final ModelPart top_ridge_8;
	private final ModelPart top_ridge_9;

	public TruckCargoUpgrade(ModelPart root) {
		this.truck = root.getChild("truck");
		this.cargo_upgrade = this.truck.getChild("cargo_upgrade");
		this.front_loop = this.cargo_upgrade.getChild("front_loop");
		this.rear_loop = this.cargo_upgrade.getChild("rear_loop");
		this.left_handle = this.rear_loop.getChild("left_handle");
		this.right_handle = this.rear_loop.getChild("right_handle");
		this.acordian_part = this.cargo_upgrade.getChild("acordian_part");
		this.left_ridges = this.acordian_part.getChild("left_ridges");
		this.left_ridge_1 = this.left_ridges.getChild("left_ridge_1");
		this.left_ridge_2 = this.left_ridges.getChild("left_ridge_2");
		this.left_ridge_3 = this.left_ridges.getChild("left_ridge_3");
		this.left_ridge_4 = this.left_ridges.getChild("left_ridge_4");
		this.left_ridge_5 = this.left_ridges.getChild("left_ridge_5");
		this.left_ridge_6 = this.left_ridges.getChild("left_ridge_6");
		this.left_ridge_7 = this.left_ridges.getChild("left_ridge_7");
		this.left_ridge_8 = this.left_ridges.getChild("left_ridge_8");
		this.left_ridge_9 = this.left_ridges.getChild("left_ridge_9");
		this.right_ridges = this.acordian_part.getChild("right_ridges");
		this.right_ridge_1 = this.right_ridges.getChild("right_ridge_1");
		this.right_ridge_2 = this.right_ridges.getChild("right_ridge_2");
		this.right_ridge_3 = this.right_ridges.getChild("right_ridge_3");
		this.right_ridge_4 = this.right_ridges.getChild("right_ridge_4");
		this.right_ridge_5 = this.right_ridges.getChild("right_ridge_5");
		this.right_ridge_6 = this.right_ridges.getChild("right_ridge_6");
		this.right_ridge_7 = this.right_ridges.getChild("right_ridge_7");
		this.right_ridge_8 = this.right_ridges.getChild("right_ridge_8");
		this.right_ridge_9 = this.right_ridges.getChild("right_ridge_9");
		this.top_ridges = this.acordian_part.getChild("top_ridges");
		this.top_ridge_1 = this.top_ridges.getChild("top_ridge_1");
		this.top_ridge_2 = this.top_ridges.getChild("top_ridge_2");
		this.top_ridge_3 = this.top_ridges.getChild("top_ridge_3");
		this.top_ridge_4 = this.top_ridges.getChild("top_ridge_4");
		this.top_ridge_5 = this.top_ridges.getChild("top_ridge_5");
		this.top_ridge_6 = this.top_ridges.getChild("top_ridge_6");
		this.top_ridge_7 = this.top_ridges.getChild("top_ridge_7");
		this.top_ridge_8 = this.top_ridges.getChild("top_ridge_8");
		this.top_ridge_9 = this.top_ridges.getChild("top_ridge_9");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition truck = partdefinition.addOrReplaceChild("truck", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cargo_upgrade = truck.addOrReplaceChild("cargo_upgrade", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -2.0F, 0.0F, 30.0F, 2.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition front_loop = cargo_upgrade.addOrReplaceChild("front_loop", CubeListBuilder.create().texOffs(0, 132).addBox(13.0F, -23.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(8, 132).addBox(-15.0F, -23.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(118, 44).addBox(-13.0F, -23.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 1.0F));

		PartDefinition cube_r1 = front_loop.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(118, 41).addBox(-15.0F, 0.0F, -2.0F, 30.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.0F, -1.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition rear_loop = cargo_upgrade.addOrReplaceChild("rear_loop", CubeListBuilder.create().texOffs(16, 132).addBox(13.0F, -23.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(24, 132).addBox(-15.0F, -23.0F, -1.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(118, 48).addBox(-13.0F, -23.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(128, 132).addBox(12.0F, -21.0F, -1.0F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(132, 132).addBox(-13.0F, -21.0F, -1.0F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(118, 92).addBox(-12.0F, -21.0F, -1.0F, 24.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 38.0F));

		PartDefinition left_handle = rear_loop.addOrReplaceChild("left_handle", CubeListBuilder.create().texOffs(138, 22).addBox(-2.0F, -3.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, 0.0F));

		PartDefinition right_handle = rear_loop.addOrReplaceChild("right_handle", CubeListBuilder.create().texOffs(138, 28).addBox(1.0F, -3.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -9.0F, 0.0F));

		PartDefinition acordian_part = cargo_upgrade.addOrReplaceChild("acordian_part", CubeListBuilder.create().texOffs(0, 41).addBox(-12.0F, -20.0F, 0.0F, 24.0F, 20.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(0, 96).addBox(12.0F, -21.0F, 0.0F, 1.0F, 1.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(72, 96).addBox(-13.0F, -21.0F, 0.0F, 1.0F, 1.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 2.0F));

		PartDefinition left_ridges = acordian_part.addOrReplaceChild("left_ridges", CubeListBuilder.create(), PartPose.offset(12.0F, 0.0F, 1.0F));

		PartDefinition cube_r2 = left_ridges.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(76, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 34.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r3 = left_ridges.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_1 = left_ridges.addOrReplaceChild("left_ridge_1", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 2.5F));

		PartDefinition cube_r4 = left_ridge_1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r5 = left_ridge_1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(40, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_2 = left_ridges.addOrReplaceChild("left_ridge_2", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 6.0F));

		PartDefinition cube_r6 = left_ridge_2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(48, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r7 = left_ridge_2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(44, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_3 = left_ridges.addOrReplaceChild("left_ridge_3", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 9.5F));

		PartDefinition cube_r8 = left_ridge_3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r9 = left_ridge_3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(136, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_4 = left_ridges.addOrReplaceChild("left_ridge_4", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 13.0F));

		PartDefinition cube_r10 = left_ridge_4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(144, 94).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r11 = left_ridge_4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(138, 0).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_5 = left_ridges.addOrReplaceChild("left_ridge_5", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 16.5F));

		PartDefinition cube_r12 = left_ridge_5.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(144, 138).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r13 = left_ridge_5.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(56, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_6 = left_ridges.addOrReplaceChild("left_ridge_6", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 20.0F));

		PartDefinition cube_r14 = left_ridge_6.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(146, 0).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r15 = left_ridge_6.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(60, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_7 = left_ridges.addOrReplaceChild("left_ridge_7", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 23.5F));

		PartDefinition cube_r16 = left_ridge_7.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(148, 94).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r17 = left_ridge_7.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(64, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_8 = left_ridges.addOrReplaceChild("left_ridge_8", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 27.0F));

		PartDefinition cube_r18 = left_ridge_8.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(148, 116).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r19 = left_ridge_8.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(68, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition left_ridge_9 = left_ridges.addOrReplaceChild("left_ridge_9", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 30.5F));

		PartDefinition cube_r20 = left_ridge_9.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(148, 138).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition cube_r21 = left_ridge_9.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(72, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition right_ridges = acordian_part.addOrReplaceChild("right_ridges", CubeListBuilder.create(), PartPose.offset(-12.0F, 0.0F, 1.0F));

		PartDefinition cube_r22 = right_ridges.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(124, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 34.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r23 = right_ridges.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(80, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_1 = right_ridges.addOrReplaceChild("right_ridge_1", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 2.5F));

		PartDefinition cube_r24 = right_ridge_1.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(88, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r25 = right_ridge_1.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(84, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_2 = right_ridges.addOrReplaceChild("right_ridge_2", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 6.0F));

		PartDefinition cube_r26 = right_ridge_2.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(96, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r27 = right_ridge_2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(92, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_3 = right_ridges.addOrReplaceChild("right_ridge_3", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 9.5F));

		PartDefinition cube_r28 = right_ridge_3.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(100, 132).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r29 = right_ridge_3.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(140, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_4 = right_ridges.addOrReplaceChild("right_ridge_4", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 13.0F));

		PartDefinition cube_r30 = right_ridge_4.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(144, 116).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r31 = right_ridge_4.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(142, 0).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_5 = right_ridges.addOrReplaceChild("right_ridge_5", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 16.5F));

		PartDefinition cube_r32 = right_ridge_5.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(150, 0).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r33 = right_ridge_5.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(104, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_6 = right_ridges.addOrReplaceChild("right_ridge_6", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 20.0F));

		PartDefinition cube_r34 = right_ridge_6.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(152, 94).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r35 = right_ridge_6.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(108, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_7 = right_ridges.addOrReplaceChild("right_ridge_7", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 23.5F));

		PartDefinition cube_r36 = right_ridge_7.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(152, 116).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r37 = right_ridge_7.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(112, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_8 = right_ridges.addOrReplaceChild("right_ridge_8", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 27.0F));

		PartDefinition cube_r38 = right_ridge_8.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(152, 138).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r39 = right_ridge_8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(116, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition right_ridge_9 = right_ridges.addOrReplaceChild("right_ridge_9", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, 30.5F));

		PartDefinition cube_r40 = right_ridge_9.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(154, 0).addBox(0.0F, -20.0F, -2.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

		PartDefinition cube_r41 = right_ridge_9.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(120, 132).addBox(0.0F, -20.0F, 0.0F, 0.0F, 20.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

		PartDefinition top_ridges = acordian_part.addOrReplaceChild("top_ridges", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));

		PartDefinition cube_r42 = top_ridges.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(118, 90).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 35.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r43 = top_ridges.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(118, 66).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_1 = top_ridges.addOrReplaceChild("top_ridge_1", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition cube_r44 = top_ridge_1.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(118, 68).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r45 = top_ridge_1.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(118, 64).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_2 = top_ridges.addOrReplaceChild("top_ridge_2", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 3.5F));

		PartDefinition cube_r46 = top_ridge_2.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(118, 72).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r47 = top_ridge_2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(118, 70).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_3 = top_ridges.addOrReplaceChild("top_ridge_3", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 7.0F));

		PartDefinition cube_r48 = top_ridge_3.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(118, 74).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r49 = top_ridge_3.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(118, 52).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_4 = top_ridges.addOrReplaceChild("top_ridge_4", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 10.5F));

		PartDefinition cube_r50 = top_ridge_4.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(118, 76).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r51 = top_ridge_4.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(118, 54).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_5 = top_ridges.addOrReplaceChild("top_ridge_5", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 14.0F));

		PartDefinition cube_r52 = top_ridge_5.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(118, 78).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r53 = top_ridge_5.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(118, 56).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_6 = top_ridges.addOrReplaceChild("top_ridge_6", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 17.5F));

		PartDefinition cube_r54 = top_ridge_6.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(118, 80).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r55 = top_ridge_6.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(118, 58).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_7 = top_ridges.addOrReplaceChild("top_ridge_7", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 21.0F));

		PartDefinition cube_r56 = top_ridge_7.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(118, 82).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r57 = top_ridge_7.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(118, 60).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_8 = top_ridges.addOrReplaceChild("top_ridge_8", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 24.5F));

		PartDefinition cube_r58 = top_ridge_8.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(118, 84).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r59 = top_ridge_8.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(118, 62).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		PartDefinition top_ridge_9 = top_ridges.addOrReplaceChild("top_ridge_9", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 28.0F));

		PartDefinition cube_r60 = top_ridge_9.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(118, 88).addBox(-12.0F, 0.0F, 0.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r61 = top_ridge_9.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(118, 86).addBox(-12.0F, 0.0F, -2.0F, 24.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, 0.5236F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		truck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return truck;
	}
}