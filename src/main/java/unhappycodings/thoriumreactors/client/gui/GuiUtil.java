package unhappycodings.thoriumreactors.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;

public class GuiUtil {

    public static void reset() {
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static void color(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
    }

    public static void bind(ResourceLocation location) {
        RenderSystem.setShaderTexture(0, location);
    }
}
