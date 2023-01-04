package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.*;

import java.util.function.Supplier;

public class ModBlocks {

    private ModBlocks() {
        throw new IllegalStateException("ModBlocks class");
    }

    public static final RegistryObject<ThoriumCraftingTableBlock> THORIUM_CRAFTING_TABLE = register("thorium_crafting_table", ThoriumCraftingTableBlock::new);
    public static final RegistryObject<Block> HARDENED_STONE = register("hardened_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> REACTOR_CASING = register("reactor_casing", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GRAPHITE_ORE = register("graphite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> GRAPHITE_BLOCK = register("graphite_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> THORIUM_BLOCK = register("thorium_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<ThermalControllerBlock> THERMAL_CONTROLLER = register("thermal_controller", ThermalControllerBlock::new);
    public static final RegistryObject<ThermalConductorBlock> THERMAL_CONDUCTOR = register("thermal_conductor", ThermalConductorBlock::new);
    public static final RegistryObject<ThermalValveBlock> THERMAL_VALVE = register("thermal_valve", ThermalValveBlock::new);
    public static final RegistryObject<ThermalSinkBlock> THERMAL_HEAT_SINK = register("thermal_heat_sink", ThermalSinkBlock::new);

    public static final RegistryObject<ReactorGraphiteModeratorBlock> REACTOR_GRAPHITE_MODERATOR = register("reactor_graphite_moderator", ReactorGraphiteModeratorBlock::new);
    public static final RegistryObject<ReactorControllerBlock> REACTOR_CONTROLLER_BLOCK = register("reactor_controller", ReactorControllerBlock::new);
    public static final RegistryObject<ReactorControlRodBlock> REACTOR_ROD_CONTROLLER =  register("reactor_rod_controller", ReactorControlRodBlock::new);
    public static final RegistryObject<ReactorCoreBlock> REACTOR_CORE= register("reactor_core", ReactorCoreBlock::new);
    public static final RegistryObject<ReactorValveBlock> REACTOR_VALVE = register("reactor_valve", ReactorValveBlock::new);
    public static final RegistryObject<ReactorGlassBlock> REACTOR_GLASS = register("reactor_glass", ReactorGlassBlock::new);

    public static final RegistryObject<MachineElectrolyticSaltSeparatorBlock> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = register("electrolytic_salt_separator", MachineElectrolyticSaltSeparatorBlock::new);
    public static final RegistryObject<MachineFluidEvaporationBlock> FLUID_EVAPORATION_BLOCK = register("fluid_evaporation_block", MachineFluidEvaporationBlock::new);
    public static final RegistryObject<MachineGeneratorBlock> GENERATOR_BLOCK = register("generator_block", MachineGeneratorBlock::new);
    public static final RegistryObject<MachineSaltMelterBlock> SAlT_MELTER_BLOCK = register("salt_melter_block", MachineSaltMelterBlock::new);

    public static final RegistryObject<LiquidBlock> MOLTEN_SALT_BLOCK = Registration.BLOCKS.register("molten_salt_block",
            () -> new LiquidBlock(ModFluids.SOURCE_MOLTEN_SALT, BlockBehaviour.Properties.of(Material.LAVA).noCollission().strength(100.0F)));

    public static final RegistryObject<LiquidBlock> HEATED_MOLTEN_SALT_BLOCK = Registration.BLOCKS.register("heated_molten_salt_block",
            () -> new LiquidBlock(ModFluids.SOURCE_HEATED_MOLTEN_SALT, BlockBehaviour.Properties.of(Material.LAVA).noCollission().strength(100.0F)));

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
