package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientReactorRenderDataPacket implements IPacket {
    private final BlockPos pos;

    private final int reactorHeight;
    private FluidStack fluidIn;
    private FluidStack fluidOut;

    public ClientReactorRenderDataPacket(BlockPos pos, int reactorHeight, FluidStack fluidIn, FluidStack fluidOut) {
        this.pos = pos;
        this.reactorHeight = reactorHeight;
        this.fluidIn = fluidIn;
        this.fluidOut = fluidOut;
    }

    public static ClientReactorRenderDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientReactorRenderDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readFluidStack(), buffer.readFluidStack());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof ReactorControllerBlockEntity blockEntity)) return;

        blockEntity.setReactorHeight(reactorHeight);
        blockEntity.setFluidIn(fluidIn);
        blockEntity.setFluidOut(fluidOut);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);

        buffer.writeInt(reactorHeight);
        buffer.writeFluidStack(fluidIn);
        buffer.writeFluidStack(fluidOut);
    }
}
