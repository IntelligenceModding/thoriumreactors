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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineCrystallizerBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineCrystallizerContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.recipe.CrystallizingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

import java.util.List;

public class MachineCrystallizerBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = MAX_POWER / 2;
    public static final int MAX_FLUID_IN = 6000;
    public static final int MAX_FLUID_TRANSFER = 100;
    public static final int NEEDED_ENERGY = 54;
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidInHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private final ModFluidTank FLUID_TANK_IN = new ModFluidTank(MAX_FLUID_IN, true, false, 0, FluidStack.EMPTY);
    public ItemStack outputItem = new ItemStack(Items.AIR);
    public FluidStack inputFluid = FluidStack.EMPTY;
    public NonNullList<ItemStack> items;
    int operationAfterTicks = 0;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    boolean inputDump;
    boolean powerable = true;
    int redstoneMode = 0;
    int recipeDefinedTicks;

    public MachineCrystallizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRYSTALLIZER_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidInHandler = LazyOptional.of(() -> FLUID_TANK_IN);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineCrystallizerBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineCrystallizerBlock.FACING).getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
            if (facing.getClockWise() == side) return lazyFluidInHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (facing.getCounterClockWise() == side) return itemHandler[side.get3DDataValue()].cast();
            return LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            this.energy = ENERGY_STORAGE.getEnergyStored();
        }
    };

    @Override
    public boolean canInputEnergy() {
        return true;
    }

    @Override
    public boolean canInputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineCrystallizerBlock.FACING).getOpposite() == direction;
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
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_CRYSTALIZER.get(), SoundSource.BLOCKS, 0.3f, 1f);
        }

        // Energy Input Slot
        items.get(1).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

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
        if (hasRecipeNeeds(NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(getRecipeDefinedTicks());
                setRecipeTime(getRecipeDefinedTicks());
                if (!getState()) setState(true);
            }
            ItemStack outputSlot = getItem(1);
            if (getEnergy() - getNeededEnergy() >= 0 && getFluidAmountIn() >= getFluidAmountNeeded() && outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize() && (outputSlot.isEmpty() || outputSlot.is(getOutputItem().getItem()))) {
                if (!getState()) setState(true);
                // Consumption of Energy, Fluids, Items etc
                setEnergy(getEnergy() - NEEDED_ENERGY);

                if (getOperationAfterTicks() != 0 && getRecipeTime() % getOperationAfterTicks() == 0) {
                    getFluidIn().shrink(getFluidAmountNeeded());
                }

                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setItem(1, new ItemStack(getOutputItem().getItem(), outputSlot.getCount() + 1));
                    setOutputItem(ItemStack.EMPTY);
                    setMaxRecipeTime(0);
                    setRecipeDefinedTicks(0);
                    setMaxRecipeTime(0);
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
        }
    }

    public boolean hasRecipeNeeds(int energy) {
        if (energy > getEnergy()) return false;
        List<CrystallizingRecipe> recipe = level.getRecipeManager().getAllRecipesFor(ModRecipes.CRYSTALLIZING_RECIPE_TYPE.get());
        for (CrystallizingRecipe decomposingRecipe : recipe) {
            if (decomposingRecipe.matchesFluid(getFluidIn()) && recipeTime == 0 && maxRecipeTime == 0) {
                setRecipeDefinedTicks(decomposingRecipe.getTicks());
                setInputFluid(decomposingRecipe.getFluidIngredient());
                setOutputItem(decomposingRecipe.getResultItem());
                setOperationAfterTicks(decomposingRecipe.getOperationAfterTicks());
                return true;
            } else if (recipeTime != 0 && maxRecipeTime != 0 && decomposingRecipe.matchesFluid(getFluidIn())) {
                return true;
            }
        }
        return false;
    }

    public boolean getState() {
        return getBlockState().getValue(MachineCrystallizerBlock.POWERED);
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineCrystallizerBlock.POWERED, state), 3);
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

    public int getEnergy() {
        return ENERGY_STORAGE.getEnergyStored();
    }

    @Override
    public void setEnergy(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
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

    public FluidStack getInputFluid() {
        return inputFluid;
    }

    public void setInputFluid(FluidStack inputFluid) {
        this.inputFluid = inputFluid;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public void setOutputItem(ItemStack outputItem) {
        this.outputItem = outputItem;
    }

    public void setOperationAfterTicks(int operationAfterTicks) {
        this.operationAfterTicks = operationAfterTicks;
    }

    public int getOperationAfterTicks() {
        return operationAfterTicks;
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
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("InputFluid", getInputFluid().writeToNBT(new CompoundTag()));
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
        setPowerable(tag.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(tag.getCompound("FluidIn"));
        setInputFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("InputFluid")));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putBoolean("InputDump", isInputDump());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidIn", FLUID_TANK_IN.writeToNBT(new CompoundTag()));
        nbt.put("InputFluid", getInputFluid().writeToNBT(new CompoundTag()));
        nbt.put("OutputItem", getOutputItem().save(new CompoundTag()));
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
        setOperationAfterTicks(nbt.getInt("OperationAfterTicks"));
        setInputDump(nbt.getBoolean("InputDump"));
        setPowerable(nbt.getBoolean("Powerable"));
        FLUID_TANK_IN.readFromNBT(nbt.getCompound("FluidIn"));
        setInputFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("InputFluid")));
        setOutputItem(ItemStack.of(nbt.getCompound("OutputItem")));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineCrystallizerContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
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
    public int @NotNull [] getSlotsForFace(Direction pSide) {
        return switch (pSide) {
            case NORTH, EAST, SOUTH, WEST -> new int[]{1};
            default -> new int[]{};
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection;
    }


}
