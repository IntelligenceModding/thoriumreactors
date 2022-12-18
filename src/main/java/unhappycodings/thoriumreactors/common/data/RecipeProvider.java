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
        ShapedRecipeBuilder.shaped(ModBlocks.THORIUM_CRAFTING_TABLE.get()).define('A', ModItems.BLASTED_IRON_INGOT.get()).define('B', ModBlocks.HARDENED_STONE.get()).define('C', Items.GOLD_INGOT).pattern("ABA").pattern("BCB").pattern("ABA").unlockedBy("has_blasted_iron_ingot", has(ModItems.BLASTED_IRON_INGOT.get())).save(consumer);
        oreSmelting(consumer, ImmutableList.of(Items.COAL), ModItems.GRAPHITE.get(), 0.1F, 400, "raw_graphite");
        oreSmelting(consumer, ImmutableList.of(ModItems.GRAPHITE.get()), ModItems.GRAPHITE_INGOT.get(), 0.1F, 400, "graphite_ingot");
        oreSmelting(consumer, ImmutableList.of(Items.SMOOTH_STONE), ModBlocks.HARDENED_STONE.get(), 0.5F, 500, "hardened_stone");
        oreSmelting(consumer, ImmutableList.of(ModItems.STEEL_COMPOUND.get()), ModItems.STEEL_INGOT.get(), 0.5F, 500, "steel_ingot");
        oreSmelting(consumer, ImmutableList.of(Items.IRON_INGOT), ModItems.BLASTED_IRON_INGOT.get(), 0.5F, 500, "blasted_iron");
    }
}
