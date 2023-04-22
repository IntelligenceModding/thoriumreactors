package unhappycodings.thoriumreactors.client.integration.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
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
import unhappycodings.thoriumreactors.common.recipe.ConcentratingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class ConcentratingRecipeCategory implements IRecipeCategory<ConcentratingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "concentrating");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable progress;

    public ConcentratingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 90, 100, 18).addPadding(20, 20, 20, 20).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CONCENTRATOR_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public void draw(@NotNull ConcentratingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        if (progress == null) {
            this.progress = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 181, 246, 41, 10), recipe.getTicks(), IDrawableAnimated.StartDirection.LEFT, false);
        }
        progress.draw(stack, 50, 24);
        RenderUtil.drawCenteredText("Concentrating", stack, getBackground().getWidth() / 2, 6);
        RenderUtil.drawCenteredText(recipe.getTicks() / 20 + "s", stack, getBackground().getWidth() / 2, 40);
    }

    @NotNull
    @Override
    public RecipeType<ConcentratingRecipe> getRecipeType() {
        return JEIModIntegration.CONCENTRATING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.concentrator_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ConcentratingRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 21, 21).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 103, 21).addItemStack(recipe.getResultItem());
    }
}
