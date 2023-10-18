package unhappycodings.thoriumreactors.common.container.machine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.machine.MachineBlastFurnaceBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.screen.MachineScreen;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ScreenUtil;

public class MachineBlastFurnaceScreen extends MachineScreen<MachineBlastFurnaceContainer> {
    private MachineBlastFurnaceContainer container;

    public MachineBlastFurnaceScreen(MachineBlastFurnaceContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void addButtons() {
        super.addButtons();
        addElements();
    }

    protected void addElements() {
        MachineBlastFurnaceBlockEntity tile = this.getMenu().getTile();
        // Dump Left
        addWidget(new ModButton(34, 90, 3, 8, null, () -> changeDumpMode("input"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(37, 90, 19, 8, null, () -> changeDumpMode("dumpInput"), null, tile, this, 0, 0, true));

        // Dump Right
        addWidget(new ModButton(116, 90, 3, 8, null, () -> changeDumpMode("output"), null, tile, this, 0, 0, true));
        addWidget(new ModButton(119, 90, 19, 8, null, () -> changeDumpMode("dumpOutput"), null, tile, this, 0, 0, true));

    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineBlastFurnaceBlockEntity entity = this.container.getTile();

        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank

        int fanBlitSize = (int) Math.floor(16 / ((double) entity.getWorkingDegree() / entity.getDegree()));
        blit(matrixStack, getGuiLeft() + 79, getGuiTop() + 27 + (16 - fanBlitSize), 190, 0 + (16 - fanBlitSize), 16, fanBlitSize); // Degree

        int height = entity.getMaxRecipeTime() != 0 ? 34 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 34) : 0;
        blit(matrixStack, getGuiLeft() + 70, getGuiTop() + 48, 184, 16, height, 11); // Progress

        if (entity.getState())
            blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Red
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);
        MachineBlastFurnaceBlockEntity entity = this.container.getTile();

        ScreenUtil.drawText(Component.translatable("key.categories.inventory").withStyle(ScreenUtil::notoSans), pPoseStack, 8, 102, 11184810);
        pPoseStack.pushPose();
        pPoseStack.scale(0.7f, 0.7f, 0.7f);
        ScreenUtil.drawText(Component.translatable(FormattingUtil.getTranslatable("machines.blast_furnace.name")).withStyle(ScreenUtil::notoSans), pPoseStack, 10, 2, 11184810);
        ScreenUtil.drawRightboundText(Component.literal(Minecraft.getInstance().player.getScoreboardName()).withStyle(ScreenUtil::notoSans), pPoseStack, 242, 2, 11184810);
        pPoseStack.popPose();
        ScreenUtil.drawCenteredText(Component.translatable(entity.getState() ? FormattingUtil.getTranslatable("machines.state.running") : FormattingUtil.getTranslatable("machines.state.idle")).withStyle(ScreenUtil::notoSans), pPoseStack, 87, 78, 4182051);

        if (ScreenUtil.mouseInArea(getGuiLeft() + 79, getGuiTop() + 27, getGuiLeft() + 94, getGuiTop() + 42, pMouseX, pMouseY))
            appendHoverText(pPoseStack, pMouseX, pMouseY, new String[]{entity.getDegree() + "°C (" + FormattingUtil.formatPercentNum(entity.getDegree() - 25, entity.getWorkingDegree() - 25, false) + ")"});

        if (ScreenUtil.mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY))
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
        return 198;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/blast_furnace_gui.png");
    }

}
