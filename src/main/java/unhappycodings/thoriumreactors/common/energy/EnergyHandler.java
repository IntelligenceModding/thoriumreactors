package unhappycodings.thoriumreactors.common.energy;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage {
    public IEnergyCapable energyHandler;
    public Direction direction;

    public EnergyHandler(IEnergyCapable energyHandler, Direction direction) {
        super((int) energyHandler.getEnergyCapacity(), (int) energyHandler.getMaxEnergyTransfer());
        this.energyHandler = energyHandler;
        this.direction = direction;
    }

    public static LazyOptional<EnergyHandler>[] createEnergyHandlers(IEnergyCapable handler, Direction... directions) {
        LazyOptional<EnergyHandler>[] handlers = new LazyOptional[directions.length];
        for (int i = 0; i < directions.length; i++) {
            final Direction side = directions[i];
            handlers[i] = LazyOptional.of(() -> new EnergyHandler(handler, side));
        }
        return handlers;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive()) return 0;

        long energyReceived = Math.min(getMaxEnergyStored() - getEnergyStored(), Math.min(energyHandler.getMaxEnergyTransfer(), maxReceive));
        if (!simulate) energyHandler.addEnergy(energyReceived, false);
        return (int) energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        long energyExtracted = Math.min(getEnergyStored(), Math.min(energyHandler.getMaxEnergyTransfer(), maxExtract));
        if (!simulate) energyHandler.removeEnergy(energyExtracted, false);
        return (int) energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return (int) energyHandler.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) energyHandler.getEnergyCapacity();
    }

    @Override
    public boolean canExtract() {
        return energyHandler.canOutputEnergy(direction);
    }

    @Override
    public boolean canReceive() {
        return energyHandler.canInputEnergy(direction);
    }

}
