package unhappycodings.thoriumreactors.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
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

}
