package unhappycodings.thoriumreactors.common.network.toserver;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class MachineChangedPacket implements IPacket {
    BlockPos pos;

    public MachineChangedPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static MachineChangedPacket decode(FriendlyByteBuf buffer) {
        return new MachineChangedPacket(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        level.getBlockEntity(pos).setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
