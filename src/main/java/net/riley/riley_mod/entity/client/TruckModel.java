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
	private final ModelPart front_suspension;
	private final ModelPart right_wheel;
	private final ModelPart left_wheel;
	private final ModelPart front_axle;
	private final ModelPart frame;
	private final ModelPart rear_suspension;
	private final ModelPart axle;

	public TruckModel(ModelPart root) {
		this.truck = root.getChild("truck");
		this.cab = this.truck.getChild("cab");
		this.front_suspension = this.truck.getChild("front_suspension");
		this.right_wheel = this.front_suspension.getChild("right_wheel");
		this.left_wheel = this.front_suspension.getChild("left_wheel");
		this.front_axle = this.front_suspension.getChild("front_axle");
		this.frame = this.truck.getChild("frame");
		this.rear_suspension = this.frame.getChild("rear_suspension");
		this.axle = this.rear_suspension.getChild("axle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition truck = partdefinition.addOrReplaceChild("truck", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

		PartDefinition cab = truck.addOrReplaceChild("cab", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -15.0F, -37.0F, 30.0F, 12.0F, 37.0F, new CubeDeformation(0.0F))
		.texOffs(0, 49).addBox(-9.0F, -3.0F, -37.0F, 18.0F, 3.0F, 37.0F, new CubeDeformation(0.0F))
		.texOffs(0, 89).addBox(-15.0F, -28.0F, -23.0F, 30.0F, 13.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition front_suspension = truck.addOrReplaceChild("front_suspension", CubeListBuilder.create().texOffs(84, 129).addBox(6.0F, 1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(134, 0).addBox(-8.0F, 1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -26.0F));

		PartDefinition right_wheel = front_suspension.addOrReplaceChild("right_wheel", CubeListBuilder.create().texOffs(40, 129).addBox(-2.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, 3.0F, 0.0F));

		PartDefinition left_wheel = front_suspension.addOrReplaceChild("left_wheel", CubeListBuilder.create().texOffs(20, 129).addBox(0.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 3.0F, 0.0F));

		PartDefinition front_axle = front_suspension.addOrReplaceChild("front_axle", CubeListBuilder.create().texOffs(48, 125).addBox(-11.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition frame = truck.addOrReplaceChild("frame", CubeListBuilder.create().texOffs(106, 89).addBox(5.0F, -3.0F, 0.0F, 4.0F, 4.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(110, 49).addBox(-9.0F, -3.0F, 0.0F, 4.0F, 4.0F, 35.0F, new CubeDeformation(0.0F))
		.texOffs(96, 128).addBox(-5.0F, -3.0F, 31.0F, 10.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rear_suspension = frame.addOrReplaceChild("rear_suspension", CubeListBuilder.create().texOffs(60, 129).addBox(6.0F, 1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(72, 129).addBox(-8.0F, 1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 26.0F));

		PartDefinition axle = rear_suspension.addOrReplaceChild("axle", CubeListBuilder.create().texOffs(0, 125).addBox(-11.0F, -1.0F, -1.0F, 22.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(124, 128).addBox(11.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 129).addBox(-13.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

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