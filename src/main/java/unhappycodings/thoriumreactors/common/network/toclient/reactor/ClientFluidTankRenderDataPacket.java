package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientFluidTankRenderDataPacket implements IPacket {
    private final BlockPos pos;
    private FluidStack fluidIn;

    public ClientFluidTankRenderDataPacket(BlockPos pos, FluidStack fluidIn) {
        this.pos = pos;
        this.fluidIn = fluidIn;
    }

    public static ClientFluidTankRenderDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientFluidTankRenderDataPacket(buffer.readBlockPos(), buffer.readFluidStack());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof FluidTankBlockEntity blockEntity)) return;
        blockEntity.setFluidIn(fluidIn);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeFluidStack(fluidIn);
    }
}
