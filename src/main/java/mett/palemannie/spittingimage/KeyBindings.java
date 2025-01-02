package mett.palemannie.spittingimage;

import com.mojang.blaze3d.platform.InputConstants;
import mett.palemannie.spittingimage.network.SpitC2SPacket;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final Lazy<KeyMapping> SPITTING_KEY = Lazy.of(() -> new KeyMapping(
            "key.spittingimage.spitting",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_COMMA,
            "key.spittingimage.category.spit"
    ));

    public static void onClientTick(ClientTickEvent.Post event) {
        while (SPITTING_KEY.get().consumeClick()) {

            PacketDistributor.sendToServer(new SpitC2SPacket());

        }
    }

    public static void register() {
        IEventBus eventBus = NeoForge.EVENT_BUS;
        eventBus.addListener(KeyBindings::onClientTick);
    }


    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SPITTING_KEY.get());
    }

}
