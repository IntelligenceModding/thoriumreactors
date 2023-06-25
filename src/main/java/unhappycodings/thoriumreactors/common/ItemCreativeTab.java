package unhappycodings.thoriumreactors.common;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;

import java.util.ArrayList;
import java.util.Collections;

public class ItemCreativeTab extends CreativeModeTab {

    public ItemCreativeTab() {
        super(ThoriumReactors.MOD_ID + ".items");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModBlocks.THORIUM_CRAFTING_TABLE.get());
    }

    @Override
    public void fillItemList(@NotNull NonNullList<ItemStack> items) {
        int index = 0;
        ArrayList<Block> blockList = new ArrayList<>();
        Collections.addAll(blockList,
                ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_CHROMITE_ORE.get(),
                ModBlocks.CHROMITE_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModBlocks.GRAPHITE_ORE.get(),
                ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(),ModBlocks.MOLYBDENUM_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get(),
                ModBlocks.FLUORITE_ORE.get(), ModBlocks.DEEPSLATE_NICKEL_ORE.get(),ModBlocks.NICKEL_ORE.get(),
                ModBlocks.DEEPSLATE_URANIUM_ORE.get(),ModBlocks.URANIUM_ORE.get(), ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(),
                ModBlocks.PYROCHLOR_ORE.get(), ModBlocks.DEEPSLATE_MANGANESE_ORE.get(),ModBlocks.MANGANESE_ORE.get(),
                ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModBlocks.BAUXITE_ORE.get(), ModBlocks.BLASTED_STONE.get(),
                ModBlocks.BLASTED_IRON_CHEST_BLOCK.get(), ModBlocks.STEEL_CHEST_BLOCK.get(), ModBlocks.THORIUM_CHEST_BLOCK.get(),
                ModBlocks.GENERATOR_BLOCK.get(), ModBlocks.FLUID_EVAPORATION_BLOCK.get(), ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(),
                ModBlocks.SALT_MELTER_BLOCK.get(), ModBlocks.CONCENTRATOR_BLOCK.get(), ModBlocks.DECOMPOSER_BLOCK.get(),
                ModBlocks.URANIUM_OXIDIZER_BLOCK.get(), ModBlocks.FLUID_CENTRIFUGE_BLOCK.get(), ModBlocks.CRYSTALLIZER_BLOCK.get(),
                ModBlocks.BLAST_FURNACE_BLOCK.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(),
                ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.REACTOR_CASING.get(),
                ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), ModBlocks.REACTOR_VALVE.get(), ModBlocks.REACTOR_ROD_CONTROLLER.get(),
                ModBlocks.REACTOR_CORE.get(), ModBlocks.REACTOR_GLASS.get(), ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(),
                ModBlocks.SIMPLE_FLUID_TANK.get(), ModBlocks.GENERIC_FLUID_TANK.get(), ModBlocks.PROGRESSIVE_FLUID_TANK.get());

        ArrayList<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, ModItems.CONFIGURATOR.get(), ModItems.CHROMIUM_INGOT.get(), ModItems.GRAPHITE_INGOT.get(), ModItems.STEEL_INGOT.get(), ModItems.BLASTED_IRON_INGOT.get(),
                ModItems.TITANIUM_INGOT.get(), ModItems.MOLYBDENUM_INGOT.get(), ModItems.COBALT_INGOT.get(), ModItems.FLUORITE_INGOT.get(),
                ModItems.NICKEL_INGOT.get(), ModItems.URANIUM_INGOT.get(), ModItems.ALUMINUM_INGOT.get(), ModItems.MANGANESE_INGOT.get(),
                ModItems.NIOB_INGOT.get(), ModItems.CHROMIUM_NUGGET.get(), ModItems.GRAPHITE_NUGGET.get(), ModItems.STEEL_NUGGET.get(),
                ModItems.BLASTED_IRON_NUGGET.get(), ModItems.TITANIUM_NUGGET.get(), ModItems.MOLYBDENUM_NUGGET.get(), ModItems.COBALT_NUGGET.get(),
                ModItems.FLUORITE_NUGGET.get(), ModItems.NICKEL_NUGGET.get(), ModItems.URANIUM_NUGGET.get(), ModItems.ALUMINUM_NUGGET.get(),
                ModItems.MANGANESE_NUGGET.get(), ModItems.NIOB_NUGGET.get(), ModItems.SODIUM.get(),
                ModItems.POTASSIUM.get(), ModItems.URAN_THREE_CHLORIDE.get(), ModItems.YELLOW_CAKE.get(), ModItems.THORIUM.get(),
                ModItems.RAW_URANIUM.get(), ModItems.ENRICHED_URANIUM.get(), ModItems.DEPLETED_URANIUM.get(), ModItems.FLUORITE.get(), ModItems.GRAPHITE_CRYSTAL.get(),
                ModItems.GRAPHITE_TUBE.get(), ModItems.REDSTONE_PROCESSOR.get());

        for (Item i : itemList) {
            items.add(index, new ItemStack(i));
            index++;
        }

        for (Block i : blockList) {
            items.add(index, new ItemStack(i));
            index++;
        }
    }

}
