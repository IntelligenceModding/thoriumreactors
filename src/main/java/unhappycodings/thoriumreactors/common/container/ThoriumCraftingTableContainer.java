package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.container.base.slot.InputSlot;
import unhappycodings.thoriumreactors.common.container.base.slot.OutputSlot;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;

public class ThoriumCraftingTableContainer extends BaseContainer {

    public ThoriumCraftingTableContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ModContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), id, inventory, pos, level, containerSize);
        layoutPlayerInventorySlots(8, 127);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                byte[] columnNumbers = {20, 38, 56, 74, 92};
                byte[] rowNumbers = {13, 31, 49, 67, 85};
                byte index = 0;
                for (byte column : columnNumbers) {
                    for (byte row : rowNumbers) {
                        addSlot(new InputSlot(tileEntity, this, handler, inventory, index, row, column, (stack) -> true)); //Output
                        index++;
                    }
                }
                addSlot(new OutputSlot(tileEntity, this, handler, inventory, index, 143, 56)); //Output
            });
        }
    }

    public ThoriumCraftingTableBlockEntity getTile() {
        return (ThoriumCraftingTableBlockEntity) this.tileEntity;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
