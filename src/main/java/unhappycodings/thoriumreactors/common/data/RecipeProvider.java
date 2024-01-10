package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider {

    public RecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.THORIUM_CRAFTING_TABLE.get()).define('A', ModItems.THORIUM.get()).define('B', Items.IRON_INGOT).define('C', ModItems.NICKEL_NUGGET.get()).define('D', Blocks.CRAFTING_TABLE).pattern("BAB").pattern("CDC").pattern("BAB").unlockedBy("has_blasted_iron_ingot", has(ModItems.BLASTED_IRON_INGOT.get())).save(consumer);

        // Building Blocks
        ShapedRecipeBuilder.shaped( ModBlocks.FACTORY_BLOCK.get(), 16).define('A', Blocks.STONE).define('B', Items.IRON_INGOT).define('C', Items.IRON_NUGGET).pattern(" C ").pattern("BAB").pattern(" C ").unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.INVERTED_FACTORY_BLOCK.get(), 8).define('A', ModBlocks.FACTORY_BLOCK.get()).define('B', Items.GRAY_DYE).pattern("AAA").pattern("ABA").pattern("AAA").unlockedBy("has_gray_dye", has(Items.GRAY_DYE)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_FACTORY_BLOCK.get(), 8).define('A', ModBlocks.FACTORY_BLOCK.get()).define('B', Items.BLACK_DYE).pattern("AAA").pattern("ABA").pattern("AAA").unlockedBy("has_black_dye", has(Items.BLACK_DYE)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get(), 8).define('A', ModBlocks.BLACK_FACTORY_BLOCK.get()).define('B', Items.GRAY_DYE).pattern("AAA").pattern("ABA").pattern("AAA").unlockedBy("has_factory_block", has(ModBlocks.BLACK_FACTORY_BLOCK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get(), 16).define('A', Blocks.STONE).define('B', ModItems.BLASTED_IRON_NUGGET.get()).pattern(" B ").pattern("BAB").pattern(" B ").unlockedBy("has_blasted_iron", has(Blocks.STONE)).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK.get(), 16).define('A', Blocks.SMOOTH_STONE).define('B', ModItems.BLASTED_IRON_NUGGET.get()).pattern(" B ").pattern("BAB").pattern(" B ").unlockedBy("has_blasted_iron", has(ModItems.BLASTED_IRON_NUGGET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get(), 7).define('A', ModBlocks.INDUSTRAL_BLOCK.get()).pattern("A A").pattern("AAA").pattern("A A").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK_BRICK.get(), 7).define('A', ModBlocks.INDUSTRAL_BLOCK.get()).pattern("AAA").pattern(" A ").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK_PAVING.get(), 8).define('A', ModBlocks.INDUSTRAL_BLOCK.get()).pattern("AAA").pattern("A A").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get(), 8).define('A', ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get()).define('B', Items.BLACK_DYE).pattern(" A ").pattern("ABA").pattern(" A ").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK.get(), 8).define('A', ModBlocks.INDUSTRAL_BLOCK.get()).define('B', Items.BLACK_DYE).pattern("AAA").pattern("ABA").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get(), 7).define('A', ModBlocks.BLACK_INDUSTRAL_BLOCK.get()).pattern("A A").pattern("AAA").pattern("A A").unlockedBy("has_industrial_block", has(ModBlocks.BLACK_INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get(), 7).define('A', ModBlocks.BLACK_INDUSTRAL_BLOCK.get()).pattern("AAA").pattern(" A ").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.BLACK_INDUSTRAL_BLOCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get(), 8).define('A', ModBlocks.BLACK_INDUSTRAL_BLOCK.get()).pattern("AAA").pattern("A A").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.BLACK_INDUSTRAL_BLOCK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get(), 16).define('A', Blocks.OBSIDIAN).define('B', ModItems.BLASTED_IRON_NUGGET.get()).pattern(" B ").pattern("BAB").pattern(" B ").unlockedBy("has_blasted_iron", has(ModItems.BLASTED_IRON_NUGGET.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get(), 8).define('A', ModBlocks.INDUSTRAL_BLOCK_FLOOR.get()).define('B', Items.BLACK_DYE).pattern("AAA").pattern("ABA").pattern("AAA").unlockedBy("has_industrial_block", has(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.FRAMELESS_INDUSTRAL_BLOCK_FLOOR.get()).requires(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get()).unlockedBy("has_industrial_block_floor", has(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get())).save(consumer, ItemUtil.getRegString(ModBlocks.FRAMELESS_INDUSTRAL_BLOCK_FLOOR.get()) + "_craft_from_normal");
        ShapelessRecipeBuilder.shapeless(ModBlocks.FRAMELESS_BLACK_INDUSTRAL_BLOCK_FLOOR.get()).requires(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get()).unlockedBy("has_black_industrial_block_floor", has(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get())).save(consumer, ItemUtil.getRegString(ModBlocks.FRAMELESS_BLACK_INDUSTRAL_BLOCK_FLOOR.get()) + "_craft_from_normal");

        // Nugget to Ingot
        ShapelessRecipeBuilder.shapeless(ModItems.BLASTED_IRON_INGOT.get()).requires(ModItems.BLASTED_IRON_NUGGET.get(), 9).unlockedBy("has_blasted_iron_nugget", has(ModItems.BLASTED_IRON_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.BLASTED_IRON_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.STEEL_INGOT.get()).requires(ModItems.STEEL_NUGGET.get(), 9).unlockedBy("has_steel_nugget", has(ModItems.STEEL_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.STEEL_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.MOLYBDENUM_INGOT.get()).requires(ModItems.MOLYBDENUM_NUGGET.get(), 9).unlockedBy("has_molybdenum_nugget", has(ModItems.MOLYBDENUM_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.MOLYBDENUM_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.MANGANESE_INGOT.get()).requires(ModItems.MANGANESE_NUGGET.get(), 9).unlockedBy("has_manganese_nugget", has(ModItems.MANGANESE_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.MANGANESE_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.NICKEL_INGOT.get()).requires(ModItems.NICKEL_NUGGET.get(), 9).unlockedBy("has_nickel_nugget", has(ModItems.NICKEL_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.NICKEL_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.ALUMINUM_INGOT.get()).requires(ModItems.ALUMINUM_NUGGET.get(), 9).unlockedBy("has_aluminum_nugget", has(ModItems.ALUMINUM_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.ALUMINUM_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.CHROMIUM_INGOT.get()).requires(ModItems.CHROMIUM_NUGGET.get(), 9).unlockedBy("has_chromium_nugget", has(ModItems.CHROMIUM_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.CHROMIUM_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.NIOB_INGOT.get()).requires(ModItems.NIOB_NUGGET.get(), 9).unlockedBy("has_niob_nugget", has(ModItems.NIOB_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.NIOB_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.TITANIUM_INGOT.get()).requires(ModItems.TITANIUM_NUGGET.get(), 9).unlockedBy("has_titanium_nugget", has(ModItems.TITANIUM_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.TITANIUM_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.URANIUM_INGOT.get()).requires(ModItems.URANIUM_NUGGET.get(), 9).unlockedBy("has_uranium_nugget", has(ModItems.URANIUM_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.URANIUM_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.FLUORITE_INGOT.get()).requires(ModItems.FLUORITE_NUGGET.get(), 9).unlockedBy("has_fluorite_nugget", has(ModItems.FLUORITE_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.FLUORITE_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.COBALT_INGOT.get()).requires(ModItems.COBALT_NUGGET.get(), 9).unlockedBy("has_cobalt_nugget", has(ModItems.COBALT_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.COBALT_INGOT.get()) + "_craft_from_nugget");
        ShapelessRecipeBuilder.shapeless(ModItems.GRAPHITE_INGOT.get()).requires(ModItems.GRAPHITE_NUGGET.get(), 9).unlockedBy("has_graphite_nugget", has(ModItems.GRAPHITE_NUGGET.get())).save(consumer, ItemUtil.getRegString(ModItems.GRAPHITE_INGOT.get()) + "_craft_from_nugget");

        // Ingot to Nugget
        ShapelessRecipeBuilder.shapeless(ModItems.BLASTED_IRON_NUGGET.get(), 9).requires(ModItems.BLASTED_IRON_INGOT.get()).unlockedBy("has_blasted_iron_ingot", has(ModItems.BLASTED_IRON_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.BLASTED_IRON_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.STEEL_NUGGET.get(), 9).requires(ModItems.STEEL_INGOT.get()).unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.STEEL_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.MOLYBDENUM_NUGGET.get(), 9).requires(ModItems.MOLYBDENUM_INGOT.get()).unlockedBy("has_molybdenum_ingot", has(ModItems.MOLYBDENUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.MOLYBDENUM_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.MANGANESE_NUGGET.get(), 9).requires(ModItems.MANGANESE_INGOT.get()).unlockedBy("has_manganese_ingot", has(ModItems.MANGANESE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.MANGANESE_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.NICKEL_NUGGET.get(), 9).requires(ModItems.NICKEL_INGOT.get()).unlockedBy("has_nickel_ingot", has(ModItems.NICKEL_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.NICKEL_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.ALUMINUM_NUGGET.get(), 9).requires(ModItems.ALUMINUM_INGOT.get()).unlockedBy("has_aluminum_ingot", has(ModItems.ALUMINUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.ALUMINUM_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.CHROMIUM_NUGGET.get(), 9).requires(ModItems.CHROMIUM_INGOT.get()).unlockedBy("has_chromium_ingot", has(ModItems.CHROMIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.CHROMIUM_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.NIOB_NUGGET.get(), 9).requires(ModItems.NIOB_INGOT.get()).unlockedBy("has_niob_ingot", has(ModItems.NIOB_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.NIOB_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.TITANIUM_NUGGET.get(), 9).requires(ModItems.TITANIUM_INGOT.get()).unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.TITANIUM_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.URANIUM_NUGGET.get(), 9).requires(ModItems.URANIUM_INGOT.get()).unlockedBy("has_uranium_ingot", has(ModItems.URANIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.URANIUM_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.FLUORITE_NUGGET.get(), 9).requires(ModItems.FLUORITE_INGOT.get()).unlockedBy("has_fluorite_ingot", has(ModItems.FLUORITE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.FLUORITE_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.COBALT_NUGGET.get(), 9).requires(ModItems.COBALT_INGOT.get()).unlockedBy("has_cobalt_ingot", has(ModItems.COBALT_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.COBALT_NUGGET.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModItems.GRAPHITE_NUGGET.get(), 9).requires(ModItems.GRAPHITE_INGOT.get()).unlockedBy("has_graphite_ingot", has(ModItems.GRAPHITE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModItems.GRAPHITE_NUGGET.get()) + "_craft_from_ingot");

        // Block to Ingot
        ShapelessRecipeBuilder.shapeless(ModItems.BLASTED_IRON_INGOT.get(), 9).requires(ModBlocks.BLASTED_IRON_BLOCK.get()).unlockedBy("has_blasted_iron_block", has(ModBlocks.BLASTED_IRON_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.BLASTED_IRON_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.STEEL_INGOT.get(), 9).requires(ModBlocks.STEEL_BLOCK.get()).unlockedBy("has_steel_block", has(ModBlocks.STEEL_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.STEEL_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.MOLYBDENUM_INGOT.get(), 9).requires(ModBlocks.MOLYBDENUM_BLOCK.get()).unlockedBy("has_molybdenum_block", has(ModBlocks.MOLYBDENUM_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.MOLYBDENUM_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.MANGANESE_INGOT.get(), 9).requires(ModBlocks.MANGANESE_BLOCK.get()).unlockedBy("has_managnese_block", has(ModBlocks.MANGANESE_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.MANGANESE_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.NICKEL_INGOT.get(), 9).requires(ModBlocks.NICKEL_BLOCK.get()).unlockedBy("has_nickel_block", has(ModBlocks.NICKEL_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.NICKEL_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.ALUMINUM_INGOT.get(), 9).requires(ModBlocks.ALUMINUM_BLOCK.get()).unlockedBy("has_aluminum_block", has(ModBlocks.ALUMINUM_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.ALUMINUM_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.CHROMIUM_INGOT.get(), 9).requires(ModBlocks.CHROMIUM_BLOCK.get()).unlockedBy("has_chromium_block", has(ModBlocks.CHROMIUM_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.CHROMIUM_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.NIOB_INGOT.get(), 9).requires(ModBlocks.NIOB_BLOCK.get()).unlockedBy("has_niob_block", has(ModBlocks.NIOB_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.NIOB_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.TITANIUM_INGOT.get(), 9).requires(ModBlocks.TITANIUM_BLOCK.get()).unlockedBy("has_titanium_block", has(ModBlocks.TITANIUM_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.TITANIUM_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.URANIUM_INGOT.get(), 9).requires(ModBlocks.URANIUM_BLOCK.get()).unlockedBy("has_uranium_block", has(ModBlocks.URANIUM_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.URANIUM_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.FLUORITE_INGOT.get(), 9).requires(ModBlocks.FLUORITE_BLOCK.get()).unlockedBy("has_fluorite_block", has(ModBlocks.FLUORITE_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.FLUORITE_INGOT.get()) + "_craft_from_block");
        ShapelessRecipeBuilder.shapeless(ModItems.COBALT_INGOT.get(), 9).requires(ModBlocks.COBALT_BLOCK.get()).unlockedBy("has_cobalt_block", has(ModBlocks.COBALT_BLOCK.get())).save(consumer, ItemUtil.getRegString(ModItems.COBALT_INGOT.get()) + "_craft_from_block");

        // Ingot to Block
        ShapelessRecipeBuilder.shapeless(ModBlocks.BLASTED_IRON_BLOCK.get()).requires(ModItems.BLASTED_IRON_INGOT.get(), 9).unlockedBy("has_blasted_iron_ingot", has(ModItems.BLASTED_IRON_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.BLASTED_IRON_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.STEEL_BLOCK.get()).requires(ModItems.STEEL_INGOT.get(), 9).unlockedBy("has_steel_ingot", has(ModItems.STEEL_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.STEEL_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.MOLYBDENUM_BLOCK.get()).requires(ModItems.MOLYBDENUM_INGOT.get(), 9).unlockedBy("has_molybdenum_ingot", has(ModItems.MOLYBDENUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.MOLYBDENUM_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.MANGANESE_BLOCK.get()).requires(ModItems.MANGANESE_INGOT.get(), 9).unlockedBy("has_manganese_ingot", has(ModItems.MANGANESE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.MANGANESE_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.NICKEL_BLOCK.get()).requires(ModItems.NICKEL_INGOT.get(), 9).unlockedBy("has_nickel_ingot", has(ModItems.NICKEL_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.NICKEL_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.ALUMINUM_BLOCK.get()).requires(ModItems.ALUMINUM_INGOT.get(), 9).unlockedBy("has_aluminum_ingot", has(ModItems.ALUMINUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.ALUMINUM_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.CHROMIUM_BLOCK.get()).requires(ModItems.CHROMIUM_INGOT.get(), 9).unlockedBy("has_chromium_ingot", has(ModItems.CHROMIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.CHROMIUM_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.NIOB_BLOCK.get()).requires(ModItems.NIOB_INGOT.get(), 9).unlockedBy("has_niob_ingot", has(ModItems.NIOB_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.NIOB_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.TITANIUM_BLOCK.get()).requires(ModItems.TITANIUM_INGOT.get(), 9).unlockedBy("has_titanium_ingot", has(ModItems.TITANIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.TITANIUM_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.URANIUM_BLOCK.get()).requires(ModItems.URANIUM_INGOT.get(), 9).unlockedBy("has_uranium_ingot", has(ModItems.URANIUM_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.URANIUM_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.FLUORITE_BLOCK.get()).requires(ModItems.FLUORITE_INGOT.get(), 9).unlockedBy("has_fluorite_ingot", has(ModItems.FLUORITE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.FLUORITE_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.COBALT_BLOCK.get()).requires(ModItems.COBALT_INGOT.get(), 9).unlockedBy("has_cobalt_ingot", has(ModItems.COBALT_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.COBALT_BLOCK.get()) + "_craft_from_ingot");
        ShapelessRecipeBuilder.shapeless(ModBlocks.GRAPHITE_BLOCK.get()).requires(ModItems.GRAPHITE_INGOT.get(), 9).unlockedBy("has_graphite_ingot", has(ModItems.GRAPHITE_INGOT.get())).save(consumer, ItemUtil.getRegString(ModBlocks.GRAPHITE_BLOCK.get()) + "_craft_from_ingot");

    }
}
