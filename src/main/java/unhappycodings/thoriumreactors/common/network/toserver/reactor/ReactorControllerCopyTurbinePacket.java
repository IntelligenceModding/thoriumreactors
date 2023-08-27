package unhappycodings.thoriumreactors.common.network.toserver.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerCopyTurbinePacket implements IPacket {
    BlockPos pos;
    int turbine;

    public ReactorControllerCopyTurbinePacket(BlockPos pos, int turbine) {
        this.pos = pos;
        this.turbine = turbine;
    }

    public static ReactorControllerCopyTurbinePacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerCopyTurbinePacket(buffer.readBlockPos(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        if (level.getBlockEntity(pos) instanceof ReactorControllerBlockEntity entity) {
            if (level.getBlockEntity(entity.getTurbinePos().get(turbine)) instanceof TurbineControllerBlockEntity copyEntity) {
                float flowrate = copyEntity.getTargetFlowrate();
                boolean engaged = copyEntity.isCoilsEngaged();
                boolean activated = copyEntity.isActivated();
                for (BlockPos turbinePos : entity.getTurbinePos()) {
                    if (level.getBlockEntity(turbinePos) instanceof TurbineControllerBlockEntity targetEntity) {
                        targetEntity.setTargetFlowrate(flowrate);
                        targetEntity.setActivated(activated);
                        targetEntity.setCoilsEngaged(engaged);
                    }
                }
            }
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(turbine);
    }
}
