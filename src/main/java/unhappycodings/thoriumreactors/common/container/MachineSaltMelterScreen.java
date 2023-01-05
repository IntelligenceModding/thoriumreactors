package unhappycodings.thoriumreactors.common.container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
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
import unhappycodings.thoriumreactors.common.blockentity.MachineSaltMelterBlockEntity;
import unhappycodings.thoriumreactors.common.container.base.BaseScreen;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toserver.MachineChangedPacket;
import unhappycodings.thoriumreactors.common.network.toserver.MachineDumpModePacket;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MachineSaltMelterScreen extends BaseScreen<MachineSaltMelterContainer> {
    private MachineSaltMelterContainer container;
    private final int[][] positionList = {
            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 26, 26, 27, 28, 29, 30,  31, 32, 33, 34, 35, 36,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 24, 25, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 36, 37,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0,  0,  0,  0,  0,  0, 23, 24, 24, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 37, 38, 38,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0,  0,  0,  0,  0, 22, 23, 23, 24, 24, 25,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 36, 37, 38, 38, 39, 39,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0,  0,  0,  0, 21, 22, 22, 23, 23,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 38, 39, 39, 40, 40,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0,  0, 20, 20, 21, 21, 22,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 40, 40, 41, 41, 42,  0,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0,  0, 19, 19, 20, 20, 21,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 41, 41, 42, 42, 43,  0,  0,  0,  0,  0,  0,  0},
            {0,  0,  0, 18, 18, 19, 19,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 42, 43, 43, 44,  0,  0,  0,  0,  0,  0},
            {0,  0,  0, 17, 18, 18,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 43, 44, 44,  0,  0,  0,  0,  0,  0},
            {0,  0, 16, 17, 18, 18,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 44, 45, 45, 45,  0,  0,  0,  0,  0},
            {0, 16, 16, 17, 17,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 45, 46, 46, 46,  0,  0,  0,  0},
            {0, 15, 15, 16,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 46, 46, 47,  0,  0,  0,  0},
            {0, 14, 15, 15,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 47, 47, 47,  0,  0,  0,  0},
            {0, 13, 14, 14,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 48, 48,  48,  0,  0,  0,  0},
            {13, 13, 13, 14,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 49, 49, 49, 49,  0,  0,  0},
            {12, 12, 12,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 50, 50, 50,  0,  0,  0},
            {11, 11, 11,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 51, 51, 51,  0,  0,  0},
            {10, 10, 10,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 52, 52, 52,  0,  0,  0},
            { 9,  9,  9,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 53, 53, 53,  0,  0,  0},
            { 8,  8,  8,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 54, 54, 54,  0,  0,  0},
            { 7,  7,  7,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 55, 55, 55,  0,  0,  0},
            { 6,  6,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 56, 56, 56,  0,  0,  0},
            { 5,  5,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 57, 57, 57, 57, 57, 57, 57, 57, 57},
            { 4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 58, 58, 58, 58, 58, 58, 58,  0},
            { 3,  3,  3,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 59, 59, 59, 59, 59,  0,  0},
            { 2,  2,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 60, 60, 60,  0,  0,  0},
            { 1,  1,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 61,  0,  0,  0,  0}};


    public MachineSaltMelterScreen(MachineSaltMelterContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.container = screenContainer;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);
        MachineSaltMelterBlockEntity entity = this.container.getTile();
        renderFluid(getGuiLeft() + 118, getGuiTop() + 85, 66, 16, entity.getMoltenSaltOut(), entity.getMaxMoltenSaltOut());
        renderFluid(getGuiLeft() + 118 + 16, getGuiTop() + 85, 66, 2, entity.getMoltenSaltOut(), entity.getMaxMoltenSaltOut());

        resetGuiTextures();
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Left Tank

        int height = entity.getMaxRecipeTime() != 0 ? 61 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 61) : 0;

        for (int xIndex = 0; xIndex < positionList.length; xIndex++) {
            for (int yIndex = 0; yIndex < positionList[xIndex].length; yIndex++) {
                if (positionList[xIndex][yIndex] <= height && positionList[xIndex][yIndex] != 0) {
                    blit(matrixStack, getGuiLeft() + 67 + yIndex, getGuiTop() + 23 + xIndex, 184, 0, 1, 1);
                }
            }
        }

        if (entity.getState()) blit(matrixStack, getGuiLeft() + 81, getGuiTop() + 89, 177, 8, 6, 1); // Power Indicator - Green
        else blit(matrixStack, getGuiLeft() + 88, getGuiTop() + 89, 177, 11, 6, 1); // Power Indicator - Green
    }

    public void resetGuiTextures() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, getTexture());
    }

    public void renderFluid(int x, int y, int h, int w, int volumeFilled, int volumeTotal) {
        FluidStack stack = new FluidStack(ModFluids.SOURCE_MOLTEN_SALT.get(), 1);
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
        MachineSaltMelterBlockEntity entity = this.container.getTile();

        drawText(Component.literal("Inventory").getString(), pPoseStack, 8, 106);
        drawCenteredText(Component.literal("Salt Melter").getString(), pPoseStack, 87, 4);
        drawCenteredText(Component.literal(entity.getState() ? "RUNNING" : "IDLE").getString(), pPoseStack, 87, 78, 4182051);

        if (mouseInArea(getGuiLeft() + 153, getGuiTop() + 25, getGuiLeft() + 161, getGuiTop() + 62, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal(formatEnergy(entity.getEnergy()) + " / " + formatEnergy(entity.getCapacity())));
            list.add(Component.literal(formatPercentNum(entity.getEnergy(), entity.getCapacity(), true)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 119, getGuiTop() + 20, getGuiLeft() + 136, getGuiTop() + 85, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            if (entity.getMoltenSaltOut() > 0)
                list.add(Component.literal("Fluid: Molten Salt"));
            list.add(Component.literal(entity.getMoltenSaltOut() + " mb / " + entity.getMaxMoltenSaltOut() + " mb"));
            list.add(Component.literal(formatPercentNum(entity.getMoltenSaltOut(), entity.getMaxMoltenSaltOut(), true)));
            this.renderComponentTooltip(pPoseStack, list, pMouseX - leftPos, pMouseY - topPos);
        }

        if (mouseInArea(getGuiLeft() + 66, getGuiTop() + 22, getGuiLeft() + 108, getGuiTop() + 67, pMouseX, pMouseY)) {
            List<Component> list = new ArrayList<>();
            list.add(Component.literal("Melting Process: " + formatPercentNum(Math.abs(entity.getRecipeTime() - entity.getMaxRecipeTime()), entity.getMaxRecipeTime(), false)));
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
        return new ResourceLocation(ThoriumReactors.MOD_ID, "textures/gui/salt_melter_gui.png");
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
