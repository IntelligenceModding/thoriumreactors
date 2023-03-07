package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class MachineElectrolyticSaltSeparatorScreen extends MachineScreen<MachineElectrolyticSaltSeparatorContainer> {
    private MachineElectrolyticSaltSeparatorContainer container;

    public MachineElectrolyticSaltSeparatorScreen(MachineElectrolyticSaltSeparatorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineElectrolyticSaltSeparatorBlockEntity tile = this.getMenu().getTile();
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
        MachineElectrolyticSaltSeparatorBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 45, 16, entity.getWaterIn(), entity.getMaxWaterIn(), Fluids.WATER);
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 45, 2, entity.getWaterIn(), entity.getMaxWaterIn(), Fluids.WATER);
        RenderUtil.renderFluid(getGuiLeft() + 118, getGuiTop() + 64, 23, 16, entity.getWaterOut(), entity.getMaxWaterOut(), Fluids.WATER);
        RenderUtil.renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 64, 23, 2, entity.getWaterOut(), entity.getMaxWaterOut(), Fluids.WATER);
        if (entity.getState()) for (int i = 0; i < 64; i += 16)
            RenderUtil.renderFluid(getGuiLeft() + 54 + i, getGuiTop() + 48, 1, 16, 1, 1, Fluids.WATER);

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 42, 180, 52, 2, 22); // Right Tank
        blit(matrixStack, getGuiLeft() + 37, getGuiTop() + 20, 176, 52, 4, 45); // Left Tank

        int height = entity.getMaxRecipeTime() != 0 ? 6 - (int) Math.floor((entity.getRecipeTime() / 200f) * 6) : 0;
        blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 39 + (6 - height), 185, (6 - height), 20, height); // Top Process
        blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 52, 185, 13, 20, height); // Bottom Process

        if (entity.getEnergy() > 0) {
            blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 34, 185, 19, 21, 3); // Top Pole
            blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 60, 185, 22, 21, 3); // Bottom Pole
        }

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
        MachineElectrolyticSaltSeparatorBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 102);
        RenderUtil.drawCenteredText(Component.literal("Electrolytic Salt Separator").getString(), pPoseStack, 90, 7);
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").getString(), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 20, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getWaterIn() > 0 ? "Fluid: Water" : "", entity.getWaterIn() + " mb / " + entity.getMaxWaterIn() + " mb", FormattingUtil.formatPercentNum(entity.getWaterIn(), entity.getMaxWaterIn(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 42, getGuiLeft() + 136, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getWaterOut() > 0 ? "Fluid: Water" : "", entity.getWaterOut() + " mb / " + entity.getMaxWaterOut() + " mb", FormattingUtil.formatPercentNum(entity.getWaterOut(), entity.getMaxWaterOut(), true)});

        if (RenderUtil.mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Auto Dump: " + entity.isInputDump()});
        if (RenderUtil.mouseInArea(getGuiLeft() + 116, getGuiTop() + 90, getGuiLeft() + 118, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Auto Dump: " + entity.isOutputDump()});
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
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/electrolytic_salt_separator_gui.png");
    }

}
