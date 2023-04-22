package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class MachineClientDumpModePacket implements IPacket {
    private final BlockPos pos;
    private final String tag;
    private final boolean state;

    public MachineClientDumpModePacket(BlockPos pos, String tag, boolean state) {
        this.pos = pos;
        this.tag = tag;
        this.state = state;
    }

    public static MachineClientDumpModePacket decode(FriendlyByteBuf buffer) {
        return new MachineClientDumpModePacket(buffer.readBlockPos(), buffer.readUtf(), buffer.readBoolean());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.getCommandSenderWorld().getBlockEntity(pos);
        if (!(machine instanceof MachineContainerBlockEntity blockEntity)) return;
        if (tag.equals("input")) blockEntity.setInputDump(state);
        if (tag.equals("output")) blockEntity.setOutputDump(state);
        if (tag.equals("dumpInput")) blockEntity.setFluidIn(FluidStack.EMPTY);
        if (tag.equals("dumpOutput")) blockEntity.setFluidOut(FluidStack.EMPTY);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeUtf(tag);
        buffer.writeBoolean(state);
    }
}
