package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import unhappycodings.thoriumreactors.common.effect.RadiationEffect;

public class ModEffects {

    public static final RegistryObject<MobEffect> RADIATION = Registration.MOB_EFFECTS.register("radiation",
            () -> new RadiationEffect(MobEffectCategory.HARMFUL, 0x42AF00));

    public static void register() {
    }

}
