package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModItems {

    public static final RegistryObject<Item> GRAPHITE_CRYSTAL = Registration.ITEMS.register("graphite_crystal", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> GRAPHITE_CLUSTER = Registration.ITEMS.register("graphite_cluster", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> GRAPHITE_INGOT = Registration.ITEMS.register("graphite_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> GRAPHITE_TUBE = Registration.ITEMS.register("graphite_tube", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> GRAPHITE_NUGGET = Registration.ITEMS.register("graphite_nugget", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> BLASTED_IRON_NUGGET = Registration.ITEMS.register("blasted_iron_nugget", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> STEEL_NUGGET = Registration.ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> SODIUM = Registration.ITEMS.register("sodium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> POTASSIUM = Registration.ITEMS.register("potassium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> URAN_THREE_CHLORIDE = Registration.ITEMS.register("uran_three_chloride", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> CONFIGURATOR = Registration.ITEMS.register("configurator", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> REDSTONE_PROCESSOR = Registration.ITEMS.register("redstone_processor", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> BLASTED_IRON_INGOT = Registration.ITEMS.register("blasted_iron_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> STEEL_INGOT = Registration.ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> THORIUM = Registration.ITEMS.register("thorium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> RAW_URANIUM = Registration.ITEMS.register("raw_uranium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> ENRICHED_URANIUM = Registration.ITEMS.register("enriched_uranium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> YELLOW_CAKE = Registration.ITEMS.register("yellow_cake", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> FLUORITE = Registration.ITEMS.register("fluorite", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<BucketItem> MOLTEN_SALT_BUCKET = Registration.ITEMS.register("molten_salt_bucket", () -> new BucketItem(ModFluids.SOURCE_MOLTEN_SALT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<BucketItem> HEATED_MOLTEN_SALT_BUCKET = Registration.ITEMS.register("heated_molten_salt_bucket", () -> new BucketItem(ModFluids.SOURCE_HEATED_MOLTEN_SALT, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<BucketItem> HYDROFLUORITE_BUCKET = Registration.ITEMS.register("hydrofluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_HYDROFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<BucketItem> URANIUM_HEXAFLUORITE_BUCKET = Registration.ITEMS.register("uranium_hexafluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_URANIUM_HEXAFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<BucketItem> ENRICHED_URANIUM_HEXAFLUORITE_BUCKET = Registration.ITEMS.register("enriched_uranium_hexafluorite_bucket", () -> new BucketItem(ModFluids.SOURCE_ENRICHED_URANIUM_HEXAFLUORITE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> SINGE_VESSEL = Registration.ITEMS.register("single_vessel", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> DOUBLE_VESSEL = Registration.ITEMS.register("double_vessel", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> TRIPLE_VESSEL = Registration.ITEMS.register("triple_vessel", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> QUADRUPLE_VESSEL = Registration.ITEMS.register("quadruple_vessel", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> SMALL_ACCUMULATOR = Registration.ITEMS.register("small_accumulator", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> ACCUMULATOR = Registration.ITEMS.register("accumulator", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> LARGE_ACCUMULATOR = Registration.ITEMS.register("large_accumulator", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    
    public static void register() {
    }

}
