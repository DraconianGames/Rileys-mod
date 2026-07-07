package net.riley.riley_mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.entity.custom.BaseVehicleEntity;
import net.riley.riley_mod.entity.custom.VehicleHitboxPart;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class VehicleHitboxDebugRenderer {
    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            return;
        }

        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }

        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        Vec3 cameraPosition = minecraft.gameRenderer.getMainCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();

        for (Entity entity : minecraft.level.entitiesForRendering()) {
            if (!(entity instanceof BaseVehicleEntity vehicle)) {
                continue;
            }

            for (VehicleHitboxPart part : vehicle.getDebugVehicleHitboxParts()) {
                AABB box = vehicle.getDebugVehiclePartBox(part).move(
                        -cameraPosition.x,
                        -cameraPosition.y,
                        -cameraPosition.z
                );

                drawPartBox(poseStack, box, part);
            }
        }
    }

    private static void drawPartBox(PoseStack poseStack, AABB box, VehicleHitboxPart part) {
        float red = 1.0F;
        float green = 1.0F;
        float blue = 1.0F;

        switch (part.type()) {
            case WHEEL -> {
                red = 0.1F;
                green = 1.0F;
                blue = 0.1F;
            }
            case BUMPER -> {
                red = 1.0F;
                green = 0.25F;
                blue = 0.05F;
            }
            case SEAT -> {
                red = 0.1F;
                green = 0.4F;
                blue = 1.0F;
            }
            case MENU -> {
                red = 0.8F;
                green = 0.1F;
                blue = 1.0F;
            }
        }

        LevelRenderer.renderLineBox(
                poseStack,
                Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(net.minecraft.client.renderer.RenderType.lines()),
                box,
                red,
                green,
                blue,
                1.0F
        );
    }
}