package unhappycodings.thoriumreactors.common.container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.gui.GuiUtil;
import unhappycodings.thoriumreactors.client.gui.widgets.ModButton;
import unhappycodings.thoriumreactors.common.blockentity.MachineElectrolyticSaltSeparatorBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseScreen;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toserver.MachineChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineDumpModePacket;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MachineElectrolyticSaltSeparatorScreen extends BaseScreen<MachineElectrolyticSaltSeparatorContainer> {
    private MachineElectrolyticSaltSeparatorContainer container;

    public MachineElectrolyticSaltSeparatorScreen(MachineElectrolyticSaltSeparatorContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void init() {
        super.init();
        addElements();
    }

    protected void addElements() {
        MachineElectrolyticSaltSeparatorBlockEntity tile = this.getMenu().getTile();
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
        MachineElectrolyticSaltSeparatorBlockEntity entity = this.container.getTile();
        renderFluid(getGuiLeft() + 36, getGuiTop() + 64, 45, 16, entity.getWaterIn(), entity.getMaxWaterIn());
        renderFluid(getGuiLeft() + 36 + 16, getGuiTop() + 64, 45, 2, entity.getWaterIn(), entity.getMaxWaterIn());
        renderFluid(getGuiLeft() + 118, getGuiTop() + 64, 23, 16, entity.getWaterOut(), entity.getMaxWaterOut());
        renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 64, 23, 2, entity.getWaterOut(), entity.getMaxWaterOut());
        if (entity.getState()) for (int i = 0; i < 64; i += 16) renderFluid(getGuiLeft() + 54 + i, getGuiTop() + 48, 1, 16, 1, 1);

        resetGuiTextures();
        int energyBlitSize = (int) Math.floor(38 / ((double) 25000 / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 9, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 42, 180, 52, 2, 22); // Right Tank
        blit(matrixStack, getGuiLeft() + 37, getGuiTop() + 20, 176, 52, 4, 45); // Left Tank
        int height = entity.getMaxRecipeTime() != 0 ? 6 - (int) Math.floor((entity.getRecipeTime() / 200f) * 6) : 0;
        blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 39 + (6 - height), 185, (6 - height), 20, height); // Top Process
        blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 52, 185, 13, 20, height); // Bottom Process

        if (entity.getEnergy() > 0) {
            blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 34, 185, 19, 21, 3); // Top Pole
            blit(matrixStack, getGuiLeft() + 77, getGuiTop() + 60, 185, 22, 21, 3); // Bottom Pole
        }

        if (entity.isInputDump()) blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 91, 185, 25, 1, 3); // Left Dump - Green
        else blit(matrixStack, getGuiLeft() + 35, getGuiTop() + 94, 185, 28, 1, 3); // Left Dump - Red

        if (entity.isOutputDump()) blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 91, 185, 25, 1, 3); // Right Dump - Green
        else blit(matrixStack, getGuiLeft() + 117, getGuiTop() + 94, 185, 28, 1, 3); // Right Dump - Red

        if (entity.getState()) blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 185, 25, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 185, 28, 6, 1); // Power Indicator - Green
    }

    public void resetGuiTextures() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, getTexture());
    }

    public void renderFluid(int x, int y, int h, int w, int volumeFilled, int volumeTotal) {
        FluidStack stack = new FluidStack(Fluids.WATER, 1);
        TextureAtlasSprite icon = getStillFluidSprite(stack);

        GuiUtil.bind(TextureAtlas.LOCATION_BLOCKS);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder tes = tesselator.getBuilder();
        tes.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();

        int color = getColorTint(stack);
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        GuiUtil.color(r,g,b,0.8f);

        int amount = (int) Math.floor(volumeFilled * ((float) h / volumeTotal));
        for (int i = 0; i < amount; i+=16) {
            int height = y - i + 1;
            int endHeight = Math.min(amount - i, 16);
            tes.vertex(x + 1, height - endHeight, 0).uv(minU, minV + (maxV - minV) * endHeight / 16F).endVertex();
            tes.vertex(x + 1, height, 0).uv(minU, minV).endVertex();
            tes.vertex(x + w + 1, height, 0).uv(minU + (maxU - minU) * w / 16F, minV).endVertex();
            tes.vertex(x + w + 1, height - endHeight, 0).uv(minU + (maxU - minU) * w / 16F, minV + (maxV - minV) * endHeight / 16F).endVertex();
        }
        tesselator.end();
        GuiUtil.reset();
    }

    private TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(fluidStack);
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }

    private int getColorTint(FluidStack ingredient) {
        Fluid fluid = ingredient.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        return renderProperties.getTintColor(ingredient);
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
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        MachineElectrolyticSaltSeparatorBlockEntity entity = this.container.getTile();

        drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 106);
        drawCenteredText(Component.literal("Electrolytic Salt Separator").getString(), pPoseStack, 87, 4);
        drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").getString(), pPoseStack, 87, 78, 4182051);

        if (mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal(formatEnergy(entity.getEnergy()) + " / " + formatEnergy(entity.getCapacity())));
            list.add(Component.literal(formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }
        if (mouseInArea(getGuiLeft() + 37, getGuiTop() + 20, getGuiLeft() + 54, getGuiTop() + 64, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            if (entity.getWaterIn() > 0)
                list.add(Component.literal("Fluid: Water"));
            list.add(Component.literal(entity.getWaterIn() + " mb / " + entity.getMaxWaterIn() + " mb"));
            list.add(Component.literal(formatPercentNum(entity.getWaterIn(), entity.getMaxWaterIn(), true)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 119, getGuiTop() + 42, getGuiLeft() + 136, getGuiTop() + 64, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            if (entity.getWaterOut() > 0)
                list.add(Component.literal("Fluid: Water"));
            list.add(Component.literal(entity.getWaterOut() + " mb / " + entity.getMaxWaterOut() + " mb"));
            list.add(Component.literal(formatPercentNum(entity.getWaterOut(), entity.getMaxWaterOut(), true)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 78, getGuiTop() + 39, getGuiLeft() + 96, getGuiTop() + 57, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Separation Process: " + formatPercentNum(Math.abs(entity.getRecipeTime() - entity.getMaxRecipeTime()), entity.getMaxRecipeTime(), false)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 34, getGuiTop() + 90, getGuiLeft() + 36, getGuiTop() + 97, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Auto Dump: " + entity.isInputDump()));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 116, getGuiTop() + 90, getGuiLeft() + 118, getGuiTop() + 97, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Auto Dump: " + entity.isOutputDump()));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 37, getGuiTop() + 90, getGuiLeft() + 55, getGuiTop() + 97, pMouseX, pMouseY) || mouseInArea(getGuiLeft() + 119, getGuiTop() + 90, getGuiLeft() + 137, getGuiTop() + 97, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Dump stored liquid instantly"));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

    }

    private void changeDumpMode(String tag) {
        PacketHandler.sendToServer(new MachineDumpModePacket(this.getMenu().getTile().getBlockPos(), tag));
        sendChangedPacket();
    }

    public void sendChangedPacket() {
        PacketHandler.sendToServer(new MachineChangedPacket(this.getMenu().getTile().getBlockPos()));
    }

    public String formatEnergy(float num) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        if (num >= 1000000000) return formatter.format(num / 1000).replaceAll(",", ".") + " GFE";
        if (num >= 1000000) return formatter.format(num / 1000).replaceAll(",", ".") + " MFE";
        if (num >= 1000) return formatter.format(num / 1000).replaceAll(",", ".") + " kFE";
        return (int) num + " FE";
    }

    public String formatPercentNum(float num, float max, boolean showDecimals) {
        DecimalFormat formatter = new DecimalFormat(showDecimals ? "0.00" : "0");
        String formatted = formatter.format(num / max * 100).replaceAll(",", ".") + " %";
        return formatted.equals("NaN %") ? "0 %" : formatted;
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
        return 195;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/electrolytic_salt_separator_gui.png");
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
