package unhappycodings.thoriumreactors.common.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    public LootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), ImmutableList.of(new net.minecraft.data.loot.LootTableProvider.SubProviderEntry(LootTableBlocks::new, LootContextParamSets.BLOCK)));
    }

    @NotNull
    @Override
    public List<net.minecraft.data.loot.LootTableProvider.SubProviderEntry> getTables() {
        return ImmutableList.of(new net.minecraft.data.loot.LootTableProvider.SubProviderEntry(LootTableBlocks::new, LootContextParamSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker) {
        map.forEach((id, table) -> table.validate(validationtracker));
    }

}