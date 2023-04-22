package unhappycodings.thoriumreactors.client.integration;

import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.recipe.OxidizingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.util.List;

public class OxidizingRecipeCategory implements IRecipeCategory<OxidizingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "oxidizing");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable tankScaleL;
    private IDrawable tankScaleR;
    private IDrawable progress;

    public OxidizingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 101, 143, 102, 68).addPadding(getGuiTop(), getGuiBottom(), getGuiLeft(), getGuiRight()).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.URANIUM_OXIDIZER_BLOCK.get()));
        this.helper = helper;
    }

    @Override
    public void draw(@NotNull OxidizingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
        if (progress == null) {
            this.progress = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE, 222, 247, 34, 9), recipe.getTicks(), IDrawableAnimated.StartDirection.LEFT, false);
            this.tankScaleL = helper.createDrawable(TEXTURE, 246, 168, 4, 41); // 2nd stage
            this.tankScaleR = helper.createDrawable(TEXTURE, 242, 168, 4, 63); // 1st stage
        }
        progress.draw(stack, getGuiLeft() + 34, getGuiTop() + 33);
        tankScaleL.draw(stack, getGuiLeft() + 1, getGuiTop() + 25);
        tankScaleR.draw(stack, getGuiLeft() + 83, getGuiTop() + 2);

        RenderUtil.drawCenteredText("Uranium Oxidization", stack, getBackground().getWidth() / 2, 6);
        RenderUtil.drawCenteredText(recipe.getTicks() / 20 + "s", stack, getBackground().getWidth() / 2, 65);
    }

    public int getGuiTop () {
        return 20;
    }

    public int getGuiBottom () {
        return 10;
    }

    public int getGuiLeft() {
        return 10;
    }

    public int getGuiRight () {
        return 10;
    }

    @NotNull
    @Override
    public RecipeType<OxidizingRecipe> getRecipeType() {
        return JEIModIntegration.OXIDIZING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.uranium_oxidizer_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, OxidizingRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 2, getGuiTop() + 1).addItemStack(recipe.getIngredients().get(0).getItems()[0]);
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 1, getGuiTop() + 23).setFluidRenderer(6000, true, 18, 44)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getFluidIngredient(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getFluidIngredient().getAmount()))));
        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 83, getGuiTop() + 1).setFluidRenderer(10000, true, 18, 66)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getResultFluid(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getResultFluid().getAmount()))));
    }
}