package net.riley.riley_mod.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.riley.riley_mod.entity.custom.WhaleHunterEntity;

@Mod.EventBusSubscriber(modid = RileyMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        // Check if the killer is a WhaleHunterEntity
        if (event.getSource().getEntity() instanceof WhaleHunterEntity) {
            LivingEntity target = event.getEntity();

            // 1. Check if the target is a Player
            boolean isPlayer = target instanceof Player;

            // 2. Check if the target is a tamed pet (like a Wolf, Cat, or Axolotl)
            boolean isTamedPet = target instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null;

            // Only cancel the loot drops if it's NOT a player and NOT a tamed pet
            if (!isPlayer && !isTamedPet) {
                event.setCanceled(true);
            }
        }
    }
}