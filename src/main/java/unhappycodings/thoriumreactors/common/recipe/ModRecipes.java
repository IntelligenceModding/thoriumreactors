package unhappycodings.thoriumreactors.common.recipe;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.Registration;

public class ModRecipes {

    public static final RegistryObject<RecipeSerializer<ThoriumCraftingRecipe>> THORIUM_CRAFTING_SERIALIZER =
            Registration.SERIALIZERS.register("thorium_crafting", () -> ThoriumCraftingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ThoriumCraftingRecipe>> THORIUM_RECIPE_TYPE = registerType(new ResourceLocation(ThoriumReactors.MOD_ID, "thorium_crafting"));

    public static void register() {
    }

    private static <I extends Recipe<?>> RegistryObject<RecipeType<I>> registerType(ResourceLocation name) {
        RecipeType<I> recipeType = new RecipeType<>() {
            @Override
            public String toString() {
                return name.toString();
            }
        };
        return Registration.TYPES.register(name.getPath(), () -> recipeType);
    }
}
