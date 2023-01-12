package unhappycodings.thoriumreactors.common.container.base.slot;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.util.CraftingUtil;

public class OutputSlot extends BaseSlot {
    public static final int MAX = CraftingUtil.MAX;

    public OutputSlot(BlockEntity entity, ThoriumCraftingTableContainer container, IItemHandler itemHandler, Inventory inventory, int index, int xPosition, int yPosition) {
        super(entity, container, itemHandler, inventory, index, xPosition, yPosition, (stack) -> false);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
        super.onTake(pPlayer, pStack);
        doCraft();
    }

    public void doCraft() {
        for (int i = 1; i < MAX; i++)
            container.getSlot(i + 35).getItem().shrink(1);
        CraftingUtil.refreshTable((ThoriumCraftingTableBlockEntity) entity);
    }

}
