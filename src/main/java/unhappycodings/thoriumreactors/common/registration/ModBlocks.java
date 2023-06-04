package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.*;
import unhappycodings.thoriumreactors.common.block.chest.BlastedIronChestBlock;
import unhappycodings.thoriumreactors.common.block.chest.SteelChestBlock;
import unhappycodings.thoriumreactors.common.block.chest.ThoriumChestBlock;
import unhappycodings.thoriumreactors.common.block.machine.*;
import unhappycodings.thoriumreactors.common.block.reactor.*;
import unhappycodings.thoriumreactors.common.block.tank.FluidTankBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalConductorBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalControllerBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalSinkBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<ThoriumCraftingTableBlock> THORIUM_CRAFTING_TABLE = register("thorium_crafting_table", ThoriumCraftingTableBlock::new);
    public static final RegistryObject<FluidTankBlock> SIMPLE_FLUID_TANK = register("simple_fluid_tank", () -> new FluidTankBlock(32000, ModBlockEntities.SIMPLE_FLUID_TANK));
    public static final RegistryObject<FluidTankBlock> GENERIC_FLUID_TANK = register("generic_fluid_tank", () -> new FluidTankBlock(64000, ModBlockEntities.GENERIC_FLUID_TANK));
    public static final RegistryObject<FluidTankBlock> PROGRESSIVE_FLUID_TANK = register("progressive_fluid_tank", () -> new FluidTankBlock(128000, ModBlockEntities.PROGRESSIVE_FLUID_TANK));

    // Ores
    public static final RegistryObject<Block> MANGANESE_ORE = register("manganese_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_MANGANESE_ORE = register("deepslate_manganese_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> CHROMITE_ORE = register("chromite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_CHROMITE_ORE = register("deepslate_chromite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> MOLYBDENUM_ORE = register("molybdenum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_MOLYBDENUM_ORE = register("deepslate_molybdenum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> NICKEL_ORE = register("nickel_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_NICKEL_ORE = register("deepslate_nickel_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> TITANIC_IRON_ORE = register("titanic_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_TITANIC_IRON_ORE = register("deepslate_titanic_iron_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> BAUXITE_ORE = register("bauxite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_BAUXITE_ORE = register("deepslate_bauxite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> PYROCHLOR_ORE = register("pyrochlor_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_PYROCHLOR_ORE = register("deepslate_pyrochlor_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> URANIUM_ORE = register("uranium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = register("deepslate_uranium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> GRAPHITE_ORE = register("graphite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_GRAPHITE_ORE = register("deepslate_graphite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    public static final RegistryObject<Block> FLUORITE_ORE = register("fluorite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4f)));
    public static final RegistryObject<Block> DEEPSLATE_FLUORITE_ORE = register("deepslate_fluorite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(4.5F, 3.0F)));

    // Blocks
    public static final RegistryObject<Block> BLASTED_STONE = register("blasted_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<Block> GRAPHITE_BLOCK = register("graphite_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<Block> THORIUM_BLOCK = register("thorium_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<Block> FLUORITE_BLOCK = register("fluoride_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<Block> URANIUM_BLOCK = register("uranium_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<Block> BLASTED_IRON_BLOCK = register("blasted_iron_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(6f)));
    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(6f)));

    public static final RegistryObject<ThoriumChestBlock> THORIUM_CHEST_BLOCK = register("thorium_chest_block", () -> new ThoriumChestBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(6f), ModBlockEntities.THORIUM_CHEST_BLOCK::get));
    public static final RegistryObject<SteelChestBlock> STEEL_CHEST_BLOCK = register("steel_chest_block", () -> new SteelChestBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(6f), ModBlockEntities.STEEL_CHEST_BLOCK::get));
    public static final RegistryObject<BlastedIronChestBlock> BLASTED_IRON_CHEST_BLOCK = register("blasted_iron_chest_block", () -> new BlastedIronChestBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(6f), ModBlockEntities.BLASTED_IRON_CHEST_BLOCK::get));

    // Thermal
    public static final RegistryObject<ThermalControllerBlock> THERMAL_CONTROLLER = register("thermal_controller", ThermalControllerBlock::new);
    public static final RegistryObject<ThermalConductorBlock> THERMAL_CONDUCTOR = register("thermal_conductor", ThermalConductorBlock::new);
    public static final RegistryObject<ThermalValveBlock> THERMAL_VALVE = register("thermal_valve", ThermalValveBlock::new);
    public static final RegistryObject<ThermalSinkBlock> THERMAL_HEAT_SINK = register("thermal_heat_sink", ThermalSinkBlock::new);

    // Reactor
    public static final RegistryObject<Block> REACTOR_CASING = register("reactor_casing", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5f)));
    public static final RegistryObject<ReactorGraphiteModeratorBlock> REACTOR_GRAPHITE_MODERATOR = register("reactor_graphite_moderator", ReactorGraphiteModeratorBlock::new);
    public static final RegistryObject<ReactorControllerBlock> REACTOR_CONTROLLER_BLOCK = register("reactor_controller", ReactorControllerBlock::new);
    public static final RegistryObject<ReactorControlRodBlock> REACTOR_ROD_CONTROLLER = register("reactor_rod_controller", ReactorControlRodBlock::new);
    public static final RegistryObject<ReactorCoreBlock> REACTOR_CORE = register("reactor_core", ReactorCoreBlock::new);
    public static final RegistryObject<ReactorValveBlock> REACTOR_VALVE = register("reactor_valve", ReactorValveBlock::new);
    public static final RegistryObject<ReactorGlassBlock> REACTOR_GLASS = register("reactor_glass", ReactorGlassBlock::new);

    // Machines
    public static final RegistryObject<MachineElectrolyticSaltSeparatorBlock> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = register("electrolytic_salt_separator", MachineElectrolyticSaltSeparatorBlock::new);
    public static final RegistryObject<MachineFluidEvaporationBlock> FLUID_EVAPORATION_BLOCK = register("fluid_evaporation_block", MachineFluidEvaporationBlock::new);
    public static final RegistryObject<MachineGeneratorBlock> GENERATOR_BLOCK = register("generator_block", MachineGeneratorBlock::new);
    public static final RegistryObject<MachineSaltMelterBlock> SALT_MELTER_BLOCK = register("salt_melter_block", MachineSaltMelterBlock::new);
    public static final RegistryObject<MachineConcentratorBlock> CONCENTRATOR_BLOCK = register("concentrator_block", MachineConcentratorBlock::new);
    public static final RegistryObject<MachineDecomposerBlock> DECOMPOSER_BLOCK = register("decomposer_block", MachineDecomposerBlock::new);
    public static final RegistryObject<MachineUraniumOxidizerBlock> URANIUM_OXIDIZER_BLOCK = register("uranium_oxidizer_block", MachineUraniumOxidizerBlock::new);
    public static final RegistryObject<MachineFluidCentrifugeBlock> FLUID_CENTRIFUGE_BLOCK = register("fluid_centrifuge_block", MachineFluidCentrifugeBlock::new);
    public static final RegistryObject<MachineCrystallizerBlock> CRYSTALLIZER_BLOCK = register("crystallizer_block", MachineCrystallizerBlock::new);
    public static final RegistryObject<MachineBlastFurnaceBlock> BLAST_FURNACE_BLOCK = register("blast_furnace_block", MachineBlastFurnaceBlock::new);

    // Fluids
    public static final RegistryObject<LiquidBlock> MOLTEN_SALT_BLOCK = Registration.BLOCKS.register("molten_salt_block", () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SALT, BlockBehaviour.Properties.of(Material.LAVA).noCollission().strength(100.0F)));
    public static final RegistryObject<LiquidBlock> HEATED_MOLTEN_SALT_BLOCK = Registration.BLOCKS.register("heated_molten_salt_block", () -> new LiquidBlock(ModFluids.SOURCE_HEATED_MOLTEN_SALT, BlockBehaviour.Properties.of(Material.LAVA).noCollission().strength(100.0F)));
    public static final RegistryObject<LiquidBlock> HYDROFLUORITE_BLOCK = Registration.BLOCKS.register("hydrofluorite_block", () -> new LiquidBlock(ModFluids.SOURCE_HYDROFLUORITE, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F)));
    public static final RegistryObject<LiquidBlock> URANIUM_HEXAFLUORITE_BLOCK = Registration.BLOCKS.register("uranium_hexafluorite_block", () -> new LiquidBlock(ModFluids.SOURCE_URANIUM_HEXAFLUORITE, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F)));
    public static final RegistryObject<LiquidBlock> ENRICHED_URANIUM_HEXAFLUORITE_BLOCK = Registration.BLOCKS.register("enriched_uranium_hexafluorite_block", () -> new LiquidBlock(ModFluids.SOURCE_ENRICHED_URANIUM_HEXAFLUORITE, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F)));
    public static final RegistryObject<LiquidBlock> STEAM_BLOCK = Registration.BLOCKS.register("steam_block", () -> new LiquidBlock(ModFluids.SOURCE_STEAM, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F)));

    private ModBlocks() {
        throw new IllegalStateException("ModBlocks class");
    }

    public static void register() {
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().tab(ThoriumReactors.creativeTab)));
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Supplier<BlockItem> blockItem) {
        RegistryObject<T> registryObject = registerNoItem(name, block);
        Registration.ITEMS.register(name, blockItem);
        return registryObject;
    }


}
