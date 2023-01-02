package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseContainer;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientElectrolyticSaltSeparatorDataPacket;

public class MachineElectrolyticSaltSeparatorContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineElectrolyticSaltSeparatorContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ContainerTypes.ELECTROLYTIC_SALT_SEPARATOR_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 116);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 38, 70));
                addSlot(new SlotItemHandler(handler, 1, 120, 20));
                addSlot(new SlotItemHandler(handler, 2, 120, 70));
                addSlot(new SlotItemHandler(handler, 3, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineElectrolyticSaltSeparatorBlockEntity entity = (MachineElectrolyticSaltSeparatorBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientElectrolyticSaltSeparatorDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getWaterIn(), entity.getWaterOut()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineElectrolyticSaltSeparatorBlockEntity getTile() {
        return (MachineElectrolyticSaltSeparatorBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
