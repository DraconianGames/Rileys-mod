package net.riley.riley_mod.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.riley.riley_mod.RileyMod;

import java.rmi.registry.Registry;

public class RileyModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RileyMod.MODID);

public static final RegistryObject<SoundEvent> FROST_HOPPER_BATTLE_CRY = registerSoundEvents("frost_hopper_battle_cry");







    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(RileyMod.MODID, name)));

    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
