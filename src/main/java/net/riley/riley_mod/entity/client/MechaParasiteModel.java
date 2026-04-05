package net.riley.riley_mod.entity.client;// Made with Blockbench 5.1.3
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
import net.riley.riley_mod.entity.animations.MechaParasiteAnimationDefinitions;
import net.riley.riley_mod.entity.custom.MechaParasiteEntity;


public class MechaParasiteModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mechaparasitemodel"), "main");
	private final ModelPart mecha_parasite;
	private final ModelPart torso;
	private final ModelPart tail;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart head;
	private final ModelPart left_horn;
	private final ModelPart right_horn;
	private final ModelPart left_arm_begining;
	private final ModelPart left_arm;
	private final ModelPart left_elbow;
	private final ModelPart left_ancle;
	private final ModelPart claw;
	private final ModelPart right_arm_beginning;
	private final ModelPart right_arm;
	private final ModelPart right_elbow;
	private final ModelPart right_ancle;
	private final ModelPart claw2;

	public MechaParasiteModel(ModelPart root) {
		this.mecha_parasite = root.getChild("mecha_parasite");
		this.torso = this.mecha_parasite.getChild("torso");
		this.tail = this.torso.getChild("tail");
		this.tail_2 = this.tail.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.head = this.torso.getChild("head");
		this.left_horn = this.head.getChild("left_horn");
		this.right_horn = this.head.getChild("right_horn");
		this.left_arm_begining = this.mecha_parasite.getChild("left_arm_begining");
		this.left_arm = this.left_arm_begining.getChild("left_arm");
		this.left_elbow = this.left_arm.getChild("left_elbow");
		this.left_ancle = this.left_elbow.getChild("left_ancle");
		this.claw = this.left_ancle.getChild("claw");
		this.right_arm_beginning = this.mecha_parasite.getChild("right_arm_beginning");
		this.right_arm = this.right_arm_beginning.getChild("right_arm");
		this.right_elbow = this.right_arm.getChild("right_elbow");
		this.right_ancle = this.right_elbow.getChild("right_ancle");
		this.claw2 = this.right_ancle.getChild("claw2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mecha_parasite = partdefinition.addOrReplaceChild("mecha_parasite", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition torso = mecha_parasite.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 14).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));

		PartDefinition tail_2 = tail.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(22, 14).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 21).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -5.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -2.0F, -2.0F, -0.2618F, 0.0F, 0.3054F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(4, 30).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, -2.0F, -0.2618F, 0.0F, -0.3054F));

		PartDefinition left_arm_begining = mecha_parasite.addOrReplaceChild("left_arm_begining", CubeListBuilder.create(), PartPose.offset(4.0F, -1.0F, -3.0F));

		PartDefinition left_arm = left_arm_begining.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(14, 28).addBox(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition left_elbow = left_arm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(16, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 0.0F, 0.0F));

		PartDefinition left_ancle = left_elbow.addOrReplaceChild("left_ancle", CubeListBuilder.create().texOffs(14, 23).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition claw = left_ancle.addOrReplaceChild("claw", CubeListBuilder.create().texOffs(34, 8).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r1 = claw.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 11).addBox(-1.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -2.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition right_arm_beginning = mecha_parasite.addOrReplaceChild("right_arm_beginning", CubeListBuilder.create(), PartPose.offset(-4.0F, -1.0F, -3.0F));

		PartDefinition right_arm = right_arm_beginning.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition right_elbow = right_arm.addOrReplaceChild("right_elbow", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 0.0F));

		PartDefinition right_ancle = right_elbow.addOrReplaceChild("right_ancle", CubeListBuilder.create().texOffs(34, 4).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

		PartDefinition claw2 = right_ancle.addOrReplaceChild("claw2", CubeListBuilder.create().texOffs(0, 36).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r2 = claw2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(6, 36).addBox(0.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.0F, -2.0F, 0.8727F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(MechaParasiteAnimationDefinitions.MECHA_PARASITE_WALK, limbSwing, limbSwingAmount, 2f, 2.25f);
		this.animate(((MechaParasiteEntity) entity).idleAnimationState, MechaParasiteAnimationDefinitions.MECHA_PARASITE_IDLE,ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mecha_parasite.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public ModelPart root() {
		return mecha_parasite;
	}
}