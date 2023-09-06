package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineFluidEvaporationBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class MachineFluidEvaporatorScreen extends MachineScreen<MachineFluidEvaporatorContainer> {
    private MachineFluidEvaporatorContainer container;

    public MachineFluidEvaporatorScreen(MachineFluidEvaporatorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineFluidEvaporationBlockEntity tile = (MachineFluidEvaporationBlockEntity) getTile();
        // Dump Left
        addWidget(new ModButton(34, 90, 3, 8, null, () -> changeDumpMode("input"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(37, 90, 19, 8, null, () -> changeDumpMode("dumpInput"), null, tile, this, 0, 0, true));
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineFluidEvaporationBlockEntity entity = this.container.getTile();

        RenderUtil.renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 45, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), Fluids.WATER);
        RenderUtil.renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 45, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), Fluids.WATER);

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 37, getGuiTop() + 20, 176, 52, 4, 45); // Left Tank

        int height = entity.getMaxRecipeTime() != 0 ? 22 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 22) : 0;
        blit(matrixStack, getGuiLeft() + 71, getGuiTop() + 32 + (22 - height), 184, 15 + (22 - height), 32, height); // Process

        if (entity.isInputDump())
            blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 91, 177, 8, 1, 3); // Left Dump - Green
        else blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 94, 177, 11, 1, 3); // Left Dump - Red

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineFluidEvaporationBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.translatable("key.categories.inventory").withStyle(RenderUtil::notoSans), pPoseStack, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.fluid_evaporator.name")).withStyle(RenderUtil::notoSans), pPoseStack, 10, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(RenderUtil::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawCenteredText(Component.translatable(entity.getState() ? FormattingUtil.getTranslatable("machines.state.running") : FormattingUtil.getTranslatable("machines.state.idle")).withStyle(RenderUtil::notoSans), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 20, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new Component[]{entity.getFluidAmountIn() > 0 ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.fluid")).append(" ").append(entity.getFluidIn().getFluid().getFluidType().getDescription().getString()) : Component.empty(), Component.literal(entity.getFluidAmountIn() + " mb / " + entity.getFluidCapacityIn() + " mb"), Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true))});

        if (RenderUtil.mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.auto_dump")).append(" " + entity.isInputDump()), Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.only_while_running"))});
        if (RenderUtil.mouseInArea(getGuiLeft() + 37, getGuiTop() + 90, getGuiLeft() + 55, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new Component[]{Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.dump_instantly"))});
    }

    @Override
    public void onClose() {
        getTile().setChanged();
        super.onClose();
    }

    @Override
    public int getSizeX() {
        return 176;
    }

    @Override
    public int getSizeY() {
        return 198;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/fluid_evaporation_gui.png");
    }

}
