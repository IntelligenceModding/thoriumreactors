package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.client.util.RenderUtil;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineRotorBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class TurbineControllerBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<TurbineControllerBlockEntity> {
    public float rotation = 0;

    public TurbineControllerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull TurbineControllerBlockEntity entity, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer boxVertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS));
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        if (entity.isAssembled()) {
            poseStack.pushPose();

            poseStack.translate(1f , 0, 1f);
            poseStack.mulPose(Quaternion.fromYXZ(rotation, 0, 0));

            blockRenderer.renderSingleBlock(ModBlocks.TURBINE_ROTOR.get().defaultBlockState().setValue(TurbineRotorBlock.BLADES, 4), poseStack, bufferSource, pPackedLight, pPackedOverlay, ModelData.EMPTY, RenderType.solid());

            poseStack.popPose();
            if (rotation < 6.6f   )
                rotation += 0.01f;
            else
                rotation = 0;
        }
    }

    public int getRenderOffset(OffsetType type, Direction direction) {
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 0;
            case NORTH -> type == OffsetType.X ? 8 : 40;
            case SOUTH -> type == OffsetType.X ? 8 : -24;
            case WEST -> type == OffsetType.X ? 40 : 8;
            case EAST -> type == OffsetType.X ? -24 : 8;
        };
    }

    enum OffsetType {
        X,
        Y;
    }

}
