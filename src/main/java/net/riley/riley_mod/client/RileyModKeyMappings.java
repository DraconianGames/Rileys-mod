package net.riley.riley_mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static net.riley.riley_mod.RileyMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RileyModKeyMappings {
    public static final String CATEGORY = "key.categories.riley_mod";

    public static final KeyMapping DOMAIN_EXPANSION = new KeyMapping(
            "key.riley_mod.domain_expansion",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            CATEGORY
    );
    public static final KeyMapping PET_MENU = new KeyMapping(
            "key.riley_mod.pet_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            CATEGORY
    );

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(DOMAIN_EXPANSION);
        event.register(PET_MENU);
    }
}