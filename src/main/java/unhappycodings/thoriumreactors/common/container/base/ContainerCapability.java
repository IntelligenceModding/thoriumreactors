package unhappycodings.thoriumreactors.common.container.base;

import net.minecraft.world.MenuProvider;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class ContainerCapability {
    public static Capability<MenuProvider> CONTAINER_PROVIDER_CAPABILITY = CapabilityManager.get(new CapabilityToken<MenuProvider>() {
    });

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(MenuProvider.class);
    }
}
