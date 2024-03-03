package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

public class GermanLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public GermanLanguageProvider(DataGenerator gen, String locale) {
        super(gen, ThoriumReactors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get(), "Thorium Werkbank");
        add(ModBlocks.THORIUM_CRAFTING_TABLE.get().getDescriptionId() + "_description", "Stellt, mithilfe eines 5x5 Werkbereiches, fast alle Thorium Rezepte her! Die JEI Mod wird empfohlen.");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get(), "Truhe aus Thorium");
        add(ModBlocks.THORIUM_CHEST_BLOCK.get().getDescriptionId() + "_description", "Truhe, hergestellt aus Stahl und Thorium. Hat eine sehr hohe Item Kapazität!");
        add(ModBlocks.STEEL_CHEST_BLOCK.get(), "Truhe aus Stahl");
        add(ModBlocks.STEEL_CHEST_BLOCK.get().getDescriptionId() + "_description", "Truhe, hergestellt aus Stahl. Hat eine relativ hohe Item Kapazität!");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), "Truhe aus gestrahltem Eisen");
        add(ModBlocks.BLASTED_IRON_CHEST_BLOCK.get().getDescriptionId() + "_description", "Truhe, hergestellt aus gestrahltem Eisen. Hat eine hohe Item Kapazität!");

        add(ModBlocks.MANGANESE_ORE.get(), "Manganerz");
        add(ModBlocks.DEEPSLATE_MANGANESE_ORE.get(), "Tiefenschiefer-Manganerz");
        add(ModBlocks.CHROMITE_ORE.get(), "Chromite Ore");
        add(ModBlocks.DEEPSLATE_CHROMITE_ORE.get(), "Tiefenschiefer-Chromerz");
        add(ModBlocks.MOLYBDENUM_ORE.get(), "Molybdänerz");
        add(ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(), "Tiefenschiefer-Molybdänerz");
        add(ModBlocks.NICKEL_ORE.get(), "Nickelerz");
        add(ModBlocks.DEEPSLATE_NICKEL_ORE.get(), "Tiefenschiefer-Nickelerz");
        add(ModBlocks.TITANIC_IRON_ORE.get(), "Titaneisenerz");
        add(ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), "Tiefenschiefer-Titaneisenerz");
        add(ModBlocks.BAUXITE_ORE.get(), "Bauxiterz");
        add(ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), "Tiefenschiefer-Bauxiterz");
        add(ModBlocks.PYROCHLOR_ORE.get(), "Pyrochlorerz");
        add(ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(), "Tiefenschiefer-Pyrochlorerz");
        add(ModBlocks.URANIUM_ORE.get(), "Uranerz");
        add(ModBlocks.DEEPSLATE_URANIUM_ORE.get(), "Tiefenschiefer-Uranerz");
        add(ModBlocks.GRAPHITE_ORE.get(), "Graphiterz");
        add(ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), "Tiefenschiefer-Graphiterz");
        add(ModBlocks.FLUORITE_ORE.get(), "Fluoriterz");
        add(ModBlocks.DEEPSLATE_FLUORITE_ORE.get(), "Tiefenschiefer-Fluoriterz");

        add(ModBlocks.BLASTED_STONE.get(), "Gestrahlter Stein");
        add(ModBlocks.THORIUM_BLOCK.get(), "Thoriumblock");
        add(ModBlocks.BLASTED_IRON_BLOCK.get(), "Gestrahlter Eisenblock");
        add(ModBlocks.STEEL_BLOCK.get(), "Stahlblock");
        add(ModBlocks.MANGANESE_BLOCK.get(), "Manganblock");
        add(ModBlocks.CHROMIUM_BLOCK.get(), "Chromblock");
        add(ModBlocks.MOLYBDENUM_BLOCK.get(), "Molybdänblock");
        add(ModBlocks.NICKEL_BLOCK.get(), "Nickelblock");
        add(ModBlocks.TITANIUM_BLOCK.get(), "Titanblock");
        add(ModBlocks.ALUMINUM_BLOCK.get(), "Aluminiumblock");
        add(ModBlocks.NIOB_BLOCK.get(), "Niobblock");
        add(ModBlocks.COBALT_BLOCK.get(), "Kobaltblock");
        add(ModBlocks.URANIUM_BLOCK.get(), "Uranblock");
        add(ModBlocks.GRAPHITE_BLOCK.get(), "Graphitblock");
        add(ModBlocks.FLUORITE_BLOCK.get(), "Fluoritblock");

        add(ModBlocks.BLAST_FURNACE_BLOCK.get(), "Hochofen");
        add(ModBlocks.BLAST_FURNACE_BLOCK.get().getDescriptionId() + "_description", "Verwendet hohe Temperaturen, um Materialien zu mischen und zu kombinieren!");
        add(ModBlocks.GENERATOR_BLOCK.get(), "Brennstoffgenerator");
        add(ModBlocks.GENERATOR_BLOCK.get().getDescriptionId() + "_description", "Der Generator verwendet Brennstoff, um leckere Energie zu erzeugen!");
        add(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), "Elektrolytischer Salzabscheider");
        add(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get().getDescriptionId() + "_description", "Der elektrolytische Salzabscheider trennt Salz durch Elektrolyse von Wasser!");
        add(ModBlocks.FLUID_EVAPORATION_BLOCK.get(), "Flüssigkeitsverdampfer");
        add(ModBlocks.FLUID_EVAPORATION_BLOCK.get().getDescriptionId() + "_description", "Der Flüssigkeitsverdampfer wird verwendet, um Salzreste aus verdampftem Wasser zu sammeln!");
        add(ModBlocks.SALT_MELTER_BLOCK.get(), "Salzschmelzer");
        add(ModBlocks.SALT_MELTER_BLOCK.get().getDescriptionId() + "_description", "Der Salzschmelzer konzentriert Salze zusammen mit angereichertem Uran, um geschmolzenes Salz, das Fundament für Reaktoren, zu bilden!");
        add(ModBlocks.CONCENTRATOR_BLOCK.get(), "Konzentrator");
        add(ModBlocks.CONCENTRATOR_BLOCK.get().getDescriptionId() + "_description", "Der Konzentrator komprimiert Materialien. Hauptsächlich zur Gewinnung von Uran-Yellowcake aus Uran!");
        add(ModBlocks.DECOMPOSER_BLOCK.get(), "Zersetzer");
        add(ModBlocks.DECOMPOSER_BLOCK.get().getDescriptionId() + "_description", "Der Zersetzer mischt Fluor mit Flüssigkeiten. Hauptsächlich für die Hydrofluorit-Produktion!");
        add(ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), "Uranoxidierer");
        add(ModBlocks.URANIUM_OXIDIZER_BLOCK.get().getDescriptionId() + "_description", "Der Uranoxidierer mischt Uran mit Flüssigkeiten. Hauptsächlich für die Produktion von Uran-Hexafluorit!");
        add(ModBlocks.FLUID_ENRICHER_BLOCK.get(), "Flüssigkeitsanreicherer");
        add(ModBlocks.FLUID_ENRICHER_BLOCK.get().getDescriptionId() + "_description", "Der Flüssigkeitsanreicherer mischt Substanzen mit Flüssigkeiten. Hauptsächlich für die Regeneration von Schmelzsalz genutzt!");
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), "Flüssigkeitszentrifuge");
        add(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get().getDescriptionId() + "_description", "Die Flüssigkeitszentrifuge dient hauptsächlich zur Modifikation von Flüssigkeiten durch atomare Trennung bei schneller Rotation. Hauptsächlich zur Anreicherung von UF6 (Hexafluorit)!");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get(), "Kristallisator");
        add(ModBlocks.CRYSTALLIZER_BLOCK.get().getDescriptionId() + "_description", "Der Kristallisator verwandelt Flüssigkeiten in ihre feste Form. Hauptsächlich UF6 (Angereichertes Uran-Hexafluorit)!");

        add(ModBlocks.SIMPLE_ENERGY_TANK.get(), "Einfacher Energietank");
        add(ModBlocks.SIMPLE_ENERGY_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Energie in kleinen Mengen zu speichern! Erste Stufe von drei, mit geringer Kapazität");
        add(ModBlocks.GENERIC_ENERGY_TANK.get(), "Generischer Energietank");
        add(ModBlocks.GENERIC_ENERGY_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Energie in großen Mengen zu speichern! Zweite Stufe, mit größerer Kapazität");
        add(ModBlocks.PROGRESSIVE_ENERGY_TANK.get(), "Progressiver Energietank");
        add(ModBlocks.PROGRESSIVE_ENERGY_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Energie in riesigen Mengen zu speichern! Letzte Stufe, mit größter Kapazität");
        add(ModBlocks.CREATIVE_ENERGY_TANK.get(), "Kreativ Energietank");
        add(ModBlocks.CREATIVE_ENERGY_TANK.get().getDescriptionId() + "_description", "Hat unbegrenzte Kapazität. In Überleben nicht erhältlich!");

        add(ModBlocks.SIMPLE_FLUID_TANK.get(), "Einfacher Flüssigkeitstank");
        add(ModBlocks.SIMPLE_FLUID_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Flüssigkeiten in kleinen Mengen zu speichern! Erste Stufe von drei, mit geringer Kapazität");
        add(ModBlocks.GENERIC_FLUID_TANK.get(), "Generischer Flüssigkeitstank");
        add(ModBlocks.GENERIC_FLUID_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Flüssigkeiten in großen Mengen zu speichern! Zweite Stufe, mit größerer Kapazität");
        add(ModBlocks.PROGRESSIVE_FLUID_TANK.get(), "Progressiver Flüssigkeitstank");
        add(ModBlocks.PROGRESSIVE_FLUID_TANK.get().getDescriptionId() + "_description", "Wird verwendet, um Flüssigkeiten in riesigen Mengen zu speichern! Letzte Stufe, mit größter Kapazität");
        add(ModBlocks.CREATIVE_FLUID_TANK.get(), "Kreativ Flüssigkeitstank");
        add(ModBlocks.CREATIVE_FLUID_TANK.get().getDescriptionId() + "_description", "Hat unbegrenzte Kapazität. In Überleben nicht erhältlich!");

        add(ModBlocks.REACTOR_CASING.get(), "Reaktorgehäuse");
        add(ModBlocks.REACTOR_CASING.get().getDescriptionId() + "_description", "Grundlegendes Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), "Reaktor Steuerblock");
        add(ModBlocks.REACTOR_CONTROLLER_BLOCK.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_VALVE.get(), "Reaktorventil");
        add(ModBlocks.REACTOR_VALVE.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_ROD_CONTROLLER.get(), "Reaktor Steuerstabregler");
        add(ModBlocks.REACTOR_ROD_CONTROLLER.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_CORE.get(), "Reaktorkern");
        add(ModBlocks.REACTOR_CORE.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_GLASS.get(), "Reaktorglas");
        add(ModBlocks.REACTOR_GLASS.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");
        add(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), "Graphitmoderator");
        add(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get().getDescriptionId() + "_description", "Rahmenelement für den Thoriumreaktor. Wird zum Bau eines Reaktors verwendet!");

        add(ModBlocks.THERMAL_CONDUCTOR.get(), "Thermischer Leitblock");
        add(ModBlocks.THERMAL_CONDUCTOR.get().getDescriptionId() + "_description", "Rahmenelement für Wärmetauscher. Wird für die Wärmeübertragung verwendet!");
        add(ModBlocks.THERMAL_CONTROLLER.get(), "Thermischer Steuerblock");
        add(ModBlocks.THERMAL_CONTROLLER.get().getDescriptionId() + "_description", "Rahmenelement für Wärmetauscher. Wird für die Wärmeübertragung verwendet!");
        add(ModBlocks.THERMAL_VALVE.get(), "Thermisches Ventil");
        add(ModBlocks.THERMAL_VALVE.get().getDescriptionId() + "_description", "Rahmenelement für Wärmetauscher. Wird für die Flüssigkeitsein- und ausgabe verwendet!");
        add(ModBlocks.THERMAL_HEAT_SINK.get(), "Thermischer Kühlkörper");
        add(ModBlocks.THERMAL_HEAT_SINK.get().getDescriptionId() + "_description", "Rahmenelement für Wärmetauscher. Wird für die Wärmeübertragung zur Luft verwendet!");

        add(ModBlocks.WATER_SOURCE_BLOCK.get(), "Wasserquelle");

        add(ModBlocks.TURBINE_CONTROLLER_BLOCK.get(), "Turbinen Steuerblock");
        add(ModBlocks.TURBINE_CONTROLLER_BLOCK.get().getDescriptionId() + "_description", "Steuerungsblock der Turbine. Wird zum Bau einer Turbine verwendet!");
        add(ModBlocks.TURBINE_CASING.get(), "Turbinen Gehäuse");
        add(ModBlocks.TURBINE_CASING.get().getDescriptionId() + "_description", "Rahmenblock für die Turbine. Wird zum Bau einer Turbine verwendet!");
        add(ModBlocks.TURBINE_GLASS.get(), "Turbinen Glas");
        add(ModBlocks.TURBINE_GLASS.get().getDescriptionId() + "_description", "Rahmenblock für die Turbine. Wird zum Bau einer Turbine verwendet!");
        add(ModBlocks.TURBINE_VALVE.get(), "Turbinen Ventil");
        add(ModBlocks.TURBINE_VALVE.get().getDescriptionId() + "_description", "Erforderlich, um erzeugten Dampf in die Turbine einzuführen.");
        add(ModBlocks.TURBINE_VENT.get(), "Turbinen Entlüftung");
        add(ModBlocks.TURBINE_VENT.get().getDescriptionId() + "_description", "Lässt überschüssiges Wasser und Dampf aus der Turbine entweichen. Für die ordnungsgemäße Funktion erforderlich!");
        add(ModBlocks.TURBINE_ROTOR.get(), "Turbinen Rotor");
        add(ModBlocks.TURBINE_ROTOR.get().getDescriptionId() + "_description", "Erforderlich in einer Turbine zur Energieerzeugung.");
        add(ModBlocks.TURBINE_ROTATION_MOUNT.get(), "Turbinen Rotorhalterung");
        add(ModBlocks.TURBINE_ROTATION_MOUNT.get().getDescriptionId() + "_description", "Unverzichtbar für die Montage des Turbinenrotors. Andernfalls kann die Turbine katastrophal versagen.");
        add(ModBlocks.TURBINE_POWER_PORT.get(), "Turbinen Stromanschluss");
        add(ModBlocks.TURBINE_POWER_PORT.get().getDescriptionId() + "_description", "Gibt erzeugte Energie aus. Wird zum Bau einer Turbine verwendet!");
        add(ModBlocks.ELECTROMAGNETIC_COIL.get(), "Elektromagnetische Spule");
        add(ModBlocks.ELECTROMAGNETIC_COIL.get().getDescriptionId() + "_description", "Erforderlich in einer Turbine zur Energieerzeugung.");
        add(ModItems.TURBINE_BLADE.get(), "Turbinen Rotorblatt");
        add(ModItems.TURBINE_BLADE.get().getDescriptionId() + "_description", "Erforderlich in einer Turbine zur Energieerzeugung.");

        add(ModItems.GRAPHITE_TUBE.get(), "Graphitrohr");
        add(ModItems.GRAPHITE_CRYSTAL.get(), "Graphit");
        add(ModItems.CHROMIUM_INGOT.get(), "Chrombarren");
        add(ModItems.GRAPHITE_INGOT.get(), "Graphitbarren");
        add(ModItems.STEEL_NUGGET.get(), "Stahlnugget");
        add(ModItems.BLASTED_IRON_INGOT.get(), "Gestrahlter Eisenbarren");
        add(ModItems.TITANIUM_INGOT.get(), "Titanbarren");
        add(ModItems.MOLYBDENUM_INGOT.get(), "Molybdänbarren");
        add(ModItems.COBALT_INGOT.get(), "Kobaltbarren");
        add(ModItems.FLUORITE_INGOT.get(), "Fluoritbarren");
        add(ModItems.NICKEL_INGOT.get(), "Nickelbarren");
        add(ModItems.URANIUM_INGOT.get(), "Uranbarren");
        add(ModItems.ALUMINUM_INGOT.get(), "Aluminiumbarren");
        add(ModItems.MANGANESE_INGOT.get(), "Manganbarren");
        add(ModItems.NIOB_INGOT.get(), "Niobbarren");

        add(ModItems.CHROMIUM_NUGGET.get(), "Chromnugget");
        add(ModItems.GRAPHITE_NUGGET.get(), "Graphitnugget");
        add(ModItems.STEEL_INGOT.get(), "Stahlbarren");
        add(ModItems.BLASTED_IRON_NUGGET.get(), "Gestrahlter Eisennugget");
        add(ModItems.TITANIUM_NUGGET.get(), "Titannugget");
        add(ModItems.MOLYBDENUM_NUGGET.get(), "Molybdännugget");
        add(ModItems.COBALT_NUGGET.get(), "Kobaltnugget");
        add(ModItems.FLUORITE_NUGGET.get(), "Fluoritnugget");
        add(ModItems.NICKEL_NUGGET.get(), "Nickelnugget");
        add(ModItems.URANIUM_NUGGET.get(), "Urannugget");
        add(ModItems.ALUMINUM_NUGGET.get(), "Aluminiumnugget");
        add(ModItems.MANGANESE_NUGGET.get(), "Mangannugget");
        add(ModItems.NIOB_NUGGET.get(), "Niobnugget");

        add(ModItems.RAW_URANIUM.get(), "Rohes Uran");
        add(ModItems.THORIUM.get(), "Rohes Thorium");
        add(ModItems.ENRICHED_URANIUM.get(), "Angereichertes Uranpellet");
        add(ModItems.DEPLETED_URANIUM.get(), "Abgereichertes Uranpellet");
        add(ModItems.CONFIGURATOR.get(), "Konfigurator");
        add(ModItems.MODULE_EMPTY.get(), "Leeres Modul");
        add(ModItems.MODULE_IO.get(), "I/O-Modul");
        add(ModItems.MODULE_ENERGY.get(), "Energie-Modul");
        add(ModItems.MODULE_STORAGE.get(), "Speicher-Modul");
        add(ModItems.MODULE_TANK.get(), "Tank-Modul");
        add(ModItems.MODULE_SENSOR.get(), "Sensor-Modul");
        add(ModItems.MODULE_PROCESSING.get(), "Verarbeitungs-Modul");
        add(ModItems.REDSTONE_PROCESSOR.get(), "Redstone-Prozessor");
        add(ModItems.POTASSIUM.get(), "Kalium");
        add(ModItems.SODIUM.get(), "Natrium");
        add(ModItems.URAN_THREE_CHLORIDE.get(), "Uran(III)-Chlorid");
        add(ModItems.YELLOW_CAKE.get(), "Uranerz-Gelbes Kuchen");
        add(ModItems.FLUORITE.get(), "Fluorit");
        add(ModItems.MOLTEN_SALT_BUCKET.get(), "Eimer mit geschmolzenem Salz");
        add(ModItems.DEPLETED_MOLTEN_SALT_BUCKET.get(), "Eimer mit aufgebrauchtem geschmolzenem Salz");
        add(ModItems.HEATED_MOLTEN_SALT_BUCKET.get(), "Eimer mit erhitztem geschmolzenem Salz");
        add(ModItems.HYDROFLUORITE_BUCKET.get(), "Eimer mit Hydrofluorit");
        add(ModItems.URANIUM_HEXAFLUORITE_BUCKET.get(), "Eimer mit Uranhexafluorit");
        add(ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get(), "Eimer mit angereichertem Uranhexafluorit");
        add(ModItems.STEAM_BUCKET.get(), "Eimer mit Dampf");

        addGuiText("machines.state.idle", "Inaktiv");
        addGuiText("machines.state.running", "Aktiv");

        addGuiText("machines.tooltip.empty", "Leer");
        addGuiText("machines.tooltip.not_placed", "Noch nicht platziert");
        addGuiText("machines.tooltip.energy_buffer", "Energiespeicher: ");
        addGuiText("machines.tooltip.recipe_status", "Rezeptstatus: ");
        addGuiText("machines.tooltip.contains_items", "Inventar enthält Gegenstände!");

        addGuiText("machines.text.amount", "Menge:");
        addGuiText("machines.text.capacity", "Kapazität:");
        addGuiText("machines.text.fillage", "Füllstand:");

        addGuiText("machines.tooltip.dump_instantly", "Flüssigkeit sofort ablassen");
        addGuiText("machines.tooltip.auto_dump", "Automatisches Ablassen: ");
        addGuiText("machines.tooltip.fluid", "Flüssigkeit: ");
        addGuiText("machines.tooltip.energy", "Energie: ");
        addGuiText("machines.tooltip.only_while_running", "Nur während des Betriebes");
        addGuiText("machines.tooltip.infinite", "Unbegrenzt");
        addGuiText("machines.tooltip.usage", "Verbrauch:");
        addGuiText("machines.tooltip.power", "Aktiv:");
        addGuiText("machines.tooltip.redstone", "Redstone:");
        addGuiText("machines.tooltip.ignore", "Ignoriert");
        addGuiText("machines.tooltip.normal", "Normal");
        addGuiText("machines.tooltip.inverted", "Invertiert");
        addGuiText("machines.tooltip.warning", "Achtung!");
        addGuiText("machines.tooltip.needs_air", "Maschine benötigt Luft zum arbeiten!");
        addGuiText("machines.tooltip.make_space", "Schaffe Platz über ihr!");
        addGuiText("machines.tooltip.hold", "Halte ");
        addGuiText("machines.tooltip.for_details", " für weitere Details.");
        addGuiText("machines.tooltip.for_description", " für eine Blockbeschreibung.");
        addGuiText("machines.tooltip.capacity", "Kapazität: ");

        addGuiText("machines.concentrator.name", "Konzentration");
        addGuiText("machines.blast_furnace.name", "Verhüttung");
        addGuiText("machines.oxidizer.name", "Oxidation");
        addGuiText("machines.fluid_enricher.name", "Flüssigkeitsanreicherung");
        addGuiText("machines.decomposer.name", "Zersetzung");
        addGuiText("machines.crystallizer.name", "Kristallisierung");
        addGuiText("machines.electrolytic_salt_separator.name", "Elektrolyse");
        addGuiText("machines.fluid_centrifuge.name", "Zentrifugation");
        addGuiText("machines.fluid_evaporator.name", "Verdampfung");
        addGuiText("machines.generator.name", "Energiegeneration");
        addGuiText("machines.salt_melter.name", "Salzschmelze");
        addGuiText("machines.fluid_tank.name", "Flüssigkeitstank");
        addGuiText("machines.thorium_crafting_table.name", "Thorium Werkbank");

        addGuiText("turbine.top_info.turbine", "Turbine");
        addGuiText("turbine.top_info.active", "Aktiv");
        addGuiText("turbine.top_info.inactive", "Inaktiv");
        addGuiText("turbine.top_info.producing", "Generation: ");
        addGuiText("turbine.top_info.speed", "Geschwindigkeit: ");
        addGuiText("turbine.top_info.flowrate", "Flussrate: ");
        addGuiText("turbine.top_info.best_performing_at", "Beste Leistung bei 1900 - 2050 U/min");
        addGuiText("turbine.top_info.rpm", "U/min");
        addGuiText("turbine.text.valve_io", "Ventil I/O-Modus: ");

        addGuiText("reactor.tooltip.remove", "Entfernen");
        addGuiText("reactor.tooltip.copy_to_all", "Auf alle Turbinen kopieren.");
        addGuiText("reactor.tooltip.current", "Aktuelle");
        addGuiText("reactor.tooltip.all", "Alle");
        addGuiText("reactor.top_info.reactor", "Reaktor");
        addGuiText("reactor.top_info.active", "Aktiv");
        addGuiText("reactor.top_info.inactive", "Inaktiv");
        addGuiText("reactor.top_info.rod_insert", "Steuerstäbe: ");
        addGuiText("reactor.top_info.uranium_fuel", "Uranbrennstoff: ");
        addGuiText("reactor.top_info.reactor_load", "Reaktorlast: ");
        addGuiText("reactor.top_info.status", "Status: ");
        addGuiText("reactor.top_info.type", "Typ: ");
        addGuiText("reactor.text.valve_io", "Ventil I/O-Modus: ");
        addGuiText("reactor.text.enriched", "Angereichert");
        addGuiText("reactor.text.molten_salt", "Salz");
        addGuiText("reactor.text.turbine_generator", "TURBINEN GENERATOR");
        addGuiText("reactor.text.steam", "DAMPF");
        addGuiText("reactor.text.coil_engage", "SPULEN STATUS");
        addGuiText("reactor.text.activated", "AKTIVIERT");
        addGuiText("reactor.text.on", "EIN");
        addGuiText("reactor.text.off", "AUS");
        addGuiText("reactor.text.producing", "Generation: ");
        addGuiText("reactor.text.speed", "Geschwindigkeit: ");
        addGuiText("reactor.text.flowrate", "Flussrate: ");
        addGuiText("reactor.text.no_turbine_added", "Keine Turbine hinzugefügt!");
        addGuiText("reactor.text.use_configurator_to_link", "Verbinde mit einem Konfigurator.");
        addGuiText("reactor.text.start", "START");
        addGuiText("reactor.text.run", "BETRIEB");
        addGuiText("reactor.text.stop", "STOP");
        addGuiText("reactor.text.insert_rods", "FÜHRT STÄBE");
        addGuiText("reactor.text.into_core", "IN DEN KERN EIN");
        addGuiText("reactor.text.manual", "ALARM");
        addGuiText("reactor.text.scram", "R.E.S.A.");
        addGuiText("reactor.text.operation", "BETRIEB");
        addGuiText("reactor.text.emergency", "NOTFALL");
        addGuiText("reactor.text.fuel_status", "BRENNSTOFFSTATUS");
        addGuiText("reactor.text.turbine", "Turbine");
        addGuiText("reactor.text.thorium_reactor", "Thoriumreaktor");
        addGuiText("reactor.text.reactor_overview_interface", "REAKTOR ÜBERSICHT");
        addGuiText("reactor.text.operating_time", "BETRIEBSZEIT");
        addGuiText("reactor.text.main_power", "HAUPTSTROM");
        addGuiText("reactor.text.reactor_status", "REAKTORSTATUS");
        addGuiText("reactor.text.reactor_load", "REAKTORLAST");
        addGuiText("reactor.text.containment", "EINDÄMMUNG");
        addGuiText("reactor.text.radiation", "STRAHLUNG");
        addGuiText("reactor.text.temp", "TEMP");
        addGuiText("reactor.text.flow", "FLUSSR.");
        addGuiText("reactor.text.speed_cap", "GESCH.");
        addGuiText("reactor.text.generation", "GEN.");
        addGuiText("reactor.text.normal", "NORMAL");
        addGuiText("reactor.text.overload", "ÜBERLAST");
        addGuiText("reactor.text.critical", "KRITISCH");
        addGuiText("reactor.text.overview", "ÜBERSICHT");
        addGuiText("reactor.text.usvh", "uSV/h");
        addGuiText("reactor.text.mbs", "mB/s");
        addGuiText("reactor.text.rpm", "U/min");
        addGuiText("reactor.text.fet", "FE/t");
        addGuiText("reactor.text.uran", "Uran: ");
        addGuiText("reactor.text.fuel", "Tank: ");
        addGuiText("reactor.text.unset", "Nicht gesetzt");
        addGuiText("reactor.text.select", "Stab");
        addGuiText("reactor.text.rod", "wählen");
        addGuiText("reactor.text.valve_manipulation", "VENTILMANIPULATION");
        addGuiText("reactor.text.system_chart", "SYSTEMDIAGRAMM");
        addGuiText("reactor.text.rod_insert", "STÄBE");
        addGuiText("reactor.text.set", "OK");
        addGuiText("reactor.text.fuel_load", "STOFF");
        addGuiText("reactor.text.molten_salt_low_warning", "Warnung: Notabschaltung! Niedriger Salzpegel!");

        addGuiText("machines.top_info.energy", "Energie: ");
        addGuiText("machines.top_info.recipe", "Rezept: ");
        addGuiText("machines.top_info.producing", "Generation: ");
        addGuiText("machines.top_info.fuel", "Tank: ");

        addGuiText("machines.text.fuel", "Stoff: ");
        addGuiText("machines.text.tank", "Speicher: ");
        addGuiText("machines.text.gen", "Gener.: ");

        addGuiText("items.text.turbine_selected", "Gespeicherte Turbine: ");
        addGuiText("items.text.thermal_selected", "Gespeicherter Wärmetauscher: ");
        addGuiText("items.text.turbine_saved_to_configurator", "Turbine im Konfigurator gespeichert");
        addGuiText("items.text.turbine_saved_to_reactor", "Turbine im Reaktor gespeichert");
        addGuiText("items.text.thermal_saved_to_configurator", "Wärmetauscher im Konfigurator gespeichert");
        addGuiText("items.text.thermal_saved_to_reactor", "Wärmetauscher im Reaktor gespeichert");

        add("config.jade.plugin_thoriumreactors.machines", "Maschinen");

        add(ModBlocks.MACHINE_CASING.get(), "Maschinengehäuse");

        add(ModBlocks.FACTORY_BLOCK.get(), "Fabrikblock");
        add(ModBlocks.INVERTED_FACTORY_BLOCK.get(), "Umgekehrter Fabrikblock");
        add(ModBlocks.BLACK_FACTORY_BLOCK.get(), "Schwarzer Fabrikblock");
        add(ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get(), "Umgekehrter Schwarzer Fabrikblock");

        add(ModBlocks.GRATE_FLOOR_BLOCK.get(), "Gitterboden Block");
        add(ModBlocks.GRATE_WALL_BLOCK.get(), "Gitterwand Block");
        add(ModBlocks.INDUSTRAL_BLOCK.get(), "Industrieblock");
        add(ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get(), "Großer Industrieblock");
        add(ModBlocks.INDUSTRAL_BLOCK_PAVING.get(), "Industrieblock Pflaster");
        add(ModBlocks.INDUSTRAL_BLOCK_BRICK.get(), "Industrieblock Ziegel");
        add(ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get(), "Industrieblock Glatt");
        add(ModBlocks.INDUSTRAL_BLOCK_FLOOR.get(), "Industrieblock Boden");
        add(ModBlocks.FRAMELESS_INDUSTRAL_BLOCK_FLOOR.get(), "Rahmenloser Industrieblock Boden");

        add(ModBlocks.BLACK_INDUSTRAL_BLOCK.get(), "Schwarzer Industrieblock");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get(), "Großer Schwarzer Industrieblock");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get(), "Schwarzer Industrieblock Pflaster");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get(), "Schwarzer Industrieblock Ziegel");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get(), "Schwarzer Industrieblock Glatt");
        add(ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get(), "Schwarzer Industrieblock Boden");
        add(ModBlocks.FRAMELESS_BLACK_INDUSTRAL_BLOCK_FLOOR.get(), "Rahmenloser Industrieblock Boden");

        add(ModBlocks.WHITE_INDUSTRAL_BLOCK.get(), "Weißer Industrieblock");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_BIG_TILE.get(), "Großer Weißer Industrieblock");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_PAVING.get(), "Weißer Industrieblock Pflaster");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_BRICK.get(), "Weißer Industrieblock Ziegel");
        add(ModBlocks.WHITE_INDUSTRAL_BLOCK_SMOOTH.get(), "Weißer Industrieblock Glatt");

        add(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get(), "Gefahrblock schwarz/gelb links gestreift");
        add(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_RIGHT.get(), "Gefahrblock schwarz/gelb rechts gestreift");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_LEFT.get(), "Gefahrblock weiß/orange links gestreift");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_RIGHT.get(), "Gefahrblock weiß/orange rechts gestreift");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_LEFT.get(), "Gefahrblock weiß/schwarz links gestreift");
        add(ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_RIGHT.get(), "Gefahrblock weiß/schwarz rechts gestreift");

        add("item_input", "Item Eingang");
        add("item_output", "Item Ausgang");
        add("coolant_input", "Kühlmittel-Eingang");
        add("coolant_output", "Kühlmittel-Ausgang");
        add("fluid_input", "Flüssigkeits-Eingang");
        add("fluid_output", "Flüssigkeits-Ausgang");
        add("heating_fluid_input", "Wärme-Eingang");
        add("heating_fluid_output", "Wärme-Ausgang");

        add("fluid_type.thoriumreactors.hydrofluorite", "Hydrofluorit");
        add("fluid_type.thoriumreactors.molten_salt", "Schmelzsalz");
        add("fluid_type.thoriumreactors.depleted_molten_salt", "Aufgebrauchted Schmelzsalz");
        add("fluid_type.thoriumreactors.heated_molten_salt", "Erhitztes Schmelzsalz");
        add("fluid_type.thoriumreactors.uranium_hexafluorite", "Uran-Hexafluorit");
        add("fluid_type.thoriumreactors.enriched_uranium_hexafluorite", "Angereichertes Uran-Hexafluorit");
        add("fluid_type.thoriumreactors.steam", "Dampf");

        add("block.thoriumreactors.hydrofluorite_block", "Hydrofluorit");
        add("block.thoriumreactors.molten_salt_block", "Schmelzsalz");
        add("block.thoriumreactors.depleted_molten_salt_block", "Aufgebrauchted Schmelzsalz");
        add("block.thoriumreactors.heated_molten_salt_block", "Erhitztes Schmelzsalz");
        add("block.thoriumreactors.uranium_hexafluorite_block", "Uran-Hexafluorit");
        add("block.thoriumreactors.enriched_uranium_hexafluorite_block", "Angereichertes Uran-Hexafluorit");
        add("block.thoriumreactors.steam_block", "Dampf");

        add("keybind.thoriumreactors.description", "Beschreibung anzeigen");
        add("keybind.thoriumreactors.details", "Details anzeigen");
        add("itemGroup.thoriumreactors.resources", "Thorium R. - Rohstoffe");
        add("itemGroup.thoriumreactors.machinery", "Thorium R. - Maschinen");
        add("itemGroup.thoriumreactors.building", "Thorium R. - Baublöcke");
        add("death.attack.thoriumreactors.radioactive_overdosis", "%1$s starb an einer radioaktiven Überdosis.");
        add("death.attack.thoriumreactors.grind", "%1$s wurde von einer Turbine zermahlen.");

    }

    private void addGuiText(String name, String text) {
        add("text.thoriumreactors.inventory." + name, text);
    }

}
