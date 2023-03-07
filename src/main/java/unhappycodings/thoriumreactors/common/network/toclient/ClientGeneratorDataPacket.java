package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientGeneratorDataPacket implements IPacket {
    private final BlockPos pos;
    private final int currentProduction;
    private final int energy;
    private final int maxFuel;
    private final int fuel;
    private final boolean powerable;
    private final int redstoneMode;

    public ClientGeneratorDataPacket(BlockPos pos, int currentProduction, int energy, int fuel, int maxFuel, boolean powerable, int redstoneMode) {
        this.pos = pos;
        this.currentProduction = currentProduction;
        this.energy = energy;
        this.fuel = fuel;
        this.maxFuel = maxFuel;
        this.powerable = powerable;
        this.redstoneMode = redstoneMode;
    }

    public static ClientGeneratorDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientGeneratorDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readBoolean(), buffer.readInt());
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
        blockEntity.setRedstoneMode(redstoneMode);
        blockEntity.setPowerable(powerable);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(currentProduction);
        buffer.writeInt(energy);
        buffer.writeInt(fuel);
        buffer.writeInt(maxFuel);
        buffer.writeBoolean(powerable);
        buffer.writeInt(redstoneMode);
    }
}
