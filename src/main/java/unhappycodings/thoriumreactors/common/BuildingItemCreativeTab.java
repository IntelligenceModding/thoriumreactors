package unhappycodings.thoriumreactors.common;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;

import java.util.ArrayList;
import java.util.Collections;

public class BuildingItemCreativeTab extends CreativeModeTab {

    public BuildingItemCreativeTab() {
        super(ThoriumReactors.MOD_ID + ".building");
    }

    @Override
    public void fillItemList(@NotNull NonNullList<ItemStack> items) {
        int index = 0;

        ArrayList<Block> blockList = new ArrayList<>();
        Collections.addAll(blockList, ModBlocks.INDUSTRAL_BLOCK.get(), ModBlocks.INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.INDUSTRAL_BLOCK_SMOOTH.get(),
                ModBlocks.BLACK_INDUSTRAL_BLOCK.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_SMOOTH.get(),
                ModBlocks.WHITE_INDUSTRAL_BLOCK.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_BIG_TILE.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_BRICK.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_PAVING.get(), ModBlocks.WHITE_INDUSTRAL_BLOCK_SMOOTH.get(),
                ModBlocks.FACTORY_BLOCK.get(), ModBlocks.INVERTED_FACTORY_BLOCK.get(), ModBlocks.BLACK_FACTORY_BLOCK.get(), ModBlocks.BLACK_INVERTED_FACTORY_BLOCK.get(), ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_RIGHT.get(),
                ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_ORANGE_RIGHT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_LEFT.get(), ModBlocks.WARNING_BLOCK_LINED_WHITE_BLACK_RIGHT.get(),
                ModBlocks.INDUSTRAL_BLOCK_FLOOR.get(), ModBlocks.BLACK_INDUSTRAL_BLOCK_FLOOR.get(), ModBlocks.GRATE_FLOOR_BLOCK.get());

        for (Block i : blockList) {
            items.add(index, new ItemStack(i));
            index++;
        }
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(ModBlocks.WARNING_BLOCK_LINED_BLACK_YELLOW_LEFT.get());
    }

}
