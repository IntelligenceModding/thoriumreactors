package unhappycodings.thoriumreactors.client.integration.top;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class TOPIntegrations {
    public static boolean TOPLoaded;
    public static final String TOP_MOD_ID = "theoneprobe";

    public static void commonSetup() {
        ModList modList = ModList.get();
        TOPLoaded = modList.isLoaded(TOP_MOD_ID);
    }

    public static void sendIMCs(final InterModEnqueueEvent ignored) {
        if (TOPLoaded) {
            InterModComms.sendTo(TOP_MOD_ID, "getTheOneProbe", TOPInfoPlugin::new);
        }
    }
}