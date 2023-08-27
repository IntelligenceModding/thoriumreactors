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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import unhappycodings.thoriumreactors.common.block.machine.MachineConcentratorBlock;
import unhappycodings.thoriumreactors.common.block.machine.MachineElectrolyticSaltSeparatorBlock;
import unhappycodings.thoriumreactors.common.blockentity.base.MachineContainerBlockEntity;
import unhappycodings.thoriumreactors.common.container.machine.MachineConcentratorContainer;
import unhappycodings.thoriumreactors.common.energy.IEnergyCapable;
import unhappycodings.thoriumreactors.common.energy.ModEnergyStorage;
import unhappycodings.thoriumreactors.common.recipe.ConcentratingRecipe;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;
import unhappycodings.thoriumreactors.common.registration.ModRecipes;
import unhappycodings.thoriumreactors.common.registration.ModSounds;
import unhappycodings.thoriumreactors.common.util.EnergyUtil;

import java.util.List;

public class MachineConcentratorBlockEntity extends MachineContainerBlockEntity implements WorldlyContainer, IEnergyCapable {
    public static final int MAX_POWER = 25000;
    public static final int MAX_TRANSFER = MAX_POWER / 100;
    public static final int NEEDED_ENERGY = 46;
    private final LazyOptional<? extends IItemHandler>[] itemHandler = SidedInvWrapper.create(this, Direction.values());
    private LazyOptional<ModEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    public ItemStack outputItem = new ItemStack(Items.AIR);
    public NonNullList<ItemStack> items;
    boolean powerable = true;
    int redstoneMode = 0;
    int recipeTime = 0;
    int maxRecipeTime = 0;
    int recipeDefinedTicks;

    public MachineConcentratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONCENTRATOR_BLOCK.get(), pPos, pBlockState);
        items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        Direction facing = this.getBlockState().getValue(MachineConcentratorBlock.FACING);
        if (cap == ForgeCapabilities.ENERGY && supportsEnergy() && side != null && facing.getOpposite() == side)
            return lazyEnergyHandler.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER && !isRemoved() && side != null) {
            if (facing.getClockWise() == side || facing.getCounterClockWise() == side)
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
        return this.getBlockState().getValue(MachineConcentratorBlock.FACING).getOpposite() == direction;
    }

    @Override
    public boolean canOutputEnergy() {
        return false;
    }

    public void tick() {
        // Play Sounds
        if (getState() && getRecipeTime() % 10 == 0) {
            this.level.playSound(null, getBlockPos(), ModSounds.MACHINE_FLUID_EVAPORATION.get(), SoundSource.BLOCKS, 0.125f, 2f);
        }

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
        } else if (getState()) {
            setState(false);
        }
    }

    public void operate() {
        if (hasRecipeNeeds(NEEDED_ENERGY)) {
            if (getMaxRecipeTime() == 0) {
                setMaxRecipeTime(getRecipeDefinedTicks());
                setRecipeTime(getRecipeDefinedTicks());
                getItem(0).shrink(1);
                if (!getState()) setState(true);
            }

            ItemStack outputSlot = getItem(1);
            if (outputSlot.getCount() + 1 <= outputSlot.getMaxStackSize()) {
                if (!getState()) setState(true);
                // Consumption of Energy, Fluids, Items etc
                setEnergy(getEnergy() - NEEDED_ENERGY);
                setRecipeTime(getRecipeTime() - 1);
                if (getRecipeTime() == 0) {
                    setItem(1, new ItemStack(getOutputItem().getItem(), outputSlot.getCount() + 1));
                    setRecipeDefinedTicks(0);
                    setMaxRecipeTime(0);
                    setRecipeTime(0);
                    setOutputItem(ItemStack.EMPTY);

                }
            } else {
                if (getState()) setState(false);
            }
        } else {
            if (getState()) setState(false);
        }
    }

    public boolean hasRecipeNeeds(int energy) {
        if (energy > getEnergy()) return false;
        ItemStack outputSlot = getItem(1);
        SimpleContainer container = new SimpleContainer(getItem(0));
        List<ConcentratingRecipe> recipe = level.getRecipeManager().getAllRecipesFor(ModRecipes.CONCENTRATING_RECIPE_TYPE.get());
        for (ConcentratingRecipe concentratingRecipe : recipe) {
            if (concentratingRecipe.matches(container, getLevel()) && getOutputItem().isEmpty() && recipeTime == 0 && maxRecipeTime == 0) {
                setOutputItem(concentratingRecipe.getResultItem());
                setRecipeDefinedTicks(concentratingRecipe.getTicks());
                return outputSlot.is(Items.AIR) || outputSlot.is(getOutputItem().getItem());
            } else if (recipeTime != 0 && maxRecipeTime != 0) {
                return true;
            }
        }

        return false;
    }

    public boolean getState() {
        return getBlockState().getValue(MachineConcentratorBlock.POWERED);
    }

    public void setState(boolean state) {
        level.setBlock(getBlockPos(), getBlockState().setValue(MachineConcentratorBlock.POWERED, state), 3);
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
        nbt.putBoolean("Powerable", isPowerable());
        return nbt;
    }

    @Override
    public void handleUpdateTag(final CompoundTag tag) {
        setEnergy(tag.getInt("Energy"));
        setRecipeTime(tag.getInt("RecipeTime"));
        setMaxRecipeTime(tag.getInt("MaxRecipeTime"));
        setRedstoneMode(tag.getInt("RedstoneMode"));
        setPowerable(tag.getBoolean("Powerable"));
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        nbt.putInt("Energy", getEnergy());
        nbt.putInt("RecipeTime", getRecipeTime());
        nbt.putInt("MaxRecipeTime", getMaxRecipeTime());
        nbt.putInt("RedstoneMode", getRedstoneMode());
        nbt.putBoolean("Powerable", isPowerable());
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
        setPowerable(nbt.getBoolean("Powerable"));
        setOutputItem(ItemStack.of(nbt.getCompound("OutputItem")));
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

    @NotNull
    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getClockWise())
            return new int[]{0};
        if (pSide == this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING).getCounterClockWise())
            return new int[]{1};
        return new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getClockWise() == pDirection;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        Direction facing = this.getBlockState().getValue(MachineElectrolyticSaltSeparatorBlock.FACING);
        return facing.getCounterClockWise() == pDirection;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new MachineConcentratorContainer(pContainerId, pInventory, getBlockPos(), getLevel(), getContainerSize());
    }

}
