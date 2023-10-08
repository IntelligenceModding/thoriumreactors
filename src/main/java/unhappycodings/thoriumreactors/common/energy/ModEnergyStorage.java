package unhappycodings.thoriumreactors.common.energy;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    final int maxTransfer;

    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.capacity = capacity;
        this.maxTransfer = maxTransfer;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        onEnergyChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        onEnergyChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

}
