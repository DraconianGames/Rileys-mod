package net.riley.riley_mod.client;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.network.*;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTickEvents {
    private static boolean lastSneakingState = false;
    private static boolean lastJumpState = false;
    private static boolean lastUseState = false;
    private static int wingFlapTicks = 0;
    private static boolean sneaking = false;

    private static UUID pendingPetSummonUUID = null;

    public static int getWingFlapTicks() {
        return wingFlapTicks;
    }

    public static boolean isSneaking() {
        return sneaking;
    }

    public static void beginPetSummonTargeting(UUID petUUID) {
        pendingPetSummonUUID = petUUID;
        RileyModPackets.sendToServer(new PetSummonChargePacket(true));
    }

    private static boolean isPetSummonTargeting() {
        return pendingPetSummonUUID != null;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (wingFlapTicks > 0) {
            --wingFlapTicks;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            lastSneakingState = false;
            lastJumpState = false;
            lastUseState = false;
            wingFlapTicks = 0;
            sneaking = false;
            pendingPetSummonUUID = null;
            return;
        }
        if (isPetSummonTargeting()) {
            spawnRightHandSoulFlame(player);
            handlePetSummonPlacement(player);
        }

        sneaking = player.isShiftKeyDown();

        if (sneaking != lastSneakingState) {
            lastSneakingState = sneaking;
            RileyModPackets.sendToServer(new WingSneakPacket(sneaking));
        }

        boolean jumping = Minecraft.getInstance().options.keyJump.isDown();
        boolean jumpPressed = jumping && !lastJumpState;

        if (jumpPressed && !player.onGround() && !player.isInWater() && !player.isInLava()) {
            ResourceLocation wingsId = ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "wings");
            int level = ClientAugmentState.getLevel(wingsId);

            if (level >= 3 && player.isFallFlying()) {
                wingFlapTicks = 40;
                RileyModPackets.sendToServer(new WingFlapPacket());
            } else if (level >= 2) {
                RileyModPackets.sendToServer(new WingJumpPacket());
            }
        }

        lastJumpState = jumping;
    }
    private static void spawnRightHandSoulFlame(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        if (minecraft.options.getCameraType() == CameraType.FIRST_PERSON) {
            spawnFirstPersonRightHandSoulFlame(player);
        } else {
            spawnThirdPersonRightHandSoulFlame(player);
        }
    }

    private static void spawnFirstPersonRightHandSoulFlame(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        Vec3 look = player.getLookAngle();
        Vec3 right = new Vec3(0.0D, 1.0D, 0.0D).cross(look).normalize();

        Vec3 handPos = player.getEyePosition()
                .add(look.scale(0.55D))
                .add(right.scale(-0.28D))
                .add(0.0D, -0.32D, 0.0D);

        minecraft.level.addParticle(
                ParticleTypes.SOUL_FIRE_FLAME,
                handPos.x + (minecraft.level.random.nextDouble() - 0.5D) * 0.035D,
                handPos.y + (minecraft.level.random.nextDouble() - 0.5D) * 0.035D,
                handPos.z + (minecraft.level.random.nextDouble() - 0.5D) * 0.035D,
                0.0D,
                0.01D,
                0.0D
        );
    }

    private static void spawnThirdPersonRightHandSoulFlame(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        double yawRadians = Math.toRadians(player.yBodyRot);

        Vec3 forward = new Vec3(
                -Math.sin(yawRadians),
                0.0D,
                Math.cos(yawRadians)
        );

        Vec3 right = new Vec3(
                -Math.cos(yawRadians),
                0.0D,
                -Math.sin(yawRadians)
        );

        Vec3 handPos = player.position()
                .add(0.0D, player.isCrouching() ? 0.85D : 1.05D, 0.0D)
                .add(forward.scale(0.18D))
                .add(right.scale(0.42D));

        minecraft.level.addParticle(
                ParticleTypes.SOUL_FIRE_FLAME,
                handPos.x + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                handPos.y + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                handPos.z + (minecraft.level.random.nextDouble() - 0.5D) * 0.08D,
                0.0D,
                0.015D,
                0.0D
        );
    }

    private static void handlePetSummonPlacement(LocalPlayer player) {
        Minecraft minecraft = Minecraft.getInstance();

        boolean using = minecraft.options.keyUse.isDown();
        boolean usePressed = using && !lastUseState;
        lastUseState = using;

        if (!usePressed || pendingPetSummonUUID == null) {
            return;
        }

        BlockHitResult hitResult = getPetSummonHitResult(player);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos clickedPos = hitResult.getBlockPos();
        Direction clickedFace = hitResult.getDirection();

        if (clickedFace != Direction.UP && !minecraft.level.getFluidState(clickedPos).isSource()) {
            return;
        }

        double summonX = clickedPos.getX() + 0.5D;
        double summonY = clickedPos.getY() + 1.0D;
        double summonZ = clickedPos.getZ() + 0.5D;

        if (minecraft.level.getFluidState(clickedPos).isSource()) {
            summonY = clickedPos.getY() + 0.05D;
        }

        RileyModPackets.sendToServer(new PetSummonLocationPacket(
                pendingPetSummonUUID,
                summonX,
                summonY,
                summonZ
        ));

        RileyModPackets.sendToServer(new PetSummonChargePacket(false));
        pendingPetSummonUUID = null;
    }

    private static BlockHitResult getPetSummonHitResult(LocalPlayer player) {
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(6.0D));

        return player.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.ANY,
                player
        ));
    }
}