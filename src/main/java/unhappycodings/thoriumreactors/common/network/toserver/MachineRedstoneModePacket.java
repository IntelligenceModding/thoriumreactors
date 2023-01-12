package unhappycodings.thoriumreactors.common.network.toserver;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class MachineRedstoneModePacket implements IPacket {
    private final BlockPos pos;

    public MachineRedstoneModePacket(BlockPos pos) {
        this.pos = pos;
    }

    public static MachineRedstoneModePacket decode(FriendlyByteBuf buffer) {
        return new MachineRedstoneModePacket(buffer.readBlockPos());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        BlockEntity machine = player.getCommandSenderWorld().getBlockEntity(pos);
        if (!(machine instanceof MachineContainerBlockEntity blockEntity)) return;

        blockEntity.setRedstoneMode(switch (blockEntity.getRedstoneMode()) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 0;
        });

    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
