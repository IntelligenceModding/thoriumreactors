package unhappycodings.thoriumreactors.common.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.THORIUM_CRAFTING_TABLE.get()).define('A', ModItems.BLASTED_IRON_INGOT.get()).define('B', ModBlocks.BLASTED_STONE.get()).define('C', Items.GOLD_INGOT).pattern("ABA").pattern("BCB").pattern("ABA").unlockedBy("has_blasted_iron_ingot", has(ModItems.BLASTED_IRON_INGOT.get())).save(consumer);
    }
}
