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

public class TruckWreckerUpgrade<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = RileyModModelLayers.TRUCK_WRECKER_UPGRADE_LAYER;
	private final ModelPart truck;
	private final ModelPart wrecker_upgrade;
	private final ModelPart left_crates;
	private final ModelPart right_crates;
	private final ModelPart tow_winch_base;
	private final ModelPart crane;
	private final ModelPart hook;

	public TruckWreckerUpgrade(ModelPart root) {
		this.truck = root.getChild("truck");
		this.wrecker_upgrade = this.truck.getChild("wrecker_upgrade");
		this.left_crates = this.wrecker_upgrade.getChild("left_crates");
		this.right_crates = this.wrecker_upgrade.getChild("right_crates");
		this.tow_winch_base = this.wrecker_upgrade.getChild("tow_winch_base");
		this.crane = this.tow_winch_base.getChild("crane");
		this.hook = this.crane.getChild("hook");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition truck = partdefinition.addOrReplaceChild("truck", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition wrecker_upgrade = truck.addOrReplaceChild("wrecker_upgrade", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -2.0F, 0.0F, 30.0F, 2.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition left_crates = wrecker_upgrade.addOrReplaceChild("left_crates", CubeListBuilder.create().texOffs(0, 41).addBox(-5.0F, -9.0F, -1.0F, 7.0F, 9.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(108, 60).addBox(2.0F, -9.0F, 4.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(108, 71).addBox(2.0F, -9.0F, 29.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, -2.0F, 1.0F));

		PartDefinition right_crates = wrecker_upgrade.addOrReplaceChild("right_crates", CubeListBuilder.create().texOffs(0, 88).addBox(-2.0F, -9.0F, -1.0F, 7.0F, 9.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(108, 82).addBox(-3.0F, -9.0F, 4.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(110, 41).addBox(-3.0F, -9.0F, 29.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -2.0F, 1.0F));

		PartDefinition tow_winch_base = wrecker_upgrade.addOrReplaceChild("tow_winch_base", CubeListBuilder.create().texOffs(90, 96).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 5.0F));

		PartDefinition crane = tow_winch_base.addOrReplaceChild("crane", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition cube_r1 = crane.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(90, 41).addBox(-2.0F, -50.0F, -2.0F, 4.0F, 50.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		PartDefinition hook = crane.addOrReplaceChild("hook", CubeListBuilder.create().texOffs(108, 41).addBox(0.0F, -2.0F, -1.0F, 0.0F, 18.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(110, 52).addBox(0.0F, 16.0F, -1.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -29.0F, 39.0F));

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