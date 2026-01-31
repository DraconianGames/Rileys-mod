package net.riley.riley_mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.entity.animations.RileyModAnimationDefinitions;
import net.riley.riley_mod.entity.custom.RapterEntity;

public class RapterModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "rapter_model"), "main");
	private final ModelPart rapter;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_foot;

	public RapterModel(ModelPart root) {
		this.rapter = root.getChild("rapter");
		this.body = this.rapter.getChild("body");
		this.torso = this.body.getChild("torso");
		this.tail = this.torso.getChild("tail");
		this.head = this.torso.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_foot = this.left_knee.getChild("left_foot");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.right_foot = this.right_knee.getChild("right_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rapter = partdefinition.addOrReplaceChild("rapter", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = rapter.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 3.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(20, 11).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(14, 38).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 21).addBox(0.0F, -5.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, 0.0F, 5.0F, 2.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(28, 32).addBox(0.0F, -4.0F, 0.0F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(0.0F, -2.0F, 5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(38, 8).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-1.0F, -4.0F, -7.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(18, 23).addBox(0.0F, -4.0F, 0.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(0.0F, -6.0F, -6.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(14, 32).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(34, 21).addBox(0.0F, -1.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 1.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(14, 23).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.0F, 1.0F));

		PartDefinition left_foot = left_knee.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(24, 7).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(22, 38).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.0F, 1.0F));

		PartDefinition right_foot = right_knee.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(38, 14).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(RileyModAnimationDefinitions.RAPTER_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		this.animate(((RapterEntity) entity).idleAnimationState, RileyModAnimationDefinitions.RAPTER_IDLE,ageInTicks, 1f);
		this.animate(((RapterEntity) entity).attackAnimationState, RileyModAnimationDefinitions.RAPTER_ATTACK,ageInTicks, 1f);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		rapter.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return rapter;
	}
}