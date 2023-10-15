package unhappycodings.thoriumreactors.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import unhappycodings.thoriumreactors.common.fluid.ModFluidTypes;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @WrapOperation(method = "aiStep()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D", ordinal = 1),
            require = 0)
    private double thoriumreactors_customFluidJumpWeaker(LivingEntity livingEntity, TagKey<Fluid> tagKey, Operation<Double> original) {
        List<FluidType> fluidTypes = List.of(ModFluidTypes.MOLTEN_SALT.get(), ModFluidTypes.DEPLETED_MOLTEN_SALT.get(), ModFluidTypes.HEATED_MOLTEN_SALT.get(), ModFluidTypes.HYDROFLUORITE.get(), ModFluidTypes.STEAM.get(), ModFluidTypes.URANIUM_HEXAFLUORITE.get(), ModFluidTypes.ENRICHED_URANIUM_HEXAFLUORITE.get());
        for (FluidType fluidType : fluidTypes) {
            double newFluidHeight = this.getFluidTypeHeight(fluidType);
            if (newFluidHeight > 0) {
                return newFluidHeight;
            }
        }
        return original.call(livingEntity, tagKey);
    }

}