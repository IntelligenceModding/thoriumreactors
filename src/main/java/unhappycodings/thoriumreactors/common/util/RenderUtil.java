package unhappycodings.thoriumreactors.common.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import unhappycodings.thoriumreactors.client.gui.GuiUtil;

public class RenderUtil {

    public static boolean mouseInArea(int x1, int y1, int x2, int y2, int mouseX, int mouseY) {
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

    public static void drawRightboundText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text)), y, 1315860);
    }

    public static void drawRightboundText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text)), y, color);
    }

    public static void drawRightboundText(MutableComponent text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text)), y, 1315860);
    }

    public static void drawRightboundText(MutableComponent text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text)), y, color);
    }

    public static void drawCenteredText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, 1315860);
    }

    public static void drawCenteredText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, color);
    }

    public static void drawCenteredText(MutableComponent text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, 1315860);
    }

    public static void drawCenteredText(MutableComponent text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x - (Minecraft.getInstance().font.width(text) / 2f), y, color);
    }

    public static void drawText(String text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x, y, 1315860);
    }

    public static void drawText(MutableComponent text, PoseStack stack, int x, int y) {
        Minecraft.getInstance().font.draw(stack, text, x, y, 1315860);
    }

    public static void drawText(String text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x, y, color);
    }

    public static void drawText(MutableComponent text, PoseStack stack, int x, int y, int color) {
        Minecraft.getInstance().font.draw(stack, text, x, y, color);
    }

    // FLUID SECTION!

    public static void renderFluid(int x, int y, int h, int w, int volumeFilled, int volumeTotal, Fluid fluid) {
        FluidStack stack = new FluidStack(fluid, 1);
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
        GuiUtil.color(r, g, b, 0.8f);

        int amount = (int) Math.floor(volumeFilled * ((float) h / volumeTotal));
        for (int i = 0; i < amount; i += 16) {
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

    public static TextureAtlasSprite getStillFluidSprite(FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        ResourceLocation fluidStill = renderProperties.getStillTexture(fluidStack);
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
    }

    public static int getColorTint(FluidStack ingredient) {
        Fluid fluid = ingredient.getFluid();
        IClientFluidTypeExtensions renderProperties = IClientFluidTypeExtensions.of(fluid);
        return renderProperties.getTintColor(ingredient);
    }

    public static void resetGuiTextures(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
    }

}
