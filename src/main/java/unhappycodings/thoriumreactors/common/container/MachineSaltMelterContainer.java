package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.ClientSaltMelterDataPacket;

public class MachineSaltMelterContainer extends BaseContainer {
    public final Inventory inventory;

    public MachineSaltMelterContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.SALT_MELTER_CONTAINER.get(), id, inventory, pos, level, containerSize);
        this.inventory = inventory;
        layoutPlayerInventorySlots(8, 116);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                addSlot(new SlotItemHandler(handler, 0, 39, 70));
                addSlot(new SlotItemHandler(handler, 1, 39, 20));
                addSlot(new SlotItemHandler(handler, 2, 150, 70));
                addSlot(new SlotItemHandler(handler, 3, 17, 46));
            });
        }
    }

    @Override
    public void broadcastChanges() {
        MachineSaltMelterBlockEntity entity = (MachineSaltMelterBlockEntity) this.tileEntity;
        PacketHandler.sendToClient(new ClientSaltMelterDataPacket(entity.getBlockPos(), entity.getEnergy(), entity.getMaxRecipeTime(), entity.getRecipeTime(), entity.getMoltenSaltOut(), entity.isPowerable(), entity.getRedstoneMode()), (ServerPlayer) inventory.player);

        super.broadcastChanges();
    }

    public MachineSaltMelterBlockEntity getTile() {
        return (MachineSaltMelterBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
