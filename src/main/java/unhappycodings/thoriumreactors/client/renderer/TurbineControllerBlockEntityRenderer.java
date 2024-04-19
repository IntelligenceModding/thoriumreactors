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
        boolean horizontal = false;
        if (level.getBlockState(entity.getBlockPos().relative(Direction.UP, 1)).is(ModBlocks.TURBINE_ROTATION_MOUNT.get())) {
            rotorPos = entity.getBlockPos().relative(Direction.UP, 1);
            horizontal = true;
        }

        for (int i = 0; i < 9; i++) {
            BlockPos loopPos = rotorPos.relative(horizontal ? entity.getBlockState().getValue(TurbineControllerBlock.FACING).getOpposite() : Direction.UP, i);
            BlockState loopState = level.getBlockState(loopPos);
            if (loopState.is(ModBlocks.TURBINE_ROTOR.get()) && loopState.getValue(TurbineRotorBlock.RENDERING) != entity.isAssembled()) {
                level.setBlockAndUpdate(loopPos, loopState.setValue(TurbineRotorBlock.RENDERING, entity.isAssembled()));
            }
        }

        if (entity.isAssembled()) {
            poseStack.pushPose();
            if (horizontal) {
                for (int i = 0; i < entity.getTurbineHeight() - 1; i++) {
                    if (facing == Direction.EAST) {
                        if (i == 0)
                            poseStack.translate(1f + getRenderOffset(OffsetType.X, facing), 0, 0.5f + getRenderOffset(OffsetType.Y, facing));
                        if (i < entity.getTurbineHeight() - 3) {
                            modelTurbine.setRotation(0, -entity.getRotation() / 5.1F, (float) (Math.PI / 2f));
                            modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelTurbine.setRotation(0, 0, 0);
                        } else {
                            modelRotor.setRotation(0, -entity.getRotation() / 5.1F, (float) (Math.PI / 2f));
                            modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelRotor.setRotation(0, 0, 0);
                        }
                        poseStack.translate(-1, 0, 0);
                    } else if (facing == Direction.WEST) {
                        if (i == 0)
                            poseStack.translate(entity.getTurbineHeight() - 3 + getRenderOffset(OffsetType.X, facing), 0, 0.5f + getRenderOffset(OffsetType.Y, facing));
                        if (i > 1) {
                            modelTurbine.setRotation(0, entity.getRotation() / 5.1F, (float) (Math.PI / 2f));
                            modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelTurbine.setRotation(0, 0, 0);
                        } else {
                            modelRotor.setRotation(0, entity.getRotation() / 5.1F, (float) (Math.PI / 2f));
                            modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelRotor.setRotation(0, 0, 0);
                        }
                        poseStack.translate(-1, 0, 0);
                    } else if (facing == Direction.SOUTH) {
                        if (i == 0)
                            poseStack.translate(0.5f, 0, 0);
                        if (i < entity.getTurbineHeight() - 3) {
                            modelTurbine.setRotation( (float) (Math.PI / 2f), 0, entity.getRotation() / 5.1F);
                            modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelTurbine.setRotation(0, 0, 0);
                        } else {
                            modelRotor.setRotation( (float) (Math.PI / 2f), 0, entity.getRotation() / 5.1F);
                            modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelRotor.setRotation(0, 0, 0);
                        }
                        poseStack.translate(0, 0, -1);
                    } else if (facing == Direction.NORTH) {
                        if (i == 0)
                            poseStack.translate(0.5f, 0, 0 + entity.getTurbineHeight());
                        if (i > 1) {
                            modelTurbine.setRotation( (float) (Math.PI / 2f), 0, -entity.getRotation() / 5.1F);
                            modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelTurbine.setRotation(0, 0, 0);
                        } else {
                            modelRotor.setRotation( (float) (Math.PI / 2f), 0, -entity.getRotation() / 5.1F);
                            modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                            modelRotor.setRotation(0, 0, 0);
                        }
                        poseStack.translate(0, 0, -1);
                    }

                }
            } else {
                poseStack.translate(0.5f + getRenderOffset(OffsetType.X, facing), -0.5f, 0.5f + getRenderOffset(OffsetType.Y, facing));
                for (int i = 0; i < entity.getTurbineHeight() - 1; i++) {
                    if (i < entity.getTurbineHeight() - 3) {
                        modelTurbine.setRotation(0, -entity.getRotation() / 5.1F, 0);
                        modelTurbine.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                        modelTurbine.setRotation(0, 0, 0);
                    } else {
                        modelRotor.setRotation(0, entity.getRotation() / 5.1F, 0);
                        modelRotor.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(new ResourceLocation(ThoriumReactors.MOD_ID, "textures/block/turbine_blades.png"))), horizontal ? getLightLevel(level, rotorPos.relative(facing.getOpposite(), 1)) : getLightLevel(level, rotorPos), pPackedOverlay, 1f, 1f, 1f, 1f);
                        modelRotor.setRotation(0, 0, 0);
                    }
                    poseStack.translate(0, 1, 0);
                }
            }
            poseStack.popPose();

            if (level.getGameTime() % 20 == 0) {
                if (entity.getTurbinetime() == 4 && entity.getRpm() > 10) {
                    showVenting(entity, horizontal);
                }

            } else if (level.getGameTime() % 7 == 0) {
                if (level.getGameTime() % 14 == 0 && entity.getLastRpm() < entity.getRpm()) {
                    for (int i = 0; i < 3; i++) {
                        showFloorParticles(entity, horizontal);
                    }
                }

                if (entity.getLastRpm() < entity.getRpm() && entity.getRpm() > 10) {
                    showSteamParticles(entity, horizontal);
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

    public static void showVenting(TurbineControllerBlockEntity entity, boolean horizontal) {
        Level level = entity.getLevel();
        Random random = new Random();
        Direction direction = entity.getBlockState().getValue(TurbineControllerBlock.FACING);
        if (!horizontal) {
            // north
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 0, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, -0.1);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, -0.1);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, -0.1);
            // south
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 0, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 4, 0, -0.1, 0.1);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 4, 0, -0.1, 0.1);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 4, 0, -0.1, 0.1);
            // east
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 1, 0.1, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 2, 0.1, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 + 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 3, 0.1, -0.1, 0);
            // west
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 1, -0.1, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 2, -0.1, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1 - 2, entity.getBlockPos().getY() + entity.getTurbineHeight() - 1, entity.getBlockPos().getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 3, -0.1, -0.1, 0);
        } else if (direction == Direction.WEST || direction == Direction.EAST) {
            BlockPos horizontalPos = entity.getBlockPos();
            horizontalPos = horizontalPos.relative(direction.getOpposite(), entity.getTurbineHeight() - (direction == Direction.WEST ? 2 : 4));

            // up
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 1, 0, 0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 2, 0, 0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 3, 0, 0.1, 0);

            // down
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 1, 0, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 2, 0, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1 + 3, 0, -0.1, 0);

            // north
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 0.5f, horizontalPos.getZ() - random.nextFloat() - 2, 0, 0, -0.1);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 1.5f, horizontalPos.getZ() - random.nextFloat() - 2, 0, 0, -0.1);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 2.5f, horizontalPos.getZ() - random.nextFloat() - 2, 0, 0, -0.1);

            // south
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 0.5f, horizontalPos.getZ() - random.nextFloat() + 4, 0, 0, 0.1);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 1.5f, horizontalPos.getZ() - random.nextFloat() + 4, 0, 0, 0.1);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 2.5f, horizontalPos.getZ() - random.nextFloat() + 4, 0, 0, 0.1);

        } else {
            BlockPos horizontalPos = entity.getBlockPos();
            horizontalPos = horizontalPos.relative(direction.getOpposite(), entity.getTurbineHeight() - (direction == Direction.SOUTH ? 5 : 1));

            // up
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, 0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1, horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, 0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 2, horizontalPos.getY() + 4, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, 0.1, 0);

            // down
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction), horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 1, horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 2, horizontalPos.getY() - 1, horizontalPos.getZ() - random.nextFloat() + getRenderOffset(OffsetType.Y, direction) - 1, 0, -0.1, 0);

            // west
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) - 2, horizontalPos.getY() + 0.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), -0.1, 0, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) - 2, horizontalPos.getY() + 1.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), -0.1, 0, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) - 2, horizontalPos.getY() + 2.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), -0.1, 0, 0);

            // east
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 4, horizontalPos.getY() + 0.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), 0.1, 0, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 4, horizontalPos.getY() + 1.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), 0.1, 0, 0);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() - random.nextFloat() + getRenderOffset(OffsetType.X, direction) + 4, horizontalPos.getY() + 2.5f, horizontalPos.getZ() - random.nextFloat() + (direction == Direction.NORTH ? 1 : -3), 0.1, 0, 0);


        }
    }

    public static void showSteamParticles(TurbineControllerBlockEntity entity, boolean horizontal) {
        Level level = entity.getLevel();
        Random random = new Random();
        Direction direction = entity.getBlockState().getValue(TurbineControllerBlock.FACING);

        if (!horizontal) {
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() + 0.5f + random.nextFloat() + getRenderOffset(OffsetType.X, direction), entity.getBlockPos().getY(), entity.getBlockPos().getZ() + 0.5f + random.nextFloat() + getRenderOffset(OffsetType.Y, direction), 0, random.nextFloat() / 3, 0);
            level.addParticle(ParticleTypes.CLOUD, true, entity.getBlockPos().getX() + 0.5f - random.nextFloat() + getRenderOffset(OffsetType.X, direction), entity.getBlockPos().getY(), entity.getBlockPos().getZ() + 0.5f - random.nextFloat() + getRenderOffset(OffsetType.Y, direction), 0, random.nextFloat() / 3, 0);
        } else {
            BlockPos horizontalPos = entity.getBlockPos();
            horizontalPos = horizontalPos.relative(direction.getOpposite(), direction == Direction.EAST || direction == Direction.WEST ? 2 : -1).above();
            float x = 0;
            float y = 0;
            switch (direction) {
                case NORTH -> y = (random.nextFloat() / 3);
                case EAST -> x = -(random.nextFloat() / 3);
                case SOUTH -> y = -(random.nextFloat() / 3);
                case WEST -> x = (random.nextFloat() / 3);
            }
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() + 0.5f + random.nextFloat(), horizontalPos.getY()+ 0.5f + random.nextFloat(), horizontalPos.getZ() + 0.5f + getRenderOffset(OffsetType.Y, direction), x, 0, y);
            level.addParticle(ParticleTypes.CLOUD, true, horizontalPos.getX() + 0.5f - random.nextFloat(), horizontalPos.getY()+ 0.5f - random.nextFloat(), horizontalPos.getZ() + 0.5f + getRenderOffset(OffsetType.Y, direction), x, 0, y);
        }

    }

    public static void showFloorParticles(TurbineControllerBlockEntity entity, boolean horizontal) {
        float xOffset = getRenderOffset(OffsetType.X, entity.getBlockState().getValue(TurbineControllerBlock.FACING));
        float yOffset = getRenderOffset(OffsetType.Y, entity.getBlockState().getValue(TurbineControllerBlock.FACING));
        Direction direction = entity.getBlockState().getValue(TurbineControllerBlock.FACING);
        boolean isRotated = direction == Direction.NORTH || direction == Direction.SOUTH;
        BlockPos pos = entity.getBlockPos();
        if (horizontal) {
            pos = pos.relative(direction.getOpposite(), 1).relative(direction.getClockWise(), -1);
            if (direction == Direction.WEST || direction == Direction.NORTH) {
                pos = pos.relative(direction.getClockWise(), 2);
            }
        }
        Level level = entity.getLevel();

        for (float i = 0; i < 3; i += 0.5f) {
            for (float e = 0; e < 3; e += 0.5f) {
                level.addParticle(ParticleTypes.CLOUD, true, pos.getX() + 0.2f + (horizontal ? isRotated ? -e : 0 : xOffset + i - 1), pos.getY() + (horizontal ? 0 + i : 0.2), pos.getZ() + 0.2f + (horizontal ? isRotated ? 0 : e : yOffset + e - 1), 0, 0, 0);
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
