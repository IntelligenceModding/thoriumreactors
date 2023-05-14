package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerLoadPacket implements IPacket {
    private final BlockPos pos;
    private final byte load;

    public ReactorControllerLoadPacket(BlockPos pos, byte load) {
        this.pos = pos;
        this.load = load;
    }

    public static ReactorControllerLoadPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerLoadPacket(buffer.readBlockPos(), buffer.readByte());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        if (load < 0 || load > 100) return;
        blockEntity.setReactorTargetLoadSet(load);
        blockEntity.setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(load);
    }
}
