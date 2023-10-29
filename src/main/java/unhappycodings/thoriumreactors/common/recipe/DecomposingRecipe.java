package unhappycodings.thoriumreactors.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
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

public class DecomposingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient inputSlot;
    private final FluidStack inputTank;
    private final FluidStack outputTank;
    private final int ticks;
    private final int operationAfterTicks;

    public DecomposingRecipe(ResourceLocation id, Ingredient inputSlot, FluidStack inputTank, FluidStack outputTank, int ticks, int operationAfterTicks) {
        this.id = id;
        this.inputSlot = inputSlot;
        this.inputTank = inputTank;
        this.outputTank = outputTank;
        this.ticks = ticks;
        this.operationAfterTicks = operationAfterTicks;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        return inputSlot.test(container.getItem(0));
    }

    public boolean matchesAll(@NotNull SimpleContainer container, FluidStack inputFluid, FluidStack outputFluid, Level level) {
        return matches(container, level) && matchesFluid(inputFluid, outputFluid);
    }

    public boolean matchesFluid(FluidStack inputFluid, FluidStack outputFluid) {
        return this.inputTank.getFluid().isSame(inputFluid.getFluid()) && this.inputTank.getAmount() <= inputFluid.getAmount()
                && (this.outputTank.getFluid().isSame(outputFluid.getFluid()) || outputFluid.isEmpty());
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SimpleContainer pContainer, @Nullable RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(@Nullable RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    public FluidStack getResultFluid() {
        return outputTank;
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
        return Serializer.INSTANCE;
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DECOMPOSING_RECIPE_TYPE.get();
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, inputSlot);
    }

    public FluidStack getFluidIngredient() {
        return inputTank;
    }

    public int getTicks() {
        return ticks;
    }

    public static class Serializer implements RecipeSerializer<DecomposingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "decomposing");

        @NotNull
        @Override
        public DecomposingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-0"));
            if (input.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input ingredient must be set! (" + input + ")");

            FluidStack inputTank = FluidParseUtil.readFluid(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("tank-0"));
            if (inputTank.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input fluid must be set! (" + inputTank + ")");

            FluidStack outputTank = FluidParseUtil.readFluid(GsonHelper.getAsJsonObject(pSerializedRecipe, "output").getAsJsonObject("tank-0"));
            if (outputTank.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Output fluid must be set! (" + outputTank + ")");

            int ticks = GsonHelper.getAsInt(pSerializedRecipe, "ticks");
            if (ticks <= 0 || ticks > 2500)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 2500! (" + ticks + ")");

            int operationAfterTicks = GsonHelper.getAsInt(pSerializedRecipe, "operationAfterTicks");
            if (operationAfterTicks <= 0 || operationAfterTicks > 1000)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 1000! (" + operationAfterTicks + ")");
            return new DecomposingRecipe(pRecipeId, input, inputTank, outputTank, ticks, operationAfterTicks);
        }

        @Nullable
        @Override
        public DecomposingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient inputSlot = Ingredient.fromNetwork(buf);
            FluidStack inputTank = buf.readFluidStack();
            FluidStack outputTank = buf.readFluidStack();
            int ticks = buf.readInt();
            int operationAfterTicks = buf.readInt();
            return new DecomposingRecipe(id, inputSlot, inputTank, outputTank, ticks, operationAfterTicks);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, DecomposingRecipe recipe) {
            recipe.inputSlot.toNetwork(buf);
            buf.writeFluidStack(recipe.inputTank);
            buf.writeFluidStack(recipe.outputTank);
            buf.writeInt(recipe.ticks);
            buf.writeInt(recipe.operationAfterTicks);
        }
    }

}
