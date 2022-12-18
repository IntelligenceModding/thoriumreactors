package unhappycodings.thoriumreactors.common.container.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.container.MachineGeneratorContainer;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableContainer;
import unhappycodings.thoriumreactors.common.registration.Registration;

public class ContainerTypes {

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

    public static void register() {
    }

}
