package unhappycodings.thoriumreactors.common.network.toclient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientSaltMelterDataPacket implements IPacket {
    private final BlockPos pos;
    private final int energy;
    private final int maxRecipeTime;
    private final int recipeTime;
    private final int fluidOut;
    private final boolean powerable;
    private final int degree;
    private final int redstoneMode;
    private final String fluidTypeOut;

    public ClientSaltMelterDataPacket(BlockPos pos, int energy, int maxRecipeTime, int recipeTime, int fluidOut, String fluidTypeOut, boolean powerable, int redstoneMode, int degree) {
        this.pos = pos;
        this.energy = energy;
        this.maxRecipeTime = maxRecipeTime;
        this.recipeTime = recipeTime;
        this.fluidOut = fluidOut;
        this.powerable = powerable;
        this.redstoneMode = redstoneMode;
        this.fluidTypeOut = fluidTypeOut;
        this.degree = degree;
    }

    public static ClientSaltMelterDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientSaltMelterDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readUtf(), buffer.readBoolean(), buffer.readInt(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineSaltMelterBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
        blockEntity.setMaxRecipeTime(maxRecipeTime);
        blockEntity.setRecipeTime(recipeTime);
        blockEntity.setFluidOut(new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidTypeOut)), fluidOut));
        blockEntity.setPowerable(powerable);
        blockEntity.setRedstoneMode(redstoneMode);
        blockEntity.setDegree(degree);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
        buffer.writeInt(maxRecipeTime);
        buffer.writeInt(recipeTime);
        buffer.writeInt(fluidOut);
        buffer.writeUtf(fluidTypeOut);
        buffer.writeBoolean(powerable);
        buffer.writeInt(redstoneMode);
        buffer.writeInt(degree);
    }
}
