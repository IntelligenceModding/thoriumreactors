package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerTurbineSpeedPacket implements IPacket {
    private final BlockPos pos;
    private final short speed;

    public ReactorControllerTurbineSpeedPacket(BlockPos pos, short speed) {
        this.pos = pos;
        this.speed = speed;
    }

    public static ReactorControllerTurbineSpeedPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerTurbineSpeedPacket(buffer.readBlockPos(), buffer.readShort());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        short finalValue = (short) (blockEntity.getTurbineTargetSpeed() + speed);
        if (finalValue >= 0 && finalValue <= 100) {
            blockEntity.setTurbineTargetSpeed(finalValue);
            blockEntity.setChanged();
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeShort(speed);
    }
}
