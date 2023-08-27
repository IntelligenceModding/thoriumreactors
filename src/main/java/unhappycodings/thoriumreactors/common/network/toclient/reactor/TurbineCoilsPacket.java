package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class TurbineCoilsPacket implements IPacket {
    private final BlockPos pos;
    private final boolean engaged;

    public TurbineCoilsPacket(BlockPos pos, boolean engaged) {
        this.pos = pos;
        this.engaged = engaged;
    }

    public static TurbineCoilsPacket decode(FriendlyByteBuf buffer) {
        return new TurbineCoilsPacket(buffer.readBlockPos(), buffer.readBoolean());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof TurbineControllerBlockEntity blockEntity)) return;
        blockEntity.setCoilsEngaged(engaged);
        blockEntity.setChanged();

    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(engaged);
    }
}
