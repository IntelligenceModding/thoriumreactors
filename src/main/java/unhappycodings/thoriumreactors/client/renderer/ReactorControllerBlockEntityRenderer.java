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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientReactorControllerDataPacket;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

public class ReactorControllerBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<ReactorControllerBlockEntity> {

    public ReactorControllerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    public static void drawBox(PoseStack stack, VertexConsumer buffer, FluidStack fluidStack, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight) {
        stack.pushPose();
        sX = sX / 16;
        sY = sY / 16;
        sZ = sZ / 16;
        pX = pX / 16;
        pY = pY / 16;
        pZ = pZ / 16;

        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.UP, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.DOWN, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.RIGHT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.LEFT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.FRONT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.BACK, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight);
        stack.popPose();
    }

    public static void drawPlane(PoseStack stack, VertexConsumer buffer, FluidStack fluidStack, RenderUtil.Perspective perspective, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight) {
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
            Integer[] positionIntegers = {0, 0, -1, -1, 0, -1, 1, -1, 1, 1, 1, 0, 0, 1, -1, 1, -1, 0};
            for (int i = 0; i < positionIntegers.length; i += 2) {
                int valueA = positionIntegers[i];
                int valueB = positionIntegers[i + 1];
                buffer.vertex(matrix4f, -sX + pX + valueA, 0 - 0.01f + height, sZ + pZ + valueB).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, 0 - 0.01f+ height, sZ + pZ + valueB).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, 0 - 0.01f+ height, -sZ + pZ + valueB).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, -sX + pX + valueA, 0 - 0.01f+ height, -sZ + pZ + valueB).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 1f, 1f).endVertex();

            }
        }
        if (perspective == RenderUtil.Perspective.DOWN) {
            Integer[] positionIntegers = {0, 0, -1, -1, 0, -1, 1, -1, 1, 1, 1, 0, 0, 1, -1, 1, -1, 0};
            for (int i = 0; i < positionIntegers.length; i += 2) {
                int valueA = positionIntegers[i];
                int valueB = positionIntegers[i + 1];
                buffer.vertex(matrix4f, -sX + pX + valueA, 0 + 0.01f, sZ + pZ + valueB).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX + valueA, 0 + 0.01f, -sZ + pZ + valueB).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, 0 + 0.01f, -sZ + pZ + valueB).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, 0 + 0.01f, sZ + pZ + valueB).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, -0, 0f).endVertex();
            }
         }
        if (perspective == RenderUtil.Perspective.BACK) {
            Integer[] positionIntegers = {+1, 0, -1};
            for (int i = 0; i < positionIntegers.length; i++) {
                int valueA = positionIntegers[i];
                float tempHeight = height;
                for (int j = 0; j < Math.ceil(height); j++) {
                    float curHeight = tempHeight < 1 ? tempHeight : 1;

                    buffer.vertex(matrix4f, sX + pX + 0.999f, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ + valueA).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f, -sY + pY + (j +1  * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ + valueA).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f, 0 + (j * 1), -sZ + pZ + valueA).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f,0 + (j * 1), sZ + pZ + valueA).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    tempHeight = tempHeight - curHeight;

                }
            }
        }
        if (perspective == RenderUtil.Perspective.FRONT) {
            Integer[] positionIntegers = {+1, 0, -1};
            for (int i = 0; i < positionIntegers.length; i++) {
                int valueA = positionIntegers[i];
                float tempHeight = height;
                for (int j = 0; j < Math.ceil(height); j++) {
                    float curHeight = tempHeight < 1 ? tempHeight : 1;

                    buffer.vertex(matrix4f, -sX + pX - 0.999f, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ + valueA).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, 0 + (j * 1), sZ + pZ + valueA).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, 0 + (j * 1), -sZ + pZ + valueA).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ + valueA).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(1f, 0f, 0f).endVertex();

                    tempHeight = tempHeight - curHeight;
                }
            }
        }
        if (perspective == RenderUtil.Perspective.RIGHT) {
            Integer[] positionIntegers = {+1, 0, -1};
            for (int i = 0; i < positionIntegers.length; i++) {
                int valueA = positionIntegers[i];
                float tempHeight = height;
                for (int j = 0; j < Math.ceil(height); j++) {
                    float curHeight = tempHeight < 1 ? tempHeight : 1;

                    buffer.vertex(matrix4f, -sX + pX + valueA, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ - 0.999f).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX + valueA,  0 + (j * 1), -sZ + pZ - 0.999f).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, 0 + (j * 1), -sZ + pZ - 0.999f).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), -sZ + pZ - 0.999f).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();

                    tempHeight = tempHeight - curHeight;
                }
            }
        }
        if (perspective == RenderUtil.Perspective.LEFT) {
            Integer[] positionIntegers = {+1, 0, -1};
            for (int i = 0; i < positionIntegers.length; i++) {
                int valueA = positionIntegers[i];
                float tempHeight = height;
                for (int j = 0; j < Math.ceil(height); j++) {
                    float curHeight = tempHeight < 1 ? tempHeight : 1;

                    buffer.vertex(matrix4f, -sX + pX + valueA, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ + 0.999f).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f), sZ + pZ + 0.999f).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, 0 + (j * 1), sZ + pZ + 0.999f).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX + valueA, 0 + (j * 1), sZ + pZ + 0.999f).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0xF000F0).normal(0f, 1f, 0f).endVertex();

                    tempHeight = tempHeight - curHeight;
                }
            }
        }
        stack.popPose();
    }

    @Override
    public void render(@NotNull ReactorControllerBlockEntity entity, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));

        if (entity.getReactorHeight() > 0 && entity.isAssembled()) {
            float height = ((float) entity.getFluidAmountIn() / (float) entity.getFluidCapacityIn()) * (entity.getReactorHeight() - 1);
            drawBox(poseStack, boxVertexConsumer, new FluidStack(ModFluids.FLOWING_MOLTEN_SALT.get(), 1), height, 40, 0.1f * height * 5, 8, 15.999f, height, 15.999f, 0, 0, 16, 16);
        }
    }

}
