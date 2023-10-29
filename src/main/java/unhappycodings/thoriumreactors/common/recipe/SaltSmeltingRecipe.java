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

public class SaltSmeltingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient inputSlot1;
    private final Ingredient inputSlot2;
    private final Ingredient inputSlot3;
    private final FluidStack outputTank;
    private final int operationAfterTicks;
    private final int ticks;
    private final int temperature;

    public SaltSmeltingRecipe(ResourceLocation id, Ingredient inputSlot1, Ingredient inputSlot2, Ingredient inputSlot3, FluidStack outputTank, int ticks, int operationAfterTicks, int temperature) {
        this.id = id;
        this.inputSlot1 = inputSlot1;
        this.inputSlot2 = inputSlot2;
        this.inputSlot3 = inputSlot3;
        this.outputTank = outputTank;
        this.ticks = ticks;
        this.operationAfterTicks = operationAfterTicks;
        this.temperature = temperature;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer container, Level level) {
        if (level.isClientSide()) return false;
        return inputSlot1.test(container.getItem(2)) && inputSlot2.test(container.getItem(1)) && inputSlot3.test(container.getItem(0));
    }

    public boolean matchesAll(@NotNull SimpleContainer container, FluidStack outputFluid, Level level) {
        return matches(container, level) && matchesFluid(outputFluid);
    }

    public boolean matchesFluid(FluidStack outputFluid) {
        return this.outputTank.getFluid().isSame(outputFluid.getFluid()) || outputFluid.isEmpty();
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SimpleContainer container, @Nullable RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    public Ingredient getInputSlot1() {
        return inputSlot1;
    }

    public Ingredient getInputSlot2() {
        return inputSlot2;
    }

    public Ingredient getInputSlot3() {
        return inputSlot3;
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
        return SaltSmeltingRecipe.Serializer.INSTANCE;
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SALT_SMELTING_RECIPE_TYPE.get();
    }

    public int getTemperature() {
        return temperature;
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, inputSlot3, inputSlot2, inputSlot1);
    }

    public int getTicks() {
        return ticks;
    }

    public static class Serializer implements RecipeSerializer<SaltSmeltingRecipe> {
        public static final SaltSmeltingRecipe.Serializer INSTANCE = new SaltSmeltingRecipe.Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ThoriumReactors.MOD_ID, "salt_smelting");

        @NotNull
        @Override
        public SaltSmeltingRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            Ingredient input1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-0"));
            if (input1.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input ingredient 0 must be set! (" + input1 + ")");
            Ingredient input2 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-1"));
            if (input2.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input ingredient 1 must be set! (" + input2 + ")");
            Ingredient input3 = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input").getAsJsonObject("slot-2"));
            if (input3.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Input ingredient 2 must be set! (" + input3 + ")");

            FluidStack outputTank = FluidParseUtil.readFluid(GsonHelper.getAsJsonObject(pSerializedRecipe, "output").getAsJsonObject("tank-0"));
            if (outputTank.isEmpty())
                throw new IllegalArgumentException("Invalid pattern: Output fluid must be set! (" + outputTank + ")");

            int ticks = GsonHelper.getAsInt(pSerializedRecipe, "ticks");
            if (ticks <= 0 || ticks > 2500)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 2500! (" + ticks + ")");

            int operationAfterTicks = GsonHelper.getAsInt(pSerializedRecipe, "operationAfterTicks");
            if (operationAfterTicks <= 0 || operationAfterTicks > 1000)
                throw new IllegalArgumentException("Invalid pattern: Needed ticks must be in between 1 and 1000! (" + operationAfterTicks + ")");
            int temperature = GsonHelper.getAsInt(pSerializedRecipe, "temperature");
            if (temperature <= 0 || temperature > 2500)
                throw new IllegalArgumentException("Invalid pattern: Needed temperature cannot be zero or higher than 2500! (" + ticks + ")");
            return new SaltSmeltingRecipe(pRecipeId, input1, input2, input3, outputTank, ticks, operationAfterTicks, temperature);
        }

        @Nullable
        @Override
        public SaltSmeltingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient inputSlot1 = Ingredient.fromNetwork(buf);
            Ingredient inputSlot2 = Ingredient.fromNetwork(buf);
            Ingredient inputSlot3 = Ingredient.fromNetwork(buf);
            FluidStack outputTank = buf.readFluidStack();
            int ticks = buf.readInt();
            int operationAfterTicks = buf.readInt();
            int temperature = buf.readInt();
            return new SaltSmeltingRecipe(id, inputSlot1, inputSlot2, inputSlot3, outputTank, ticks, operationAfterTicks, temperature);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, SaltSmeltingRecipe recipe) {
            recipe.inputSlot1.toNetwork(buf);
            recipe.inputSlot2.toNetwork(buf);
            recipe.inputSlot3.toNetwork(buf);
            buf.writeFluidStack(recipe.outputTank);
            buf.writeInt(recipe.ticks);
            buf.writeInt(recipe.operationAfterTicks);
            buf.writeInt(recipe.temperature);
        }
    }

}
