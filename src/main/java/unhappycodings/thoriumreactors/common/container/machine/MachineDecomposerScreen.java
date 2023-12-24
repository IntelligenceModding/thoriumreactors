package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineDecomposerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

public class MachineDecomposerScreen extends MachineScreen<MachineDecomposerContainer> {
    private MachineDecomposerContainer container;

    public MachineDecomposerScreen(MachineDecomposerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineDecomposerBlockEntity tile = this.getMenu().getTile();
        // Dump Left
        addWidget(new ModButton(34, 90, 3, 8, null, () -> changeDumpMode("input"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(37, 90, 19, 8, null, () -> changeDumpMode("dumpInput"), null, tile, this, 0, 0, true));

        // Dump Right
        addWidget(new ModButton(116, 90, 3, 8, null, () -> changeDumpMode("output"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(119, 90, 19, 8, null, () -> changeDumpMode("dumpOutput"), null, tile, this, 0, 0, true));

    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int x, int y) {
        super.renderBg(graphics, partialTicks, x, y);
        MachineDecomposerBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 23, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 23, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());

        RenderUtil.renderFluid(getGuiLeft() + 118, getGuiTop() + 85, 66, 16, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 85, 66, 2, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        graphics.blit(getTexture(), getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank
        graphics.blit(getTexture(), getGuiLeft() + 37, getGuiTop() + 42, 180, 52, 2, 22); // Left Tank
        graphics.blit(getTexture(), getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Right Tank

        int height = entity.getMaxRecipeTime() != 0 ? 36 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 36) : 0;
        graphics.blit(getTexture(), getGuiLeft() + 69, getGuiTop() + 51, 184, 0, height, 6); // Progress

        if (entity.isInputDump())
            graphics.blit(getTexture(), getGuiLeft() + 35, getGuiTop() + 91, 185, 25, 1, 3); // Left Dump - Green
        else graphics.blit(getTexture(), getGuiLeft() + 35, getGuiTop() + 94, 185, 28, 1, 3); // Left Dump - Red

        if (entity.isOutputDump())
            graphics.blit(getTexture(), getGuiLeft() + 117, getGuiTop() + 91, 185, 25, 1, 3); // Right Dump - Green
        else graphics.blit(getTexture(), getGuiLeft() + 117, getGuiTop() + 94, 185, 28, 1, 3); // Right Dump - Red

        if (entity.getState())
            graphics.blit(getTexture(), getGuiLeft() + 81, getGuiTop() + 89, 185, 25, 6, 1); // Power Indicator - Green
        else graphics.blit(getTexture(), getGuiLeft() + 88, getGuiTop() + 89, 185, 28, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY) {
        super.renderLabels(graphics, pMouseX, pMouseY);
        MachineDecomposerBlockEntity entity = this.container.getTile();
        PoseStack pPoseStack = graphics.pose();

        ScreenUtil.drawText(Component.translatable("key.categories.inventory").withStyle(ScreenUtil::notoSans), graphics, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.decomposer.name")).withStyle(ScreenUtil::notoSans), graphics, 10, 2, 11184810);
        ScreenUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(ScreenUtil::notoSans), graphics, 242, 2, 11184810);
        pPoseStack.popPose();
        ScreenUtil.drawCenteredText(Component.translatable(entity.getState() ? FormattingUtil.getTranslatable("machines.state.running") : FormattingUtil.getTranslatable("machines.state.idle")).withStyle(ScreenUtil::notoSans), graphics, 87, 78, 4182051);

        if (ScreenUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 42, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{entity.getFluidAmountIn() > 0 ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.fluid")).append(" ").append(entity.getFluidIn().getFluid().getFluidType().getDescription().getString()) : Component.empty(), Component.literal(entity.getFluidAmountIn() + " mB / " + entity.getFluidCapacityIn() + " mB"), Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true))});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 20, getGuiLeft() + 136, getGuiTop() + 85, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{entity.getFluidAmountOut() > 0 ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.fluid")).append(" ").append(entity.getFluidOut().getFluid().getFluidType().getDescription().getString()) : Component.empty(), Component.literal(entity.getFluidAmountOut() + " mB / " + entity.getFluidCapacityOut() + " mB"), Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountOut(), entity.getFluidCapacityOut(), true))});

        if (ScreenUtil.mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.auto_dump")).append(" " + entity.isInputDump()), Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.only_while_running"))});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 116, getGuiTop() + 90, getGuiLeft() + 118, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.auto_dump")).append(" " + entity.isOutputDump()), Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.only_while_running"))});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 90, getGuiLeft() + 55, getGuiTop() + 97, pMouseX, pMouseY) || ScreenUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 90, getGuiLeft() + 137, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.dump_instantly"))});

    }

    @Override
    public void onClose() {
        this.getMenu().getTile().setChanged();
        super.onClose();
    }

    @Override
    public int getSizeX() {
        return 176;
    }

    @Override
    public int getSizeY() {
        return 194;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/decomposer_gui.png");
    }

}
