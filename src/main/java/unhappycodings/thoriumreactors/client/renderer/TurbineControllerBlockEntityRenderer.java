package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineBladeModel;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineRotorModel;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineControllerBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineRotorBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

public class TurbineControllerBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<TurbineControllerBlockEntity> {
    public final TurbineBladeModel<?> modelTurbine = new TurbineBladeModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineBladeModel.LAYER_LOCATION));
    public final TurbineRotorModel<?> modelRotor = new TurbineRotorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineRotorModel.LAYER_LOCATION));
    public float rotation = 0;

    public TurbineControllerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull TurbineControllerBlockEntity entity, float pPartialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int pPackedLight, int pPackedOverlay) {
        Level level = entity.getLevel();
        Direction facing = entity.getBlockState().getValue(TurbineControllerBlock.FACING);
        BlockPos rotorPos = entity.getBlockPos().relative(facing.getOpposite(), 2);

        for (int i = 0; i < 9; i++) {
            BlockPos pos = rotorPos.relative(Direction.UP, i);
            BlockState state = level.getBlockState(pos);
            if (state.is(ModBlocks.TURBINE_ROTOR.get()) && state.getValue(TurbineRotorBlock.RENDERING) != entity.isAssembled()) {
                level.setBlockAndUpdate(pos, state.setValue(TurbineRotorBlock.RENDERING, entity.isAssembled()));
            }
        }

        if (entity.isAssembled()) {
            poseStack.pushPose();
            poseStack.translate(0.5f + getRenderOffset(OffsetType.X, facing), -0.5f, 0.5f + getRenderOffset(OffsetType.Y, facing));
            poseStack.mulPose(Vector3f.YN.rotation(-entity.getRotation()));

            for (int i = 0; i < entity.getTurbineHeight() - 1; i++) {
                if (i < entity.getTurbineHeight() - 3) {
                    modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f);
                } else {
                    modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), pPackedLight, pPackedOverlay, 1f, 1f, 1f, 1f);
                }
                poseStack.translate(0, 1, 0);
            }

            poseStack.popPose();

            float rpm = 1;
            float rps = rpm / 60;
            float rad = (float) (rps * (Math.PI * 2f));
            System.out.println(rad);

            if (entity.getRotation() < Math.PI * 2) {
                entity.setRotation(entity.getRotation() + rad / 100 * pPartialTick);
            } else {
                entity.setRotation(0);
            }
            entity.setChanged();
        }
    }

    public float getRenderOffset(OffsetType type, Direction direction) {
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 0;
            case NORTH -> type == OffsetType.X ? 0 : 2;
            case SOUTH -> type == OffsetType.X ? 0 : -2;
            case WEST -> type == OffsetType.X ? 2 : 0;
            case EAST -> type == OffsetType.X ? -2 : 0;
        };
    }

    enum OffsetType {
        X,
        Y;
    }

}
