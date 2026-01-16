package net.riley.riley_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;

public class RileyModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RileyMod.MODID);

    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 0x34aec7));
    public static final RegistryObject<MobEffect> BLEED = MOB_EFFECTS.register("bleed",
            () -> new BleedEffect(MobEffectCategory.HARMFUL, 0x780c0c));
    public static final RegistryObject<MobEffect> DEAF = MOB_EFFECTS.register( "deaf",
            () -> new DeafEffect(MobEffectCategory.HARMFUL, 0x780c0c));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}