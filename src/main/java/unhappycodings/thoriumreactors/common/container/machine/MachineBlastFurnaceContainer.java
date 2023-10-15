package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientBlastFurnaceDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineBlastFurnaceContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineBlastFurnaceContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.BLAST_FURNACE_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 112);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 12, 45));
                addSlot(new SlotItemHandler(handler, 1, 38, 45));
                addSlot(new SlotItemHandler(handler, 2, 113, 45));
                addSlot(new SlotItemHandler(handler, 3, 131, 45));
                addSlot(new SlotItemHandler(handler, 4, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineBlastFurnaceBlockEntity entity = (MachineBlastFurnaceBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientBlastFurnaceDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.isPowerable(), entity.getRedstoneMode(), entity.getDegree(), entity.getMaxFuel(), entity.getFuel()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineBlastFurnaceBlockEntity getTile() {
        return (MachineBlastFurnaceBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
