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
import net.riley.riley_mod.entity.animations.FrostHopperAnimationDefinitions;
import net.riley.riley_mod.entity.custom.FrostHopperEntity;

public class FrostHopperModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "frosthoppermodel"), "main");
	private final ModelPart frosthopper;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart horn;
	private final ModelPart left_leg;
	private final ModelPart left_knee;
	private final ModelPart left_ancle;
	private final ModelPart right_leg;
	private final ModelPart right_knee;
	private final ModelPart right_ancle;

	public FrostHopperModel(ModelPart root) {
		this.frosthopper = root.getChild("frosthopper");
		this.body = this.frosthopper.getChild("body");
		this.torso = this.body.getChild("torso");
		this.tail = this.torso.getChild("tail");
		this.head = this.torso.getChild("head");
		this.horn = this.head.getChild("horn");
		this.left_leg = this.body.getChild("left_leg");
		this.left_knee = this.left_leg.getChild("left_knee");
		this.left_ancle = this.left_knee.getChild("left_ancle");
		this.right_leg = this.body.getChild("right_leg");
		this.right_knee = this.right_leg.getChild("right_knee");
		this.right_ancle = this.right_knee.getChild("right_ancle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition frosthopper = partdefinition.addOrReplaceChild("frosthopper", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition body = frosthopper.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 20).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -3.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(-1.0F, -2.0F, -2.0F));

		PartDefinition cube_r1 = horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 12).addBox(-0.5F, -1.0F, -5.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -1.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -2.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 3.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition left_knee = left_leg.addOrReplaceChild("left_knee", CubeListBuilder.create().texOffs(12, 21).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_knee.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(22, 4).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(6, 21).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 3.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_knee = right_leg.addOrReplaceChild("right_knee", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_ancle = right_knee.addOrReplaceChild("right_ancle", CubeListBuilder.create().texOffs(12, 25).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 3.0F, -0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(FrostHopperAnimationDefinitions.FROST_HOPPER_WALK,limbSwing, limbSwingAmount,2f,2.25f);
		this.animate(((FrostHopperEntity) entity).idleAnimationState, FrostHopperAnimationDefinitions.FROST_HOPPER_IDLE,ageInTicks, 1f);
		this.animate(((FrostHopperEntity) entity).attackAnimationState, FrostHopperAnimationDefinitions.FROST_HOPPER_ATTACK,ageInTicks, 1f);
		}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		frosthopper.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return frosthopper;
	}
}