package unhappycodings.thoriumreactors.client.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableScreen;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.recipe.ModRecipes;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIModIntegration implements IModPlugin {
    public static final mezz.jei.api.recipe.RecipeType<ThoriumCraftingRecipe> THORIUM_RECIPE_TYPE = new RecipeType<>(ThoriumCraftingRecipeCategory.UID, ThoriumCraftingRecipe.class);

    public static final int SLOT_GRID = 0;
    public static final int PLAYER_INVENTORY_COUNT = 36;

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModBlocks.THORIUM_CRAFTING_TABLE.get().asItem().getDefaultInstance(), THORIUM_RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ThoriumCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ThoriumCraftingTableScreen.class, 109, 53, 22, 15, THORIUM_RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ThoriumCraftingRecipe> recipes = rm.getAllRecipesFor(ModRecipes.THORIUM_RECIPE_TYPE.get());
        registration.addRecipes(THORIUM_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ThoriumCraftingTableContainer.class, ContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), THORIUM_RECIPE_TYPE,
                SLOT_GRID + PLAYER_INVENTORY_COUNT, ThoriumCraftingRecipe.MAX_WIDTH * ThoriumCraftingRecipe.MAX_HEIGHT, SLOT_GRID, PLAYER_INVENTORY_COUNT);
    }
}
