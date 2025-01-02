package mett.palemannie.spittingimage.network;

import mett.palemannie.spittingimage.SpittingImage;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessages {
    private ModMessages(){}

    public static void registerMessages(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar(SpittingImage.MODID).versioned("1.0");
        registrar.playToServer(SpitC2SPacket.TYPE, SpitC2SPacket.STREAM_CODEC, SpitC2SPacket::handle);

    }
}
