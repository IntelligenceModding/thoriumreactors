package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerScramPacket implements IPacket {
    private final BlockPos pos;

    public ReactorControllerScramPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ReactorControllerScramPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerScramPacket(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        if (!blockEntity.isScrammed())
            blockEntity.scram("Manual Scram!");
        else
            blockEntity.setScrammed(!blockEntity.isScrammed());
        blockEntity.setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
