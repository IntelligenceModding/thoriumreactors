package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.EnergyTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

public class EnergyTankBlockEntityRenderer implements BlockEntityRenderer<EnergyTankBlockEntity> {

    public EnergyTankBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {
    }

    protected Material getMaterial() {
        return new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation(ThoriumReactors.MOD_ID, "block/blasted_iron_chest"));
    }

    @Override
    public void render(@NotNull EnergyTankBlockEntity entity, float pPartialTick, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));
        if (entity.getEnergy() > 0) {
            float height = ((float) entity.getEnergy() / (float) entity.getEnergyCapacity()) * 0.94f;

            drawBox(stack, boxVertexConsumer, 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, height, 0.032f, pPackedLight);
        }
    }

    public static void drawBox(PoseStack stack, VertexConsumer buffer, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float height, float yOffset, int pPackedLight) {
        stack.pushPose();
        pX += 8;
        pY += sY / 2f;
        pZ += 8;
        sX = sX / 16;
        sY = sY / 16;
        sZ = sZ / 16;
        pX = pX / 16;
        pY = pY / 16;
        pZ = pZ / 16;

        drawPlane(stack, buffer, RenderUtil.Perspective.UP, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        drawPlane(stack, buffer, RenderUtil.Perspective.DOWN, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        drawPlane(stack, buffer, RenderUtil.Perspective.RIGHT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        drawPlane(stack, buffer, RenderUtil.Perspective.LEFT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        drawPlane(stack, buffer, RenderUtil.Perspective.FRONT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        drawPlane(stack, buffer, RenderUtil.Perspective.BACK, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);

        stack.popPose();
    }

    public static void drawPlane(PoseStack stack, VertexConsumer buffer, RenderUtil.Perspective perspective, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float yHeightOffset, int pPackedLight) {
        stack.pushPose();
        Matrix4f matrix4f = stack.last().pose();
        TextureAtlasSprite icon = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(new ResourceLocation(ThoriumReactors.MOD_ID, "block/energy"));

        sX = sX / 2;
        sY = sY / 2;
        sZ = sZ / 2;

        float u1 = icon.getU(pUOffset);
        float u2 = icon.getU(pWidth);
        float v1 = icon.getV(pVOffset);
        float v2 = icon.getV(pHeight);

        int color = 0xB33FD023;

        if (perspective == RenderUtil.Perspective.UP) {
            // middle
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset - 0.01f + height, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset - 0.01f + height, sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset - 0.01f + height, -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset - 0.01f + height, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
        }
        if (perspective == RenderUtil.Perspective.DOWN) {
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset, -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset, sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
        }

        float tempHeight = height;
        for (int j = 0; j < Math.ceil(height); j++) {
            float curHeight = tempHeight < 1 ? tempHeight : 1;
            float size = tempHeight > 1 ? 16 : tempHeight * 16;
            float pY1 = yHeightOffset -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f);
            if (perspective == RenderUtil.Perspective.BACK) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, sX + pX, pY1, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, pY1, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.FRONT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, pY1, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, pY1, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.RIGHT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, pY1, -sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j), -sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, pY1, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.LEFT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, pY1, sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, pY1, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j), sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
            }
            tempHeight = tempHeight - curHeight;
        }
        stack.popPose();
    }

}