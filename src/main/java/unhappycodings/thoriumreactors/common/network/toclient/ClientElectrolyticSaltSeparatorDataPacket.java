package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientElectrolyticSaltSeparatorDataPacket implements IPacket {
    private final BlockPos pos;
    private final int energy;
    private final int maxRecipeTime;
    private final int recipeTime;
    private final int waterIn;
    private final int waterOut;
    private final int redstoneMode;
    private final boolean powerable;

    public ClientElectrolyticSaltSeparatorDataPacket(BlockPos pos, int energy, int maxRecipeTime, int recipeTime, int waterIn, int waterOut, boolean powerable, int redstoneMode) {
        this.pos = pos;
        this.energy = energy;
        this.maxRecipeTime = maxRecipeTime;
        this.recipeTime = recipeTime;
        this.waterIn = waterIn;
        this.waterOut = waterOut;
        this.powerable = powerable;
        this.redstoneMode = redstoneMode;
    }

    public static ClientElectrolyticSaltSeparatorDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientElectrolyticSaltSeparatorDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readBoolean(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineElectrolyticSaltSeparatorBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
        blockEntity.setMaxRecipeTime(maxRecipeTime);
        blockEntity.setRecipeTime(recipeTime);
        blockEntity.setWaterIn(waterIn);
        blockEntity.setWaterOut(waterOut);
        blockEntity.setPowerable(powerable);
        blockEntity.setRedstoneMode(redstoneMode);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
        buffer.writeInt(maxRecipeTime);
        buffer.writeInt(recipeTime);
        buffer.writeInt(waterIn);
        buffer.writeInt(waterOut);
        buffer.writeBoolean(powerable);
        buffer.writeInt(redstoneMode);
    }
}
