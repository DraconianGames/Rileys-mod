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

public class SkeletonFairyModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "skeletonfairymodel"), "main");
	private final ModelPart skeletonfairy;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart left_arm;
	private final ModelPart left_elbow;
	private final ModelPart left_hand;
	private final ModelPart right_arm;
	private final ModelPart right_elbow;
	private final ModelPart right_hand;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_ancle;
	private final ModelPart left_wing;
	private final ModelPart left_wing_2;
	private final ModelPart right_wing;
	private final ModelPart right_wing_2;

	public SkeletonFairyModel(ModelPart root) {
		this.skeletonfairy = root.getChild("skeletonfairy");
		this.body = this.skeletonfairy.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.tail = this.torso.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.left_arm = this.body.getChild("left_arm");
		this.left_elbow = this.left_arm.getChild("left_elbow");
		this.left_hand = this.left_elbow.getChild("left_hand");
		this.right_arm = this.body.getChild("right_arm");
		this.right_elbow = this.right_arm.getChild("right_elbow");
		this.right_hand = this.right_elbow.getChild("right_hand");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.right_ancle = this.right_knee.getChild("right_ancle");
		this.left_wing = this.body.getChild("left_wing");
		this.left_wing_2 = this.left_wing.getChild("left_wing_2");
		this.right_wing = this.body.getChild("right_wing");
		this.right_wing_2 = this.right_wing.getChild("right_wing_2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition skeletonfairy = partdefinition.addOrReplaceChild("skeletonfairy", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -4.0F));

		PartDefinition body = skeletonfairy.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(112, 102).addBox(-6.0F, -4.0F, -7.0F, 12.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 122).addBox(-6.0F, -2.0F, 7.0F, 12.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 122).addBox(-4.0F, -3.0F, -7.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(122, 42).addBox(-4.0F, 0.0F, -16.0F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(122, 64).addBox(3.0F, 2.0F, -15.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(100, 135).addBox(-4.0F, 2.0F, -15.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(122, 53).addBox(-3.0F, 0.0F, -9.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -7.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(122, 12).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 19.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 72).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 27.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(1.0F, 0.0F, 12.0F, 8.0F, 0.0F, 20.0F, new CubeDeformation(0.0F))
		.texOffs(56, 102).addBox(-9.0F, 0.0F, 12.0F, 8.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 14.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(106, 125).addBox(0.0F, -2.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 2.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_elbow = left_arm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(128, 64).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 5.0F, 0.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition left_hand = left_elbow.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(48, 136).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(68, 136).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(120, 125).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 2.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_elbow = right_arm.addOrReplaceChild("right_elbow", CubeListBuilder.create().texOffs(128, 78).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 5.0F, 0.0F, -0.829F, 0.0F, 0.0F));

		PartDefinition right_hand = right_elbow.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(58, 136).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(106, 136).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(78, 122).addBox(0.0F, -2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 2.0F, 13.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(134, 125).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.0F, 1.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(128, 92).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(92, 122).addBox(-3.0F, -2.0F, -2.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 2.0F, 13.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(78, 135).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, 1.0F, 1.2217F, 0.0F, 0.0F));

		PartDefinition right_ancle = right_knee.addOrReplaceChild("right_ancle", CubeListBuilder.create().texOffs(90, 135).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, -1.3526F, 0.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(122, 30).addBox(0.0F, -2.0F, -1.0F, 20.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(58, 72).addBox(0.0F, 0.0F, 2.0F, 20.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -2.0F, -5.0F));

		PartDefinition left_wing_2 = left_wing.addOrReplaceChild("left_wing_2", CubeListBuilder.create().texOffs(122, 0).addBox(0.0F, -2.0F, -1.0F, 25.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, 0.0F, 2.0F, 25.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, 0.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(122, 36).addBox(-20.0F, -2.0F, -1.0F, 20.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(58, 87).addBox(-20.0F, 0.0F, 2.0F, 20.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -2.0F, -5.0F));

		PartDefinition right_wing_2 = right_wing.addOrReplaceChild("right_wing_2", CubeListBuilder.create().texOffs(122, 6).addBox(-25.0F, -2.0F, -1.0F, 25.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-25.0F, 0.0F, 2.0F, 25.0F, 0.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		skeletonfairy.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return skeletonfairy;
	}
}