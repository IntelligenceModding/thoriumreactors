package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.container.*;

public class ModContainerTypes {

    public static final RegistryObject<MenuType<ThoriumCraftingTableContainer>> THORIUM_CRAFTING_TABLE_CONTAINER = Registration.CONTAINER_TYPES.register("thorium_crafting_table_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new ThoriumCraftingTableContainer(windowId, inv, pos, level, 0);
    }));

    public static final RegistryObject<MenuType<MachineGeneratorContainer>> GENERATOR_CONTAINER = Registration.CONTAINER_TYPES.register("generator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MachineGeneratorContainer(windowId, inv, pos, level, 0);
    }));

    public static final RegistryObject<MenuType<MachineElectrolyticSaltSeparatorContainer>> ELECTROLYTIC_SALT_SEPARATOR_CONTAINER = Registration.CONTAINER_TYPES.register("electrolytic_salt_separator_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MachineElectrolyticSaltSeparatorContainer(windowId, inv, pos, level, 0);
    }));

    public static final RegistryObject<MenuType<MachineFluidEvaporatorContainer>> FLUID_EVAPORATION_CONTAINER = Registration.CONTAINER_TYPES.register("fluid_evaporation_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MachineFluidEvaporatorContainer(windowId, inv, pos, level, 0);
    }));

    public static final RegistryObject<MenuType<MachineSaltMelterContainer>> SALT_MELTER_CONTAINER = Registration.CONTAINER_TYPES.register("salt_melter_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new MachineSaltMelterContainer(windowId, inv, pos, level, 0);
    }));

    public static void register() {
    }

}
