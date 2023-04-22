package unhappycodings.thoriumreactors.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;

import java.util.Objects;

public class ThoriumCraftingRecipe implements Recipe<SimpleContainer> {
    public static final int MAX_WIDTH = 5;
    public static final int MAX_HEIGHT = 5;
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public ThoriumCraftingRecipe(ResourceLocation id, NonNullList<Ingredient> recipeItems, ItemStack output) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        NonNullList<ItemStack> currentItems = NonNullList.withSize(MAX_WIDTH * MAX_HEIGHT, ItemStack.EMPTY);
        for (int i = 0; i < MAX_WIDTH * MAX_HEIGHT; i++)
            currentItems.set(i, container.getItem(i).is(Items.AIR) ? ItemStack.EMPTY : container.getItem(i));
        return isRecipe(currentItems);
    }

    public boolean isRecipe(NonNullList<ItemStack> currentItems) {
        byte airs = 0;
        for (int i = 0, s = recipeItems.size(); i < s; ++i) {
            if (!recipeItems.get(i).test(currentItems.get(i))) return false;
            if (currentItems.get(i).is(Items.AIR)) airs++;
        }
        return airs != MAX_WIDTH * MAX_HEIGHT;
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SimpleContainer container) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.THORIUM_RECIPE_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public static class Serializer implements RecipeSerializer<ThoriumCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "thorium_crafting");

        @Override
        public ThoriumCraftingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            JsonArray pattern = GsonHelper.getAsJsonArray(pSerializedRecipe, "pattern");
            JsonObject ingredients = GsonHelper.getAsJsonObject(pSerializedRecipe, "key");
            NonNullList<Ingredient> recipeItems = NonNullList.withSize(MAX_WIDTH * MAX_HEIGHT, Ingredient.EMPTY);

            if (pattern.size() == 0) throw new IllegalArgumentException("Invalid pattern: Recipe height cannot be 0!");
            if (pattern.size() > MAX_HEIGHT)
                throw new IllegalArgumentException("Invalid pattern: Recipe height cannot be bigger than 5! (Value: " + pattern.size() + ")");
            int index = 0;

            for (int i = 0; i < pattern.size(); i++) {
                String[] current = pattern.get(i).getAsString().split("");
                if (Objects.equals(current[0], ""))
                    throw new IllegalArgumentException("Invalid pattern: Recipe width cannot be 0!");
                if (current.length > MAX_WIDTH)
                    throw new IllegalArgumentException("Invalid pattern: Recipe width cannot be bigger than 5! (Value: " + current.length + ")");
                for (int e = 0; e < current.length; e++) {
                    if (!ingredients.has(current[e]) && !Objects.equals(current[e], " "))
                        throw new IllegalArgumentException("Invalid pattern: Recipe key not existing! (Value: " + current[e] + ")");
                    recipeItems.set(index, !Objects.equals(current[e], " ") ? Ingredient.fromJson(ingredients.get(current[e])) : Ingredient.EMPTY);
                    index++;
                }
            }

            return new ThoriumCraftingRecipe(pRecipeId, recipeItems, output);
        }

        @Override
        public @Nullable ThoriumCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(MAX_WIDTH * MAX_HEIGHT, Ingredient.EMPTY);

            for (int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            return new ThoriumCraftingRecipe(id, nonnulllist, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ThoriumCraftingRecipe recipe) {
            for (Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.output);
        }
    }

}
