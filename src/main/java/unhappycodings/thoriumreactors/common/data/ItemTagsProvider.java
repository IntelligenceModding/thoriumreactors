package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.Registration;
import unhappycodings.thoriumreactors.common.util.FormattingUtil;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

import java.nio.file.Path;

public class ItemTagsProvider extends net.minecraft.data.tags.TagsProvider<Item> {
    public static final TagKey<Item> WRENCHES_TAG = ItemTags.create(new ResourceLocation("forge", "wrenches"));
    public static final TagKey<Item> WRENCH_TAG = ItemTags.create(new ResourceLocation("forge", "tools/wrench"));
    public static final TagKey<Item> TOOLS_TAG = ItemTags.create(new ResourceLocation("forge", "tools"));
    private DataGenerator generator;

    @SuppressWarnings("deprecation")
    protected ItemTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Registry.ITEM, ThoriumReactors.MOD_ID, existingFileHelper);
        this.generator = generator;
    }

    @Override
    protected void addTags() {
        tag(WRENCHES_TAG).add(ModItems.CONFIGURATOR.get());
        tag(WRENCH_TAG).add(ModItems.CONFIGURATOR.get());
        tag(TOOLS_TAG).add(ModItems.CONFIGURATOR.get());

        Item[] ingotList = {ModItems.GRAPHITE_INGOT.get(), ModItems.BLASTED_IRON_INGOT.get(), ModItems.STEEL_INGOT.get(), ModItems.MOLYBDENUM_INGOT.get(), ModItems.MANGANESE_INGOT.get(), ModItems.NICKEL_INGOT.get(), ModItems.ALUMINUM_INGOT.get(), ModItems.CHROMIUM_INGOT.get(), ModItems.NIOB_INGOT.get(), ModItems.TITANIUM_INGOT.get(), ModItems.URANIUM_INGOT.get(), ModItems.FLUORITE_INGOT.get(), ModItems.COBALT_INGOT.get()};
        Item[] nuggetList = {ModItems.GRAPHITE_NUGGET.get(), ModItems.BLASTED_IRON_NUGGET.get(), ModItems.STEEL_NUGGET.get(), ModItems.MOLYBDENUM_NUGGET.get(), ModItems.MANGANESE_NUGGET.get(), ModItems.NICKEL_NUGGET.get(), ModItems.ALUMINUM_NUGGET.get(), ModItems.CHROMIUM_NUGGET.get(), ModItems.NIOB_NUGGET.get(), ModItems.TITANIUM_NUGGET.get(), ModItems.URANIUM_NUGGET.get(), ModItems.FLUORITE_NUGGET.get(), ModItems.COBALT_NUGGET.get()};

        for (Item item : ingotList) {
            String current = ItemUtil.getRegString(item).replace("thoriumreactors:", "").replace("_ingot", "");
            tag(ItemTags.create(new ResourceLocation("forge", "ingots/" + current))).add(item);
        }
        for (Item item : nuggetList) {
            String current = ItemUtil.getRegString(item).replace("thoriumreactors:", "").replace("_nugget", "");
            tag(ItemTags.create(new ResourceLocation("forge", "nuggets/" + current))).add(item);
        }

    }

    @NotNull
    @Override
    protected Path getPath(ResourceLocation location) {
        return this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/items/" + location.getPath() + ".json");
    }

    @NotNull
    @Override
    public String getName() {
        return "Item Tags";
    }
}
