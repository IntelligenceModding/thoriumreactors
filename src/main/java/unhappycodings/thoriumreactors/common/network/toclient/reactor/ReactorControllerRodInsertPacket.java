package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerRodInsertPacket implements IPacket {
    private final BlockPos pos;
    private final byte rod;
    private final byte insert;
    private final boolean shiftDown;

    public ReactorControllerRodInsertPacket(BlockPos pos, byte insert, byte rod, boolean shiftDown) {
        this.pos = pos;
        this.insert = insert;
        this.rod = rod;
        this.shiftDown = shiftDown;
    }

    public static ReactorControllerRodInsertPacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerRodInsertPacket(buffer.readBlockPos(), buffer.readByte(), buffer.readByte(), buffer.readBoolean());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        if (shiftDown) {
            for (int i = 0; i < 64; i++) {
                blockEntity.setTargetControlRodStatus((byte) i, insert);
            }
        } else {
            blockEntity.setTargetControlRodStatus(rod, insert);
        }
        blockEntity.setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(insert);
        buffer.writeByte(rod);
        buffer.writeBoolean(shiftDown);
    }
}
