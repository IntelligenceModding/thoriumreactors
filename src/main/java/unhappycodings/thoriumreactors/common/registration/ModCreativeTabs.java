package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.CompleteItemCreativeTabs;

public class ModCreativeTabs {

    public static final RegistryObject<CreativeModeTab> THORIUMREACTORS_TAB = Registration.CREATIVE_MODE_TABS.register(ThoriumReactors.MOD_ID, ModCreativeTabs::createCreativeTab);

    private static CreativeModeTab createCreativeTab() {
        CreativeModeTab.Builder builder = new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, -1);
        CompleteItemCreativeTabs.populateCreativeTabBuilder(builder);
        return builder.build();
    }

    public static void register() {

    }

}