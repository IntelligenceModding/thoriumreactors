package unhappycodings.thoriumreactors.common.util;

import net.minecraftforge.energy.IEnergyStorage;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;

public class EnergyUtil {

    public static void tryDischargeItem(IEnergyStorage other, ModEnergyStorage energyStorage, int transferRate) {
        if (other.canExtract()) {
            long toSend = energyStorage.receiveEnergy(transferRate, true);
            int sent = other.extractEnergy((int) toSend, false);
            if (sent > 0) energyStorage.receiveEnergy(sent, false);
        }
    }

    public static void tryChargeItem(IEnergyStorage other, ModEnergyStorage energyStorage, int transferRate) {
        if (other.canReceive()) {
            long toSend = energyStorage.extractEnergy(transferRate, true);
            int sent = other.receiveEnergy((int) toSend, false);
            if (sent > 0) energyStorage.extractEnergy(sent, false);
        }
    }

}
