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
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineGeneratorBlock;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineElectrolyticSaltSeparatorContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModItems;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

public class MachineElectrolyticSaltSeparatorBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, IEnergyCapable {
    public static final int MAX_WATER_IN = 6000;
    public static final int MAX_WATER_OUT = 3000;
    public static final int MAX_WATER_TRANSFER = 100;
    public static final int MAX_POWER = 25000;
    public static final int MAX_TRANSFER = 170;
    public static final int MAX_RECIPE_TIME = 200;
    public static final int NEEDED_ENERGY = 40;
    public static final int NEEDED_WATER = 4;

    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidOutHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler;
    public NonNullList<ItemStack> items;
    boolean inputDump;
    boolean outputDump;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    int waterIn = 0;
    int waterOut = 0;

    boolean powerable = true;
    int redstoneMode = 0;

    public MachineElectrolyticSaltSeparatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ELECTROLYTIC_SALT_SEPARATOR_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        itemHandler = SidedInvWrapper.create(this, Direction.values());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
        this.lazyFluidOutHandler = LazyOptional.of(() -> FLUID_TANK_OUT);
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            this.energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    private final FluidTank FLUID_TANK_IN = new FluidTank(MAX_WATER_IN) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }

        @Override
        public @NotNull FluidStack getFluid() {
            return new FluidStack(Fluids.WATER, waterIn);
        }

        @Override
        public int getFluidAmount() {
            return waterIn;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!resource.getFluid().isSame(Fluids.WATER)) return 0;
            int canFill = getMaxWaterIn() - getWaterIn();
            int filled = Math.min(canFill, MAX_WATER_TRANSFER); // canFill > MAX_WATER_TRANSFER ? MAX_WATER_TRANSFER : canFill

            if (action == FluidAction.EXECUTE) if (items.get(0).hasCraftingRemainingItem())
                items.get(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
                    setWaterIn(getWaterIn() + storage.getTankCapacity(0));
                });
            else setWaterIn(getWaterIn() + Math.min(filled, resource.getAmount()));
            return Math.min(filled, resource.getAmount());
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }

    };

    private final FluidTank FLUID_TANK_OUT = new FluidTank(MAX_WATER_OUT) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }

        @Override
        public @NotNull FluidStack getFluid() {
            return new FluidStack(Fluids.WATER, waterOut);
        }

        @Override
        public int getFluidAmount() {
            return waterOut;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            if (!resource.getFluid().isSame(Fluids.WATER)) return FluidStack.EMPTY;
            int canDrain = getWaterOut();
            int filled = Math.min(canDrain, MAX_WATER_TRANSFER);
            if (action == FluidAction.EXECUTE) setWaterOut(getWaterOut() - filled);
            return new FluidStack(Fluids.WATER, filled);
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            int canDrain = getWaterOut() - MAX_WATER_TRANSFER > 0 ? MAX_WATER_TRANSFER : getWaterOut();
            return new FluidStack(Fluids.WATER, canDrain);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY.getAmount();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }

    };

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && facing.getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            if (facing.getCounterClockWise() == side) return lazyFluidOutHandler.cast();
            if (facing.getClockWise() == side) return lazyFluidInHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (side == Direction.DOWN) return itemHandler[side.get3DDataValue()].cast();
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
        return this.getBlockState().getValue(MachineGeneratorBlock.FACING).getOpposite() == direction;
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
        if (getState() && getRecipeTime() % 15 == 0) {
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_ELECTROLYTIC_SALT_SEPARATOR.get(), SoundSource.BLOCKS, 0.125f, 1.3f);
        }

        // Fluid Input Slot
        items.get(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
            if (storage.getFluidInTank(0).getFluid() == Fluids.WATER && storage.getTankCapacity(0) > 0) {
                fillTankFromItemInput(storage, FLUID_TANK_IN, items.get(0).hasCraftingRemainingItem() ? storage.getTankCapacity(0) : getMaxWaterTransfer(), items.get(0).hasCraftingRemainingItem());
            }
        });

        // Fluid Output Slot
        items.get(2).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(storage -> {
            if ((storage.getFluidInTank(0).getFluid() == Fluids.WATER || storage.getFluidInTank(0).getFluid() == Fluids.EMPTY) && storage.getTankCapacity(0) > 0) {
                tryDrainTank(storage, FLUID_TANK_OUT, getMaxWaterTransfer());
            }
        });

        // Energy Input Slot
        items.get(3).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

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

        if (isOutputDump() && getWaterOut() > 0) setWaterOut(Math.max(getWaterOut() - MAX_WATER_TRANSFER, 0));
        if (isInputDump() && getWaterIn() > 0) setWaterIn(Math.max(getWaterIn() - MAX_WATER_TRANSFER, 0));
    }

    public void operate() {
        if (hasRecipeNeeds(NEEDED_WATER, NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(MAX_RECIPE_TIME);
                setRecipeTime(MAX_RECIPE_TIME);
                if (!getState()) setState(true);
            }
            ItemStack outputSlot = getItem(1);
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0 && getWaterOut() + (NEEDED_WATER) / 2 <= getMaxWaterOut() && outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize() && (outputSlot.isEmpty() || outputSlot.is(ModItems.POTASSIUM.get()))) {
                if (!getState()) setState(true);
                setEnergy(getEnergy() - NEEDED_ENERGY);
                setWaterIn(getWaterIn() - NEEDED_WATER);
                setWaterOut(getWaterOut() + NEEDED_WATER / 2);
                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setMaxRecipeTime(0);
                    setItem(1, new ItemStack(ModItems.POTASSIUM.get(), outputSlot.getCount() + 1));
                }
            } else {
                if (getState()) setState(false);
            }
        } else {
            if (getState()) setState(false);
        }
    }

    public boolean hasRecipeNeeds(int water, int energy) {
        return getWaterIn() >= water && getEnergy() >= energy;
    }

    public void tryDrainTank(IFluidHandlerItem other, FluidTank fluidTank, int transferRate) {
        FluidStack toSend = fluidTank.drain(new FluidStack(Fluids.WATER, transferRate), IFluidHandler.FluidAction.SIMULATE);
        int sent = other.fill(toSend, IFluidHandler.FluidAction.EXECUTE);
        if (sent > 0) fluidTank.drain(new FluidStack(Fluids.WATER, sent), IFluidHandler.FluidAction.EXECUTE);
    }

    public void fillTankFromItemInput(IFluidHandlerItem other, FluidTank fluidTank, int transferRate, boolean hasCraftingRemainder) {
        int toSend = fluidTank.fill(new FluidStack(Fluids.WATER, transferRate), IFluidHandler.FluidAction.SIMULATE);
        FluidStack sent = other.drain(toSend, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) {
            if (fluidTank.getFluidInTank(0).getAmount() + transferRate > fluidTank.getTankCapacity(0)) return;
            sent = new FluidStack(Fluids.WATER, transferRate);
        }
        if (sent.getAmount() > 0) fluidTank.fill(sent, IFluidHandler.FluidAction.EXECUTE);
        if (hasCraftingRemainder) items.set(0, new ItemStack(items.get(0).getItem().getCraftingRemainingItem()));
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineElectrolyticSaltSeparatorBlock.POWERED, state), 3);
    }

    public boolean getState() {
        return getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.POWERED);
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

    public int getNeededEnergy() {
        return NEEDED_ENERGY;
    }

    public int getFluidAmountNeeded() {
        return NEEDED_WATER;
    }

    public int getMaxWaterTransfer() {
        return MAX_WATER_TRANSFER;
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

    public int getWaterIn() {
        return waterIn;
    }

    public void setWaterIn(int waterIn) {
        this.waterIn = waterIn;
    }

    public int getWaterOut() {
        return waterOut;
    }

    public void setWaterOut(int waterOut) {
        this.waterOut = waterOut;
    }

    public int getMaxWaterIn() {
        return MAX_WATER_IN;
    }

    public int getMaxWaterOut() {
        return MAX_WATER_OUT;
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

    @Override
    public boolean isInputDump() {
        return inputDump;
    }

    @Override
    public void setInputDump(boolean inputDump) {
        this.inputDump = inputDump;
    }

    @Override
    public boolean isOutputDump() {
        return outputDump;
    }

    @Override
    public void setOutputDump(boolean outputDump) {
        this.outputDump = outputDump;
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
        nbt.putInt("WaterIn", getWaterIn());
        nbt.putInt("WaterOut", getWaterOut());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("OutputDump", isOutputDump());
        nbt.putBoolean("Powerable", isPowerable());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setWaterIn(tag.getInt("WaterIn"));
        setWaterOut(tag.getInt("WaterOut"));
        setRedstoneMode(tag.getInt("RedstoneMode"));
        setInputDump(tag.getBoolean("InputDump"));
        setOutputDump(tag.getBoolean("OutputDump"));
        setPowerable(tag.getBoolean("Powerable"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("WaterIn", getWaterIn());
        nbt.putInt("WaterOut", getWaterOut());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("OutputDump", isOutputDump());
        nbt.putBoolean("Powerable", isPowerable());
        ContainerHelper.saveAllItems(nbt, this.items, true);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        ContainerHelper.loadAllItems(nbt, this.items);
        setEnergy(nbt.getInt("Energy"));
        setRecipeTime(nbt.getInt("RecipeTime"));
        setMaxRecipeTime(nbt.getInt("MaxRecipeTime"));
        setWaterIn(nbt.getInt("WaterIn"));
        setWaterOut(nbt.getInt("WaterOut"));
        setRedstoneMode(nbt.getInt("RedstoneMode"));
        setInputDump(nbt.getBoolean("InputDump"));
        setOutputDump(nbt.getBoolean("OutputDump"));
        setPowerable(nbt.getBoolean("Powerable"));
    }

    @Override
    public int getContainerSize() {
        return 4;
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

    @Override
    public void setCapacity(int capacity) {
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) return new int[]{1};
        return new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return pDirection == Direction.DOWN;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MachineElectrolyticSaltSeparatorContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }
}
