package net.riley.riley_mod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;

public class JournalItem extends Item {
    public JournalItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) {
            // We use DistExecutor to ensure client-only code (screens) doesn't crash a server
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> openJournalScreen());
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pUsedHand), pLevel.isClientSide());
    }

    private void openJournalScreen()  {
        // Change this line to open your new custom screen
        Minecraft.getInstance().setScreen(new net.riley.riley_mod.entity.client.JournalScreen());
    }
}