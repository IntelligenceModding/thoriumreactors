package unhappycodings.thoriumreactors.common.util;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;

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
            if (recipe.get().matches(inventory, entity.getLevel()))
                if (!entity.getItem(MAX).is(recipe.get().getResultItem().getItem()))
                    entity.setItem(MAX, recipe.get().getResultItem());
        } else {
            entity.setItem(MAX, Items.AIR.getDefaultInstance());
        }

    }

}
