package unhappycodings.thoriumreactors.common.data;

import net.minecraft.core.NonNullList;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.registration.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.util.List;
import java.util.Set;

public class LootTableBlocks extends BlockLootSubProvider {

    private static final List<Block> EXCLUSION_LIST = NonNullList.of(Blocks.AIR, ModBlocks.URANIUM_ORE.get(), ModBlocks.DEEPSLATE_URANIUM_ORE.get(), ModBlocks.GRAPHITE_ORE.get(), ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModBlocks.FLUORITE_ORE.get(), ModBlocks.DEEPSLATE_FLUORITE_ORE.get());

    protected LootTableBlocks() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach((block -> {
                    if (!EXCLUSION_LIST.contains(block)) {
                        dropSelf(block);
                    }
                }));

        add(ModBlocks.URANIUM_ORE.get(), createOreDrops(ModBlocks.URANIUM_ORE.get(), ModItems.RAW_URANIUM.get(), 1, 2));
        add(ModBlocks.DEEPSLATE_URANIUM_ORE.get(), createOreDrops(ModBlocks.DEEPSLATE_URANIUM_ORE.get(), ModItems.RAW_URANIUM.get(), 1, 2));
        add(ModBlocks.FLUORITE_ORE.get(), createOreDrops(ModBlocks.FLUORITE_ORE.get(), ModItems.FLUORITE.get(), 2, 4));
        add(ModBlocks.DEEPSLATE_FLUORITE_ORE.get(), createOreDrops(ModBlocks.DEEPSLATE_FLUORITE_ORE.get(), ModItems.FLUORITE.get(), 2, 4));
        add(ModBlocks.GRAPHITE_ORE.get(), createOreDrops(ModBlocks.GRAPHITE_ORE.get(), ModItems.GRAPHITE_CRYSTAL.get(), 1, 3));
        add(ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), createOreDrops(ModBlocks.DEEPSLATE_GRAPHITE_ORE.get(), ModItems.GRAPHITE_CRYSTAL.get(), 1, 3));

    }

    protected LootTable.Builder createOreDrops(Block pBlock, Item item, float min, float max) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}