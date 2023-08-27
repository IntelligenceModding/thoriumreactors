package unhappycodings.thoriumreactors.common.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineFluidCentrifugeBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineFluidCentrifugeContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.recipe.CentrifugingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

import java.util.List;

public class MachineFluidCentrifugeBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = MAX_POWER / 100;
    public static final int MAX_FLUID_IN = 10000;
    public static final int MAX_FLUID_OUT = 10000;
    public static final int MAX_FLUID_TRANSFER = 100;
    public static final int NEEDED_ENERGY = 46;
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidOutHandler = LazyOptional.empty();
    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(MAX_FLUID_IN, true, false, 0, FluidStack.EMPTY);
    private final ModFluidTank FLUID_TANK_OUT = new ModFluidTank(MAX_FLUID_OUT, false, true, -1, FluidStack.EMPTY);
    public FluidStack inputFluid = FluidStack.EMPTY;
    public FluidStack outputFluid = FluidStack.EMPTY;
    public NonNullList<ItemStack> items;
    int operationAfterTicks = 0;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    boolean inputDump;
    boolean outputDump;
    boolean powerable = true;
    int redstoneMode = 0;
    int recipeDefinedTicks;

    public MachineFluidCentrifugeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FLUID_CENTRIFUGE_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineFluidCentrifugeBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineFluidCentrifugeBlock.FACING).getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            if (facing.getCounterClockWise() == side) return lazyFluidOutHandler.cast();
            if (facing.getClockWise() == side) return lazyFluidInHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public boolean canInputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineFluidCentrifugeBlock.FACING).getOpposite() == direction;
    }

    @Override
    public boolean canOutputEnergy() {
        return false;
    }

    public void tick() {
        // Play Sounds
        if (getState() && getRecipeTime() % 20 == 0) {
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_FLUID_CENTRIFUGE.get(), SoundSource.BLOCKS, 0.5f, 1f);
        }

        // Energy Input Slot
        items.get(0).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

        if (isPowerable() && isSpaceAbove()) {
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

    }

    public void operate() {
        if (hasRecipeNeeds(getNeededEnergy())) {
            if (getRecipeTime() == 0) {
                setMaxRecipeTime(getRecipeDefinedTicks());
                setRecipeTime(getRecipeDefinedTicks());
                getFluidIn().shrink(getFluidAmountNeeded());
                if (!getState()) setState(true);
            }
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0 && getEnergy() - getNeededEnergy() >= 0 && getFluidAmountIn() >= getFluidAmountNeeded() && getFluidAmountOut() + getFluidAmountNeeded() <= getFluidCapacityOut()) {
                if (!getState()) setState(true);
                // Consumption of Energy, Fluids, Items etc
                setEnergy(getEnergy() - NEEDED_ENERGY);

                setRecipeTime(getRecipeTime() - 1);
                if ((getOperationAfterTicks() != 0 && getRecipeTime() % getOperationAfterTicks() == 0)) {
                    if (getOutputFluid().isEmpty()) {
                        setRecipeTime(getMaxRecipeTime());
                        return;
                    }
                    if (getFluidOut().isEmpty())
                        setFluidOut(new FluidStack(getOutputFluid(), 0));
                    getFluidOut().grow(getFluidAmountNeeded());
                }

                if (getRecipeTime() == 0) {
                    setMaxRecipeTime(0);
                    setInputFluid(FluidStack.EMPTY);
                    setOutputFluid(FluidStack.EMPTY);
                    setMaxRecipeTime(0);
                    setRecipeDefinedTicks(0);
                    setOperationAfterTicks(0);
                }

                if (isOutputDump() && getFluidAmountOut() > 0)
                    getFluidOut().shrink(getFluidAmountOut() - MAX_FLUID_TRANSFER < MAX_FLUID_TRANSFER ? getFluidAmountOut() : MAX_FLUID_TRANSFER);
                if (isInputDump() && getFluidAmountIn() > 0)
                    getFluidIn().shrink(getFluidAmountIn() - MAX_FLUID_TRANSFER < MAX_FLUID_TRANSFER ? getFluidAmountIn() : MAX_FLUID_TRANSFER);
            } else {
                if (getState())
                    setState(false);
            }
        } else {
            if (getState())
                setState(false);

            setInputFluid(FluidStack.EMPTY);
            setOutputFluid(FluidStack.EMPTY);
            setRecipeDefinedTicks(0);
            setOperationAfterTicks(0);
        }
    }

    public boolean hasRecipeNeeds(int energy) {
        if (energy > getEnergy()) return false;
        SimpleContainer container = new SimpleContainer(getItem(0).copy());
        List<CentrifugingRecipe> recipe = level.getRecipeManager().getAllRecipesFor(ModRecipes.CENTRIFUGING_RECIPE_TYPE.get());
        for (CentrifugingRecipe decomposingRecipe : recipe) {
            if (decomposingRecipe.matchesFluid(getFluidIn(), getFluidOut()) && recipeTime == 0 && maxRecipeTime == 0) {
                setRecipeDefinedTicks(decomposingRecipe.getTicks());
                setInputFluid(decomposingRecipe.getFluidIngredient());
                setOutputFluid(decomposingRecipe.getResultFluid());
                setOperationAfterTicks(decomposingRecipe.getOperationAfterTicks());
                return true;
            } else if (recipeTime != 0 && maxRecipeTime != 0 && decomposingRecipe.matchesFluid(getFluidIn(), getFluidOut())) {
                return true;
            }
        }
        return false;
    }

    public boolean getState() {
        return getBlockState().getValue(MachineFluidCentrifugeBlock.POWERED);
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineFluidCentrifugeBlock.POWERED, state), 3);
    }

    @Override
    public FluidStack getFluidIn() {
        return FLUID_TANK_IN.getFluid();
    }

    @Override
    public void setFluidIn(FluidStack stack) {
        FLUID_TANK_IN.setFluid(stack);
    }

    @Override
    public int getFluidCapacityIn() {
        return FLUID_TANK_IN.getCapacity();
    }

    @Override
    public int getFluidSpaceIn() {
        return FLUID_TANK_IN.getSpace();
    }

    @Override
    public int getFluidAmountIn() {
        return FLUID_TANK_IN.getFluidAmount();
    }

    @Override
    public FluidStack getFluidOut() {
        return FLUID_TANK_OUT.getFluid();
    }

    @Override
    public void setFluidOut(FluidStack stack) {
        FLUID_TANK_OUT.setFluid(stack);
    }

    @Override
    public int getFluidCapacityOut() {
        return FLUID_TANK_OUT.getCapacity();
    }

    @Override
    public int getFluidSpaceOut() {
        return FLUID_TANK_OUT.getSpace();
    }

    @Override
    public int getFluidAmountOut() {
        return FLUID_TANK_OUT.getFluidAmount();
    }

    public int getMaxFluidTransfer() {
        return MAX_FLUID_TRANSFER;
    }

    public int getFluidAmountNeeded() {
        return inputFluid.getAmount();
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
    public boolean isPowerable() {
        return powerable;
    }

    @Override
    public void setPowerable(boolean powerable) {
        this.powerable = powerable;
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

    public int getMaxFluidOut() {
        return MAX_FLUID_OUT;
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

    public FluidStack getInputFluid() {
        return inputFluid;
    }

    public void setInputFluid(FluidStack inputFluid) {
        this.inputFluid = inputFluid;
    }

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public void setOperationAfterTicks(int operationAfterTicks) {
        this.operationAfterTicks = operationAfterTicks;
    }

    public int getOperationAfterTicks() {
        return operationAfterTicks;
    }

    public void setOutputFluid(FluidStack outputFluid) {
        this.outputFluid = outputFluid;
    }

    public void setRecipeDefinedTicks(int recipeDefinedTicks) {
        this.recipeDefinedTicks = recipeDefinedTicks;
    }

    public int getRecipeDefinedTicks() {
        return recipeDefinedTicks;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (getLevel().isClientSide && net.getDirection() == PacketFlow.CLIENTBOUND) handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("OutputDump", isOutputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.put("InputFluid", getInputFluid().writeToNBT(new CompoundTag()));
        nbt.put("OutputFluid", getOutputFluid().writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setRedstoneMode(tag.getInt("RedstoneMode"));
        setOperationAfterTicks(tag.getInt("OperationAfterTicks"));
        setInputDump(tag.getBoolean("InputDump"));
        setOutputDump(tag.getBoolean("OutputDump"));
        setPowerable(tag.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(tag.getCompound("FluidOut"));
        setInputFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("InputFluid")));
        setOutputFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("OutputFluid")));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("OutputDump", isOutputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.put("InputFluid", getInputFluid().writeToNBT(new CompoundTag()));
        nbt.put("OutputFluid", getOutputFluid().writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.items.clear();
        setEnergy(nbt.getInt("Energy"));
        setRecipeTime(nbt.getInt("RecipeTime"));
        setMaxRecipeTime(nbt.getInt("MaxRecipeTime"));
        setRedstoneMode(nbt.getInt("RedstoneMode"));
        setOperationAfterTicks(nbt.getInt("OperationAfterTicks"));
        setInputDump(nbt.getBoolean("InputDump"));
        setOutputDump(nbt.getBoolean("OutputDump"));
        setPowerable(nbt.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        FLUID_TANK_OUT.readFromNBT(nbt.getCompound("FluidOut"));
        setInputFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("InputFluid")));
        setOutputFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("OutputFluid")));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineFluidCentrifugeContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

    @Override
    public int getContainerSize() {
        return 1;
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
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN)
            return new int[]{0};
        return new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return pDirection == Direction.DOWN;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }


}
