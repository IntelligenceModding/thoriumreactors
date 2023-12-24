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
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineCrystallizerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

public class MachineCrystallizerScreen extends MachineScreen<MachineCrystallizerContainer> {
    private MachineCrystallizerContainer container;

    public MachineCrystallizerScreen(MachineCrystallizerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineCrystallizerBlockEntity tile = this.getMenu().getTile();
        // Dump Left
        addWidget(new ModButton(34, 90, 3, 8, null, () -> changeDumpMode("input"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(37, 90, 19, 8, null, () -> changeDumpMode("dumpInput"), null, tile, this, 0, 0, true));
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int x, int y) {
        super.renderBg(graphics, partialTicks, x, y);
        MachineCrystallizerBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 45, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 45, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        graphics.blit(getTexture(), getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank
        graphics.blit(getTexture(), getGuiLeft() + 37, getGuiTop() + 20, 176, 52, 4, 45); // Left Tank

        int width = entity.getMaxRecipeTime() != 0 ? 41 - (int) Math.floor(((float) entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 41) : 0;
        graphics.blit(getTexture(), getGuiLeft() + 67, getGuiTop() + 42, 184, 0, width, 10); // Process

        if (entity.isInputDump())
            graphics.blit(getTexture(), getGuiLeft() + 35, getGuiTop() + 91, 177, 8, 1, 3); // Left Dump - Green
        else graphics.blit(getTexture(), getGuiLeft() + 35, getGuiTop() + 94, 177, 11, 1, 3); // Left Dump - Red

        if (entity.getState())
            graphics.blit(getTexture(), getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else graphics.blit(getTexture(), getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY) {
        super.renderLabels(graphics, pMouseX, pMouseY);
        MachineCrystallizerBlockEntity entity = this.container.getTile();
        PoseStack pPoseStack = graphics.pose();

        ScreenUtil.drawText(Component.translatable("key.categories.inventory").withStyle(ScreenUtil::notoSans), graphics, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.crystallizer.name")).withStyle(ScreenUtil::notoSans), graphics, 10, 2, 11184810);
        ScreenUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(ScreenUtil::notoSans), graphics, 242, 2, 11184810);
        pPoseStack.popPose();
        ScreenUtil.drawCenteredText(Component.translatable(entity.getState() ? FormattingUtil.getTranslatable("machines.state.running") : FormattingUtil.getTranslatable("machines.state.idle")).withStyle(ScreenUtil::notoSans), graphics, 87, 78, 4182051);

        if (ScreenUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 20, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{entity.getFluidAmountIn() > 0 ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.fluid")).append(" ").append(entity.getFluidIn().getFluid().getFluidType().getDescription().getString()) : Component.empty(), Component.literal(entity.getFluidAmountIn() + " mB / " + entity.getFluidCapacityIn() + " mB"), Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true))});

        if (ScreenUtil.mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(graphics, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.auto_dump")).append(" " + entity.isInputDump()), Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.only_while_running"))});
        if (ScreenUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 90, getGuiLeft() + 55, getGuiTop() + 97, pMouseX, pMouseY))
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
        return 195;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/crystallizer_gui.png");
    }

}
