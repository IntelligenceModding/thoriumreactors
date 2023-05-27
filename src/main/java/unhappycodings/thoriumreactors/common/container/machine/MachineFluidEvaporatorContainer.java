package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineFluidEvaporationBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientFluidEvaporatorDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineFluidEvaporatorContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineFluidEvaporatorContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.FLUID_EVAPORATION_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 116);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 70));
                addSlot(new SlotItemHandler(handler, 1, 119, 41));
                addSlot(new SlotItemHandler(handler, 2, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineFluidEvaporationBlockEntity entity = (MachineFluidEvaporationBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientFluidEvaporatorDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountIn(), entity.getFluidIn().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineFluidEvaporationBlockEntity getTile() {
        return (MachineFluidEvaporationBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
