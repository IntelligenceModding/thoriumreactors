package unhappycodings.thoriumreactors.common.registration.worldgeneration;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, ThoriumReactors.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> CHROMITE_ORE = CONFIGURED_FEATURES.register("chromite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CHROMITE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_CHROMITE_ORE.get().defaultBlockState())), 8)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MANGANESE_ORE = CONFIGURED_FEATURES.register("manganese_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MANGANESE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_MANGANESE_ORE.get().defaultBlockState())), 8)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> MOLYBDENUM_ORE = CONFIGURED_FEATURES.register("molybdenum_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.MOLYBDENUM_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> NICKEL_ORE = CONFIGURED_FEATURES.register("nickel_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.NICKEL_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_NICKEL_ORE.get().defaultBlockState())), 6)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> TITANIC_IRON_ORE = CONFIGURED_FEATURES.register("titanic_iron_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TITANIC_IRON_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> BAUXITE_ORE = CONFIGURED_FEATURES.register("bauxite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.BAUXITE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_BAUXITE_ORE.get().defaultBlockState())), 4)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PYROCHLORE_ORE = CONFIGURED_FEATURES.register("pyrochlore_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.PYROCHLOR_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get().defaultBlockState())), 3)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FLUORITE_ORE = CONFIGURED_FEATURES.register("fluorite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.FLUORITE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_FLUORITE_ORE.get().defaultBlockState())), 12)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GRAPHITE_ORE = CONFIGURED_FEATURES.register("graphite_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.GRAPHITE_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_GRAPHITE_ORE.get().defaultBlockState())), 14)));

    public static final RegistryObject<ConfiguredFeature<?, ?>> URANIUM_ORE = CONFIGURED_FEATURES.register("uranium_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.URANIUM_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_URANIUM_ORE.get().defaultBlockState())), 3)));

    public static void register(IEventBus bus) {
        CONFIGURED_FEATURES.register(bus);
    }

}
