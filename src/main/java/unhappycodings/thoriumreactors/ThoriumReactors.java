package unhappycodings.thoriumreactors;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import unhappycodings.thoriumreactors.common.ItemCreativeTab;
import unhappycodings.thoriumreactors.common.block.ModBlocks;
import unhappycodings.thoriumreactors.common.item.ModItems;
import unhappycodings.thoriumreactors.common.registration.Registration;

@Mod(ThoriumReactors.MOD_ID)
public class ThoriumReactors {
    public static final String MOD_ID = "thoriumreactors";

    public static final CreativeModeTab creativeTab = new ItemCreativeTab();

    public ThoriumReactors() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registration.register();
        ModItems.register();
        ModBlocks.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

}
