package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerTurbineLoadPacket implements IPacket {
    private final BlockPos pos;
    private final byte load;

    public ReactorControllerTurbineLoadPacket(BlockPos pos, byte load) {
        this.pos = pos;
        this.load = load;
    }

    public static ReactorControllerTurbineLoadPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerTurbineLoadPacket(buffer.readBlockPos(), buffer.readByte());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        byte finalValue = (byte) (blockEntity.getTurbineTargetLoadSet() + load);
        if (finalValue >= 0 && finalValue <= 100) {
            blockEntity.setTurbineTargetLoadSet(finalValue);
            blockEntity.setChanged();
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(load);
    }
}
