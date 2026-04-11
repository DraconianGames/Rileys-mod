package net.riley.riley_mod.client;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 5.1.3
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class WingAnimationDefinitions {
	public static final AnimationDefinition WING_FOLDED = AnimationDefinition.Builder.withLength(1.0F)
		.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -87.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 87.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 180.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -180.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 182.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -182.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition WING_FLAP = AnimationDefinition.Builder.withLength(2.0F).looping()
			.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 87.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(2.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -87.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.SCALE,
					new Keyframe(0.0F, KeyframeAnimations.scaleVec(2.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 57.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 15.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -57.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("left_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 50.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("right_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, -50.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition WING_GLIDE = AnimationDefinition.Builder.withLength(2.0F).looping()
		.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 70.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(2.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -70.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -72.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(2.0F, 2.0F, 2.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -22.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -25.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -10.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -12.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition WING_FALL = AnimationDefinition.Builder.withLength(1.0F).looping()
		.addAnimation("left_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 87.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5F, -2.5F, 87.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, 87.5F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, -87.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(87.5F, 2.5F, -87.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(82.5F, 0.0F, -87.5F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_mid", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 7.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_wing_end", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
}