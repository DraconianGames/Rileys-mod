package net.riley.riley_mod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TruckArmorUpgrade<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = RileyModModelLayers.TRUCK_ARMOR_UPGRADE_LAYER;
	private final ModelPart truck;
	private final ModelPart armor_upgrade;
	private final ModelPart left_fron_wheel_cover;
	private final ModelPart right_fron_wheel_cover;
	private final ModelPart front_plating;
	private final ModelPart hood;
	private final ModelPart left_side_plating;
	private final ModelPart right_side_plating;
	private final ModelPart rear_plating;
	private final ModelPart roof_armor;
	private final ModelPart inside;

	public TruckArmorUpgrade(ModelPart root) {
		this.truck = root.getChild("truck");
		this.armor_upgrade = this.truck.getChild("armor_upgrade");
		this.left_fron_wheel_cover = this.armor_upgrade.getChild("left_fron_wheel_cover");
		this.right_fron_wheel_cover = this.armor_upgrade.getChild("right_fron_wheel_cover");
		this.front_plating = this.armor_upgrade.getChild("front_plating");
		this.hood = this.front_plating.getChild("hood");
		this.left_side_plating = this.armor_upgrade.getChild("left_side_plating");
		this.right_side_plating = this.armor_upgrade.getChild("right_side_plating");
		this.rear_plating = this.armor_upgrade.getChild("rear_plating");
		this.roof_armor = this.armor_upgrade.getChild("roof_armor");
		this.inside = this.armor_upgrade.getChild("inside");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition truck = partdefinition.addOrReplaceChild("truck", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition armor_upgrade = truck.addOrReplaceChild("armor_upgrade", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_fron_wheel_cover = armor_upgrade.addOrReplaceChild("left_fron_wheel_cover", CubeListBuilder.create().texOffs(0, 171).addBox(-1.0F, -4.0F, -5.0F, 1.0F, 10.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(108, 216).addBox(-9.0F, -2.7F, -5.75F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(108, 196).addBox(-1.0F, -8.0F, -3.0F, 1.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(208, 126).addBox(-4.0F, -8.0F, -3.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(58, 200).addBox(-3.0F, -8.0F, 7.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(190, 157).addBox(-3.0F, -8.0F, 12.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(202, 157).addBox(-3.0F, -8.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(18.0F, 0.0F, -29.0F));

		PartDefinition cube_r1 = left_fron_wheel_cover.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(206, 196).addBox(0.0F, 0.0F, -7.0F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -10.0F, 6.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition cube_r2 = left_fron_wheel_cover.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(210, 216).addBox(-4.0F, -2.0F, -1.0F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 13.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition cube_r3 = left_fron_wheel_cover.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(214, 188).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -8.0F, -3.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition right_fron_wheel_cover = armor_upgrade.addOrReplaceChild("right_fron_wheel_cover", CubeListBuilder.create().texOffs(38, 172).addBox(0.0F, -4.0F, -5.0F, 1.0F, 10.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(128, 216).addBox(0.0F, -2.7F, -5.75F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(142, 196).addBox(0.0F, -8.0F, -3.0F, 1.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(176, 211).addBox(1.0F, -8.0F, -3.0F, 3.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(58, 207).addBox(1.0F, -8.0F, 7.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(196, 157).addBox(1.0F, -8.0F, 12.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(206, 157).addBox(2.0F, -8.0F, 13.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.0F, 0.0F, -29.0F));

		PartDefinition cube_r4 = right_fron_wheel_cover.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(206, 206).addBox(-7.0F, 0.0F, -7.0F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -10.0F, 6.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition cube_r5 = right_fron_wheel_cover.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(70, 220).addBox(0.0F, -2.0F, -1.0F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 13.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition cube_r6 = right_fron_wheel_cover.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(148, 216).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -8.0F, -3.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition front_plating = armor_upgrade.addOrReplaceChild("front_plating", CubeListBuilder.create().texOffs(80, 220).addBox(-11.0F, -2.0F, -1.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(90, 220).addBox(10.0F, -2.0F, -1.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(190, 149).addBox(-12.0F, -24.0F, 15.0F, 24.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -37.0F));

		PartDefinition cube_r7 = front_plating.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(196, 103).addBox(-11.0F, -8.0F, -1.0F, 22.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, -1.0F, -0.1222F, 0.0F, 0.0F));

		PartDefinition hood = front_plating.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(128, 160).addBox(-5.0F, 0.0F, -16.0F, 10.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 15.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition cube_r8 = hood.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(126, 178).addBox(0.0F, 0.0F, -14.0F, 6.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3403F));

		PartDefinition cube_r9 = hood.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(170, 178).addBox(-6.0F, 0.0F, -14.0F, 6.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3403F));

		PartDefinition left_side_plating = armor_upgrade.addOrReplaceChild("left_side_plating", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -2.0F, -16.0F, 1.0F, 10.0F, 52.0F, new CubeDeformation(0.0F))
				.texOffs(76, 195).addBox(0.0F, -21.0F, -19.0F, 0.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -2.0F, 0.0F));

		PartDefinition right_side_plating = armor_upgrade.addOrReplaceChild("right_side_plating", CubeListBuilder.create().texOffs(0, 62).addBox(-1.0F, -2.0F, -16.0F, 1.0F, 10.0F, 52.0F, new CubeDeformation(0.0F))
				.texOffs(196, 36).addBox(0.0F, -21.0F, -19.0F, 0.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, -2.0F, 0.0F));

		PartDefinition rear_plating = armor_upgrade.addOrReplaceChild("rear_plating", CubeListBuilder.create().texOffs(208, 138).addBox(-8.0F, -26.0F, 0.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(128, 149).addBox(-15.0F, -4.0F, 35.0F, 30.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(106, 0).addBox(-15.0F, -4.0F, 0.0F, 30.0F, 1.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(168, 216).addBox(13.0F, -20.0F, 8.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(202, 216).addBox(-15.0F, -20.0F, 8.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r10 = rear_plating.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(180, 168).addBox(-13.0F, -2.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -18.4F, 9.4F, -0.5847F, 0.0F, 0.0F));

		PartDefinition cube_r11 = rear_plating.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(180, 164).addBox(-13.0F, -2.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -13.4F, 16.9F, -0.5847F, 0.0F, 0.0F));

		PartDefinition cube_r12 = rear_plating.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(180, 160).addBox(-13.0F, -2.0F, -1.0F, 26.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.4F, 26.0F, -0.5847F, 0.0F, 0.0F));

		PartDefinition cube_r13 = rear_plating.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(106, 81).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, new CubeDeformation(0.0F))
				.texOffs(106, 36).addBox(27.0F, 0.0F, 0.0F, 2.0F, 2.0F, 43.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, -27.0F, 0.0F, -0.5847F, 0.0F, 0.0F));

		PartDefinition roof_armor = armor_upgrade.addOrReplaceChild("roof_armor", CubeListBuilder.create(), PartPose.offset(0.0F, -27.0F, 0.0F));

		PartDefinition cube_r14 = roof_armor.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(64, 149).addBox(0.0F, 0.0F, -1.0F, 10.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition cube_r15 = roof_armor.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 148).addBox(-10.0F, 0.0F, -1.0F, 10.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -21.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r16 = roof_armor.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(104, 126).addBox(-15.0F, 0.0F, -22.0F, 30.0F, 1.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition inside = armor_upgrade.addOrReplaceChild("inside", CubeListBuilder.create().texOffs(0, 124).addBox(-15.0F, -27.0F, -22.0F, 30.0F, 2.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = inside.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(76, 172).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -21.0F, 0.0524F, 0.0F, 0.0F));

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