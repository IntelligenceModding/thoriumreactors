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
        add(ModBlocks.HARDENED_STONE.get(), "Hardened Smooth Stone");
        add(ModBlocks.REACTOR_CASING.get(), "Reactor Casing");
        add(ModBlocks.GRAPHITE_ORE.get(), "Graphite Ore");

        add(ModBlocks.THERMAL_CONDUCTOR.get(), "Thermal Conductor");
        add(ModBlocks.THERMAL_CONTROLLER.get(), "Thermal Controller");
        add(ModBlocks.THERMAL_VALVE.get(), "Thermal Valve");
        add(ModBlocks.THERMAL_HEAT_SINK.get(), "Thermal Sink");
        add(ModItems.GRAPHITE.get(), "Graphite");
        add(ModItems.RAW_URANIUM.get(), "Raw Uranium");
        add(ModItems.RAW_THORIUM.get(), "Raw Thorium");
        add(ModItems.ENRICHED_URANIUM.get(), "Enriched Uranium");

        add(ModItems.STEEL_COMPOUND.get(), "Steel Compound");
        add(ModItems.GRAPHITE_TUBE.get(), "Graphite Tube");

        add(ModItems.REDSTONE_PROCESSOR.get(), "Redstone Processor");

        add(ModItems.GRAPHITE_INGOT.get(), "Graphite Ingot");
        add(ModItems.BLASTED_IRON_INGOT.get(), "Blasted Iron Ingot");
        add(ModItems.STEEL_INGOT.get(), "Steel Ingot");

        add("itemGroup.thoriumreactors.items", "Thorium Reactors");
    }
}
