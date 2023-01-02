package unhappycodings.thoriumreactors.common.energy;

import net.minecraft.core.Direction;

public interface IEnergyCapable {

    long getEnergyStored();

    long getEnergyCapacity();

    long getMaxEnergyTransfer();

    int getEnergyDrain();

    long removeEnergy(long energy, boolean simulate);

    long addEnergy(long energy, boolean simulate);

    void setCapacity(int capacity);

    void setEnergy(int energy);

    default boolean canInputEnergy(Direction direction) {
        return false;
    }

    default boolean canInputEnergy() {
        return true;
    }

    default boolean canOutputEnergy(Direction direction) {
        return false;
    }

    default boolean canOutputEnergy() {
        return true;
    }

}
