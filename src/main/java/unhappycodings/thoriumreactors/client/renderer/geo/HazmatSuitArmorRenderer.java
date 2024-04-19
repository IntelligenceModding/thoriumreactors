package unhappycodings.thoriumreactors.client.renderer.geo;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import unhappycodings.thoriumreactors.ThoriumReactors;
import unhappycodings.thoriumreactors.common.item.HazmatSuitArmorItem;

public class HazmatSuitArmorRenderer extends GeoArmorRenderer<HazmatSuitArmorItem> {

    public HazmatSuitArmorRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(ThoriumReactors.MOD_ID, "hazmat_suit")));
    }

}
