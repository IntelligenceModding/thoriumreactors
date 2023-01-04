package unhappycodings.thoriumreactors.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.container.*;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.registration.ModFluids;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), ThoriumCraftingTableScreen::new);
        MenuScreens.register(ContainerTypes.GENERATOR_CONTAINER.get(), MachineGeneratorScreen::new);
        MenuScreens.register(ContainerTypes.ELECTROLYTIC_SALT_SEPARATOR_CONTAINER.get(), MachineElectrolyticSaltSeparatorScreen::new);
        MenuScreens.register(ContainerTypes.FLUID_EVAPORATION_CONTAINER.get(), MachineFluidEvaporatorScreen::new);
        MenuScreens.register(ContainerTypes.SALT_MELTER_CONTAINER.get(), MachineSaltMelterScreen::new);
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HEATED_MOLTEN_SALT.get(), RenderType.translucent());
    }

}
