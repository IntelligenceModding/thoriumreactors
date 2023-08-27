package unhappycodings.thoriumreactors.common.container.reactor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.editbox.ModEditBox;
import unhappycodings.thoriumreactors.common.enums.ReactorButtonTypeEnum;
import unhappycodings.thoriumreactors.common.enums.ReactorStateEnum;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.*;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerCopyTurbinePacket;
import unhappycodings.thoriumreactors.common.network.toserver.reactor.ReactorControllerRemoveTurbinePacket;
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
    public Float[] generationGraphValues = new Float[93];
    public boolean leftSideButtonsAdded;
    public boolean rightSideButtonsAdded;
    public int tempIntegers = 0;
    public int flowIntegers = 0;
    public int speedIntegers = 0;
    public int generationIntegers = 0;
    public int selectedRod = -1;
    public int curMouseX = 0;
    public int curMouseY = 0;

    public boolean lastScramState;

    public ModButton[] controlRodsButtons = new ModButton[64];
    public ModButton incrementerFlow;
    public ModButton turbineLeft;
    public ModButton turbineRight;
    public ModButton turbineRemove;
    public ModButton turbineCopy;
    public ModButton coilEngageButton;
    public ModButton coilDisengageButton;
    public ModButton activateButton;
    public ModButton deactivateButton;
    public ModButton scramButton;
    public ModButton rodSetButton;
    public ModButton loadSetButton;
    public ModButton tempSetButton;

    public ModEditBox[] positionInputs;
    public ModEditBox inputBox1;
    public ModEditBox inputBox2;
    public ModEditBox inputBox3;

    public int selectedTurbine = 0;

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
                inputBox3.setValue(String.valueOf(entity.getControlRodStatus((byte) index)));
            }, null, entity, this, 0, 0, true);
            addWidget(controlRodsButtons[i]);
        }

    }

    public void trySetValue(ReactorButtonTypeEnum typeEnum) {
        ReactorControllerBlockEntity entity = container.getTile();
        if (typeEnum == ReactorButtonTypeEnum.TEMP)
            PacketHandler.sendToServer(new ReactorControllerTemperaturePacket(entity.getBlockPos(), Short.parseShort(inputBox1.getValue())));
        //if (typeEnum == ReactorButtonTypeEnum.LOAD && Integer.parseInt(inputBox2.getValue()) <= 100 && Integer.parseInt(inputBox2.getValue()) >= 0) PacketHandler.sendToServer(new ReactorControllerLoadPacket(entity.getBlockPos(), Byte.parseByte(inputBox2.getValue())));
        if (typeEnum == ReactorButtonTypeEnum.RODS && Integer.parseInt(inputBox3.getValue()) <= 100 && Integer.parseInt(inputBox3.getValue()) >= 0)
            PacketHandler.sendToServer(new ReactorControllerRodInsertPacket(entity.getBlockPos(), Byte.parseByte(inputBox3.getValue()), (byte) selectedRod, hasShiftDown()));
    }

    public void setTurbineCoils(boolean value) {
        ReactorControllerBlockEntity entity = container.getTile();
        if (entity.getTurbinePos().size() <= 0) return;
        PacketHandler.sendToServer(new TurbineCoilsPacket(entity.getTurbinePos().get(selectedTurbine), value));
    }

    public void setTurbineActive(boolean value) {
        ReactorControllerBlockEntity entity = container.getTile();
        if (entity.getTurbinePos().size() <= 0) return;
        PacketHandler.sendToServer(new TurbineActivePacket(entity.getTurbinePos().get(selectedTurbine), value));
    }

    public void setTurbineFlow() {
        ReactorControllerBlockEntity entity = container.getTile();
        if (entity.getTurbinePos().size() <= 0) return;
        int xPos = width - (getMainSizeX() / 2);
        int yPos = height - (getMainSizeY() / 2);
        boolean mouseOver = RenderUtil.mouseInArea((xPos + 514) / 2, (yPos + 149) / 2, (xPos + 514 + 28) / 2, (yPos + 149 + 19) / 2, curMouseX, curMouseY);
        TurbineControllerBlockEntity targetEntity = (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(this.container.getTile().getTurbinePos().get(selectedTurbine));
        PacketHandler.sendToServer(new TurbineFlowPacket(targetEntity.getBlockPos(), (byte) (incrementerFlow.isMouseOver(curMouseX, curMouseY) && mouseOver ? -1 * (hasShiftDown() ? 10 : 1) : (hasShiftDown() ? 10 : 1))));
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
        boolean mouseOverIncrementFlow = RenderUtil.mouseInArea((xPos + 583) / 2, (yPos + 135) / 2, (xPos + 583 + 28) / 2, (yPos + 135 + 19) / 2, x, y);

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
            boolean turbineActive = false;
            if (entity.turbinePos.size() > 0 && entity.getLevel().getBlockEntity(entity.turbinePos.get(selectedTurbine)) instanceof TurbineControllerBlockEntity controllerBlockEntity) {
                if (controllerBlockEntity.isActivated())
                    turbineActive = true;
            }

            blit(matrixStack, xPos - 49, yPos + 293, (entity.isScrammed() ? 957 : 1017) + (turbineActive || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !turbineActive ? 155 : ticks % 10 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right top
            blit(matrixStack, xPos - 47, yPos + 285, (entity.isScrammed() ? 957 : 1017) + (turbineActive || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !turbineActive ? 155 : ticks % 11 == 0 ? 160 : 155, 6, 5, 1024, 1024); // right bottom
            blit(matrixStack, xPos - 98, yPos + 285, (entity.isScrammed() ? 957 : 1017) + (turbineActive || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !turbineActive ? 155 : 155, 6, 5, 1024, 1024); // mid right top
            blit(matrixStack, xPos - 100, yPos + 293, (entity.isScrammed() ? 957 : 1017) + (turbineActive || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !turbineActive ? 155 : ticks % 6 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right bottom
            blit(matrixStack, xPos - 89, yPos + 253, (entity.isScrammed() ? 957 : 1017) + (turbineActive || entity.isScrammed() ? 0 : -60), entity.isScrammed() || !turbineActive ? 155 : ticks % 20 == 0 ? 160 : 155, 6, 5, 1024, 1024); // mid right

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
            String status = entity.isScrammed() ? "SCRAM" : ((entity.getReactorCurrentTemperature() / 971) * 100) > 104 ? ((entity.getReactorCurrentTemperature() / 971) * 100) > 114 ? "CRITICAL" : "OVERLOAD" : "NORMAL";
            blit(matrixStack, xPos - 205, yPos + 340, (ticks % 20 < 10 ? 991 : 1003) + (entity.isScrammed() ? -24 : 0), 154, 12, 12, 1024, 1024); // left right bottom
            blit(matrixStack, xPos + (450 - Minecraft.getInstance().font.width(status) * 2 - 8), yPos + 181, (ticks % 20 < 10 ? 991 : 1003) + (entity.isScrammed() ? -24 : 0), 154 + 12, 12, 12, 1024, 1024); // left right bottom
        }

        if (rightSideButtonsAdded) {
            blit(matrixStack, xPos + getMainSizeX() + 1, yPos, 728, 0, getRightSideX(), getRightSideY(), 1024, 1024); //right

            // incrementer buttons
            blit(matrixStack, xPos + 515, yPos + 149, 166, entity.getTurbinePos().size() > 0 ? incrementerFlow.isMouseOver(curMouseX, curMouseY) ? (mouseOverIncrementFlow ? 449 + 19 : 449 + 38) : 449 : 900, 58, 19, 1024, 1024); // right incrementer speed bottom

            // state buttons right
            if (entity.getReactorState() != ReactorStateEnum.STARTING)
                blit(matrixStack, xPos + 514, yPos + 261, 83, mouseOverStart ? 470 : 449, 83, 21, 1024, 1024); // left right bottom
            if (entity.getReactorState() != ReactorStateEnum.RUNNING)
                blit(matrixStack, xPos + 514, yPos + 289, 0, mouseOverRunning ? 512 : 491, 83, 21, 1024, 1024); // left right bottom
            if (entity.getReactorState() != ReactorStateEnum.STOP)
                blit(matrixStack, xPos + 514, yPos + 316, 0, mouseOverStop ? 470 : 449, 83, 21, 1024, 1024); // left right bottom

            // scram button
            blit(matrixStack, xPos + 627, yPos + 301, 166, scramButton.isMouseOver(curMouseX, curMouseY) ? 540 : 506, 80, 34, 1024, 1024);

            // turbine buttons
            TurbineControllerBlockEntity targetEntity = entity.getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(entity.getTurbinePos().get(selectedTurbine)) : null;
            if (targetEntity != null) {
                blit(matrixStack, xPos + 590, yPos + 86, 82, (!coilEngageButton.isMouseOver(curMouseX, curMouseY) ? 533 : 555) + (targetEntity.isCoilsEngaged() ? 44 : 0), 52, 22, 1024, 1024);
                blit(matrixStack, xPos + 655, yPos + 86, 30, (!coilDisengageButton.isMouseOver(curMouseX, curMouseY) ? 533 : 555) + (!targetEntity.isCoilsEngaged() ? 44 : 0), 52, 22, 1024, 1024);

                blit(matrixStack, xPos + 590, yPos + 127, 82, (!activateButton.isMouseOver(curMouseX, curMouseY) ? 533 : 555) + (targetEntity.isActivated() ? 44 : 0), 52, 22, 1024, 1024);
                blit(matrixStack, xPos + 655, yPos + 127, 30, (!deactivateButton.isMouseOver(curMouseX, curMouseY) ? 533 : 555) + (!targetEntity.isActivated() ? 44 : 0), 52, 22, 1024, 1024);

            }
            blit(matrixStack, xPos + 548, yPos + 22, 961, selectedTurbine != 0 ? (!turbineLeft.isMouseOver(curMouseX, curMouseY) ? 360 : 377) : 394, 17, 17, 1024, 1024); // left
            blit(matrixStack, xPos + 663, yPos + 22, 978, selectedTurbine + 1 < this.container.getTile().getTurbinePos().size() ? (!turbineRight.isMouseOver(curMouseX, curMouseY) ? 360 : 377) : 394, 17, 17, 1024, 1024); // right

            blit(matrixStack, xPos + 692, yPos + 25, 995, !turbineRemove.isMouseOver(curMouseX, curMouseY) ? 393 : 382, 28, 11, 1024, 1024); // x
            blit(matrixStack, xPos + 508, yPos + 25, 995, !turbineRemove.isMouseOver(curMouseX, curMouseY) ? 360 : 371, 28, 11, 1024, 1024); // x

            // fuel bars
            int value = 0;
            if (entity.getFuelRodStatus().length != 0) {
                for (int i = 0; i < entity.getFuelRodStatus().length; i++) value += entity.getFuelRodStatus()[i];
                blit(matrixStack, xPos + 564, yPos + 376, 224, 449, (int) ((value / 8100f * 100f) * 1.23f), 9, 1024, 1024); // uran
                blit(matrixStack, xPos + 564, yPos + 396, 224, 458, (int) (((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * 100 * 1.23f), 9, 1024, 1024); // salt
            }
        }

        // middle rods grit
        renderRods(matrixStack);

        // middle graphs
        updateTempGraphData();
        updateFlowGraphData();
        updateSpeedGraphData();
        updateGenerationGraphData();
        renderGraph(matrixStack, xPos + 39, yPos + 395, tempGraphValues); // temp
        renderGraph(matrixStack, xPos + 149, yPos + 395, flowGraphValues); // flow
        renderGraph(matrixStack, xPos + 259, yPos + 395, speedGraphValues); // speed
        renderGraph(matrixStack, xPos + 369, yPos + 395, generationGraphValues); // pressure

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
            incrementerFlow = new ModButton(220, 45, 31, 11, null, this::setTurbineFlow, null, entity, this, 0, 0, true);
            addWidget(incrementerFlow);

            // Coil engage buttons
            turbineLeft = new ModButton(237, -18, 8, 8, null, () -> trySetTurbineSelection(false), null, entity, this, 0, 0, true);
            turbineRight = new ModButton(295, -18, 8, 8, null, () -> trySetTurbineSelection(true), null, entity, this, 0, 0, true);
            turbineRemove = new ModButton(309, -17, 14, 6, null, () -> removeTurbine(selectedTurbine), null, entity, this, 0, 0, true);
            turbineCopy = new ModButton(217, -17, 14, 6, null, () -> copyTurbineConfigurations(selectedTurbine), null, entity, this, 0, 0, true);
            coilEngageButton = new ModButton(258, 15, 26, 11, null, () -> setTurbineCoils(true), null, entity, this, 0, 0, true);
            coilDisengageButton = new ModButton(258 + 32, 15, 26, 11, null, () -> setTurbineCoils(false), null, entity, this, 0, 0, true);
            activateButton = new ModButton(258, 35, 26, 11, null, () -> setTurbineActive(true), null, entity, this, 0, 0, true);
            deactivateButton = new ModButton(258 + 32, 35, 26, 11, null, () -> setTurbineActive(false), null, entity, this, 0, 0, true);
            addWidget(turbineLeft);
            addWidget(turbineRight);
            addWidget(turbineRemove);
            addWidget(turbineCopy);
            addWidget(coilEngageButton);
            addWidget(coilDisengageButton);
            addWidget(activateButton);
            addWidget(deactivateButton);

            // State buttons
            addWidget(new ModButton(220, 101, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STARTING), null, entity, this, 0, 0, true));
            addWidget(new ModButton(220, 115, 41, 11, null, () -> changeReactorState(ReactorStateEnum.RUNNING), null, entity, this, 0, 0, true));
            addWidget(new ModButton(220, 128, 41, 11, null, () -> changeReactorState(ReactorStateEnum.STOP), null, entity, this, 0, 0, true));

            // Scram button
            scramButton = new ModButton(276, 120, 41, 19, null, () -> scram(), null, entity, this, 0, 0, true);
            addWidget(scramButton);

            rightSideButtonsAdded = true;
        } else if (rightSideButtonsAdded && !ClientConfig.showRightReactorScreenArea.get()) {
            resetWidgets();
        }

        // LEFT SIDE
        if (ClientConfig.showLeftReactorScreenArea.get() && !leftSideButtonsAdded) {
            // Set buttons
            rodSetButton = new ModButton(-142, +44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.RODS), null, entity, this, 0, 0, true); // Left
            loadSetButton = new ModButton(-108, +44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.LOAD), null, entity, this, 0, 0, true); // Middle
            tempSetButton = new ModButton(-72, +44, 25, 12, null, () -> trySetValue(ReactorButtonTypeEnum.TEMP), null, entity, this, 0, 0, true); // Right
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
            resetWidgets();
        }

        lastScramState = entity.isScrammed();
    }

    public void removeTurbine(int turbine) {
        ReactorControllerBlockEntity entity = this.container.getTile();
        PacketHandler.sendToServer(new ReactorControllerRemoveTurbinePacket(entity.getBlockPos(), turbine));
        selectedTurbine = 0;
    }

    public void copyTurbineConfigurations(int turbine) {
        ReactorControllerBlockEntity entity = this.container.getTile();
        PacketHandler.sendToServer(new ReactorControllerCopyTurbinePacket(entity.getBlockPos(), turbine));
    }

    public void trySetTurbineSelection(boolean increase) {
        if (!increase && selectedTurbine > 0) {
            selectedTurbine--;
        } else if (increase && selectedTurbine + 1 < this.container.getTile().getTurbinePos().size()) {
            selectedTurbine++;
        }
    }

    public void resetWidgets() {
        clearWidgets();
        addElements();
        rightSideButtonsAdded = false;
        leftSideButtonsAdded = false;
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        ReactorControllerBlockEntity entity = container.getTile();
        renderCenterPartTexts(pPoseStack);

        if (leftSideButtonsAdded) {
            renderLeftPartProgress(pPoseStack);
            renderLeftPartTexts(pPoseStack);

            if (inputBox1.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"25-999°C"});
            if (inputBox2.isMouseOver(pMouseX, pMouseY) || inputBox3.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"0-100%"});
            if (rodSetButton.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"≤ Current", "≥≤ All"});
        }

        if (rightSideButtonsAdded) {
            renderRightPartProgress(pPoseStack);
            renderRightPartTexts(pPoseStack);

            if (turbineRemove.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Remove selected turbine"});

            if (turbineCopy.isMouseOver(pMouseX, pMouseY))
                appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{"Copy configuration to all turbines"});

            if (incrementerFlow.isMouseOver(pMouseX, pMouseY))
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
        renderRadialProgress(pPoseStack, -558, -38, (int) Math.floor(container.getTile().getReactorCurrentTemperature() / container.getTile().getReactorTargetTemperature() * 100)); // Right
        pPoseStack.popPose();
    }

    public void renderRightPartProgress(PoseStack pPoseStack) {
        pPoseStack.pushPose();
        pPoseStack.scale(0.14f, 0.14f, 0.14f);
        TurbineControllerBlockEntity targetEntity = this.container.getTile().getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(this.container.getTile().getTurbinePos().get(selectedTurbine)) : null;
        renderRadialProgress(pPoseStack, 1550, 14, targetEntity != null ? (int) Math.floor(targetEntity.getTargetFlowrate() / 2500 * 100) : 0); // Middle
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
        for (int i = 0; i < container.getTile().getFuelRodStatus().length; i++)
            fuelValue += container.getTile().getFuelRodStatus()[i];

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal((byte) selectedRod == -1 ? "0" : entity.getControlRodStatus((byte) selectedRod) + "%").withStyle(RenderUtil::notoSans), pPoseStack, -323, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getReactorTargetLoadSet() + "%").withStyle(RenderUtil::notoSans), pPoseStack, -236, 48, 16711422);
        RenderUtil.drawCenteredText(Component.literal(Math.floor(entity.getReactorTargetTemperature() * 10) / 10 + "°C").withStyle(RenderUtil::notoSans), pPoseStack, -149, 48, 16711422);
        pPoseStack.popPose();

        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("MANUAL VALVE MANIPULATION").withStyle(RenderUtil::notoSans), pPoseStack, -206, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("ROD INSERT").withStyle(RenderUtil::notoSans), pPoseStack, -184, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(selectedRod == -1 ? "Select" : "Rod").withStyle(RenderUtil::notoSans), pPoseStack, -184, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal(selectedRod == -1 ? "Rod" : "#" + selectedRod).withStyle(RenderUtil::notoSans), pPoseStack, -184, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -184, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("FUEL LOAD").withStyle(RenderUtil::notoSans), pPoseStack, -135, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf((int) (fuelValue / 8100f * 100f))).withStyle(RenderUtil::notoSans), pPoseStack, -135, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("%").withStyle(RenderUtil::notoSans), pPoseStack, -135, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -135, 68, 11566128);

        RenderUtil.drawCenteredText(Component.literal("TEMP").withStyle(RenderUtil::notoSans), pPoseStack, -85, -17, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(Math.round(entity.getReactorCurrentTemperature() * 10f) / 10f)).withStyle(RenderUtil::notoSans), pPoseStack, -85, 8, 16711422);
        RenderUtil.drawCenteredText(Component.literal("°C").withStyle(RenderUtil::notoSans), pPoseStack, -85, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SET").withStyle(RenderUtil::notoSans), pPoseStack, -85, 68, 11566128);

        RenderUtil.drawText(Component.literal("OPERATIONAL SYSTEM CHART").withStyle(RenderUtil::notoSans), pPoseStack, -206, 97, 11184810);
        RenderUtil.drawCenteredText(Component.literal("REACTOR STATUS").withStyle(RenderUtil::notoSans), pPoseStack, -165, 193, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawText(Component.literal(entity.isScrammed() ? "SCRAM" : ((entity.getReactorCurrentTemperature() / 971) * 100) > 104 ? ((entity.getReactorCurrentTemperature() / 971) * 100) > 114 ? "CRITICAL" : "OVERLOAD" : "NORMAL").withStyle(RenderUtil::notoSans), pPoseStack, -130, 141, entity.isScrammed() ? 11075598 : 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.8f, 1.8f, 1.8f);
        float value = (float) (Math.floor((entity.getReactorStatus()) * 10f) / 10f);
        RenderUtil.drawRightboundText(Component.literal((entity.getReactorStatus() == 100f ? "100" : value) + "%").withStyle(RenderUtil::notoSans), pPoseStack, -25, 77, 16711422);
        pPoseStack.popPose();
    }

    public void renderRightPartTexts(PoseStack pPoseStack) {
        ReactorControllerBlockEntity entity = container.getTile();
        int fuelValue = 0;
        if (container.getTile().getFuelRodStatus().length != 0) {
            for (int i = 0; i < container.getTile().getFuelRodStatus().length; i++)
                fuelValue += container.getTile().getFuelRodStatus()[i];
        }

        TurbineControllerBlockEntity targetEntity = entity.getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(entity.getTurbinePos().get(selectedTurbine)) : null;

        // very, very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.4f, 0.4f, 0.4f);
        RenderUtil.drawCenteredText(Component.literal((targetEntity != null ? (int) targetEntity.getTargetFlowrate() : 0) + "mb").withStyle(RenderUtil::notoSans), pPoseStack, 587, 63, 16711422);
        RenderUtil.drawCenteredText(Component.literal(entity.getTurbinePos().size() > 0 ? entity.turbinePos.get(selectedTurbine).toString().replace("BlockPos{", "").replace("}", "") : "").withStyle(RenderUtil::notoSans), pPoseStack, 676, -22, 16711422);

        RenderUtil.drawCenteredText(Component.literal("Enriched").withStyle(RenderUtil::notoSans), pPoseStack, 577, 407, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Molten Salt").withStyle(RenderUtil::notoSans), pPoseStack, 577, 433, 16711422);
        pPoseStack.popPose();

        // very small text
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("TURBINE GENERATOR").withStyle(RenderUtil::notoSans), pPoseStack, 314, -39, 11184810);
        RenderUtil.drawCenteredText(Component.literal("FLOW").withStyle(RenderUtil::notoSans), pPoseStack, 336, -5, 16711422);
        RenderUtil.drawCenteredText(Component.literal(targetEntity != null ? String.valueOf((int) targetEntity.getCurrentFlowrate()) : "0").withStyle(RenderUtil::notoSans), pPoseStack, 336, 17, 16711422);
        RenderUtil.drawCenteredText(Component.literal("mB/t").withStyle(RenderUtil::notoSans), pPoseStack, 336, 26, 16711422);
        RenderUtil.drawCenteredText(Component.literal("STEAM").withStyle(RenderUtil::notoSans), pPoseStack, 336, 53, 16711422);

        RenderUtil.drawCenteredText(Component.literal("COIL ENGAGE").withStyle(RenderUtil::notoSans), pPoseStack, 410, 10, 16711422);
        RenderUtil.drawCenteredText(Component.literal("ACTIVATED").withStyle(RenderUtil::notoSans), pPoseStack, 410, 39, 16711422);

        RenderUtil.drawCenteredText(Component.literal("ON").withStyle(RenderUtil::notoSans), pPoseStack, 387, 25, targetEntity != null ? targetEntity.isCoilsEngaged() ? 2039583 : 43275 : 2039583);
        RenderUtil.drawCenteredText(Component.literal("OFF").withStyle(RenderUtil::notoSans), pPoseStack, 434, 25, targetEntity != null ? !targetEntity.isCoilsEngaged() ? 2039583 : 12459309 : 2039583);
        RenderUtil.drawCenteredText(Component.literal("ON").withStyle(RenderUtil::notoSans), pPoseStack, 387, 54, targetEntity != null ? targetEntity.isActivated() ? 2039583 : 43275 : 2039583);
        RenderUtil.drawCenteredText(Component.literal("OFF").withStyle(RenderUtil::notoSans), pPoseStack, 434, 54, targetEntity != null ? !targetEntity.isActivated() ? 2039583 : 12459309 : 2039583);

        if (targetEntity != null) {
            RenderUtil.drawText(Component.literal("Producing:   ").withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(targetEntity != null ? (targetEntity.isCoilsEngaged() ? FormattingUtil.formatEnergy((float) Math.floor((targetEntity.getRpm() * (FormattingUtil.getTurbineGenerationModifier(targetEntity.getRpm()))) * 100) / 100) : "0 FE") + "/t" : "0 FE/t").withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 320, 88, targetEntity != null ? !targetEntity.isActivated() ? 2039583 : 12459309 : 2039583);
            RenderUtil.drawText(Component.literal("Speed:        ").withStyle(FormattingUtil.hex(0x55D38A)).append(Component.literal(targetEntity != null ? Math.floor(targetEntity.getRpm() * 100) / 100 + " Rpm" : "0 Rpm").withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 320, 99, targetEntity != null ? !targetEntity.isActivated() ? 2039583 : 12459309 : 2039583);
            RenderUtil.drawText(Component.literal("Flowrate:    ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(targetEntity != null ? targetEntity.getCurrentFlowrate() + " mB/t" : "0 mB/t").withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 320, 110, targetEntity != null ? !targetEntity.isActivated() ? 2039583 : 12459309 : 2039583);
        } else {
            RenderUtil.drawCenteredText(Component.literal("No turbine added!").withStyle(ChatFormatting.GRAY).withStyle(RenderUtil::notoSans), pPoseStack, 385, 94);
            RenderUtil.drawCenteredText(Component.literal("Use Configurator to link").withStyle(ChatFormatting.GRAY).withStyle(RenderUtil::notoSans), pPoseStack, 385, 102);
        }

        RenderUtil.drawCenteredText(Component.literal("START").withStyle(RenderUtil::notoSans), pPoseStack, 345, 149, entity.getReactorState() == ReactorStateEnum.STARTING ? 2039583 : 43275);
        RenderUtil.drawCenteredText(Component.literal("RUN").withStyle(RenderUtil::notoSans), pPoseStack, 345, 169, entity.getReactorState() == ReactorStateEnum.RUNNING ? 2039583 : 11566128);
        RenderUtil.drawCenteredText(Component.literal("STOP").withStyle(RenderUtil::notoSans), pPoseStack, 345, 188, entity.getReactorState() == ReactorStateEnum.STOP ? 2039583 : 12459309);

        RenderUtil.drawCenteredText(Component.literal("INSERT RODS").withStyle(RenderUtil::notoSans), pPoseStack, 424, 150, 16711422);
        RenderUtil.drawCenteredText(Component.literal("INTO CORE").withStyle(RenderUtil::notoSans), pPoseStack, 424, 159, 16711422);
        RenderUtil.drawCenteredText(Component.literal("MANUAL").withStyle(RenderUtil::notoSans), pPoseStack, 424, 178, 16711422);
        RenderUtil.drawCenteredText(Component.literal("SCRAM").withStyle(RenderUtil::notoSans), pPoseStack, 424, 187, 16711422);

        RenderUtil.drawCenteredText(Component.literal("Uran:").withStyle(RenderUtil::notoSans), pPoseStack, 330, 226, 16711422);
        RenderUtil.drawCenteredText(Component.literal((int) (fuelValue / 8100f * 100f) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 452, 226, 16711422);
        RenderUtil.drawCenteredText(Component.literal("Fuel:").withStyle(RenderUtil::notoSans), pPoseStack, 330, 241, 16711422);
        RenderUtil.drawCenteredText(Component.literal((int) (((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * 100) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 452, 241, 16711422);

        RenderUtil.drawText(Component.literal("OPERATION").withStyle(RenderUtil::notoSans), pPoseStack, 314, 129, 11184810);
        RenderUtil.drawText(Component.literal("EMERGENCY").withStyle(RenderUtil::notoSans), pPoseStack, 393, 129, 11184810);
        RenderUtil.drawText(Component.literal("FUEL STATUS").withStyle(RenderUtil::notoSans), pPoseStack, 314, 214, 11184810);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawCenteredText(Component.literal("Turbine #" + (selectedTurbine + 1)).withStyle(RenderUtil::notoSans), pPoseStack, 271, -17, 16711422);
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
        TurbineControllerBlockEntity targetEntity = entity.getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(entity.getTurbinePos().get(selectedTurbine)) : null;
        RenderUtil.drawText(Component.literal("TEMP, °C").withStyle(RenderUtil::notoSans), pPoseStack, -21, 189, 11184810);
        RenderUtil.drawText(Component.literal("FLOW, mB/s").withStyle(RenderUtil::notoSans), pPoseStack, 47, 189, 11184810);
        RenderUtil.drawText(Component.literal("SPEED, RPM").withStyle(RenderUtil::notoSans), pPoseStack, 117, 189, 11184810);
        RenderUtil.drawText(Component.literal("GENER., FE/t").withStyle(RenderUtil::notoSans), pPoseStack, 185, 189, 11184810);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(Math.round(entity.getReactorCurrentTemperature() * 10f) / 10f)).withStyle(RenderUtil::notoSans), pPoseStack, 7, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(targetEntity != null ? targetEntity.getCurrentFlowrate() : 0f)).withStyle(RenderUtil::notoSans), pPoseStack, 76, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(targetEntity != null ? Math.floor(targetEntity.getRpm() * 100f) / 100f : 0f)).withStyle(RenderUtil::notoSans), pPoseStack, 145, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal(String.valueOf(targetEntity != null ? targetEntity.isCoilsEngaged() ? (float) Math.floor((targetEntity.getRpm() * (FormattingUtil.getTurbineGenerationModifier(targetEntity.getRpm()))) * 100) / 100 : 0f : 0f)).withStyle(RenderUtil::notoSans), pPoseStack, 215, 202, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        long seconds = (entity.getReactorRunningSince()) / 20;
        int hours = (int) Math.floor(seconds / 3600f);
        int feProduction = 0;
        for (BlockPos blockPos : entity.getTurbinePos()) {
            if (entity.getLevel().getBlockEntity(blockPos) instanceof TurbineControllerBlockEntity controllerBlockEntity)
                feProduction += controllerBlockEntity.isCoilsEngaged() ? (float) Math.floor((controllerBlockEntity.getRpm() * (FormattingUtil.getTurbineGenerationModifier(controllerBlockEntity.getRpm()))) * 100) / 100 : 0;
        }
        String status = entity.isScrammed() ? "SCRAM" : ((entity.getReactorCurrentTemperature() / 971) * 100) > 104 ? ((entity.getReactorCurrentTemperature() / 971) * 100) > 114 ? "CRITICAL" : "OVERLOAD" : "NORMAL";
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorRunningSince() == -1 ? "Unset" : (hours > 0 ? (hours < 10 ? "0" + hours : hours) + ":" : "00:") + dateFormat.format(((entity.getReactorRunningSince()) / 20) * 1000)).withStyle(RenderUtil::notoSans), pPoseStack, 196, 19, 16711422);
        RenderUtil.drawRightboundText(Component.literal(FormattingUtil.formatEnergy(feProduction)).withStyle(RenderUtil::notoSans), pPoseStack, 196, 38, 16711422);
        RenderUtil.drawRightboundText(Component.literal(status).withStyle(RenderUtil::notoSans).withStyle(ChatFormatting.BOLD), pPoseStack, 196, 61, entity.isScrammed() ? 11075598 : ((entity.getReactorCurrentTemperature() / 971) * 100) > 104 ? ((entity.getReactorCurrentTemperature() / 971) * 100) > 114 ? 0x9F0006 : 0xA9A600 : 43275);
        RenderUtil.drawRightboundText(Component.literal((int) (((entity.getReactorCurrentTemperature() - 22) / 949) * 100) + "%").withStyle(RenderUtil::notoSans), pPoseStack, 196, 82, ((entity.getReactorCurrentTemperature() / 971) * 100) > 104 ? ((entity.getReactorCurrentTemperature() / 971) * 100) > 114 ? 0x9F0006 : 0xA9A600 : 0x00A90B);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorContainment() + "%").withStyle(RenderUtil::notoSans), pPoseStack, 196, 104, 16711422);
        RenderUtil.drawRightboundText(Component.literal(entity.getReactorRadiation() + "uSV/H").withStyle(RenderUtil::notoSans), pPoseStack, 196, 125, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.3f, 1.3f, 1.3f);
        dateFormat = new SimpleDateFormat("HH:mm:ss");
        RenderUtil.drawCenteredText(Component.literal("OVERVIEW").withStyle(RenderUtil::notoSans), pPoseStack, -2, -6, 16711422);
        RenderUtil.drawRightboundText(Component.literal(dateFormat.format(new Date())).withStyle(RenderUtil::notoSans), pPoseStack, 151, -6, 16711422);
        pPoseStack.popPose();
    }
    //endregion

    //region Graphs
    public void updateTempGraphData() {
        float value = container.getTile().getReactorCurrentTemperature();
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
        TurbineControllerBlockEntity targetEntity = this.container.getTile().getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(this.container.getTile().getTurbinePos().get(selectedTurbine)) : null;
        float value = targetEntity != null ? targetEntity.getCurrentFlowrate() : 0f;
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
        TurbineControllerBlockEntity targetEntity = this.container.getTile().getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(this.container.getTile().getTurbinePos().get(selectedTurbine)) : null;
        float value = targetEntity != null ? (float) (Math.floor(targetEntity.getRpm() * 100) / 100) : 0f;
        if (speedIntegers < speedGraphValues.length) {
            speedGraphValues[speedIntegers] = value;
            speedIntegers++;
        } else {
            for (int i = 1; i < speedGraphValues.length; i++)
                speedGraphValues[i - 1] = speedGraphValues[i];
            speedGraphValues[speedGraphValues.length - 1] = value;
        }
    }

    public void updateGenerationGraphData() {
        TurbineControllerBlockEntity targetEntity = this.container.getTile().getTurbinePos().size() > 0 ? (TurbineControllerBlockEntity) container.getTile().getLevel().getBlockEntity(this.container.getTile().getTurbinePos().get(selectedTurbine)) : null;
        float value = targetEntity != null ? (float) (targetEntity.isCoilsEngaged() ? Math.floor((targetEntity.getRpm() * (FormattingUtil.getTurbineGenerationModifier(targetEntity.getRpm()))) * 100) / 100 : 0f) : 0f;
        if (generationIntegers < generationGraphValues.length) {
            generationGraphValues[generationIntegers] = value;
            generationIntegers++;
        } else {
            for (int i = 1; i < generationGraphValues.length; i++)
                generationGraphValues[i - 1] = generationGraphValues[i];
            generationGraphValues[generationGraphValues.length - 1] = value;
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
                if ((calculationMax - 1 == calculationCurrent || calculationMax - 0.010009766f == calculationCurrent || calculationMax - 0.100097656f == calculationCurrent) && blitSize == 0)
                    blitSize = 10;
                if (blitSize == 0) blitSize = 1;
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

}
