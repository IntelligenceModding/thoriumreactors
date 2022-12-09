package unhappycodings.thoriumreactors.common.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.blockentity.CraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.registration.Registration;

import java.util.function.Supplier;

public class ModBlocks {

    private ModBlocks() {
        throw new IllegalStateException("ModBlocks class");
    }

    public static final RegistryObject<CraftingTableBlock> THORIUM_CRAFTING_TABLE = register("thorium_crafting_table", CraftingTableBlock::new);

    public static void register() {

    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(toReturn.get(), new Item.Properties().tab(ThoriumReactors.creativeTab)));
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Supplier<BlockItem> blockItem) {
        RegistryObject<T> registryObject = registerNoItem(name, block);
        Registration.ITEMS.register(name, blockItem);
        return registryObject;
    }


}
