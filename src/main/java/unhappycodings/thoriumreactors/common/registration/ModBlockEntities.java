package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.BlastedIronChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.ThoriumChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.machine.*;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.tank.FluidTankBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.thermal.ThermalValveBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineControllerBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbinePowerPortBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.turbine.TurbineValveBlockEntity;

public final class ModBlockEntities {

    public static final RegistryObject<BlockEntityType<ThoriumCraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(ThoriumCraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorControllerBlockEntity>> REACTOR_CONTROLLER = Registration.BLOCK_ENTITIES.register("reactor_controller", () -> BlockEntityType.Builder.of(ReactorControllerBlockEntity::new, ModBlocks.REACTOR_CONTROLLER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorValveBlockEntity>> REACTOR_VALVE = Registration.BLOCK_ENTITIES.register("reactor_valve", () -> BlockEntityType.Builder.of(ReactorValveBlockEntity::new, ModBlocks.REACTOR_VALVE.get()).build(null));

    public static final RegistryObject<BlockEntityType<TurbineControllerBlockEntity>> TURBINE_CONTROLLER = Registration.BLOCK_ENTITIES.register("turbine_controller", () -> BlockEntityType.Builder.of(TurbineControllerBlockEntity::new, ModBlocks.TURBINE_CONTROLLER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineValveBlockEntity>> TURBINE_VALVE = Registration.BLOCK_ENTITIES.register("turbine_valve", () -> BlockEntityType.Builder.of(TurbineValveBlockEntity::new, ModBlocks.TURBINE_VALVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbinePowerPortBlockEntity>> TURBINE_POWER_PORT = Registration.BLOCK_ENTITIES.register("turbine_power_port", () -> BlockEntityType.Builder.of(TurbinePowerPortBlockEntity::new, ModBlocks.TURBINE_POWER_PORT.get()).build(null));

    public static final RegistryObject<BlockEntityType<ThermalValveBlockEntity>> THERMAL_VALVE = Registration.BLOCK_ENTITIES.register("thermal_valve", () -> BlockEntityType.Builder.of(ThermalValveBlockEntity::new, ModBlocks.THERMAL_VALVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ThermalControllerBlockEntity>> THERMAL_CONTROLLER = Registration.BLOCK_ENTITIES.register("thermal_controller", () -> BlockEntityType.Builder.of(ThermalControllerBlockEntity::new, ModBlocks.THERMAL_CONTROLLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> SIMPLE_FLUID_TANK = Registration.BLOCK_ENTITIES.register("simple_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 32000, ModBlockEntities.SIMPLE_FLUID_TANK.get()), ModBlocks.SIMPLE_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> GENERIC_FLUID_TANK = Registration.BLOCK_ENTITIES.register("generic_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 64000, ModBlockEntities.GENERIC_FLUID_TANK.get()), ModBlocks.GENERIC_FLUID_TANK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidTankBlockEntity>> PROGRESSIVE_FLUID_TANK = Registration.BLOCK_ENTITIES.register("progressive_fluid_tank", () -> BlockEntityType.Builder.of((pos, state) -> new FluidTankBlockEntity(pos, state, 128000, ModBlockEntities.PROGRESSIVE_FLUID_TANK.get()), ModBlocks.PROGRESSIVE_FLUID_TANK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineGeneratorBlockEntity>> GENERATOR_BLOCK = Registration.BLOCK_ENTITIES.register("generator_block", () -> BlockEntityType.Builder.of(MachineGeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidEvaporationBlockEntity>> FLUID_EVAPORATION_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_evaporation_block", () -> BlockEntityType.Builder.of(MachineFluidEvaporationBlockEntity::new, ModBlocks.FLUID_EVAPORATION_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineElectrolyticSaltSeparatorBlockEntity>> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = Registration.BLOCK_ENTITIES.register("electrolytic_salt_separator_block", () -> BlockEntityType.Builder.of(MachineElectrolyticSaltSeparatorBlockEntity::new, ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineSaltMelterBlockEntity>> SALT_MELTER_BLOCK = Registration.BLOCK_ENTITIES.register("salt_melter_block", () -> BlockEntityType.Builder.of(MachineSaltMelterBlockEntity::new, ModBlocks.SALT_MELTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineConcentratorBlockEntity>> CONCENTRATOR_BLOCK = Registration.BLOCK_ENTITIES.register("concentrator_block", () -> BlockEntityType.Builder.of(MachineConcentratorBlockEntity::new, ModBlocks.CONCENTRATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineDecomposerBlockEntity>> DECOMPOSER_BLOCK = Registration.BLOCK_ENTITIES.register("decomposer_block", () -> BlockEntityType.Builder.of(MachineDecomposerBlockEntity::new, ModBlocks.DECOMPOSER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineUraniumOxidizerBlockEntity>> URANIUM_OXIDIZER_BLOCK = Registration.BLOCK_ENTITIES.register("uranium_oxidizer_block", () -> BlockEntityType.Builder.of(MachineUraniumOxidizerBlockEntity::new, ModBlocks.URANIUM_OXIDIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidCentrifugeBlockEntity>> FLUID_CENTRIFUGE_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_centrifuge_block", () -> BlockEntityType.Builder.of(MachineFluidCentrifugeBlockEntity::new, ModBlocks.FLUID_CENTRIFUGE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineCrystallizerBlockEntity>> CRYSTALLIZER_BLOCK = Registration.BLOCK_ENTITIES.register("crystallizer_block", () -> BlockEntityType.Builder.of(MachineCrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineBlastFurnaceBlockEntity>> BLAST_FURNACE_BLOCK = Registration.BLOCK_ENTITIES.register("blast_furnace_block", () -> BlockEntityType.Builder.of(MachineBlastFurnaceBlockEntity::new, ModBlocks.BLAST_FURNACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ThoriumChestBlockEntity>> THORIUM_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("thorium_chest_block", () -> BlockEntityType.Builder.of(ThoriumChestBlockEntity::new, ModBlocks.THORIUM_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SteelChestBlockEntity>> STEEL_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("steel_chest_block", () -> BlockEntityType.Builder.of(SteelChestBlockEntity::new, ModBlocks.STEEL_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlastedIronChestBlockEntity>> BLASTED_IRON_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("blasted_iron_chest_block", () -> BlockEntityType.Builder.of(BlastedIronChestBlockEntity::new, ModBlocks.BLASTED_IRON_CHEST_BLOCK.get()).build(null));

    public static void register() {
    }

}
