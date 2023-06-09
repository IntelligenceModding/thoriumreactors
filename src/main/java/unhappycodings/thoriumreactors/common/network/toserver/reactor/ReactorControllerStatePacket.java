package unhappycodings.thoriumreactors.common.network.toserver.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ReactorControllerStatePacket implements IPacket {
    BlockPos pos;
    ReactorStateEnum state;

    public ReactorControllerStatePacket(BlockPos pos, ReactorStateEnum state) {
        this.pos = pos;
        this.state = state;
    }

    public static ReactorControllerStatePacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerStatePacket(buffer.readBlockPos(), buffer.readEnum(ReactorStateEnum.class));
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        if (level.getBlockEntity(pos) instanceof ReactorControllerBlockEntity entity) {
            entity.setReactorState(state);
            if (state == ReactorStateEnum.STARTING && entity.getReactorRunningSince() == 0) {
                entity.setReactorRunningSince(0);
            } else if (state == ReactorStateEnum.STOP){
                entity.setReactorRunningSince(-1);
            }
        }
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeEnum(state);
    }
}
