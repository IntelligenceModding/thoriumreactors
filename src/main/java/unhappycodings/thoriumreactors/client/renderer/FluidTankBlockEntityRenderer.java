package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;

public class FluidTankBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<FluidTankBlockEntity> {

    public FluidTankBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull FluidTankBlockEntity entity, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));

        if (entity.getFluidAmountIn() > 0) {
            float height = ((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * 0.94f;
            drawBox(poseStack, boxVertexConsumer, entity.getFluidIn(), 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, height, 0.032f);
        }
    }

    public static void drawBox(PoseStack stack, VertexConsumer buffer, FluidStack fluid, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float height, float yOffset) {
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

        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.UP, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.DOWN, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.RIGHT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.LEFT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.FRONT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        drawPlane(stack, buffer, fluid, RenderUtil.Perspective.BACK, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset);
        stack.popPose();
    }

    public static void drawPlane(PoseStack stack, VertexConsumer buffer, FluidStack fluidStack, RenderUtil.Perspective perspective, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float yHeightOffset) {
        stack.pushPose();
        Matrix4f matrix4f = stack.last().pose();
        TextureAtlasSprite icon = unhappycodings.thoriumreactors.common.util.RenderUtil.getStillFluidSprite(fluidStack);

        sX = sX / 2;
        sY = sY / 2;
        sZ = sZ / 2;

        float u1 = icon.getU(pUOffset);
        float u2 = icon.getU(pWidth);
        float v1 = icon.getV(pVOffset);
        float v2 = icon.getV(pHeight);

        int color = unhappycodings.thoriumreactors.common.util.RenderUtil.getColorTint(fluidStack);

        if (perspective == RenderUtil.Perspective.UP) {
            // middle
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset - 0.01f + height, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset - 0.01f + height, sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset - 0.01f + height, -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset - 0.01f + height, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
        }
        if (perspective == RenderUtil.Perspective.DOWN) {
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset, sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, -sX + pX, yHeightOffset, -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset, -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
            buffer.vertex(matrix4f, sX + pX, yHeightOffset, sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
        }
        float tempHeight = height;
        for (int j = 0; j < Math.ceil(height); j++) {
            float curHeight = tempHeight < 1 ? tempHeight : 1;
            float size = tempHeight > 1 ? 16 : tempHeight * 16;
            if (perspective == RenderUtil.Perspective.BACK) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + -sY + pY + (j +1  * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j * 1), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX,yHeightOffset + (j * 1), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.FRONT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j * 1), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j * 1), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.RIGHT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX,  yHeightOffset + (j * 1), -sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j * 1), -sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
            }
            if (perspective == RenderUtil.Perspective.LEFT) {
                v2 = icon.getV(size);
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX, yHeightOffset + (j * 1), sZ + pZ).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX, yHeightOffset + (j * 1), sZ + pZ).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
            }
            tempHeight = tempHeight - curHeight;
        }
        stack.popPose();
    }

}
