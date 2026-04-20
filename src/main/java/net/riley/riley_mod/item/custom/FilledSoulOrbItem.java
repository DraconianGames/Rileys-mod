package net.riley.riley_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.riley.riley_mod.item.RileyModItems;

import javax.annotation.Nullable;
import java.util.List;

public class FilledSoulOrbItem extends Item {
    public FilledSoulOrbItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains(SoulOrbItem.TAG_ENTITY_DATA, Tag.TAG_COMPOUND)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (context.getPlayer() == null) {
            return InteractionResult.PASS;
        }

        CompoundTag entityData = tag.getCompound(SoulOrbItem.TAG_ENTITY_DATA);
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

        Entity entity = EntityType.loadEntityRecursive(entityData, level, loaded -> {
            loaded.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, context.getPlayer().getYRot(), 0.0F);
            return loaded;
        });

        if (entity == null) {
            return InteractionResult.FAIL;
        }

        if (entity instanceof LivingEntity living) {
            living.setHealth(living.getMaxHealth());
        }

        level.addFreshEntity(entity);

        ItemStack emptyOrb = new ItemStack(RileyModItems.SOUL_ORB.get());


        stack.shrink(1);
        if (stack.isEmpty()) {
            context.getPlayer().setItemInHand(context.getHand(), emptyOrb);
        } else {
            context.getPlayer().setItemInHand(context.getHand(), stack);
            if (!context.getPlayer().getInventory().add(emptyOrb)) {
                context.getPlayer().drop(emptyOrb, false);
            }
        }


        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(SoulOrbItem.TAG_ENTITY_NAME)) {
            tooltip.add(Component.literal("Contains: " + tag.getString(SoulOrbItem.TAG_ENTITY_NAME)));
        } else {
            tooltip.add(Component.literal("Contains: Unknown Mob"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}