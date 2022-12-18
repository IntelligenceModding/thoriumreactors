package unhappycodings.thoriumreactors.common.container.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.util.CraftingUtil;

import java.util.function.Predicate;

public class InputSlot extends BaseSlot {

    public InputSlot(BlockEntity entity, ThoriumCraftingTableContainer container, IItemHandler itemHandler, Inventory inventory, int index, int xPosition, int yPosition, Predicate<ItemStack> canPlace) {
        super(entity, container, itemHandler, inventory, index, xPosition, yPosition, canPlace);
    }

    @Override
    public void set(@NotNull ItemStack stack) {
        super.set(stack);
        CraftingUtil.refreshTable((ThoriumCraftingTableBlockEntity) entity);
    }

}
