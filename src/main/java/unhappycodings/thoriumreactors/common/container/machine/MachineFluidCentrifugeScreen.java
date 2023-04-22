package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineFluidCentrifugeBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class MachineFluidCentrifugeScreen extends MachineScreen<MachineFluidCentrifugeContainer> {
    private MachineFluidCentrifugeContainer container;

    public MachineFluidCentrifugeScreen(MachineFluidCentrifugeContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineFluidCentrifugeBlockEntity tile = this.getMenu().getTile();
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
        MachineFluidCentrifugeBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 85, 66, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 85, 66, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 118, getGuiTop() + 85, 66, 16, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 85, 66, 2, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), entity.getFluidOut().getFluid());

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank

        blit(matrixStack, getGuiLeft() + 37, getGuiTop() + 22, 176, 52, 4, 63); // Left Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Right Tank

        int width = entity.getMaxRecipeTime() != 0 ? 34 - (int) Math.floor(((float) entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 34) : 0;
        blit(matrixStack, getGuiLeft() + 70, getGuiTop() + 58, 184, 0, width, 9); // Process

        if (entity.isInputDump())
            blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 91, 177, 8, 1, 3); // Left Dump - Green
        else blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 94, 177, 11, 1, 3); // Left Dump - Red

        if (entity.isOutputDump())
            blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 91, 177, 8, 1, 3); // Right Dump - Green
        else blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 94, 177, 11, 1, 3); // Right Dump - Red

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineFluidCentrifugeBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 102);
        RenderUtil.drawCenteredText(Component.literal("Centrifuging").getString(), pPoseStack, getSizeX() / 2, 7);
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").getString(), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 20, getGuiLeft() + 54, getGuiTop() + 85, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getFluidAmountIn() > 0 ? "Fluid: " + entity.getFluidIn().getFluid().getFluidType().getDescription().getString() : "", entity.getFluidAmountIn() + " mb / " + entity.getFluidCapacityIn() + " mb", FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 20, getGuiLeft() + 136, getGuiTop() + 85, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getFluidAmountOut() > 0 ? "Fluid: " + entity.getFluidOut().getFluid().getFluidType().getDescription().getString() : "", entity.getFluidAmountOut() + " mb / " + entity.getFluidCapacityOut() + " mb", FormattingUtil.formatPercentNum(entity.getFluidAmountOut(), entity.getFluidCapacityOut(), true)});

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
        return 195;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/fluid_centrifuge_gui.png");
    }

}