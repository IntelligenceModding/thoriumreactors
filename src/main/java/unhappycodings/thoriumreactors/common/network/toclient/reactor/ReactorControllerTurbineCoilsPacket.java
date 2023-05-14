package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerTurbineCoilsPacket implements IPacket {
    private final BlockPos pos;
    private final boolean engaged;

    public ReactorControllerTurbineCoilsPacket(BlockPos pos, boolean engaged) {
        this.pos = pos;
        this.engaged = engaged;
    }

    public static ReactorControllerTurbineCoilsPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerTurbineCoilsPacket(buffer.readBlockPos(), buffer.readBoolean());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        blockEntity.setTurbineCoilsEngaged(engaged);
        blockEntity.setChanged();

    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(engaged);
    }
}
