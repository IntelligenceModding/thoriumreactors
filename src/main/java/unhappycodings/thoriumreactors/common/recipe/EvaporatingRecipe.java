package unhappycodings.thoriumreactors.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.util.FluidParseUtil;

public class EvaporatingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final FluidStack inputTank;
    private final Ingredient outputSlot;
    private final int operationAfterTicks;
    private final int ticks;

    public EvaporatingRecipe(ResourceLocation id, Ingredient inputSlot, FluidStack inputTank, int ticks, int operationAfterTicks) {
        this.id = id;
        this.inputTank = inputTank;
        this.outputSlot = inputSlot;
        this.ticks = ticks;
        this.operationAfterTicks = operationAfterTicks;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        return false;
    }

    public boolean matchesFluid(FluidStack inputFluid) {
        return this.inputTank.getFluid().isSame(inputFluid.getFluid()) && this.inputTank.getAmount() <= inputFluid.getAmount();
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
        return outputSlot.getItems()[0];
    }

    public int getOperationAfterTicks() {
        return operationAfterTicks;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return EvaporatingRecipe.Serializer.INSTANCE;
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.EVAPORATING_RECIPE_TYPE.get();
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY);
    }

    public FluidStack getFluidIngredient() {
        return inputTank;
    }

    public int getTicks() {
        return ticks;
    }

    public static class Serializer implements RecipeSerializer<EvaporatingRecipe> {
        public static final EvaporatingRecipe.Serializer INSTANCE = new EvaporatingRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "evaporating");

        @NotNull
        @Override
        public EvaporatingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            Ingredient output = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output").getAsJsonObject("slot-0"));
            if (output.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input ingredient must be set! (" + output + ")");

            FluidStack inputTank = FluidParseUtil.readFluid(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("tank-0"));
            if (inputTank.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input fluid must be set! (" + inputTank + ")");

            int ticks = GsonHelper.getAsInt(pSerializedRecipe, "ticks");
            if (ticks <= 0 || ticks > 2500)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 2500! (" + ticks + ")");

            int operationAfterTicks = GsonHelper.getAsInt(pSerializedRecipe, "operationAfterTicks");
            if (operationAfterTicks <= 0 || operationAfterTicks > 1000)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 1000! (" + operationAfterTicks + ")");
            return new EvaporatingRecipe(pRecipeId, output, inputTank, ticks, operationAfterTicks);
        }

        @Nullable
        @Override
        public EvaporatingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient outputSlot = Ingredient.fromNetwork(buf);
            FluidStack inputTank = buf.readFluidStack();
            int ticks = buf.readInt();
            int operationAfterTicks = buf.readInt();
            return new EvaporatingRecipe(id, outputSlot, inputTank, ticks, operationAfterTicks);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, EvaporatingRecipe recipe) {
            recipe.outputSlot.toNetwork(buf);
            buf.writeFluidStack(recipe.inputTank);
            buf.writeInt(recipe.ticks);
            buf.writeInt(recipe.operationAfterTicks);
        }
    }

}
