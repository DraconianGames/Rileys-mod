package net.riley.riley_mod.entity.client;// Made with Blockbench 5.1.6
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
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.animations.SummoningCircleAnimationDefinitions;
import net.riley.riley_mod.entity.custom.SummoningCircleEntity;

public class SummoningCircleModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "summoningcircle"), "main");
	private final ModelPart summoning_circle_base;
	private final ModelPart summoning_circle_main;
	private final ModelPart under_shadow_main;
	private final ModelPart lines_main;
	private final ModelPart main_straight;
	private final ModelPart main_east_vert;
	private final ModelPart main_west_vert;
	private final ModelPart main_south_vert;
	private final ModelPart main_north_vert;
	private final ModelPart main_straight_1;
	private final ModelPart main_straight_2;
	private final ModelPart main_straight_3;
	private final ModelPart main_straight_4;
	private final ModelPart main_angle;
	private final ModelPart main_east_angle;
	private final ModelPart main_west_angle;
	private final ModelPart main_south_angle;
	private final ModelPart main_north_angle;
	private final ModelPart main_angle_1;
	private final ModelPart main_angle_2;
	private final ModelPart main_angle_3;
	private final ModelPart main_angle_4;

	public SummoningCircleModel(ModelPart root) {
		this.summoning_circle_base = root.getChild("summoning_circle_base");
		this.summoning_circle_main = this.summoning_circle_base.getChild("summoning_circle_main");
		this.under_shadow_main = this.summoning_circle_main.getChild("under_shadow_main");
		this.lines_main = this.summoning_circle_main.getChild("lines_main");
		this.main_straight = this.lines_main.getChild("main_straight");
		this.main_east_vert = this.main_straight.getChild("main_east_vert");
		this.main_west_vert = this.main_straight.getChild("main_west_vert");
		this.main_south_vert = this.main_straight.getChild("main_south_vert");
		this.main_north_vert = this.main_straight.getChild("main_north_vert");
		this.main_straight_1 = this.main_straight.getChild("main_straight_1");
		this.main_straight_2 = this.main_straight.getChild("main_straight_2");
		this.main_straight_3 = this.main_straight.getChild("main_straight_3");
		this.main_straight_4 = this.main_straight.getChild("main_straight_4");
		this.main_angle = this.lines_main.getChild("main_angle");
		this.main_east_angle = this.main_angle.getChild("main_east_angle");
		this.main_west_angle = this.main_angle.getChild("main_west_angle");
		this.main_south_angle = this.main_angle.getChild("main_south_angle");
		this.main_north_angle = this.main_angle.getChild("main_north_angle");
		this.main_angle_1 = this.main_angle.getChild("main_angle_1");
		this.main_angle_2 = this.main_angle.getChild("main_angle_2");
		this.main_angle_3 = this.main_angle.getChild("main_angle_3");
		this.main_angle_4 = this.main_angle.getChild("main_angle_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition summoning_circle_base = partdefinition.addOrReplaceChild("summoning_circle_base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition summoning_circle_main = summoning_circle_base.addOrReplaceChild("summoning_circle_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition under_shadow_main = summoning_circle_main.addOrReplaceChild("under_shadow_main", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, 0.0F, -10.0F, 48.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.1F, 0.0F));

		PartDefinition cube_r1 = under_shadow_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 60).addBox(-24.0F, 0.0F, -10.0F, 48.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = under_shadow_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 40).addBox(-24.0F, 0.0F, -10.0F, 48.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r3 = under_shadow_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 20).addBox(-24.0F, 0.0F, -10.0F, 48.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition lines_main = summoning_circle_main.addOrReplaceChild("lines_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition main_straight = lines_main.addOrReplaceChild("main_straight", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition main_east_vert = main_straight.addOrReplaceChild("main_east_vert", CubeListBuilder.create().texOffs(136, 8).addBox(0.0F, -1.0F, -20.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 0.0F, 10.0F));

		PartDefinition main_west_vert = main_straight.addOrReplaceChild("main_west_vert", CubeListBuilder.create().texOffs(136, 29).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 0.0F, -10.0F));

		PartDefinition main_south_vert = main_straight.addOrReplaceChild("main_south_vert", CubeListBuilder.create().texOffs(136, 71).addBox(-20.0F, -1.0F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, 24.0F));

		PartDefinition main_north_vert = main_straight.addOrReplaceChild("main_north_vert", CubeListBuilder.create().texOffs(136, 73).addBox(0.0F, -1.0F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, -24.0F));

		PartDefinition main_straight_1 = main_straight.addOrReplaceChild("main_straight_1", CubeListBuilder.create().texOffs(0, 80).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, -23.0F));

		PartDefinition main_straight_2 = main_straight.addOrReplaceChild("main_straight_2", CubeListBuilder.create().texOffs(136, 2).addBox(-46.0F, -1.0F, -1.0F, 46.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(23.0F, 0.0F, 10.0F));

		PartDefinition main_straight_3 = main_straight.addOrReplaceChild("main_straight_3", CubeListBuilder.create().texOffs(94, 80).addBox(0.0F, -1.0F, -46.0F, 1.0F, 1.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 23.0F));

		PartDefinition main_straight_4 = main_straight.addOrReplaceChild("main_straight_4", CubeListBuilder.create().texOffs(136, 0).addBox(0.0F, -1.0F, 0.0F, 46.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.0F, 0.0F, -10.0F));

		PartDefinition main_angle = lines_main.addOrReplaceChild("main_angle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition main_east_angle = main_angle.addOrReplaceChild("main_east_angle", CubeListBuilder.create().texOffs(136, 50).addBox(0.0F, -1.0F, -20.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(-24.0F, 0.0F, 10.0F));

		PartDefinition main_west_angle = main_angle.addOrReplaceChild("main_west_angle", CubeListBuilder.create().texOffs(0, 174).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(24.0F, 0.0F, -10.0F));

		PartDefinition main_south_angle = main_angle.addOrReplaceChild("main_south_angle", CubeListBuilder.create().texOffs(136, 75).addBox(-20.0F, -1.0F, -1.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, 24.0F));

		PartDefinition main_north_angle = main_angle.addOrReplaceChild("main_north_angle", CubeListBuilder.create().texOffs(136, 77).addBox(0.0F, -1.0F, 0.0F, 20.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, -24.0F));

		PartDefinition main_angle_1 = main_angle.addOrReplaceChild("main_angle_1", CubeListBuilder.create().texOffs(136, 4).addBox(-46.0F, -1.0F, -1.0F, 46.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(23.0F, 0.0F, 10.0F));

		PartDefinition main_angle_2 = main_angle.addOrReplaceChild("main_angle_2", CubeListBuilder.create().texOffs(94, 127).addBox(0.0F, -1.0F, -46.0F, 1.0F, 1.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 23.0F));

		PartDefinition main_angle_3 = main_angle.addOrReplaceChild("main_angle_3", CubeListBuilder.create().texOffs(136, 6).addBox(0.0F, -1.0F, 0.0F, 46.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-23.0F, 0.0F, -10.0F));

		PartDefinition main_angle_4 = main_angle.addOrReplaceChild("main_angle_4", CubeListBuilder.create().texOffs(0, 127).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, -23.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity instanceof SummoningCircleEntity summoningCircle) {
			this.animate(
					summoningCircle.releaseAnimationState,
					SummoningCircleAnimationDefinitions.CIRCLE_RELEASE,
					ageInTicks,
					1.0F
			);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		summoning_circle_base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return summoning_circle_base;
	}
}