package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

public class ReactorControllerBlockEntityRenderer implements BlockEntityRenderer<ReactorControllerBlockEntity> {

    public ReactorControllerBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {

    }

    @Override
    public void render(@NotNull ReactorControllerBlockEntity entity, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));

        if (entity.getReactorHeight() > 0 && entity.isAssembled()) {
            float height = ((float) entity.getFluidAmountIn() / (float) entity.getReactorCapacity()) * (entity.getReactorHeight() - 1f);
            float heightOut = ((float) entity.getFluidAmountOut() / (float) entity.getReactorCapacity()) * (entity.getReactorHeight() - 1f);

            BlockPos pos = entity.getBlockPos().above();
            Level level = entity.getLevel();
            int brightness = LightTexture.pack(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos));

            Direction direction = entity.getBlockState().getValue(ReactorControllerBlock.FACING);
            if (entity.getFluidAmountIn() > 20)
                drawBox(poseStack, boxVertexConsumer, new FluidStack(ModFluids.FLOWING_MOLTEN_SALT.get(), 1), height, getFluidRenderOffset(FluidOffsetType.X, direction), 0.1f * height * 5, getFluidRenderOffset(FluidOffsetType.Y, direction), 15.999f, height, 15.999f, 0, 0, 16, 16, 0, true, brightness);
            if (entity.getFluidAmountOut() > 20)
                drawBox(poseStack, boxVertexConsumer, new FluidStack(ModFluids.FLOWING_HEATED_MOLTEN_SALT.get(), 1), heightOut, getFluidRenderOffset(FluidOffsetType.X, direction), 0.1f * heightOut * 5, getFluidRenderOffset(FluidOffsetType.Y, direction), 15.999f, heightOut, 15.999f, 0, 0, 16, 16, height - 0.01f, false, brightness);
        }

    }

    public static void drawBox(PoseStack stack, VertexConsumer buffer, FluidStack fluidStack, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float yHeightOffset, boolean renderDown, int pPackedLight) {
        stack.pushPose();
        sX = sX / 16;
        sY = sY / 16;
        sZ = sZ / 16;
        pX = pX / 16;
        pY = pY / 16;
        pZ = pZ / 16;

        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.UP, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        if (renderDown)
            drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.DOWN, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.RIGHT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.LEFT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.FRONT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        drawPlane(stack, buffer, fluidStack, RenderUtil.Perspective.BACK, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yHeightOffset, pPackedLight);
        stack.popPose();
    }

    public static void drawPlane(PoseStack stack, VertexConsumer buffer, FluidStack fluidStack, RenderUtil.Perspective perspective, float height, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float yHeightOffset, int pPackedLight) {
        stack.pushPose();
        Matrix4f matrix4f = stack.last().pose();
        TextureAtlasSprite icon = RenderUtil.getStillFluidSprite(fluidStack);
        if (icon == null) return;

        sX = sX / 2;
        sY = sY / 2;
        sZ = sZ / 2;

        float u1 = icon.getU(pUOffset);
        float u2 = icon.getU(pWidth);
        float v1 = icon.getV(pVOffset);
        float v2 = icon.getV(pHeight);

        int color = RenderUtil.getColorTint(fluidStack);

        if (perspective == RenderUtil.Perspective.UP) {
            // middle
            Integer[] positionIntegers = {0, 0, -1, -1, 0, -1, 1, -1, 1, 1, 1, 0, 0, 1, -1, 1, -1, 0};
            for (int i = 0; i < positionIntegers.length; i += 2) {
                int valueA = positionIntegers[i];
                int valueB = positionIntegers[i + 1];
                buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset - 0.01f + height, sZ + pZ + valueB).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset - 0.01f + height, sZ + pZ + valueB).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset - 0.01f + height, -sZ + pZ + valueB).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();
                buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset - 0.01f + height, -sZ + pZ + valueB).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 1f, 1f).endVertex();

            }
        }
        if (perspective == RenderUtil.Perspective.DOWN) {
            Integer[] positionIntegers = {0, 0, -1, -1, 0, -1, 1, -1, 1, 1, 1, 0, 0, 1, -1, 1, -1, 0};
            for (int i = 0; i < positionIntegers.length; i += 2) {
                int valueA = positionIntegers[i];
                int valueB = positionIntegers[i + 1];
                buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset + 0.001f, sZ + pZ + valueB).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset + 0.001f, -sZ + pZ + valueB).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset + 0.001f, -sZ + pZ + valueB).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
                buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset + 0.001f, sZ + pZ + valueB).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, -0, 0f).endVertex();
            }
        }
        Integer[] positionIntegers = {+1, 0, -1};
        for (int valueA : positionIntegers) {
            float tempHeight = height;
            for (int j = 0; j < Math.ceil(height); j++) {
                float curHeight = tempHeight < 1 ? tempHeight : 1;
                float size = tempHeight > 1 ? 16 : tempHeight * 16;
                float pY1 = yHeightOffset -sY + pY + (j + 1 * curHeight) - (j + 1 == Math.ceil(height) ? 0.01f : 0f);
                if (perspective == RenderUtil.Perspective.BACK) {
                    v2 = icon.getV(size);
                    buffer.vertex(matrix4f, sX + pX + 0.999f, pY1, sZ + pZ + valueA).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f, pY1, -sZ + pZ + valueA).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f, yHeightOffset + j, -sZ + pZ + valueA).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + 0.999f, yHeightOffset + j, sZ + pZ + valueA).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                }
                if (perspective == RenderUtil.Perspective.FRONT) {
                    v2 = icon.getV(size);
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, pY1, sZ + pZ + valueA).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, yHeightOffset + j, sZ + pZ + valueA).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, yHeightOffset + j, -sZ + pZ + valueA).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX - 0.999f, pY1, -sZ + pZ + valueA).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(1f, 0f, 0f).endVertex();
                }
                if (perspective == RenderUtil.Perspective.RIGHT) {
                    u2 = icon.getU(size);
                    buffer.vertex(matrix4f, -sX + pX + valueA, pY1, -sZ + pZ - 0.999f).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset + j, -sZ + pZ - 0.999f).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset + j, -sZ + pZ - 0.999f).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, pY1, -sZ + pZ - 0.999f).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                }
                if (perspective == RenderUtil.Perspective.LEFT) {
                    u2 = icon.getU(size);
                    buffer.vertex(matrix4f, -sX + pX + valueA, pY1, sZ + pZ + 0.999f).color(color).uv(u1, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, pY1, sZ + pZ + 0.999f).color(color).uv(u1, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, sX + pX + valueA, yHeightOffset + j, sZ + pZ + 0.999f).color(color).uv(u2, v1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                    buffer.vertex(matrix4f, -sX + pX + valueA, yHeightOffset + j, sZ + pZ + 0.999f).color(color).uv(u2, v2).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pPackedLight).normal(0f, 1f, 0f).endVertex();
                }

                tempHeight = tempHeight - curHeight;
            }
        }
        stack.popPose();
    }

    public int getFluidRenderOffset(FluidOffsetType type, Direction direction) {
        return switch (direction) {
            case DOWN, UP -> 0;
            case NORTH -> type == FluidOffsetType.X ? 8 : 40;
            case SOUTH -> type == FluidOffsetType.X ? 8 : -24;
            case WEST -> type == FluidOffsetType.X ? 40 : 8;
            case EAST -> type == FluidOffsetType.X ? -24 : 8;
        };
    }

    enum FluidOffsetType {
        X,
        Y;
    }

}
