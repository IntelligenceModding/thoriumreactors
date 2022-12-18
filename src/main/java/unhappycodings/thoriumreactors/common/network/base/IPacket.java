package unhappycodings.thoriumreactors.common.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// CREDIT GOES TO: Sr_endi  | https://github.com/Seniorendi
public interface IPacket {

    static <MSG extends IPacket> void handle(MSG message, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> message.handle(ctx));
        ctx.setPacketHandled(true);
    }

    void handle(NetworkEvent.Context context);

    void encode(FriendlyByteBuf buffer);

}
