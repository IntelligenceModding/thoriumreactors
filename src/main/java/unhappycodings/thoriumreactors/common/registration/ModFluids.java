package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.fluid.ModFluidTypes;

public class ModFluids {

    private ModFluids() {
        throw new IllegalStateException("ModFluids class");
    }

    public static final RegistryObject<FlowingFluid> SOURCE_MOLTEN_SALT = Registration.FLUIDS.register("molten_salt_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_SALT = Registration.FLUIDS.register("flowing_molten_salt",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_SALT_PROPERTIES));

    public static final ForgeFlowingFluid.Properties MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SALT, SOURCE_MOLTEN_SALT, FLOWING_MOLTEN_SALT)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(ModBlocks.MOLTEN_SALT_BLOCK);

}
