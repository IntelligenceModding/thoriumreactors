package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineCrystallizerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.container.base.slot.OutputSlot;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientCrystallizerDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineCrystallizerContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineCrystallizerContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.CRYSTALLIZER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 70));
                addSlot(new OutputSlot(handler, 1, 120, 39));
                addSlot(new SlotItemHandler(handler, 2, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineCrystallizerBlockEntity entity = (MachineCrystallizerBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientCrystallizerDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountIn(), entity.getFluidIn().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    @Override
    public MachineCrystallizerBlockEntity getTile() {
        return (MachineCrystallizerBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
