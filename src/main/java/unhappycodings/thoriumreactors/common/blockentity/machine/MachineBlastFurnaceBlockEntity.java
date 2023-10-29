package unhappycodings.thoriumreactors.common.blockentity.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineBlastFurnaceBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineBlastFurnaceContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.recipe.BlastingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

import java.util.List;

public class MachineBlastFurnaceBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, IEnergyCapable {
    public static final int MAX_POWER = 25000;
    public static final int MAX_TRANSFER = MAX_POWER / 100;
    public static final int NEEDED_ENERGY = 189;
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    public ItemStack outputItem = new ItemStack(Items.AIR);
    public ItemStack secondaryOutputItem = new ItemStack(Items.AIR);
    public int secondaryChance = 0;
    public NonNullList<ItemStack> items;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    boolean powerable = true;
    int redstoneMode = 0;
    int degree = 25;
    int maxFuel = 1;
    int fuel = 0;
    int recipeDefinedTemperature;
    int recipeDefinedTicks;

    public MachineBlastFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BLAST_FURNACE_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineBlastFurnaceBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && facing.getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (facing.getClockWise() == side || facing.getCounterClockWise() == side || side == Direction.DOWN)
                return itemHandler[side.get3DDataValue()].cast();
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
    public boolean canInputEnergy(Direction direction) {
        return this.getBlockState().getValue(MachineBlastFurnaceBlock.FACING).getOpposite() == direction;
    }

    @Override
    public boolean canOutputEnergy() {
        return false;
    }

    public void tick() {
        // Energy Input Slot
        items.get(4).getCapability(ForgeCapabilities.ENERGY).ifPresent(storage -> EnergyUtil.tryDischargeItem(storage, ENERGY_STORAGE, getMaxInput()));

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
            if (getState()) setState(false);
            if (getDegree() > 25) setDegree(getDegree() - getDegreeHeating(true));
        }

    }

    public void operate() {
        if (hasRecipeNeeds(NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(getRecipeDefinedTicks());
                setRecipeTime(getRecipeDefinedTicks() - 1);
                setMaxFuel(ForgeHooks.getBurnTime(items.get(0), null));
                setFuel(getMaxFuel());
                getItem(0).shrink(1);
                getItem(1).shrink(1);
                if (!getState()) setState(true);
            }

            ItemStack outputSlot = getItem(2);
            ItemStack secondaryOutputSlot = getItem(3);
            if (getRecipeTime() > 0 && getMaxRecipeTime() > 0 && outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize()&& secondaryOutputSlot.getCount() + 1 <= secondaryOutputSlot.getMaxStackSize()) {
                if (getDegree() >= getWorkingDegree()) {

                    // Play Sounds
                    if (getState() && getRecipeTime() % 10 == 0) {
                        this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_BLAST_FURNACE.get(), SoundSource.BLOCKS, 0.55f, 2f);
                    }

                    if (!getState()) setState(true);
                    // Consumption of Energy, Fluids, Items etc
                    setEnergy(getEnergy() - NEEDED_ENERGY);
                    setRecipeTime(getRecipeTime() - 1);
                    if (getRecipeTime() == 0) {
                        setItem(2, new ItemStack(getOutputItem().getItem(), outputSlot.getCount() + 1));
                        if (!getSecondaryOutputItem().isEmpty() && getSecondaryChance() <= Math.random() * 100f) {
                            setItem(3, new ItemStack(getSecondaryOutputItem().getItem(), secondaryOutputSlot.getCount() + 1));
                        }
                        setMaxRecipeTime(0);
                        setRecipeDefinedTicks(0);
                        setMaxRecipeTime(0);
                        setWorkingDegree(0);
                    }
                } else {
                    setDegree(getDegree() + getDegreeHeating(false));
                }
            } else {
                if (getState()) setState(false);
            }
        } else {
            if (getDegree() > 25) {
                setDegree(getDegree() - getDegreeHeating(true));
            }
            if (getState()) setState(false);
            if (getRecipeTime() == 0 || (getItem(0).isEmpty() && getItem(1).isEmpty())) {
                setMaxRecipeTime(0);
                setRecipeTime(0);
                setSecondaryChance(0);
                setSecondaryOutputItem(ItemStack.EMPTY);
                setOutputItem(ItemStack.EMPTY);
                setRecipeDefinedTicks(0);
            }
        }
    }

    public int getDegreeHeating(boolean cooling) {
        if (getDegree() < 350) {
            return cooling ? 1 : 3;
        } else if (getDegree() < 840) {
            return 2;
        } else {
            return cooling ? 3 : 1;
        }
    }

    public boolean hasRecipeNeeds(int energy) {
        if (energy > getEnergy()) return false;
        ItemStack outputSlot = getItem(2);
        SimpleContainer container = new SimpleContainer(getItem(0), getItem(1));
        List<BlastingRecipe> recipe = level.getRecipeManager().getAllRecipesFor(ModRecipes.BLASTING_RECIPE_TYPE.get());
        for (BlastingRecipe blastingRecipe : recipe) {
            if (blastingRecipe.matches(container, getLevel()) && getOutputItem().isEmpty() && recipeTime == 0 && maxRecipeTime == 0) {
                setOutputItem(blastingRecipe.getResultItem(null));
                setSecondaryOutputItem(blastingRecipe.getSecondaryResultItem());
                setSecondaryChance(blastingRecipe.getSecondaryChance());
                setRecipeDefinedTicks(blastingRecipe.getTicks());
                setWorkingDegree(blastingRecipe.getTemperature());
                return outputSlot.is(Items.AIR) || outputSlot.is(getOutputItem().getItem());
            } else if (recipeTime != 0 && maxRecipeTime != 0 && !getOutputItem().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean getState() {
        return getBlockState().getValue(MachineBlastFurnaceBlock.POWERED);
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

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public void setMaxFuel(int maxFuel) {
        this.maxFuel = maxFuel;
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineBlastFurnaceBlock.POWERED, state), 3);
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

    @Override
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

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public void setOutputItem(ItemStack outputItem) {
        this.outputItem = outputItem;
    }

    public void setSecondaryOutputItem(ItemStack secondaryOutputItem) {
        this.secondaryOutputItem = secondaryOutputItem;
    }

    public ItemStack getSecondaryOutputItem() {
        return secondaryOutputItem;
    }

    public void setSecondaryChance(int secondaryChance) {
        this.secondaryChance = secondaryChance;
    }

    public int getSecondaryChance() {
        return secondaryChance;
    }

    public void setRecipeDefinedTicks(int recipeDefinedTicks) {
        this.recipeDefinedTicks = recipeDefinedTicks;
    }

    public int getRecipeDefinedTicks() {
        return recipeDefinedTicks;
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("Temp", getDegree());
        nbt.putInt("WorkingTemp", getWorkingDegree());
        nbt.putInt("MaxFuel", getMaxFuel());
        nbt.putInt("Fuel", getFuel());
        nbt.putBoolean("Powerable", isPowerable());
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
        setMaxFuel(tag.getInt("MaxFuel"));
        setFuel(tag.getInt("Fuel"));
        setPowerable(tag.getBoolean("Powerable"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putInt("Temp", getDegree());
        nbt.putInt("WorkingTemp", getWorkingDegree());
        nbt.putInt("MaxFuel", getMaxFuel());
        nbt.putInt("Fuel", getFuel());
        nbt.putBoolean("Powerable", isPowerable());
        nbt.put("OutputItem", getOutputItem().save(new CompoundTag()));
        nbt.put("SecondaryOutputItem", getSecondaryOutputItem().save(new CompoundTag()));
        nbt.putInt("SecondaryChance", getSecondaryChance());
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
        setMaxFuel(nbt.getInt("MaxFuel"));
        setFuel(nbt.getInt("Fuel"));
        setPowerable(nbt.getBoolean("Powerable"));
        setOutputItem(ItemStack.of(nbt.getCompound("OutputItem")));
        setSecondaryOutputItem(ItemStack.of(nbt.getCompound("SecondaryOutputItem")));
        setSecondaryChance(nbt.getInt("SecondaryChance"));
    }

    @Override
    public int getContainerSize() {
        return 5;
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
            return new int[]{1};
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getCounterClockWise())
            return new int[]{2};
        if (pSide == Direction.DOWN)
            return new int[]{0};
        return new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getClockWise() == pDirection || pDirection == Direction.DOWN;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MachineBlastFurnaceContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

}
