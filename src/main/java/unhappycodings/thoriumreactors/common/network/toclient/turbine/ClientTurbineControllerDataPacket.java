package unhappycodings.thoriumreactors.common.network.toclient.turbine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientTurbineControllerDataPacket implements IPacket {
    private final BlockPos pos;
    public final boolean coilsEngaged;
    public final boolean activated;
    public final float flowrate;
    public final float currentFlowrate;
    public final float rpm;
    public final long turbinetime;

    public ClientTurbineControllerDataPacket(BlockPos pos, boolean coilsEngaged, boolean activated, float flowrate, float currentFlowrate, float rpm, long turbinetime) {
        this.coilsEngaged = coilsEngaged;
        this.activated = activated;
        this.pos = pos;
        this.flowrate = flowrate;
        this.currentFlowrate = currentFlowrate;
        this.rpm = rpm;
        this.turbinetime = turbinetime;
    }

    public static ClientTurbineControllerDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientTurbineControllerDataPacket(buffer.readBlockPos(), buffer.readBoolean(), buffer.readBoolean(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readLong());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level().getBlockEntity(pos);
        if (!(machine instanceof TurbineControllerBlockEntity blockEntity)) return;
        blockEntity.setCoilsEngaged(coilsEngaged);
        blockEntity.setActivated(activated);
        blockEntity.setTargetFlowrate(flowrate);
        blockEntity.setCurrentFlowrate(currentFlowrate);
        blockEntity.setRpm(rpm);
        blockEntity.setTurbinetime(turbinetime);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(coilsEngaged);
        buffer.writeBoolean(activated);
        buffer.writeFloat(flowrate);
        buffer.writeFloat(currentFlowrate);
        buffer.writeFloat(rpm);
        buffer.writeLong(turbinetime);
    }
}
