package unhappycodings.thoriumreactors.client.integration.jei;

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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.recipe.SaltSmeltingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class SmeltingRecipeCategory implements IRecipeCategory<SaltSmeltingRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ThoriumReactors.MOD_ID, "smelting");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei_second.png");
    public final static ResourceLocation TEXTURE_ONE = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/gui_jei.png");

    private final IGuiHelper helper;
    private final IDrawable background;
    private final IDrawable icon;
    private IDrawable heating;

    public SmeltingRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 102, 0, 122, 68).addPadding(getGuiTop(), getGuiBottom(), getGuiLeft(), getGuiRight()).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SALT_MELTER_BLOCK.get()));
        this.helper = helper;
    }

    @NotNull
    @Override
    public List<Component> getTooltipStrings(@NotNull SaltSmeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if (ScreenUtil.mouseInArea(getGuiLeft() + 23, getGuiTop() + 26, getGuiLeft() + 38, getGuiTop() + 41, (int) mouseX, (int) mouseY)) {
            list.add(Component.literal(recipe.getTemperature() + "°C"));
        }
        return list;
        // 10 + 52, 20 + 0
    }

    @Override
    public void draw(@NotNull SaltSmeltingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        if (heating == null) {
            this.heating = helper.createAnimatedDrawable(helper.createDrawable(TEXTURE_ONE, 240, 231, 16, 16), 80, IDrawableAnimated.StartDirection.BOTTOM, false);
        }
        heating.draw(graphics, getGuiLeft() + 23, getGuiTop() + 26);
        ScreenUtil.drawCenteredText("Salt Melting", graphics, getBackground().getWidth() / 2, 6, false);
        ScreenUtil.drawCenteredText(recipe.getTicks() / 20 + "s ", graphics, getBackground().getWidth() / 2, getGuiTop() + 60, false);
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
        return 30;
    }

    @NotNull
    @Override
    public RecipeType<SaltSmeltingRecipe> getRecipeType() {
        return JEIModIntegration.SMELTING_RECIPE_TYPE;
    }

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("block.thoriumreactors.salt_melter_block");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SaltSmeltingRecipe recipe, @NotNull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 23, getGuiTop() + 51).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 1, getGuiTop() + 26).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, getGuiLeft() + 23, getGuiTop() + 1).addIngredients(recipe.getIngredients().get(2));

        builder.addSlot(RecipeIngredientRole.OUTPUT, getGuiLeft() + 103, getGuiTop() + 1).setFluidRenderer(3000, true, 18, 66)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(new FluidStack(recipe.getResultFluid(), (int) (Math.floor((float) recipe.getTicks() / recipe.getOperationAfterTicks()) * recipe.getResultFluid().getAmount()))));
    }
}
