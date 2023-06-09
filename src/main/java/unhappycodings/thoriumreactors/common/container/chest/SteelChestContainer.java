package unhappycodings.thoriumreactors.common.container.chest;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.container.base.slot.BaseSlot;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class SteelChestContainer extends BaseContainer {

    public SteelChestContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.STEEL_CHEST_CONTAINER.get(), id, inventory, pos, level, containerSize);
        layoutPlayerInventorySlots(26, 156);

        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                short[] columnNumbers = {17, 35, 53, 71, 89, 107, 125};
                short[] rowNumbers = {8, 26, 44, 62, 80, 98, 116, 134, 152, 170, 188};
                byte index = 0;
                for (short column : columnNumbers) {
                    for (short row : rowNumbers) {
                        addSlot(new BaseSlot(level.getBlockEntity(pos), this, handler, inventory, index, row, column, (x) -> true));
                        index++;
                    }
                }
            });
        }
    }

    public SteelChestBlockEntity getTile() {
        return (SteelChestBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
