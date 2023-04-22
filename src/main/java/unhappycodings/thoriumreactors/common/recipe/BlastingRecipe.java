package unhappycodings.thoriumreactors.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;

public class BlastingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient inputLeft;
    private final Ingredient inputRight;
    private final ItemStack output;
    private final int ticks;
    private final int temperature;

    public BlastingRecipe(ResourceLocation id, Ingredient inputLeft, Ingredient inputRight, ItemStack output, int ticks, int temperature) {
        this.id = id;
        this.output = output;
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.ticks = ticks;
        this.temperature = temperature;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        return inputLeft.test(container.getItem(0)) && inputRight.test(container.getItem(1));
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
        return ModRecipes.BLASTING_RECIPE_TYPE.get();
    }

    public int getTemperature() {
        return temperature;
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, inputLeft, inputRight);
    }

    public int getTicks() {
        return ticks;
    }

    public static class Serializer implements RecipeSerializer<BlastingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "blasting");

        @NotNull
        @Override
        public BlastingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            Ingredient inputLeft = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-0"));
            if (inputLeft.isEmpty()) throw new IllegalArgumentException("Invalid pattern: Input left ingredient must be set! (" + inputLeft + ")");
            Ingredient inputRight = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-1"));
            if (inputRight.isEmpty()) throw new IllegalArgumentException("Invalid pattern: Input right ingredient must be set! (" + inputRight + ")");

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            if (output.isEmpty()) throw new IllegalArgumentException("Invalid pattern: Output ingredient must be set! (" + output + ")");
            int ticks = GsonHelper.getAsInt(pSerializedRecipe, "ticks");
            if (ticks <= 0 || ticks > 2500) throw new IllegalArgumentException("Invalid pattern: Needed ticks cannot be zero or higher than 2500! (" + ticks + ")");
            int temperature = GsonHelper.getAsInt(pSerializedRecipe, "temperature");
            if (temperature <= 0 || temperature > 2500) throw new IllegalArgumentException("Invalid pattern: Needed temperature cannot be zero or higher than 2500! (" + ticks + ")");
            return new BlastingRecipe(pRecipeId, inputLeft, inputRight, output, ticks, temperature);
        }

        @Nullable
        @Override
        public BlastingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient inputLeft = Ingredient.fromNetwork(buf);
            Ingredient inputRight = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int ticks = buf.readInt();
            int temperature = buf.readInt();
            return new BlastingRecipe(id, inputLeft, inputRight, output, ticks, temperature);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, BlastingRecipe recipe) {
            recipe.inputLeft.toNetwork(buf);
            recipe.inputRight.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeInt(recipe.ticks);
            buf.writeInt(recipe.temperature);
        }
    }

}
