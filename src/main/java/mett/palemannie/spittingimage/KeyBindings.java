package mett.palemannie.spittingimage;

import com.mojang.blaze3d.platform.InputConstants;
import mett.palemannie.spittingimage.network.SpitC2SPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeyBindings {

    private static final Map<UUID, Long> cooldownMap = new HashMap<>();
    private static final long COOLDOWN_TIME = 150;

    public static final Lazy<KeyMapping> SPITTING_KEY = Lazy.of(() -> new KeyMapping(
            "key.spittingimage.spitting",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_COMMA,
            "key.spittingimage.category.spit"
    ));

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        LivingEntity player = minecraft.player;
        if (player.isSpectator()) return;

        while (SPITTING_KEY.get().consumeClick()) {
            UUID playerId = player.getUUID();
            long currentTime = System.currentTimeMillis();

            if (!cooldownMap.containsKey(playerId) || (currentTime - cooldownMap.get(playerId) >= COOLDOWN_TIME)) {
                PacketDistributor.sendToServer(new SpitC2SPacket());
                cooldownMap.put(playerId, currentTime);

            }
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
