package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineDecomposerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

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
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineDecomposerBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 23, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 23, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());

        RenderUtil.renderFluid(getGuiLeft() + 118, getGuiTop() + 85, 66, 16, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 85, 66, 2, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 37, getGuiTop() + 42, 180, 52, 2, 22); // Left Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Right Tank

        int height = entity.getMaxRecipeTime() != 0 ? 36 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 36) : 0;
        blit(matrixStack, getGuiLeft() + 69, getGuiTop() + 51, 184, 0, height, 6); // Progress

        if (entity.isInputDump())
            blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 91, 185, 25, 1, 3); // Left Dump - Green
        else blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 94, 185, 28, 1, 3); // Left Dump - Red

        if (entity.isOutputDump())
            blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 91, 185, 25, 1, 3); // Right Dump - Green
        else blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 94, 185, 28, 1, 3); // Right Dump - Red

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 185, 25, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 185, 28, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineDecomposerBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").withStyle(RenderUtil::notoSans), pPoseStack, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("Decomposing").withStyle(RenderUtil::notoSans), pPoseStack, 10, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(RenderUtil::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").withStyle(RenderUtil::notoSans), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 42, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getFluidAmountIn() > 0 ? "Fluid: " + Component.translatable(entity.getFluidIn().getTranslationKey()).getString() : "", entity.getFluidAmountIn() + " mb / " + entity.getFluidCapacityIn() + " mb", FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 20, getGuiLeft() + 136, getGuiTop() + 85, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getFluidAmountOut() > 0 ? "Fluid: " + Component.translatable(entity.getFluidOut().getTranslationKey()).getString() : "", entity.getFluidAmountOut() + " mb / " + entity.getFluidCapacityOut() + " mb", FormattingUtil.formatPercentNum(entity.getFluidAmountOut(), entity.getFluidCapacityOut(), true)});

        if (RenderUtil.mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Auto Dump: " + entity.isInputDump(), "Only while running"});
        if (RenderUtil.mouseInArea(getGuiLeft() + 116, getGuiTop() + 90, getGuiLeft() + 118, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Auto Dump: " + entity.isOutputDump(), "Only while running"});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 90, getGuiLeft() + 55, getGuiTop() + 97, pMouseX, pMouseY) || RenderUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 90, getGuiLeft() + 137, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Dump stored liquid instantly"});

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
