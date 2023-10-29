package unhappycodings.thoriumreactors.common.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ScreenUtil {

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

    public static Style notoSans(Style style) {
        return style.withFont(new ResourceLocation(ThoriumReactors.MOD_ID, "notosans"));
    }

    public static void drawRightboundText(String text, GuiGraphics graphics, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x - (Minecraft.getInstance().font.width(text)), y, 1315860);
    }

    public static void drawRightboundText(String text, GuiGraphics graphics, int x, int y, int color) {
        drawRightboundText(Component.literal(text), graphics, x, y, color);
    }

    public static void drawRightboundText(MutableComponent text, GuiGraphics graphics, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x - (Minecraft.getInstance().font.width(text)), y, 1315860);
    }

    public static void drawRightboundText(MutableComponent text, GuiGraphics graphics, int x, int y, int color) {
        graphics.drawString(Minecraft.getInstance().font, text, x - (Minecraft.getInstance().font.width(text)), y, color);
    }

    public static void drawCenteredText(String text, GuiGraphics graphics, int x, int y) {
        drawCenteredText(Component.literal(text), graphics, x, y);
    }

    public static void drawCenteredText(String text, GuiGraphics graphics, int x, int y, int color) {
        drawCenteredText(Component.literal(text), graphics, x, y, color);
    }

    public static void drawCenteredText(MutableComponent text, GuiGraphics graphics, int x, int y) {
        graphics.drawCenteredString(Minecraft.getInstance().font, text, x, y, 1315860);
    }

    public static void drawCenteredText(MutableComponent text, GuiGraphics graphics, int x, int y, int color) {
        graphics.drawCenteredString(Minecraft.getInstance().font, text, x, y, color);
    }

    public static void drawCenteredText(MutableComponent text, GuiGraphics graphics, int x, int y, int color, boolean dropShadow) {
        FormattedCharSequence formattedcharsequence = text.getVisualOrderText();
        graphics.drawString(Minecraft.getInstance().font, formattedcharsequence, x - Minecraft.getInstance().font.width(formattedcharsequence) / 2, y, color, dropShadow);
    }

    public static void drawText(String text, GuiGraphics graphics, int x, int y) {
        drawText(Component.literal(text), graphics, x, y);
    }

    public static void drawText(MutableComponent text, GuiGraphics graphics, int x, int y) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, 1315860);
    }

    public static void drawText(String text, GuiGraphics graphics, int x, int y, int color) {
        drawText(Component.literal(text), graphics, x, y, color);
    }

    public static void drawText(MutableComponent text, GuiGraphics graphics, int x, int y, int color) {
        graphics.drawString(Minecraft.getInstance().font, text, x, y, color);
    }

}
