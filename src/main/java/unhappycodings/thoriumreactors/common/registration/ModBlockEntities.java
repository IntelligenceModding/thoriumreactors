package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.blockentity.MachineGeneratorBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;

public final class ModBlockEntities {
    public static final RegistryObject<BlockEntityType<ThoriumCraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(ThoriumCraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineGeneratorBlockEntity>> GENERATOR_BLOCK = Registration.BLOCK_ENTITIES.register("generator_block", () -> BlockEntityType.Builder.of(MachineGeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));

    public static void register() {
    }

}
