package unhappycodings.thoriumreactors.client.event;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.client.renderer.FluidTankBlockEntityRenderer;
import unhappycodings.thoriumreactors.client.renderer.ReactorControllerBlockEntityRenderer;
import unhappycodings.thoriumreactors.common.blockentity.renderer.BlastedIronChestRenderer;
import unhappycodings.thoriumreactors.common.blockentity.renderer.SteelChestRenderer;
import unhappycodings.thoriumreactors.common.blockentity.renderer.ThoriumChestRenderer;
import unhappycodings.thoriumreactors.common.blockentity.renderer.ThoriumCraftingTableBlockRenderer;
import unhappycodings.thoriumreactors.common.container.chest.BlastedIronChestScreen;
import unhappycodings.thoriumreactors.common.container.chest.SteelChestScreen;
import unhappycodings.thoriumreactors.common.container.chest.ThoriumChestScreen;
import unhappycodings.thoriumreactors.common.container.ThoriumCraftingTableScreen;
import unhappycodings.thoriumreactors.common.container.machine.*;
import unhappycodings.thoriumreactors.common.container.reactor.ReactorControllerScreen;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModContainerTypes;
import unhappycodings.thoriumreactors.common.registration.ModFluids;
import unhappycodings.thoriumreactors.common.registration.ModKeyBindings;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterKeyMappingEvent(RegisterKeyMappingsEvent event) {
        event.register(ModKeyBindings.SHOW_DETAILS);
        event.register(ModKeyBindings.SHOW_DESCRIPTION);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ModContainerTypes.THORIUM_CRAFTING_TABLE_CONTAINER.get(), ThoriumCraftingTableScreen::new);
        MenuScreens.register(ModContainerTypes.GENERATOR_CONTAINER.get(), MachineGeneratorScreen::new);
        MenuScreens.register(ModContainerTypes.ELECTROLYTIC_SALT_SEPARATOR_CONTAINER.get(), MachineElectrolyticSaltSeparatorScreen::new);
        MenuScreens.register(ModContainerTypes.FLUID_EVAPORATION_CONTAINER.get(), MachineFluidEvaporatorScreen::new);
        MenuScreens.register(ModContainerTypes.SALT_MELTER_CONTAINER.get(), MachineSaltMelterScreen::new);
        MenuScreens.register(ModContainerTypes.CONCENTRATOR_CONTAINER.get(), MachineConcentratorScreen::new);
        MenuScreens.register(ModContainerTypes.DECOMPOSER_CONTAINER.get(), MachineDecomposerScreen::new);
        MenuScreens.register(ModContainerTypes.URANIUM_OXIDIZER_CONTAINER.get(), MachineUraniumOxidizerScreen::new);
        MenuScreens.register(ModContainerTypes.FLUID_CENTRIFUGE_CONTAINER.get(), MachineFluidCentrifugeScreen::new);
        MenuScreens.register(ModContainerTypes.CRYSTALLIZER_CONTAINER.get(), MachineCrystallizerScreen::new);
        MenuScreens.register(ModContainerTypes.BLAST_FURNACE_CONTAINER.get(), MachineBlastFurnaceScreen::new);

        MenuScreens.register(ModContainerTypes.THORIUM_CHEST_CONTAINER.get(), ThoriumChestScreen::new);
        MenuScreens.register(ModContainerTypes.STEEL_CHEST_CONTAINER.get(), SteelChestScreen::new);
        MenuScreens.register(ModContainerTypes.BLASTED_IRON_CHEST_CONTAINER.get(), BlastedIronChestScreen::new);

        MenuScreens.register(ModContainerTypes.REACTOR_CONTROLLER_CONTAINER.get(), ReactorControllerScreen::new);

        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HEATED_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HEATED_MOLTEN_SALT.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_HYDROFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_HYDROFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_URANIUM_HEXAFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_URANIUM_HEXAFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_ENRICHED_URANIUM_HEXAFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_ENRICHED_URANIUM_HEXAFLUORITE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_STEAM.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_STEAM.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.CRAFTING_TABLE.get(), ThoriumCraftingTableBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.THORIUM_CHEST_BLOCK.get(), ThoriumChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.STEEL_CHEST_BLOCK.get(), SteelChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLASTED_IRON_CHEST_BLOCK.get(), BlastedIronChestRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.REACTOR_CONTROLLER.get(), ReactorControllerBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SIMPLE_FLUID_TANK.get(), FluidTankBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GENERIC_FLUID_TANK.get(), FluidTankBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PROGRESSIVE_FLUID_TANK.get(), FluidTankBlockEntityRenderer::new);
    }
}
