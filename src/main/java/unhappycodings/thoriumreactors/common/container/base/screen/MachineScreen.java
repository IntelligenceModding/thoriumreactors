package unhappycodings.thoriumreactors.common.container.base.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.enums.MachineSettingsWindow;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toserver.MachineChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineDumpModePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachinePowerablePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineRedstoneModePacket;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MachineScreen<T extends BaseContainer> extends BaseScreen<T> {
    public static final ResourceLocation POWER_OFF = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/power_off.png");
    public static final ResourceLocation POWER_ON = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/power_on.png");
    public static final ResourceLocation REDSTONE_NORMAL = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_normal.png");
    public static final ResourceLocation REDSTONE_INVERTED = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_inverted.png");
    public static final ResourceLocation REDSTONE_IGNORED = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_ignored.png");
    public static final ResourceLocation INFORMATION = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/information.png");
    public static final ResourceLocation WARNING = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/warning.png");
    public static final ResourceLocation UPGRADES = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/upgrades.png");
    public static final ResourceLocation SIDES = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/sides.png");
    public static final ResourceLocation SIDES_BUTTON = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/sides_btn.png");

    boolean lastPowerable;
    int lastRedstoneMode;

    public MachineScreen(T screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public void refreshWidgets() {
        clearWidgets();
        addButtons();
    }

    @Override
    protected void init() {
        addButtons();
        super.init();
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {
        super.renderBackground(graphics);

        if (ClientConfig.machineSettingsWindow.get() == MachineSettingsWindow.UPGRADES)
            graphics.blit(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/upgrades.png"), getGuiLeft() + getSizeX() + 24, getGuiTop(), 0, 0, 54, 59, 54, 59); //left

        if (ClientConfig.machineSettingsWindow.get() == MachineSettingsWindow.SIDES)
            graphics.blit(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/sides.png"), getGuiLeft() + getSizeX() + 24, getGuiTop(), 0, 0, 54, 59, 54, 59); //left

    }

    protected void addButtons() {
        MachineContainerBlockEntity tile = (MachineContainerBlockEntity) getTile();
        // Information
        addRenderableOnly(new ModButton(-20, 6, 16, 16, INFORMATION, null, null, tile, this, 16, 32, false));

        // Power Button
        lastPowerable = tile.isPowerable();
        addRenderableWidget(new ModButton(-20, 24, 16, 16, lastPowerable ? POWER_ON : POWER_OFF, () -> changePowerable(!tile.isPowerable()), null, tile, this, 16, 32, true));

        // Redstone Button
        lastRedstoneMode = tile.getRedstoneMode();
        addRenderableWidget(new ModButton(-20, 42, 16, 16, lastRedstoneMode == 0 ? REDSTONE_IGNORED : lastRedstoneMode == 1 ? REDSTONE_NORMAL : REDSTONE_INVERTED, this::changeRedstoneMode, null, tile, this, 16, 32, true));

        // Upgrades Button
        addRenderableWidget(new ModButton(getSizeX() + 4, 6, 16, 16, UPGRADES, this::openUpgrades, null, tile, this, 16, 32, true));

        // Sides Config Button
        addRenderableWidget(new ModButton(getSizeX() + 4, 24, 16, 16, SIDES, this::openSidesConfig, null, tile, this, 16, 32, true));

        // Warning
        if (!isSpaceAbove())
            addRenderableWidget(new ModButton(-20, getSizeY() - 18, 16, 16, WARNING, null, null, tile, this, 16, 32, false));

        addRenderableWidget(new ModButton(getSizeX() + 28 + 53, 27, 15, 15, SIDES_BUTTON, null, null, tile, this, 15, 30, true));

    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY) {
        MachineContainerBlockEntity entity = (MachineContainerBlockEntity) getTile();
        if (ScreenUtil.mouseInArea(getGuiLeft() -18, getGuiTop() + 6, getGuiLeft() -3, getGuiTop() + 21, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.usage")).append(" ").append(Component.literal(FormattingUtil.formatEnergy(entity.getState() ? entity.getNeededEnergy() : 0) + "/t")));
            graphics.renderComponentTooltip(Minecraft.getInstance().font, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (ScreenUtil.mouseInArea(getGuiLeft() -18, getGuiTop() + 24, getGuiLeft() -3, getGuiTop() + 39, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.power")).append(" ").append(Component.literal(entity.isPowerable() + "")));
            graphics.renderComponentTooltip(Minecraft.getInstance().font, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (ScreenUtil.mouseInArea(getGuiLeft() -18, getGuiTop() + 42, getGuiLeft() -3, getGuiTop() + 57, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.redstone")).append(" ").append(Component.translatable((lastRedstoneMode == 0 ? FormattingUtil.getTranslatable("machines.tooltip.ignore") : lastRedstoneMode == 1 ? FormattingUtil.getTranslatable("machines.tooltip.normal") : FormattingUtil.getTranslatable("machines.tooltip.inverted")))));
            graphics.renderComponentTooltip(Minecraft.getInstance().font, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (ScreenUtil.mouseInArea(getGuiLeft() - 20, getGuiTop() + getSizeY() - 18, getGuiLeft() -5, getGuiTop() + getSizeY() - 3, pMouseX, pMouseY) && !isSpaceAbove()) {
            List<Component> list = new ArrayList<>();
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.warning")).withStyle(ChatFormatting.RED));
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.needs_air")));
            list.add(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.make_space")));
            graphics.renderComponentTooltip(Minecraft.getInstance().font, list, pMouseX - leftPos, pMouseY - topPos);
        }

        PoseStack pPoseStack = graphics.pose();
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        if (ClientConfig.machineSettingsWindow.get() == MachineSettingsWindow.UPGRADES) {
            ScreenUtil.drawCenteredText(Component.literal("Upgrades").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 148, 2, 11184810);

            pPoseStack.popPose();
            pPoseStack.pushPose();
            pPoseStack.scale(0.55f, 0.55f, 0.55f);

            ScreenUtil.drawText(Component.literal("Speed").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 193, 27, 16711422);
            ScreenUtil.drawText(Component.literal("1").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 254, 27, 16711422);

            ScreenUtil.drawText(Component.literal("Power").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 193, 55, 16711422);
            ScreenUtil.drawText(Component.literal("1").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 254, 55, 16711422);

            ScreenUtil.drawText(Component.literal("Output").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 193, 83, 16711422);
            ScreenUtil.drawText(Component.literal("1").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 254, 83, 16711422);

            pPoseStack.popPose();
            pPoseStack.pushPose();
            pPoseStack.scale(0.35f, 0.35f, 0.35f);

            ScreenUtil.drawText(Component.literal("Processing").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 404, 57, 16711422);

            ScreenUtil.drawText(Component.literal("Energy").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 404, 101, 16711422);

            ScreenUtil.drawText(Component.literal("Sensor").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 404, 145, 16711422);

        }
        if (ClientConfig.machineSettingsWindow.get() == MachineSettingsWindow.SIDES) {
            ScreenUtil.drawCenteredText(Component.literal("Sides IO").withStyle(ScreenUtil::notoSans), graphics, getSizeX() + 148, 2, 11184810);
        }
        pPoseStack.popPose();

    }

    public boolean isSpaceAbove() {
        return this.getTile().getLevel().getBlockState(this.getTile().getBlockPos().above()).is(Blocks.AIR);
    }

    protected void changeRedstoneMode() {
        PacketHandler.sendToServer(new MachineRedstoneModePacket(getTile().getBlockPos()));
        sendChangedPacket();
    }

    protected void changePowerable(boolean state) {
        PacketHandler.sendToServer(new MachinePowerablePacket(getTile().getBlockPos(), state));
        sendChangedPacket();
    }

    protected void changeDumpMode(String tag) {
        PacketHandler.sendToServer(new MachineDumpModePacket(getTile().getBlockPos(), tag));
        sendChangedPacket();
    }

    protected void openUpgrades() {
        ClientConfig.machineSettingsWindow.set(ClientConfig.machineSettingsWindow.get() != MachineSettingsWindow.UPGRADES ? MachineSettingsWindow.UPGRADES : MachineSettingsWindow.NONE);
    }

    protected void openSidesConfig() {
        ClientConfig.machineSettingsWindow.set(ClientConfig.machineSettingsWindow.get() != MachineSettingsWindow.SIDES ? MachineSettingsWindow.SIDES : MachineSettingsWindow.NONE);
    }

    protected void sendChangedPacket() {
        PacketHandler.sendToServer(new MachineChangedPacket(getTile().getBlockPos()));
    }

    public void appendHoverText(GuiGraphics graphics, int x, int y, String[] texts) {
        List<Component> list = new ArrayList<>();
        for (String text : texts)
            if (!text.equals("")) list.add(Component.literal(text));
        graphics.renderComponentTooltip(Minecraft.getInstance().font, list, x - leftPos, y - topPos);
    }

    public void appendHoverText(GuiGraphics graphics, int x, int y, Component[] texts) {
        List<Component> list = new ArrayList<>();
        for (Component text : texts)
            if (!text.getString().equals("")) list.add(text);
        graphics.renderComponentTooltip(Minecraft.getInstance().font, list, x - leftPos, y - topPos);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTicks, int x, int y) {
        super.renderBg(graphics, partialTicks, x, y);
        refreshWidgets();
    }

    @Override
    public void onClose() {
        getTile().setChanged();
        super.onClose();
    }

    @Override
    public int getSizeX() {
        return 0;
    }

    @Override
    public int getSizeY() {
        return 0;
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    public BlockEntity getTile() {
        return this.getMenu().getTile();
    }

    public boolean supportsSpeedUpgrade() {
        return false;
    }

    public boolean supportsPowerUpgrade() {
        return false;
    }

    public boolean supportsProcessingUpgrade() {
        return false;
    }

}
