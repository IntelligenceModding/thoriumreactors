package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineBladeModel;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineRotorModel;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;

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
            poseStack.translate(0.5f, -0.5f, 2.5f);
            poseStack.mulPose(Vector3f.YN.rotation(rotation));
            TurbineBladeModel modelTurbine = new TurbineBladeModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineBladeModel.LAYER_LOCATION));
            TurbineRotorModel modelRotor = new TurbineRotorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineRotorModel.LAYER_LOCATION));

            for (int i = 0; i < entity.getTurbineHeight() - 1; i++) {
                if (i < entity.getTurbineHeight() - 4) {
                    modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f);
                } else {
                    modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f);

                }
                poseStack.translate(0, 1, 0);
            }

            poseStack.popPose();
            if (rotation < Math.PI * 1) {
                rotation += 0.1f;
            } else {
                rotation = 0;
            }
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
