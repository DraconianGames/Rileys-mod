package net.riley.riley_mod.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.item.RileyModItems;
import net.riley.riley_mod.item.custom.FilledSoulOrbItem;
import net.riley.riley_mod.item.custom.SoulOrbItem;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoulOrbEvents {

    @SubscribeEvent
    public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity targetEntity = event.getTarget();
        if (!(targetEntity instanceof LivingEntity target)) {
            return;
        }
        InteractionHand hand = event.getHand();
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(RileyModItems.SOUL_ORB.get())) {
            if (target instanceof Player) {
                return;
            }

            if (player.level().isClientSide()) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
                return;
            }

            CompoundTag capturedData = new CompoundTag();
            target.save(capturedData);

            CompoundTag orbTag = new CompoundTag();
            orbTag.putString(SoulOrbItem.TAG_ENTITY_ID, EntityType.getKey(target.getType()).toString());
            orbTag.putString(SoulOrbItem.TAG_ENTITY_NAME, target.getDisplayName().getString());
            orbTag.put(SoulOrbItem.TAG_ENTITY_DATA, capturedData);

            ItemStack filledOrb = new ItemStack(RileyModItems.FILLED_SOUL_ORB.get());
            filledOrb.setTag(orbTag);


            stack.shrink(1);
            player.setItemInHand(hand, stack.isEmpty() ? ItemStack.EMPTY : stack);


            if (!player.getInventory().add(filledOrb)) {
                player.drop(filledOrb, false);
            }

            target.discard();

            event.setCancellationResult(InteractionResult.CONSUME);
            event.setCanceled(true);
            return;
        }
    }
}