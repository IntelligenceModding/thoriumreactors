package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.BlastedIronChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.ThoriumChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.machine.*;
import unhappycodings.thoriumreactors.common.blockentity.reactor.*;
import unhappycodings.thoriumreactors.common.blockentity.tank.EnergyTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalConductorBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalHeatSinkBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.*;

public final class ModBlockEntities {

    public static final RegistryObject<BlockEntityType<ThoriumCraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(ThoriumCraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<ReactorCasingBlockEntity>> REACTOR_CASING = Registration.BLOCK_ENTITIES.register("reactor_casing", () -> BlockEntityType.Builder.of(ReactorCasingBlockEntity::new, ModBlocks.REACTOR_CASING.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorControllerBlockEntity>> REACTOR_CONTROLLER = Registration.BLOCK_ENTITIES.register("reactor_controller", () -> BlockEntityType.Builder.of(ReactorControllerBlockEntity::new, ModBlocks.REACTOR_CONTROLLER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorControlRodBlockEntity>> REACTOR_CONTROL_ROD = Registration.BLOCK_ENTITIES.register("reactor_control_rod", () -> BlockEntityType.Builder.of(ReactorControlRodBlockEntity::new, ModBlocks.REACTOR_ROD_CONTROLLER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorCoreBlockEntity>> REACTOR_CORE = Registration.BLOCK_ENTITIES.register("reactor_core", () -> BlockEntityType.Builder.of(ReactorCoreBlockEntity::new, ModBlocks.REACTOR_CORE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorGlassBlockEntity>> REACTOR_GLASS = Registration.BLOCK_ENTITIES.register("reactor_glass", () -> BlockEntityType.Builder.of(ReactorGlassBlockEntity::new, ModBlocks.REACTOR_GLASS.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorGraphiteModeratorBlockEntity>> REACTOR_GRAPHITE_MODERATOR = Registration.BLOCK_ENTITIES.register("reactor_graphite_moderator", () -> BlockEntityType.Builder.of(ReactorGraphiteModeratorBlockEntity::new, ModBlocks.REACTOR_GRAPHITE_MODERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorValveBlockEntity>> REACTOR_VALVE = Registration.BLOCK_ENTITIES.register("reactor_valve", () -> BlockEntityType.Builder.of(ReactorValveBlockEntity::new, ModBlocks.REACTOR_VALVE.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectromagneticCoilBlockEntity>> ELECTROMAGNETIC_COIL = Registration.BLOCK_ENTITIES.register("electromagnetic_coil", () -> BlockEntityType.Builder.of(ElectromagneticCoilBlockEntity::new, ModBlocks.ELECTROMAGNETIC_COIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineCasingBlockEntity>> TURBINE_CASING = Registration.BLOCK_ENTITIES.register("turbine_casing", () -> BlockEntityType.Builder.of(TurbineCasingBlockEntity::new, ModBlocks.TURBINE_CASING.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineControllerBlockEntity>> TURBINE_CONTROLLER = Registration.BLOCK_ENTITIES.register("turbine_controller", () -> BlockEntityType.Builder.of(TurbineControllerBlockEntity::new, ModBlocks.TURBINE_CONTROLLER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineGlassBlockEntity>> TURBINE_GLASS = Registration.BLOCK_ENTITIES.register("turbine_glass", () -> BlockEntityType.Builder.of(TurbineGlassBlockEntity::new, ModBlocks.TURBINE_GLASS.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbinePowerPortBlockEntity>> TURBINE_POWER_PORT = Registration.BLOCK_ENTITIES.register("turbine_power_port", () -> BlockEntityType.Builder.of(TurbinePowerPortBlockEntity::new, ModBlocks.TURBINE_POWER_PORT.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineRotationMountBlockEntity>> TURBINE_ROTATION_MOUNT = Registration.BLOCK_ENTITIES.register("turbine_rotation_mount", () -> BlockEntityType.Builder.of(TurbineRotationMountBlockEntity::new, ModBlocks.TURBINE_ROTATION_MOUNT.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineRotorBlockEntity>> TURBINE_ROTOR = Registration.BLOCK_ENTITIES.register("turbine_rotor", () -> BlockEntityType.Builder.of(TurbineRotorBlockEntity::new, ModBlocks.TURBINE_ROTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineValveBlockEntity>> TURBINE_VALVE = Registration.BLOCK_ENTITIES.register("turbine_valve", () -> BlockEntityType.Builder.of(TurbineValveBlockEntity::new, ModBlocks.TURBINE_VALVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineVentBlockEntity>> TURBINE_VENT = Registration.BLOCK_ENTITIES.register("turbine_vent", () -> BlockEntityType.Builder.of(TurbineVentBlockEntity::new, ModBlocks.TURBINE_VENT.get()).build(null));

    public static final RegistryObject<BlockEntityType<ThermalConductorBlockEntity>> THERMAL_CONDUCTOR = Registration.BLOCK_ENTITIES.register("thermal_conductor", () -> BlockEntityType.Builder.of(ThermalConductorBlockEntity::new, ModBlocks.THERMAL_CONDUCTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ThermalHeatSinkBlockEntity>> THERMAL_HEAT_SINK = Registration.BLOCK_ENTITIES.register("thermal_heat_sink", () -> BlockEntityType.Builder.of(ThermalHeatSinkBlockEntity::new, ModBlocks.THERMAL_HEAT_SINK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ThermalValveBlockEntity>> THERMAL_VALVE = Registration.BLOCK_ENTITIES.register("thermal_valve", () -> BlockEntityType.Builder.of(ThermalValveBlockEntity::new, ModBlocks.THERMAL_VALVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ThermalControllerBlockEntity>> THERMAL_CONTROLLER = Registration.BLOCK_ENTITIES.register("thermal_controller", () -> BlockEntityType.Builder.of(ThermalControllerBlockEntity::new, ModBlocks.THERMAL_CONTROLLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyTankBlockEntity>> SIMPLE_ENERGY_TANK = Registration.BLOCK_ENTITIES.register("simple_energy_tank", () -> BlockEntityType.Builder.of((pos, state) -> new EnergyTankBlockEntity(ModBlockEntities.SIMPLE_ENERGY_TANK.get(), pos, state, 2560000), ModBlocks.SIMPLE_ENERGY_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyTankBlockEntity>> GENERIC_ENERGY_TANK = Registration.BLOCK_ENTITIES.register("generic_energy_tank", () -> BlockEntityType.Builder.of((pos, state) -> new EnergyTankBlockEntity(ModBlockEntities.GENERIC_ENERGY_TANK.get(), pos, state, 5120000), ModBlocks.GENERIC_ENERGY_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyTankBlockEntity>> PROGRESSIVE_ENERGY_TANK = Registration.BLOCK_ENTITIES.register("progressive_energy_tank", () -> BlockEntityType.Builder.of((pos, state) -> new EnergyTankBlockEntity(ModBlockEntities.PROGRESSIVE_ENERGY_TANK.get(), pos, state, 10240000), ModBlocks.PROGRESSIVE_ENERGY_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyTankBlockEntity>> CREATIVE_ENERGY_TANK = Registration.BLOCK_ENTITIES.register("creative_energy_tank", () -> BlockEntityType.Builder.of((pos, state) -> new EnergyTankBlockEntity(ModBlockEntities.CREATIVE_ENERGY_TANK.get(), pos, state, -1), ModBlocks.CREATIVE_ENERGY_TANK.get()).build(null));

    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> SIMPLE_FLUID_TANK = Registration.BLOCK_ENTITIES.register("simple_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 32000, ModBlockEntities.SIMPLE_FLUID_TANK.get()), ModBlocks.SIMPLE_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> GENERIC_FLUID_TANK = Registration.BLOCK_ENTITIES.register("generic_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 64000, ModBlockEntities.GENERIC_FLUID_TANK.get()), ModBlocks.GENERIC_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> PROGRESSIVE_FLUID_TANK = Registration.BLOCK_ENTITIES.register("progressive_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 128000, ModBlockEntities.PROGRESSIVE_FLUID_TANK.get()), ModBlocks.PROGRESSIVE_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> CREATIVE_FLUID_TANK = Registration.BLOCK_ENTITIES.register("creative_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, -1, ModBlockEntities.CREATIVE_FLUID_TANK.get()), ModBlocks.CREATIVE_FLUID_TANK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineGeneratorBlockEntity>> GENERATOR_BLOCK = Registration.BLOCK_ENTITIES.register("generator_block", () -> BlockEntityType.Builder.of(MachineGeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidEvaporationBlockEntity>> FLUID_EVAPORATION_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_evaporation_block", () -> BlockEntityType.Builder.of(MachineFluidEvaporationBlockEntity::new, ModBlocks.FLUID_EVAPORATION_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineElectrolyticSaltSeparatorBlockEntity>> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = Registration.BLOCK_ENTITIES.register("electrolytic_salt_separator_block", () -> BlockEntityType.Builder.of(MachineElectrolyticSaltSeparatorBlockEntity::new, ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineSaltMelterBlockEntity>> SALT_MELTER_BLOCK = Registration.BLOCK_ENTITIES.register("salt_melter_block", () -> BlockEntityType.Builder.of(MachineSaltMelterBlockEntity::new, ModBlocks.SALT_MELTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineConcentratorBlockEntity>> CONCENTRATOR_BLOCK = Registration.BLOCK_ENTITIES.register("concentrator_block", () -> BlockEntityType.Builder.of(MachineConcentratorBlockEntity::new, ModBlocks.CONCENTRATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineDecomposerBlockEntity>> DECOMPOSER_BLOCK = Registration.BLOCK_ENTITIES.register("decomposer_block", () -> BlockEntityType.Builder.of(MachineDecomposerBlockEntity::new, ModBlocks.DECOMPOSER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineUraniumOxidizerBlockEntity>> URANIUM_OXIDIZER_BLOCK = Registration.BLOCK_ENTITIES.register("uranium_oxidizer_block", () -> BlockEntityType.Builder.of(MachineUraniumOxidizerBlockEntity::new, ModBlocks.URANIUM_OXIDIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidEnricherBlockEntity>> FLUID_ENRICHER_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_enricher_block", () -> BlockEntityType.Builder.of(MachineFluidEnricherBlockEntity::new, ModBlocks.FLUID_ENRICHER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidCentrifugeBlockEntity>> FLUID_CENTRIFUGE_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_centrifuge_block", () -> BlockEntityType.Builder.of(MachineFluidCentrifugeBlockEntity::new, ModBlocks.FLUID_CENTRIFUGE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineCrystallizerBlockEntity>> CRYSTALLIZER_BLOCK = Registration.BLOCK_ENTITIES.register("crystallizer_block", () -> BlockEntityType.Builder.of(MachineCrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineBlastFurnaceBlockEntity>> BLAST_FURNACE_BLOCK = Registration.BLOCK_ENTITIES.register("blast_furnace_block", () -> BlockEntityType.Builder.of(MachineBlastFurnaceBlockEntity::new, ModBlocks.BLAST_FURNACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ThoriumChestBlockEntity>> THORIUM_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("thorium_chest_block", () -> BlockEntityType.Builder.of(ThoriumChestBlockEntity::new, ModBlocks.THORIUM_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SteelChestBlockEntity>> STEEL_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("steel_chest_block", () -> BlockEntityType.Builder.of(SteelChestBlockEntity::new, ModBlocks.STEEL_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlastedIronChestBlockEntity>> BLASTED_IRON_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("blasted_iron_chest_block", () -> BlockEntityType.Builder.of(BlastedIronChestBlockEntity::new, ModBlocks.BLASTED_IRON_CHEST_BLOCK.get()).build(null));

    public static void register() {
    }

}
