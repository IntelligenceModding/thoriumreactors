package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineDecomposerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientDecomposerDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineDecomposerContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineDecomposerContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.DECOMPOSER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 70));
                addSlot(new SlotItemHandler(handler, 1, 38, 20));
                addSlot(new SlotItemHandler(handler, 2, 150, 70));
            });

        }
    }

    @Override
    public void broadcastChanges() {
        MachineDecomposerBlockEntity entity = (MachineDecomposerBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientDecomposerDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountIn(), entity.getFluidAmountOut(), entity.getFluidIn().getFluid().getFluidType().toString(), entity.getFluidOut().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineDecomposerBlockEntity getTile() {
        return (MachineDecomposerBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
