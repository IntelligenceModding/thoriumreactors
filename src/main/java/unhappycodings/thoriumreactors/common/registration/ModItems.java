package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.item.ConfiguratorItem;
import unhappycodings.thoriumreactors.common.item.DosiMeterItem;
import unhappycodings.thoriumreactors.common.item.GeigerCounterItem;
import unhappycodings.thoriumreactors.common.item.HazmatSuitArmorItem;

public class ModItems {

    public static final RegistryObject<Item> GRAPHITE_INGOT = Registration.ITEMS.register("graphite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAPHITE_NUGGET = Registration.ITEMS.register("graphite_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAPHITE_CRYSTAL = Registration.ITEMS.register("graphite_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAPHITE_TUBE = Registration.ITEMS.register("graphite_tube", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLASTED_IRON_INGOT = Registration.ITEMS.register("blasted_iron_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLASTED_IRON_NUGGET = Registration.ITEMS.register("blasted_iron_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = Registration.ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = Registration.ITEMS.register("steel_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MOLYBDENUM_INGOT = Registration.ITEMS.register("molybdenum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOLYBDENUM_NUGGET = Registration.ITEMS.register("molybdenum_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MANGANESE_INGOT = Registration.ITEMS.register("manganese_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MANGANESE_NUGGET = Registration.ITEMS.register("manganese_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NICKEL_INGOT = Registration.ITEMS.register("nickel_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NICKEL_NUGGET = Registration.ITEMS.register("nickel_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ALUMINUM_INGOT = Registration.ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_NUGGET = Registration.ITEMS.register("aluminum_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHROMIUM_INGOT = Registration.ITEMS.register("chromium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHROMIUM_NUGGET = Registration.ITEMS.register("chromium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NIOB_INGOT = Registration.ITEMS.register("niob_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NIOB_NUGGET = Registration.ITEMS.register("niob_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_INGOT = Registration.ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_NUGGET = Registration.ITEMS.register("titanium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> URANIUM_INGOT = Registration.ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_NUGGET = Registration.ITEMS.register("uranium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLUORITE_INGOT = Registration.ITEMS.register("fluorite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLUORITE_NUGGET = Registration.ITEMS.register("fluorite_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COBALT_INGOT = Registration.ITEMS.register("cobalt_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COBALT_NUGGET = Registration.ITEMS.register("cobalt_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SODIUM = Registration.ITEMS.register("sodium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POTASSIUM = Registration.ITEMS.register("potassium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URAN_THREE_CHLORIDE = Registration.ITEMS.register("uran_three_chloride", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_PROCESSOR = Registration.ITEMS.register("redstone_processor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENRICHED_URANIUM = Registration.ITEMS.register("enriched_uranium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEPLETED_URANIUM = Registration.ITEMS.register("depleted_uranium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_CAKE = Registration.ITEMS.register("yellow_cake", () -> new Item(new Item.Properties()));

    public static final RegistryObject<ConfiguratorItem> CONFIGURATOR = Registration.ITEMS.register("configurator", () -> new ConfiguratorItem(new Item.Properties()));
    public static final RegistryObject<Item> GAS_MASK = Registration.ITEMS.register("gas_mask", () -> new Item(new Item.Properties()));
    public static final RegistryObject<GeigerCounterItem> GEIGER_COUNTER = Registration.ITEMS.register("geiger_counter", () -> new GeigerCounterItem(new Item.Properties()));
    public static final RegistryObject<DosiMeterItem> DOSI_METER = Registration.ITEMS.register("dosi_meter", () -> new DosiMeterItem(new Item.Properties()));
    public static final RegistryObject<Item> COPY_CARD = Registration.ITEMS.register("copy_card", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WIRELESS_REACTOR_INTERFACE = Registration.ITEMS.register("wireless_reactor_interface", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ACCELERATION_UPGRADE = Registration.ITEMS.register("acceleration_upgrade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PROCESSING_UPGRADE = Registration.ITEMS.register("processing_upgrade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENERGY_UPGRADE = Registration.ITEMS.register("energy_upgrade", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MULTITOOL = Registration.ITEMS.register("multitool", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_PICKAXE = Registration.ITEMS.register("thorium_pickaxe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_SHOVEL = Registration.ITEMS.register("thorium_shovel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_AXE = Registration.ITEMS.register("thorium_axe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_HOE = Registration.ITEMS.register("thorium_hoe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM_SWORD = Registration.ITEMS.register("thorium_sword", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLUORITE = Registration.ITEMS.register("fluorite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THORIUM = Registration.ITEMS.register("thorium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_URANIUM = Registration.ITEMS.register("raw_uranium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_TITANIC_IRON = Registration.ITEMS.register("raw_titanic_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PYROCHLORE = Registration.ITEMS.register("raw_pyrochlore", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_MOLYBDENUM = Registration.ITEMS.register("raw_molybdenum", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BAUXITE = Registration.ITEMS.register("raw_bauxite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_NICKEL = Registration.ITEMS.register("raw_nickel", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_MANGANESE = Registration.ITEMS.register("raw_manganese", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_CHROMITE = Registration.ITEMS.register("raw_chromite", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TURBINE_BLADE = Registration.ITEMS.register("turbine_blade", () -> new Item(new Item.Properties()));

    public static final RegistryObject<BucketItem> MOLTEN_SALT_BUCKET = Registration.ITEMS.register("molten_salt_bucket", () -> new BucketItem(ModFluids.SOURCE_MOLTEN_SALT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> DEPLETED_MOLTEN_SALT_BUCKET = Registration.ITEMS.register("depleted_molten_salt_bucket", () -> new BucketItem(ModFluids.SOURCE_DEPLETED_MOLTEN_SALT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> HEATED_MOLTEN_SALT_BUCKET = Registration.ITEMS.register("heated_molten_salt_bucket", () -> new BucketItem(ModFluids.SOURCE_HEATED_MOLTEN_SALT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> HYDROFLUORITE_BUCKET = Registration.ITEMS.register("hydrofluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_HYDROFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> URANIUM_HEXAFLUORITE_BUCKET = Registration.ITEMS.register("uranium_hexafluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_URANIUM_HEXAFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> ENRICHED_URANIUM_HEXAFLUORITE_BUCKET = Registration.ITEMS.register("enriched_uranium_hexafluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_ENRICHED_URANIUM_HEXAFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<BucketItem> STEAM_BUCKET = Registration.ITEMS.register("steam_bucket", () -> new BucketItem(ModFluids.SOURCE_STEAM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<Item> MODULE_EMPTY = Registration.ITEMS.register("module_empty", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_IO = Registration.ITEMS.register("module_io", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_ENERGY = Registration.ITEMS.register("module_energy", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_STORAGE = Registration.ITEMS.register("module_storage", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_TANK = Registration.ITEMS.register("module_tank", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_SENSOR = Registration.ITEMS.register("module_sensor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODULE_PROCESSING = Registration.ITEMS.register("module_processing", () -> new Item(new Item.Properties()));

    public static final RegistryObject<HazmatSuitArmorItem> HAZMAT_SUIT_HELMET = Registration.ITEMS.register("hazmat_suit_helmet", () -> new HazmatSuitArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).durability(-1).fireResistant()));
    public static final RegistryObject<HazmatSuitArmorItem> HAZMAT_SUIT_CHESTPLATE= Registration.ITEMS.register("hazmat_suit_chestplate", () -> new HazmatSuitArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).durability(-1).fireResistant()));
    public static final RegistryObject<HazmatSuitArmorItem> HAZMAT_SUIT_LEGGINGS = Registration.ITEMS.register("hazmat_suit_leggings", () -> new HazmatSuitArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).durability(-1).fireResistant()));
    public static final RegistryObject<HazmatSuitArmorItem> HAZMAT_SUIT_BOOTS = Registration.ITEMS.register("hazmat_suit_boots", () -> new HazmatSuitArmorItem(ModArmorMaterials.HAZMAT, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).durability(-1).fireResistant()));

    public static void register() {
    }

}
