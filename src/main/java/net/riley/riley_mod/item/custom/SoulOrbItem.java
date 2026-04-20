package net.riley.riley_mod.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SoulOrbItem extends Item {
    public static final String TAG_ENTITY_ID = "CapturedEntityId";
    public static final String TAG_ENTITY_NAME = "CapturedEntityName";
    public static final String TAG_ENTITY_DATA = "CapturedEntityData";

    public SoulOrbItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_ENTITY_NAME)) {
            tooltip.add(Component.literal("Contains: " + tag.getString(TAG_ENTITY_NAME)));
        } else {
            tooltip.add(Component.literal("Empty"));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }
}