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
import unhappycodings.thoriumreactors.common.recipe.FluidEnrichingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.List;

public class FluidEnrichingRecipeCategory implements IRecipeCategory<FluidEnrichingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "fluid_enriching");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable tankScaleL;
    private IDrawable tankScaleR;
    private IDrawable progress;

    public FluidEnrichingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 101, 143, 102, 68).addPadding(getGuiTop(), getGuiBottom(), getGuiLeft(), getGuiRight()).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FLUID_ENRICHER_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public void draw(@NotNull FluidEnrichingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        if (progress == null) {
            this.progress = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 222, 247, 34, 9), recipe.getTicks(), IDrawableAnimated.StartDirection.LEFT, false);
            this.tankScaleL = helper.createDrawable(TEXTURE, 246, 168, 4, 41); // 2nd stage
            this.tankScaleR = helper.createDrawable(TEXTURE, 242, 168, 4, 63); // 1st stage
        }
        progress.draw(graphics, getGuiLeft() + 34, getGuiTop() + 33);
        tankScaleL.draw(graphics, getGuiLeft() + 1, getGuiTop() + 25);
        tankScaleR.draw(graphics, getGuiLeft() + 83, getGuiTop() + 2);

        ScreenUtil.drawCenteredText("Fluid Enriching", graphics, getBackground().getWidth() / 2, 6, false);
        ScreenUtil.drawCenteredText(recipe.getTicks() / 20 + "s", graphics, getBackground().getWidth() / 2, 65, false);
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
    public RecipeType<FluidEnrichingRecipe> getRecipeType() {
        return JEIModIntegration.FLUID_ENRICHING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.fluid_enricher_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FluidEnrichingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 2, getGuiTop() + 1).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 1, getGuiTop() + 23).setFluidRenderer(recipe.getFluidIngredient().getAmount(), true, 18, 44)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getFluidIngredient(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getFluidIngredient().getAmount()))));
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 83, getGuiTop() + 1).setFluidRenderer(recipe.getResultFluid().getAmount(), true, 18, 66)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getResultFluid(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getResultFluid().getAmount()))));
    }
}