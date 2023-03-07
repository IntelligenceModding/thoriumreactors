package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModItems {

    public static final RegistryObject<Item> GRAPHITE = Registration.ITEMS.register("graphite", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> GRAPHITE_INGOT = Registration.ITEMS.register("graphite_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> GRAPHITE_TUBE = Registration.ITEMS.register("graphite_tube", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> SODIUM = Registration.ITEMS.register("sodium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> POTASSIUM = Registration.ITEMS.register("potassium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> URAN_THREE_CHLORIDE = Registration.ITEMS.register("uran_three_chloride", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> REDSTONE_PROCESSOR = Registration.ITEMS.register("redstone_processor", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> BLASTED_IRON_INGOT = Registration.ITEMS.register("blasted_iron_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> STEEL_COMPOUND = Registration.ITEMS.register("steel_compound", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> STEEL_INGOT = Registration.ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static final RegistryObject<Item> THORIUM = Registration.ITEMS.register("thorium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> RAW_URANIUM = Registration.ITEMS.register("raw_uranium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> ENRICHED_URANIUM = Registration.ITEMS.register("enriched_uranium", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> YELLOW_CAKE = Registration.ITEMS.register("yellow_cake", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));
    public static final RegistryObject<Item> FLUORIDE = Registration.ITEMS.register("fluoride", () -> new Item(new Item.Properties().tab(ThoriumReactors.creativeTab)));

    public static void register() {
    }

}
