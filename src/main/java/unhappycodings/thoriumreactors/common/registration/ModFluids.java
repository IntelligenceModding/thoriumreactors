package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.fluid.ModFluidTypes;

public class ModFluids {

    private ModFluids() {
        throw new IllegalStateException("ModFluids class");
    }

    public static final RegistryObject<FlowingFluid> SOURCE_MOLTEN_SALT = Registration.FLUIDS.register("molten_salt",
            () -> new ForgeFlowingFluid.Source(ModFluids.MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOLTEN_SALT = Registration.FLUIDS.register("flowing_molten_salt",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_HEATED_MOLTEN_SALT = Registration.FLUIDS.register("heated_molten_salt",
            () -> new ForgeFlowingFluid.Source(ModFluids.HEATED_MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HEATED_MOLTEN_SALT = Registration.FLUIDS.register("flowing_heated_molten_salt",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.HEATED_MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SOURCE_HYDROFLUORIDE = Registration.FLUIDS.register("hydrofluoride",
            () -> new ForgeFlowingFluid.Source(ModFluids.HYDROFLUORIDE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HYDROFLUORIDE = Registration.FLUIDS.register("flowing_hydrofluoride",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.HYDROFLUORIDE_PROPERTIES));

    public static final ForgeFlowingFluid.Properties MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SALT, SOURCE_MOLTEN_SALT, FLOWING_MOLTEN_SALT)
            .slopeFindDistance(1).levelDecreasePerBlock(2).block(ModBlocks.MOLTEN_SALT_BLOCK);
    public static final ForgeFlowingFluid.Properties HEATED_MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HEATED_MOLTEN_SALT, SOURCE_HEATED_MOLTEN_SALT, FLOWING_HEATED_MOLTEN_SALT)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(ModBlocks.HEATED_MOLTEN_SALT_BLOCK);
    public static final ForgeFlowingFluid.Properties HYDROFLUORIDE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HYDROFLUORIDE, SOURCE_HYDROFLUORIDE, FLOWING_HYDROFLUORIDE)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.HYDROFLUORIDE_BLOCK);

}
