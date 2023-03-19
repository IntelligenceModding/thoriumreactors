package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class ModFluidTank extends FluidTank {
    public final int slotId;
    public boolean canInput;
    public boolean canOutput;
    public final Fluid filter;

    public ModFluidTank(int capacity, boolean canInput, boolean canOuput, int slotId, FluidStack fluid, Fluid filter) {
        super(capacity);
        this.capacity = capacity;
        this.canInput = canInput;
        this.canOutput = canOuput;
        this.slotId = slotId;
        this.fluid = fluid;
        this.filter = filter;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return canInput ? super.fill(resource.getAmount() > getMaxFluidTransfer() ? new FluidStack(resource.getFluid(), (int) getMaxFluidTransfer()) : resource, action) : 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        return canOutput ? super.drain(resource, action) : FluidStack.EMPTY;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return filter.isSame(stack.getFluid());
    }

    public int getMaxFluidTransfer() {
        return 50;
    }

}
