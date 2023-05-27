package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineConcentratorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientConcentratorDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineConcentratorContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineConcentratorContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.CONCENTRATOR_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 39));
                addSlot(new SlotItemHandler(handler, 1, 120, 39));
                addSlot(new SlotItemHandler(handler, 2, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineConcentratorBlockEntity entity = (MachineConcentratorBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientConcentratorDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineConcentratorBlockEntity getTile() {
        return (MachineConcentratorBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
