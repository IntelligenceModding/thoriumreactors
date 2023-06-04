package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThoriumReactors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get(), "Thorium Crafting Table");
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get().getDescriptionId() + "_description", "This Crafting Table has a 5x5 crafting area for almost all Thorium recipes! Use the Just Enough Items mod (JEI) for all recipes!");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get(), "Thorium Chest");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Steel and Thorium, has a higher capacity for items!");
        add(ModBlocks.STEEL_CHEST_BLOCK.get(), "Steel Chest");
        add(ModBlocks.STEEL_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Steel, has a high capacity for items!");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), "Blasted Iron Chest");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get().getDescriptionId() + "_description", "Chest made of Blasted Iron, has a big capacity for items!");

        add(ModBlocks.BLASTED_STONE.get(), "Blasted Stone");
        add(ModBlocks.BLASTED_IRON_BLOCK.get(), "Blasted Iron Block");
        add(ModBlocks.STEEL_BLOCK.get(), "Steel Block");
        add(ModBlocks.GRAPHITE_BLOCK.get(), "Graphite Block");
        add(ModBlocks.THORIUM_BLOCK.get(), "Thorium Block");
        add(ModBlocks.FLUORITE_BLOCK.get(), "Fluorite Block");
        add(ModBlocks.URANIUM_BLOCK.get(), "Uranium Block");

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
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), "Fluid Centrifuge");
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get().getDescriptionId() + "_description", "The Fluid Centrifuge is mainly used to modify fluids by atomic separation at fast rotation. Mainy used to enrich UF6 (Hexafluorite)!");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get(), "Crystallizer");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get().getDescriptionId() + "_description", "The Crystallizer transforms fluids into their solid variant. Mainly UF6 (Enriched Uranium Hexafluorite)!");

        add(ModBlocks.SIMPLE_FLUID_TANK.get(), "Simple Fluid Tank");

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

        add(ModItems.GRAPHITE_CRYSTAL.get(), "Graphite");
        add(ModItems.RAW_URANIUM.get(), "Raw Uranium");
        add(ModItems.THORIUM.get(), "Raw Thorium");
        add(ModItems.ENRICHED_URANIUM.get(), "Enriched Uranium Pellet");
        add(ModItems.CONFIGURATOR.get(), "Configurator");
        add(ModItems.GRAPHITE_TUBE.get(), "Graphite Tube");
        add(ModItems.REDSTONE_PROCESSOR.get(), "Redstone Processor");
        add(ModItems.POTASSIUM.get(), "Potassium");
        add(ModItems.SODIUM.get(), "Sodium");
        add(ModItems.URAN_THREE_CHLORIDE.get(), "Uran(III)-Chloride");
        add(ModItems.YELLOW_CAKE.get(), "Uranium Yellow Cake");
        add(ModItems.FLUORITE.get(), "Fluorite");
        add(ModItems.MOLTEN_SALT_BUCKET.get(), "Molten Salt (142°) Bucket");
        add(ModItems.HEATED_MOLTEN_SALT_BUCKET.get(), "Heated Molten Salt (654°) Bucket");
        add(ModItems.HYDROFLUORITE_BUCKET.get(), "Hydrofluorite Bucket");
        add(ModItems.URANIUM_HEXAFLUORITE_BUCKET.get(), "Uranium Hexafluorite Bucket");
        add(ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get(), "Enriched Uranium Hexafluorite Bucket");
        add(ModItems.STEAM_BUCKET.get(), "Steam Bucket");
        add(ModItems.GRAPHITE_INGOT.get(), "Graphite Ingot");
        add(ModItems.BLASTED_IRON_INGOT.get(), "Blasted Iron Ingot");
        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");

        add("fluid_type.thoriumreactors.hydrofluorite", "Hydrofluorite");
        add("fluid_type.thoriumreactors.molten_salt", "Molten Salt");
        add("fluid_type.thoriumreactors.heated_molten_salt", "Heated Molten Salt");
        add("fluid_type.thoriumreactors.uranium_hexafluorite", "Uranium Hexafluorite");
        add("fluid_type.thoriumreactors.enriched_uranium_hexafluorite", "Enriched Uranium Hexafluorite");
        add("fluid_type.thoriumreactors.steam", "Steam");

        add("keybind.thoriumreactors.description", "Show Description");
        add("keybind.thoriumreactors.details", "Show Details");
        add("itemGroup.thoriumreactors.items", "Thorium Reactors");
        add("death.attack.thoriumreactors.radioactive_overdosis", "%1$s died of a radioactive overdose.");
    }
}
