package unhappycodings.thoriumreactors.common.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import unhappycodings.thoriumreactors.ThoriumReactors;

@Mod.EventBusSubscriber(modid = ThoriumReactors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(ThoriumReactors.MOD_ID, "block/thorium_chest"));
        event.addSprite(new ResourceLocation(ThoriumReactors.MOD_ID, "block/steel_chest"));
        event.addSprite(new ResourceLocation(ThoriumReactors.MOD_ID, "block/blasted_iron_chest"));

    }

}
