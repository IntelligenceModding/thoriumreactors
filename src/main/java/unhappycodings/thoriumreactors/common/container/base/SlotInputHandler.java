package unhappycodings.thoriumreactors.common.container.base;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

// CREDIT GOES TO: Sr_endi  | https://github.com/Seniorendi
public class SlotInputHandler extends SlotItemHandler {
    SlotCondition condition;

    public SlotInputHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, SlotCondition condition) {
        super(itemHandler, index, xPosition, yPosition);
        this.condition = condition;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return condition.isValid(stack);
    }
}
