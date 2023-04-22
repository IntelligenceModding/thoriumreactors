package unhappycodings.thoriumreactors.common.container.base.slot;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;

import java.util.function.Predicate;

// CREDIT GOES TO: Sr_endi  | https://github.com/Seniorendi
public class BaseSlot extends SlotItemHandler {
    public static final ResourceLocation GHOST_OVERLAY = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/slot/ghost_overlay.png");

    public final BaseContainer container;
    public final BlockEntity entity;
    public final Inventory inventory;
    public final Predicate<ItemStack> canPlace;
    public boolean isEnabled = true;

    public BaseSlot(BlockEntity entity, BaseContainer container, IItemHandler itemHandler, Inventory inventory, int index, int xPosition, int yPosition, Predicate<ItemStack> canPlace) {
        super(itemHandler, index, xPosition, yPosition);
        this.inventory = inventory;
        this.canPlace = canPlace;
        this.entity = entity;
        this.container = container;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        this.setChanged();
        return canPlace.test(stack);
    }

    public void setEnabled(boolean enable) {
        this.isEnabled = enable;
    }

    @Override
    public boolean isActive() {
        return isEnabled;
    }

    @Override
    public void setChanged() {
        if (inventory != null) inventory.setChanged();
    }

}
