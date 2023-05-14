package unhappycodings.thoriumreactors.common.network.toserver.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerChangedPacket implements IPacket {
    BlockPos pos;

    public ReactorControllerChangedPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ReactorControllerChangedPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerChangedPacket(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        level.getBlockEntity(pos).setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
