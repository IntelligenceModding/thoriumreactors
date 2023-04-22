package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineFluidCentrifugeBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientFluidCentrifugeDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineFluidCentrifugeContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineFluidCentrifugeContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.FLUID_CENTRIFUGE_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineFluidCentrifugeBlockEntity entity = (MachineFluidCentrifugeBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientFluidCentrifugeDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountIn(), entity.getFluidAmountOut(), entity.getFluidIn().getFluid().getFluidType().toString(), entity.getFluidOut().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    @Override
    public MachineFluidCentrifugeBlockEntity getTile() {
        return (MachineFluidCentrifugeBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
