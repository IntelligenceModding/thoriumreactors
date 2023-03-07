package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.container.*;
import unhappycodings.thoriumreactors.common.container.machine.*;

public class ModContainerTypes {

    public static final RegistryObject<MenuType<ThoriumCraftingTableContainer>> THORIUM_CRAFTING_TABLE_CONTAINER = Registration.CONTAINER_TYPES.register("thorium_crafting_table_container", () -> IForgeMenuType.create((windowId, inv, data) -> new ThoriumCraftingTableContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineGeneratorContainer>> GENERATOR_CONTAINER = Registration.CONTAINER_TYPES.register("generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineGeneratorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineElectrolyticSaltSeparatorContainer>> ELECTROLYTIC_SALT_SEPARATOR_CONTAINER = Registration.CONTAINER_TYPES.register("electrolytic_salt_separator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineElectrolyticSaltSeparatorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineFluidEvaporatorContainer>> FLUID_EVAPORATION_CONTAINER = Registration.CONTAINER_TYPES.register("fluid_evaporation_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineFluidEvaporatorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineSaltMelterContainer>> SALT_MELTER_CONTAINER = Registration.CONTAINER_TYPES.register("salt_melter_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineSaltMelterContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineConcentratorContainer>> CONCENTRATOR_CONTAINER = Registration.CONTAINER_TYPES.register("concentrator_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineConcentratorContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));
    public static final RegistryObject<MenuType<MachineDecomposerContainer>> DECOMPOSER_CONTAINER = Registration.CONTAINER_TYPES.register("decomposer_container", () -> IForgeMenuType.create((windowId, inv, data) -> new MachineDecomposerContainer(windowId, inv, data.readBlockPos(), inv.player.getCommandSenderWorld(), 0)));

    public static void register() {
    }

}
