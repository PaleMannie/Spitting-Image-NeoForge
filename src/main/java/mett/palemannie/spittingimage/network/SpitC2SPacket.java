package mett.palemannie.spittingimage.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpitC2SPacket() implements CustomPacketPayload {
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final CustomPacketPayload.Type<SpitC2SPacket> TYPE = new CustomPacketPayload
            .Type<>(ResourceLocation.fromNamespaceAndPath("mymod", "my_data"));

    public static final StreamCodec<ByteBuf, SpitC2SPacket> STREAM_CODEC = ModStreamCodec.solo(SpitC2SPacket::new);

    public static void handle(SpitC2SPacket payload, IPayloadContext context) {
        context.enqueueWork(()->{

            ServerPlayer player = context.player().getServer().getPlayerList().getPlayer(context.player().getUUID());
            ServerPlayHandler.handleSpitting(player);

        });
    }
}
