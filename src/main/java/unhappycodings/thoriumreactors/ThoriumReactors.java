package unhappycodings.thoriumreactors;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import unhappycodings.thoriumreactors.common.ItemCreativeTab;
import unhappycodings.thoriumreactors.common.block.ModBlocks;
import unhappycodings.thoriumreactors.common.blockentity.ModBlockEntities;
import unhappycodings.thoriumreactors.common.container.ContainerTypes;
import unhappycodings.thoriumreactors.common.item.ModItems;
import unhappycodings.thoriumreactors.common.recipe.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.Registration;

@Mod(ThoriumReactors.MOD_ID)
public class ThoriumReactors {
    public static final String MOD_ID = "thoriumreactors";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreativeModeTab creativeTab = new ItemCreativeTab();

    public ThoriumReactors() {
        LOGGER.info("[" + MOD_ID + "] Initialization");

        Registration.register();

        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();

        ContainerTypes.register();
        ModRecipes.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

}
