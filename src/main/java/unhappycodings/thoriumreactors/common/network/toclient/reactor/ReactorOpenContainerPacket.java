package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorOpenContainerPacket implements IPacket {
    private final BlockPos pos;

    public ReactorOpenContainerPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static ReactorOpenContainerPacket decode(FriendlyByteBuf buffer) {
        return new ReactorOpenContainerPacket(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        BlockEntity machine = player.level().getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;

        MenuProvider namedContainerProvider = blockEntity.getBlockState().getBlock().getMenuProvider(blockEntity.getBlockState(), blockEntity.getLevel(), pos);
        if (namedContainerProvider != null) {
            NetworkHooks.openScreen(player, namedContainerProvider, pos);
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
