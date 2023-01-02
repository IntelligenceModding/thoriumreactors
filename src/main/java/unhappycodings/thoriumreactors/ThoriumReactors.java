package unhappycodings.thoriumreactors;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import unhappycodings.thoriumreactors.common.ItemCreativeTab;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.registration.*;
import unhappycodings.thoriumreactors.common.container.util.ContainerTypes;
import unhappycodings.thoriumreactors.common.recipe.ModRecipes;

@Mod(ThoriumReactors.MOD_ID)
public class ThoriumReactors {
    public static final String MOD_ID = "thoriumreactors";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab creativeTab = new ItemCreativeTab();

    public ThoriumReactors() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        LOGGER.info("[" + MOD_ID + "] Initialization");

        Registration.register();

        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModSounds.register();

        ContainerTypes.register();
        ModRecipes.register();

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::onCommonSetup);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
    }

}
