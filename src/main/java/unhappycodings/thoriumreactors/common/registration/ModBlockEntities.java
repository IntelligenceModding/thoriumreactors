package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.block.chest.SteelChestBlock;
import unhappycodings.thoriumreactors.common.blockentity.chest.BlastedIronChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.SteelChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.chest.ThoriumChestBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.ThoriumCraftingTableBlockEntity;
import unhappycodings.thoriumreactors.common.blockentity.machine.*;
import unhappycodings.thoriumreactors.common.blockentity.reactor.ReactorControllerBlockEntity;

public final class ModBlockEntities {
    public static void register() {
    }

    public static final RegistryObject<BlockEntityType<ThoriumCraftingTableBlockEntity>> CRAFTING_TABLE = Registration.BLOCK_ENTITIES.register("thorium_crafting_table", () -> BlockEntityType.Builder.of(ThoriumCraftingTableBlockEntity::new, ModBlocks.THORIUM_CRAFTING_TABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ReactorControllerBlockEntity>> REACTOR_CONTROLLER = Registration.BLOCK_ENTITIES.register("reactor_controller", () -> BlockEntityType.Builder.of(ReactorControllerBlockEntity::new, ModBlocks.REACTOR_CONTROLLER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineGeneratorBlockEntity>> GENERATOR_BLOCK = Registration.BLOCK_ENTITIES.register("generator_block", () -> BlockEntityType.Builder.of(MachineGeneratorBlockEntity::new, ModBlocks.GENERATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidEvaporationBlockEntity>> FLUID_EVAPORATION_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_evaporation_block", () -> BlockEntityType.Builder.of(MachineFluidEvaporationBlockEntity::new, ModBlocks.FLUID_EVAPORATION_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineElectrolyticSaltSeparatorBlockEntity>> ELECTROLYTIC_SALT_SEPARATOR_BLOCK = Registration.BLOCK_ENTITIES.register("electrolytic_salt_separator_block", () -> BlockEntityType.Builder.of(MachineElectrolyticSaltSeparatorBlockEntity::new, ModBlocks.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineSaltMelterBlockEntity>> SALT_MELTER_BLOCK = Registration.BLOCK_ENTITIES.register("salt_melter_block", () -> BlockEntityType.Builder.of(MachineSaltMelterBlockEntity::new, ModBlocks.SALT_MELTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<MachineConcentratorBlockEntity>> CONCENTRATOR_BLOCK = Registration.BLOCK_ENTITIES.register("concentrator_block", () -> BlockEntityType.Builder.of(MachineConcentratorBlockEntity::new, ModBlocks.CONCENTRATOR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineDecomposerBlockEntity>> DECOMPOSER_BLOCK = Registration.BLOCK_ENTITIES.register("decomposer_block", () -> BlockEntityType.Builder.of(MachineDecomposerBlockEntity::new, ModBlocks.DECOMPOSER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineUraniumOxidizerBlockEntity>> URANIUM_OXIDIZER_BLOCK = Registration.BLOCK_ENTITIES.register("uranium_oxidizer_block", () -> BlockEntityType.Builder.of(MachineUraniumOxidizerBlockEntity::new, ModBlocks.URANIUM_OXIDIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineFluidCentrifugeBlockEntity>> FLUID_CENTRIFUGE_BLOCK = Registration.BLOCK_ENTITIES.register("fluid_centrifuge_block", () -> BlockEntityType.Builder.of(MachineFluidCentrifugeBlockEntity::new, ModBlocks.FLUID_CENTRIFUGE_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineCrystallizerBlockEntity>> CRYSTALLIZER_BLOCK = Registration.BLOCK_ENTITIES.register("crystallizer_block", () -> BlockEntityType.Builder.of(MachineCrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MachineBlastFurnaceBlockEntity>> BLAST_FURNACE_BLOCK = Registration.BLOCK_ENTITIES.register("blast_furnace_block", () -> BlockEntityType.Builder.of(MachineBlastFurnaceBlockEntity::new, ModBlocks.BLAST_FURNACE_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ThoriumChestBlockEntity>> THORIUM_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("thorium_chest_block", () -> BlockEntityType.Builder.of(ThoriumChestBlockEntity::new, ModBlocks.THORIUM_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<SteelChestBlockEntity>> STEEL_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("steel_chest_block", () -> BlockEntityType.Builder.of(SteelChestBlockEntity::new, ModBlocks.STEEL_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlastedIronChestBlockEntity>> BLASTED_IRON_CHEST_BLOCK = Registration.BLOCK_ENTITIES.register("blasted_iron_chest_block", () -> BlockEntityType.Builder.of(BlastedIronChestBlockEntity::new, ModBlocks.BLASTED_IRON_CHEST_BLOCK.get()).build(null));

}
