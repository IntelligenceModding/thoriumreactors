package unhappycodings.thoriumreactors.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineBladeModel;
import unhappycodings.thoriumreactors.client.renderer.model.TurbineRotorModel;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineControllerBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineRotorBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.Random;

public class TurbineControllerBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<TurbineControllerBlockEntity> {
    public final TurbineBladeModel<?> modelTurbine = new TurbineBladeModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineBladeModel.LAYER_LOCATION));
    public final TurbineRotorModel<?> modelRotor = new TurbineRotorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(TurbineRotorModel.LAYER_LOCATION));

    public TurbineControllerBlockEntityRenderer(BlockEntityRendererProvider.Context ignored) {

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
            poseStack.mulPose(Axis.YN.rotation(entity.getRotation() / 5.1F));
            for (int i = 0; i < entity.getTurbineHeight() - 1; i++) {
                if (i < entity.getTurbineHeight() - 3) {
                    modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                } else {
                    modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                }
                poseStack.translate(0, 1, 0);
            }
            poseStack.popPose();

            if (level.getGameTime() % 20 == 0) {
                if (entity.getTurbinetime() == 4 && entity.getRpm() > 10) {
                    showVenting(entity);
                }

                if (entity.getLastRpm() < entity.getRpm()) {
                    for (int i = 0; i < 3; i++) {
                        showFloorParticles(entity);
                    }
                }
            } else if (level.getGameTime() % 10 == 0) {
                if (entity.getLastRpm() < entity.getRpm() && entity.getRpm() > 10) {
                    showSteamParticles(entity);
                }
            }

            float rpm = entity.getRpm();
            float rps = rpm / 60f;
            float rad = (float) (rps * (Math.PI * 2f));

            if (entity.getRotation() < Math.PI * 2) {
                if (entity.getTicks() > pPartialTick) {
                    float partial = 1 - entity.getTicks() < 0 ? -(1 - entity.getTicks()) : 1 - entity.getTicks();
                    entity.setRotation(entity.getRotation() + ((rad / 20) * (partial)));
                    entity.setTicks(0);
                }
                entity.setRotation(entity.getRotation() + ((rad / 20) * (entity.getTicks() - pPartialTick)));
                entity.setTicks(pPartialTick);
            } else {
                entity.setRotation(0);
            }

            entity.setChanged();
            entity.setLastRpm(entity.getRpm());
        }
    }

    public int getLightLevel(Level level, BlockPos pos) {
        int block = level.getBrightness(LightLayer.BLOCK, pos);
        int sky = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(block, sky);
    }

    public static void showVenting(TurbineControllerBlockEntity entity) {
        Level level = entity.getLevel();
        Random random = new Random();
        // north
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 0, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1, 0, -0.1, -0.1);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1, 0, -0.1, -0.1);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1, 0, -0.1, -0.1);
        // south
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 0, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 4, 0, -0.1, 0.1);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 4, 0, -0.1, 0.1);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 4, 0, -0.1, 0.1);
        // east
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 1, 0.1, -0.1, 0);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 2, 0.1, -0.1, 0);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 3, 0.1, -0.1, 0);
        // west
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 1, -0.1, -0.1, 0);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 2, -0.1, -0.1, 0);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)) - 1 + 3, -0.1, -0.1, 0);

    }

    public static void showSteamParticles(TurbineControllerBlockEntity entity) {
        Level level = entity.getLevel();
        Random random = new Random();
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() + 0.5f + random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)), entity.getBlockPos().getY(), entity.getBlockPos().getZ() + 0.5f + random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)), 0, random.nextFloat() / 3, 0);
        level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() + 0.5f - random.nextFloat() + getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING)), entity.getBlockPos().getY(), entity.getBlockPos().getZ() + 0.5f - random.nextFloat() + getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING)), 0, random.nextFloat() / 3, 0);
    }

    public static void showFloorParticles(TurbineControllerBlockEntity entity) {
        float xOffset = getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING));
        float yOffset = getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING));
        BlockPos pos = entity.getBlockPos();
        Level level = entity.getLevel();

        for (float i = 0; i < 3; i += 0.5) {
            for (float e = 0; e < 3; e += 0.5) {
                level.addParticle(ParticleTypes.CLOUD, true, pos.getX() + 0.5f + xOffset + i - 1, pos.getY() + 0.2, pos.getZ() + 0.5f + yOffset + e - 1, 0, 0, 0);
            }
        }

    }

    public static float getRenderOffset(OffsetType type, Direction direction) {
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
