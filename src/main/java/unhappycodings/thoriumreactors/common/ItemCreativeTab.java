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
        Collections.addAll(blockList, ModBlocks.THORIUM_CRAFTING_TABLE.get(), ModBlocks.THERMAL_CONDUCTOR.get(), ModBlocks.THERMAL_CONTROLLER.get(),
                ModBlocks.THERMAL_VALVE.get(), ModBlocks.THERMAL_HEAT_SINK.get() , ModBlocks.REACTOR_CASING.get(), ModBlocks.REACTOR_CONTROLLER_BLOCK.get(),
                ModBlocks.REACTOR_VALVE.get(), ModBlocks.REACTOR_GLASS.get() ,ModBlocks.HARDENED_STONE.get());

        ArrayList<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, ModItems.GRAPHITE_INGOT.get(), ModItems.BLASTED_IRON_INGOT.get(), ModItems.STEEL_INGOT.get(),
                ModItems.GRAPHITE.get(), ModItems.GRAPHITE_TUBE.get(), ModItems.RAW_URANIUM.get(), ModItems.ENRICHED_URANIUM.get());

        for (Block i : blockList) {
            items.add(index, new ItemStack(i));
            index++;
        }
        for (Item i : itemList) {
            items.add(index, new ItemStack(i));
            index++;
        }
    }

}
