package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class WaterSourceBlockEntity extends BlockEntity {
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private final ModFluidTank FLUID_TANK_IN;

    public WaterSourceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WATER_SOURCE_BLOCK.get(), pPos, pBlockState);
        FLUID_TANK_IN = new ModFluidTank(-1, true, true, 0, new FluidStack(Fluids.WATER, Integer.MAX_VALUE));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            return lazyFluidInHandler.cast();
        }
        return super.getCapability(cap, side);
    }
}
