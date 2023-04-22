package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.recipe.*;

public class ModRecipes {

    public static final RegistryObject<RecipeSerializer<ThoriumCraftingRecipe>> THORIUM_CRAFTING_SERIALIZER = Registration.SERIALIZERS.register(ThoriumCraftingRecipe.Serializer.ID.getPath(), () -> ThoriumCraftingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ConcentratingRecipe>> CONCENTRATING_SERIALIZER = Registration.SERIALIZERS.register(ConcentratingRecipe.Serializer.ID.getPath(), () -> ConcentratingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<BlastingRecipe>> BLASTING_SERIALIZER = Registration.SERIALIZERS.register(BlastingRecipe.Serializer.ID.getPath(), () -> BlastingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<DecomposingRecipe>> DECOMPOSING_SERIALIZER = Registration.SERIALIZERS.register(DecomposingRecipe.Serializer.ID.getPath(), () -> DecomposingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<OxidizingRecipe>> OXIDIZING_SERIALIZER = Registration.SERIALIZERS.register(OxidizingRecipe.Serializer.ID.getPath(), () -> OxidizingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CentrifugingRecipe>> CENTRIFUGING_SERIALIZER = Registration.SERIALIZERS.register(CentrifugingRecipe.Serializer.ID.getPath(), () -> CentrifugingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CrystallizingRecipe>> CRYSTALLIZING_SERIALIZER = Registration.SERIALIZERS.register(CrystallizingRecipe.Serializer.ID.getPath(), () -> CrystallizingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ElectrolysingRecipe>> ELECTROLYSING_SERIALIZER = Registration.SERIALIZERS.register(ElectrolysingRecipe.Serializer.ID.getPath(), () -> ElectrolysingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<EvaporatingRecipe>> EVAPORATING_SERIALIZER = Registration.SERIALIZERS.register(EvaporatingRecipe.Serializer.ID.getPath(), () -> EvaporatingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SaltSmeltingRecipe>> SALT_SMELTING_SERIALIZER = Registration.SERIALIZERS.register(SaltSmeltingRecipe.Serializer.ID.getPath(), () -> SaltSmeltingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ThoriumCraftingRecipe>> THORIUM_RECIPE_TYPE = registerType(ThoriumCraftingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<ConcentratingRecipe>> CONCENTRATING_RECIPE_TYPE = registerType(ConcentratingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<BlastingRecipe>> BLASTING_RECIPE_TYPE = registerType(BlastingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<DecomposingRecipe>> DECOMPOSING_RECIPE_TYPE = registerType(DecomposingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<OxidizingRecipe>> OXIDIZING_RECIPE_TYPE = registerType(OxidizingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<CentrifugingRecipe>> CENTRIFUGING_RECIPE_TYPE = registerType(CentrifugingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<CrystallizingRecipe>> CRYSTALLIZING_RECIPE_TYPE = registerType(CrystallizingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<ElectrolysingRecipe>> ELECTROLYSING_RECIPE_TYPE = registerType(ElectrolysingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<EvaporatingRecipe>> EVAPORATING_RECIPE_TYPE = registerType(EvaporatingRecipe.Serializer.ID);
    public static final RegistryObject<RecipeType<SaltSmeltingRecipe>> SALT_SMELTING_RECIPE_TYPE = registerType(SaltSmeltingRecipe.Serializer.ID);

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
