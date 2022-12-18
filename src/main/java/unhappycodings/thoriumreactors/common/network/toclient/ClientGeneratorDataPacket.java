package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientGeneratorDataPacket implements IPacket {
    private final BlockPos pos;
    private final float currentProduction;
    private final float energy;
    private final float maxFuel;
    private final float fuel;

    public ClientGeneratorDataPacket(BlockPos pos, float currentProduction, float energy, float fuel, float maxFuel) {
        this.pos = pos;
        this.currentProduction = currentProduction;
        this.energy = energy;
        this.fuel = fuel;
        this.maxFuel = maxFuel;
    }

    public static ClientGeneratorDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientGeneratorDataPacket(buffer.readBlockPos(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineGeneratorBlockEntity blockEntity)) return;
        blockEntity.setCurrentProduction(currentProduction);
        blockEntity.setEnergy(energy);
        blockEntity.setFuel(fuel);
        blockEntity.setMaxFuel(maxFuel);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeFloat(currentProduction);
        buffer.writeFloat(energy);
        buffer.writeFloat(fuel);
        buffer.writeFloat(maxFuel);
    }
}
