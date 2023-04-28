package unhappycodings.thoriumreactors.common.container.reactor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReactorControllerScreen extends AbstractContainerScreen<ReactorControllerContainer> {
    public static final ResourceLocation BUTTON_OFF = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/reactor/state_button_off.png");
    public static final ResourceLocation BUTTON_GREEN = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/reactor/state_button_green.png");
    private ReactorControllerContainer container;
    public Integer[] tempGraphValues = new Integer[93];
    public int tempIntegers = 0;

    public ReactorControllerScreen(ReactorControllerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void init() {
        super.init();
        addElements();
    }

    protected void addElements() {
        ReactorControllerBlockEntity tile = this.getMenu().getTile();

        // Dump Left
        addRenderableWidget(new ModButton(0, 0, 83, 21, BUTTON_OFF, null, null, tile, this, 83, 42, true, 0.5f));

    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int x, int y, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, x, y, pPartialTick);
        renderTooltip(pPoseStack, x, y);
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getBackgroundTexture());
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);

        matrixStack.pushPose();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        blit(matrixStack, xPos - getLeftSideX() - 2, yPos, 0, 0, getLeftSideX(), getLeftSideY(), 1024, 1024); //left
        blit(matrixStack, xPos, yPos, 227, 0, 500, 448, 1024, 1024); // mid
        blit(matrixStack, xPos + getMainSizeX() + 1, yPos, 728, 0, getRightSideX(), getRightSideY(), 1024, 1024); //right

        updateTempGraphData();
        renderGraph(matrixStack, xPos + 39, yPos + 395, tempGraphValues);

        matrixStack.popPose();

    }

    public void updateTempGraphData() {
        if (container.getTile().getLevel().getGameTime() % 5     == 0) {
            int value = container.getTile().getReactorCurrentTemperature();
            if (tempIntegers < tempGraphValues.length) {
                tempGraphValues[tempIntegers] = value;
                tempIntegers++;
            } else {
                for (int i = 1; i < tempGraphValues.length; i++)
                    tempGraphValues[i - 1] = tempGraphValues[i];
                tempGraphValues[tempGraphValues.length - 1] = value;
            }
        }
    }

    public void renderGraph(PoseStack pPoseStack, int x, int y, Integer[] list) {
        if (list[0] != null) {
            int max = 0, min = 0;
            for (int i = 0; i < list.length; i++)
                if (list[i] != null && list[i] > max) max = list[i];
            min = max;
            for (int i = 0; i < list.length; i++)
                if (list[i] != null && list[i] < min) min = list[i];

            int calculationMax = max - min;
            for (int i = 0; i < list.length; i++) {
                if (list[i] == null) break;
                int calculationCurrent = list[i] - min;
                int blitSize = (int) (((float) calculationCurrent / calculationMax) * 20);
                if (blitSize == 0 && calculationCurrent == calculationMax) {
                    blitSize = 20;
                }
                blit(pPoseStack, x + i, y + (20 - blitSize), 509, 393, 1, blitSize, 1024, 1024);
            }
        }
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        renderLeftPartTexts(pPoseStack);
        renderCenterPartTexts(pPoseStack);
        renderRightPartTexts(pPoseStack);

        renderLeftPartProgress(pPoseStack);
        renderRightPartProgress(pPoseStack);
    }

    public void renderLeftPartProgress(PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.14f, 0.14f, 0.14f);
        renderRadialProgress(pPoseStack, -1054, -38, 50); // Left
        renderRadialProgress(pPoseStack, -808, -38, 50); // Middle
        renderRadialProgress(pPoseStack, -558, -38, 50); // Right
        pPoseStack.popPose();
    }

    public void renderRightPartProgress(PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.14f, 0.14f, 0.14f);
        renderRadialProgress(pPoseStack, 1550, -38, 50); // Left
        renderRadialProgress(pPoseStack, 1797, -38, 50); // Middle
        renderRadialProgress(pPoseStack, 2044, -38, 50); // Right
        pPoseStack.popPose();
    }

    public void renderRadialProgress(PoseStack poseStack, int x, int y, int progress) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/progress/radial_progress_" + (progress >= 0 && progress <= 100 ? progress : 0) + ".png"));
        blit(poseStack, x, y, 0, 0, 222, 222);
    }

    public void renderLeftPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("MANUAL VALVE MANIPULATION").withStyle(this::notoSans), pPoseStack, -206, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("ROD INSERT").withStyle(this::notoSans), pPoseStack, -184, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Select").withStyle(this::notoSans), pPoseStack, -184, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Rod").withStyle(this::notoSans), pPoseStack, -184, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(this::notoSans), pPoseStack, -184, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("LOAD SET").withStyle(this::notoSans), pPoseStack, -135, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorCurrentLoadSet() + "").withStyle(this::notoSans), pPoseStack, -135, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(this::notoSans), pPoseStack, -135, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(this::notoSans), pPoseStack, -135, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("TEMP").withStyle(this::notoSans), pPoseStack, -85, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(container.getTile().getReactorCurrentTemperature() + "").withStyle(this::notoSans), pPoseStack, -85, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("°C").withStyle(this::notoSans), pPoseStack, -85, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(this::notoSans), pPoseStack, -85, 68, 11566128);

        RenderUtil.drawText(Component.literal("OPERATIONAL SYSTEM CHART").withStyle(this::notoSans), pPoseStack, -206, 96, 11184810);
        RenderUtil.drawCenteredText(Component.literal("REACTOR STATUS").withStyle(this::notoSans), pPoseStack, -165, 188, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawCenteredText(Component.literal("NORMAL").withStyle(this::notoSans), pPoseStack, -114, 138, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorStatus() + "%").withStyle(this::notoSans), pPoseStack, -27, 73, 16711422);
        pPoseStack.popPose();
    }

    public void renderRightPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("TURBINE GENERATOR").withStyle(this::notoSans), pPoseStack, 314, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("SPEED").withStyle(this::notoSans), pPoseStack, 336, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbineCurrentSpeed() + "").withStyle(this::notoSans), pPoseStack, 336, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RPM").withStyle(this::notoSans), pPoseStack, 336, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RUNUP").withStyle(this::notoSans), pPoseStack, 336, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("OVERFLOW").withStyle(this::notoSans), pPoseStack, 385, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbineCurrentOverflowSet() + "").withStyle(this::notoSans), pPoseStack, 385, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(this::notoSans), pPoseStack, 385, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RATE").withStyle(this::notoSans), pPoseStack, 385, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("LOAD SET").withStyle(this::notoSans), pPoseStack, 435, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbineCurrentLoadSet() + "").withStyle(this::notoSans), pPoseStack, 435, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(this::notoSans), pPoseStack, 435, 19, 16711422);
        RenderUtil.drawCenteredText(Component.literal("LOAD").withStyle(this::notoSans), pPoseStack, 435, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("COILS").withStyle(this::notoSans), pPoseStack, 390, 84, 16711422);
        RenderUtil.drawCenteredText(Component.literal("ENGAGE").withStyle(this::notoSans), pPoseStack, 352, 102, 43275);
        RenderUtil.drawCenteredText(Component.literal("DISENGAGE").withStyle(this::notoSans), pPoseStack, 419, 102, 12459309);

        RenderUtil.drawCenteredText(Component.literal("START").withStyle(this::notoSans), pPoseStack, 345, 150, entity.getReactorState() == ReactorStateEnum.STARTING ? 43275 : 2039583);
        RenderUtil.drawCenteredText(Component.literal("RUN").withStyle(this::notoSans), pPoseStack, 345, 169, entity.getReactorState() == ReactorStateEnum.RUNNING ? 11566128 : 2039583);
        RenderUtil.drawCenteredText(Component.literal("STOP").withStyle(this::notoSans), pPoseStack, 345, 188, entity.getReactorState() == ReactorStateEnum.STOP ? 12459309 : 2039583);

        RenderUtil.drawCenteredText(Component.literal("INSERT RODS").withStyle(this::notoSans), pPoseStack, 424, 150, 16711422);
        RenderUtil.drawCenteredText(Component.literal("INTO CORE").withStyle(this::notoSans), pPoseStack, 424, 159, 16711422);
        RenderUtil.drawCenteredText(Component.literal("MANUAL").withStyle(this::notoSans), pPoseStack, 424, 178, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SCRAM").withStyle(this::notoSans), pPoseStack, 424, 187, 16711422);

        RenderUtil.drawText(Component.literal("OPERATION").withStyle(this::notoSans), pPoseStack, 314, 128, 11184810);
        RenderUtil.drawText(Component.literal("EMERGENCY").withStyle(this::notoSans), pPoseStack, 393, 128, 11184810);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawCenteredText(Component.literal("").withStyle(this::notoSans), pPoseStack, -114, 138, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        RenderUtil.drawRightboundText(Component.literal("").withStyle(this::notoSans), pPoseStack, -27, 73, 16711422);
        pPoseStack.popPose();
    }

    public void renderCenterPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawCenteredText(Component.literal("Thorium Reactor").withStyle(this::notoSans), pPoseStack, -2, 1, 11184810);
        RenderUtil.drawCenteredText(Component.literal("REACTOR OVERVIEW INTERFACE").withStyle(this::notoSans), pPoseStack, 20, -39, 11184810);
        pPoseStack.popPose();

        // small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.8f, 0.8f, 0.8f);
        RenderUtil.drawRightboundText(Component.literal("OPERATING TIME").withStyle(this::notoSans), pPoseStack, 245, 16, 11184810);
        RenderUtil.drawRightboundText(Component.literal("MAIN POWER").withStyle(this::notoSans), pPoseStack, 245, 40, 11184810);
        RenderUtil.drawRightboundText(Component.literal("REACTOR STATUS").withStyle(this::notoSans), pPoseStack, 245, 69, 11184810);
        RenderUtil.drawRightboundText(Component.literal("REACTOR LOAD").withStyle(this::notoSans), pPoseStack, 245, 95, 11184810);
        RenderUtil.drawRightboundText(Component.literal("CONTAINMENT").withStyle(this::notoSans), pPoseStack, 245, 123, 11184810);
        RenderUtil.drawRightboundText(Component.literal("RADIATION").withStyle(this::notoSans), pPoseStack, 245, 149, 11184810);

        // Graphs
        RenderUtil.drawText(Component.literal("TEMP, °C").withStyle(this::notoSans), pPoseStack, -21, 189, 11184810);
        RenderUtil.drawText(Component.literal("FLOW, B/s").withStyle(this::notoSans), pPoseStack, 47, 189, 11184810);
        RenderUtil.drawText(Component.literal("SPEED, RPM").withStyle(this::notoSans), pPoseStack, 117, 189, 11184810);
        RenderUtil.drawText(Component.literal("PRESSURE, PSI").withStyle(this::notoSans), pPoseStack, 185, 189, 11184810);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorCurrentTemperature() + "").withStyle(this::notoSans), pPoseStack, 7, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbineCurrentFlow() + "").withStyle(this::notoSans), pPoseStack, 76, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbineCurrentSpeed() + "").withStyle(this::notoSans), pPoseStack, 145, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorPressure() + "").withStyle(this::notoSans), pPoseStack, 215, 202, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        RenderUtil.drawRightboundText(Component.literal(dateFormat.format(entity.getReactorRunningSince() - new Date().getTime())).withStyle(this::notoSans), pPoseStack, 196, 19, 16711422);
        RenderUtil.drawRightboundText(Component.literal(FormattingUtil.formatEnergy(entity.getTurbinePowerGeneration())).withStyle(this::notoSans), pPoseStack, 196, 38, 16711422);
        RenderUtil.drawRightboundText(Component.literal("NORMAL").withStyle(this::notoSans).withStyle(ChatFormatting.BOLD), pPoseStack, 196, 61, 43275);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorCurrentLoadSet() + "%").withStyle(this::notoSans), pPoseStack, 196, 82, 16711422);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorContainment() + "%").withStyle(this::notoSans), pPoseStack, 196, 104, 16711422);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorRadiation() + "uSV/H").withStyle(this::notoSans), pPoseStack, 196, 125, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.3f, 1.3f, 1.3f);
        RenderUtil.drawCenteredText(Component.literal("OVERVIEW").withStyle(this::notoSans), pPoseStack, -2, -6, 16711422);
        RenderUtil.drawRightboundText(Component.literal(dateFormat.format(new Date())).withStyle(this::notoSans), pPoseStack, 151, -6, 16711422);
        pPoseStack.popPose();
    }

    public Style notoSans(Style style) {
        return style.withFont(new ResourceLocation(ThoriumReactors.MOD_ID, "notosans"));
    }

    @Override
    public void onClose() {
        this.getMenu().getTile().setChanged();
        super.onClose();
    }

    public int getMainSizeX() {
        return 500;
    }

    public int getMainSizeY() {
        return 448;
    }

    public int getLeftSideX() {
        return 226;
    }

    public int getLeftSideY() {
        return 379;
    }

    public int getRightSideX() {
        return 226;
    }

    public int getRightSideY() {
        return 353;
    }

    public ResourceLocation getBackgroundTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/thorium_reactor_gui.png");
    }

    public ResourceLocation getSideTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/thorium_reactor_sidewindows.png");
    }

}
