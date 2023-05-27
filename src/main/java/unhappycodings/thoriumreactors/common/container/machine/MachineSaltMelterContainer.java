package unhappycodings.thoriumreactors.common.container.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.machine.ClientSaltMelterDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class MachineSaltMelterContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineSaltMelterContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.SALT_MELTER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 116);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 39, 70));
                addSlot(new SlotItemHandler(handler, 1, 17, 45));
                addSlot(new SlotItemHandler(handler, 2, 39, 20));
                addSlot(new SlotItemHandler(handler, 3, 150, 70));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineSaltMelterBlockEntity entity = (MachineSaltMelterBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientSaltMelterDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getFluidAmountOut(), entity.getFluidOut().getFluid().getFluidType().toString(), entity.isPowerable(), entity.getRedstoneMode(), entity.getDegree()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    @Override
    public MachineSaltMelterBlockEntity getTile() {
        return (MachineSaltMelterBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
