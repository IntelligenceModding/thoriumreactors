package unhappycodings.thoriumreactors;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import unhappycodings.thoriumreactors.client.config.ClientConfig;
import unhappycodings.thoriumreactors.client.integration.top.TOPIntegrations;
import unhappycodings.thoriumreactors.common.CompleteItemCreativeTab;
import unhappycodings.thoriumreactors.common.config.CommonConfig;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.registration.*;
import unhappycodings.thoriumreactors.common.registration.worldgeneration.ModConfiguredFeatures;
import unhappycodings.thoriumreactors.common.registration.worldgeneration.ModPlacedFeatures;

@Mod(ThoriumReactors.MOD_ID)
public class ThoriumReactors {
    public static final String MOD_ID = "thoriumreactors";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab creativeTab = new CompleteItemCreativeTab();

    public ThoriumReactors() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("[" + MOD_ID + "] Initialization");

        Registration.register();
        ModItems.register();
        ModBlocks.register();

        ModConfiguredFeatures.register(bus);
        ModPlacedFeatures.register(bus);

        ModBlockEntities.register();
        ModSounds.register();
        ModContainerTypes.register();
        ModRecipes.register();

        bus.addListener(TOPIntegrations::sendIMCs);

        CommonConfig.loadConfigFile(CommonConfig.commonConfig, FMLPaths.CONFIGDIR.get().resolve("thoriumreactors-common.toml").toString());
        ClientConfig.loadConfigFile(ClientConfig.clientConfig, FMLPaths.CONFIGDIR.get().resolve("thoriumreactors-client.toml").toString());
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::onCommonSetup);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
        TOPIntegrations.commonSetup();
    }

}
