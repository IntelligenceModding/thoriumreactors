package unhappycodings.thoriumreactors.client.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.recipe.ThoriumCraftingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class ThoriumCraftingRecipeCategory implements IRecipeCategory<ThoriumCraftingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "thorium_crafting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ThoriumCraftingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 152, 90);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.THORIUM_CRAFTING_TABLE.get()));
    }

    @NotNull
    @Override
    public RecipeType<ThoriumCraftingRecipe> getRecipeType() {
        return JEIModIntegration.THORIUM_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.thorium_crafting_table");
    }

    @NotNull
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @NotNull
    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ThoriumCraftingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        byte[] columnNumbers = {17, 35, 53, 71, 89};
        byte[] rowNumbers = {13, 31, 49, 67, 85};
        byte index = 0;
        for (byte column : columnNumbers) {
            for (byte row : rowNumbers) {
                builder.addSlot(RecipeIngredientRole.INPUT, row - 12, column - 16).addIngredients(recipe.getIngredients().get(index));
                index++;
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 143 - 12, 53 - 16).addItemStack(recipe.getResultItem(null));
    }
}
