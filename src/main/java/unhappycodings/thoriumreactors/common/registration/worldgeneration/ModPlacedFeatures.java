package unhappycodings.thoriumreactors.common.registration.worldgeneration;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.Registration;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ThoriumReactors.MOD_ID);

    public static final RegistryObject<PlacedFeature> CHROMITE_ORE = PLACED_FEATURES.register("chromite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.CHROMITE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(9, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> MANGANESE_ORE = PLACED_FEATURES.register("manganese_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.MANGANESE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> MOLYBDENUM_ORE = PLACED_FEATURES.register("molybdenum_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.MOLYBDENUM_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(5, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> NICKEL_ORE = PLACED_FEATURES.register("nickel_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NICKEL_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(12, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> TITANIC_IRON_ORE = PLACED_FEATURES.register("titanic_iron_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.TITANIC_IRON_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(5, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> BAUXITE_ORE = PLACED_FEATURES.register("bauxite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.BAUXITE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(6, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> PYROCHLORE_ORE = PLACED_FEATURES.register("pyrochlore_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.PYROCHLORE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(5, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> FLUORITE_ORE = PLACED_FEATURES.register("fluorite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.FLUORITE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> GRAPHITE_ORE = PLACED_FEATURES.register("graphite_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.GRAPHITE_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static final RegistryObject<PlacedFeature> URANIUM_ORE = PLACED_FEATURES.register("uranium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.URANIUM_ORE.getHolder().get(),
                    OrePlacements.commonOrePlacement(8, HeightRangePlacement.uniform(
                            VerticalAnchor.bottom(),
                            VerticalAnchor.top()
                    ))));

    public static void register(IEventBus bus) {
        PLACED_FEATURES.register(bus);
    }

}
