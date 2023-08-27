package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class MachineSaltMelterScreen extends MachineScreen<MachineSaltMelterContainer> {
    private final int[][] positionList = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 24, 25, 25, 26, 27, 28, 30, 31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 44, 45, 47, 48, 49, 50, 50, 51, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 22, 23, 24, 24, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50, 51, 51, 52, 52, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 21, 21, 22, 23, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 52, 52, 53, 53, 54, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 20, 20, 21, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 53, 54, 55, 55, 0, 0, 0, 0, 0},
            {0, 0, 0, 19, 19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 55, 56, 56, 0, 0, 0, 0, 0},
            {0, 0, 18, 18, 18, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 57, 57, 57, 0, 0, 0, 0},
            {0, 17, 17, 17, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 58, 58, 58, 58, 0, 0, 0},
            {0, 16, 16, 16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 59, 59, 59, 0, 0, 0},
            {0, 15, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60, 60, 60, 0, 0, 0},
            {0, 14, 14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 61, 61, 61, 0, 0, 0},
            {13, 13, 13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 62, 62, 62, 62, 0, 0},
            {12, 12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 63, 63, 63, 0, 0},
            {11, 11, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 64, 64, 64, 0, 0},
            {10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 65, 65, 65, 0, 0},
            {9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 66, 66, 66, 0, 0},
            {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 67, 67, 67, 0, 0},
            {7, 7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 68, 68, 68, 0, 0},
            {6, 6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 69, 69, 69, 0, 0},
            {5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 70, 70, 70, 0, 0},
            {4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 71, 71, 71, 0, 0},
            {3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 72, 72, 72, 0, 0},
            {2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 73, 73, 73, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 74, 74, 74, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 75, 75, 75, 75, 75, 75, 75},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 76, 76, 76, 76, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 76, 76, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0}};
    private MachineSaltMelterContainer container;

    public MachineSaltMelterScreen(MachineSaltMelterContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineSaltMelterBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 118, getGuiTop() + 85, 66, 16, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), ModFluids.FLOWING_MOLTEN_SALT.get());
        RenderUtil.renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 85, 66, 2, entity.getFluidAmountOut(), entity.getFluidCapacityOut(), ModFluids.FLOWING_MOLTEN_SALT.get());

        RenderUtil.resetGuiTextures(getTexture());
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Left Tank

        int fanBlitSize = entity.getWorkingDegree() == 0 ? 0 : (int) Math.floor(16 / ((double) entity.getWorkingDegree() / entity.getDegree()));
        blit(matrixStack, getGuiLeft() + 39, getGuiTop() + 45 + (16 - fanBlitSize), 190, 0 + (16 - fanBlitSize), 16, fanBlitSize); // Degree

        int height = entity.getMaxRecipeTime() != 0 ? 78 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 78) : 0;

        for (int xIndex = 0; xIndex < positionList.length; xIndex++) {
            for (int yIndex = 0; yIndex < positionList[xIndex].length; yIndex++) {
                if (positionList[xIndex][yIndex] <= height && positionList[xIndex][yIndex] != 0) {
                    blit(matrixStack, getGuiLeft() + 66 + yIndex, getGuiTop() + 24 + xIndex, 184, 0, 1, 1);
                }
            }
        }

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineSaltMelterBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").withStyle(RenderUtil::notoSans), pPoseStack, 8, 106, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("Salt Melting").withStyle(RenderUtil::notoSans), pPoseStack, 10, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(RenderUtil::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").withStyle(RenderUtil::notoSans), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 39, getGuiTop() + 45, getGuiLeft() + 54, getGuiTop() + 60, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getDegree() + "°C (" + FormattingUtil.formatPercentNum(entity.getDegree() - 25, entity.getWorkingDegree() - 25, false) + ")"});

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});
        if (RenderUtil.mouseInArea(getGuiLeft() + 119, getGuiTop() + 20, getGuiLeft() + 136, getGuiTop() + 85, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getFluidAmountOut() > 0 ? "Fluid: " + entity.getFluidOut().getFluid().getFluidType().getDescription().getString() : "", entity.getFluidAmountOut() + " mb / " + entity.getFluidCapacityOut() + " mb", FormattingUtil.formatPercentNum(entity.getFluidAmountOut(), entity.getFluidCapacityOut(), true)});

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
        return 198;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/salt_melter_gui.png");
    }

}
