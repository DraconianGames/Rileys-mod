package net.riley.riley_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.ToothFairyEntity;
import org.jetbrains.annotations.NotNull;

public class CaggedFairyItem extends Item {

    public CaggedFairyItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockPos clicked = context.getClickedPos();
            Direction face = context.getClickedFace();
            BlockPos spawnPos = clicked.relative(face);
            spawnBabyAt(level, context.getPlayer(), spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, context.getItemInHand());
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    private void spawnBabyAt(Level level, Player player, double x, double y, double z, ItemStack stack) {
        @SuppressWarnings("unchecked")
        EntityType<ToothFairyEntity> type = (EntityType<ToothFairyEntity>) RileyModEntities.TOOTHFAIRY.get();
        ToothFairyEntity fairy = type.create(level);
        if (fairy != null) {
            fairy.moveTo(x, y, z, player != null ? player.getYRot() : 0.0F, 0.0F);
            fairy.setAge(-24000); // make it a baby (same approach used elsewhere in the repo)
            // Optionally set tamed/owner here if desired:
            // if (player != null) { fairy.setTame(true); fairy.setOwnerUUID(player.getUUID()); }
            level.addFreshEntity(fairy);

            if (player != null && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
    }
}
