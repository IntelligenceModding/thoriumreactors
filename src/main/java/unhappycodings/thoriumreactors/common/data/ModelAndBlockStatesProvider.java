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
import unhappycodings.thoriumreactors.common.block.*;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

public class ModelAndBlockStatesProvider extends BlockStateProvider {
    DataGenerator gen;

    public ModelAndBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ThoriumReactors.MOD_ID, exFileHelper);
        this.gen = gen;
    }

    private static int getSensorRotation(Direction facing) {
        return switch (facing) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.HARDENED_STONE.get());
        simpleBlock(ModBlocks.REACTOR_CASING.get());
        simpleBlock(ModBlocks.GRAPHITE_ORE.get());
        simpleBlock(ModBlocks.GRAPHITE_BLOCK.get());

        heatSinkBlock(ModBlocks.THERMAL_HEAT_SINK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        allSideBlock(ModBlocks.THERMAL_CONDUCTOR.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        allSideFrontBlockOnOff(ModBlocks.THERMAL_CONTROLLER.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_controller"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        allSideFrontBlock(ModBlocks.THERMAL_VALVE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_valve"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));

        allSideFrontBlockOnOff(ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_controller"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        controlRodBlock(ModBlocks.REACTOR_ROD_CONTROLLER.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_rod_controller"));
        coreBlock(ModBlocks.REACTOR_CORE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_core"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        valveBlock(ModBlocks.REACTOR_VALVE.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_valve"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        simpleBlock(ModBlocks.REACTOR_GLASS.get(), models().withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_GLASS.get()), new ResourceLocation("block/cube_all")).texture("all", new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_glass")).texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_glass")).renderType("cutout"));

        machineBlock(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/electrolytic_salt_separator"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve"));
        machineBlock(ModBlocks.FLUID_EVAPORATION_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_evaporation_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_valve"));
        machineBlock(ModBlocks.GENERATOR_BLOCK.get(), new ResourceLocation(ThoriumReactors.MOD_ID, "block/generator_block"), new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base"));
    }

    public void machineBlock(Block block, ResourceLocation texture, ResourceLocation valve) {
        ResourceLocation ventilator = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_ventilator");
        ResourceLocation ventilatorOff = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_ventilator_off");
        ResourceLocation side = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_base");
        ResourceLocation power = new ResourceLocation(ThoriumReactors.MOD_ID, "block/machine_power");
        ModelFile off = models().withExistingParent(ItemUtil.getRegString(block) + "_off", new ResourceLocation("block/cube"))
                .texture("up", ventilatorOff).texture("down", side).texture("north", texture + "_off").texture("east", valve).texture("south", power).texture("west", valve).texture("particle", texture + "_off");
        ModelFile on = models().withExistingParent(ItemUtil.getRegString(block) + "_on", new ResourceLocation("block/cube"))
                .texture("up", ventilator).texture("down", side).texture("north", texture + "_on").texture("east", valve).texture("south", power).texture("west", valve).texture("particle", texture + "_on");
       getVariantBuilder(block).forAllStates(state -> {
           int rot = getRotForDir(state.getValue(MachineElectrolyticSaltSeparatorBlock.FACING));
           return ConfiguredModel.builder().modelFile(state.getValue(MachineElectrolyticSaltSeparatorBlock.POWERED) ? on : off).rotationY(rot).build();
       });
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
            return ConfiguredModel.builder().modelFile(state.getValue(ThermalControllerBlock.POWERED) ? on : off).rotationY(0).build();
        });
    }

    public void heatSinkBlock(Block block, ResourceLocation texture) {
        horizontalBlock(block, models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation(ThoriumReactors.MOD_ID, "generation/heat_sink"))
                .texture("0", texture).texture("particle", texture));
    }

    public void craftingTableBlock(ThoriumCraftingTableBlock block) {
        ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("down", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_bottom"))
                .texture("up", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_top"))
                .texture("north", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("east", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("south", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("west", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"));
        craftingTableBlock(block, model);
    }

    public void craftingTableBlock(ThoriumCraftingTableBlock block, ModelFile model) {
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
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
