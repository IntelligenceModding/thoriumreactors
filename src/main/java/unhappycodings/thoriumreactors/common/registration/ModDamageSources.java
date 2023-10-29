package unhappycodings.thoriumreactors.common.registration;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import unhappycodings.thoriumreactors.ThoriumReactors;

public class ModDamageSources {

    public static final ResourceKey<DamageType> OVERDOSIS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ThoriumReactors.MOD_ID, "radioactive_overdosis"));
    public static final ResourceKey<DamageType> GRIND = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ThoriumReactors.MOD_ID, "grind"));

}
