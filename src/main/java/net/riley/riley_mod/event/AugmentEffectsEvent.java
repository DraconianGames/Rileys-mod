package net.riley.riley_mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.util.AugmentData;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = RileyMod.MODID)
public final class AugmentEffectsEvent {

    private static final ResourceLocation REINFORCED_MUSCULATURE =
            ResourceLocation.fromNamespaceAndPath(RileyMod.MODID, "reinforced_musculature");

    private static final UUID REINFORCED_MUSCULATURE_ATK_UUID =
            UUID.fromString("11111111-2222-3333-4444-555555555555");

    private AugmentEffectsEvent() {}

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        boolean active = AugmentData.isActive(player, REINFORCED_MUSCULATURE);
        int level = AugmentData.getLevel(player, REINFORCED_MUSCULATURE); // 0 if not crafted

        AttributeInstance atk = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (atk == null) return;

        if (!active || level <= 0) {
            if (atk.getModifier(REINFORCED_MUSCULATURE_ATK_UUID) != null) {
                atk.removeModifier(REINFORCED_MUSCULATURE_ATK_UUID);
            }
            return;
        }

        // Linear scaling:
        // level 1 => +100% (2x)
        // level 2 => +200% (3x)
        // level 3 => +300% (4x)
        double amount = (double) level;

        AttributeModifier existing = atk.getModifier(REINFORCED_MUSCULATURE_ATK_UUID);
        if (existing == null || existing.getAmount() != amount) {
            atk.removeModifier(REINFORCED_MUSCULATURE_ATK_UUID);
            atk.addPermanentModifier(new AttributeModifier(
                    REINFORCED_MUSCULATURE_ATK_UUID,
                    "riley_mod:reinforced_musculature_attack",
                    amount,
                    AttributeModifier.Operation.MULTIPLY_TOTAL
            ));
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        boolean active = AugmentData.isActive(player, REINFORCED_MUSCULATURE);
        int level = AugmentData.getLevel(player, REINFORCED_MUSCULATURE);
        if (!active || level <= 0) return;

        // Multiply the mining speed linearly with the same multiplier as damage.
        // level 1 => 2x speed, level 2 => 3x, etc.
        float multiplier = 1.0f + level;
        event.setNewSpeed(event.getNewSpeed() * multiplier);
    }
}