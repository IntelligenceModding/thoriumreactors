package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ThoriumReactors.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.GRAPHITE_CRYSTAL.get());
        simpleItem(ModItems.GRAPHITE_CLUSTER.get());
        simpleItem(ModItems.GRAPHITE_INGOT.get());
        simpleItem(ModItems.GRAPHITE_TUBE.get());
        simpleItem(ModItems.GRAPHITE_NUGGET.get());
        simpleItem(ModItems.BLASTED_IRON_NUGGET.get());
        simpleItem(ModItems.STEEL_NUGGET.get());
        simpleItem(ModItems.SODIUM.get());
        simpleItem(ModItems.POTASSIUM.get());
        simpleItem(ModItems.URAN_THREE_CHLORIDE.get());
        simpleItem(ModItems.BLASTED_IRON_INGOT.get());
        simpleItem(ModItems.STEEL_INGOT.get());
        simpleItem(ModItems.RAW_URANIUM.get());
        simpleItem(ModItems.ENRICHED_URANIUM.get());
        simpleItem(ModItems.REDSTONE_PROCESSOR.get());
        simpleItem(ModItems.THORIUM.get());
        simpleItem(ModItems.YELLOW_CAKE.get());
        simpleItem(ModItems.FLUORITE.get());

        simpleItem(ModItems.MOLTEN_SALT_BUCKET.get());
        simpleItem(ModItems.HEATED_MOLTEN_SALT_BUCKET.get());
        simpleItem(ModItems.HYDROFLUORITE_BUCKET.get());
        simpleItem(ModItems.URANIUM_HEXAFLUORITE_BUCKET.get());
        simpleItem(ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get());

        simpleItem(ModItems.SINGE_VESSEL.get());
        simpleItem(ModItems.DOUBLE_VESSEL.get());
        simpleItem(ModItems.TRIPLE_VESSEL.get());
        simpleItem(ModItems.QUADRUPLE_VESSEL.get());

        simpleItem(ModItems.SMALL_ACCUMULATOR.get());
        simpleItem(ModItems.ACCUMULATOR.get());
        simpleItem(ModItems.LARGE_ACCUMULATOR.get());

        withExistingParent(ItemUtil.getRegString(ModBlocks.THORIUM_CRAFTING_TABLE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.BLASTED_STONE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/blasted_stone"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.URANIUM_ORE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/uranium_ore"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.GRAPHITE_ORE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/graphite_ore"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.FLUORITE_ORE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluorite_ore"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.BLASTED_IRON_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/blasted_iron_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.STEEL_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/steel_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.GRAPHITE_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/graphite_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.THORIUM_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.FLUORITE_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluoride_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.URANIUM_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/uranium_block"));

        withExistingParent(ItemUtil.getRegString(ModBlocks.THERMAL_CONDUCTOR.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_conductor"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.THERMAL_CONTROLLER.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_controller"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.THERMAL_VALVE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_valve"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.THERMAL_HEAT_SINK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/thermal_heat_sink"));

        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_GLASS.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_glass"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_CASING.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_casing"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_VALVE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_valve"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_CORE.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_core_off"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_CONTROLLER_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_controller"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_ROD_CONTROLLER.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_rod_controller"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.REACTOR_GRAPHITE_MODERATOR.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/reactor_graphite_moderator"));

        withExistingParent(ItemUtil.getRegString(ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/electrolytic_salt_separator"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.FLUID_EVAPORATION_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_evaporation_block_off"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.GENERATOR_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/generator_block_off"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.SALT_MELTER_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/salt_melter_block_off"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.CONCENTRATOR_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/concentrator_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.DECOMPOSER_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/decomposer_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.URANIUM_OXIDIZER_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/uranium_oxidizer_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.FLUID_CENTRIFUGE_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/fluid_centrifuge_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.CRYSTALLIZER_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/crystallizer_block"));
        withExistingParent(ItemUtil.getRegString(ModBlocks.BLAST_FURNACE_BLOCK.get()), new ResourceLocation(ThoriumReactors.MOD_ID, "block/blast_furnace_block_off"));
    }

    private void simpleItem(Item item) {
        withExistingParent(ItemUtil.getRegString(item), "item/generated").texture("layer0", new ResourceLocation(this.modid, "item/" + ItemUtil.getRegName(item).getPath()));
    }

    private void simpleItem(Item item, ResourceLocation texture) {
        withExistingParent(ItemUtil.getRegString(item), "item/generated").texture("layer0", new ResourceLocation(this.modid, "item/" + texture));
    }


}
