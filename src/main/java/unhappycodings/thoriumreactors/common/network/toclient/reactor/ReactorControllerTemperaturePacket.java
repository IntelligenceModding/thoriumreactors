package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.base.IPacket;
import unhappycodings.thoriumreactors.common.network.toclient.ClientReactorControllerDataPacket;

public class ReactorControllerTemperaturePacket implements IPacket {
    private final BlockPos pos;
    private final short temperature;

    public ReactorControllerTemperaturePacket(BlockPos pos, short temperature) {
        this.pos = pos;
        this.temperature = temperature;
    }

    public static ReactorControllerTemperaturePacket decode(FriendlyByteBuf buffer) {
        return new ReactorControllerTemperaturePacket(buffer.readBlockPos(), buffer.readShort());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        ServerPlayer player = context.getSender();
        Level level = player.getCommandSenderWorld();
        BlockEntity machine = level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;
        blockEntity.setReactorTargetTemperature(temperature);
        blockEntity.setChanged();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeShort(temperature);
    }
}
