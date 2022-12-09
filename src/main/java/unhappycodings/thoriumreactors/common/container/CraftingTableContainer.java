package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.blockentity.CraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseContainer;
import unhappycodings.thoriumreactors.common.container.base.SlotCondition;
import unhappycodings.thoriumreactors.common.container.base.SlotInputHandler;
import unhappycodings.thoriumreactors.common.recipe.ModRecipes;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;

import java.util.Optional;

public class CraftingTableContainer extends BaseContainer {
    public static SlotItemHandler inputSlot;

    public CraftingTableContainer(int id, Inventory inventory, BlockPos pos, Level level) {
        super(ContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), id, inventory, pos, level);
        layoutPlayerInventorySlots(8, 120);
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                byte[] columnNumbers = {17, 35, 53, 71, 89};
                byte[] rowNumbers = {13, 31, 49, 67, 85};
                byte index = 0;
                for (byte column : columnNumbers) {
                    for (byte row : rowNumbers) {
                        addSlot(new SlotInputHandler(handler, index, row, column, new SlotCondition())); //Output
                        index++;
                    }
                }
                addSlot(new SlotInputHandler(handler, index, 143, 53, new SlotCondition().setNeededItem(Items.AIR))); //Output
            });
        }
    }

    @Override
    public void clicked(int slot, int button, @NotNull ClickType type, @NotNull Player player) {
        super.clicked(slot, button, type, player);

        if (type != ClickType.PICKUP_ALL)
            refreshTable(slot);
    }

    public void refreshTable(int slot) {
        CraftingTableBlockEntity entity = this.getTile();
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());

        for (int i = 0; i < 25; i++) inventory.setItem(i, entity.getItem(i));
        Optional<ThoriumCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.THORIUM_RECIPE_TYPE.get(), inventory, level);

        refreshOutput(slot - 35, inventory, recipe);
    }

    public void refreshOutput(int slot, SimpleContainer inventory, Optional<ThoriumCraftingRecipe> recipe) {
        CraftingTableBlockEntity entity = this.getTile();
        if (slot <= 25) {
            if (recipe.isPresent()) {
                if (recipe.get().matches(inventory, entity.getLevel())) {
                    entity.setItem(25, new ItemStack(recipe.get().getResultItem().getItem(), 1));
                }
            } else {
                entity.setItem(25, Items.AIR.getDefaultInstance());
            }
        } else if (slot == 26) {
            if (recipe.isPresent()) {
                for (int i = 0; i < 25; i++) entity.getItem(i).shrink(1);
                refreshTable(slot);
            }
        }
    }

    public CraftingTableBlockEntity getTile() {
        return this.tileEntity;
    }

    public SlotItemHandler getInputSlot() {
        return inputSlot;
    }

    @Override
    public boolean stillValid(@NotNull Player playerIn) {
        return true;
    }
}
