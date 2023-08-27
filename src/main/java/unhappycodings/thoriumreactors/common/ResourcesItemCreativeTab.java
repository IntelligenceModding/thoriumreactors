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

public class ResourcesItemCreativeTab extends CreativeModeTab {

    public ResourcesItemCreativeTab() {
        super(ThoriumReactors.MOD_ID + ".resources");
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModItems.THORIUM.get());
    }

    @Override
    public void fillItemList(@NotNull NonNullList<ItemStack> items) {
        int index = 0;
        ArrayList<Item> itemList = new ArrayList<>();
        Collections.addAll(itemList, ModItems.CHROMIUM_INGOT.get(), ModItems.GRAPHITE_INGOT.get(), ModItems.STEEL_INGOT.get(), ModItems.BLASTED_IRON_INGOT.get(),
                ModItems.TITANIUM_INGOT.get(), ModItems.MOLYBDENUM_INGOT.get(), ModItems.COBALT_INGOT.get(), ModItems.FLUORITE_INGOT.get(),
                ModItems.NICKEL_INGOT.get(), ModItems.URANIUM_INGOT.get(), ModItems.ALUMINUM_INGOT.get(), ModItems.MANGANESE_INGOT.get(),
                ModItems.NIOB_INGOT.get(), ModItems.CHROMIUM_NUGGET.get(), ModItems.GRAPHITE_NUGGET.get(), ModItems.STEEL_NUGGET.get(), ModItems.BLASTED_IRON_NUGGET.get(), ModItems.TITANIUM_NUGGET.get(),
                ModItems.MOLYBDENUM_NUGGET.get(), ModItems.COBALT_NUGGET.get(), ModItems.FLUORITE_NUGGET.get(), ModItems.NICKEL_NUGGET.get(),
                ModItems.URANIUM_NUGGET.get(), ModItems.ALUMINUM_NUGGET.get(), ModItems.MANGANESE_NUGGET.get(), ModItems.NIOB_NUGGET.get(), ModItems.SODIUM.get(), ModItems.POTASSIUM.get(),
                ModItems.URAN_THREE_CHLORIDE.get(), ModItems.YELLOW_CAKE.get(), ModItems.THORIUM.get(), ModItems.RAW_URANIUM.get(),
                ModItems.ENRICHED_URANIUM.get(), ModItems.DEPLETED_URANIUM.get(), ModItems.FLUORITE.get(), ModItems.GRAPHITE_CRYSTAL.get(),
                ModItems.MOLTEN_SALT_BUCKET.get(), ModItems.HEATED_MOLTEN_SALT_BUCKET.get(), ModItems.URANIUM_HEXAFLUORITE_BUCKET.get(),
                ModItems.ENRICHED_URANIUM_HEXAFLUORITE_BUCKET.get(), ModItems.HYDROFLUORITE_BUCKET.get(), ModItems.STEAM_BUCKET.get());

        ArrayList<Block> blockList = new ArrayList<>();
        Collections.addAll(blockList,
                ModBlocks.CHROMIUM_BLOCK.get(), ModBlocks.GRAPHITE_BLOCK.get(), ModBlocks.STEEL_BLOCK.get(),
                ModBlocks.BLASTED_IRON_BLOCK.get(), ModBlocks.TITANIUM_BLOCK.get(), ModBlocks.MOLYBDENUM_BLOCK.get(), ModBlocks.COBALT_BLOCK.get(),
                ModBlocks.FLUORITE_BLOCK.get(), ModBlocks.NICKEL_BLOCK.get(), ModBlocks.URANIUM_BLOCK.get(), ModBlocks.ALUMINUM_BLOCK.get(),
                ModBlocks.NIOB_BLOCK.get(), ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_CHROMITE_ORE.get(),
                ModBlocks.CHROMITE_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModBlocks.GRAPHITE_ORE.get(),
                ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(), ModBlocks.MOLYBDENUM_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get(),
                ModBlocks.FLUORITE_ORE.get(), ModBlocks.DEEPSLATE_NICKEL_ORE.get(), ModBlocks.NICKEL_ORE.get(),
                ModBlocks.DEEPSLATE_URANIUM_ORE.get(), ModBlocks.URANIUM_ORE.get(), ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(),
                ModBlocks.PYROCHLOR_ORE.get(), ModBlocks.DEEPSLATE_MANGANESE_ORE.get(), ModBlocks.MANGANESE_ORE.get(),
                ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModBlocks.BAUXITE_ORE.get(), ModBlocks.BLASTED_STONE.get());

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
