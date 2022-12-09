package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.block.ModBlocks;
import unhappycodings.thoriumreactors.common.registration.Registration;

public final class ModBlockEntities {
    public static final RegistryObject<BlockEntityType<CraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(CraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));

    public static void register() {
    }

}
