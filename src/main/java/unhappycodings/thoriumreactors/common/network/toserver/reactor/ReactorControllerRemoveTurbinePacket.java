package unhappycodings.thoriumreactors.common.network.toserver.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerRemoveTurbinePacket implements IPacket {
    BlockPos pos;
    int turbine;

    public ReactorControllerRemoveTurbinePacket(BlockPos pos, int turbine) {
        this.pos = pos;
        this.turbine = turbine;
    }

    public static ReactorControllerRemoveTurbinePacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerRemoveTurbinePacket(buffer.readBlockPos(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        if (level.getBlockEntity(pos) instanceof ReactorControllerBlockEntity entity) {

            entity.getTurbinePos().remove(entity.getTurbinePos().get(turbine));
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(turbine);
    }
}
