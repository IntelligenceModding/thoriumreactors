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

    public static final RegistryObject<FlowingFluid> SOURCE_DEPLETED_MOLTEN_SALT = Registration.FLUIDS.register("depleted_molten_salt",
            () -> new ForgeFlowingFluid.Source(ModFluids.DEPLETED_MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_DEPLETED_MOLTEN_SALT = Registration.FLUIDS.register("flowing_depleted_molten_salt",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.DEPLETED_MOLTEN_SALT_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_HEATED_MOLTEN_SALT = Registration.FLUIDS.register("heated_molten_salt",
            () -> new ForgeFlowingFluid.Source(ModFluids.HEATED_MOLTEN_SALT_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HEATED_MOLTEN_SALT = Registration.FLUIDS.register("flowing_heated_molten_salt",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.HEATED_MOLTEN_SALT_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_HYDROFLUORITE = Registration.FLUIDS.register("hydrofluorite",
            () -> new ForgeFlowingFluid.Source(ModFluids.HYDROFLUORITE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HYDROFLUORITE = Registration.FLUIDS.register("flowing_hydrofluorite",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.HYDROFLUORITE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_URANIUM_HEXAFLUORITE = Registration.FLUIDS.register("uranium_hexafluorite",
            () -> new ForgeFlowingFluid.Source(ModFluids.URANIUM_HEXAFLUORITE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_URANIUM_HEXAFLUORITE = Registration.FLUIDS.register("flowing_uranium_hexafluorite",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.URANIUM_HEXAFLUORITE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_ENRICHED_URANIUM_HEXAFLUORITE = Registration.FLUIDS.register("enriched_uranium_hexafluorite",
            () -> new ForgeFlowingFluid.Source(ModFluids.ENRICHED_URANIUM_HEXAFLUORITE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_ENRICHED_URANIUM_HEXAFLUORITE = Registration.FLUIDS.register("flowing_enriched_uranium_hexafluorite",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.ENRICHED_URANIUM_HEXAFLUORITE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SOURCE_STEAM = Registration.FLUIDS.register("steam",
            () -> new ForgeFlowingFluid.Source(ModFluids.STEAM_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_STEAM = Registration.FLUIDS.register("flowing_steam",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.STEAM_PROPERTIES));

    public static final ForgeFlowingFluid.Properties MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.MOLTEN_SALT, SOURCE_MOLTEN_SALT, FLOWING_MOLTEN_SALT)
            .slopeFindDistance(1).levelDecreasePerBlock(2).block(ModBlocks.MOLTEN_SALT_BLOCK).bucket(ModItems.MOLTEN_SALT_BUCKET);
    public static final ForgeFlowingFluid.Properties DEPLETED_MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.DEPLETED_MOLTEN_SALT, SOURCE_DEPLETED_MOLTEN_SALT, FLOWING_DEPLETED_MOLTEN_SALT)
            .slopeFindDistance(1).levelDecreasePerBlock(2).block(ModBlocks.MOLTEN_SALT_BLOCK).bucket(ModItems.MOLTEN_SALT_BUCKET);
    public static final ForgeFlowingFluid.Properties HEATED_MOLTEN_SALT_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HEATED_MOLTEN_SALT, SOURCE_HEATED_MOLTEN_SALT, FLOWING_HEATED_MOLTEN_SALT)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(ModBlocks.HEATED_MOLTEN_SALT_BLOCK).bucket(ModItems.HEATED_MOLTEN_SALT_BUCKET);
    public static final ForgeFlowingFluid.Properties HYDROFLUORITE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.HYDROFLUORITE, SOURCE_HYDROFLUORITE, FLOWING_HYDROFLUORITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.HYDROFLUORITE_BLOCK).bucket(ModItems.HYDROFLUORITE_BUCKET);
    public static final ForgeFlowingFluid.Properties URANIUM_HEXAFLUORITE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.URANIUM_HEXAFLUORITE, SOURCE_URANIUM_HEXAFLUORITE, FLOWING_URANIUM_HEXAFLUORITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.URANIUM_HEXAFLUORITE_BLOCK).bucket(ModItems.URANIUM_HEXAFLUORITE_BUCKET);
    public static final ForgeFlowingFluid.Properties ENRICHED_URANIUM_HEXAFLUORITE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.ENRICHED_URANIUM_HEXAFLUORITE, SOURCE_ENRICHED_URANIUM_HEXAFLUORITE, FLOWING_ENRICHED_URANIUM_HEXAFLUORITE)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.ENRICHED_URANIUM_HEXAFLUORITE_BLOCK).bucket(ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET);
    public static final ForgeFlowingFluid.Properties STEAM_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.STEAM, SOURCE_STEAM, FLOWING_STEAM)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(ModBlocks.STEAM_BLOCK).bucket(ModItems.STEAM_BUCKET);

}
