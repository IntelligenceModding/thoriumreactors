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
import net.minecraft.world.SimpleContainer;
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
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineSaltMelterBlock;
import unhappycodings.thoriumreactors.common.blockentity.ModFluidTank;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineSaltMelterContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.recipe.SaltSmeltingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

import java.util.List;

public class MachineSaltMelterBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, MenuProvider, IEnergyCapable {
    public static final int MAX_POWER = 100000;
    public static final int MAX_TRANSFER = MAX_POWER / 100;
    public static final int MAX_FLUID_OUT = 10000;
    public static final int MAX_FLUID_TRANSFER = 100;
    public static final int NEEDED_ENERGY = 233;
    public static final int WORKING_DEGREES = 659;
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<FluidTank> lazyFluidOutHandler = LazyOptional.empty();
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private final ModFluidTank FLUID_TANK_OUT = new ModFluidTank(MAX_FLUID_OUT, false, true, -1, FluidStack.EMPTY);
    public ItemStack inputItem1 = new ItemStack(Items.AIR);
    public ItemStack inputItem2 = new ItemStack(Items.AIR);
    public ItemStack inputItem3 = new ItemStack(Items.AIR);
    public FluidStack outputFluid = FluidStack.EMPTY;
    public NonNullList<ItemStack> items;
    int operationAfterTicks = 0;
    int degree = 25;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    boolean powerable = true;
    int redstoneMode = 0;
    int recipeDefinedTicks;
    int recipeDefinedTemperature;

    public MachineSaltMelterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SALT_MELTER_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        this.lazyFluidOutHandler = LazyOptional.of(() -> FLUID_TANK_OUT);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && this.getBlockState().getValue(MachineSaltMelterBlock.FACING).getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER && side != null)
            if (side == Direction.DOWN) return lazyFluidOutHandler.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (side == facing.getCounterClockWise() || side == facing.getClockWise() || side == facing)
                return itemHandler[side.get3DDataValue()].cast();
            return LazyOptional.empty();
        }
        return super.getCapability(cap, side);
    }    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(MAX_POWER, MAX_TRANSFER) {
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
        return this.getBlockState().getValue(MachineSaltMelterBlock.FACING).getOpposite() == direction;
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
        // Energy Input Slot
        items.get(2).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

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
        } else {
            if (getState())
                setState(false);
            if (getDegree() > 25) setDegree(getDegree() - getDegreeHeating(true));
        }

    }

    public void operate() {
        if (hasRecipeNeeds(NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(getRecipeDefinedTicks());
                setRecipeTime(getRecipeDefinedTicks());
                items.get(0).shrink(1);
                items.get(1).shrink(1);
                items.get(2).shrink(1);
                if (!getState()) setState(true);
            }
            if (getFluidAmountOut() + getFluidAmountNeeded() <= getMaxFluidOut()) {
                if (getDegree() >= getWorkingDegree()) {
                    // Play Sounds
                    if (getState() && getRecipeTime() % 20 == 0) {
                        this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_SALT_MELTER.get(), SoundSource.BLOCKS, 0.3f, 1f);
                    }

                    if (!getState()) setState(true);
                    // Consumption of Energy, Fluids, Items etc
                    setEnergy(getEnergy() - getNeededEnergy());

                    if (getOperationAfterTicks() != 0 && getRecipeTime() % getOperationAfterTicks() == 0) {
                        if (getFluidOut().isEmpty())
                            setFluidOut(new FluidStack(getOutputFluid(), 0));
                        getFluidOut().grow(getFluidAmountNeeded());
                    }

                    setRecipeTime(getRecipeTime() - 1);
                    if (getRecipeTime() == 0) {
                        setMaxRecipeTime(0);
                        setRecipeDefinedTicks(0);
                        setOperationAfterTicks(0);
                        setOutputFluid(FluidStack.EMPTY);
                        setInputItem1(ItemStack.EMPTY);
                        setInputItem2(ItemStack.EMPTY);
                        setInputItem3(ItemStack.EMPTY);
                        setWorkingDegree(0);
                    }
                } else {
                    setDegree(getDegree() + getDegreeHeating(false));
                }
            } else {
                if (getState())
                    setState(false);
            }
        } else {
            if (getDegree() > 25) {
                setDegree(getDegree() - getDegreeHeating(true));
            }
            if (getState())
                setState(false);
        }
    }

    public int getDegreeHeating(boolean cooling) {
        if (getDegree() < 219) {
            return cooling ? 1 : 3;
        } else if (getDegree() < 434) {
            return 2;
        } else {
            return cooling ? 3 : 1;
        }
    }

    public boolean hasRecipeNeeds(int energy) {
        if (energy > getEnergy()) return false;
        SimpleContainer container = new SimpleContainer(getItem(0).copy(), getItem(1).copy(), getItem(2).copy());
        List<SaltSmeltingRecipe> recipe = level.getRecipeManager().getAllRecipesFor(ModRecipes.SALT_SMELTING_RECIPE_TYPE.get());
        for (SaltSmeltingRecipe saltSmeltingRecipe : recipe) {
            if (saltSmeltingRecipe.matchesAll(container, getFluidIn(), level) && recipeTime == 0 && maxRecipeTime == 0) {
                setRecipeDefinedTicks(saltSmeltingRecipe.getTicks());
                setOutputFluid(saltSmeltingRecipe.getResultFluid());
                setInputItem1(saltSmeltingRecipe.getInputSlot1().getItems()[0]);
                setInputItem2(saltSmeltingRecipe.getInputSlot2().getItems()[0]);
                setInputItem3(saltSmeltingRecipe.getInputSlot3().getItems()[0]);
                setOperationAfterTicks(saltSmeltingRecipe.getOperationAfterTicks());
                setWorkingDegree(saltSmeltingRecipe.getTemperature());
                return true;
            } else if (recipeTime != 0 && maxRecipeTime != 0 && saltSmeltingRecipe.matchesFluid(getFluidIn())) {
                return true;
            }
        }
        return false;
    }

    public boolean getState() {
        return getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.POWERED);
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineElectrolyticSaltSeparatorBlock.POWERED, state), 3);
    }

    public int getWorkingDegree() {
        return recipeDefinedTemperature;
    }

    public void setWorkingDegree(int temp) {
        recipeDefinedTemperature = temp;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getFluidAmountNeeded() {
        return outputFluid.getAmount();
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

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public void setOutputFluid(FluidStack inputFluid) {
        this.outputFluid = inputFluid;
    }

    public ItemStack getInputItem1() {
        return inputItem1;
    }

    public void setInputItem1(ItemStack inputItem1) {
        this.inputItem1 = inputItem1;
    }

    public ItemStack getInputItem2() {
        return inputItem2;
    }

    public void setInputItem2(ItemStack inputItem2) {
        this.inputItem2 = inputItem2;
    }

    public ItemStack getInputItem3() {
        return inputItem3;
    }

    public void setInputItem3(ItemStack inputItem3) {
        this.inputItem3 = inputItem3;
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
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("Temp", getDegree());
        nbt.putInt("WorkingTemp", getWorkingDegree());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.put("OutputFluid", getOutputFluid().writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setRedstoneMode(tag.getInt("RedstoneMode"));
        setDegree(tag.getInt("Temp"));
        setWorkingDegree(tag.getInt("WorkingTemp"));
        setOperationAfterTicks(tag.getInt("OperationAfterTicks"));
        setPowerable(tag.getBoolean("Powerable"));
        FLUID_TANK_OUT.readFromNBT(tag.getCompound("FluidOut"));
        setOutputFluid(FluidStack.loadFluidStackFromNBT(tag.getCompound("OutputFluid")));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("Temp", getDegree());
        nbt.putInt("WorkingTemp", getWorkingDegree());
        nbt.putInt("OperationAfterTicks", getOperationAfterTicks());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("FluidOut", FLUID_TANK_OUT.writeToNBT(new CompoundTag()));
        nbt.put("InputItem1", getInputItem1().save(new CompoundTag()));
        nbt.put("InputItem2", getInputItem2().save(new CompoundTag()));
        nbt.put("InputItem3", getInputItem3().save(new CompoundTag()));
        nbt.put("OutputFluid", getOutputFluid().writeToNBT(new CompoundTag()));
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
        setDegree(nbt.getInt("Temp"));
        setWorkingDegree(nbt.getInt("WorkingTemp"));
        setOperationAfterTicks(nbt.getInt("OperationAfterTicks"));
        setPowerable(nbt.getBoolean("Powerable"));
        FLUID_TANK_OUT.readFromNBT(nbt.getCompound("FluidOut"));
        setInputItem1(ItemStack.of(nbt.getCompound("InputItem1")));
        setInputItem2(ItemStack.of(nbt.getCompound("InputItem2")));
        setInputItem3(ItemStack.of(nbt.getCompound("InputItem3")));
        setOutputFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompound("OutputFluid")));
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new MachineSaltMelterContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
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
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getClockWise())
            return new int[]{2};
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getCounterClockWise())
            return new int[]{0};
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING)) return new int[]{1};
        return new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection || facing.getClockWise() == pDirection || facing == pDirection;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    ;




}
