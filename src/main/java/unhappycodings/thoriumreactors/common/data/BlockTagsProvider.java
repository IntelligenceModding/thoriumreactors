package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.Registration;
import unhappycodings.thoriumreactors.common.util.ItemUtil;

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
            tag(BlockTags.NEEDS_STONE_TOOL).add(getResourceKey(block));
        }
        Block[] stoneList = {ModBlocks.MANGANESE_ORE.get(), ModBlocks.CHROMITE_ORE.get(), ModBlocks.MOLYBDENUM_ORE.get(), ModBlocks.NICKEL_ORE.get(), ModBlocks.TITANIC_IRON_ORE.get(), ModBlocks.BAUXITE_ORE.get(), ModBlocks.PYROCHLOR_ORE.get(), ModBlocks.URANIUM_ORE.get(), ModBlocks.GRAPHITE_ORE.get(), ModBlocks.FLUORITE_ORE.get()};
        Block[] deepslateList = {ModBlocks.DEEPSLATE_MANGANESE_ORE.get(), ModBlocks.DEEPSLATE_CHROMITE_ORE.get(), ModBlocks.DEEPSLATE_MOLYBDENUM_ORE.get(), ModBlocks.DEEPSLATE_NICKEL_ORE.get(), ModBlocks.DEEPSLATE_TITANIC_IRON_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModBlocks.DEEPSLATE_PYROCHLOR_ORE.get(), ModBlocks.DEEPSLATE_URANIUM_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get()};

        for (int i = 0; i < stoneList.length; i++) {
            tag(create(new ResourceLocation("forge", "ores"))).add(getBlockResourceKey(stoneList[i]), getBlockResourceKey(deepslateList[i]));
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

        RegistryObject[] blocks = {ModBlocks.DEEPSLATE_TITANIC_IRON_ORE, ModBlocks.TITANIC_IRON_ORE, ModBlocks.DEEPSLATE_CHROMITE_ORE, ModBlocks.CHROMITE_ORE, ModBlocks.DEEPSLATE_GRAPHITE_ORE, ModBlocks.GRAPHITE_ORE, ModBlocks.DEEPSLATE_MOLYBDENUM_ORE, ModBlocks.MOLYBDENUM_ORE, ModBlocks.DEEPSLATE_FLUORITE_ORE, ModBlocks.FLUORITE_ORE, ModBlocks.DEEPSLATE_NICKEL_ORE, ModBlocks.NICKEL_ORE, ModBlocks.DEEPSLATE_URANIUM_ORE, ModBlocks.URANIUM_ORE, ModBlocks.DEEPSLATE_PYROCHLOR_ORE, ModBlocks.PYROCHLOR_ORE, ModBlocks.DEEPSLATE_MANGANESE_ORE, ModBlocks.MANGANESE_ORE, ModBlocks.DEEPSLATE_BAUXITE_ORE, ModBlocks.BAUXITE_ORE};

        for (RegistryObject<Block> block : blocks) {
            tag(create(new ResourceLocation("forge", "ores_in_ground/" + (block.get().getName().getString().contains("deepslate") ? "deepslate" : "stone")))).add(getResourceKey(block));
            tag(create(new ResourceLocation("forge", "ores"))).add(getResourceKey(block));
        }

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
        tag(create(new ResourceLocation("forge", "storage_blocks/thorium"))).add(getResourceKey(ModBlocks.THORIUM_BLOCK));

        tag(create(new ResourceLocation("forge", "chests/thorium"))).add(getResourceKey(ModBlocks.THORIUM_CHEST_BLOCK));
        tag(create(new ResourceLocation("forge", "chests/steel"))).add(getResourceKey(ModBlocks.STEEL_CHEST_BLOCK));
        tag(create(new ResourceLocation("forge", "chests/blasted_iron"))).add(getResourceKey(ModBlocks.BLASTED_IRON_CHEST_BLOCK));
        tag(create(new ResourceLocation("forge", "chests"))).add(getResourceKey(ModBlocks.THORIUM_CHEST_BLOCK)).add(getResourceKey(ModBlocks.STEEL_CHEST_BLOCK)).add(getResourceKey(ModBlocks.BLASTED_IRON_CHEST_BLOCK));

        tag(create(new ResourceLocation("forge", "ore_rates/singular"))).add(getResourceKey(ModBlocks.URANIUM_ORE)).add(getResourceKey(ModBlocks.DEEPSLATE_URANIUM_ORE));
        tag(create(new ResourceLocation("forge", "ore_rates/dense"))).add(getResourceKey(ModBlocks.GRAPHITE_ORE)).add(getResourceKey(ModBlocks.DEEPSLATE_GRAPHITE_ORE));
        tag(create(new ResourceLocation("forge", "ore_rates/dense"))).add(getResourceKey(ModBlocks.FLUORITE_ORE)).add(getResourceKey(ModBlocks.DEEPSLATE_FLUORITE_ORE));
    }

    public static TagKey<Block> create(ResourceLocation name) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), name);
    }

    @NotNull
    @Override
    protected Path getPath(ResourceLocation location) {
        return this.packOutput.getOutputFolder().resolve("data/" + location.getNamespace() + "/tags/blocks/" + location.getPath() + ".json");
    }

    public ResourceKey<Block> getResourceKey(RegistryObject<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getResourceKey(block.get()).get();
    }

    public ResourceKey<Block> getBlockResourceKey(Block block) {
        return ForgeRegistries.BLOCKS.getResourceKey(block).get();
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}
