package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.world.damagesource.DamageSource;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModDamageSources {

    public static final DamageSource OVERDOSIS = new DamageSource(ThoriumReactors.MOD_ID + ".radioactive_overdosis");
    public static final DamageSource GRIND = new DamageSource(ThoriumReactors.MOD_ID + ".grind");

}
