package unhappycodings.thoriumreactors.common.container;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.registration.Registration;

public class ContainerTypes {

    public static final RegistryObject<MenuType<CraftingTableContainer>> THORIUM_CRAFTING_TABLE_CONTAINER = Registration.CONTAINER_TYPES.register("thorium_crafting_table_container", () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level level = inv.player.getCommandSenderWorld();
        return new CraftingTableContainer(windowId, inv, pos, level);
    }));

    public static void register() {
    }

}
