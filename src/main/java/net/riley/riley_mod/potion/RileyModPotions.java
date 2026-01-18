package net.riley.riley_mod.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;
import net.riley.riley_mod.effect.RileyModEffects;

public class RileyModPotions{
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, RileyMod.MODID);

    public static final RegistryObject<Potion> FREEZE_POTION = POTIONS.register("freeze_potion",
            () -> new Potion(new MobEffectInstance(RileyModEffects.FREEZE.get(), 2000, 0, false, true, true)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}

