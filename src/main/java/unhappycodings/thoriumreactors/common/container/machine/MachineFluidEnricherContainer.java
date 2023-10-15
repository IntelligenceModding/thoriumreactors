package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineFluidEnricherBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientFluidEnricherDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineFluidEnricherContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineFluidEnricherContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.FLUID_ENRICHER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 20));
                addSlot(new SlotItemHandler(handler, 1, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineFluidEnricherBlockEntity entity = (MachineFluidEnricherBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientFluidEnricherDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountIn(), entity.getFluidAmountOut(), entity.getFluidIn().getFluid().getFluidType().toString(), entity.getFluidOut().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    @Override
    public MachineFluidEnricherBlockEntity getTile() {
        return (MachineFluidEnricherBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
