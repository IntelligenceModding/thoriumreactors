package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.blockentity.*;

public final class ModBlockEntities {
    public static final RegistryObject<BlockEntityType<ThoriumCraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(ThoriumCraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineGeneratorBlockEntity>> GENERATOR_BLOCK = Registration.BLOCK_ENTITIES.register("generator_block", () -> BlockEntityType.Builder.of(MachineGeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidEvaporationBlockEntity>> FLUID_EVAPORATION_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_evaporation_block", () -> BlockEntityType.Builder.of(MachineFluidEvaporationBlockEntity::new, ModBlocks.FLUID_EVAPORATION_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineElectrolyticSaltSeparatorBlockEntity>> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = Registration.BLOCK_ENTITIES.register("electrolytic_salt_separator_block", () -> BlockEntityType.Builder.of(MachineElectrolyticSaltSeparatorBlockEntity::new, ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineSaltMelterBlockEntity>> SALT_MELTER_BLOCK = Registration.BLOCK_ENTITIES.register("salt_melter_block", () -> BlockEntityType.Builder.of(MachineSaltMelterBlockEntity::new, ModBlocks.SAlT_MELTER_BLOCK.get()).build(null));

    public static void register() {
    }

}
