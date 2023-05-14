package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerTurbineOverflowPacket implements IPacket {
    private final BlockPos pos;
    private final byte overflow;

    public ReactorControllerTurbineOverflowPacket(BlockPos pos, byte overflow) {
        this.pos = pos;
        this.overflow = overflow;
    }

    public static ReactorControllerTurbineOverflowPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerTurbineOverflowPacket(buffer.readBlockPos(), buffer.readByte());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        byte finalValue = (byte) (blockEntity.getTurbineTargetOverflowSet() + overflow);
        if (finalValue >= 0 && finalValue <= 9999) {
            blockEntity.setTurbineTargetOverflowSet(finalValue);
            blockEntity.setChanged();
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(overflow);
    }
}
