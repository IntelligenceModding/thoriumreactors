package unhappycodings.thoriumreactors.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import unhappycodings.thoriumreactors.ThoriumReactors;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeMods {
    private static final ResourceLocation SPLASHES_LOCATION = new ResourceLocation(ThoriumReactors.MOD_ID, "texts/splashes.txt");

    @SubscribeEvent
    public static void onTitleScreenOpen(ScreenEvent.Init event) {
        if (event.getScreen() instanceof TitleScreen) {
            SplashManager manager = Minecraft.getInstance().getSplashManager();

            List<String> tags = prepare();
            manager.splashes.addAll(tags);
        }
    }

    // copy from net.minecraft.client.resources.SplashManager
    protected static List<String> prepare() {
        try {
            BufferedReader bufferedreader = Minecraft.getInstance().getResourceManager().openAsReader(SPLASHES_LOCATION);
            List<String> list;

            try {
                list = bufferedreader.lines().map(String::trim).filter((p_118876_) -> p_118876_.hashCode() != 125780783).collect(Collectors.toList());
            } catch (Throwable throwable1) {
                try {
                    bufferedreader.close();
                } catch (Throwable throwable) {
                    throwable1.addSuppressed(throwable);
                }
                throw throwable1;
            }
            bufferedreader.close();
            return list;
        } catch (IOException ioexception) {
            return Collections.emptyList();
        }
    }

}
