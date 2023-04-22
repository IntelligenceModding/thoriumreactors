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
        Collections.addAll(blockList, ModBlocks.GENERATOR_BLOCK.get(), ModBlocks.FLUID_EVAPORATION_BLOCK.get(), ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), ModBlocks.SALT_MELTER_BLOCK.get(), ModBlocks.CONCENTRATOR_BLOCK.get(), ModBlocks.DECOMPOSER_BLOCK.get(), ModBlocks.REACTOR_CASING.get(), ModBlocks.REACTOR_CONTROLLER_BLOCK.get(), ModBlocks.REACTOR_VALVE.get(), ModBlocks.REACTOR_ROD_CONTROLLER.get(), ModBlocks.REACTOR_CORE.get(), ModBlocks.REACTOR_GLASS.get(), ModBlocks.THORIUM_CRAFTING_TABLE.get(), ModBlocks.REACTOR_GRAPHITE_MODERATOR.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(), ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_HEAT_SINK.get(), ModBlocks.BLASTED_STONE.get(), ModBlocks.GRAPHITE_ORE.get(), ModBlocks.GRAPHITE_BLOCK.get(), ModBlocks.THORIUM_BLOCK.get());

        ArrayList<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, ModItems.GRAPHITE_TUBE.get(), ModItems.GRAPHITE_CRYSTAL.get(), ModItems.GRAPHITE_CLUSTER.get(), ModItems.GRAPHITE_INGOT.get(),
                ModItems.BLASTED_IRON_INGOT.get(), ModItems.STEEL_INGOT.get(), ModItems.SODIUM.get(), ModItems.POTASSIUM.get(),
                ModItems.URAN_THREE_CHLORIDE.get(), ModItems.YELLOW_CAKE.get(), ModItems.THORIUM.get(), ModItems.RAW_URANIUM.get(),
                ModItems.ENRICHED_URANIUM.get(), ModItems.REDSTONE_PROCESSOR.get());

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
