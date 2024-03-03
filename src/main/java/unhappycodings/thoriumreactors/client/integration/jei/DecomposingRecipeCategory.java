package unhappycodings.thoriumreactors.client.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.recipe.DecomposingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.List;

public class DecomposingRecipeCategory implements IRecipeCategory<DecomposingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "decomposing");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable tankGroin1;
    private IDrawable tankGroin2;
    private IDrawable progress;

    public DecomposingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 143, 101, 68).addPadding(getGuiTop(), getGuiBottom(), getGuiLeft(), getGuiRight()).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DECOMPOSER_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public void draw(@NotNull DecomposingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        if (progress == null) {
            this.progress = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 204, 240, 36, 8), recipe.getTicks(), IDrawableAnimated.StartDirection.LEFT, false);
            this.tankGroin1 = helper.createDrawable(TEXTURE, 246, 168, 4, 41); // 2nd stage
            this.tankGroin2 = helper.createDrawable(TEXTURE, 242, 168, 4, 63); // 1st stage
        }
        progress.draw(guiGraphics, getGuiLeft() + 32, getGuiTop() + 32);
        tankGroin1.draw(guiGraphics, getGuiLeft() + 1, getGuiTop() + 25);
        tankGroin2.draw(guiGraphics, getGuiLeft() + 82, getGuiTop() + 2);

        ScreenUtil.drawCenteredText("Decomposing", guiGraphics, getBackground().getWidth() / 2, 6, false);
        ScreenUtil.drawCenteredText(recipe.getTicks() / 20 + "s", guiGraphics, getBackground().getWidth() / 2, 40, false);
    }

    public int getGuiTop() {
        return 20;
    }

    public int getGuiBottom() {
        return 10;
    }

    public int getGuiLeft() {
        return 10;
    }

    public int getGuiRight() {
        return 10;
    }

    @NotNull
    @Override
    public RecipeType<DecomposingRecipe> getRecipeType() {
        return JEIModIntegration.DECOMPOSING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.decomposer_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, DecomposingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 1, getGuiTop() + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 1, getGuiTop() + 23).setFluidRenderer(recipe.getFluidIngredient().getAmount(), true, 17, 44)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getFluidIngredient(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getFluidIngredient().getAmount()))));
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 82, getGuiTop() + 1).setFluidRenderer(recipe.getResultFluid().getAmount(), true, 18, 66)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getResultFluid(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getResultFluid().getAmount()))));
    }
}
