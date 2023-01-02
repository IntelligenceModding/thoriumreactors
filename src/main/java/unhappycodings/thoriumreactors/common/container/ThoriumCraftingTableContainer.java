package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.*;
import unhappycodings.thoriumreactors.common.container.util.ContainerCapability;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;

import java.util.Objects;

public class ThoriumCraftingTableContainer extends BaseContainer {
    private static final int MAX = ThoriumCraftingRecipe.MAX_WIDTH * ThoriumCraftingRecipe.MAX_HEIGHT;
    public static SlotItemHandler inputSlot;

    public ThoriumCraftingTableContainer(int id, Inventory inventory, BlockPos pos, Level level, int containerSize) {
        super(ContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), id, inventory, pos, level, containerSize);
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

    public static <T extends ThoriumCraftingTableContainer> MenuType<ThoriumCraftingTableContainer> createContainerType() {
        MenuType<ThoriumCraftingTableContainer> containerType = IForgeMenuType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            BlockEntity te = inv.player.getCommandSenderWorld().getBlockEntity(pos);
            if (te == null) {
                throw new IllegalStateException("Something went wrong getting the GUI");
            } else {
                return (ThoriumCraftingTableContainer) te.getCapability(ContainerCapability.CONTAINER_PROVIDER_CAPABILITY).map((h) -> {
                    return (ThoriumCraftingTableContainer) Objects.requireNonNull(h.createMenu(windowId, inv, inv.player));
                }).orElseThrow(RuntimeException::new);
            }
        });
        return containerType;
    }

    public ThoriumCraftingTableBlockEntity getTile() {
        return (ThoriumCraftingTableBlockEntity) this.tileEntity;
    }

    public SlotItemHandler getInputSlot() {
        return inputSlot;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
