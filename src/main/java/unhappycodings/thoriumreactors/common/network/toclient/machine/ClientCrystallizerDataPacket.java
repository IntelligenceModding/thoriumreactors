package unhappycodings.thoriumreactors.common.network.toclient.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineCrystallizerBlockEntity;
import unhappycodings.thoriumreactors.common.network.base.IPacket;

public class ClientCrystallizerDataPacket implements IPacket {
    private final BlockPos pos;
    private final int energy;
    private final int maxRecipeTime;
    private final int recipeTime;
    private final int fluidIn;
    private final boolean powerable;
    private final int redstoneMode;
    private final String fluidTypeIn;

    public ClientCrystallizerDataPacket(BlockPos pos, int energy, int maxRecipeTime, int recipeTime, int fluidIn, String fluidTypeIn, boolean powerable, int redstoneMode) {
        this.pos = pos;
        this.energy = energy;
        this.maxRecipeTime = maxRecipeTime;
        this.recipeTime = recipeTime;
        this.fluidIn = fluidIn;
        this.powerable = powerable;
        this.redstoneMode = redstoneMode;
        this.fluidTypeIn = fluidTypeIn;
    }

    public static ClientCrystallizerDataPacket decode(FriendlyByteBuf buffer) {
        return new ClientCrystallizerDataPacket(buffer.readBlockPos(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readUtf(), buffer.readBoolean(), buffer.readInt());
    }

    @SuppressWarnings("ConstantConditions")
    public void handle(NetworkEvent.Context context) {
        LocalPlayer player = Minecraft.getInstance().player;
        BlockEntity machine = player.level.getBlockEntity(pos);
        if (!(machine instanceof MachineCrystallizerBlockEntity blockEntity)) return;
        blockEntity.setEnergy(energy);
        blockEntity.setMaxRecipeTime(maxRecipeTime);
        blockEntity.setRecipeTime(recipeTime);
        blockEntity.setFluidIn(new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidTypeIn)), fluidIn));
        blockEntity.setPowerable(powerable);
        blockEntity.setRedstoneMode(redstoneMode);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeInt(energy);
        buffer.writeInt(maxRecipeTime);
        buffer.writeInt(recipeTime);
        buffer.writeInt(fluidIn);
        buffer.writeUtf(fluidTypeIn);
        buffer.writeBoolean(powerable);
        buffer.writeInt(redstoneMode);
    }
}
