package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

public class AmericanLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public AmericanLanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThoriumReactors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get(), "Thorium Crafting Table");
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get().getDescriptionId() + "_description", "Crafting with a 5x5 crafting area for almost all thorium recipes! JEI mod is recommended.");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get(), "Thorium Chest");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Steel and Thorium, has a higher capacity for items!");
        add(ModBlocks.STEEL_CHEST_BLOCK.get(), "Steel Chest");
        add(ModBlocks.STEEL_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Steel, has a high capacity for items!");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), "Blasted Iron Chest");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Blasted Iron, has a big capacity for items!");

        add(ModBlocks.MANGANESE_ORE.get(), "Manganese Ore");
        add(ModBlocks.DEEPSLATE_MANGANESE_ORE.get(), "Deepslate Manganese Ore");
        add(ModBlocks.CHROMITE_ORE.get(), "Chromite Ore");
        add(ModBlocks.DEEPSLATE_CHROMITE_ORE.get(), "Deepslate Chromite Ore");
        add(ModBlocks.MOLYBDENUM_ORE.get(), "Molybdenum Ore");
        add(ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(), "Deepslate Molybdenum Ore");
        add(ModBlocks.NICKEL_ORE.get(), "Nickel Ore");
        add(ModBlocks.DEEPSLATE_NICKEL_ORE.get(), "Deepslate Nickel Ore");
        add(ModBlocks.TITANIC_IRON_ORE.get(), "Titanic Iron Ore");
        add(ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), "Deepslate Titanic Iron Ore");
        add(ModBlocks.BAUXITE_ORE.get(), "Bauxite");
        add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), "Deepslate Bauxite");
        add(ModBlocks.PYROCHLOR_ORE.get(), "Pyrochlore Ore");
        add(ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(), "Deepslate Pyrochlore Ore");
        add(ModBlocks.URANIUM_ORE.get(), "Uranium Ore");
        add(ModBlocks.DEEPSLATE_URANIUM_ORE.get(), "Deepslate Uranium Ore");
        add(ModBlocks.GRAPHITE_ORE.get(), "Graphite Ore");
        add(ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), "Deepslate Graphite Ore");
        add(ModBlocks.FLUORITE_ORE.get(), "Fluorite Ore");
        add(ModBlocks.DEEPSLATE_FLUORITE_ORE.get(), "Deepslate Fluorite Ore");

        add(ModBlocks.BLASTED_STONE.get(), "Blasted Stone");
        add(ModBlocks.THORIUM_BLOCK.get(), "Thorium Block");
        add(ModBlocks.BLASTED_IRON_BLOCK.get(), "Blasted Iron Block");
        add(ModBlocks.STEEL_BLOCK.get(), "Steel Block");
        add(ModBlocks.MANGANESE_BLOCK.get(), "Manganese Block");
        add(ModBlocks.CHROMIUM_BLOCK.get(), "Chromium Block");
        add(ModBlocks.MOLYBDENUM_BLOCK.get(), "Molybdenum Block");
        add(ModBlocks.NICKEL_BLOCK.get(), "Nickel Block");
        add(ModBlocks.TITANIUM_BLOCK.get(), "Titanium Block");
        add(ModBlocks.ALUMINUM_BLOCK.get(), "Aluminum Block");
        add(ModBlocks.NIOB_BLOCK.get(), "Niob Block");
        add(ModBlocks.COBALT_BLOCK.get(), "Cobalt Block");
        add(ModBlocks.URANIUM_BLOCK.get(), "Uranium Block");
        add(ModBlocks.GRAPHITE_BLOCK.get(), "Graphite Block");
        add(ModBlocks.FLUORITE_BLOCK.get(), "Fluorite Block");

        add(ModBlocks.BLAST_FURNACE_BLOCK.get(), "Blast Furnace");
        add(ModBlocks.BLAST_FURNACE_BLOCK.get().getDescriptionId() + "_description", "Uses high temperates to mix and combine materials!");
        add(ModBlocks.GENERATOR_BLOCK.get(), "Fuel Generator");
        add(ModBlocks.GENERATOR_BLOCK.get().getDescriptionId() + "_description", "The Generator uses Fuel to produce some delicious energy!");
        add(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), "Electrolytic Separator");
        add(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get().getDescriptionId() + "_description", "The Electrolytic Salt Separator separates salt from water with electrolysis!");
        add(ModBlocks.FLUID_EVAPORATION_BLOCK.get(), "Fluid Evaporator");
        add(ModBlocks.FLUID_EVAPORATION_BLOCK.get().getDescriptionId() + "_description", "The Fluid Evaporator is used to collect salt remains from vaporized water!");
        add(ModBlocks.SALT_MELTER_BLOCK.get(), "Salt Melter");
        add(ModBlocks.SALT_MELTER_BLOCK.get().getDescriptionId() + "_description", "The Salt Melter concentrates salts together with enriched uranium to form Molten Salt, the reactors foundation!");
        add(ModBlocks.CONCENTRATOR_BLOCK.get(), "Concentrator");
        add(ModBlocks.CONCENTRATOR_BLOCK.get().getDescriptionId() + "_description", "The Concentrator compacts materials. Mainly used for obtaining Uranium Yellow Cake from Uranium!");
        add(ModBlocks.DECOMPOSER_BLOCK.get(), "Decomposer");
        add(ModBlocks.DECOMPOSER_BLOCK.get().getDescriptionId() + "_description", "The Decomposer mixes fluor with fluids into others. Mainly used for Hydrofluorite production!");
        add(ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), "Uranium Oxidizer");
        add(ModBlocks.URANIUM_OXIDIZER_BLOCK.get().getDescriptionId() + "_description", "The Uranium Oxidizer mixes uranium with fluids. Mainly used for Uranium Hexafluorite production!");
        add(ModBlocks.FLUID_ENRICHER_BLOCK.get(), "Fluid Enricher");
        add(ModBlocks.FLUID_ENRICHER_BLOCK.get().getDescriptionId() + "_description", "The Fluid Enricher mixes substances into fluids. Mainly used for Molten Salt regeneration!");
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), "Fluid Centrifuge");
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get().getDescriptionId() + "_description", "The Fluid Centrifuge is mainly used to modify fluids by atomic separation at fast rotation. Mainy used to enrich UF6 (Hexafluorite)!");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get(), "Crystallizer");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get().getDescriptionId() + "_description", "The Crystallizer transforms fluids into their solid variant. Mainly UF6 (Enriched Uranium Hexafluorite)!");

        add(ModBlocks.SIMPLE_ENERGY_TANK.get(), "Simple Energy Tank");
        add(ModBlocks.SIMPLE_ENERGY_TANK.get().getDescriptionId() + "_description", "Used to store energy in small amounts! First stage of three, with small capacity");
        add(ModBlocks.GENERIC_ENERGY_TANK.get(), "Generic Energy Tank");
        add(ModBlocks.GENERIC_ENERGY_TANK.get().getDescriptionId() + "_description", "Used to store energy in big amounts! Second stage, with bigger capacity");
        add(ModBlocks.PROGRESSIVE_ENERGY_TANK.get(), "Progressive Energy Tank");
        add(ModBlocks.PROGRESSIVE_ENERGY_TANK.get().getDescriptionId() + "_description", "Used to store energy in huge amounts! Last stage, therefore with a huge capacity");
        add(ModBlocks.CREATIVE_ENERGY_TANK.get(), "Creative Energy Tank");
        add(ModBlocks.CREATIVE_ENERGY_TANK.get().getDescriptionId() + "_description", "Has infinite storage. Not obtainable in survival!");

        add(ModBlocks.SIMPLE_FLUID_TANK.get(), "Simple Fluid Tank");
        add(ModBlocks.SIMPLE_FLUID_TANK.get().getDescriptionId() + "_description", "Used to store fluids in small amounts! First stage of three, with small capacity");
        add(ModBlocks.GENERIC_FLUID_TANK.get(), "Generic Fluid Tank");
        add(ModBlocks.GENERIC_FLUID_TANK.get().getDescriptionId() + "_description", "Used to store fluids in big amounts! Second stage, with bigger capacity");
        add(ModBlocks.PROGRESSIVE_FLUID_TANK.get(), "Progressive Fluid Tank");
        add(ModBlocks.PROGRESSIVE_FLUID_TANK.get().getDescriptionId() + "_description", "Used to store fluids in huge amounts! Last stage, therefore with a huge capacity");
        add(ModBlocks.CREATIVE_FLUID_TANK.get(), "Creative Fluid Tank");
        add(ModBlocks.CREATIVE_FLUID_TANK.get().getDescriptionId() + "_description", "Has infinite storage. Not obtainable in survival!");

        add(ModBlocks.REACTOR_CASING.get(), "Reactor Casing");
        add(ModBlocks.REACTOR_CASING.get().getDescriptionId() + "_description", "Fundamental framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), "Reactor Controller");
        add(ModBlocks.REACTOR_CONTROLLER_BLOCK.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_VALVE.get(), "Reactor Valve");
        add(ModBlocks.REACTOR_VALVE.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_ROD_CONTROLLER.get(), "Reactor Rod Controller");
        add(ModBlocks.REACTOR_ROD_CONTROLLER.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_CORE.get(), "Reactor Core");
        add(ModBlocks.REACTOR_CORE.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_GLASS.get(), "Reactor Glass");
        add(ModBlocks.REACTOR_GLASS.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");
        add(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), "Graphite Moderator");
        add(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get().getDescriptionId() + "_description", "Framing block for Thorium Reactor. Used to build a reactor!");

        add(ModBlocks.THERMAL_CONDUCTOR.get(), "Thermal Conductor");
        add(ModBlocks.THERMAL_CONDUCTOR.get().getDescriptionId() + "_description", "Framing block for Heat Exchanger. Used to transform heat!");
        add(ModBlocks.THERMAL_CONTROLLER.get(), "Thermal Controller");
        add(ModBlocks.THERMAL_CONTROLLER.get().getDescriptionId() + "_description", "Framing block for Heat Exchanger. Used to transform heat!");
        add(ModBlocks.THERMAL_VALVE.get(), "Thermal Valve");
        add(ModBlocks.THERMAL_VALVE.get().getDescriptionId() + "_description", "Framing block for Heat Exchanger. Needed for fluid input and output");
        add(ModBlocks.THERMAL_HEAT_SINK.get(), "Thermal Sink");
        add(ModBlocks.THERMAL_HEAT_SINK.get().getDescriptionId() + "_description", "Framing block for Heat Exchanger. Needed to transfer heat to air.");

        add(ModBlocks.TURBINE_CONTROLLER_BLOCK.get(), "Turbine Controller");
        add(ModBlocks.TURBINE_CONTROLLER_BLOCK.get().getDescriptionId() + "_description", "Controlling block of turbine. Used to build a turbine!");
        add(ModBlocks.TURBINE_CASING.get(), "Turbine Casing");
        add(ModBlocks.TURBINE_CASING.get().getDescriptionId() + "_description", "Framing block for turbine. Used to build a turbine!");
        add(ModBlocks.TURBINE_GLASS.get(), "Turbine Glass");
        add(ModBlocks.TURBINE_GLASS.get().getDescriptionId() + "_description", "Framing block for turbine. Used to build a turbine!");
        add(ModBlocks.TURBINE_VALVE.get(), "Turbine Valve");
        add(ModBlocks.TURBINE_VALVE.get().getDescriptionId() + "_description", "Needed to insert generated steam into the turbine.");
        add(ModBlocks.TURBINE_VENT.get(), "Turbine Vent");
        add(ModBlocks.TURBINE_VENT.get().getDescriptionId() + "_description", "Lets excess water and vapor escape the turbine. Needed for proper functionality!");
        add(ModBlocks.TURBINE_ROTOR.get(), "Turbine Rotor");
        add(ModBlocks.TURBINE_ROTOR.get().getDescriptionId() + "_description", "Needed in a turbine for energy production.");
        add(ModBlocks.TURBINE_ROTATION_MOUNT.get(), "Turbine Rotation Mount");
        add(ModBlocks.TURBINE_ROTATION_MOUNT.get().getDescriptionId() + "_description", "Essential for the turbine rotor to be mounted. Otherwise the turbine may fail catastrophic.");
        add(ModBlocks.TURBINE_POWER_PORT.get(), "Turbine Power Port");
        add(ModBlocks.TURBINE_POWER_PORT.get().getDescriptionId() + "_description", "Outputs generated energy. Used to build a turbine!");
        add(ModBlocks.ELECTROMAGNETIC_COIL.get(), "Electromagnetic Coil");
        add(ModBlocks.ELECTROMAGNETIC_COIL.get().getDescriptionId() + "_description", "Needed in a turbine for energy production.");
        add(ModItems.TURBINE_BLADE.get(), "Turbine Blade");
        add(ModItems.TURBINE_BLADE.get().getDescriptionId() + "_description", "Needed in a turbine for energy production.");

        add(ModItems.GRAPHITE_TUBE.get(), "Graphite Tube");
        add(ModItems.GRAPHITE_CRYSTAL.get(), "Graphite");

        add(ModItems.CHROMIUM_INGOT.get(), "Chromium Ingot");
        add(ModItems.GRAPHITE_INGOT.get(), "Graphite Ingot");
        add(ModItems.STEEL_NUGGET.get(), "Steel Nugget");
        add(ModItems.BLASTED_IRON_INGOT.get(), "Blasted Iron Ingot");
        add(ModItems.TITANIUM_INGOT.get(), "Titanium Ingot");
        add(ModItems.MOLYBDENUM_INGOT.get(), "Molybdenum Ingot");
        add(ModItems.COBALT_INGOT.get(), "Cobalt Ingot");
        add(ModItems.FLUORITE_INGOT.get(), "Fluorite Ingot");
        add(ModItems.NICKEL_INGOT.get(), "Nickel Ingot");
        add(ModItems.URANIUM_INGOT.get(), "Uranium Ingot");
        add(ModItems.ALUMINUM_INGOT.get(), "Aluminum Ingot");
        add(ModItems.MANGANESE_INGOT.get(), "Manganese Ingot");
        add(ModItems.NIOB_INGOT.get(), "Niob Ingot");

        add(ModItems.CHROMIUM_NUGGET.get(), "Chromium Nugget");
        add(ModItems.GRAPHITE_NUGGET.get(), "Graphite Nugget");
        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");
        add(ModItems.BLASTED_IRON_NUGGET.get(), "Blasted Iron Nugget");
        add(ModItems.TITANIUM_NUGGET.get(), "Titanium Nugget");
        add(ModItems.MOLYBDENUM_NUGGET.get(), "Molybdenum Nugget");
        add(ModItems.COBALT_NUGGET.get(), "Cobalt Nugget");
        add(ModItems.FLUORITE_NUGGET.get(), "Fluorite Nugget");
        add(ModItems.NICKEL_NUGGET.get(), "Nickel Nugget");
        add(ModItems.URANIUM_NUGGET.get(), "Uranium Nugget");
        add(ModItems.ALUMINUM_NUGGET.get(), "Aluminum Nugget");
        add(ModItems.MANGANESE_NUGGET.get(), "Manganese Nugget");
        add(ModItems.NIOB_NUGGET.get(), "Niob Nugget");

        add(ModItems.RAW_URANIUM.get(), "Raw Uranium");
        add(ModItems.THORIUM.get(), "Raw Thorium");
        add(ModItems.ENRICHED_URANIUM.get(), "Enriched Uranium Pellet");
        add(ModItems.DEPLETED_URANIUM.get(), "Depleted Uranium Pellet");
        add(ModItems.CONFIGURATOR.get(), "Configurator");
        add(ModItems.MODULE_EMPTY.get(), "Empty Module");
        add(ModItems.MODULE_IO.get(), "I/O Module");
        add(ModItems.MODULE_ENERGY.get(), "Energy Module");
        add(ModItems.MODULE_STORAGE.get(), "Storage Module");
        add(ModItems.MODULE_TANK.get(), "Tank Module");
        add(ModItems.MODULE_SENSOR.get(), "Sensor Module");
        add(ModItems.MODULE_PROCESSING.get(), "Processing Module");
        add(ModItems.REDSTONE_PROCESSOR.get(), "Redstone Processor");
        add(ModItems.POTASSIUM.get(), "Potassium");
        add(ModItems.SODIUM.get(), "Sodium");
        add(ModItems.URAN_THREE_CHLORIDE.get(), "Uran(III)-Chloride");
        add(ModItems.YELLOW_CAKE.get(), "Uranium Yellow Cake");
        add(ModItems.FLUORITE.get(), "Fluorite");
        add(ModItems.MOLTEN_SALT_BUCKET.get(), "Molten Salt Bucket");
        add(ModItems.DEPLETED_MOLTEN_SALT_BUCKET.get(), "Depleted Molten Salt Bucket");
        add(ModItems.HEATED_MOLTEN_SALT_BUCKET.get(), "Heated Molten Salt Bucket");
        add(ModItems.HYDROFLUORITE_BUCKET.get(), "Hydrofluorite Bucket");
        add(ModItems.URANIUM_HEXAFLUORITE_BUCKET.get(), "Uranium Hexafluorite Bucket");
        add(ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get(), "Enriched Uranium Hexafluorite Bucket");
        add(ModItems.STEAM_BUCKET.get(), "Steam Bucket");

        addGuiText("machines.state.idle", "Idle");
        addGuiText("machines.state.running", "Running");

        addGuiText("machines.tooltip.empty", "Empty");
        addGuiText("machines.tooltip.not_placed", "Not Placed Yet");
        addGuiText("machines.tooltip.energy_buffer", "Energy Buffer: ");
        addGuiText("machines.tooltip.recipe_status", "Recipe Status: ");
        addGuiText("machines.tooltip.contains_items", "Inventory contains items!");

        addGuiText("machines.text.amount", "Amount:");
        addGuiText("machines.text.capacity", "Capacity:");
        addGuiText("machines.text.fillage", "Fillage:");

        addGuiText("machines.tooltip.dump_instantly", "Dump stored liquid instantly");
        addGuiText("machines.tooltip.auto_dump", "Auto Dump: ");
        addGuiText("machines.tooltip.fluid", "Fluid: ");
        addGuiText("machines.tooltip.energy", "Energy: ");
        addGuiText("machines.tooltip.only_while_running", "Only while running");
        addGuiText("machines.tooltip.infinite", "Infinite");
        addGuiText("machines.tooltip.usage", "Usage:");
        addGuiText("machines.tooltip.power", "Power:");
        addGuiText("machines.tooltip.redstone", "Redstone:");
        addGuiText("machines.tooltip.ignore", "Ignore");
        addGuiText("machines.tooltip.normal", "Normal");
        addGuiText("machines.tooltip.inverted", "Inverted");
        addGuiText("machines.tooltip.warning", "Warning!");
        addGuiText("machines.tooltip.needs_air", "Machine needs air to ventilate!");
        addGuiText("machines.tooltip.make_space", "Make space above it!");
        addGuiText("machines.tooltip.hold", "Hold ");
        addGuiText("machines.tooltip.for_details", " for further details.");
        addGuiText("machines.tooltip.for_description", " for a block description.");
        addGuiText("machines.tooltip.capacity", "Capacity: ");

        addGuiText("machines.concentrator.name", "Concentrating");
        addGuiText("machines.blast_furnace.name", "Blasting");
        addGuiText("machines.oxidizer.name", "Oxidizing");
        addGuiText("machines.fluid_enricher.name", "Fluid Enriching");
        addGuiText("machines.decomposer.name", "Decomposing");
        addGuiText("machines.crystallizer.name", "Crystallizing");
        addGuiText("machines.electrolytic_salt_separator.name", "Electrolysing");
        addGuiText("machines.fluid_centrifuge.name", "Centrifuging");
        addGuiText("machines.fluid_evaporator.name", "Evaporating");
        addGuiText("machines.generator.name", "Energy Production");
        addGuiText("machines.salt_melter.name", "Salt Melting");
        addGuiText("machines.fluid_tank.name", "Fluid Tank");
        addGuiText("machines.thorium_crafting_table.name", "Thorium Crafting");

        addGuiText("turbine.top_info.turbine", "Turbine");
        addGuiText("turbine.top_info.active", "Active");
        addGuiText("turbine.top_info.inactive", "Inactive");
        addGuiText("turbine.top_info.producing", "Producing: ");
        addGuiText("turbine.top_info.speed", "Speed: ");
        addGuiText("turbine.top_info.flowrate", "Flowrate: ");
        addGuiText("turbine.top_info.best_performing_at", "Best performing @ 1900 - 2050 Rpm");
        addGuiText("turbine.top_info.rpm", "Rpm");
        addGuiText("turbine.text.valve_io", "Valve I/O Mode: ");

        addGuiText("reactor.tooltip.remove", "Remove current");
        addGuiText("reactor.tooltip.copy_to_all", "Copy to all turbines.");
        addGuiText("reactor.tooltip.current", "Current");
        addGuiText("reactor.tooltip.all", "All");
        addGuiText("reactor.top_info.reactor", "Reactor");
        addGuiText("reactor.top_info.active", "Active");
        addGuiText("reactor.top_info.inactive", "Inactive");
        addGuiText("reactor.top_info.rod_insert", "Rod Insert: ");
        addGuiText("reactor.top_info.uranium_fuel", "Uranium Fuel: ");
        addGuiText("reactor.top_info.reactor_load", "Reactor Load: ");
        addGuiText("reactor.top_info.status", "Status: ");
        addGuiText("reactor.top_info.type", "Type: ");
        addGuiText("reactor.text.valve_io", "Valve I/O Mode: ");
        addGuiText("reactor.text.enriched", "Enriched");
        addGuiText("reactor.text.molten_salt", "Molten Salt");
        addGuiText("reactor.text.turbine_generator", "TURBINE GENERATOR");
        addGuiText("reactor.text.steam", "STEAM");
        addGuiText("reactor.text.coil_engage", "COIL ENGAGE");
        addGuiText("reactor.text.activated", "ACTIVATED");
        addGuiText("reactor.text.on", "ON");
        addGuiText("reactor.text.off", "OFF");
        addGuiText("reactor.text.producing", "Producing: ");
        addGuiText("reactor.text.speed", "Speed: ");
        addGuiText("reactor.text.flowrate", "Flowrate: ");
        addGuiText("reactor.text.no_turbine_added", "No turbine added!");
        addGuiText("reactor.text.use_configurator_to_link", "Use configurator to link.");
        addGuiText("reactor.text.start", "START");
        addGuiText("reactor.text.run", "RUN");
        addGuiText("reactor.text.stop", "STOP");
        addGuiText("reactor.text.insert_rods", "INSERT RODS");
        addGuiText("reactor.text.into_core", "INTO CORE");
        addGuiText("reactor.text.manual", "MANUAL");
        addGuiText("reactor.text.scram", "SCRAM");
        addGuiText("reactor.text.operation", "OPERATION");
        addGuiText("reactor.text.emergency", "EMERGENCY");
        addGuiText("reactor.text.fuel_status", "FUEL STATUS");
        addGuiText("reactor.text.turbine", "Turbine");
        addGuiText("reactor.text.thorium_reactor", "Thorium Reactor");
        addGuiText("reactor.text.reactor_overview_interface", "REACTOR OVERVIEW INTERFACE");
        addGuiText("reactor.text.operating_time", "OPERATING TIME");
        addGuiText("reactor.text.main_power", "MAIN POWER");
        addGuiText("reactor.text.reactor_status", "REACTOR STATUS");
        addGuiText("reactor.text.reactor_load", "REACTOR LOAD");
        addGuiText("reactor.text.containment", "CONTAINMENT");
        addGuiText("reactor.text.radiation", "RADIATION");
        addGuiText("reactor.text.temp", "TEMP");
        addGuiText("reactor.text.flow", "FLOW");
        addGuiText("reactor.text.speed_cap", "SPEED");
        addGuiText("reactor.text.generation", "GENER.");
        addGuiText("reactor.text.normal", "NORMAL");
        addGuiText("reactor.text.overload", "OVERLOAD");
        addGuiText("reactor.text.critical", "CRITICAL");
        addGuiText("reactor.text.overview", "OVERVIEW");
        addGuiText("reactor.text.usvh", "uSV/h");
        addGuiText("reactor.text.mbs", "mB/s");
        addGuiText("reactor.text.rpm", "RPM");
        addGuiText("reactor.text.fet", "FE/t");
        addGuiText("reactor.text.uran", "Uran: ");
        addGuiText("reactor.text.fuel", "Fuel: ");
        addGuiText("reactor.text.unset", "Unset");
        addGuiText("reactor.text.select", "Select");
        addGuiText("reactor.text.rod", "Rod");
        addGuiText("reactor.text.valve_manipulation", "MANUAL VALVE MANIPULATION");
        addGuiText("reactor.text.system_chart", "OPERATIONAL SYSTEM CHART");
        addGuiText("reactor.text.rod_insert", "ROD INSERT");
        addGuiText("reactor.text.set", "SET");
        addGuiText("reactor.text.fuel_load", "FUEL LOAD");
        addGuiText("reactor.text.molten_salt_low_warning", "Warning: Emergency Scram! Molten Salt level low");

        addGuiText("machines.top_info.energy", "Energy: ");
        addGuiText("machines.top_info.recipe", "Recipe: ");
        addGuiText("machines.top_info.producing", "Producing: ");
        addGuiText("machines.top_info.fuel", "Fuel: ");

        addGuiText("machines.text.fuel", "Fuel: ");
        addGuiText("machines.text.tank", "Tank: ");
        addGuiText("machines.text.gen", "Gen: ");

        addGuiText("items.text.turbine_selected", "Turbine selected: ");
        addGuiText("items.text.thermal_selected", "Thermal selected: ");
        addGuiText("items.text.turbine_saved_to_configurator", "Turbine saved to configurator");
        addGuiText("items.text.turbine_saved_to_reactor", "Turbine saved to reactor.");
        addGuiText("items.text.thermal_saved_to_configurator", "Thermal saved to configurator");
        addGuiText("items.text.thermal_saved_to_reactor", "Thermal saved to reactor.");

        add(ModBlocks.MACHINE_CASING.get(), "Machine Casing");

        add(ModBlocks.FACTORY_BLOCK.get(), "Factory Block");
        add(ModBlocks.INVERTED_FACTORY_BLOCK.get(), "Inverted Factory Block");
        add(ModBlocks.BLACK_FACTORY_BLOCK.get(), "Black Factory Block");
        add(ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get(), "Inverted Black Factory Block");

        add(ModBlocks.GRATE_FLOOR_BLOCK.get(), "Floor Grate Block");
        add(ModBlocks.INDUSTRAL_BLOCK.get(), "Industrial Block");
        add(ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get(), "Industrial Block Big Tile");
        add(ModBlocks.INDUSTRAL_BLOCK_PAVING.get(), "Industrial Block Paving");
        add(ModBlocks.INDUSTRAL_BLOCK_BRICK.get(), "Industrial Block Brick");
        add(ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get(), "Industrial Block Smooth");
        add(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get(), "Industrial Block Floor");

        add(ModBlocks.BLACK_INDUSTRAL_BLOCK.get(), "Black Industrial Block");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get(), "Black Industrial Block Big Tile");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get(), "Black Industrial Block Paving");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get(), "Black Industrial Block Brick");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get(), "Black Industrial Block Smooth");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get(), "Black Industrial Block Floor");

        add(ModBlocks.WHITE_INDUSTRAL_BLOCK.get(), "White Industrial Block");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_BIG_TILE.get(), "White Industrial Block Big Tile");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_PAVING.get(), "White Industrial Block Paving");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_BRICK.get(), "White Industrial Block Brick");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_SMOOTH.get(), "White Industrial Block Smooth");

        add(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get(), "Lined Warning Block Black/Yellow Left");
        add(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_RIGHT.get(), "Lined Warning Block Black/Yellow Right");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_LEFT.get(), "Lined Warning Block White/Orange Left");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_RIGHT.get(), "Lined Warning Block White/Orange Right");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_LEFT.get(), "Lined Warning Block White/Black Left");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_RIGHT.get(), "Lined Warning Block White/Black Right");

        add("item_input", "Item Input");
        add("item_output", "Item Output");
        add("coolant_input", "Coolant Input");
        add("coolant_output", "Coolant Output");
        add("fluid_input", "Fluid Input");
        add("fluid_output", "Fluid Output");
        add("heating_fluid_input", "Heat Input");
        add("heating_fluid_output", "Heat Output");

        add("block.thoriumreactors.hydrofluorite_block", "Hydrofluorite");
        add("block.thoriumreactors.molten_salt_block", "Molten Salt");
        add("block.thoriumreactors.depleted_molten_salt_block", "Depleted Molten Salt");
        add("block.thoriumreactors.heated_molten_salt_block", "Heated Molten Salt");
        add("block.thoriumreactors.uranium_hexafluorite_block", "Uranium Hexafluorite");
        add("block.thoriumreactors.enriched_uranium_hexafluorite_block", "Enriched Uranium Hexafluorite");
        add("block.thoriumreactors.steam_block", "Steam");

        add("fluid_type.thoriumreactors.hydrofluorite", "Hydrofluorite");
        add("fluid_type.thoriumreactors.molten_salt", "Molten Salt");
        add("fluid_type.thoriumreactors.depleted_molten_salt", "Depleted Molten Salt");
        add("fluid_type.thoriumreactors.heated_molten_salt", "Heated Molten Salt");
        add("fluid_type.thoriumreactors.uranium_hexafluorite", "Uranium Hexafluorite");
        add("fluid_type.thoriumreactors.enriched_uranium_hexafluorite", "Enriched Uranium Hexafluorite");
        add("fluid_type.thoriumreactors.steam", "Steam");

        add("keybind.thoriumreactors.description", "Show Description");
        add("keybind.thoriumreactors.details", "Show Details");
        add("itemGroup.thoriumreactors", "Thorium Reactors");
        add("itemGroup.thoriumreactors.resources", "Thorium R. - Resources");
        add("itemGroup.thoriumreactors.machinery", "Thorium R. - Machinery");
        add("itemGroup.thoriumreactors.building", "Thorium R. - Building");
        add("death.attack.thoriumreactors.radioactive_overdosis", "%1$s died of a radioactive overdose.");
        add("death.attack.thoriumreactors.grind", "%1$s met a crushing end in a turbine. Rest in pieces");
    }

    private void addGuiText(String name, String text) {
        add("text.thoriumreactors.inventory." + name, text);
    }

}
