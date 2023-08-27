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
        int extractedEnergy = super.extractEnergy(maxTransfer, simulate);
        if (extractedEnergy != 0) onEnergyChanged();
        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receivedEnergy = super.receiveEnergy(maxTransfer, simulate);
        if (receivedEnergy != 0) onEnergyChanged();
        return receivedEnergy;
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

}
