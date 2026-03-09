package net.riley.riley_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.TrisonCartEntity;

public class TrisonCartItem extends Item {
    public TrisonCartItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos placePos = context.getClickedPos().relative(context.getClickedFace());
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();

        TrisonCartEntity cart = RileyModEntities.TRISON_CART.get().create(level);
        if (cart == null) {
            return InteractionResult.FAIL;
        }

        float yaw = player != null ? player.getYRot() : 0.0F;

        cart.moveTo(
                placePos.getX() + 0.5D,
                placePos.getY(),
                placePos.getZ() + 0.5D,
                yaw,
                0.0F
        );
        cart.setYHeadRot(yaw);
        cart.yHeadRot = yaw;
        cart.yBodyRot = yaw;

        if (!level.noCollision(cart, cart.getBoundingBox())) {
            return InteractionResult.FAIL;
        }

        level.addFreshEntity(cart);

        if (player == null || !player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}