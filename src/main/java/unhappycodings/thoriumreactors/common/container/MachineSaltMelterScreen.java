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
    private final int[][] positionList = {{99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 26, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 16, 16, 16, 17, 17, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 27, 27, 27, 27, 27, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 15, 15, 16, 16, 16, 16, 16, 16, 16, 16, 16, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 27, 27, 27, 28, 28, 28, 28, 28, 28, 28, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99, 14, 14, 14, 15, 15, 15, 15, 15, 15, 15, 15, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 28, 28, 29, 29, 29, 29, 29, 29, 29, 29, 29, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 13, 13, 13, 14, 14, 14, 14, 14, 14, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 29, 29, 30, 30, 30, 30, 30, 30, 30, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 12, 13, 13, 13, 13, 13, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 30, 31, 31, 31, 31, 31, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 11, 12, 12, 12, 12, 12, 12, 12, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 31, 31, 31, 32, 32, 32, 32, 99, 99, 99, 99, 99, 99, 99, 99},
            {99, 10, 11, 11, 11, 11, 11, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 32, 32, 32, 32, 32, 33, 99, 99, 99, 99, 99, 99},
            {99, 10, 10, 10, 10, 10, 10, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 33, 33, 33, 33, 33, 33, 99, 99, 99, 99, 99, 99},
            {8, 9, 9, 9, 9, 9, 9, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 34, 34, 34, 34, 34, 34, 34, 99, 99, 99, 99, 99},
            {8, 8, 8, 8, 8, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 35, 35, 35, 35, 35, 99, 99, 99, 99, 99},
            {7, 7, 7, 7, 7, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 36, 36, 36, 36, 36, 99, 99, 99, 99, 99},
            {6, 6, 6, 6, 6, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 37, 37, 37, 37, 37, 99, 99, 99, 99, 99},
            {5, 5, 5, 5, 5, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 38, 38, 38, 38, 38, 99, 99, 99, 99, 99},
            {4, 4, 4, 4, 4, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39},
            {3, 3, 3, 3, 3, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 99},
            {2, 2, 2, 2, 2, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 41, 41, 41, 41, 41, 41, 41, 99, 99, 99, 99},
            {1, 1, 1, 1, 1, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 42, 42, 99, 99, 99, 99, 99, 99}};

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
        renderFluid(getGuiLeft() + 119, getGuiTop() + 85, 66, 16, entity.getMoltenSaltOut(), entity.getMaxMoltenSaltOut());
        renderFluid(getGuiLeft() + 119 + 16, getGuiTop() + 85, 66, 2, entity.getMoltenSaltOut(), entity.getMaxMoltenSaltOut());

        resetGuiTextures();
        int energyBlitSize = (int) Math.floor(38 / ((double) entity.getEnergyCapacity() / entity.getEnergy()));
        blit(matrixStack, getGuiLeft() + 153, getGuiTop() + 25 + (38 - energyBlitSize), 176, 14, 8, energyBlitSize); // Energy Tank
        blit(matrixStack, getGuiLeft() + 119, getGuiTop() + 22, 176, 52, 4, 63); // Left Tank

        int height = entity.getMaxRecipeTime() != 0 ? 22 - (int) Math.floor((entity.getRecipeTime() / (float) entity.getMaxRecipeTime()) * 22) : 0;
        //blit(matrixStack, getGuiLeft() + 71, getGuiTop() + 19 + (22 - height), 184, 15 + (22 - height), 32, height); // Process

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
