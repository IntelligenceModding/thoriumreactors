package unhappycodings.thoriumreactors.common.container.tank;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.BaseScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class FluidTankScreen extends BaseScreen<FluidTankContainer> {
    private final FluidTankContainer container;

    public FluidTankScreen(FluidTankContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        FluidTankBlockEntity entity = this.container.getTile();
        RenderUtil.renderFluid(getGuiLeft() + 15, getGuiTop() + 97, 76, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 31, getGuiTop() + 97, 76, 16, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());
        RenderUtil.renderFluid(getGuiLeft() + 47, getGuiTop() + 97, 76, 2, entity.getFluidAmountIn(), entity.getFluidCapacityIn(), entity.getFluidIn().getFluid());

        RenderUtil.resetGuiTextures(getTexture());

        blit(matrixStack, getGuiLeft() + 16, getGuiTop() + 22, 176, 0, 4, 75); // Tank
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        FluidTankBlockEntity entity = this.container.getTile();

        ScreenUtil.drawText(Component.translatable("key.categories.inventory").withStyle(ScreenUtil::notoSans), pPoseStack, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.fluid_tank.name")).withStyle(ScreenUtil::notoSans), pPoseStack, 10, 2, 11184810);
        ScreenUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(ScreenUtil::notoSans), pPoseStack, 242, 2, 11184810);

        MutableComponent amount = entity.getFluidAmountIn() == Integer.MAX_VALUE ? Component.literal(" ").append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite"))) : Component.literal(" " + entity.getFluidAmountIn() + "mb");
        MutableComponent capacity = entity.getFluidCapacityIn() == Integer.MAX_VALUE ? Component.literal(" ").append(Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.infinite"))) : Component.literal(" " + entity.getFluidCapacityIn() + "mb");
        pPoseStack.popPose();

        int textLenght = Minecraft.getInstance().font.width(entity.getFluidIn().getFluid().getFluidType().getDescription().getString());
        float textSize = textLenght > 100 ? 0.7f : 0.8f;
        pPoseStack.pushPose();
        pPoseStack.scale(textSize, textSize, textSize);
        ScreenUtil.drawCenteredText(Component.literal(entity.getFluidIn().getFluid().getFluidType().getDescription().getString()).withStyle(FormattingUtil.hex(0x55D38A).withUnderlined(true)).withStyle(ScreenUtil::notoSans), pPoseStack, textLenght < 100 ? 145 : 165, textLenght < 100 ? 28 : 32);
        pPoseStack.popPose();

        pPoseStack.pushPose();
        pPoseStack.scale(0.8f, 0.8f, 0.8f);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.text.amount")).withStyle(FormattingUtil.hex(0x0ACECE)).append(amount.withStyle(ChatFormatting.GRAY)).withStyle(ScreenUtil::notoSans), pPoseStack, 90, 46);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.text.capacity")).withStyle(FormattingUtil.hex(0xC6CC3E)).append(capacity.withStyle(ChatFormatting.GRAY)).withStyle(ScreenUtil::notoSans), pPoseStack, 90, 56);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.text.fillage")).withStyle(FormattingUtil.hex(0x7ED355)).append(Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true)).withStyle(ChatFormatting.GRAY)).withStyle(ScreenUtil::notoSans), pPoseStack, 90, 66);
        pPoseStack.popPose();

        if (ScreenUtil.mouseInArea(getGuiLeft() + 16, getGuiTop() + 21, getGuiLeft() + 49, getGuiTop() + 97, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new Component[]{entity.getFluidAmountIn() > 0 ? Component.translatable(FormattingUtil.getTranslatable("machines.tooltip.fluid")).append(" ").append(entity.getFluidIn().getFluid().getFluidType().getDescription().getString()) : Component.empty(), Component.literal(entity.getFluidAmountIn() + " mb / " + entity.getFluidCapacityIn() + " mb"), Component.literal(FormattingUtil.formatPercentNum(entity.getFluidAmountIn(), entity.getFluidCapacityIn(), true))});

    }

    public void appendHoverText(PoseStack poseStack, int x, int y, String[] texts) {
        List<Component> list = new ArrayList<>();
        for (String text : texts)
            if (!text.equals("")) list.add(Component.literal(text));
        this.renderComponentTooltip(poseStack, list, x - leftPos, y - topPos);
    }
    public void appendHoverText(PoseStack poseStack, int x, int y, Component[] texts) {
        List<Component> list = new ArrayList<>();
        for (Component text : texts)
            if (!text.getString().equals("")) list.add(Component.literal(text.getString()));
        this.renderComponentTooltip(poseStack, list, x - leftPos, y - topPos);
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
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/fluid_tank_gui.png");
    }

}
