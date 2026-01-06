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
import net.riley.riley_mod.entity.animations.WhaleHunterAnimationDefinitions;
import net.riley.riley_mod.entity.custom.WhaleHunterEntity;

public class WhaleHunterModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "whalehuntermodel"), "main");
	private final ModelPart whalehunter;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart neck;
	private final ModelPart skull;
	private final ModelPart jaw;
	private final ModelPart topjaw;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart leftfluke;
	private final ModelPart rightfluke;
	private final ModelPart leftarm;
	private final ModelPart leftelbow;
	private final ModelPart rightarm;
	private final ModelPart rightelbow;

	public WhaleHunterModel(ModelPart root) {
		this.whalehunter = root.getChild("whalehunter");
		this.body = this.whalehunter.getChild("body");
		this.torso = this.body.getChild("torso");
		this.neck = this.torso.getChild("neck");
		this.skull = this.neck.getChild("skull");
		this.jaw = this.skull.getChild("jaw");
		this.topjaw = this.skull.getChild("topjaw");
		this.tail = this.torso.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.leftfluke = this.tail2.getChild("leftfluke");
		this.rightfluke = this.tail2.getChild("rightfluke");
		this.leftarm = this.body.getChild("leftarm");
		this.leftelbow = this.leftarm.getChild("leftelbow");
		this.rightarm = this.body.getChild("rightarm");
		this.rightelbow = this.rightarm.getChild("rightelbow");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition whalehunter = partdefinition.addOrReplaceChild("whalehunter", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition body = whalehunter.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -24.0F, 16.0F, 16.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 176).addBox(-7.0F, -7.0F, -13.0F, 14.0F, 14.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -24.0F));

		PartDefinition skull = neck.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(110, 198).addBox(-7.0F, -7.0F, -9.0F, 14.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -13.0F));

		PartDefinition jaw = skull.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(122, 126).addBox(-8.0F, -7.0F, -42.0F, 16.0F, 7.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -9.0F));

		PartDefinition topjaw = skull.addOrReplaceChild("topjaw", CubeListBuilder.create().texOffs(0, 128).addBox(-7.0F, 0.0F, -41.0F, 14.0F, 7.0F, 41.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -9.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 65).addBox(-6.0F, -7.0F, 0.0F, 12.0F, 14.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 25.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(122, 65).addBox(-4.0F, -6.0F, 0.0F, 8.0F, 12.0F, 49.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 49.0F));

		PartDefinition leftfluke = tail2.addOrReplaceChild("leftfluke", CubeListBuilder.create().texOffs(130, 52).addBox(0.0F, -1.0F, 0.0F, 17.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(110, 175).addBox(10.0F, -1.0F, 4.0F, 9.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(172, 52).addBox(0.0F, -1.0F, -5.0F, 14.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(156, 198).addBox(4.0F, -1.0F, 4.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 45.0F));

		PartDefinition rightfluke = tail2.addOrReplaceChild("rightfluke", CubeListBuilder.create().texOffs(130, 58).addBox(-17.0F, -1.0F, 0.0F, 17.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(170, 175).addBox(-19.0F, -1.0F, 4.0F, 9.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
		.texOffs(0, 203).addBox(-14.0F, -1.0F, -5.0F, 14.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(196, 198).addBox(-10.0F, -1.0F, 4.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 45.0F));

		PartDefinition leftarm = body.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(54, 176).addBox(0.0F, -2.0F, -5.0F, 17.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -13.0F));

		PartDefinition leftelbow = leftarm.addOrReplaceChild("leftelbow", CubeListBuilder.create().texOffs(130, 0).addBox(0.0F, -1.0F, -5.0F, 39.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, 0.0F, 0.0F));

		PartDefinition rightarm = body.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(54, 191).addBox(-17.0F, -2.0F, -5.0F, 17.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, -13.0F));

		PartDefinition rightelbow = rightarm.addOrReplaceChild("rightelbow", CubeListBuilder.create().texOffs(130, 26).addBox(-39.0F, -1.0F, -5.0F, 39.0F, 2.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity instanceof WhaleHunterEntity whaleHunter) {
			this.animate(whaleHunter.idleAnimationState, WhaleHunterAnimationDefinitions.WHALE_HUNTER_IDLE, ageInTicks);
			this.animate(whaleHunter.attackAnimationState, WhaleHunterAnimationDefinitions.WHALE_HUNTER_ATTACK, ageInTicks);
			this.animate(whaleHunter.swimAnimationState, WhaleHunterAnimationDefinitions.WHALE_HUNTER_SWIM, ageInTicks);

			float pitch = entity.getXRot();
			this.whalehunter.xRot = pitch * ((float)Math.PI / 180F);

			float turnSpeed = Mth.wrapDegrees(whaleHunter.yBodyRot - whaleHunter.yBodyRotO);
			// Limit the tilt so it doesn't flip over (e.g., max 30 degrees)
			float tiltAmount = Mth.clamp(turnSpeed * 2.0F, -30.0F, 30.0F);
			// Apply to Z axis (Roll)
			this.whalehunter.zRot = -tiltAmount * ((float)Math.PI / 180F);
		}
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		whalehunter.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return whalehunter;
	}
}