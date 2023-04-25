package unhappycodings.thoriumreactors.common.container.reactor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ReactorControllerScreen extends AbstractContainerScreen<ReactorControllerContainer> {
    private ReactorControllerContainer container;

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
        addWidget(new ModButton(34, 90, 3, 8, null, null, null, tile, this, 0, 0, true));

    }

    @Override
    public void render(PoseStack pPoseStack, int x, int y, float pPartialTick) {
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

        matrixStack.popPose();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {

        renderCenterPartTexts(pPoseStack);

    }

    public void renderCenterPartTexts(PoseStack pPoseStack) {

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
        RenderUtil.drawCenteredText(Component.literal("698.8").withStyle(this::notoSans), pPoseStack, 7, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal("1272.7").withStyle(this::notoSans), pPoseStack, 76, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal("789").withStyle(this::notoSans), pPoseStack, 145, 202, 16711422);
        RenderUtil.drawCenteredText(Component.literal("29.97").withStyle(this::notoSans), pPoseStack, 215, 202, 16711422);
        pPoseStack.popPose();

        // normal text
        pPoseStack.pushPose();
        RenderUtil.drawRightboundText(Component.literal("23:14:24").withStyle(this::notoSans), pPoseStack, 196, 19, 16711422);
        RenderUtil.drawRightboundText(Component.literal("1.781 MFE").withStyle(this::notoSans), pPoseStack, 196, 38, 16711422);
        RenderUtil.drawRightboundText(Component.literal("NORMAL").withStyle(this::notoSans).withStyle(ChatFormatting.BOLD), pPoseStack, 196, 61, 43275);
        RenderUtil.drawRightboundText(Component.literal("49%").withStyle(this::notoSans), pPoseStack, 196, 82, 16711422);
        RenderUtil.drawRightboundText(Component.literal("99,997%").withStyle(this::notoSans), pPoseStack, 196, 104, 16711422);
        RenderUtil.drawRightboundText(Component.literal("0,11uSV/H").withStyle(this::notoSans), pPoseStack, 196, 125, 16711422);
        pPoseStack.popPose();

        // big text
        pPoseStack.pushPose();
        pPoseStack.scale(1.3f, 1.3f, 1.3f);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
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
