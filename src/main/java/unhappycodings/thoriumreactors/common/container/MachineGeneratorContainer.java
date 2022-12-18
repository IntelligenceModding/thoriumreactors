package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseContainer;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientGeneratorDataPacket;

public class MachineGeneratorContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineGeneratorContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ContainerTypes.GENERATOR_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 104);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 17, 44)); //Output
                addSlot(new SlotItemHandler(handler, 1, 143, 67)); //Output
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineGeneratorBlockEntity entity = (MachineGeneratorBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientGeneratorDataPacket(entity.getBlockPos(), entity.getCurrentProduction(), entity.getEnergy(), entity.getFuel(), entity.getMaxFuel()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineGeneratorBlockEntity getTile() {
        return (MachineGeneratorBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
