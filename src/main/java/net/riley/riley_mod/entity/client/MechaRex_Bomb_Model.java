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
import net.minecraft.world.entity.Entity;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.animations.MechaRex_BombAnimationDefinitions;
import net.riley.riley_mod.entity.custom.MechaRexBombEntity;

public class MechaRex_Bomb_Model<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = RileyModModelLayers.MECHAREXBOMB_LAYER;

	private final ModelPart mecharex_bomb;
	private final ModelPart flames;

	public MechaRex_Bomb_Model(ModelPart root) {
		this.mecharex_bomb = root.getChild("mecharex_bomb");
		this.flames = this.mecharex_bomb.getChild("flames");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mecharex_bomb = partdefinition.addOrReplaceChild("mecharex_bomb", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(32, 0).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 36).addBox(-1.0F, -1.0F, -16.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(14, 36).addBox(-1.0F, -1.0F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 42).addBox(-2.0F, -2.0F, 6.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));

		PartDefinition cube_r1 = mecharex_bomb.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 29).addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r2 = mecharex_bomb.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 16).addBox(0.0F, -3.0F, -3.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r3 = mecharex_bomb.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 3.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r4 = mecharex_bomb.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 3.0F, -2.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition flames = mecharex_bomb.addOrReplaceChild("flames", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = flames.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(32, 10).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r6 = flames.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 12).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity instanceof MechaRexBombEntity bomb) {
			// Always-on looping flames animation
			this.animate(bomb.activeAnimationState, MechaRex_BombAnimationDefinitions.MECHAREX_MISSILE_ACTIVE, ageInTicks);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		mecharex_bomb.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return mecharex_bomb;
	}
}