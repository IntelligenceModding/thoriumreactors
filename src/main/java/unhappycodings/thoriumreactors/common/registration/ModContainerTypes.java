package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.blockentity.renderer.BlastedIronChestRenderer;
import unhappycodings.thoriumreactors.common.container.chest.BlastedIronChestContainer;
import unhappycodings.thoriumreactors.common.container.chest.SteelChestContainer;
import unhappycodings.thoriumreactors.common.container.chest.ThoriumChestContainer;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.container.machine.*;

public class ModContainerTypes {

    public static final RegistryObject<MenuType<ThoriumCraftingTableContainer>> THORIUM_CRAFTING_TABLE_CONTAINER = Registration.CONTAINER_TYPES.register("thorium_crafting_table_container", () -> IForgeMenuType.create((windowId, inv, data) -> new ThoriumCraftingTableContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));

    public static final RegistryObject<MenuType<MachineGeneratorContainer>> GENERATOR_CONTAINER = Registration.CONTAINER_TYPES.register("generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineGeneratorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineElectrolyticSaltSeparatorContainer>> ELECTROLYTIC_SALT_SEPARATOR_CONTAINER = Registration.CONTAINER_TYPES.register("electrolytic_salt_separator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineElectrolyticSaltSeparatorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineFluidEvaporatorContainer>> FLUID_EVAPORATION_CONTAINER = Registration.CONTAINER_TYPES.register("fluid_evaporation_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineFluidEvaporatorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineSaltMelterContainer>> SALT_MELTER_CONTAINER = Registration.CONTAINER_TYPES.register("salt_melter_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineSaltMelterContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineConcentratorContainer>> CONCENTRATOR_CONTAINER = Registration.CONTAINER_TYPES.register("concentrator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineConcentratorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineDecomposerContainer>> DECOMPOSER_CONTAINER = Registration.CONTAINER_TYPES.register("decomposer_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineDecomposerContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineUraniumOxidizerContainer>> URANIUM_OXIDIZER_CONTAINER = Registration.CONTAINER_TYPES.register("uranium_oxidizer_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineUraniumOxidizerContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineFluidCentrifugeContainer>> FLUID_CENTRIFUGE_CONTAINER = Registration.CONTAINER_TYPES.register("fluid_centrifuge_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineFluidCentrifugeContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineCrystallizerContainer>> CRYSTALLIZER_CONTAINER = Registration.CONTAINER_TYPES.register("crystallizer_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineCrystallizerContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineBlastFurnaceContainer>> BLAST_FURNACE_CONTAINER = Registration.CONTAINER_TYPES.register("blast_furnace_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineBlastFurnaceContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));

    public static final RegistryObject<MenuType<ThoriumChestContainer>> THORIUM_CHEST_CONTAINER = Registration.CONTAINER_TYPES.register("thorium_chest_container", () -> IForgeMenuType.create((windowId, inv, data) -> new ThoriumChestContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<SteelChestContainer>> STEEL_CHEST_CONTAINER = Registration.CONTAINER_TYPES.register("steel_chest_container", () -> IForgeMenuType.create((windowId, inv, data) -> new SteelChestContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<BlastedIronChestContainer>> BLASTED_IRON_CHEST_CONTAINER = Registration.CONTAINER_TYPES.register("blasted_iron_chest_container", () -> IForgeMenuType.create((windowId, inv, data) -> new BlastedIronChestContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));

    public static void register() {
    }

}
