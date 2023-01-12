package unhappycodings.thoriumreactors.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
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

    public static void trySendToNeighbors(Level world, BlockPos pos, ModEnergyStorage energyStorage, int energy, int transferRate) {
        for (Direction side : Direction.values()) {
            if (energy == 0)
                return;
            trySendTo(world, pos, energyStorage, side, transferRate);
        }
    }

    public static void trySendTo(Level world, BlockPos pos, ModEnergyStorage energyStorage, Direction side, int transferRate) {
        BlockEntity tileEntity = world.getBlockEntity(pos.relative(side));
        if (tileEntity != null) {
            tileEntity.getCapability(ForgeCapabilities.ENERGY, side.getOpposite()).ifPresent((storage) -> trySendEnergy(storage, energyStorage, transferRate));
        }
    }

    private static void trySendEnergy(IEnergyStorage other, EnergyStorage energyStorage, int transferRate) {
        if (other.canReceive()) {
            long toSend = energyStorage.extractEnergy(transferRate, true);
            int sent = other.receiveEnergy((int) toSend, false);
            if (sent > 0) energyStorage.extractEnergy(sent, false);
        }
    }

}
