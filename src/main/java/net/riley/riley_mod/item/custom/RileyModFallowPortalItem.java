package net.riley.riley_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.worldgen.portal.RileyModFallowPortalShape;

import java.util.Optional;

public class RileyModFallowPortalItem extends Item {
    public RileyModFallowPortalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos portalPos = pos.relative(direction);

        if (!level.isClientSide()) {
            // Try X axis first
            Optional<RileyModFallowPortalShape> optional = RileyModFallowPortalShape.findEmptyPortalShape(level, portalPos, Direction.Axis.X);

            // If X fails, try Z axis
            if (optional.isEmpty()) {
                optional = RileyModFallowPortalShape.findEmptyPortalShape(level, portalPos, Direction.Axis.Z);
            }

            if (optional.isPresent()) {
                optional.get().createPortalBlocks();
                // Play a sound to show it worked!
                level.playSound(null, portalPos, net.minecraft.sounds.SoundEvents.PORTAL_TRIGGER, net.minecraft.world.entity.EntityDimensions.fixed(1f, 1f).width > 0 ? net.minecraft.sounds.SoundSource.BLOCKS : net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
                context.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }
}
