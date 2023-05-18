package unhappycodings.thoriumreactors.common.container.reactor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.ReactorButtonTypeEnum;
import unhappycodings.thoriumreactors.common.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.config.CommonConfig;
import unhappycodings.thoriumreactors.common.container.base.editbox.ModEditBox;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.*;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerStatePacket;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReactorControllerScreen extends AbstractContainerScreen<ReactorControllerContainer> {
    private ReactorControllerContainer container;
    public Integer[] tempGraphValues = new Integer[93];
    public Integer[] flowGraphValues = new Integer[93];
    public Integer[] speedGraphValues = new Integer[93];
    public Integer[] pressureGraphValues = new Integer[93];
    public int tempIntegers = 0;
    public int flowIntegers = 0;
    public int speedIntegers = 0;
    public int pressureIntegers = 0;
    public ModEditBox set1;
    public ModEditBox set2;
    public ModEditBox set3;
    public ModEditBox[] positionInputs;
    public ModButton incrementerSpeed;
    public ModButton incrementerOverflow;
    public ModButton incrementerLoad;
    public ModButton coilEngageButton;
    public ModButton coilDisengageButton;
    public boolean leftSideButtonsAdded;
    public boolean rightSideButtonsAdded;
    public ModButton[] buttons = new ModButton[64];
    public int selectedRod = -1;
    int curMouseX = 0;
    int curMouseY = 0;

    public ReactorControllerScreen(ReactorControllerContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void init() {
        super.init();
        addElements();
    }

    public void addElements() {
        ReactorControllerBlockEntity tile = container.getTile();
        addWidget(new ModButton(-36, 20, 6, 25, null, () -> ClientConfig.showLeftReactorScreenArea.set(!ClientConfig.showLeftReactorScreenArea.get()), null, tile, this, 0, 0, true));
        addWidget(new ModButton(206, 20, 6, 25, null, () -> ClientConfig.showRightReactorScreenArea.set(!ClientConfig.showRightReactorScreenArea.get()), null, tile, this, 0, 0, true));

        for (int i = 0; i < buttons.length; i++) {
            int row = i / 8;
            int index = i;
            buttons[i] = new ModButton((64 - (row * 7)) + (i % 8 * 7), (14 + (row * 7)) + (i % 8 * 7), 4, 4, null, () -> {
                selectedRod = index;
                set3.setValue(tile.getControlRodStatus((byte) index) + "");
            }, null, tile, this, 0, 0, true);
            addWidget(buttons[i]);
        }

    }

    public void trySetValue(ReactorButtonTypeEnum typeEnum) {
        ReactorControllerBlockEntity entity = container.getTile();
        if (typeEnum == ReactorButtonTypeEnum.TEMP) PacketHandler.sendToServer(new ReactorControllerTemperaturePacket(entity.getBlockPos(), Short.parseShort(set1.getValue())));
        if (typeEnum == ReactorButtonTypeEnum.LOAD && Integer.parseInt(set2.getValue()) <= 100 && Integer.parseInt(set2.getValue()) >= 0) PacketHandler.sendToServer(new ReactorControllerLoadPacket(entity.getBlockPos(), Byte.parseByte(set2.getValue())));
        if (typeEnum == ReactorButtonTypeEnum.RODS && Integer.parseInt(set3.getValue()) <= 100 && Integer.parseInt(set3.getValue()) >= 0) PacketHandler.sendToServer(new ReactorControllerRodInsertPacket(entity.getBlockPos(), Byte.parseByte(set3.getValue()), (byte) selectedRod, hasShiftDown()));
    }

    public void setTurbineCoils(boolean value) {
        ReactorControllerBlockEntity entity = container.getTile();
        PacketHandler.sendToServer(new ReactorControllerTurbineCoilsPacket(entity.getBlockPos(), value));
    }

    public void setTurbineSpeed() {
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        boolean mouseOver = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 135) / 2, (xPos + 514 + 28) / 2, (yPos + 135 + 19) / 2, curMouseX, curMouseY);
        ReactorControllerBlockEntity entity = container.getTile();
        PacketHandler.sendToServer(new ReactorControllerTurbineSpeedPacket(entity.getBlockPos(), (short) (incrementerSpeed.isMouseOver(curMouseX, curMouseY) && mouseOver ? -1 * (hasShiftDown() ? 10 : 1) : 1 * (hasShiftDown() ? 10 : 1))));
    }

    public void setTurbineOverflow() {
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        boolean mouseOver = RenderUtil.mouseInArea((xPos + 583) / 2, (yPos + 135) / 2, (xPos + 583 + 28) / 2, (yPos + 135 + 19) / 2, curMouseX, curMouseY);
        ReactorControllerBlockEntity entity = container.getTile();
        PacketHandler.sendToServer(new ReactorControllerTurbineOverflowPacket(entity.getBlockPos(), (byte) (incrementerOverflow.isMouseOver(curMouseX, curMouseY) && mouseOver ? -1 * (hasShiftDown() ? 10 : 1) : 1 * (hasShiftDown() ? 10 : 1))));
    }

    public void setTurbineLoad() {
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        boolean mouseOver = RenderUtil.mouseInArea((xPos + 653) / 2, (yPos + 135) / 2, (xPos + 653 + 28) / 2, (yPos + 135 + 19) / 2, curMouseX, curMouseY);
        ReactorControllerBlockEntity entity = container.getTile();
        PacketHandler.sendToServer(new ReactorControllerTurbineLoadPacket(entity.getBlockPos(), (byte) (incrementerLoad.isMouseOver(curMouseX, curMouseY) && mouseOver ? -1 * (hasShiftDown() ? 10 : 1) : 1 * (hasShiftDown() ? 10 : 1))));
    }

    protected void subInit() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        for (ModEditBox editBox : positionInputs) {
            editBox.setBordered(false);
            editBox.setEditable(true);
            editBox.setMaxLength(3);
            editBox.setFilter(this::isInputValid);
            this.addWidget(editBox);
        }

        ReactorControllerBlockEntity entity = container.getTile();
        set3.setValue("0");
        set2.setValue(String.valueOf(entity.getReactorTargetLoadSet()));
        set1.setValue(String.valueOf(entity.getReactorTargetTemperature()));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (leftSideButtonsAdded) {
            for (ModEditBox editBox : positionInputs) {
                editBox.setFocus(false);
                if (editBox.isHoveredOrFocused()) {
                    setInitialFocus(editBox);
                }
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean isInputValid(String string) {
        if (string.isEmpty()) return true;
        if (string.contains(" ")) return false;
        if (string.length() == 1) return string.matches("[0-9-]");
        else return string.split("")[string.split("").length - 1].matches("^-?(\\d+$)");
    }

    public void renderRods(PoseStack matrixStack) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getBackgroundTexture());
        ReactorControllerBlockEntity tile = this.getMenu().getTile();
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);

        // Fuel rods
        for (int i = 0; i < tile.getFuelRodStatus().length; i++) {
            int row = i / 9;
            blit(matrixStack, xPos + (200 - (row * 14)) + (i % 9 * 14), yPos + (68 + (row * 14)) + (i % 9 * 14), 993, 140 - (getBlitOffset(tile.getFuelRodStatus((byte) i)) * 14), 14, 14, 1024, 1024); //left
        }

        // Control rods
        for (int i = 0; i < tile.getControlRodStatus().length; i++) {
            int row = i / 8;
            blit(matrixStack, xPos + (200 - (row * 14)) + (i % 8 * 14), yPos + (82 + (row * 14)) + (i % 8 * 14), buttons[i].isMouseOver(curMouseX, curMouseY) || selectedRod == i ? 965 : 979, 140 - (getBlitOffset(tile.getControlRodStatus((byte) i)) * 14), 14, 14, 1024, 1024); //left
        }

    }

    public int getBlitOffset(int status) {
        int value = 0;
        for (int i = 0; i <= 100; i = i + 10) {
            if (status >= i) {
                value = i;
            }
        }
        return value / 10;
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, getBackgroundTexture());
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        long ticks = container.getTile().getLevel().getGameTime();
        boolean mouseOverSetL = RenderUtil.mouseInArea((xPos - 72) / 2, (yPos + 147) / 2, (xPos - 72 + 52) / 2, (yPos + 147 + 21) / 2, x, y);
        boolean mouseOverSetM = RenderUtil.mouseInArea((xPos - 141) / 2, (yPos + 147) / 2, (xPos - 141 + 52) / 2, (yPos + 147 + 21) / 2, x, y);
        boolean mouseOverSetR = RenderUtil.mouseInArea((xPos - 211) / 2, (yPos + 147) / 2, (xPos - 211 + 52) / 2, (yPos + 147 + 21) / 2, x, y);
        boolean mouseOverStart = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 261) / 2, (xPos + 514 + 83) / 2, (yPos + 261 + 21) / 2, x, y);
        boolean mouseOverRunning = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 289) / 2, (xPos + 514 + 83) / 2, (yPos + 289 + 21) / 2, x, y);
        boolean mouseOverStop = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 316) / 2, (xPos + 514 + 83) / 2, (yPos + 316 + 21) / 2, x, y);
        boolean mouseOverIncrementSpeed = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 135) / 2, (xPos + 514 + 28) / 2, (yPos + 135 + 19) / 2, x, y);
        boolean mouseOverIncrementOverflow = RenderUtil.mouseInArea((xPos + 583) / 2, (yPos + 135) / 2, (xPos + 583 + 28) / 2, (yPos + 135 + 19) / 2, x, y);
        boolean mouseOverIncrementLoad = RenderUtil.mouseInArea((xPos + 653) / 2, (yPos + 135) / 2, (xPos + 653 + 28) / 2, (yPos + 135 + 19) / 2, x, y);

        matrixStack.pushPose();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        blit(matrixStack, xPos, yPos, 227, 0, 500, 448, 1024, 1024); // mid

        if (leftSideButtonsAdded) {
            blit(matrixStack, xPos - getLeftSideX() - 2, yPos, 0, 0, getLeftSideX(), getLeftSideY(), 1024, 1024); //left

            // set buttons
            blit(matrixStack, xPos - 72, yPos + 148, 83, mouseOverSetL ? 512 : 491, 83, 21, 1024, 1024); // left left bottom
            blit(matrixStack, xPos - 141, yPos + 148, 83, mouseOverSetM ? 512 : 491, 83, 21, 1024, 1024); // left middle bottom
            blit(matrixStack, xPos - 211, yPos + 148, 83, mouseOverSetR ? 512 : 491, 83, 21, 1024, 1024); // left right bottom

            // blinking left side
            blit(matrixStack, xPos - 49, yPos + 293, 1017, ticks % 10 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right top
            blit(matrixStack, xPos - 47, yPos + 285, 1017, ticks % 11 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right bottom
            blit(matrixStack, xPos - 98, yPos + 285, 1017, 155, 6, 5, 1024, 1024); // mid right top
            blit(matrixStack, xPos - 100, yPos + 293, 1017, ticks % 6 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right bottom
            blit(matrixStack, xPos - 190, yPos + 248, 1017, ticks % 2 == 0 ? 160 : 155, 6, 5, 1024, 1024); // left top
            blit(matrixStack, xPos - 111, yPos + 233, 1017, ticks % 16 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid top
            blit(matrixStack, xPos - 89, yPos + 253, 1017, ticks % 20 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right
            blit(matrixStack, xPos - 127, yPos + 302, 1017, ticks % 18 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid bottom
            blit(matrixStack, xPos - 183, yPos + 297, 1017, 160, 6, 5, 1024, 1024); // left left bottom
            blit(matrixStack, xPos - 174, yPos + 297, 1017, ticks % 13 == 0 ? 160 : 155, 6, 5, 1024, 1024); // left mid left bottom
            blit(matrixStack, xPos - 166, yPos + 297, 1017, 155, 6, 5, 1024, 1024); // left mid right bottom
            blit(matrixStack, xPos - 158, yPos + 297, 1017, ticks % 8 == 0 ? 155 : 160, 6, 5, 1024, 1024); // left right bottom

            blit(matrixStack, xPos - 205, yPos + 340, ticks % 20 < 10 ? 991 : 1003, 154, 12, 12, 1024, 1024); // left right bottom
            blit(matrixStack, xPos + 368, yPos + 181, ticks % 20 < 10 ? 991 : 1003, 154 + 12, 12, 12, 1024, 1024); // left right bottom
        }

        if (rightSideButtonsAdded) {
            blit(matrixStack, xPos + getMainSizeX() + 1, yPos, 728, 0, getRightSideX(), getRightSideY(), 1024, 1024); //right

            // incrementer buttons
            blit(matrixStack, xPos + 515, yPos + 136, 166, incrementerSpeed.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementSpeed ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom
            blit(matrixStack, xPos + 584, yPos + 136, 166, incrementerOverflow.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementOverflow ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom
            blit(matrixStack, xPos + 654, yPos + 136, 166, incrementerLoad.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementLoad ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom

            // state buttons right
            if (container.getTile().getReactorState() != ReactorStateEnum.STARTING) blit(matrixStack, xPos + 514, yPos + 261, 83, mouseOverStart ? 470 : 449, 83, 21, 1024, 1024); // left right bottom
            if (container.getTile().getReactorState() != ReactorStateEnum.RUNNING) blit(matrixStack, xPos + 514, yPos + 289, 0, mouseOverRunning ? 512 : 491, 83, 21, 1024, 1024); // left right bottom
            if (container.getTile().getReactorState() != ReactorStateEnum.STOP) blit(matrixStack, xPos + 514, yPos + 316, 0, mouseOverStop ? 470 : 449, 83, 21, 1024, 1024); // left right bottom

            // coil engaged buttons right
            if (container.getTile().isTurbineCoilsEngaged()) blit(matrixStack, xPos + 619, yPos + 195, 0, coilDisengageButton.isMouseOver(curMouseX, curMouseY) ? 470 : 449, 83, 21, 1024, 1024); // left right bottom
            else blit(matrixStack, xPos + 524, yPos + 195, 83, coilEngageButton.isMouseOver(curMouseX, curMouseY) ? 470 : 449, 83, 21, 1024, 1024); // left right bottom

        }

        // middle graphs
        updateTempGraphData();
        updateFlowGraphData();
        updateSpeedGraphData();
        updatePressureGraphData();
        renderGraph(matrixStack, xPos + 39, yPos + 395, tempGraphValues); // temp
        renderGraph(matrixStack, xPos + 149, yPos + 395, flowGraphValues); // flow
        renderGraph(matrixStack, xPos + 259, yPos + 395, speedGraphValues); // speed
        renderGraph(matrixStack, xPos + 369, yPos + 395, pressureGraphValues); // pressure

        // middle rods grit
        renderRods(matrixStack);

        matrixStack.popPose();

    }

    public void sendChangedPacket() {
        PacketHandler.sendToServer(new ReactorControllerChangedPacket(this.getMenu().getTile().getBlockPos()));
    }

    private void changeReactorState(ReactorStateEnum state) {
        PacketHandler.sendToServer(new ReactorControllerStatePacket(this.getMenu().getTile().getBlockPos(), state));
        sendChangedPacket();
    }

    private void scram() {
        PacketHandler.sendToServer(new ReactorControllerScramPacket(this.getMenu().getTile().getBlockPos()));
        sendChangedPacket();
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int x, int y, float pPartialTick) {
        curMouseX = x;
        curMouseY = y;
        renderBackground(pPoseStack);
        super.render(pPoseStack, x, y, pPartialTick);
        renderTooltip(pPoseStack, x, y);

        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        ReactorControllerBlockEntity tile = this.getMenu().getTile();
        if (ClientConfig.showRightReactorScreenArea.get() && !rightSideButtonsAdded) {
            // Incrementer buttons
            incrementerSpeed = new ModButton(220, 38, 31, 11, null, () -> setTurbineSpeed(), null, tile, this, 0, 0, true);
            incrementerOverflow = new ModButton(254, 38, 31, 11, null, () -> setTurbineOverflow(), null, tile, this, 0, 0, true);
            incrementerLoad = new ModButton(289, 38, 31, 11, null, () -> setTurbineLoad(), null, tile, this, 0, 0, true);
            addWidget(incrementerSpeed);
            addWidget(incrementerOverflow);
            addWidget(incrementerLoad);

            // Coil engage buttons
            coilEngageButton = new ModButton(225, 69, 41, 11, null, () -> setTurbineCoils(true), null, tile, this, 0, 0, true);
            coilDisengageButton = new ModButton(225 + 48, 69, 41, 11, null, () -> setTurbineCoils(false), null, tile, this, 0, 0, true);
            addWidget(coilEngageButton);
            addWidget(coilDisengageButton);

            // State buttons
            addWidget(new ModButton(220, 101, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STARTING), null, tile, this, 0, 0, true));
            addWidget(new ModButton(220, 115, 41, 11, null, () -> changeReactorState(ReactorStateEnum.RUNNING), null, tile, this, 0, 0, true));
            addWidget(new ModButton(220, 128, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STOP), null, tile, this, 0, 0, true));

            // Scram button
            addWidget(new ModButton(276, 120, 41, 19, null, () -> scram(), null, tile, this, 0, 0, true));

            rightSideButtonsAdded = true;
        } else if (rightSideButtonsAdded && !ClientConfig.showRightReactorScreenArea.get()) {
            clearWidgets();
            addElements();
            rightSideButtonsAdded = false;
            leftSideButtonsAdded = false;
        }

        if (ClientConfig.showLeftReactorScreenArea.get() && !leftSideButtonsAdded) {
            // Set buttons
            addWidget(new ModButton(- 72, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.TEMP), null, tile, this, 0, 0, true)); // Right
            addWidget(new ModButton(- 108, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.LOAD), null, tile, this, 0, 0, true)); // Middle
            addWidget(new ModButton(- 142, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.RODS), null, tile, this, 0, 0, true)); // Left

            // Edit boxes left
            set1 = new ModEditBox(font, leftPos - 68, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            set2 = new ModEditBox(font, leftPos - 102, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            set3 = new ModEditBox(font, leftPos - 138, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            positionInputs = new ModEditBox[]{set1, set2, set3};
            for (ModEditBox modEditBox : positionInputs) addRenderableWidget(modEditBox);
            subInit();

            leftSideButtonsAdded = true;
        } else if (leftSideButtonsAdded && !ClientConfig.showLeftReactorScreenArea.get()) {
            clearWidgets();
            addElements();
            rightSideButtonsAdded = false;
            leftSideButtonsAdded = false;
        }
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        ReactorControllerBlockEntity entity = container.getTile();
        renderCenterPartTexts(pPoseStack);

        if (leftSideButtonsAdded) {
            renderLeftPartProgress(pPoseStack);
            renderLeftPartTexts(pPoseStack);

            if (set1.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Reactor Temp", "25°C - 999°C"});
            if (set2.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Load level", "0% - 100%"});
            if (set3.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Insert Level", "0% - 100%"});

        }
        if (rightSideButtonsAdded) {
            renderRightPartProgress(pPoseStack);
            renderRightPartTexts(pPoseStack);

            if (incrementerSpeed.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Turbine Speed", "Click ±1", "Sh+Click ±10"});
            if (incrementerOverflow.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Turbine Overflow", "Click ±1", "Shift+Click ±10"});
            if (incrementerLoad.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Turbine Load", "Click ±1", "Shift+Click ±10"});

            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].isMouseOver(pMouseX, pMouseY)) {
                    appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getControlRodStatus((byte) i) + "%"});
                }
            }

        }

    }

    public void renderLeftPartProgress(PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.14f, 0.14f, 0.14f);
        ReactorControllerBlockEntity entity = container.getTile();
        renderRadialProgress(pPoseStack, -1054, -38, selectedRod == -1 ? 0 : entity.getControlRodStatus((byte) selectedRod)); // Left
        renderRadialProgress(pPoseStack, -808, -38, entity.getFuelRodStatus((byte) 0)); // Middle
        renderRadialProgress(pPoseStack, -558, -38, (int) Math.floor((float) container.getTile().getReactorCurrentTemperature()/container.getTile().getReactorTargetTemperature()*100)); // Right
        pPoseStack.popPose();
    }

    public void renderRightPartProgress(PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.14f, 0.14f, 0.14f);
        renderRadialProgress(pPoseStack, 1550, -38, (int) Math.floor((float) container.getTile().getTurbineCurrentSpeed()/container.getTile().getTurbineTargetSpeed()*100)); // Left
        renderRadialProgress(pPoseStack, 1797, -38, (int) Math.floor((float) container.getTile().getTurbineCurrentOverflowSet()/container.getTile().getTurbineTargetOverflowSet()*100)); // Middle
        renderRadialProgress(pPoseStack, 2044, -38, (int) Math.floor((float) container.getTile().getTurbineCurrentLoadSet()/container.getTile().getTurbineTargetLoadSet()*100)); // Right
        pPoseStack.popPose();
    }

    public void renderRadialProgress(PoseStack poseStack, int x, int y, int progress) {
        RenderSystem.setShaderTexture(0, new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/progress/radial_progress_" + (progress >= 0 && progress <= 100 ? progress : 0) + ".png"));
        blit(poseStack, x, y, 0, 0, 222, 222);
    }

    public void renderLeftPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal((byte) selectedRod == -1 ? "0" : entity.getControlRodStatus((byte) selectedRod) + "%").withStyle(RenderUtil::notoSans), pPoseStack, -323, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getFuelRodStatus((byte) 0) + "%").withStyle(RenderUtil::notoSans), pPoseStack, -236, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorTargetTemperature() + "°C").withStyle(RenderUtil::notoSans), pPoseStack, -149, 48, 16711422);
        pPoseStack.popPose();

        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("MANUAL VALVE MANIPULATION").withStyle(RenderUtil::notoSans), pPoseStack, -206, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("ROD INSERT").withStyle(RenderUtil::notoSans), pPoseStack, -184, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(selectedRod == -1 ? "Select" : "Rod").withStyle(RenderUtil::notoSans), pPoseStack, -184, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal(selectedRod == -1 ? "Rod" : "#" + selectedRod).withStyle(RenderUtil::notoSans), pPoseStack, -184, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -184, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("LOAD SET").withStyle(RenderUtil::notoSans), pPoseStack, -135, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getFuelRodStatus((byte) 0) + "").withStyle(RenderUtil::notoSans), pPoseStack, -135, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(RenderUtil::notoSans), pPoseStack, -135, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -135, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("TEMP").withStyle(RenderUtil::notoSans), pPoseStack, -85, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorCurrentTemperature() + "").withStyle(RenderUtil::notoSans), pPoseStack, -85, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("°C").withStyle(RenderUtil::notoSans), pPoseStack, -85, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -85, 68, 11566128);

        RenderUtil.drawText(Component.literal("OPERATIONAL SYSTEM CHART").withStyle(RenderUtil::notoSans), pPoseStack, -206, 96, 11184810);
        RenderUtil.drawCenteredText(Component.literal("REACTOR STATUS").withStyle(RenderUtil::notoSans), pPoseStack, -165, 193, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawCenteredText(Component.literal("NORMAL").withStyle(RenderUtil::notoSans), pPoseStack, -114, 141, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorStatus() + "%").withStyle(RenderUtil::notoSans), pPoseStack, -27, 75, 16711422);
        pPoseStack.popPose();
    }

    public void renderRightPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetSpeed())).withStyle(RenderUtil::notoSans), pPoseStack, 587, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetOverflowSet()) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 674, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetLoadSet()) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 761, 48, 16711422);
        pPoseStack.popPose();

        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("TURBINE GENERATOR").withStyle(RenderUtil::notoSans), pPoseStack, 314, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("SPEED").withStyle(RenderUtil::notoSans), pPoseStack, 336, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineCurrentSpeed())).withStyle(RenderUtil::notoSans), pPoseStack, 336, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RPM").withStyle(RenderUtil::notoSans), pPoseStack, 336, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RUNUP").withStyle(RenderUtil::notoSans), pPoseStack, 336, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("OVERFLOW").withStyle(RenderUtil::notoSans), pPoseStack, 385, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineCurrentOverflowSet())).withStyle(RenderUtil::notoSans), pPoseStack, 385, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(RenderUtil::notoSans), pPoseStack, 385, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("RATE").withStyle(RenderUtil::notoSans), pPoseStack, 385, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("LOAD SET").withStyle(RenderUtil::notoSans), pPoseStack, 435, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineCurrentLoadSet())).withStyle(RenderUtil::notoSans), pPoseStack, 435, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(RenderUtil::notoSans), pPoseStack, 435, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("LOAD").withStyle(RenderUtil::notoSans), pPoseStack, 435, 44, 16711422);

        RenderUtil.drawCenteredText(Component.literal("COILS").withStyle(RenderUtil::notoSans), pPoseStack, 385, 84, 16711422);
        RenderUtil.drawCenteredText(Component.literal("ENGAGE").withStyle(RenderUtil::notoSans), pPoseStack, 352, 102, entity.isTurbineCoilsEngaged() ? 2039583 : 43275);
        RenderUtil.drawCenteredText(Component.literal("DISENGAGE").withStyle(RenderUtil::notoSans), pPoseStack, 419, 102, !entity.isTurbineCoilsEngaged() ? 2039583 : 12459309);

        RenderUtil.drawCenteredText(Component.literal("START").withStyle(RenderUtil::notoSans), pPoseStack, 345, 149, entity.getReactorState() == ReactorStateEnum.STARTING ? 2039583 : 43275);
        RenderUtil.drawCenteredText(Component.literal("RUN").withStyle(RenderUtil::notoSans), pPoseStack, 345, 169, entity.getReactorState() == ReactorStateEnum.RUNNING ? 2039583 : 11566128);
        RenderUtil.drawCenteredText(Component.literal("STOP").withStyle(RenderUtil::notoSans), pPoseStack, 345, 188, entity.getReactorState() == ReactorStateEnum.STOP ? 2039583 : 12459309);

        RenderUtil.drawCenteredText(Component.literal("INSERT RODS").withStyle(RenderUtil::notoSans), pPoseStack, 424, 150, 16711422);
        RenderUtil.drawCenteredText(Component.literal("INTO CORE").withStyle(RenderUtil::notoSans), pPoseStack, 424, 159, 16711422);
        RenderUtil.drawCenteredText(Component.literal("MANUAL").withStyle(RenderUtil::notoSans), pPoseStack, 424, 178, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SCRAM").withStyle(RenderUtil::notoSans), pPoseStack, 424, 187, 16711422);

        RenderUtil.drawText(Component.literal("OPERATION").withStyle(RenderUtil::notoSans), pPoseStack, 314, 128, 11184810);
        RenderUtil.drawText(Component.literal("EMERGENCY").withStyle(RenderUtil::notoSans), pPoseStack, 393, 128, 11184810);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawCenteredText(Component.literal("").withStyle(RenderUtil::notoSans), pPoseStack, -114, 138, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        RenderUtil.drawRightboundText(Component.literal("").withStyle(RenderUtil::notoSans), pPoseStack, -27, 73, 16711422);
        pPoseStack.popPose();
    }

    public void renderCenterPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawCenteredText(Component.literal("Thorium Reactor").withStyle(RenderUtil::notoSans), pPoseStack, -2, 1, 11184810);
        RenderUtil.drawCenteredText(Component.literal("REACTOR OVERVIEW INTERFACE").withStyle(RenderUtil::notoSans), pPoseStack, 20, -39, 11184810);
        pPoseStack.popPose();

        // small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.8f, 0.8f, 0.8f);
        RenderUtil.drawRightboundText(Component.literal("OPERATING TIME").withStyle(RenderUtil::notoSans), pPoseStack, 245, 16, 11184810);
        RenderUtil.drawRightboundText(Component.literal("MAIN POWER").withStyle(RenderUtil::notoSans), pPoseStack, 245, 40, 11184810);
        RenderUtil.drawRightboundText(Component.literal("REACTOR STATUS").withStyle(RenderUtil::notoSans), pPoseStack, 245, 69, 11184810);
        RenderUtil.drawRightboundText(Component.literal("REACTOR LOAD").withStyle(RenderUtil::notoSans), pPoseStack, 245, 95, 11184810);
        RenderUtil.drawRightboundText(Component.literal("CONTAINMENT").withStyle(RenderUtil::notoSans), pPoseStack, 245, 123, 11184810);
        RenderUtil.drawRightboundText(Component.literal("RADIATION").withStyle(RenderUtil::notoSans), pPoseStack, 245, 149, 11184810);

        // Graphs
        RenderUtil.drawText(Component.literal("TEMP, °C").withStyle(RenderUtil::notoSans), pPoseStack, -21, 189, 11184810);
        RenderUtil.drawText(Component.literal("FLOW, B/s").withStyle(RenderUtil::notoSans), pPoseStack, 47, 189, 11184810);
        RenderUtil.drawText(Component.literal("SPEED, RPM").withStyle(RenderUtil::notoSans), pPoseStack, 117, 189, 11184810);
        RenderUtil.drawText(Component.literal("PRESSURE, PSI").withStyle(RenderUtil::notoSans), pPoseStack, 185, 189, 11184810);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getReactorCurrentTemperature())).withStyle(RenderUtil::notoSans), pPoseStack, 7, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineCurrentFlow())).withStyle(RenderUtil::notoSans), pPoseStack, 76, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineCurrentSpeed())).withStyle(RenderUtil::notoSans), pPoseStack, 145, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getReactorPressure())).withStyle(RenderUtil::notoSans), pPoseStack, 215, 202, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        RenderUtil.drawRightboundText(Component.literal(dateFormat.format(entity.getReactorRunningSince() - new Date().getTime())).withStyle(RenderUtil::notoSans), pPoseStack, 196, 19, 16711422);
        RenderUtil.drawRightboundText(Component.literal(FormattingUtil.formatEnergy(entity.getTurbinePowerGeneration())).withStyle(RenderUtil::notoSans), pPoseStack, 196, 38, 16711422);
        RenderUtil.drawRightboundText(Component.literal("NORMAL").withStyle(RenderUtil::notoSans).withStyle(ChatFormatting.BOLD), pPoseStack, 196, 61, 43275);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorCurrentLoadSet() + "%").withStyle(RenderUtil::notoSans), pPoseStack, 196, 82, 16711422);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorContainment() + "%").withStyle(RenderUtil::notoSans), pPoseStack, 196, 104, 16711422);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorRadiation() + "uSV/H").withStyle(RenderUtil::notoSans), pPoseStack, 196, 125, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.3f, 1.3f, 1.3f);
        RenderUtil.drawCenteredText(Component.literal("OVERVIEW").withStyle(RenderUtil::notoSans), pPoseStack, -2, -6, 16711422);
        RenderUtil.drawRightboundText(Component.literal(dateFormat.format(new Date())).withStyle(RenderUtil::notoSans), pPoseStack, 151, -6, 16711422);
        pPoseStack.popPose();
    }

    public void updateTempGraphData() {
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

    public void updateFlowGraphData() {
        int value = container.getTile().getTurbineCurrentFlow();
        if (flowIntegers < flowGraphValues.length) {
            flowGraphValues[flowIntegers] = value;
            flowIntegers++;
        } else {
            for (int i = 1; i < flowGraphValues.length; i++)
                flowGraphValues[i - 1] = flowGraphValues[i];
            flowGraphValues[flowGraphValues.length - 1] = value;
        }
    }

    public void updateSpeedGraphData() {
        int value = container.getTile().getTurbineCurrentSpeed();
        if (speedIntegers < speedGraphValues.length) {
            speedGraphValues[speedIntegers] = value;
            speedIntegers++;
        } else {
            for (int i = 1; i < speedGraphValues.length; i++)
                speedGraphValues[i - 1] = speedGraphValues[i];
            speedGraphValues[speedGraphValues.length - 1] = value;
        }
    }

    public void updatePressureGraphData() {
        int value = 30;
        if (pressureIntegers < pressureGraphValues.length) {
            pressureGraphValues[pressureIntegers] = value;
            pressureIntegers++;
        } else {
            for (int i = 1; i < pressureGraphValues.length; i++)
                pressureGraphValues[i - 1] = pressureGraphValues[i];
            pressureGraphValues[pressureGraphValues.length - 1] = value;
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
                blit(pPoseStack, x + i, y + (20 - blitSize), 1022, 167, 1, blitSize, 1024, 1024);
            }
        }
    }

    public void appendHoverText(PoseStack poseStack, int x, int y, String[] texts) {
        List<Component> list = new ArrayList<>();
        for (String text : texts)
            if (!text.equals("")) list.add(Component.literal(text).withStyle(RenderUtil::notoSans));
        this.renderComponentTooltip(poseStack, list, x - leftPos, y - topPos);
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
