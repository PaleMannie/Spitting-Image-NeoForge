package mett.palemannie.spittingimage.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public class ModStreamCodec {
    private ModStreamCodec(){}

    public static <A extends ByteBuf, Z> StreamCodec<A, Z> solo(Supplier<Z> inst) {
        return new StreamCodec<A, Z>() {

            @Override
            public Z decode(A dc) {
                return inst.get();

            }

            @Override
            public void encode(A ec, Z ce) {}
        };
    }
}
