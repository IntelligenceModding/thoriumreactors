package unhappycodings.thoriumreactors.common.blockentity.turbine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineGeneratorBlock;
import unhappycodings.thoriumreactors.common.block.turbine.TurbinePowerPortBlock;
import unhappycodings.thoriumreactors.common.blockentity.turbine.base.TurbineFrameBlockEntity;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class TurbinePowerPortBlockEntity extends TurbineFrameBlockEntity implements IEnergyCapable {
    public static final int MAX_POWER = 124102300;
    public static final int MAX_TRANSFER = MAX_POWER / 100;
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    public TurbinePowerPortBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURBINE_POWER_PORT.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(TurbinePowerPortBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && side != null && facing == side)
            return lazyEnergyHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ENERGY)
            return lazyEnergyHandler.cast();
        return super.getCapability(cap);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Energy", getEnergy());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        setEnergy(nbt.getInt("Energy"));
    }

    public ModEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    @Override
    public boolean canInputEnergy() {
        return false;
    }

    @Override
    public boolean canOutputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineGeneratorBlock.FACING).getOpposite() == direction;
    }

    public int getEnergy() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }

    public int getCapacity() {
        return ENERGY_STORAGE.getMaxEnergyStored();
    }

    @Override
    public void setCapacity(int capacity) {

    }

    @Override
    public long getEnergyStored() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    @Override
    public long getEnergyCapacity() {
        return MAX_POWER;
    }

    @Override
    public long getMaxEnergyTransfer() {
        return MAX_TRANSFER;
    }

    @Override
    public int getEnergyDrain() {
        return 0;
    }

    @Override
    public long removeEnergy(long energy, boolean simulate) {
        return ENERGY_STORAGE.extractEnergy((int) energy, simulate);
    }

    @Override
    public long addEnergy(long energy, boolean simulate) {
        return ENERGY_STORAGE.receiveEnergy((int) energy, simulate);
    }

}
