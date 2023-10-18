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
import unhappycodings.thoriumreactors.common.recipe.BlastingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class BlastingRecipeCategory implements IRecipeCategory<BlastingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "blasting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable progress;
    private IDrawable heating;

    public BlastingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 108, 98 + 18, 35).addPadding(20, 20, 10, 22).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BLAST_FURNACE_BLOCK.get()));
        this.helper = helper;
    }

    @NotNull
    @Override
    public List<Component> getTooltipStrings(@NotNull BlastingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (ScreenUtil.mouseInArea(10 + 52, 20, 10 + 67, 20 + 15, (int) mouseX, (int) mouseY)) {
            list.add(Component.literal(recipe.getTemperature() + "°C"));
        }
        return list;
    }

    @Override
    public void draw(@NotNull BlastingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        if (progress == null || heating == null) {
            this.progress = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 222, 247, 34, 9), recipe.getTicks(), IDrawableAnimated.StartDirection.LEFT, false);
            this.heating = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 240, 231, 16, 16), 80, IDrawableAnimated.StartDirection.BOTTOM, false);
        }
        progress.draw(stack, 10 + 43, 20 + 21);
        heating.draw(stack, 10 + 52, 20);
        ScreenUtil.drawCenteredText("Blasting", stack, getBackground().getWidth() / 2, 6);
        ScreenUtil.drawCenteredText(recipe.getTicks() / 20 + "s ", stack, getBackground().getWidth() / 2, 20 + 40);
        if (recipe.getSecondaryChance() > 0)
            ScreenUtil.drawCenteredText(recipe.getSecondaryChance() + "%", stack, 120, 20 + 40);

    }

    @NotNull
    @Override
    public RecipeType<BlastingRecipe> getRecipeType() {
        return JEIModIntegration.BLASTING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.blast_furnace_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BlastingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10 + 1, 20 + 18).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.INPUT, 10 + 22, 20 + 18).addItemStack(recipe.getIngredients().get(1).getItems().length > 0 ? recipe.getIngredients().get(1).getItems()[0] : ItemStack.EMPTY);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 10 + 81, 20 + 18).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 10 + 81 + 18, 20 + 18).addItemStack(recipe.getSecondaryResultItem());
    }
}
