package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.blockentity.WaterSourceBlockEntity;

public class WaterSourceBlockEntityRenderer implements BlockEntityRenderer<WaterSourceBlockEntity> {

    public WaterSourceBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {

    }

    @Override
    public void render(@NotNull WaterSourceBlockEntity entity, float pPartialTick, @NotNull PoseStack stack, @NotNull MultiBufferSource buffer, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));

        drawBox(stack, boxVertexConsumer, new FluidStack(Fluids.WATER, 1), 0, 0f, 0, 11, 0.01f, 11, 0, 0, 16, 16, 0.975f, 0.032f, pPackedLight);
    }

    public static void drawBox(PoseStack stack, VertexConsumer buffer, FluidStack fluid, float pX, float pY, float pZ, float sX, float sY, float sZ, float pUOffset, float pVOffset, float pWidth, float pHeight, float height, float yOffset, int pPackedLight) {
        stack.pushPose();
        pX += 8;
        pY += sY / 2f;
        pZ += 8;
        sX = sX / 11.01f;
        sY = sY / 11.01f;
        sZ = sZ / 11.01f;
        pX = pX / 16;
        pY = pY / 16;
        pZ = pZ / 16;

        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.UP, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.DOWN, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.RIGHT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.LEFT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.FRONT, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);
        FluidTankBlockEntityRenderer.drawPlane(stack, buffer, fluid, RenderUtil.Perspective.BACK, height, pX, pY, pZ, sX, sY, sZ, pUOffset, pVOffset, pWidth, pHeight, yOffset, pPackedLight);

        stack.popPose();
    }

}
