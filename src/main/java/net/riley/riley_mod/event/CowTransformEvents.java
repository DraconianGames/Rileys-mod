package net.riley.riley_mod.event;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.entity.RileyModEntities;
import net.riley.riley_mod.entity.custom.BisonEntity;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CowTransformEvents {

    @SubscribeEvent
    public static void onInteractWithCow(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Cow cow)) {
            return;
        }

        if (!event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).is(Items.APPLE)) {
            return;
        }

        if (cow.level().isClientSide) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }

        if (!event.getEntity().getAbilities().instabuild) {
            event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
        }

        BisonEntity bison = RileyModEntities.BISON.get().create(cow.level());
        if (bison != null) {
            bison.moveTo(cow.getX(), cow.getY(), cow.getZ(), cow.getYRot(), cow.getXRot());
            bison.setHealth(cow.getHealth());
            bison.setBaby(cow.isBaby());

            cow.discard();
            cow.level().addFreshEntity(bison);
        }

        event.setCancellationResult(InteractionResult.CONSUME);
        event.setCanceled(true);
    }
}