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
            tag(BlockTags.NEEDS_STONE_TOOL).add(block.get());
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

        tag(create(new ResourceLocation("forge", "storage_blocks/chromium"))).add(ModBlocks.CHROMIUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/graphite"))).add(ModBlocks.GRAPHITE_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/steel"))).add(ModBlocks.STEEL_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/blasted_iron"))).add(ModBlocks.BLASTED_IRON_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/titanic_iron"))).add(ModBlocks.TITANIUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/titanium"))).add(ModBlocks.TITANIUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/molybdenum"))).add(ModBlocks.MOLYBDENUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/cobalt"))).add(ModBlocks.COBALT_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/fluorite"))).add(ModBlocks.FLUORITE_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/nickel"))).add(ModBlocks.NICKEL_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/uranium"))).add(ModBlocks.URANIUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/aluminum"))).add(ModBlocks.ALUMINUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/aluminium"))).add(ModBlocks.ALUMINUM_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/manganese"))).add(ModBlocks.MANGANESE_BLOCK.get());
        tag(create(new ResourceLocation("forge", "storage_blocks/pyrochlore"))).add(ModBlocks.NIOB_BLOCK.get());
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
