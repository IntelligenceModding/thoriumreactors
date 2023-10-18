package unhappycodings.thoriumreactors.common.energy;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    final int maxTransfer;
    public boolean isCreative;

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
        this.isCreative = capacity == -1;
        if (this.isCreative) this.capacity = Integer.MAX_VALUE;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (isCreative) return maxExtract;
        onEnergyChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (isCreative) return maxReceive;
        onEnergyChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

}
