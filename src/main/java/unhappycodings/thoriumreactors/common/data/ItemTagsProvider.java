package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.nio.file.Path;

public class ItemTagsProvider extends net.minecraft.data.tags.TagsProvider<Item> {
    private DataGenerator generator;

    @SuppressWarnings("deprecation")
    protected ItemTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Registry.ITEM, ThoriumReactors.MOD_ID, existingFileHelper);
        this.generator = generator;
    }

    @Override
    protected void addTags() {
        tag(ItemTags.create(new ResourceLocation("forge", "tool/wrench"))).add(ModItems.CONFIGURATOR.get());
        tag(ItemTags.create(new ResourceLocation("forge", "tool/configurator"))).add(ModItems.CONFIGURATOR.get());

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
