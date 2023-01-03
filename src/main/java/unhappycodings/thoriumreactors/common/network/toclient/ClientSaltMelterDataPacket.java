package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import unhappycodings.thoriumreactors.common.blockentity.MachineFluidEvaporationBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.container.MachineSaltMelterScreen;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientSaltMelterDataPacket implements IPacket {
    private final BlockPos pos;
    private final int energy;
    private final int maxRecipeTime;
    private final int recipeTime;
    private final int moltenSaltOut;

    public ClientSaltMelterDataPacket(BlockPos pos, int energy, int maxRecipeTime, int recipeTime, int moltenSaltOut) {
        this.pos = pos;
        this.energy = energy;
        this.maxRecipeTime = maxRecipeTime;
        this.recipeTime = recipeTime;
        this.moltenSaltOut = moltenSaltOut;
    }

    public static ClientSaltMelterDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientSaltMelterDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineSaltMelterBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
        blockEntity.setMaxRecipeTime(maxRecipeTime);
        blockEntity.setRecipeTime(recipeTime);
        blockEntity.setMoltenSaltOut(moltenSaltOut);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
        buffer.writeInt(maxRecipeTime);
        buffer.writeInt(recipeTime);
        buffer.writeInt(moltenSaltOut);
    }
}
