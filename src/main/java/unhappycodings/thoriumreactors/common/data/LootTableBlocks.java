package unhappycodings.thoriumreactors.common.data;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LootTableBlocks extends BlockLoot {

    @Override
    protected void addTables() {
        Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(this::dropSelf);
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}