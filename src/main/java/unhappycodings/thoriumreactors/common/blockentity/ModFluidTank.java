package unhappycodings.thoriumreactors.common.blockentity;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class ModFluidTank extends FluidTank {
    public final int slotId;
    public boolean canInput;
    public boolean canOutput;
    public boolean isCreative;
    public int allowedFluid;

    public ModFluidTank(int capacity, boolean canInput, boolean canOuput, int slotId, FluidStack fluid) {
        super(capacity);
        this.capacity = capacity;
        this.canInput = canInput;
        this.canOutput = canOuput;
        this.slotId = slotId;
        this.fluid = fluid;
        this.isCreative = capacity == -1;
        if (this.isCreative) this.capacity = Integer.MAX_VALUE;
    }

    public ModFluidTank(int capacity, boolean canInput, boolean canOuput, int slotId, FluidStack fluid, int allowedFluid) {
        super(allowedFluid);
        this.capacity = allowedFluid;
        this.canInput = canInput;
        this.canOutput = canOuput;
        this.slotId = slotId;
        this.fluid = fluid;
    }

    public void setAllowedFluid(int allowedFluid) {
        this.allowedFluid = allowedFluid;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (isCreative) return resource.getAmount();
        return canInput ? super.fill(resource.getAmount() > getMaxFluidTransfer() ? new FluidStack(resource.getFluid(), getMaxFluidTransfer()) : resource, action) : 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        if (isCreative) return resource;
        return canOutput ? super.drain(resource, action) : FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        if (isCreative) return new FluidStack(fluid, maxDrain);
        return super.drain(maxDrain, action);
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return true;
    }

    public int getMaxFluidTransfer() {
        return isCreative ? Integer.MAX_VALUE / 2 : capacity;
    }

}
