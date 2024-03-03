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
import unhappycodings.thoriumreactors.common.recipe.ElectrolysingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.List;

public class ElectrolysingRecipeCategory implements IRecipeCategory<ElectrolysingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "electrolysing");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei_second.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable tankScaleL;
    private IDrawable tankScaleR;
    private IDrawable progressUp;
    private IDrawable progressDown;

    public ElectrolysingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 102, 68).addPadding(getGuiTop(), getGuiBottom(), getGuiLeft(), getGuiRight()).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public void draw(ElectrolysingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        if (progressUp == null) {
            this.progressUp = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 235, 236, 20, 7), recipe.getTicks(), IDrawableAnimated.StartDirection.BOTTOM, false);
            this.progressDown = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 235, 250, 20, 6), recipe.getTicks(), IDrawableAnimated.StartDirection.TOP, false);
            this.tankScaleL = helper.createDrawable(TEXTURE, 242, 168, 4, 63); // 1st stage
            this.tankScaleR = helper.createDrawable(TEXTURE, 246, 168, 4, 41); // 2nd stage
        }
        progressUp.draw(guiGraphics, getGuiLeft() + 41, getGuiTop() + 19);
        progressDown.draw(guiGraphics, getGuiLeft() + 41, getGuiTop() + 32);
        tankScaleL.draw(guiGraphics, getGuiLeft() + 1, getGuiTop() + 2);
        tankScaleR.draw(guiGraphics, getGuiLeft() + 83, getGuiTop() + 24);

        ScreenUtil.drawCenteredText("Electrolytic Separation", guiGraphics, getBackground().getWidth() / 2, 6, false);
        ScreenUtil.drawCenteredText(recipe.getTicks() / 20 + "s", guiGraphics, getBackground().getWidth() / 2, 71, false);
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
    public RecipeType<ElectrolysingRecipe> getRecipeType() {
        return JEIModIntegration.ELECTROLYSING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.electrolytic_salt_separator");
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
    public void setRecipe(IRecipeLayoutBuilder builder, ElectrolysingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 1, getGuiTop() + 1).setFluidRenderer(recipe.getFluidIngredient().getAmount(), true, 18, 66)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getFluidIngredient(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getFluidIngredient().getAmount()))));
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 83, getGuiTop() + 23).setFluidRenderer(recipe.getResultFluid().getAmount(), true, 18, 44)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getResultFluid(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getResultFluid().getAmount()))));
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 84, getGuiTop() + 1).addItemStack(recipe.getResultItem(null));
    }
}
