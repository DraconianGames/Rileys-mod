package net.riley.riley_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.ParasiteCarrierEntity;
import org.jetbrains.annotations.NotNull;

public class ParasiteCarrierEggItem extends Item {

    public ParasiteCarrierEggItem(Properties properties) {
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
        EntityType<ParasiteCarrierEntity> type = (EntityType<ParasiteCarrierEntity>) RileyModEntities.PARASITE_CARRIER.get();
        ParasiteCarrierEntity mecha = type.create(level);
        if (mecha != null) {
            mecha.moveTo(x, y, z, player != null ? player.getYRot() : 0.0F, 0.0F);
            mecha.setAge(-24000);

            if (player != null) {
                mecha.setTame(true);
                mecha.setOwnerUUID(player.getUUID());
            }

            // Add this: play activation animation only for egg-spawned rex
            mecha.startActivation();

            level.addFreshEntity(mecha);

            if (player != null && !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
    }
}
