package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientBlastFurnaceDataPacket implements IPacket {
    private final BlockPos pos;
    private final int energy;
    private final int maxRecipeTime;
    private final int recipeTime;
    private final int redstoneMode;
    private final boolean powerable;
    private final int degree;
    private final int maxFuel;
    private final int fuel;

    public ClientBlastFurnaceDataPacket(BlockPos pos, int energy, int maxRecipeTime, int recipeTime, boolean powerable, int redstoneMode, int degree, int maxFuel, int fuel) {
        this.pos = pos;
        this.energy = energy;
        this.maxRecipeTime = maxRecipeTime;
        this.recipeTime = recipeTime;
        this.powerable = powerable;
        this.redstoneMode = redstoneMode;
        this.degree = degree;
        this.fuel = fuel;
        this.maxFuel = maxFuel;
    }

    public static ClientBlastFurnaceDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientBlastFurnaceDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readBoolean(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineBlastFurnaceBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
        blockEntity.setMaxRecipeTime(maxRecipeTime);
        blockEntity.setRecipeTime(recipeTime);
        blockEntity.setPowerable(powerable);
        blockEntity.setRedstoneMode(redstoneMode);
        blockEntity.setDegree(degree);
        blockEntity.setMaxFuel(maxFuel);
        blockEntity.setFuel(fuel);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
        buffer.writeInt(maxRecipeTime);
        buffer.writeInt(recipeTime);
        buffer.writeBoolean(powerable);
        buffer.writeInt(redstoneMode);
        buffer.writeInt(degree);
        buffer.writeInt(maxFuel);
        buffer.writeInt(fuel);
    }
}
