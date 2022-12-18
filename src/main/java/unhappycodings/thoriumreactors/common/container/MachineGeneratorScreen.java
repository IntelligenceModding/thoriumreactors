package unhappycodings.thoriumreactors.common.container;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseScreen;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MachineGeneratorScreen extends BaseScreen<MachineGeneratorContainer> {
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
        int burnBlitSize = (int) Math.floor(14 * (entity.getFuel() / entity.getMaxFuel()));

        blit(matrixStack, getGuiLeft() + 18, getGuiTop() + 64 + 13 - burnBlitSize, 176, 13 - burnBlitSize, 14, burnBlitSize + (burnBlitSize > 0 ? 1 : 0));
        blit(matrixStack, getGuiLeft() + 146, getGuiTop() + 22 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize);
    }

    public boolean mouseInArea(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
        int differenceX = x2 - x1 + 1;
        int differenceY = y2 - y1 + 1;
        boolean isXOver = false;
        boolean isYOver = false;
        for (int i = x1; i < x1 + differenceX; i++)
            for (int e = y1; e < y1 + differenceY; e++) {
                if (i == mouseX && !isXOver) isXOver = true;
                if (e == mouseY && !isYOver) isYOver = true;
            }
        return isXOver && isYOver;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        MachineGeneratorBlockEntity entity = this.container.getTile();
        SimpleDateFormat format = new SimpleDateFormat("mm'm' ss's'");
        float fuel = entity.getFuel() / 20 * 1000 + (entity.getFuel() > 0 ? 1000 : 0);
        drawText(Component.literal("Generator").getString(), pPoseStack, 62, 6);
        drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 94);
        drawText(Component.literal("Fuel: " + format.format(fuel)).getString(), pPoseStack, 52, 28, 4182051);
        drawText(Component.literal("Tank: " + (int) entity.getEnergy() + " FE").getString(), pPoseStack, 52, 39, 4182051);
        drawText(Component.literal("Gen: " + entity.getCurrentProduction() + " FE/t").getString(), pPoseStack, 52, 50, 4182051);
        drawText(Component.literal("     " + (entity.getCurrentProduction() * 20) + " FE/s").getString(), pPoseStack, 52, 61, 4182051);

        if (mouseInArea(getGuiLeft() + 146, getGuiTop() + 22, getGuiLeft() + 154, getGuiTop() + 59, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal(formatNum(entity.getEnergy()) + "/" + formatNum(entity.getCapacity())));
            list.add(Component.literal(formatPercentNum(entity.getEnergy(), entity.getCapacity())));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }
    }

    public String formatNum(float num) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (num >= 1000000000) return formatter.format(num / 1000).replaceAll(",", ".") + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000).replaceAll(",", ".") + " MFE";
        if (num >= 1000) return formatter.format(num / 1000).replaceAll(",", ".") + " kFE";
        return (int) num + " FE";
    }

    public String formatPercentNum(float num, float max) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        return formatter.format(num / max * 100).replaceAll(",", ".") + " %";
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
        return 186;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/generator_gui.png");
    }

    public void drawCenteredText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, 1315860);
    }

    public void drawCenteredText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, color);
    }

    public void drawText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x, y, 1315860);
    }

    public void drawText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x, y, color);
    }

}
