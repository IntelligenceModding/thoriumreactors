package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.RenderUtil;

import java.text.SimpleDateFormat;

public class MachineGeneratorScreen extends MachineScreen<MachineGeneratorContainer> {
    MachineGeneratorContainer container;

    public MachineGeneratorScreen(MachineGeneratorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineGeneratorBlockEntity entity = this.container.getTile();

        int energyBlitSize = (int) Math.floor(38 / ((double) 75000 / entity.getEnergy()));
        int burnBlitSize = (int) Math.floor(14 * ((float) entity.getFuel() / entity.getMaxFuel()));

        blit(matrixStack, getGuiLeft() + 18, getGuiTop() + 64 + 13 - burnBlitSize, 176, 13 - burnBlitSize, 14, burnBlitSize + (burnBlitSize > 0 ? 1 : 0));
        blit(matrixStack, getGuiLeft() + 146, getGuiTop() + 22 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize);

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 81, 185, 14, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 81, 185, 15, 6, 1); // Power Indicator - Green
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineGeneratorBlockEntity entity = this.container.getTile();

        RenderUtil.drawText(Component.literal("Inventory").withStyle(RenderUtil::notoSans), pPoseStack, 8, 92, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        RenderUtil.drawText(Component.literal("FE Generation").withStyle(RenderUtil::notoSans), pPoseStack, 10, 2, 11184810);
        RenderUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(RenderUtil::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        RenderUtil.drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").withStyle(RenderUtil::notoSans), pPoseStack, 87, 70, 4182051);

        SimpleDateFormat format = new SimpleDateFormat("mm'm' ss's'");
        float fuel = entity.getFuel() / 20f * 1000 + (entity.getFuel() > 0 ? 1000 : 0);
        RenderUtil.drawText(Component.literal("Fuel:    ").withStyle(FormattingUtil.hex(0x0ACECE)).append(Component.literal(format.format(fuel)).withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 52, 26);
        RenderUtil.drawText(Component.literal("Tank:   ").withStyle(FormattingUtil.hex(0xC6CC3E)).append(Component.literal(entity.getEnergy() + " FE").withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 52, 37);
        RenderUtil.drawText(Component.literal("Gen:    ").withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(entity.getCurrentProduction() + " FE/t").withStyle(ChatFormatting.GRAY)).withStyle(RenderUtil::notoSans), pPoseStack, 52, 48);

        if (RenderUtil.mouseInArea(getGuiLeft() + 146, getGuiTop() + 22, getGuiLeft() + 154, getGuiTop() + 59, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{FormattingUtil.formatNum(entity.getEnergy()) + "/" + FormattingUtil.formatNum(entity.getCapacity()), FormattingUtil.formatPercentNum(entity.getEnergy(), entity.getCapacity())});
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
        return 184;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/generator_gui.png");
    }

}
