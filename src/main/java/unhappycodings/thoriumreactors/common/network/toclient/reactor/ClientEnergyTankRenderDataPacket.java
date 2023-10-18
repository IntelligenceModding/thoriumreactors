package unhappycodings.thoriumreactors.common.network.toclient.reactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.tank.EnergyTankBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientEnergyTankRenderDataPacket implements IPacket {
    private final BlockPos pos;
    private int energy;

    public ClientEnergyTankRenderDataPacket(BlockPos pos, int energy) {
        this.pos = pos;
        this.energy = energy;
    }

    public static ClientEnergyTankRenderDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientEnergyTankRenderDataPacket(buffer.readBlockPos(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof EnergyTankBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
    }
}
