package unhappycodings.thoriumreactors.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.effect.RadiationEffect;

public class RadiationHudOverlay {

    public static final IGuiOverlay HUD_OVERLAY = (forgeGui, guiGraphics, partialTicks, x, y) -> {
        forgeGui.setupOverlayRenderState(true, false);

        Player player = Minecraft.getInstance().player;
        player.getActiveEffects().forEach(mobEffectInstance -> {
            if (mobEffectInstance.getEffect() instanceof RadiationEffect) {
                String contamination = "thoriumreactors_radiation_contamination";
                float radiation = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG).getFloat(contamination) / 2000f;



                //System.out.println("Client: " + radiation);
                renderTextureOverlay(guiGraphics, new ResourceLocation(ThoriumReactors.MOD_ID, "textures/misc/radiation.png"), 1, x, y);
            }
        });

    };

    public static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha, int x, int y) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, x, y, x, y);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
