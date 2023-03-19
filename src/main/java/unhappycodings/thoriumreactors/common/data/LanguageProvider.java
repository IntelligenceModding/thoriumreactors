package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.fluid.ModFluidTypes;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.registration.ModItems;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThoriumReactors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get(), "Thorium Crafting Table");

        add(ModBlocks.HARDENED_STONE.get(), "Hardened Smooth Stone");
        add(ModBlocks.GRAPHITE_ORE.get(), "Graphite Ore");
        add(ModBlocks.THORIUM_BLOCK.get(), "Thorium");
        add(ModBlocks.GRAPHITE_BLOCK.get(), "Graphite");

        add(ModBlocks.GENERATOR_BLOCK.get(), "Fuel Generator");
        add(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), "Electrolytic Separator");
        add(ModBlocks.FLUID_EVAPORATION_BLOCK.get(), "Fluid Evaporator");
        add(ModBlocks.SAlT_MELTER_BLOCK.get(), "Salt Melter");
        add(ModBlocks.CONCENTRATOR_BLOCK.get(), "Concentrator");
        add(ModBlocks.DECOMPOSER_BLOCK.get(), "Decomposer");

        add(ModBlocks.REACTOR_CASING.get(), "Reactor Casing");
        add(ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), "Reactor Controller");
        add(ModBlocks.REACTOR_VALVE.get(), "Reactor Valve");
        add(ModBlocks.REACTOR_ROD_CONTROLLER.get(), "Reactor Rod Controller");
        add(ModBlocks.REACTOR_CORE.get(), "Reactor Core");
        add(ModBlocks.REACTOR_GLASS.get(), "Reactor Glass");
        add(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), "Graphite Moderator");

        add(ModBlocks.THERMAL_CONDUCTOR.get(), "Thermal Conductor");
        add(ModBlocks.THERMAL_CONTROLLER.get(), "Thermal Controller");
        add(ModBlocks.THERMAL_VALVE.get(), "Thermal Valve");
        add(ModBlocks.THERMAL_HEAT_SINK.get(), "Thermal Sink");

        add(ModItems.GRAPHITE.get(), "Graphite");
        add(ModItems.RAW_URANIUM.get(), "Raw Uranium");
        add(ModItems.THORIUM.get(), "Thorium");
        add(ModItems.ENRICHED_URANIUM.get(), "Enriched Uranium");

        add(ModItems.STEEL_COMPOUND.get(), "Steel Compound");
        add(ModItems.GRAPHITE_TUBE.get(), "Graphite Tube");
        add(ModItems.REDSTONE_PROCESSOR.get(), "Redstone Processor");
        add(ModItems.POTASSIUM.get(), "Potassium");
        add(ModItems.SODIUM.get(), "Sodium");
        add(ModItems.URAN_THREE_CHLORIDE.get(), "Uran(III)-Chloride");
        add(ModItems.YELLOW_CAKE.get(), "Yellow Cake");
        add(ModItems.FLUORIDE.get(), "Fluorite");

        add(ModItems.GRAPHITE_INGOT.get(), "Graphite Ingot");
        add(ModItems.BLASTED_IRON_INGOT.get(), "Blasted Iron Ingot");
        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");

        add("fluid_type.thoriumreactors.hydrofluoride", "Hydrofluorite");

        add("fluid_type.thoriumreactors.molten_salt", "Molten Salt (142°)");
        add("fluid_type.thoriumreactors.heated_molten_salt", "Heated Molten Salt (654°)");

        add("itemGroup.thoriumreactors.items", "Thorium Reactors");
    }
}
