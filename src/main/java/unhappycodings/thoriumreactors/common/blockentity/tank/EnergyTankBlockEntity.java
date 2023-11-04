package unhappycodings.thoriumreactors.common.blockentity.tank;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineGeneratorBlock;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.network.PacketHandler;
import unhappycodings.thoriumreactors.common.network.toclient.reactor.ClientEnergyTankRenderDataPacket;

import java.util.List;

public class EnergyTankBlockEntity extends BlockEntity implements IEnergyCapable {
    public int capacity = 0;
    private final boolean isCreative;
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final ModEnergyStorage ENERGY_STORAGE;

    public EnergyTankBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int capacity) {
        super(pType, pPos, pBlockState);
        this.isCreative = capacity == -1;
        this.capacity = capacity;
        if (this.isCreative) this.capacity = Integer.MAX_VALUE;
        ENERGY_STORAGE = new ModEnergyStorage(capacity, this.capacity / 100) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                energy = this.getEnergyStored();
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side != null)
            return lazyEnergyHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    public void tick() {
        if (isCreative) {
            boolean isAir = ENERGY_STORAGE.getEnergyStored() < Integer.MAX_VALUE;
            ENERGY_STORAGE.setEnergy(isAir ? 0 : Integer.MAX_VALUE);
        }
        updateRenderData();
    }

    public void updateRenderData() {
        BlockPos p = getBlockPos();
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, new AABB(p.getX() -18, p.getY() -18, p.getZ() -18, p.getX() + 18, p.getY() + 18, p.getZ() + 18));

        for (ServerPlayer player : players) {
            PacketHandler.sendToClient(new ClientEnergyTankRenderDataPacket(getBlockPos(), getEnergy()), player);
        }
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
        return capacity;
    }

    @Override
    public long getMaxEnergyTransfer() {
        return capacity / 100;
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
