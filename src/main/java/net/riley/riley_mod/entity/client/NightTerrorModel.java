package net.riley.riley_mod.entity.client;// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.entity.animations.RileyModNightTerrorAnimationDefinitions;
import net.riley.riley_mod.entity.custom.NightTerrorEntity;


public class NightTerrorModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "nightterror"), "main");
	private final ModelPart night_terror;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart upjaw;
	private final ModelPart lowerjaw;
	private final ModelPart tailbase;
	private final ModelPart tailmid;
	private final ModelPart tailmid2;
	private final ModelPart stinger;
	private final ModelPart leftwingbase;
	private final ModelPart leftwingend;
	private final ModelPart rightwingbase;
	private final ModelPart rightwingend;

	public NightTerrorModel(ModelPart root) {
		this.night_terror = root.getChild("night_terror");
		this.body = this.night_terror.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.upjaw = this.head.getChild("upjaw");
		this.lowerjaw = this.head.getChild("lowerjaw");
		this.tailbase = this.torso.getChild("tailbase");
		this.tailmid = this.tailbase.getChild("tailmid");
		this.tailmid2 = this.tailmid.getChild("tailmid2");
		this.stinger = this.tailmid2.getChild("stinger");
		this.leftwingbase = this.body.getChild("leftwingbase");
		this.leftwingend = this.leftwingbase.getChild("leftwingend");
		this.rightwingbase = this.body.getChild("rightwingbase");
		this.rightwingend = this.rightwingbase.getChild("rightwingend");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition night_terror = partdefinition.addOrReplaceChild("night_terror", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, 3.0F));

		PartDefinition body = night_terror.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 1.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(118, 80).addBox(-9.0F, -3.0F, -16.0F, 18.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(136, 18).addBox(-8.0F, -2.0F, 2.0F, 16.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(136, 68).addBox(-2.0F, -2.0F, -23.0F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(20, 144).addBox(8.0F, -1.0F, 2.0F, 1.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(40, 144).addBox(-9.0F, -1.0F, 2.0F, 1.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 144).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -23.0F));

		PartDefinition upjaw = head.addOrReplaceChild("upjaw", CubeListBuilder.create().texOffs(136, 52).addBox(-3.0F, 0.0F, -12.0F, 6.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -4.0F));

		PartDefinition lowerjaw = head.addOrReplaceChild("lowerjaw", CubeListBuilder.create().texOffs(70, 136).addBox(-3.0F, -4.0F, -12.0F, 6.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -4.0F));

		PartDefinition tailbase = torso.addOrReplaceChild("tailbase", CubeListBuilder.create().texOffs(118, 105).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 3.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

		PartDefinition tailmid = tailbase.addOrReplaceChild("tailmid", CubeListBuilder.create().texOffs(118, 130).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 2.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 22.0F));

		PartDefinition tailmid2 = tailmid.addOrReplaceChild("tailmid2", CubeListBuilder.create().texOffs(136, 32).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 23.0F));

		PartDefinition stinger = tailmid2.addOrReplaceChild("stinger", CubeListBuilder.create().texOffs(106, 136).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(106, 141).addBox(-0.5F, -0.5F, 3.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 18.0F));

		PartDefinition leftwingbase = body.addOrReplaceChild("leftwingbase", CubeListBuilder.create().texOffs(0, 136).addBox(0.0F, -2.0F, -2.0F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 80).addBox(0.0F, 0.0F, 2.0F, 31.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -1.0F, -9.0F));

		PartDefinition leftwingend = leftwingbase.addOrReplaceChild("leftwingend", CubeListBuilder.create().texOffs(136, 8).addBox(0.0F, -1.0F, -1.0F, 28.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, 0.0F, 2.0F, 28.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(31.0F, 0.0F, 0.0F));

		PartDefinition rightwingbase = body.addOrReplaceChild("rightwingbase", CubeListBuilder.create().texOffs(136, 0).addBox(-31.0F, -2.0F, -2.0F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(-31.0F, 0.0F, 2.0F, 31.0F, 0.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -1.0F, -9.0F));

		PartDefinition rightwingend = rightwingbase.addOrReplaceChild("rightwingend", CubeListBuilder.create().texOffs(136, 13).addBox(-28.0F, -1.0F, -1.0F, 28.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-28.0F, 0.0F, 2.0F, 28.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(-31.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		NightTerrorEntity nightTerror = (NightTerrorEntity) entity;

		this.animate(nightTerror.flyAnimationState, RileyModNightTerrorAnimationDefinitions.NIGHT_TERROR_FLY, ageInTicks, 1f);
		this.animate(nightTerror.flyAttackAnimationState, RileyModNightTerrorAnimationDefinitions.NIGHT_TERROR_ATTACK, ageInTicks, 1f);
		this.animate(nightTerror.roarAnimationState, RileyModNightTerrorAnimationDefinitions.NIGHT_TERROR_ROAR, ageInTicks, 1f); // Add this

		// PITCH
		this.night_terror.xRot = entity.getXRot() * ((float)Math.PI / 180F);

		// BANKING (Turn Tilt)
		float turnSpeed = Mth.wrapDegrees(nightTerror.yBodyRot - nightTerror.yBodyRotO);
		float tiltAmount = Mth.clamp(turnSpeed * 2.0F, -30.0F, 30.0F);
		this.night_terror.zRot = -tiltAmount * ((float)Math.PI / 180F);
	}


	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		night_terror.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}


	@Override
	public ModelPart root() {
		return night_terror;
	}
}