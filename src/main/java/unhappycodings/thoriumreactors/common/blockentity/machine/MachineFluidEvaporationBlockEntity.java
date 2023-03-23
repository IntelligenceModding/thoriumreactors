package unhappycodings.thoriumreactors.common.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineDecomposerBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineFluidEvaporationBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineFluidEvaporatorContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

public class MachineFluidEvaporationBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = 250;
    public static final int MAX_RECIPE_TIME = 400;
    public static final int MAX_FLUID_IN = 6000;
    public static final int MAX_FLUID_TRANSFER = 100;
    public static final int NEEDED_ENERGY = 65;
    public static final int NEEDED_FLUID = 6;
    public static final int PRODUCTION = 135;

    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    public NonNullList<ItemStack> items;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    boolean inputDump;
    boolean powerable = true;
    int redstoneMode = 0;

    public MachineFluidEvaporationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_EVAPORATION_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            this.energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(MAX_FLUID_IN, true, false, 0, FluidStack.EMPTY, Fluids.WATER);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineFluidEvaporationBlock.FACING).getOpposite() == side) return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) if (facing.getClockWise() == side) return lazyFluidInHandler.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (side == facing.getCounterClockWise()) return itemHandler[side.get3DDataValue()].cast();
            return LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public boolean canInputEnergy() {
        return true;
    }

    @Override
    public boolean canInputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineFluidEvaporationBlock.FACING).getOpposite() == direction;
    }

    @Override
    public boolean canOutputEnergy() {
        return false;
    }

    @Override
    public boolean canOutputEnergy(Direction direction) {
        return false;
    }

    public void tick() {
        // Play Sounds
        if (getState() && getRecipeTime() % 20 == 0) {
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_FLUID_EVAPORATION.get(), SoundSource.BLOCKS, 0.09f,1f);
        }

        // Fluid Input Slot
        items.get(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
            if (storage.getFluidInTank(0).getFluid() == Fluids.WATER && storage.getTankCapacity(0) > 0) {
                tryFillTank(storage, FLUID_TANK_IN, storage.getTankCapacity(0), items.get(0).hasCraftingRemainingItem());
            }
        });

        // Energy Input Slot
        items.get(2).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

        if (isPowerable()) {
            switch (getRedstoneMode()) {
                case 0 -> operate(); // Ignored
                case 1 -> { // Normal
                    if (level.hasNeighborSignal(getBlockPos())) operate();
                    else if (getState()) setState(false);
                }
                case 2 -> { // Inverted
                    if (!level.hasNeighborSignal(getBlockPos())) operate();
                    else if (getState()) setState(false);
                }
            }
        } else if (getState()) {
            setState(false);
        }

        if (isInputDump() && getFluidAmountIn() > 0) getFluidIn().shrink(getFluidAmountIn() - MAX_FLUID_TRANSFER < MAX_FLUID_TRANSFER ? getFluidAmountIn() : MAX_FLUID_TRANSFER);
    }

    public void operate() {
        if (hasRecipeNeeds(NEEDED_FLUID, NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(MAX_RECIPE_TIME);
                setRecipeTime(MAX_RECIPE_TIME);
                if (!getState()) setState(true);
            }
            ItemStack outputSlot = getItem(1);
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0 && outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize() && (outputSlot.isEmpty() || outputSlot.is(ModItems.SODIUM.get()))) {
                if (!getState()) setState(true);
                // Consumption of Energy, Fluids, Items etc
                setEnergy(getEnergy() - NEEDED_ENERGY);
                getFluidIn().shrink(getFluidAmountNeeded());

                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setMaxRecipeTime(0);
                    setItem(1, new ItemStack(ModItems.SODIUM.get(), outputSlot.getCount() + 1));
                }
            } else {
                if (getState())
                    setState(false);
            }
        } else {
            if (getState())
                setState(false);
        }
    }

    public void tryFillTank(IFluidHandlerItem other, FluidTank fluidTank, int transferRate, boolean hasCraftingRemainder) {
        int toSend = fluidTank.fill(new FluidStack(Fluids.WATER, transferRate), IFluidHandler.FluidAction.SIMULATE);
        FluidStack sent = other.drain(toSend, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) {
            if (fluidTank.getFluidInTank(0).getAmount() + transferRate > fluidTank.getTankCapacity(0)) return;
            sent = new FluidStack(Fluids.WATER, transferRate);
        }
        if (sent.getAmount() > 0) fluidTank.fill(sent, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) items.set(0, new ItemStack(items.get(0).getItem().getCraftingRemainingItem()));
    }

    public boolean hasRecipeNeeds(int water, int energy) {
        return getFluidAmountIn() >= water && getEnergy() >= energy;
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineDecomposerBlock.POWERED, state), 3);
    }

    public boolean getState() {
        return getBlockState().getValue(MachineDecomposerBlock.POWERED);
    }

    public int getFluidAmountNeeded() {
        return NEEDED_FLUID;
    }

    public void setFluidIn(FluidStack stack) {
        FLUID_TANK_IN.setFluid(stack);
    }

    public FluidStack getFluidIn() {
        return FLUID_TANK_IN.getFluid();
    }

    public int getFluidCapacityIn() {
        return FLUID_TANK_IN.getCapacity();
    }

    public int getFluidSpaceIn() {
        return FLUID_TANK_IN.getSpace();
    }

    public int getFluidAmountIn() {
        return FLUID_TANK_IN.getFluidAmount();
    }

    public int getNeededEnergy() {
        return NEEDED_ENERGY;
    }

    public int getRecipeTime() {
        return recipeTime;
    }

    public void setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
    }

    public int getMaxRecipeTime() {
        return maxRecipeTime;
    }

    public void setMaxRecipeTime(int maxRecipeTime) {
        this.maxRecipeTime = maxRecipeTime;
    }

    @Override
    public int getRedstoneMode() {
        return redstoneMode;
    }

    @Override
    public void setRedstoneMode(int redstoneMode) {
        this.redstoneMode = redstoneMode;
    }

    @Override
    public void setPowerable(boolean powerable) {
        this.powerable = powerable;
    }

    @Override
    public boolean isPowerable() {
        return powerable;
    }

    @Override
    public void setEnergy(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }

    public int getEnergy() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    public int getCapacity() {
        return ENERGY_STORAGE.getMaxEnergyStored();
    }

    public int getMaxInput() {
        return MAX_TRANSFER;
    }

    public boolean supportsEnergy() {
        return getEnergyCapacity() > 0;
    }

    public boolean isInputDump() {
        return inputDump;
    }

    public void setInputDump(boolean inputDump) {
        this.inputDump = inputDump;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (getLevel().isClientSide && net.getDirection() == PacketFlow.CLIENTBOUND) handleUpdateTag(pkt.getTag());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setWaterIn(tag.getInt("WaterIn"));
        setRedstoneMode(tag.getInt("RedstoneMode"));
        setInputDump(tag.getBoolean("InputDump"));
        setPowerable(tag.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
        setEnergy(nbt.getInt("Energy"));
        setRecipeTime(nbt.getInt("RecipeTime"));
        setMaxRecipeTime(nbt.getInt("MaxRecipeTime"));
        setRedstoneMode(nbt.getInt("RedstoneMode"));
        setInputDump(nbt.getBoolean("InputDump"));
        setPowerable(nbt.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineFluidEvaporatorContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) return true;
        }
        return false;
    }

    @NotNull
    @Override
    public ItemStack getItem(int index) {
        if (index < 0 || index >= items.size()) {
            return ItemStack.EMPTY;
        }
        return items.get(index);
    }

    @NotNull
    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(items, index, count);
    }

    @NotNull
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(items, index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        items.set(index, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clearContent() {
        items.clear();
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
        return PRODUCTION;
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

    @Override
    public void setCapacity(int capacity) {

    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return switch (pSide) {
            case NORTH, EAST, SOUTH, WEST -> new int[]{1};
            default -> new int[]{};
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection;
    }
}
