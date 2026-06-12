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
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.entity.animations.TruckAnimationDefinitions;
import net.riley.riley_mod.entity.custom.TruckEntity;
import net.riley.riley_mod.entity.custom.BaseVehicleEntity;


public class TruckModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("riley_mod", "truck"), "main");
	private final ModelPart truck;
	private final ModelPart cab;
	private final ModelPart insides;
	private final ModelPart steering_wheel;
	private final ModelPart left_light;
	private final ModelPart right_light;
	private final ModelPart great;
	private final ModelPart front_suspension;
	private final ModelPart right_wheel;
	private final ModelPart left_wheel;
	private final ModelPart front_axle;
	private final ModelPart frame;
	private final ModelPart rear_suspension;
	private final ModelPart axle;
	private final ModelPart drive_shaft;

	public TruckModel(ModelPart root) {
		this.truck = root.getChild("truck");
		this.cab = this.truck.getChild("cab");
		this.insides = this.cab.getChild("insides");
		this.steering_wheel = this.insides.getChild("steering_wheel");
		this.left_light = this.cab.getChild("left_light");
		this.right_light = this.cab.getChild("right_light");
		this.great = this.cab.getChild("great");
		this.front_suspension = this.truck.getChild("front_suspension");
		this.right_wheel = this.front_suspension.getChild("right_wheel");
		this.left_wheel = this.front_suspension.getChild("left_wheel");
		this.front_axle = this.front_suspension.getChild("front_axle");
		this.frame = this.truck.getChild("frame");
		this.rear_suspension = this.frame.getChild("rear_suspension");
		this.axle = this.rear_suspension.getChild("axle");
		this.drive_shaft = this.truck.getChild("drive_shaft");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition truck = partdefinition.addOrReplaceChild("truck", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cab = truck.addOrReplaceChild("cab", CubeListBuilder.create().texOffs(0, 52).addBox(-15.0F, -28.0F, -22.0F, 30.0F, 25.0F, 22.0F, new CubeDeformation(0.0F))
				.texOffs(0, 99).addBox(-9.0F, -3.0F, -37.0F, 18.0F, 3.0F, 37.0F, new CubeDeformation(0.0F))
				.texOffs(110, 78).addBox(-11.0F, -15.0F, -38.0F, 22.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(66, 143).addBox(-14.0F, -9.0F, -32.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(150, 120).addBox(11.0F, -9.0F, -32.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition cube_r1 = cab.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(130, 155).addBox(-2.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(66, 155).addBox(23.0F, 0.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -9.0F, -32.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition insides = cab.addOrReplaceChild("insides", CubeListBuilder.create().texOffs(110, 106).addBox(-9.0F, -3.0F, -6.0F, 18.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(110, 120).addBox(-9.0F, -20.0F, 4.0F, 18.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -6.0F));

		PartDefinition steering_wheel = insides.addOrReplaceChild("steering_wheel", CubeListBuilder.create().texOffs(166, 166).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(152, 151).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition cube_r2 = steering_wheel.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(166, 160).addBox(0.0F, -1.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, 4.0F, 0.0F, 0.9338F, 0.0F));

		PartDefinition cube_r3 = steering_wheel.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 166).addBox(-1.0F, -1.0F, -5.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, 4.0F, 0.0F, -0.9338F, 0.0F));

		PartDefinition cube_r4 = steering_wheel.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 166).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, -4.0F, 0.0F, -0.9338F, 0.0F));

		PartDefinition cube_r5 = steering_wheel.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 166).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, -4.0F, 0.0F, 0.9338F, 0.0F));

		PartDefinition cube_r6 = steering_wheel.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(152, 140).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition left_light = cab.addOrReplaceChild("left_light", CubeListBuilder.create().texOffs(104, 89).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, -9.0F, -31.5F));

		PartDefinition cube_r7 = left_light.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(104, 95).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(54, 159).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.25F, 0.0F, 0.0F, 0.7854F));

		PartDefinition right_light = cab.addOrReplaceChild("right_light", CubeListBuilder.create().texOffs(104, 91).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -9.0F, -31.5F));

		PartDefinition cube_r8 = right_light.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(96, 171).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 93).addBox(0.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -0.25F, 0.0F, 0.0F, -0.7854F));

		PartDefinition great = cab.addOrReplaceChild("great", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -38.0F));

		PartDefinition cube_r9 = great.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(92, 171).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r10 = great.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(88, 171).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r11 = great.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(170, 106).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = great.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(84, 170).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r13 = great.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(80, 170).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r14 = great.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(92, 143).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r15 = great.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(104, 78).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition front_suspension = truck.addOrReplaceChild("front_suspension", CubeListBuilder.create().texOffs(130, 163).addBox(6.0F, 1.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 166).addBox(-8.0F, 1.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -26.0F));

		PartDefinition right_wheel = front_suspension.addOrReplaceChild("right_wheel", CubeListBuilder.create().texOffs(0, 143).addBox(-3.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(120, 156).addBox(-3.0F, -3.0F, 4.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(142, 167).addBox(-3.0F, -3.0F, -5.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(84, 156).addBox(-3.0F, -5.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(102, 156).addBox(-3.0F, 4.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, 3.0F, 0.0F));

		PartDefinition left_wheel = front_suspension.addOrReplaceChild("left_wheel", CubeListBuilder.create().texOffs(22, 143).addBox(0.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(150, 167).addBox(0.0F, -3.0F, 4.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(158, 167).addBox(0.0F, -3.0F, -5.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 159).addBox(0.0F, -5.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(18, 159).addBox(0.0F, 4.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 3.0F, 0.0F));

		PartDefinition front_axle = front_suspension.addOrReplaceChild("front_axle", CubeListBuilder.create().texOffs(48, 139).addBox(-11.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition frame = truck.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(104, 0).addBox(5.0F, -3.0F, 0.0F, 4.0F, 4.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(104, 39).addBox(-9.0F, -3.0F, 0.0F, 4.0F, 4.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(152, 132).addBox(-5.0F, -3.0F, 31.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(96, 139).addBox(-2.0F, 1.0F, -32.0F, 4.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(90, 163).addBox(-2.0F, 1.0F, 24.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rear_suspension = frame.addOrReplaceChild("rear_suspension", CubeListBuilder.create().texOffs(106, 163).addBox(6.0F, 1.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(118, 163).addBox(-8.0F, 1.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

		PartDefinition axle = rear_suspension.addOrReplaceChild("axle", CubeListBuilder.create().texOffs(0, 139).addBox(-11.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(130, 139).addBox(11.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(36, 159).addBox(11.0F, -5.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(148, 160).addBox(11.0F, 4.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(48, 170).addBox(11.0F, -3.0F, 4.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(56, 170).addBox(11.0F, -3.0F, -5.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(44, 143).addBox(-14.0F, -4.0F, -4.0F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(54, 163).addBox(-14.0F, -5.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(72, 163).addBox(-14.0F, 4.0F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(64, 170).addBox(-14.0F, -3.0F, 4.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(72, 170).addBox(-14.0F, -3.0F, -5.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition drive_shaft = truck.addOrReplaceChild("drive_shaft", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -25.0F, 2.0F, 2.0F, 50.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity instanceof BaseVehicleEntity vehicleEntity) {
			this.animate(vehicleEntity.parkAnimationState, TruckAnimationDefinitions.TRUCK_PARK, ageInTicks, 1.0F);
			this.animate(vehicleEntity.forwardAnimationState, TruckAnimationDefinitions.TRUCK_FORWARD, ageInTicks, 1.0F);
			this.animate(vehicleEntity.backwardAnimationState, TruckAnimationDefinitions.TRUCK_BACKWARD, ageInTicks, 1.0F);
			this.animate(vehicleEntity.steerLeftAnimationState, TruckAnimationDefinitions.TRUCK_STEER_LEFT, ageInTicks, 1.0F);
			this.animate(vehicleEntity.steerRightAnimationState, TruckAnimationDefinitions.TRUCK_STEER_RIGHT, ageInTicks, 1.0F);
		}
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