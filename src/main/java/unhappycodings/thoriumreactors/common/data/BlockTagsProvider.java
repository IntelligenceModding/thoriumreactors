package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends net.minecraft.data.tags.TagsProvider<Block> {
    private final PackOutput packOutput;

    protected BlockTagsProvider(PackOutput pOutput, ResourceKey<? extends Registry<Block>> pRegistryKey, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pRegistryKey, pLookupProvider, ThoriumReactors.MOD_ID, existingFileHelper);
        this.packOutput = pOutput;
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Interate through all Blocks and add them the "Mineable with Pickaxe" tag
        for (RegistryObject<Block> block : Registration.BLOCKS.getEntries()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getResourceKey(block));
            tag(BlockTags.NEEDS_IRON_TOOL).add(getResourceKey(block));
        }

        tag(create(new ResourceLocation("forge", "ores/manganese"))).add(getResourceKey(ModBlocks.MANGANESE_ORE), getResourceKey(ModBlocks.DEEPSLATE_MANGANESE_ORE));
        tag(create(new ResourceLocation("forge", "ores/chromite"))).add(getResourceKey(ModBlocks.CHROMITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_CHROMITE_ORE));
        tag(create(new ResourceLocation("forge", "ores/molybdenum"))).add(getResourceKey(ModBlocks.MOLYBDENUM_ORE), getResourceKey(ModBlocks.DEEPSLATE_MOLYBDENUM_ORE));
        tag(create(new ResourceLocation("forge", "ores/nickel"))).add(getResourceKey(ModBlocks.NICKEL_ORE), getResourceKey(ModBlocks.DEEPSLATE_NICKEL_ORE));
        tag(create(new ResourceLocation("forge", "ores/titanic_iron"))).add(getResourceKey(ModBlocks.TITANIC_IRON_ORE), getResourceKey(ModBlocks.DEEPSLATE_TITANIC_IRON_ORE));
        tag(create(new ResourceLocation("forge", "ores/titanium"))).add(getResourceKey(ModBlocks.TITANIC_IRON_ORE), getResourceKey(ModBlocks.DEEPSLATE_TITANIC_IRON_ORE));
        tag(create(new ResourceLocation("forge", "ores/bauxite"))).add(getResourceKey(ModBlocks.BAUXITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_BAUXITE_ORE));
        tag(create(new ResourceLocation("forge", "ores/aluminum"))).add(getResourceKey(ModBlocks.BAUXITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_BAUXITE_ORE));
        tag(create(new ResourceLocation("forge", "ores/aluminium"))).add(getResourceKey(ModBlocks.BAUXITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_BAUXITE_ORE));
        tag(create(new ResourceLocation("forge", "ores/pyrochlore"))).add(getResourceKey(ModBlocks.PYROCHLOR_ORE), getResourceKey(ModBlocks.DEEPSLATE_PYROCHLOR_ORE));
        tag(create(new ResourceLocation("forge", "ores/uranium"))).add(getResourceKey(ModBlocks.URANIUM_ORE), getResourceKey(ModBlocks.DEEPSLATE_URANIUM_ORE));
        tag(create(new ResourceLocation("forge", "ores/graphite"))).add(getResourceKey(ModBlocks.GRAPHITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_GRAPHITE_ORE));
        tag(create(new ResourceLocation("forge", "ores/fluorite"))).add(getResourceKey(ModBlocks.FLUORITE_ORE), getResourceKey(ModBlocks.DEEPSLATE_FLUORITE_ORE));

        tag(create(new ResourceLocation("forge", "storage_blocks/chromium"))).add(getResourceKey(ModBlocks.CHROMIUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/graphite"))).add(getResourceKey(ModBlocks.GRAPHITE_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/steel"))).add(getResourceKey(ModBlocks.STEEL_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/blasted_iron"))).add(getResourceKey(ModBlocks.BLASTED_IRON_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/titanic_iron"))).add(getResourceKey(ModBlocks.TITANIUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/titanium"))).add(getResourceKey(ModBlocks.TITANIUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/molybdenum"))).add(getResourceKey(ModBlocks.MOLYBDENUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/cobalt"))).add(getResourceKey(ModBlocks.COBALT_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/fluorite"))).add(getResourceKey(ModBlocks.FLUORITE_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/nickel"))).add(getResourceKey(ModBlocks.NICKEL_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/uranium"))).add(getResourceKey(ModBlocks.URANIUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/aluminum"))).add(getResourceKey(ModBlocks.ALUMINUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/aluminium"))).add(getResourceKey(ModBlocks.ALUMINUM_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/manganese"))).add(getResourceKey(ModBlocks.MANGANESE_BLOCK));
        tag(create(new ResourceLocation("forge", "storage_blocks/pyrochlore"))).add(getResourceKey(ModBlocks.NIOB_BLOCK));
    }

    public static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), name);
    }

    @NotNull
    @Override
    protected Path getPath(ResourceLocation location) {
        return this.packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
    }

    public ResourceKey<Block> getResourceKey(RegistryObject<Block> block) {
        return ForgeRegistries.BLOCKS.getResourceKey(block.get()).get();
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}
