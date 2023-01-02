package unhappycodings.thoriumreactors.common.fluid;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.util.function.Consumer;

public class ModFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation SOAP_OVERLAY_RL = new ResourceLocation(ThoriumReactors.MOD_ID, "misc/in_molten_salt");

    //public static final RegistryObject<FluidType> MOLTEN_SALT = register("molten_salt",
    //        FluidType.Properties.create().density(3000).viscosity(6000).temperature(1300).canSwim(false).canDrown(false).motionScale(0.0023333333333333335D).fallDistanceModifier(-0.5f));

    public static final RegistryObject<FluidType> MOLTEN_SALT = Registration.FLUID_TYPES.register("molten_salt", () ->
        new FluidType(FluidType.Properties.create().density(3000).viscosity(6000).temperature(1000).motionScale(0.0023333333333333335D)) {

            @Override
            public double motionScale(Entity entity) {
                return 0.0013333333333333335D;
            }

            @Override
            public boolean canDrownIn(LivingEntity entity) {
                return false;
            }

            @Override
            public boolean canHydrate(Entity entity) {
                return false;
            }

            @Override
            public void setItemMovement(ItemEntity entity) {
                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(vec3.x * (double)0.95F, vec3.y + (double)(vec3.y < (double)0.06F ? 5.0E-4F : 0.0F), vec3.z * (double)0.95F);
            }

            @Override
            public float getFallDistanceModifier(Entity entity) {
                return Fluids.LAVA.getFluidType().getFallDistanceModifier(entity);
            }

            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    private static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still"), WATER_FLOW = new ResourceLocation("block/water_flow");

                    @Override
                    public ResourceLocation getStillTexture() {
                        return WATER_STILL;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return WATER_FLOW;
                    }

                    @Override
                    public int getTintColor() {
                        return 0xE68C8E6A;
                    }

                    @Override
                    public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                        return new Vector3f(140f / 255f, 142f / 255f, 106f / 255f);
                    }

                    @Override
                    public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                        RenderSystem.setShaderFogStart(0f);
                        RenderSystem.setShaderFogEnd(1f); // distance when the fog starts
                    }
                });
            }
        });

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return Registration.FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, SOAP_OVERLAY_RL, 0xFF8C8E6A, new Vector3f(140f / 255f, 142f / 255f, 106f / 255f), properties));
    }

}
