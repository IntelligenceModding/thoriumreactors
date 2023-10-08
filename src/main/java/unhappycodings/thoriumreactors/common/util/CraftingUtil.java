package unhappycodings.thoriumreactors.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import unhappycodings.thoriumreactors.common.block.chest.SteelChestBlock;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;

import java.util.Optional;

public class CraftingUtil {
    public static final int MAX = ThoriumCraftingRecipe.MAX_WIDTH * ThoriumCraftingRecipe.MAX_HEIGHT;

    public static void refreshTable(ThoriumCraftingTableBlockEntity entity) {
        Level level = entity.getLevel();
        SimpleContainer inventory = new SimpleContainer(entity.getContainerSize());

        for (int i = 0; i < ThoriumCraftingRecipe.MAX_WIDTH * ThoriumCraftingRecipe.MAX_HEIGHT; i++)
            inventory.setItem(i, entity.getItem(i).is(Items.AIR) ? Items.AIR.getDefaultInstance() : entity.getItem(i));
        Optional<ThoriumCraftingRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.THORIUM_RECIPE_TYPE.get(), inventory, level);

        if (recipe.isPresent()) {
            if (recipe.get().matches(inventory, entity.getLevel())) {
                ItemStack result = recipe.get().getResultItem();
                if (result.is(ModBlocks.STEEL_CHEST_BLOCK.get().asItem()) || result.is(ModBlocks.THORIUM_CHEST_BLOCK.get().asItem())) {
                    CompoundTag tag = entity.getItem(17).getOrCreateTag();
                    if (!tag.isEmpty())
                        result.getOrCreateTag().put("BlockEntityTag", tag.getCompound("BlockEntityTag"));
                }
                if (result.is(ModBlocks.GENERIC_FLUID_TANK.get().asItem()) || result.is(ModBlocks.PROGRESSIVE_FLUID_TANK.get().asItem()) || result.is(ModBlocks.GENERIC_ENERGY_TANK.get().asItem()) || result.is(ModBlocks.PROGRESSIVE_ENERGY_TANK.get().asItem())) {
                    CompoundTag tag = entity.getItem(12).getOrCreateTag();
                    if (!tag.isEmpty())
                        result.getOrCreateTag().put("BlockEntityTag", tag.getCompound("BlockEntityTag"));
                }
                entity.setItem(MAX, result);
            }
        } else {
            entity.setItem(MAX, Items.AIR.getDefaultInstance());
        }

    }

}
