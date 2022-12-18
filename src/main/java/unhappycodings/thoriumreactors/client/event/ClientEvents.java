package unhappycodings.thoriumreactors.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.container.MachineGeneratorContainer;
import unhappycodings.thoriumreactors.common.container.MachineGeneratorScreen;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableScreen;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), ThoriumCraftingTableScreen::new);
        MenuScreens.register(ContainerTypes.GENERATOR_CONTAINER.get(), MachineGeneratorScreen::new);
    }

}
