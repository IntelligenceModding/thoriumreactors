package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class TurbineFlowPacket implements IPacket {
    private final BlockPos pos;
    private final byte flow;

    public TurbineFlowPacket(BlockPos pos, byte flow) {
        this.pos = pos;
        this.flow = flow;
    }

    public static TurbineFlowPacket decode(FriendlyByteBuf buffer) {
        return new TurbineFlowPacket(buffer.readBlockPos(), buffer.readByte());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof TurbineControllerBlockEntity blockEntity)) return;
        float finalValue = blockEntity.getTargetFlowrate() + flow;
        if (finalValue >= 0 && finalValue <= 2500) {
            blockEntity.setTargetFlowrate(finalValue);
            blockEntity.setChanged();
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(flow);
    }
}
