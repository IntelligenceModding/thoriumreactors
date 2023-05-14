package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineConcentratorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

public class MachineConcentratorScreen extends MachineScreen<MachineConcentratorContainer> {
    private MachineConcentratorContainer container;

    public MachineConcentratorScreen(MachineConcentratorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineConcentratorBlockEntity tile = this.getMenu().getTile();

    }

    public Style notoSans(Style style) {
        return style.withFont(new ResourceLocation(ThoriumReactors.MOD_ID, "notosans"));
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineConcentratorBlockEntity entity = this.container.getTile();

        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank

        int height = entity.getMaxRecipeTime() != 0 ? 41 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 41) : 0;
        blit(matrixStack, getGuiLeft() + 67, getGuiTop() + 42, 184, 0, height, 11); // Progress

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 185, 25, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 185, 28, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineConcentratorBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").withStyle(this::notoSans), pPoseStack, 8, 102);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawCenteredText(Component.literal("Concentrating").withStyle(this::notoSans), pPoseStack, 38, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal("UnhappyWithoutU").withStyle(this::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").withStyle(this::notoSans), pPoseStack, 87, 78, 4182051);

        if (RenderUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatEnergy(entity.getEnergy()) + " / " + FormattingUtil.formatEnergy(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)});

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
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/concentrator_gui.png");
    }

}
