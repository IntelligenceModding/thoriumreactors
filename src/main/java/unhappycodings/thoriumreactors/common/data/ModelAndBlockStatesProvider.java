package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.ThoriumCraftingTableBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorControllerBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorCoreBlock;
import unhappycodings.thoriumreactors.common.block.reactor.ReactorValveBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalControllerBlock;
import unhappycodings.thoriumreactors.common.block.thermal.ThermalValveBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbineRotorBlock;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

public class ModelAndBlockStatesProvider extends BlockStateProvider {
    DataGenerator gen;

    public ModelAndBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ThoriumReactors.MOD_ID, exFileHelper);
        this.gen = gen;
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.FACTORY_BLOCK.get());
        simpleBlock(ModBlocks.INVERTED_FACTORY_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_FACTORY_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get());

        simpleBlock(ModBlocks.INDUSTRAL_BLOCK.get());
        simpleBlock(ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get());
        simpleBlock(ModBlocks.INDUSTRAL_BLOCK_PAVING.get());
        simpleBlock(ModBlocks.INDUSTRAL_BLOCK_BRICK.get());
        simpleBlock(ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get());
        simpleBlock(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get());

        simpleBlock(ModBlocks.GRATE_FLOOR_BLOCK.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.GRATE_FLOOR_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/grate_floor")).texture("0", new ResourceLocation(ThoriumReactors.MOD_ID, "block/black_industrial_block_smooth")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/black_industrial_block_smooth")));

        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get());
        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get());
        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get());
        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get());
        simpleBlock(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get());

        simpleBlock(ModBlocks.WHITE_INDUSTRAL_BLOCK.get());
        simpleBlock(ModBlocks.WHITE_INDUSTRAL_BLOCK_BIG_TILE.get());
        simpleBlock(ModBlocks.WHITE_INDUSTRAL_BLOCK_PAVING.get());
        simpleBlock(ModBlocks.WHITE_INDUSTRAL_BLOCK_BRICK.get());
        simpleBlock(ModBlocks.WHITE_INDUSTRAL_BLOCK_SMOOTH.get());

        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get());
        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_RIGHT.get());
        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_LEFT.get());
        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_RIGHT.get());
        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_LEFT.get());
        simpleBlock(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_RIGHT.get());

        simpleBlock(ModBlocks.MANGANESE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_MANGANESE_ORE.get());
        simpleBlock(ModBlocks.CHROMITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_CHROMITE_ORE.get());
        simpleBlock(ModBlocks.MOLYBDENUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get());
        simpleBlock(ModBlocks.NICKEL_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_NICKEL_ORE.get());
        simpleBlock(ModBlocks.TITANIC_IRON_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get());
        simpleBlock(ModBlocks.BAUXITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_BAUXITE_ORE.get());
        simpleBlock(ModBlocks.PYROCHLOR_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get());
        simpleBlock(ModBlocks.URANIUM_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_URANIUM_ORE.get());
        simpleBlock(ModBlocks.GRAPHITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_GRAPHITE_ORE.get());
        simpleBlock(ModBlocks.FLUORITE_ORE.get());
        simpleBlock(ModBlocks.DEEPSLATE_FLUORITE_ORE.get());

        simpleBlock(ModBlocks.BLASTED_STONE.get());
        simpleBlock(ModBlocks.THORIUM_BLOCK.get());
        simpleBlock(ModBlocks.BLASTED_IRON_BLOCK.get());
        simpleBlock(ModBlocks.STEEL_BLOCK.get());
        simpleBlock(ModBlocks.MANGANESE_BLOCK.get());
        simpleBlock(ModBlocks.CHROMIUM_BLOCK.get());
        simpleBlock(ModBlocks.MOLYBDENUM_BLOCK.get());
        simpleBlock(ModBlocks.NICKEL_BLOCK.get());
        simpleBlock(ModBlocks.TITANIUM_BLOCK.get());
        simpleBlock(ModBlocks.ALUMINUM_BLOCK.get());
        simpleBlock(ModBlocks.NIOB_BLOCK.get());
        simpleBlock(ModBlocks.COBALT_BLOCK.get());
        simpleBlock(ModBlocks.URANIUM_BLOCK.get());
        simpleBlock(ModBlocks.GRAPHITE_BLOCK.get());
        simpleBlock(ModBlocks.FLUORITE_BLOCK.get());

        simpleBlock(ModBlocks.SIMPLE_FLUID_TANK.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.SIMPLE_FLUID_TANK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("1", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("2", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank_valve")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).renderType("cutout"));
        simpleBlock(ModBlocks.GENERIC_FLUID_TANK.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.GENERIC_FLUID_TANK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("1", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("2", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank_valve")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).renderType("cutout"));
        simpleBlock(ModBlocks.PROGRESSIVE_FLUID_TANK.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.PROGRESSIVE_FLUID_TANK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("1", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("2", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank_valve")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).renderType("cutout"));
        simpleBlock(ModBlocks.CREATIVE_FLUID_TANK.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.CREATIVE_FLUID_TANK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("1", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).texture("2", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank_valve")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_tank")).renderType("cutout"));

        heatSinkBlock(ModBlocks.THERMAL_HEAT_SINK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        allSideBlock(ModBlocks.THERMAL_CONDUCTOR.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        allSideFrontBlockOnOff(ModBlocks.THERMAL_CONTROLLER.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_controller"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        valveBlock(ModBlocks.THERMAL_VALVE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_valve"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));

        simpleBlock(ModBlocks.REACTOR_CASING.get());
        allSideFrontBlockOnOffScram(ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_controller"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        controlRodBlock(ModBlocks.REACTOR_ROD_CONTROLLER.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_rod_controller"));
        coreBlock(ModBlocks.REACTOR_CORE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_core"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        valveBlock(ModBlocks.REACTOR_VALVE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_valve"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        simpleBlock(ModBlocks.REACTOR_GLASS.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_GLASS.get()), new ResourceLocation("block/cube_all")).texture("all", new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_glass")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_glass")).renderType("cutout"));

        simpleBlock(ModBlocks.ELECTROMAGNETIC_COIL.get());
        simpleBlock(ModBlocks.TURBINE_CASING.get());
        allSideFrontBlockOnOff(ModBlocks.TURBINE_CONTROLLER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_controller"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_casing"));
        valveBlock(ModBlocks.TURBINE_POWER_PORT.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_power_port"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_casing"));
        valveBlock(ModBlocks.TURBINE_VALVE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_valve"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_casing"));
        valveBlock(ModBlocks.TURBINE_VENT.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_vent"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_casing"));
        valveBlockDirectional(ModBlocks.TURBINE_ROTATION_MOUNT.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_rotation_mount"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_casing"));
        rotorBlock(ModBlocks.TURBINE_ROTOR.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_rotor"), false);
        simpleBlock(ModBlocks.TURBINE_GLASS.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.TURBINE_GLASS.get()), new ResourceLocation("block/cube_all")).texture("all", new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_glass")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/turbine_glass")).renderType("cutout"));

        machineBlock(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/electrolytic_salt_separator_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_yellow"), false);
        machineBlock(ModBlocks.FLUID_EVAPORATION_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_evaporation_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), true);
        machineBlock(ModBlocks.GENERATOR_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/generator_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), true);
        machineBlock(ModBlocks.SALT_MELTER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/salt_melter_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_yellow"), true);
        machineBlock(ModBlocks.CONCENTRATOR_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/concentrator_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), false);
        machineBlock(ModBlocks.DECOMPOSER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/decomposer_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_yellow"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), false);
        machineBlock(ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/uranium_oxidizer_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_yellow"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), false);
        machineBlock(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_centrifuge_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), false);
        machineBlock(ModBlocks.CRYSTALLIZER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/crystallizer_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"), false);
        machineBlock(ModBlocks.BLAST_FURNACE_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/blast_furnace_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_cyan"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_blue"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve_yellow"), true);

        liquidBlock(ModBlocks.HYDROFLUORITE_BLOCK.get());
        liquidBlock(ModBlocks.MOLTEN_SALT_BLOCK.get());
        liquidBlock(ModBlocks.HEATED_MOLTEN_SALT_BLOCK.get());
        liquidBlock(ModBlocks.URANIUM_HEXAFLUORITE_BLOCK.get());
        liquidBlock(ModBlocks.ENRICHED_URANIUM_HEXAFLUORITE_BLOCK.get());
        liquidBlock(ModBlocks.STEAM_BLOCK.get());

        simpleBlock(ModBlocks.MACHINE_CASING.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.MACHINE_CASING.get()), new ResourceLocation("block/cube_all")).texture("all", new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base")));

    }

    public void liquidBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/water"))).build());
    }

    public void machineBlock(Block block, ResourceLocation texture, ResourceLocation valveLeft, ResourceLocation valveRight, ResourceLocation bottom, boolean onOffState) {
        ResourceLocation vent = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_vent");
        ResourceLocation power = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_power");
        if (onOffState) {
            ModelFile off = models().withExistingParent(ItemUtil.getRegString(block) + "_off", new ResourceLocation("block/cube"))
                    .texture("up", vent).texture("down", bottom).texture("north", texture + "_off").texture("east", valveLeft).texture("south", power).texture("west", valveRight).texture("particle", texture + "_off");
            ModelFile on = models().withExistingParent(ItemUtil.getRegString(block) + "_on", new ResourceLocation("block/cube"))
                    .texture("up", vent).texture("down", bottom).texture("north", texture + "_on").texture("east", valveLeft).texture("south", power).texture("west", valveRight).texture("particle", texture + "_on");
            getVariantBuilder(block).forAllStates(state -> {
                int rot = getRotForDir(state.getValue(MachineElectrolyticSaltSeparatorBlock.FACING));
                return ConfiguredModel.builder().modelFile(state.getValue(MachineElectrolyticSaltSeparatorBlock.POWERED) ? on : off).rotationY(rot).build();
            });
        } else {
            ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                    .texture("up", vent).texture("down", bottom).texture("north", texture).texture("east", valveLeft).texture("south", power).texture("west", valveRight).texture("particle", texture);
            getVariantBuilder(block).forAllStates(state -> {
                int rot = getRotForDir(state.getValue(MachineElectrolyticSaltSeparatorBlock.FACING));
                return ConfiguredModel.builder().modelFile(model).rotationY(rot).build();
            });
        }
    }

    public void coreBlock(Block block, ResourceLocation texture, ResourceLocation down) {
        ModelFile off = models().withExistingParent(ItemUtil.getRegString(block) + "_off", new ResourceLocation("block/cube"))
                .texture("up", texture + "_up").texture("down", down).texture("north", texture + "_off").texture("east", texture + "_off").texture("south", texture + "_off").texture("west", texture + "_off").texture("particle", texture + "_off");
        ModelFile offFueled = models().withExistingParent(ItemUtil.getRegString(block) + "_off_fueled", new ResourceLocation("block/cube"))
                .texture("up", texture + "_up_fueled").texture("down", down).texture("north", texture + "_off").texture("east", texture + "_off").texture("south", texture + "_off").texture("west", texture + "_off").texture("particle", texture + "_off");
        ModelFile on = models().withExistingParent(ItemUtil.getRegString(block) + "_on", new ResourceLocation("block/cube"))
                .texture("up", texture + "_up").texture("down", down).texture("north", texture + "_on").texture("east", texture + "_on").texture("south", texture + "_on").texture("west", texture + "_on").texture("particle", texture + "_on");
        ModelFile onFueled = models().withExistingParent(ItemUtil.getRegString(block) + "_on_fueled", new ResourceLocation("block/cube"))
                .texture("up", texture + "_up_fueled").texture("down", down).texture("north", texture + "_on").texture("east", texture + "_on").texture("south", texture + "_on").texture("west", texture + "_on").texture("particle", texture + "_on");
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(ReactorCoreBlock.HEATING) ? (state.getValue(ReactorCoreBlock.FUELED) ? onFueled : on) : (state.getValue(ReactorCoreBlock.FUELED) ? offFueled : off)).build());
    }

    public void controlRodBlock(Block block, ResourceLocation texture) {
        simpleBlock(block, models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture + "_side").texture("east", texture + "_side").texture("south", texture + "_side").texture("west", texture + "_side").texture("up", texture + "_up").texture("down", texture + "_down").texture("particle", texture + "_side"));
    }

    public void allSideBlock(Block block, ResourceLocation texture) {
        simpleBlock(block, models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube_all"))
                .texture("all", texture).texture("particle", texture));
    }

    public void valveBlock(Block block, ResourceLocation texture, ResourceLocation main) {
        ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture).texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture);
        getVariantBuilder(block).forAllStates(state -> {
            int rot = getRotForDir(state.getValue(ReactorValveBlock.FACING));
            return ConfiguredModel.builder().modelFile(model).rotationY(rot).build();
        });
    }

    public void valveBlockDirectional(Block block, ResourceLocation texture, ResourceLocation main) {
        ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture).texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture);
        getVariantBuilder(block).forAllStates(state -> {
            int rot = getRotForDir(state.getValue(ReactorValveBlock.FACING));
            if (state.getValue(ReactorValveBlock.FACING) == Direction.UP || state.getValue(ReactorValveBlock.FACING) == Direction.DOWN)
                return ConfiguredModel.builder().modelFile(model).rotationX(rot).build();
            return ConfiguredModel.builder().modelFile(model).rotationY(rot).build();
        });
    }

    public void rotorBlock(Block block, ResourceLocation texture, boolean simple) {
        ModelFile modelDefault = models().withExistingParent(ItemUtil.getRegString(block) + "_0", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor")).texture("0", texture).texture("particle", texture);
        ModelFile model1 = models().withExistingParent(ItemUtil.getRegString(block) + "_1", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_1")).texture("0", texture).texture("particle", texture);
        ModelFile model2 = models().withExistingParent(ItemUtil.getRegString(block) + "_2", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_2")).texture("0", texture).texture("particle", texture);
        ModelFile model3 = models().withExistingParent(ItemUtil.getRegString(block) + "_3", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_3")).texture("0", texture).texture("particle", texture);
        ModelFile model4 = models().withExistingParent(ItemUtil.getRegString(block) + "_4", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_4")).texture("0", texture).texture("particle", texture);
        ModelFile model5 = models().withExistingParent(ItemUtil.getRegString(block) + "_5", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_5")).texture("0", texture).texture("particle", texture);
        ModelFile model6 = models().withExistingParent(ItemUtil.getRegString(block) + "_6", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_6")).texture("0", texture).texture("particle", texture);
        ModelFile model7 = models().withExistingParent(ItemUtil.getRegString(block) + "_7", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_7")).texture("0", texture).texture("particle", texture);
        ModelFile model8 = models().withExistingParent(ItemUtil.getRegString(block) + "_8", new ResourceLocation(ThoriumReactors.MOD_ID, "generation/turbine_rotor_8")).texture("0", texture).texture("particle", texture);
        if (simple) return;
        ConfiguredModel.builder().modelFile(modelDefault).build();
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile finalModel = switch (state.getValue(TurbineRotorBlock.BLADES)) {
                case 0 -> modelDefault;
                case 1 -> model1;
                case 2 -> model2;
                case 3 -> model3;
                case 4 -> model4;
                case 5 -> model5;
                case 6 -> model6;
                case 7 -> model7;
                default -> model8;
            };
            return ConfiguredModel.builder().modelFile(finalModel).build();
        });
    }

    public void allSideFrontBlock(Block block, ResourceLocation texture, ResourceLocation main) {
        ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture).texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture);
        getVariantBuilder(block).forAllStates(state -> {
            int rot = getRotForDir(state.getValue(ThermalValveBlock.FACING));
            return ConfiguredModel.builder().modelFile(model).rotationX(rot).build();
        });
    }

    public void allSideFrontBlockOnOff(Block block, ResourceLocation texture, ResourceLocation main) {
        ModelFile off = models().withExistingParent(ItemUtil.getRegString(block) + "_off", new ResourceLocation("block/cube"))
                .texture("north", texture + "_off").texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture + "_off");
        ModelFile on = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture).texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture);
        getVariantBuilder(block).forAllStates(state -> {
            int rot = getRotForDir(state.getValue(ThermalControllerBlock.FACING));
            return ConfiguredModel.builder().modelFile(state.getValue(ThermalControllerBlock.POWERED) ? on : off).rotationY(rot).build();
        });
    }


    public void allSideFrontBlockOnOffScram(Block block, ResourceLocation texture, ResourceLocation main) {
        ModelFile off = models().withExistingParent(ItemUtil.getRegString(block) + "_off", new ResourceLocation("block/cube"))
                .texture("north", texture + "_off").texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture + "_off");
        ModelFile on = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("north", texture).texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture);
        ModelFile scram = models().withExistingParent(ItemUtil.getRegString(block) + "_scram", new ResourceLocation("block/cube"))
                .texture("north", texture + "_scram").texture("east", main).texture("south", main).texture("west", main).texture("up", main).texture("down", main).texture("particle", texture + "_scram");
        getVariantBuilder(block).forAllStates(state -> {
            int rot = getRotForDir(state.getValue(ThermalControllerBlock.FACING));
            ModelFile target = off;
            if (state.getValue(ReactorControllerBlock.POWERED)) target = on;
            if (state.getValue(ReactorControllerBlock.SCRAMMED)) target = scram;
            return ConfiguredModel.builder().modelFile(target).rotationY(rot).build();
        });
    }

    public void heatSinkBlock(Block block, ResourceLocation texture) {
        horizontalBlock(block, models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation(ThoriumReactors.MOD_ID, "generation/heat_sink"))
                .texture("0", texture).texture("particle", texture));
    }

    public int getRotForDir(Direction side) {
        return switch (side) {
            case NORTH -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            case UP -> 270;
            default -> 90;
        };
    }

}
