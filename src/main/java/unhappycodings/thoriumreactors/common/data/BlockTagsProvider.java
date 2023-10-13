package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.nio.file.Path;

public class BlockTagsProvider extends net.minecraft.data.tags.TagsProvider<Block> {
    private DataGenerator generator;

    @SuppressWarnings("deprecation")
    protected BlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Registry.BLOCK, ThoriumReactors.MOD_ID, existingFileHelper);
        this.generator = generator;
    }

    @Override
    protected void addTags() {
        // Interate through all Blocks and add them the "Mineable with Pickaxe" tag
        for (RegistryObject<Block> block : Registration.BLOCKS.getEntries()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block.get());
            tag(BlockTags.NEEDS_IRON_TOOL).add(block.get());
        }
        tag(create(new ResourceLocation("forge", "ores/manganese"))).add(ModBlocks.MANGANESE_ORE.get(), ModBlocks.DEEPSLATE_MANGANESE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/chromite"))).add(ModBlocks.CHROMITE_ORE.get(), ModBlocks.DEEPSLATE_CHROMITE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/molybdenum"))).add(ModBlocks.MOLYBDENUM_ORE.get(), ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/nickel"))).add(ModBlocks.NICKEL_ORE.get(), ModBlocks.DEEPSLATE_NICKEL_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/titanic_iron"))).add(ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/titanium"))).add(ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/bauxite"))).add(ModBlocks.BAUXITE_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/aluminum"))).add(ModBlocks.BAUXITE_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/aluminium"))).add(ModBlocks.BAUXITE_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/pyrochlore"))).add(ModBlocks.PYROCHLOR_ORE.get(), ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/uranium"))).add(ModBlocks.URANIUM_ORE.get(), ModBlocks.DEEPSLATE_URANIUM_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/graphite"))).add(ModBlocks.GRAPHITE_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get());
        tag(create(new ResourceLocation("forge", "ores/fluorite"))).add(ModBlocks.FLUORITE_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get());
    }

    public static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, name);
    }

    @NotNull
    @Override
    protected Path getPath(ResourceLocation location) {
        return this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}
