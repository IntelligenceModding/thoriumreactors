package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.CraftingTableBlock;
import unhappycodings.thoriumreactors.common.block.ModBlocks;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

public class ModelAndBlockStatesProvider extends BlockStateProvider {
    DataGenerator gen;

    public ModelAndBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ThoriumReactors.MOD_ID, exFileHelper);
        this.gen = gen;
    }

    private static int getSensorRotation(Direction facing) {
        return switch (facing) {
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
            default -> 0;
        };
    }

    @Override
    protected void registerStatesAndModels() {
        craftingTableBlock(ModBlocks.THORIUM_CRAFTING_TABLE.get());
    }

    public void craftingTableBlock(CraftingTableBlock block) {
        ModelFile model = models().withExistingParent(ItemUtil.getRegString(block), new ResourceLocation("block/cube"))
                .texture("down", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_bottom"))
                .texture("up", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_top"))
                .texture("north", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("east", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("south", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("west", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"))
                .texture("particle", new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_crafting_table_side"));
        craftingTableBlock(block, model);
    }

    public void craftingTableBlock(CraftingTableBlock block, ModelFile model) {
        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

}
