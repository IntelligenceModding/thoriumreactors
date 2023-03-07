package unhappycodings.thoriumreactors.common.container.base.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.container.BaseContainer;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toserver.MachineChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineDumpModePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachinePowerablePacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineRedstoneModePacket;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.util.ArrayList;
import java.util.List;

public class MachineScreen<T extends BaseContainer> extends BaseScreen<T> {
    public static final ResourceLocation POWER_OFF = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/power_off.png");
    public static final ResourceLocation POWER_ON = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/power_on.png");
    public static final ResourceLocation REDSTONE_NORMAL = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_normal.png");
    public static final ResourceLocation REDSTONE_INVERTED = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_inverted.png");
    public static final ResourceLocation REDSTONE_IGNORED = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/redstone_ignored.png");
    public static final ResourceLocation INFORMATION = new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/button/information.png");

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

    protected void addButtons() {
        MachineContainerBlockEntity tile = (MachineContainerBlockEntity) getTile();
        // Information
        addRenderableOnly(new ModButton(-18, 6, 16, 16, INFORMATION, null, null, tile, this, 16, 32, false));

        // Power Button
        lastPowerable = tile.isPowerable();
        addRenderableWidget(new ModButton(-18, 24, 16, 16, lastPowerable ? POWER_ON : POWER_OFF, () -> changePowerable(!tile.isPowerable()), null, tile, this, 16, 32, true));

        // Redstone Button
        lastRedstoneMode = tile.getRedstoneMode();
        addRenderableWidget(new ModButton(-18, 42, 16, 16, lastRedstoneMode == 0 ? REDSTONE_IGNORED : lastRedstoneMode == 1 ? REDSTONE_NORMAL : REDSTONE_INVERTED, this::changeRedstoneMode, null, tile, this, 16, 32, true));

    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        MachineContainerBlockEntity entity = (MachineContainerBlockEntity) getTile();
        if (RenderUtil.mouseInArea(getGuiLeft() + -18, getGuiTop() + 6, getGuiLeft() + -3, getGuiTop() + 21, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Usage: " + FormattingUtil.formatEnergy(entity.getState() ? entity.getNeededEnergy() : 0) + "/t"));
            list.add(Component.literal("Needs: "));
            if (entity.getEnergy() < entity.getNeededEnergy()) list.add(Component.literal("- " + FormattingUtil.formatEnergy(entity.getNeededEnergy() - entity.getEnergy()) + " Energy"));
            if (entity.getWaterIn() < entity.getFluidAmountNeeded()) list.add(Component.literal("- " + (entity.getFluidAmountNeeded() - entity.getWaterIn()) + " mb Water"));
            if (entity.getItem(1).getCount() == entity.getItem(1).getMaxStackSize()) list.add(Component.literal("- Output Space"));
            if (list.size() == 2) list.remove(1);
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (RenderUtil.mouseInArea(getGuiLeft() + -18, getGuiTop() + 24, getGuiLeft() + -3, getGuiTop() + 39, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Power: " + entity.isPowerable()));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (RenderUtil.mouseInArea(getGuiLeft() + -18, getGuiTop() + 42, getGuiLeft() + -3, getGuiTop() + 57, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Redstone: " + (lastRedstoneMode == 0 ? "Ignore" : lastRedstoneMode == 1 ? "Normal" : "Inverted")));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }
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

    protected void sendChangedPacket() {
        PacketHandler.sendToServer(new MachineChangedPacket(getTile().getBlockPos()));
    }

    public void appendHoverText(PoseStack poseStack, int x, int y, String[] texts) {
        List<Component> list = new ArrayList<>();
        for (String text : texts)
            if (!text.equals("")) list.add(Component.literal(text));
        this.renderComponentTooltip(poseStack, list, x - leftPos, y - topPos);
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
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
}
