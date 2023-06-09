package unhappycodings.thoriumreactors.common.container.reactor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.editbox.ModEditBox;
import unhappycodings.thoriumreactors.common.enums.ReactorButtonTypeEnum;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.*;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerStatePacket;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ReactorControllerScreen extends AbstractContainerScreen<ReactorControllerContainer> {
    private ReactorControllerContainer container;
    public Float[] tempGraphValues = new Float[93];
    public Float[] flowGraphValues = new Float[93];
    public Float[] speedGraphValues = new Float[93];
    public Float[] pressureGraphValues = new Float[93];
    public boolean leftSideButtonsAdded;
    public boolean rightSideButtonsAdded;
    public int tempIntegers = 0;
    public int flowIntegers = 0;
    public int speedIntegers = 0;
    public int pressureIntegers = 0;
    public int selectedRod = -1;
    public int curMouseX = 0;
    public int curMouseY = 0;

    public boolean lastScramState;

    public ModButton[] controlRodsButtons = new ModButton[64];
    public ModButton incrementerSpeed;
    public ModButton incrementerOverflow;
    public ModButton incrementerLoad;
    public ModButton coilEngageButton;
    public ModButton coilDisengageButton;
    public ModButton scramButton;
    public ModButton rodSetButton;
    public ModButton loadSetButton;
    public ModButton tempSetButton;

    public ModEditBox[] positionInputs;
    public ModEditBox inputBox1;
    public ModEditBox inputBox2;
    public ModEditBox inputBox3;

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
        ReactorControllerBlockEntity entity = container.getTile();
        addWidget(new ModButton(-36, 20, 6, 25, null, () -> ClientConfig.showLeftReactorScreenArea.set(!ClientConfig.showLeftReactorScreenArea.get()), null, entity, this, 0, 0, true));
        addWidget(new ModButton(206, 20, 6, 25, null, () -> ClientConfig.showRightReactorScreenArea.set(!ClientConfig.showRightReactorScreenArea.get()), null, entity, this, 0, 0, true));

        for (int i = 0; i < controlRodsButtons.length; i++) {
            int row = i / 8;
            int index = i;
            controlRodsButtons[i] = new ModButton((64 - (row * 7)) + (i % 8 * 7), (14 + (row * 7)) + (i % 8 * 7), 4, 4, null, () -> {
                selectedRod = index;
                inputBox3.setValue(entity.getControlRodStatus((byte) index) + "");
            }, null, entity, this, 0, 0, true);
            addWidget(controlRodsButtons[i]);
        }

    }

    public void trySetValue(ReactorButtonTypeEnum typeEnum) {
        ReactorControllerBlockEntity entity = container.getTile();
        if (typeEnum == ReactorButtonTypeEnum.TEMP) PacketHandler.sendToServer(new ReactorControllerTemperaturePacket(entity.getBlockPos(), Short.parseShort(inputBox1.getValue())));
        if (typeEnum == ReactorButtonTypeEnum.LOAD && Integer.parseInt(inputBox2.getValue()) <= 100 && Integer.parseInt(inputBox2.getValue()) >= 0) PacketHandler.sendToServer(new ReactorControllerLoadPacket(entity.getBlockPos(), Byte.parseByte(inputBox2.getValue())));
        if (typeEnum == ReactorButtonTypeEnum.RODS && Integer.parseInt(inputBox3.getValue()) <= 100 && Integer.parseInt(inputBox3.getValue()) >= 0) PacketHandler.sendToServer(new ReactorControllerRodInsertPacket(entity.getBlockPos(), Byte.parseByte(inputBox3.getValue()), (byte) selectedRod, hasShiftDown()));
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

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        super.resize(pMinecraft, pWidth, pHeight);
        if (mouseReleased(curMouseX, curMouseY, 0)) {
            PacketHandler.sendToServer(new ReactorOpenContainerPacket(container.getTile().getBlockPos()));
        }
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        ReactorControllerBlockEntity entity = this.getMenu().getTile();
        for (int i = 0; i < controlRodsButtons.length; i++) {
            if (controlRodsButtons[i].isMouseOver(pMouseX, pMouseY)) {
                int inputValue = entity.getControlRodStatus((byte) i);
                byte finalValue = (byte) (inputValue + (hasShiftDown() ? pDelta * 10 : pDelta));
                if (finalValue <= 100 && finalValue >= 0) {
                    if (i == selectedRod) inputBox3.setValue(String.valueOf(finalValue));
                    PacketHandler.sendToServer(new ReactorControllerRodInsertPacket(entity.getBlockPos(), finalValue, (byte) i, false));
                    minecraft.getSoundManager().play(SimpleSoundInstance.forUI(ModSounds.DIGITALBEEP_0.get(), pDelta > 0 ? 1F : 0.99f));
                }
            }
        }
        return super.mouseScrolled(pMouseX, pMouseY, pDelta);
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
        inputBox3.setValue("0");
        inputBox2.setValue(String.valueOf(entity.getReactorTargetLoadSet()));
        inputBox1.setValue(String.valueOf(entity.getReactorTargetTemperature()));
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
        ReactorControllerBlockEntity entity = this.getMenu().getTile();
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);

        // Fuel rods
        for (int i = 0; i < entity.getFuelRodStatus().length; i++) {
            int row = i / 9;
            blit(matrixStack, xPos + (200 - (row * 14)) + (i % 9 * 14), yPos + (68 + (row * 14)) + (i % 9 * 14), 993, 140 - (getBlitOffset(entity.getFuelRodStatus((byte) i)) * 14), 14, 14, 1024, 1024); //left
        }

        // Control rods
        for (int i = 0; i < entity.getControlRodStatus().length; i++) {
            int row = i / 8;
            blit(matrixStack, xPos + (200 - (row * 14)) + (i % 8 * 14), yPos + (82 + (row * 14)) + (i % 8 * 14), controlRodsButtons[i].isMouseOver(curMouseX, curMouseY) || selectedRod == i ? 965 : 979, 140 - (getBlitOffset(entity.getControlRodStatus((byte) i)) * 14) + (entity.isScrammed() && entity.getLevel().getGameTime() % 20 > 10 ? 200 : 0), 14, 14, 1024, 1024); //left
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
        ReactorControllerBlockEntity entity = this.container.getTile();
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

            // turbine
            blit(matrixStack, xPos - 49, yPos + 293, (entity.isScrammed() ? 957 : 1017) + (entity.isTurbineActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isTurbineActive() ? 155 : ticks % 10 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right top
            blit(matrixStack, xPos - 47, yPos + 285, (entity.isScrammed() ? 957 : 1017) + (entity.isTurbineActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isTurbineActive() ? 155 : ticks % 11 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right bottom
            blit(matrixStack, xPos - 98, yPos + 285, (entity.isScrammed() ? 957 : 1017) + (entity.isTurbineActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isTurbineActive() ? 155 : 155, 6, 5, 1024, 1024); // mid right top
            blit(matrixStack, xPos - 100, yPos + 293, (entity.isScrammed() ? 957 : 1017) + (entity.isTurbineActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isTurbineActive() ? 155 : ticks % 6 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right bottom
            blit(matrixStack, xPos - 89, yPos + 253, (entity.isScrammed() ? 957 : 1017) + (entity.isTurbineActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isTurbineActive() ? 155 : ticks % 20 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right

            // reactor
            blit(matrixStack, xPos - 190, yPos + 248, (entity.isScrammed() ? 957 : 1017) + (entity.isReactorActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isReactorActive() ? 155 : ticks % 2 == 0 ? 160 : 155, 6, 5, 1024, 1024); // left top

            blit(matrixStack, xPos - 183, yPos + 297, (entity.isScrammed() ? 957 : 1017) + (entity.isReactorActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isReactorActive() ? 155 : 160, 6, 5, 1024, 1024); // left left bottom
            blit(matrixStack, xPos - 174, yPos + 297, (entity.isScrammed() ? 957 : 1017) + (entity.isReactorActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isReactorActive() ? 155 : ticks % 13 == 0 ? 160 : 155, 6, 5, 1024, 1024); // left mid left bottom
            blit(matrixStack, xPos - 166, yPos + 297, (entity.isScrammed() ? 957 : 1017) + (entity.isReactorActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isReactorActive() ? 155 : 155, 6, 5, 1024, 1024); // left mid right bottom
            blit(matrixStack, xPos - 158, yPos + 297, (entity.isScrammed() ? 957 : 1017) + (entity.isReactorActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isReactorActive() ? 155 : ticks % 8 == 0 ? 155 : 160, 6, 5, 1024, 1024); // left right bottom

            // exchanger
            blit(matrixStack, xPos - 111, yPos + 233, (entity.isScrammed() ? 957 : 1017) + (entity.isExchangerActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isExchangerActive() ? 155 : ticks % 16 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid top
            blit(matrixStack, xPos - 127, yPos + 302, (entity.isScrammed() ? 957 : 1017) + (entity.isExchangerActive() || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !entity.isExchangerActive() ? 155 : ticks % 18 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid bottom

            // blinking indicators
            blit(matrixStack, xPos - 205, yPos + 340, (ticks % 20 < 10 ? 991 : 1003) + (entity.isScrammed() ? -24 : 0), 154, 12, 12, 1024, 1024); // left right bottom
            blit(matrixStack, xPos + 368, yPos + 181, (ticks % 20 < 10 ? 991 : 1003) + (entity.isScrammed() ? -24 : 0), 154 + 12, 12, 12, 1024, 1024); // left right bottom
        }

        if (rightSideButtonsAdded) {
            blit(matrixStack, xPos + getMainSizeX() + 1, yPos, 728, 0, getRightSideX(), getRightSideY(), 1024, 1024); //right

            // incrementer buttons
            blit(matrixStack, xPos + 515, yPos + 136, 166, incrementerSpeed.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementSpeed ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom
            blit(matrixStack, xPos + 584, yPos + 136, 166, incrementerOverflow.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementOverflow ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom
            blit(matrixStack, xPos + 654, yPos + 136, 166, incrementerLoad.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementLoad ? 449 + 19 : 449 + 38) : 449, 58, 19, 1024, 1024); // right incrementer speed bottom

            // state buttons right
            if (entity.getReactorState() != ReactorStateEnum.STARTING) blit(matrixStack, xPos + 514, yPos + 261, 83, mouseOverStart ? 470 : 449, 83, 21, 1024, 1024); // left right bottom
            if (entity.getReactorState() != ReactorStateEnum.RUNNING) blit(matrixStack, xPos + 514, yPos + 289, 0, mouseOverRunning ? 512 : 491, 83, 21, 1024, 1024); // left right bottom
            if (entity.getReactorState() != ReactorStateEnum.STOP) blit(matrixStack, xPos + 514, yPos + 316, 0, mouseOverStop ? 470 : 449, 83, 21, 1024, 1024); // left right bottom

            // scram button
            blit(matrixStack, xPos + 627, yPos + 301, 166, scramButton.isMouseOver(curMouseX, curMouseY) ? 540 : 506, 80, 34, 1024, 1024);

            // coil engaged buttons right
            if (entity.isTurbineCoilsEngaged()) blit(matrixStack, xPos + 619, yPos + 195, 0, coilDisengageButton.isMouseOver(curMouseX, curMouseY) ? 470 : 449, 83, 21, 1024, 1024); // left right bottom
            else blit(matrixStack, xPos + 524, yPos + 195, 83, coilEngageButton.isMouseOver(curMouseX, curMouseY) ? 470 : 449, 83, 21, 1024, 1024); // left right bottom

            // fuel bars
            int value = 0;
            for (int i = 0; i < entity.getFuelRodStatus().length; i++) value += entity.getFuelRodStatus()[i];
            blit(matrixStack, xPos + 564, yPos + 376, 224, 449, (int) ((value / 8100f * 100f)*1.23f), 9, 1024, 1024); // uran
            blit(matrixStack, xPos + 564, yPos + 396, 224, 458, (int) (((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * 100 * 1.23f), 9, 1024, 1024); // salt
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
        ReactorControllerBlockEntity entity = this.getMenu().getTile();
        curMouseX = x;
        curMouseY = y;

        renderBackground(pPoseStack);
        super.render(pPoseStack, x, y, pPartialTick);
        renderTooltip(pPoseStack, x, y);

        // RIGHT SIDE
        if (ClientConfig.showRightReactorScreenArea.get() && !rightSideButtonsAdded) {
            // Incrementer buttons
            incrementerSpeed = new ModButton(220, 38, 31, 11, null, () -> setTurbineSpeed(), null, entity, this, 0, 0, true);
            incrementerOverflow = new ModButton(254, 38, 31, 11, null, () -> setTurbineOverflow(), null, entity, this, 0, 0, true);
            incrementerLoad = new ModButton(289, 38, 31, 11, null, () -> setTurbineLoad(), null, entity, this, 0, 0, true);
            addWidget(incrementerSpeed);
            addWidget(incrementerOverflow);
            addWidget(incrementerLoad);

            // Coil engage buttons
            coilEngageButton = new ModButton(225, 69, 41, 11, null, () -> setTurbineCoils(true), null, entity, this, 0, 0, true);
            coilDisengageButton = new ModButton(225 + 48, 69, 41, 11, null, () -> setTurbineCoils(false), null, entity, this, 0, 0, true);
            addWidget(coilEngageButton);
            addWidget(coilDisengageButton);

            // State buttons
            addWidget(new ModButton(220, 101, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STARTING), null, entity, this, 0, 0, true));
            addWidget(new ModButton(220, 115, 41, 11, null, () -> changeReactorState(ReactorStateEnum.RUNNING), null, entity, this, 0, 0, true));
            addWidget(new ModButton(220, 128, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STOP), null, entity, this, 0, 0, true));

            // Scram button
            scramButton = new ModButton(276, 120, 41, 19, null, () -> scram(), null, entity, this, 0, 0, true);
            addWidget(scramButton);

            rightSideButtonsAdded = true;
        } else if (rightSideButtonsAdded && !ClientConfig.showRightReactorScreenArea.get()) {
            clearWidgets();
            addElements();
            rightSideButtonsAdded = false;
            leftSideButtonsAdded = false;
        }

        // LEFT SIDE
        if (ClientConfig.showLeftReactorScreenArea.get() && !leftSideButtonsAdded) {
            // Set buttons
            rodSetButton = new ModButton(- 142, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.RODS), null, entity, this, 0, 0, true); // Left
            loadSetButton = new ModButton(- 108, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.LOAD), null, entity, this, 0, 0, true); // Middle
            tempSetButton = new ModButton(- 72, + 44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.TEMP), null, entity, this, 0, 0, true); // Right
            addWidget(rodSetButton);
            addWidget(loadSetButton);
            addWidget(tempSetButton);

            // Edit boxes left
            inputBox1 = new ModEditBox(font, leftPos - 68, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            inputBox2 = new ModEditBox(font, leftPos - 102, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            inputBox3 = new ModEditBox(font, leftPos - 138, topPos + 32, 30, 8, Component.empty().withStyle(RenderUtil::notoSans));
            positionInputs = new ModEditBox[]{inputBox1, inputBox2, inputBox3};
            for (ModEditBox modEditBox : positionInputs) addRenderableWidget(modEditBox);
            subInit();

            leftSideButtonsAdded = true;
        } else if (leftSideButtonsAdded && !ClientConfig.showLeftReactorScreenArea.get()) {
            clearWidgets();
            addElements();
            rightSideButtonsAdded = false;
            leftSideButtonsAdded = false;
        }

        lastScramState = entity.isScrammed();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        ReactorControllerBlockEntity entity = container.getTile();
        renderCenterPartTexts(pPoseStack);

        if (leftSideButtonsAdded) {
            renderLeftPartProgress(pPoseStack);
            renderLeftPartTexts(pPoseStack);

            if (inputBox1.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"25-999°C"});
            if (inputBox2.isMouseOver(pMouseX, pMouseY) || inputBox3.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"0-100%"});
            if (rodSetButton.isMouseOver(pMouseX, pMouseY)) appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"≤ Current", "≥≤ All"});
        }
        if (rightSideButtonsAdded) {
            renderRightPartProgress(pPoseStack);
            renderRightPartTexts(pPoseStack);

            if (incrementerSpeed.isMouseOver(pMouseX, pMouseY) || incrementerOverflow.isMouseOver(pMouseX, pMouseY) || incrementerLoad.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"≤    ±1", "≥≤ ±10"});

            for (int i = 0; i < controlRodsButtons.length; i++) {
                if (controlRodsButtons[i].isMouseOver(pMouseX, pMouseY)) {
                    appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getControlRodStatus((byte) i) + "%", "⌠ ±"});
                }
            }

        }

    }

    //region Radial Progress
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
        RenderSystem.setShaderTexture(0, new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/progress/radial_progress_" + (progress >= 0 && progress <= 100 ? progress : 100) + ".png"));
        blit(poseStack, x, y, 0, 0, 222, 222);
    }
    //endregion

    //region Texts
    public void renderLeftPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        int fuelValue = 0;
        for (int i = 0; i < container.getTile().getFuelRodStatus().length; i++) fuelValue += container.getTile().getFuelRodStatus()[i];

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal((byte) selectedRod == -1 ? "0" : entity.getControlRodStatus((byte) selectedRod) + "%").withStyle(RenderUtil::notoSans), pPoseStack, -323, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorTargetLoadSet() + "%").withStyle(RenderUtil::notoSans), pPoseStack, -236, 48, 16711422);
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
        RenderUtil.drawCenteredText(Component.literal((int) (fuelValue / 8100f * 100f) + "").withStyle(RenderUtil::notoSans), pPoseStack, -135, 8, 16711422);
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
        RenderUtil.drawText(Component.literal(entity.isScrammed() ? "SCRAM" : "NORMAL").withStyle(RenderUtil::notoSans), pPoseStack, -130, 141, entity.isScrammed() ? 11075598 : 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorStatus() + "%").withStyle(RenderUtil::notoSans), pPoseStack, -27, 75, 16711422);
        pPoseStack.popPose();
    }

    public void renderRightPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        int fuelValue = 0;
        for (int i = 0; i < container.getTile().getFuelRodStatus().length; i++) fuelValue += container.getTile().getFuelRodStatus()[i];

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetSpeed())).withStyle(RenderUtil::notoSans), pPoseStack, 587, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetOverflowSet()) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 674, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(entity.getTurbineTargetLoadSet()) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 761, 48, 16711422);

        RenderUtil.drawCenteredText(Component.literal("Enriched").withStyle(RenderUtil::notoSans), pPoseStack, 577, 407, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Molten Salt").withStyle(RenderUtil::notoSans), pPoseStack, 577, 433, 16711422);
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

        RenderUtil.drawCenteredText(Component.literal("Uran:").withStyle(RenderUtil::notoSans), pPoseStack, 330, 226, 16711422);
        RenderUtil.drawCenteredText(Component.literal( (int) (fuelValue / 8100f * 100f) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 452, 226, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Fuel:").withStyle(RenderUtil::notoSans), pPoseStack, 330, 241, 16711422);
        RenderUtil.drawCenteredText(Component.literal((int) (((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * 100) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 452, 241, 16711422);

        RenderUtil.drawText(Component.literal("OPERATION").withStyle(RenderUtil::notoSans), pPoseStack, 314, 128, 11184810);
        RenderUtil.drawText(Component.literal("EMERGENCY").withStyle(RenderUtil::notoSans), pPoseStack, 393, 128, 11184810);
        RenderUtil.drawText(Component.literal("FUEL STATUS").withStyle(RenderUtil::notoSans), pPoseStack, 314, 213, 11184810);
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
        RenderUtil.drawCenteredText(Component.literal(entity.getNotification()).withStyle(RenderUtil::notoSans), pPoseStack, this.getXSize() / 2, 170, 16711422);

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
        dateFormat.setTimeZone(TimeZone.getTimeZone("MEZ"));
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorRunningSince() == -1 ? "Unset" : dateFormat.format((entity.getReactorRunningSince() / 20) * 1000)).withStyle(RenderUtil::notoSans), pPoseStack, 196, 19, 16711422);
        RenderUtil.drawRightboundText(Component.literal(FormattingUtil.formatEnergy(entity.getTurbinePowerGeneration())).withStyle(RenderUtil::notoSans), pPoseStack, 196, 38, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.isScrammed() ? "SCRAM" : "NORMAL").withStyle(RenderUtil::notoSans).withStyle(ChatFormatting.BOLD), pPoseStack, 176, 61, entity.isScrammed() ? 11075598 : 43275);
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
    //endregion

    //region Graphs
    public void updateTempGraphData() {
        int value = container.getTile().getReactorCurrentTemperature();
        if (tempIntegers < tempGraphValues.length) {
            tempGraphValues[tempIntegers] = (float) value;
            tempIntegers++;
        } else {
            for (int i = 1; i < tempGraphValues.length; i++)
                tempGraphValues[i - 1] = tempGraphValues[i];
            tempGraphValues[tempGraphValues.length - 1] = (float) value;
        }
    }

    public void updateFlowGraphData() {
        int value = container.getTile().getTurbineCurrentFlow();
        if (flowIntegers < flowGraphValues.length) {
            flowGraphValues[flowIntegers] = (float) value;
            flowIntegers++;
        } else {
            for (int i = 1; i < flowGraphValues.length; i++)
                flowGraphValues[i - 1] = flowGraphValues[i];
            flowGraphValues[flowGraphValues.length - 1] = (float) value;
        }
    }

    public void updateSpeedGraphData() {
        int value = container.getTile().getTurbineCurrentSpeed();
        if (speedIntegers < speedGraphValues.length) {
            speedGraphValues[speedIntegers] = (float) value;
            speedIntegers++;
        } else {
            for (int i = 1; i < speedGraphValues.length; i++)
                speedGraphValues[i - 1] = speedGraphValues[i];
            speedGraphValues[speedGraphValues.length - 1] = (float) value;
        }
    }

    public void updatePressureGraphData() {
        float value = container.getTile().getReactorPressure();
        if (pressureIntegers < pressureGraphValues.length) {
            pressureGraphValues[pressureIntegers] = value;
            pressureIntegers++;
        } else {
            for (int i = 1; i < pressureGraphValues.length; i++)
                pressureGraphValues[i - 1] = pressureGraphValues[i];
            pressureGraphValues[pressureGraphValues.length - 1] = value;
        }
    }

    public void renderGraph(PoseStack pPoseStack, int x, int y, Float[] list) {
        if (list[0] != null) {
            float max = 0, min = 0;
            for (int i = 0; i < list.length; i++)
                if (list[i] != null && list[i] > max) max = list[i];
            min = max;
            for (int i = 0; i < list.length; i++)
                if (list[i] != null && list[i] < min) min = list[i];

            float calculationMax = max - min;
            for (int i = 0; i < list.length; i++) {
                if (list[i] == null) break;
                float calculationCurrent = list[i] - min;
                float blitSize = (int) ((calculationCurrent / calculationMax) * 20);

                if (blitSize == 0 && calculationCurrent == calculationMax) blitSize = 20;
                if (calculationMax - 1 == calculationCurrent && blitSize == 0) blitSize = 10;
                if (blitSize == 0) blitSize = 2;
                blit(pPoseStack, x + i, (int) (y + (20 - blitSize)), (float) (this.container.getTile().isScrammed() ? 1021 : 1022), 167F, 1, (int) blitSize, 1024, 1024);
            }
        }
    }
    //endregion

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
        return 416;
    }

    public ResourceLocation getBackgroundTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/thorium_reactor_gui.png");
    }

    public ResourceLocation getSideTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/thorium_reactor_sidewindows.png");
    }

}
