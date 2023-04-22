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

public class ConcentratingRecipe implements Recipe<SimpleContainer> {
    public final ResourceLocation id;
    public final Ingredient inputSlot;
    public final ItemStack outputSlot;
    public final int ticks;

    public ConcentratingRecipe(ResourceLocation id, Ingredient inputSlot, ItemStack outputTank, int ticks) {
        this.id = id;
        this.inputSlot = inputSlot;
        this.outputSlot = outputTank;
        this.ticks = ticks;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        return inputSlot.test(container.getItem(0));
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SimpleContainer container) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return outputSlot.copy();
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
        return ModRecipes.CONCENTRATING_RECIPE_TYPE.get();
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, inputSlot);
    }

    public int getTicks() {
        return ticks;
    }

    public static class Serializer implements RecipeSerializer<ConcentratingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "concentrating");

        @NotNull
        @Override
        public ConcentratingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-0"));
            if (input.isEmpty()) throw new IllegalArgumentException("Invalid pattern: Input ingredient must be set! (" + input + ")");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output").getAsJsonObject("slot-0"));
            if (output.isEmpty()) throw new IllegalArgumentException("Invalid pattern: Output ingredient must be set! (" + output + ")");
            int ticks = GsonHelper.getAsInt(pSerializedRecipe, "ticks");
            if (ticks <= 0 || ticks > 2500) throw new IllegalArgumentException("Invalid pattern: Needed ticks cannot be zero or higher than 2500! (" + ticks + ")");
            return new ConcentratingRecipe(pRecipeId, input, output, ticks);
        }

        @Nullable
        @Override
        public ConcentratingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            int ticks = buf.readInt();
            return new ConcentratingRecipe(id, input, output, ticks);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, ConcentratingRecipe recipe) {
            recipe.inputSlot.toNetwork(buf);
            buf.writeItem(recipe.outputSlot);
            buf.writeInt(recipe.ticks);
        }
    }

}
