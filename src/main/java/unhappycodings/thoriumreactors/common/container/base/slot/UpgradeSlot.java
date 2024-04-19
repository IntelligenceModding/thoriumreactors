package unhappycodings.thoriumreactors.common.container.base.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.enums.UpgradeItemType;
import unhappycodings.thoriumreactors.common.item.UpgradeItem;

public class UpgradeSlot extends SlotItemHandler {

    public UpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getItem() instanceof UpgradeItem;
    }

}
