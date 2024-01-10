package unhappycodings.thoriumreactors.common.container.base.slot;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.util.CraftingUtil;

public class CraftingOutputSlot extends BaseSlot {
    public static final int MAX = CraftingUtil.MAX;
    private final Inventory inventory;
    private final BaseContainer container;

    public CraftingOutputSlot(BlockEntity entity, BaseContainer container, IItemHandler itemHandler, Inventory inventory, int index, int xPosition, int yPosition) {
        super(entity, container, itemHandler, inventory, index, xPosition, yPosition, (stack) -> false);
        this.inventory = inventory;
        this.container = container;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
        super.onTake(pPlayer, pStack);
        if (!getItem().isEmpty()) {
            ItemStack carried = container.getCarried();
            if (carried.getCount() < carried.getMaxStackSize()) {
                int couldRemove = Math.min(carried.getMaxStackSize() - carried.getCount(), getItem().getCount());
                if (carried.is(getItem().getItem())) {
                    carried.grow(couldRemove);
                }
            }
        }
        doCraft();
    }

    public void doCraft() {
        for (int i = 1; i < MAX; i++)
            container.getSlot(i + 35).getItem().shrink(1);

        CraftingUtil.refreshTable((ThoriumCraftingTableBlockEntity) entity);
    }

}
